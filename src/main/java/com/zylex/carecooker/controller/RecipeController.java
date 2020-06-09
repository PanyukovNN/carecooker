package com.zylex.carecooker.controller;

import com.zylex.carecooker.model.Category;
import com.zylex.carecooker.model.Recipe;
import com.zylex.carecooker.model.dto.GreetingDto;
import com.zylex.carecooker.repository.CategoryRepository;
import com.zylex.carecooker.repository.RecipeRepository;
import com.zylex.carecooker.repository.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/recipe")
public class RecipeController {

    private final RecipeRepository recipeRepository;

    private final CategoryRepository categoryRepository;

    private final SectionRepository sectionRepository;

    @Autowired
    public RecipeController(RecipeRepository recipeRepository,
                            CategoryRepository categoryRepository,
                            SectionRepository sectionRepository) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.sectionRepository = sectionRepository;
    }

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/all")
    public String getAllRecipes(
            @PageableDefault(sort = { "id" }, direction = Sort.Direction.DESC, size = 30) Pageable pageable,
            Model model) {
        Page<Recipe> page = recipeRepository.findAllByToPublicationIsTrue(pageable);

        model.addAttribute("page", page);
        model.addAttribute("greetingDto", new GreetingDto("Все рецепты", null));
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("url", "/recipe/all");

        return "recipesAll";
    }

    @GetMapping("/no-section-list")
    public String getNoSectionRecipes(
            @PageableDefault(sort = { "id" }, direction = Sort.Direction.DESC, size = 30) Pageable pageable,
            Model model) {
        Page<Recipe> page = recipeRepository.findBySectionIsNull(pageable);

        model.addAttribute("page", page);
        model.addAttribute("greetingDto", new GreetingDto("Рецепты без раздела", null));
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("url", "/recipe/no-section-list");

        return "recipesAll";
    }

    @GetMapping("/to-publication")
    public String getToPublicationRecipes(
            @PageableDefault(sort = { "id" }, direction = Sort.Direction.DESC, size = 30) Pageable pageable,
            Model model) {
        Page<Recipe> page = recipeRepository.findByToPublicationIsFalse(pageable);

        model.addAttribute("page", page);
        model.addAttribute("greetingDto", new GreetingDto("Неопубликованные рецепты", null));
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("url", "/recipe/no-section-list");

        return "recipesAll";
    }

    @GetMapping("/{id}")
    public String getRecipe(@PathVariable long id,
                            Model model) {
        Recipe recipe = recipeRepository.findById(id).orElse(new Recipe());
        model.addAttribute("recipe", recipe);

        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);

        return "recipe";
    }

    @GetMapping("/add")
    public String getSaveRecipe(Model model) {
        model.addAttribute("sections", sectionRepository.findAll());
        return "recipeSaveUpdate";
    }

    @PostMapping("/add")
    public String postSaveRecipe(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam("file") MultipartFile file,
            @RequestParam String cookTime,
            @RequestParam String serving,
            @RequestParam String complexity,
            @RequestParam String ingredients,
            @RequestParam String method,
            @RequestParam String section,
            @RequestParam String categories,
            @RequestParam String toPublication) throws IOException {
        Recipe recipe = new Recipe(name,
                description,
                cookTime,
                serving,
                complexity,
                splitByNewLine(ingredients),
                method,
//                null,
                sectionRepository.findByName(section),
                parseCategories(categories),
                !toPublication.isEmpty());

        if (file != null && !StringUtils.isEmpty(file.getOriginalFilename())) {
            String resultFileName = uploadFile(file);
            recipe.setMainImage(resultFileName);
        }

        recipeRepository.save(recipe);

        return "redirect:/recipe/" + recipe.getId();
    }

    @GetMapping("/edit")
    public String getUpdateRecipe(
            @RequestParam long id,
            Model model) {
        Recipe recipe = recipeRepository.findById(id).orElse(new Recipe());
        model.addAttribute("recipe", recipe);
        model.addAttribute("sections", sectionRepository.findAll());

        return "recipeSaveUpdate";
    }

    @PostMapping("/edit")
    public String postUpdateRecipe(
            @RequestParam long id,
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam("file") MultipartFile file,
            @RequestParam String cookTime,
            @RequestParam String serving,
            @RequestParam String ingredients,
            @RequestParam String method,
            @RequestParam String section,
            @RequestParam String categories,
            @RequestParam String toPublication) throws IOException {
        Recipe recipe = recipeRepository.findById(id).orElse(new Recipe());
        recipe.setName(name);
        recipe.setDescription(description);
        recipe.setCookTime(cookTime);
        recipe.setServing(serving);
        recipe.setMethod(method);
        recipe.setSection(sectionRepository.findByName(section));
        recipe.setIngredients(splitByNewLine(ingredients));
        recipe.setCategories(parseCategories(categories));
        recipe.setToPublication(!toPublication.isEmpty());

        if (file != null && !StringUtils.isEmpty(file.getOriginalFilename())) {
            String resultFileName = uploadFile(file);
            recipe.setMainImage(resultFileName);
        }

        recipeRepository.save(recipe);

        return "redirect:/recipe/" + id;
    }

    @GetMapping("/delete")
    public String getDeleteRecipe(@RequestParam long id) {
        Recipe recipe = recipeRepository.findById(id).orElse(new Recipe());
        if (recipe.getName() != null) {
            recipeRepository.delete(recipe);
        }

        return "redirect:/";
    }

    private String uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            //noinspection ResultOfMethodCallIgnored
            uploadDir.mkdir();
        }
        String uidFile = UUID.randomUUID().toString();
        String resultFileName = uidFile + "." + file.getOriginalFilename();
        file.transferTo(new File(uploadPath + "/" + resultFileName));
        return resultFileName;
    }

    private List<Category> parseCategories(@RequestParam String categories) {
        List<String> categoryNames = splitByNewLine(categories);
        List<Category> categoryList = new ArrayList<>();
        for (String categoryName : categoryNames) {
            Category category = categoryRepository.findByName(categoryName);
            if (category == null) {
                category = new Category(categoryName);
                categoryRepository.save(category);
            }
            categoryList.add(category);
        }
        return categoryList;
    }

    private List<String> splitByNewLine(@RequestParam String ingredients) {
        return Arrays.stream(ingredients.split("\n"))
                .map(String::trim)
                .collect(Collectors.toList());
    }
}

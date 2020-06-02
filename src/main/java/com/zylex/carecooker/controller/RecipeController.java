package com.zylex.carecooker.controller;

import com.zylex.carecooker.model.Category;
import com.zylex.carecooker.model.Recipe;
import com.zylex.carecooker.repository.CategoryRepository;
import com.zylex.carecooker.repository.RecipeRepository;
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

import static com.zylex.carecooker.controller.MainController.PAGE_SIZE;

@Controller
@RequestMapping("/recipe")
public class RecipeController {

    private final RecipeRepository recipeRepository;

    private final CategoryRepository categoryRepository;

    @Autowired
    public RecipeController(RecipeRepository recipeRepository,
                            CategoryRepository categoryRepository) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
    }

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/all")
    public String getAllRecipes(
            @PageableDefault(sort = { "id" }, direction = Sort.Direction.DESC, size = PAGE_SIZE) Pageable pageable,
            Model model) {
        Page<Recipe> recipes = recipeRepository.findAll(pageable);

        model.addAttribute("recipes", recipes);
        model.addAttribute("recipesAll", "true");
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("url", "/recipe/all?");

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
    public String getSaveRecipe() {
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
            @RequestParam String categories) throws IOException {
        Recipe recipe = new Recipe(name,
                description,
                cookTime,
                serving,
                complexity,
                splitByNewLine(ingredients),
                method,
                parseCategories(categories));

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
            @RequestParam String categories) throws IOException {
        Recipe recipe = recipeRepository.findById(id).orElse(new Recipe());
        recipe.setName(name);
        recipe.setDescription(description);
        recipe.setCookTime(cookTime);
        recipe.setServing(serving);
        recipe.setMethod(method);
        recipe.setIngredients(splitByNewLine(ingredients));
        recipe.setCategories(parseCategories(categories));

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

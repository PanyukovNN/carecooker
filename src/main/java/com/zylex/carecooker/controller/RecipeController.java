package com.zylex.carecooker.controller;

import com.zylex.carecooker.model.Recipe;
import com.zylex.carecooker.model.Section;
import com.zylex.carecooker.model.User;
import com.zylex.carecooker.model.dto.GreetingDto;
import com.zylex.carecooker.repository.RecipeRepository;
import com.zylex.carecooker.repository.SectionRepository;
import com.zylex.carecooker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/recipe")
public class RecipeController {

    private static final int PAGE_SIZE = 15;

    private final RecipeRepository recipeRepository;

    private final SectionRepository sectionRepository;

    private final UserService userService;

    @Autowired
    public RecipeController(RecipeRepository recipeRepository,
                            SectionRepository sectionRepository,
                            UserService userService) {
        this.recipeRepository = recipeRepository;
        this.sectionRepository = sectionRepository;
        this.userService = userService;
    }

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/all")
    public String getAllRecipes(
            @PageableDefault(sort = { "id" }, direction = Sort.Direction.DESC, size = PAGE_SIZE) Pageable pageable,
            Model model) {
        Page<Recipe> page = recipeRepository.findAllByToPublicationIsTrue(pageable);

        model.addAttribute("page", page);
        model.addAttribute("greetingDto", new GreetingDto("Все рецепты", null));
        model.addAttribute("url", "/recipe/all");

        return "recipesAll";
    }

    @GetMapping("/no-section-list")
    public String getNoSectionRecipes(
            @PageableDefault(sort = { "id" }, direction = Sort.Direction.DESC, size = PAGE_SIZE) Pageable pageable,
            Model model) {
        Page<Recipe> page = recipeRepository.findBySectionsIsNull(pageable);

        model.addAttribute("page", page);
        model.addAttribute("greetingDto", new GreetingDto("Рецепты без раздела", ""));
        model.addAttribute("url", "/recipe/no-section-list");

        return "recipesAll";
    }

    @GetMapping("/to-publication")
    public String getToPublicationRecipes(
            @PageableDefault(sort = { "id" }, direction = Sort.Direction.DESC, size = PAGE_SIZE) Pageable pageable,
            Model model) {
        Page<Recipe> page = recipeRepository.findByToPublicationIsFalse(pageable);

        model.addAttribute("page", page);
        model.addAttribute("greetingDto", new GreetingDto("Неопубликованные рецепты", ""));
        model.addAttribute("url", "/recipe/no-section-list");

        return "recipesAll";
    }

    @GetMapping("/{id}")
    public String getRecipe(@PathVariable long id,
                            Model model) {
        Recipe recipe = recipeRepository.findById(id).orElse(new Recipe());
        if (!recipe.getSections().isEmpty()) {
            Section section = recipe.getSections().get(0);
            if (section != null) {
                List<Recipe> similarRecipes = recipeRepository.findTop6BySectionsContaining(section);
                model.addAttribute("similarRecipes", similarRecipes);
            }
        }
        recipe.incrementViews();
        if (recipe.getId() != 0) {
            recipeRepository.save(recipe);
        }
        model.addAttribute("recipe", recipe);

        User user = (User) recipe.getAuthor();
        model.addAttribute("authorRecipesNumber", recipeRepository.countByAuthor(user));

        return "recipe";
    }

    @GetMapping("/add")
    public String getSaveRecipe(Model model) {
        model.addAttribute("allSections", sectionRepository.findAll());
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
            @RequestParam(required = false, name = "sections") List<String> sectionNameList,
            @RequestParam String toPublication) throws IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) userService.loadUserByUsername(authentication.getName());

        Recipe newRecipe = new Recipe(name,
                description,
                LocalTime.parse(cookTime),
                serving == null || serving.isEmpty()
                        ? 0
                        : Integer.parseInt(serving),
                complexity,
                splitByNewLine(ingredients),
                method,
//                null,
                sectionNameList != null
                        ? sectionNameList.stream().map(sectionRepository::findByName).collect(Collectors.toList())
                        : Collections.emptyList(),
                user,
                !toPublication.isEmpty());

        if (file != null && !StringUtils.isEmpty(file.getOriginalFilename())) {
            String resultFileName = uploadFile(file);
            newRecipe.setMainImage(resultFileName);
        }

        recipeRepository.save(newRecipe);

        return "redirect:/recipe/" + newRecipe.getId();
    }

    @GetMapping("/edit")
    public String getUpdateRecipe(
            @RequestParam long id,
            Model model) {
        Recipe recipe = recipeRepository.findById(id).orElse(new Recipe());
        model.addAttribute("recipe", recipe);
        model.addAttribute("allSections", sectionRepository.findAll());

        return "recipeSaveUpdate";
    }

    @PostMapping("/edit")
    public String postUpdateRecipe(
            @RequestParam long id,
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam("file") MultipartFile file,
            @RequestParam("cookTime") String cookTimeStr,
            @RequestParam String serving,
            @RequestParam String complexity,
            @RequestParam String ingredients,
            @RequestParam String method,
            @RequestParam(required = false, name = "sections") List<String> sectionNameList,
            @RequestParam String toPublication) throws IOException {

        if (complexity.equals("Сложность")) {
            complexity = null;
        }
        Recipe editedRecipe = recipeRepository.findById(id).orElse(new Recipe());
        editedRecipe.setName(name);
        editedRecipe.setDescription(description);
        editedRecipe.setCookTime(LocalTime.parse(cookTimeStr));
        if (serving == null || serving.isEmpty()) {
            editedRecipe.setServing(0);
        } else {
            editedRecipe.setServing(Integer.parseInt(serving));
        }
        editedRecipe.setMethod(method);
        if (sectionNameList != null) {
            editedRecipe.setSections(sectionNameList.stream().map(sectionRepository::findByName).collect(Collectors.toList()));
        }
        editedRecipe.setComplexity(complexity);
        editedRecipe.setIngredients(splitByNewLine(ingredients));
        editedRecipe.setToPublication(!toPublication.isEmpty());

        if (file != null && !StringUtils.isEmpty(file.getOriginalFilename())) {
            String resultFileName = uploadFile(file);
            editedRecipe.setMainImage(resultFileName);
        }

        recipeRepository.save(editedRecipe);

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

    private List<String> splitByNewLine(@RequestParam String ingredients) {
        return Arrays.stream(ingredients.split("\n"))
                .map(String::trim)
                .collect(Collectors.toList());
    }
}

package com.zylex.carecooker.controller;

import com.zylex.carecooker.model.Recipe;
import com.zylex.carecooker.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/recipe")
public class RecipeController {

    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeController(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/{id}")
    public String getRecipe(@PathVariable long id,
                            Model model) {
        Recipe recipe = recipeRepository.findById(id).get();
        model.addAttribute("recipe", recipe);

        return "recipe";
    }

    @GetMapping("/add")
    public String getAddRecipe() {
        return "addRecipe";
    }

    @PostMapping("/add")
    public String addMessage(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam("file") MultipartFile file,
            @RequestParam String cookTime,
            @RequestParam String serving,
            @RequestParam String ingredients,
            @RequestParam String method,
            @RequestParam String categories,
            Model model) throws IOException {

        List<String> ingredientsList = Arrays.stream(ingredients.split("\n"))
                .map(String::trim)
                .collect(Collectors.toList());
        List<String> categoriesList = Arrays.stream(categories.split("\n"))
                .map(String::trim)
                .collect(Collectors.toList());

        Recipe recipe = new Recipe(name, description, cookTime, serving, ingredientsList, method, categoriesList);

        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uidFile = UUID.randomUUID().toString();
            String resultFileName = uidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFileName));

            recipe.setMainImage(resultFileName);
        }

        recipeRepository.save(recipe);

        model.addAttribute("successfullyAdded", "true");
        return "addRecipe";
    }

    @GetMapping("/edit")
    public String getEditRecipe(
            @RequestParam long id,
            Model model) {
        Recipe recipe = recipeRepository.findById(id).get();
        model.addAttribute("recipe", recipe);

        return "addRecipe";
    }

    @PostMapping("/edit")
    public String editRecipe(
            @RequestParam long id,
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam("file") MultipartFile file,
            @RequestParam String cookTime,
            @RequestParam String serving,
            @RequestParam String ingredients,
            @RequestParam String method,
            @RequestParam String categories,
            Model model) throws IOException {

        List<String> ingredientsList = Arrays.stream(ingredients.split("\n"))
                .map(String::trim)
                .collect(Collectors.toList());
        List<String> categoriesList = Arrays.stream(categories.split("\n"))
                .map(String::trim)
                .collect(Collectors.toList());

        Recipe recipe = recipeRepository.findById(id).get();
        recipe.setName(name);
        recipe.setDescription(description);
        recipe.setCookTime(cookTime);
        recipe.setServing(serving);
        recipe.setMethod(method);
        recipe.setIngredients(ingredientsList);
        recipe.setCategories(categoriesList);

        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uidFile = UUID.randomUUID().toString();
            String resultFileName = uidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFileName));

            recipe.setMainImage(resultFileName);
        }

        recipeRepository.save(recipe);

        model.addAttribute("successfullyUpdated", "true");
        return "redirect:/recipe/" + id;
    }
}

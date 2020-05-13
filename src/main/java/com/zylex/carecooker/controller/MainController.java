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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class MainController {

    private RecipeRepository recipeRepository;

    @Autowired
    public MainController(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/")
    public String main(Model model,
                       @PageableDefault(sort = { "id" }, direction = Sort.Direction.DESC, size = 6) Pageable pageable) {
        Page<Recipe> recipes = recipeRepository.findAll(pageable);

        model.addAttribute("recipes", recipes);
        model.addAttribute("url", "/");

        return "main";
    }

    @GetMapping("/add_recipe")
    public String getAddRecipe() {
        return "add_recipe";
    }

    @PostMapping("/add_recipe")
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

        model.addAttribute("successfully_added", "true");
        return "add_recipe";
    }

    @GetMapping("/recipe")
    public String getRecipe(@RequestParam long id,
                            Model model) {

        Recipe recipe = recipeRepository.findById(id).get();

        model.addAttribute("recipe", recipe);

        return "recipe";
    }
}

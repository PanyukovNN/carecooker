package com.zylex.carecooker.controller;

import com.zylex.carecooker.model.Recipe;
import com.zylex.carecooker.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    private final RecipeRepository recipeRepository;

    @Autowired
    public MainController(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @GetMapping("/")
    public String mainPage(Model model,
                           @PageableDefault(sort = { "id" }, direction = Sort.Direction.DESC, size = 6) Pageable pageable) {
        Page<Recipe> recipes = recipeRepository.findAll(pageable);

        model.addAttribute("recipes", recipes);
        model.addAttribute("url", "/");

        return "main";
    }
}

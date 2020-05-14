package com.zylex.carecooker.controller;

import com.zylex.carecooker.model.Category;
import com.zylex.carecooker.model.Recipe;
import com.zylex.carecooker.repository.CategoryRepository;
import com.zylex.carecooker.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    private final RecipeRepository recipeRepository;

    private final CategoryRepository categoryRepository;

    @Autowired
    public MainController(RecipeRepository recipeRepository,
                          CategoryRepository categoryRepository) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/")
    public String mainPage(Model model,
                           @RequestParam(name = "category", required = false, defaultValue = "") String categoryFilter,
                           @PageableDefault(sort = { "id" }, direction = Sort.Direction.DESC, size = 6) Pageable pageable) {
        Category category = categoryRepository.findByName(categoryFilter);
        Page<Recipe> recipes = category == null
                ? recipeRepository.findAll(pageable)
                : recipeRepository.findByCategoriesContaining(category, pageable);

        model.addAttribute("recipes", recipes);
        model.addAttribute("category", category);
        String url = category == null
                ? "/?"
                : "?category=" + category.getName() + "&";
        model.addAttribute("url", url);

        return "main";
    }
}

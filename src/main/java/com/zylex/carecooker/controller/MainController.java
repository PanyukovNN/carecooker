package com.zylex.carecooker.controller;

import com.zylex.carecooker.model.Category;
import com.zylex.carecooker.model.Recipe;
import com.zylex.carecooker.model.Section;
import com.zylex.carecooker.repository.CategoryRepository;
import com.zylex.carecooker.repository.RecipeRepository;
import com.zylex.carecooker.repository.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
public class MainController {

    private final RecipeRepository recipeRepository;

    private final SectionRepository sectionRepository;

    private final CategoryRepository categoryRepository;

    public static final int PAGE_SIZE = 9;

    @Autowired
    public MainController(RecipeRepository recipeRepository,
                          SectionRepository sectionRepository,
                          CategoryRepository categoryRepository) {
        this.recipeRepository = recipeRepository;
        this.sectionRepository = sectionRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/")
    public String mainPage(
            @PageableDefault(sort = { "id" }, direction = Sort.Direction.DESC, size = PAGE_SIZE) Pageable pageable,
            Model model) {
        List<Section> sections = sectionRepository.findAll();
        sections.sort(Comparator.comparing(Section::getPosition).thenComparing(Section::getId));

        Map<Section, Page<Recipe>> sectionRecipes = new LinkedHashMap<>();
        for (Section section : sections) {
            sectionRecipes.put(section, recipeRepository.findByCategoriesContaining(section.getCategory(), pageable));
        }

        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("sections", sections);
//        model.addAttribute("sectionRecipes", sectionRecipes);
        model.addAttribute("url", "/?");

        return "main";
    }

    @GetMapping("category")
    public String getByCategory(
            @RequestParam(name = "category", required = false, defaultValue = "") String categoryName,
            @PageableDefault(sort = { "id" }, direction = Sort.Direction.DESC, size = PAGE_SIZE) Pageable pageable,
            Model model) {
        Category category = categoryRepository.findByName(categoryName);
        Page<Recipe> recipes = recipeRepository.findByCategoriesContaining(category, pageable);

        model.addAttribute("recipes", recipes);
        model.addAttribute("category", category);
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("url", "/category?category=" + categoryName + "&");

        return "main";
    }

    @PostMapping("category")
    public String postByCategory(
            @RequestParam(name = "category", required = false, defaultValue = "") String categoryName,
            @PageableDefault(sort = { "id" }, direction = Sort.Direction.DESC, size = PAGE_SIZE) Pageable pageable,
            Model model) {
        Category category = categoryRepository.findByName(categoryName);
        Page<Recipe> recipes = recipeRepository.findByCategoriesContaining(category, pageable);

        model.addAttribute("recipes", recipes);
        model.addAttribute("category", category);
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("url", "/category?category=" + categoryName + "&");

        return "main";
    }

    @PostMapping("filter")
    public String postByFilter(
            @RequestParam(name = "filter", required = false, defaultValue = "") String filter,
            @PageableDefault(sort = { "id" }, direction = Sort.Direction.DESC, size = PAGE_SIZE) Pageable pageable,
            Model model) {
        Page<Recipe> recipes = recipeRepository.findByNameContainingIgnoreCase(filter, pageable);

        model.addAttribute("recipes", recipes);
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("url", "/filter?filter=" + filter + "&");

        return "main";
    }

    @GetMapping("filter")
    public String getByFilter(
            @RequestParam(name = "filter", required = false, defaultValue = "") String filter,
            @PageableDefault(sort = { "id" }, direction = Sort.Direction.DESC, size = PAGE_SIZE) Pageable pageable,
            Model model) {
        Page<Recipe> recipes = recipeRepository.findByNameContainingIgnoreCase(filter, pageable);

        model.addAttribute("recipes", recipes);
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("url", "/filter?filter=" + filter + "&");

        return "main";
    }
}

package com.zylex.carecooker.controller;

import com.zylex.carecooker.model.Section;
import com.zylex.carecooker.model.dto.GreetingDto;
import com.zylex.carecooker.repository.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;

@Controller
public class MainController {

    private final SectionRepository sectionRepository;

    public final int PAGE_SIZE = 30;

    @Autowired
    public MainController(SectionRepository sectionRepository) {
        this.sectionRepository = sectionRepository;
    }

    @GetMapping("/")
    public String mainPage(
            @PageableDefault(sort = { "id" }, direction = Sort.Direction.DESC, size = 30) Pageable pageable,
            Model model) {
        List<Section> sections = sectionRepository.findAll();
        sections.sort(Comparator.comparing(Section::getPosition).thenComparing(Section::getId));

        model.addAttribute("greetingDto", new GreetingDto("Разделы", "Выберите интересующий раздел."));
        model.addAttribute("sections", sections);
        model.addAttribute("mainPage", "true");
        model.addAttribute("url", "/?");

        return "main";
    }

//    @PostMapping("filter")
//    public String postByFilter(
//            @RequestParam(name = "filter", required = false, defaultValue = "") String filter,
//            @PageableDefault(sort = { "id" }, direction = Sort.Direction.DESC, size = 30) Pageable pageable,
//            Model model) {
//        Page<Recipe> recipes = recipeRepository.findByNameContainingIgnoreCase(filter, pageable);
//
//        model.addAttribute("recipes", recipes);
//        model.addAttribute("categories", categoryRepository.findAll());
//        model.addAttribute("url", "/filter?filter=" + filter + "&");
//
//        return "main";
//    }

//    @GetMapping("filter")
//    public String getByFilter(
//            @RequestParam(name = "filter", required = false, defaultValue = "") String filter,
//            @PageableDefault(sort = { "id" }, direction = Sort.Direction.DESC, size = 30) Pageable pageable,
//            Model model) {
//        Page<Recipe> recipes = recipeRepository.findByNameContainingIgnoreCase(filter, pageable);
//
//        model.addAttribute("recipes", recipes);
//        model.addAttribute("categories", categoryRepository.findAll());
//        model.addAttribute("url", "/filter?filter=" + filter + "&");
//
//        return "main";
//    }
}

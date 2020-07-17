package com.zylex.carecooker.controller;

import com.zylex.carecooker.model.Dish;
import com.zylex.carecooker.model.Recipe;
import com.zylex.carecooker.model.Section;
import com.zylex.carecooker.repository.DishRepository;
import com.zylex.carecooker.repository.RecipeRepository;
import com.zylex.carecooker.repository.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
@RequestMapping("/dish")
public class DishController {

    private final DishRepository dishRepository;

    private final RecipeRepository recipeRepository;

    private final SectionRepository sectionRepository;

    @Autowired
    public DishController(DishRepository dishRepository,
                          RecipeRepository recipeRepository,
                          SectionRepository sectionRepository) {
        this.dishRepository = dishRepository;
        this.recipeRepository = recipeRepository;
        this.sectionRepository = sectionRepository;
    }

    @GetMapping("/list")
    public String getDishes(Model model) {
        List<Section> sections = sectionRepository.findAll();
        sections.sort(Comparator.comparing(Section::getPosition).thenComparing(Section::getId));

        model.addAttribute("sections", sections);
        model.addAttribute("currentSection", sections.get(0));

        List<Dish> dishes = dishRepository.findBySection(sections.get(0));
        dishes.sort(Comparator.comparing(Dish::getId));

        model.addAttribute("dishes", dishes);

        return "dishEditList";
    }

    @PostMapping("/list")
    public String postDishes(
            @RequestParam Section section,
            Model model) {
        List<Section> sections = sectionRepository.findAll();
        sections.sort(Comparator.comparing(Section::getPosition).thenComparing(Section::getId));

        model.addAttribute("sections", sections);
        model.addAttribute("currentSection", section);

        List<Dish> dishes = dishRepository.findBySection(section);
        dishes.sort(Comparator.comparing(Dish::getId));

        model.addAttribute("dishes", dishes);

        return "dishEditList";
    }

    @ResponseBody
    @GetMapping("/json/{id}")
    public Optional<Dish> getJsonDish(@PathVariable long id) {
        return dishRepository.findById(id);
    }

    @ResponseBody
    @GetMapping(value = "/json/section/{id}", produces = "application/json")
    public List<Dish> getJsonDishesBySection(@PathVariable(name = "id") long sectionId) {
        Section section = sectionRepository.findById(sectionId).orElseThrow(IllegalArgumentException::new);

        List<Dish> sectionDishes = dishRepository.findBySection(section);
        sectionDishes.sort(Comparator.comparing(Dish::getId));

        return sectionDishes;
    }

    @ResponseBody
    @GetMapping(value = "/json/sections", produces = "application/json")
    public List<Section> getJsonSections() {
        List<Section> sections = sectionRepository.findAll();
        sections.sort(Comparator.comparing(Section::getId));

        return sections;
    }

    @ResponseBody
    @GetMapping(value = "/json/dishes", produces = "application/json")
    public List<Dish> getJsonDishes() {
        List<Dish> dishes = dishRepository.findAll();
        dishes.sort(Comparator.comparing(Dish::getId));

        return dishes;
    }

    @PostMapping("/update")
    public ModelAndView postUpdateDish(
            Dish dish,
            ModelMap model) {
        model.addAttribute("currentSection", dish.getSection());
        if (dish.getId() == 0) {
            Dish dishFromDb = dishRepository.findByName(dish.getName());
            if (dishFromDb != null) {
                model.addAttribute("dishExists", "");

                return new ModelAndView("forward:/dish/list", model);
            } else {
                dishRepository.save(dish);

                return new ModelAndView("forward:/dish/list", model);
            }
        } else {
            Dish dishWithSameName = dishRepository.findByName(dish.getName());
            if (dishWithSameName != null && dishWithSameName.getId() != dish.getId()) {
                model.addAttribute("dishExists", "");

                return new ModelAndView("forward:/dish/list", model);
            }

            dishRepository.save(dish);

            return new ModelAndView("forward:/dish/list", model);
        }
    }

    @GetMapping("/delete")
    public String getDeleteSection(@RequestParam long id) {
        Dish dish = dishRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        List<Recipe> recipes = recipeRepository.findByDishesContaining(dish);
        for (Recipe recipe : recipes) {
            recipe.getDishes().remove(dish);
            recipeRepository.save(recipe);
        }

        dishRepository.delete(dish);

        return "redirect:/dish/list";
    }
}

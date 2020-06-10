package com.zylex.carecooker.controller;

import com.zylex.carecooker.model.Dish;
import com.zylex.carecooker.model.Recipe;
import com.zylex.carecooker.model.Section;
import com.zylex.carecooker.model.User;
import com.zylex.carecooker.repository.DishRepository;
import com.zylex.carecooker.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/dish")
public class DishController {

    private final DishRepository dishRepository;

    private final RecipeRepository recipeRepository;

    @Autowired
    public DishController(DishRepository dishRepository,
                          RecipeRepository recipeRepository) {
        this.dishRepository = dishRepository;
        this.recipeRepository = recipeRepository;
    }

    @GetMapping("/list")
    public String getSections(Model model) {
        List<Dish> dishes = dishRepository.findAll();
        dishes.sort(Comparator.comparing(Dish::getId));

        model.addAttribute("dishes", dishes);

        return "dishEditList";
    }

    @ResponseBody
    @GetMapping("/json/{id}")
    public Optional<Dish> getJsonDish(@PathVariable long id) {
        return dishRepository.findById(id);
    }

    @PostMapping("/add")
    public String postAddDish(
            @RequestParam String name,
            @RequestParam String description,
            Model model) {
        Dish dishFromDb = dishRepository.findByName(name);
        if (dishFromDb != null) {
            model.addAttribute("dishExists", "");

            return "redirect:/dish/list";
        }

        Dish dish = new Dish(name, description);
        dishRepository.save(dish);

        return "redirect:/dish/list";
    }

    @PostMapping("/update")
    public String postUpdateDish(
            Dish dish,
            Model model) {
        if (dish.getId() == 0) {
            Dish dishFromDb = dishRepository.findByName(dish.getName());
            if (dishFromDb != null) {
                model.addAttribute("dishExists", "");

                return "redirect:/dish/list";
            } else {
                dishRepository.save(dish);

                return "redirect:/dish/list";
            }
        } else {
            Dish dishWithSameName = dishRepository.findByName(dish.getName());
            if (dishWithSameName != null && dishWithSameName.getId() != dish.getId()) {
                model.addAttribute("dishExists", "");

                return "redirect:/dish/list";
            }

            dishRepository.save(dish);

            return "redirect:/dish/list";
        }
    }

    @GetMapping("/delete")
    public String getDeleteSection(@RequestParam long id) {
        Dish dish = dishRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        List<Recipe> recipes = recipeRepository.findByDish(dish);
        for (Recipe recipe : recipes) {
            recipe.setDish(null);
            recipeRepository.save(recipe);
        }

        dishRepository.delete(dish);

        return "redirect:/dish/list";
    }
}

package com.zylex.carecooker.controller;

import com.zylex.carecooker.model.Dish;
import com.zylex.carecooker.model.Recipe;
import com.zylex.carecooker.model.Section;
import com.zylex.carecooker.model.User;
import com.zylex.carecooker.model.dto.GreetingDto;
import com.zylex.carecooker.model.dto.RecipeCardDto;
import com.zylex.carecooker.repository.DishRepository;
import com.zylex.carecooker.repository.RecipeRepository;
import com.zylex.carecooker.repository.SectionRepository;
import com.zylex.carecooker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/recipe")
public class RecipeController {

    public static final int PAGE_SIZE = 9;

    private final RecipeRepository recipeRepository;

    private final SectionRepository sectionRepository;

    private final UserService userService;

    private final DishRepository dishRepository;

    @Autowired
    public RecipeController(RecipeRepository recipeRepository,
                            SectionRepository sectionRepository,
                            UserService userService,
                            DishRepository dishRepository) {
        this.recipeRepository = recipeRepository;
        this.sectionRepository = sectionRepository;
        this.userService = userService;
        this.dishRepository = dishRepository;
    }

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping(value = "/all", produces = "text/html")
    public String getAllRecipes(Model model) {
        model.addAttribute("greetingDto", new GreetingDto("Рецепты", null));

        return "recipesAll";
    }

    @ResponseBody
    @GetMapping(value = "/all", produces = "application/json")
    public Page<RecipeCardDto> getRecipesPage(@RequestParam int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE, Sort.by(Sort.Direction.DESC, "publicationDateTime"));

        return recipeRepository.findAllByToPublicationIsTrue(pageable).map(RecipeCardDto::new);
    }

    @GetMapping(value = "/section/{id}", produces = "text/html")
    public String getSectionRecipes(
            @PathVariable long id,
            Model model) {
        Section section = sectionRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        model.addAttribute("section", section);
        model.addAttribute("greetingDto", new GreetingDto(section.getName(), null));

        return "section";
    }

    @ResponseBody
    @GetMapping(value = "/section/{id}", produces = "application/json")
    public Page<RecipeCardDto> getSectionRecipesPage(
            @PathVariable long id,
            @RequestParam int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE, Sort.by(Sort.Direction.DESC, "publicationDateTime"));
        Section section = sectionRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        return recipeRepository.findBySectionsContainingAndToPublicationIsTrue(section, pageable).map(RecipeCardDto::new);
    }

    @GetMapping(value = "/dish/{dishId}", produces = "text/html")
    public String getSectionDishRecipesByDish(
            @PathVariable long dishId,
            Model model) {
        Dish dish = dishRepository.findById(dishId).orElseThrow(IllegalArgumentException::new);

        model.addAttribute("section", dish.getSection());
        model.addAttribute("dish", dish);
        model.addAttribute("greetingDto",
                new GreetingDto(dish.getSection().getName() + " • " + dish.getName(),
                        dish.getTitleDescription()));

        return "section";
    }

    @ResponseBody
    @GetMapping(value = "/dish/{dishId}", produces = "application/json")
    public Page<RecipeCardDto> getSectionDishRecipesPageByDishJson(
            @PathVariable long dishId,
            @RequestParam int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE, Sort.by(Sort.Direction.DESC, "publicationDateTime"));
        Dish dish = dishRepository.findById(dishId).orElseThrow(IllegalArgumentException::new);

        return recipeRepository.findBySectionsContainingAndDishesContainingAndToPublicationIsTrue(dish.getSection(), dish, pageable).map(RecipeCardDto::new);
    }

    @GetMapping(value = "/search", produces = "text/html")
    public String getSearchedRecipes(Model model,
                                     @RequestParam String filter) {
        model.addAttribute("greetingDto", new GreetingDto("«" + filter + "»", " "));

        return "recipesAll";
    }

    @ResponseBody
    @GetMapping(value = "/search", produces = "application/json")
    public Page<RecipeCardDto> getSearchedRecipesJson(
            @RequestParam String filter,
            @RequestParam int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE, Sort.by(Sort.Direction.DESC, "publicationDateTime"));

        return recipeRepository.findByNameContainingIgnoreCase(filter, pageable).map(RecipeCardDto::new);
    }

    @GetMapping(value = "/no-section-list", produces = "text/html")
    public String getNoSectionRecipes(Model model) {
        model.addAttribute("greetingDto", new GreetingDto("Рецепты без раздела", ""));
        model.addAttribute("url", "/recipe/no-section-list");

        return "recipesAll";
    }

    @ResponseBody
    @GetMapping(value = "/no-section-list", produces = "application/json")
    public Page<RecipeCardDto> getNoSectionRecipesPage(@RequestParam int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE, Sort.by(Sort.Direction.DESC, "publicationDateTime"));

        return recipeRepository.findBySectionsIsNull(pageable).map(RecipeCardDto::new);
    }

    @GetMapping(value = "/to-publication", produces = "text/html")
    public String getToPublicationRecipes(Model model) {
        model.addAttribute("greetingDto", new GreetingDto("Неопубликованные рецепты", ""));
        model.addAttribute("url", "/recipe/to-publication");

        return "recipesAll";
    }

    @ResponseBody
    @GetMapping(value = "/to-publication", produces = "application/json")
    public Page<RecipeCardDto> getToPublicationRecipesPage(@RequestParam int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE, Sort.by(Sort.Direction.DESC, "publicationDateTime"));

        return recipeRepository.findByToPublicationIsFalse(pageable).map(RecipeCardDto::new);
    }

    @GetMapping(value = "/{id}", produces = "text/html")
    public String getRecipe(@PathVariable long id,
                            HttpServletRequest request,
                            Model model) {
        Recipe recipe = recipeRepository.findById(id).orElse(new Recipe());
        if (!recipe.getSections().isEmpty()) {
            Section section = recipe.getSections().get(0);
            if (section != null) {
                List<Recipe> similarRecipes = recipeRepository.findTop7BySectionsContaining(section);
                similarRecipes.remove(recipe);
                if (similarRecipes.size() > 6) {
                    similarRecipes.remove(6);
                }
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

        request.getSession().setAttribute("url_prior_login", request.getHeader("Referer"));

        return "recipe";
    }

    @ResponseBody
    @GetMapping(value = "/{id}", produces = "application/json")
    public Recipe getRecipeJson(@PathVariable long id) {
        return recipeRepository.findById(id).orElseThrow(IllegalArgumentException::new);
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
            @RequestParam(required = false) Long dish,
            @RequestParam String ingredients,
            @RequestParam String method,
//            @RequestParam(required = false, name = "sections") List<String> sectionNameList,
            @RequestParam Long section,
            @RequestParam String toPublication) throws IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) userService.loadUserByUsername(authentication.getName());

        Recipe newRecipe = new Recipe(name,
                description,
                cookTime == null || cookTime.isEmpty()
                        ? LocalTime.of(0, 0)
                        : LocalTime.parse(cookTime),
                serving == null || serving.isEmpty()
                        ? 0
                        : Integer.parseInt(serving),
                complexity,
                splitByNewLine(ingredients),
                method,
                dish != null && dish != 0
                        ? Arrays.asList(dishRepository.findById(dish).orElseThrow(IllegalArgumentException::new))
                        : Collections.emptyList(),
//                sectionNameList != null
//                        ? sectionNameList.stream().map(sectionRepository::findByName).collect(Collectors.toList())
//                        : Collections.emptyList(),
                section != null && section != 0
                        ? Arrays.asList(sectionRepository.findById(section).orElseThrow(IllegalArgumentException::new))
                        : Collections.emptyList(),
                user,
                !toPublication.isEmpty());

        if (newRecipe.isToPublication()) {
            newRecipe.setPublicationDateTime(LocalDateTime.now());
        }

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
            @RequestParam(required = false) Long dish,
            @RequestParam String ingredients,
            @RequestParam String method,
//            @RequestParam(required = false, name = "sections") List<String> sectionNameList,
            @RequestParam Long section,
            @RequestParam String toPublication) throws IOException {
        Recipe editedRecipe = recipeRepository.findById(id).orElse(new Recipe());
        editedRecipe.setName(name);
        editedRecipe.setDescription(description);
        editedRecipe.setCookTime(cookTimeStr == null || cookTimeStr.isEmpty()
                ? LocalTime.of(0, 0)
                : LocalTime.parse(cookTimeStr));
        if (serving == null || serving.isEmpty()) {
            editedRecipe.setServing(0);
        } else {
            editedRecipe.setServing(Integer.parseInt(serving));
        }
        editedRecipe.setMethod(method);
//        if (sectionNameList != null) {
//            editedRecipe.setSections(sectionNameList.stream().map(sectionRepository::findByName).collect(Collectors.toList()));
//        } else {
//            editedRecipe.setSections(Collections.emptyList());
//        }
        editedRecipe.getSections().clear();
        if (section != null && section != 0) {
            editedRecipe.getSections().add(sectionRepository.findById(section).orElseThrow(IllegalArgumentException::new));
        }
        editedRecipe.setComplexity(complexity);

        editedRecipe.setIngredients(splitByNewLine(ingredients));
        boolean toPublicationBool = !toPublication.isEmpty();
        if (toPublicationBool) {
            editedRecipe.setPublicationDateTime(LocalDateTime.now());
        }
        editedRecipe.setToPublication(toPublicationBool);
        editedRecipe.getDishes().clear();
        if (dish != null && dish != 0) {
            editedRecipe.getDishes().add(dishRepository.findById(dish).orElseThrow(IllegalArgumentException::new));
        }

        if (file != null && !StringUtils.isEmpty(file.getOriginalFilename())) {
            String resultFileName = uploadFile(file);
            editedRecipe.setMainImage(resultFileName);
        }
//        editedRecipe.setPublicationDateTime(LocalDateTime.now());
        recipeRepository.save(editedRecipe);

        return "redirect:/recipe/" + id;
    }

    @GetMapping("/delete")
    public String getDeleteRecipe(@RequestParam long id,
                                  HttpServletRequest request) {
        Recipe recipe = recipeRepository.findById(id).orElse(new Recipe());
        if (recipe.getName() != null) {
            recipeRepository.delete(recipe);
        }

        HttpSession session = request.getSession();
        if (session != null) {
            String redirectUrl = (String) session.getAttribute("url_prior_login");
            if (redirectUrl != null) {
                session.removeAttribute("url_prior_login");
                return "redirect:" + redirectUrl;
            }
        }

        return "redirect:/";
    }

    @GetMapping("/publish")
    public String getPublishRecipe(@RequestParam long id) {
        Recipe recipe = recipeRepository.findById(id).orElse(new Recipe());
        if (recipe.getName() != null) {
            recipe.setToPublication(true);
            recipe.setPublicationDateTime(LocalDateTime.now());
            recipeRepository.save(recipe);
        }

        return "redirect:/recipe/all";
    }

    @GetMapping("/unpublish")
    public String getUnpublishRecipe(@RequestParam long id) {
        Recipe recipe = recipeRepository.findById(id).orElse(new Recipe());
        if (recipe.getName() != null) {
            recipe.setToPublication(false);
            recipeRepository.save(recipe);
        }

        return "redirect:/recipe/all";
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

package com.zylex.carecooker.controller;

import com.zylex.carecooker.model.*;
import com.zylex.carecooker.model.dto.GreetingDto;
import com.zylex.carecooker.model.dto.RecipeCardDto;
import com.zylex.carecooker.repository.*;
import com.zylex.carecooker.service.S3Services;
import com.zylex.carecooker.service.UserService;
import org.hibernate.internal.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final IngredientRepository ingredientRepository;

    private final IngredientAmountRepository ingredientAmountRepository;

    private final S3Services s3Services;

    @Autowired
    public RecipeController(RecipeRepository recipeRepository,
                            SectionRepository sectionRepository,
                            UserService userService,
                            DishRepository dishRepository,
                            IngredientRepository ingredientRepository,
                            IngredientAmountRepository ingredientAmountRepository,
                            S3Services s3Services) {
        this.recipeRepository = recipeRepository;
        this.sectionRepository = sectionRepository;
        this.userService = userService;
        this.dishRepository = dishRepository;
        this.ingredientRepository = ingredientRepository;
        this.ingredientAmountRepository = ingredientAmountRepository;
        this.s3Services = s3Services;
    }

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

    @GetMapping(value = "/incomplete", produces = "text/html")
    public String getToEditRecipes(Model model) {
        model.addAttribute("greetingDto", new GreetingDto("Неполные рецепты", ""));
        model.addAttribute("url", "/recipe/incomplete");

        return "recipesAll";
    }

    @ResponseBody
    @GetMapping(value = "/incomplete", produces = "application/json")
    public Page<RecipeCardDto> getToEditRecipesPage(@RequestParam int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE, Sort.by(Sort.Direction.DESC, "publicationDateTime"));

        return recipeRepository.findIncomplete(pageable).map(RecipeCardDto::new);
    }

    @GetMapping(value = "/{id}", produces = "text/html")
    public String getRecipe(@PathVariable long id,
                            HttpServletRequest request,
                            Model model) {
        Recipe recipe = recipeRepository.findById(id).orElse(new Recipe());
        if (recipe.getId() == 0) {
            return "redirect:/recipe/all";
        }

        model.addAttribute("similarRecipes", findSimilarRecipes(recipe));

        recipe.incrementViews();
        if (recipe.getId() != 0) {
            recipeRepository.save(recipe);
        }
        model.addAttribute("recipe", recipe);

        if (StringHelper.isEmpty(recipe.getSource())) {
            User user = (User) recipe.getAuthor();
            model.addAttribute("authorRecipesNumber", recipeRepository.countByAuthorAndSourceIsNull(user));
        } else {
            model.addAttribute("source", recipe.getSource());
        }

        setupRedirectionUrl(request);

        return "recipe";
    }

    private void setupRedirectionUrl(HttpServletRequest request) {
        if (request != null && request.getHeader("Referer") != null && request.getRequestURL() != null) {
            if (!request.getHeader("Referer").startsWith(request.getRequestURL().toString())) {
                if (request.getSession().getAttribute("url_prior_login") == null) {
                    request.getSession().setAttribute("url_prior_login", request.getHeader("Referer"));
                }
            }
        }
    }

    private List<Recipe> findSimilarRecipes(Recipe recipe) {
        if (recipe.getSections() == null || recipe.getSections().isEmpty()) {
            return new ArrayList<>();
        }

        List<Section> sections = recipe.getSections();
        List<Recipe> similarRecipes = new ArrayList<>();

        for (Section section : sections) {
            if (section != null) {
                List<Recipe> recipesFromDb = recipeRepository.findTop7BySectionsContaining(section);
                recipesFromDb.remove(recipe);
                for (Recipe recipeFromDb : recipesFromDb) {
                    if (!similarRecipes.contains(recipeFromDb)) {
                        similarRecipes.add(recipeFromDb);
                        if (similarRecipes.size() > 5) {
                            return similarRecipes;
                        }
                    }
                }
            }
        }

        return similarRecipes;
    }

    @ResponseBody
    @GetMapping(value = "/{id}", produces = "application/json")
    public Recipe getRecipeJson(@PathVariable long id) {
        return recipeRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    @GetMapping("/update")
    public String getUpdateRecipe(
            @RequestParam(required = false) Long id,
            Model model) {
        if (id != null) {
            Recipe recipe = recipeRepository.findById(id).orElseThrow(IllegalArgumentException::new);
            model.addAttribute("recipe", recipe);
        }
        model.addAttribute("allSections", sectionRepository.findAll());

        return "recipeSaveUpdate";
    }

    @PostMapping("/update")
    public String postSaveRecipe(
            @RequestParam(required = false) Long id,
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam("file") MultipartFile file,
            @RequestParam String fileName,
            @RequestParam("cookTime") String cookTimeStr,
            @RequestParam("serving") String servingStr,
            @RequestParam String complexity,
            @RequestParam List<String> ingredientName,
            @RequestParam List<String> ingredientAmount,
            @RequestParam List<String> ingredientUnits,
            @RequestParam String ingredientHeap,
            @RequestParam List<Object> method,
            @RequestParam("sections") Set<Long> sectionIds,
            @RequestParam(name = "dishes", required = false) Set<Long> dishIds,
            @RequestParam("toPublication") String toPublicationStr,
            @RequestParam String source,
            HttpServletRequest request) throws IOException {
        Recipe recipe = new Recipe();
        if (id != null && id != 0) {
            recipe = recipeRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        }

        recipe.setName(name.trim());
        recipe.setDescription(description.trim());
        recipe.setCookTime(cookTimeStr == null || cookTimeStr.isEmpty()
                ? LocalTime.of(0, 0)
                : LocalTime.parse(cookTimeStr));
        recipe.setServing(servingStr == null || servingStr.isEmpty()
                ? 0
                : Integer.parseInt(servingStr));
        recipe.setComplexity(complexity);

        List<IngredientAmount> ingredientAmounts = new ArrayList<>();
        int ingredientPosition = 0;
        for (int i = 0; i < ingredientName.size(); i++) {
            if (ingredientName.get(i) != null) {
                ingredientName.set(i, ingredientName.get(i).trim());
            }
            if (StringHelper.isEmpty(ingredientName.get(i))) {
                continue;
            }
            float amount = StringHelper.isEmpty(ingredientAmount.get(i))
                    ? 0
                    : Float.parseFloat(ingredientAmount.get(i));

            Ingredient ingredientFromDb = ingredientRepository.findByName(ingredientName.get(i));
            Ingredient ingredient = ingredientFromDb == null
                    ? ingredientRepository.save(new Ingredient(ingredientName.get(i)))
                    : ingredientFromDb;

            ingredientAmounts.add(
                    new IngredientAmount(
                            ingredient,
                            amount,
                            Units.valueOf(ingredientUnits.get(i)),
                            ingredientPosition++
                    ))
            ;
        }
        List<IngredientAmount> oldIngredientAmounts = recipe.getIngredientAmounts();
        recipe.setIngredientAmounts(ingredientAmounts);
        if (oldIngredientAmounts != null) {
            oldIngredientAmounts.forEach(ingredientAmountRepository::delete);
        }

        if (!StringUtils.isEmpty(ingredientHeap)) {
            ingredientHeap = ingredientHeap.trim();
            List<String> ingredientHeapLines = Arrays.asList(ingredientHeap.split("\n"));
            ingredientHeapLines.removeIf(String::isEmpty);
            recipe.setIngredientHeap(ingredientHeap);
        } else {
            recipe.setIngredientHeap("");
        }

        List<String> methodSteps = method.stream()
                .map(String::valueOf)
                .collect(Collectors.toList());
        for (int i = 0; i < methodSteps.size(); i++) {
            if (methodSteps.get(i) != null) {
                methodSteps.set(i, methodSteps.get(i).trim());
            }
        }
        while (methodSteps.contains("")) {
            methodSteps.remove("");
        }
        recipe.setMethod(methodSteps);

        List<Section> sections = new ArrayList<>();
        for (Long sectionId : sectionIds.stream().sorted(Long::compareTo).collect(Collectors.toList())) {
            if (sectionId != null && sectionId != 0) {
                sections.add(sectionRepository.findById(sectionId).orElseThrow(IllegalArgumentException::new));
            }
        }
        recipe.setSections(new ArrayList<>(sections));

        List<Dish> dishes = new ArrayList<>();
        if (dishIds != null) {
            for (Long dishId : dishIds.stream().sorted(Long::compareTo).collect(Collectors.toList())) {
                if (dishId != null && dishId != 0) {
                    dishes.add(dishRepository.findById(dishId).orElseThrow(IllegalArgumentException::new));
                }
            }
        }
        recipe.setDishes(new ArrayList<>(dishes));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) userService.loadUserByUsername(authentication.getName());

        recipe.setAuthor(user);

        boolean toPublication = !toPublicationStr.isEmpty();
        recipe.setToPublication(toPublication);

        recipe.setSource(StringHelper.isNotEmpty(source)
                ? source
                : "");

        if (toPublication) {
            recipe.setPublicationDateTime(LocalDateTime.now());
        }

        // Если есть новый файл то загрузить в облако
        if (file != null && StringHelper.isNotEmpty(file.getOriginalFilename())) {
            if (!file.getOriginalFilename().equals(recipe.getMainImage())) {
                String resultFileName = s3Services.uploadFile(file);
                recipe.setMainImage(resultFileName);
            }
        } else if (StringHelper.isEmpty(fileName)) {
            // Если имя файла отсутствует, значит файл удален
            recipe.setMainImage("");
        }

        recipeRepository.save(recipe);

        setupRedirectionUrl(request);

        return "redirect:/recipe/" + recipe.getId();
    }

    @GetMapping("/delete")
    public String getDeleteRecipe(@RequestParam long id,
                                  HttpServletRequest request) {
        Recipe recipe = recipeRepository.findById(id).orElse(new Recipe());
        if (recipe.getId() != 0) {
            recipeRepository.delete(recipe);
        }

        HttpSession session = request.getSession();
        if (session != null) {
            String redirectUrl = (String) session.getAttribute("url_prior_login");
            if (StringHelper.isNotEmpty(redirectUrl)) {
                session.removeAttribute("url_prior_login");
                return "redirect:" + redirectUrl;
            }
        }

        return "redirect:/recipe/all";
    }

    @GetMapping("/publish")
    public String getPublishRecipe(@RequestParam long id) {
        Recipe recipe = recipeRepository.findById(id).orElse(new Recipe());
        if (recipe.getName() != null) {
            recipe.setToPublication(true);
            recipe.setPublicationDateTime(LocalDateTime.now());
            recipeRepository.save(recipe);
        }

        return "redirect:/recipe/" + id;
    }

    @GetMapping("/unpublish")
    public String getUnpublishRecipe(@RequestParam long id) {
        Recipe recipe = recipeRepository.findById(id).orElse(new Recipe());
        if (recipe.getName() != null) {
            recipe.setToPublication(false);
            recipeRepository.save(recipe);
        }

        return "redirect:/recipe/" + id;
    }
}

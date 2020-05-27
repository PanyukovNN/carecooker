package com.zylex.carecooker.controller;

import com.zylex.carecooker.model.Category;
import com.zylex.carecooker.model.Recipe;
import com.zylex.carecooker.model.Section;
import com.zylex.carecooker.repository.CategoryRepository;
import com.zylex.carecooker.repository.RecipeRepository;
import com.zylex.carecooker.repository.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static com.zylex.carecooker.controller.MainController.PAGE_SIZE;

@Controller
@RequestMapping("/section")
public class SectionController {

    private final CategoryRepository categoryRepository;

    private final SectionRepository sectionRepository;

    private final RecipeRepository recipeRepository;

    @Autowired
    public SectionController(CategoryRepository categoryRepository,
                             SectionRepository sectionRepository,
                             RecipeRepository recipeRepository) {
        this.categoryRepository = categoryRepository;
        this.sectionRepository = sectionRepository;
        this.recipeRepository = recipeRepository;
    }

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/{id}")
    public String getSection(
            @PathVariable long id,
            @PageableDefault(sort = { "id" }, direction = Sort.Direction.DESC, size = PAGE_SIZE) Pageable pageable,
            Model model) {
        Section section = sectionRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
        Page<Recipe> recipes = recipeRepository.findByCategoriesContaining(section.getCategory(), pageable);

        model.addAttribute("recipes", recipes);
        model.addAttribute("section", section);
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("url", "/section/" + section.getId() + "?");

        return "section";
    }

    @GetMapping("/list")
    public String getSections(Model model) {
        List<Section> sections = sectionRepository.findAll();
        sections.sort(Comparator.comparing(Section::getPosition).thenComparing(Section::getId));

        model.addAttribute("sections", sections);

        return "sectionEditList";
    }

    @GetMapping("/add")
    public String getSaveSection(Model model) {
        List<Category> categories = categoryRepository.findAll();
        categories.sort(Comparator.comparing(Category::getId));

        model.addAttribute("categories", categories);

        return "sectionEdit";
    }

    @PostMapping("/add")
    public String postSaveSection(
            @RequestParam("file") MultipartFile file,
            @RequestParam String category,
            @RequestParam String name,
            @RequestParam int position) throws IOException {
        Category sectionCategory = categoryRepository.findByName(category);
        Section section = new Section(name, sectionCategory, position);

        if (file != null && !StringUtils.isEmpty(file.getOriginalFilename())) {
            String resultFileName = uploadFile(file);
            section.setImage(resultFileName);
        }

        sectionRepository.save(section);

        return "redirect:/section/list";
    }

    @GetMapping("/edit")
    public String getEditSection(
            @RequestParam long id,
            Model model) {
        Section section = sectionRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
        List<Category> categories = categoryRepository.findAll();
        categories.sort(Comparator.comparing(Category::getId));

        model.addAttribute("section", section);
        model.addAttribute("categories" ,categories);

        return "sectionEdit";
    }

    @PostMapping("/edit")
    public String postEditSection(
            @RequestParam long id,
            @RequestParam("file") MultipartFile file,
            @RequestParam String category,
            @RequestParam String name,
            @RequestParam int position) throws IOException {
        Section section = sectionRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
        section.setName(name);
        section.setCategory(categoryRepository.findByName(category));
        section.setPosition(position);

        if (file != null && !StringUtils.isEmpty(file.getOriginalFilename())) {
            String resultFileName = uploadFile(file);
            section.setImage(resultFileName);
        }

        sectionRepository.save(section);

        return "redirect:/section/list";
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
}

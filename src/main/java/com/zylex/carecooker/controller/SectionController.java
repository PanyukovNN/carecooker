package com.zylex.carecooker.controller;

import com.zylex.carecooker.model.Category;
import com.zylex.carecooker.model.Recipe;
import com.zylex.carecooker.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/section")
public class SectionController {

    private final CategoryRepository categoryRepository;

    @Autowired
    public SectionController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/list")
    public String getSections(Model model) {
        List<Category> sectionCategories = categoryRepository.findBySectionNameNotNull();
        sectionCategories.sort(Comparator.comparing(Category::getSectionOrder).thenComparing(Category::getId));

        model.addAttribute("sectionCategories", sectionCategories);

        return "sectionList";
    }

    @GetMapping("/add")
    public String getSaveSection(Model model) {
        List<Category> categories = categoryRepository.findAll();
        categories.sort(Comparator.comparing(Category::getId));

        model.addAttribute("categories" ,categories);

        return "sectionEdit";
    }

    @PostMapping("/add")
    public String postSaveSection(
            @RequestParam("file") MultipartFile file,
            @RequestParam("category") String sectionCategory,
            @RequestParam String name,
            @RequestParam int order) throws IOException {
        Category category = new Category(name, sectionCategory, order);

        if (file != null && !StringUtils.isEmpty(file.getOriginalFilename())) {
            String resultFileName = uploadFile(file);
            category.setSectionImage(resultFileName);
        }

        categoryRepository.save(category);

        return "redirect:/section/list";
    }

    @GetMapping("/edit")
    public String getEditSection(
            @RequestParam long id,
            Model model) {
        Category sectionCategory = categoryRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
        List<Category> categories = categoryRepository.findAll();
        categories.sort(Comparator.comparing(Category::getId));

        model.addAttribute("categories" ,categories);
        model.addAttribute("sectionCategory", sectionCategory);

        return "sectionEdit";
    }

    @PostMapping("/edit")
    public String postEditSection(
            @RequestParam long id,
            @RequestParam("file") MultipartFile file,
            @RequestParam("category") String sectionCategory,
            @RequestParam String name,
            @RequestParam int order) throws IOException {
        Category category = categoryRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        category.setName(name);
        category.setSectionName(sectionCategory);
        category.setSectionOrder(order);

        if (file != null && !StringUtils.isEmpty(file.getOriginalFilename())) {
            String resultFileName = uploadFile(file);
            category.setSectionImage(resultFileName);
        }

        categoryRepository.save(category);

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

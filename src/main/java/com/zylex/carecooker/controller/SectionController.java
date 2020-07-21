package com.zylex.carecooker.controller;

import com.zylex.carecooker.model.Recipe;
import com.zylex.carecooker.model.Section;
import com.zylex.carecooker.model.SectionType;
import com.zylex.carecooker.model.dto.GreetingDto;
import com.zylex.carecooker.model.dto.SectionDto;
import com.zylex.carecooker.repository.RecipeRepository;
import com.zylex.carecooker.repository.SectionRepository;
import com.zylex.carecooker.service.S3Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/section")
public class SectionController {

    private final SectionRepository sectionRepository;

    private final RecipeRepository recipeRepository;

    private final S3Services s3Services;

    @Autowired
    public SectionController(SectionRepository sectionRepository,
                             RecipeRepository recipeRepository,
                             S3Services s3Services) {
        this.sectionRepository = sectionRepository;
        this.recipeRepository = recipeRepository;
        this.s3Services = s3Services;
    }

    @GetMapping(value = "/all", produces = "text/html")
    public String allSections(Model model) {
        List<Section> sections = sectionRepository.findAll();
        sections.sort(Comparator.comparing(Section::getPosition).thenComparing(Section::getId));

        model.addAttribute("greetingDto", new GreetingDto("Разделы", "Выберите интересующий вас раздел."));
        model.addAttribute("sections", sections);
        model.addAttribute("url", "/");

        return "sectionAll";
    }

    @GetMapping("/list")
    public String getSections(Model model) {
        List<Section> sections = sectionRepository.findAll();
        sections.sort(Comparator.comparing(Section::getPosition).thenComparing(Section::getId));

        model.addAttribute("sections", sections);

        return "sectionEditList";
    }

    @ResponseBody
    @GetMapping(value = "/all", produces = "application/json")
    public List<SectionDto> getSectionsDto() {
        List<Section> sections = sectionRepository.findAll();
        sections.sort(Comparator.comparing(Section::getPosition));

        return sections.stream()
                .map(SectionDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/add")
    public String getSaveSection() {
        return "sectionEdit";
    }

    @PostMapping("/add")
    public String postSaveSection(
            @RequestParam("file") MultipartFile file,
            @RequestParam String name,
            @RequestParam int position) throws IOException {
        Section section = new Section(name, position, SectionType.RECIPE);

        if (file != null && !StringUtils.isEmpty(file.getOriginalFilename())) {
            String resultFileName = s3Services.uploadFile(file);
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

        model.addAttribute("section", section);

        return "sectionEdit";
    }

    @PostMapping("/edit")
    public String postEditSection(
            @RequestParam long id,
            @RequestParam("file") MultipartFile file,
            @RequestParam String name,
            @RequestParam int position) throws IOException {
        Section section = sectionRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
        section.setName(name);
        section.setPosition(position);

        if (file != null && !StringUtils.isEmpty(file.getOriginalFilename())) {
            String resultFileName = s3Services.uploadFile(file);
            section.setImage(resultFileName);
        }

        sectionRepository.save(section);

        return "redirect:/section/list";
    }

    @GetMapping("/delete")
    public String getDeleteSection(@RequestParam long id) {
        Section section = sectionRepository.findById(id).orElseThrow(IllegalArgumentException::new);

            List<Recipe> recipes = recipeRepository.findBySectionsContaining(section);
        for (Recipe recipe : recipes) {
            List<Section> sections = recipe.getSections();
            sections.remove(section);
            recipeRepository.save(recipe);
        }

        sectionRepository.delete(section);

        return "redirect:/section/list";
    }
}

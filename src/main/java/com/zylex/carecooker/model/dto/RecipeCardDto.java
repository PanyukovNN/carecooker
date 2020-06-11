package com.zylex.carecooker.model.dto;

import com.zylex.carecooker.model.Recipe;

import java.time.LocalTime;

public class RecipeCardDto {

    private long id;

    private String name;

    private String mainImage;

    private LocalTime cookTime;

    private int serving;

    private String complexity;

    public RecipeCardDto(Recipe recipe) {
        this.id = recipe.getId();
        this.name = recipe.getName();
        this.mainImage = recipe.getMainImage();
        this.cookTime = recipe.getCookTime();
        this.serving = recipe.getServing();
        this.complexity = recipe.getComplexity();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMainImage() {
        return mainImage;
    }

    public LocalTime getCookTime() {
        return cookTime;
    }

    public int getServing() {
        return serving;
    }

    public String getComplexity() {
        return complexity;
    }
}

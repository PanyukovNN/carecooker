package com.zylex.carecooker.model.dto;

public class GreetingDto {

    private String title;

    private String description;

    public GreetingDto(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}

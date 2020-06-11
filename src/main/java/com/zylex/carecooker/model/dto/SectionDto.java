package com.zylex.carecooker.model.dto;

import com.zylex.carecooker.model.Section;

public class SectionDto {

    private long id;

    private String name;

    public SectionDto(Section section) {
        this.id = section.getId();
        this.name = section.getName();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

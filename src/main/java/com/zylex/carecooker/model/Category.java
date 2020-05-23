package com.zylex.carecooker.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String sectionName;

    private int sectionOrder;

    private String sectionImage;

    public Category() {
    }

    public Category(String name, String sectionName, int sectionOrder) {
        this.name = name;
        this.sectionName = sectionName;
        this.sectionOrder = sectionOrder;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public int getSectionOrder() {
        return sectionOrder;
    }

    public void setSectionOrder(int sectionOrder) {
        this.sectionOrder = sectionOrder;
    }

    public String getSectionImage() {
        return sectionImage;
    }

    public void setSectionImage(String sectionImage) {
        this.sectionImage = sectionImage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(name, category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

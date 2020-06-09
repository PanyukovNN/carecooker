package com.zylex.carecooker.model;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String description;

    private String mainImage;

    private String cookTime;

    private String serving;

    private String complexity;

    @ElementCollection
    private List<String> ingredients;

    private String method;

    @ManyToMany
    private List<Section> sections;

//    @ManyToOne
//    private Dish dish;

    @ManyToMany
    private List<Category> categories;

    private boolean toPublication;

    public Recipe() {
    }

    public Recipe(String name,
                  String description,
                  String cookTime,
                  String serving,
                  String complexity,
                  List<String> ingredients,
                  String method,
//                  Dish dish,
                  List<Section> sections,
                  List<Category> categories,
                  boolean toPublication
    ) {
        this.name = name;
        this.description = description;
        this.cookTime = cookTime;
        this.serving = serving;
        this.complexity = complexity;
        this.ingredients = ingredients;
        this.method = method;
//        this.dish = dish;
        this.sections = sections;
        this.categories = categories;
        this.toPublication = toPublication;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public String getShortDescription() {
        return description.length() > 150
                ? description.substring(0, 150) + "..."
                : description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    public String getCookTime() {
        return cookTime;
    }

    public void setCookTime(String cookTime) {
        this.cookTime = cookTime;
    }

    public String getServing() {
        return serving;
    }

    public void setServing(String serving) {
        this.serving = serving;
    }

    public String getComplexity() {
        return complexity;
    }

    public void setComplexity(String complexity) {
        this.complexity = complexity;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public String getMethod() {
        return method;
    }

    public List<String> getMethodSteps() {
        return Arrays.asList(method.split("\n"));
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }
//
//    public Dish getDish() {
//        return dish;
//    }
//
//    public void setDish(Dish dish) {
//        this.dish = dish;
//    }

    public List<Category> getCategories() {
        return categories;
    }

    public boolean isToPublication() {
        return toPublication;
    }

    public void setToPublication(boolean toPublication) {
        this.toPublication = toPublication;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return Objects.equals(name, recipe.name) &&
                Objects.equals(description, recipe.description) &&
                Objects.equals(ingredients, recipe.ingredients) &&
                Objects.equals(method, recipe.method);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, ingredients, method);
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", mainImage='" + mainImage + '\'' +
                ", cookTime='" + cookTime + '\'' +
                ", serving='" + serving + '\'' +
                ", complexity='" + complexity + '\'' +
                ", ingredients=" + ingredients +
                ", method='" + method + '\'' +
//                ", section=" + section +
//                ", dish=" + dish +
                ", categories=" + categories +
//                ", toPublication=" + toPublication +
                '}';
    }
}

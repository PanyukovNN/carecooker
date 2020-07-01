package com.zylex.carecooker.model;

import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

    private LocalTime cookTime;

    private int serving;

    private String complexity;

    @ElementCollection
    private List<String> ingredients;

    @ElementCollection
    private List<String> method;

    @ManyToMany
    private List<Section> sections;

    @ManyToOne
    private User author;

    @ManyToMany
    private List<Dish> dishes;

    private boolean toPublication;

    private LocalDateTime publicationDateTime;

    private int views;

    public Recipe() {
    }

    public Recipe(String name,
                  String description,
                  LocalTime cookTime,
                  int serving,
                  String complexity,
                  List<String> ingredients,
                  List<String> method,
                  List<Section> sections,
                  List<Dish> dishes,
                  User author,
                  boolean toPublication
    ) {
        this.name = name;
        this.description = description;
        this.cookTime = cookTime;
        this.serving = serving;
        this.complexity = complexity;
        this.ingredients = ingredients;
        this.method = method;
        this.sections = sections;
        this.dishes = dishes;
        this.author = author;
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

    public LocalTime getCookTime() {
        return cookTime;
    }

    public void setCookTime(LocalTime cookTime) {
        this.cookTime = cookTime;
    }

    public int getServing() {
        return serving;
    }

    public void setServing(int serving) {
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

    public List<String> getMethod() {
        return method;
    }


    public void setMethod(List<String> method) {
        this.method = method;
    }

    //TODO do not forget to remove
    public Section getSection() {
        if (sections != null && !sections.isEmpty()) {
            return sections.get(0);
        } else {
            return null;
        }
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    public UserDetails getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    //TODO do not forget to remove
    public Dish getDish() {
        if (dishes != null && !dishes.isEmpty()) {
            return dishes.get(0);
        } else {
            return null;
        }
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

    public boolean isToPublication() {
        return toPublication;
    }

    public void setToPublication(boolean toPublication) {
        this.toPublication = toPublication;
    }

    public LocalDateTime getPublicationDateTime() {
        return publicationDateTime;
    }

    public void setPublicationDateTime(LocalDateTime publicationDateTime) {
        this.publicationDateTime = publicationDateTime;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public void incrementViews() {
        this.views++;
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
                ", cookTime=" + cookTime +
                ", serving=" + serving +
                ", complexity='" + complexity + '\'' +
                ", ingredients=" + ingredients +
                ", method='" + method + '\'' +
                ", sections=" + sections +
                ", author=" + author +
                ", dishes=" + dishes +
                ", toPublication=" + toPublication +
                ", publicationDateTime=" + publicationDateTime +
                ", views=" + views +
                '}';
    }
}

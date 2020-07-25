package com.zylex.carecooker.repository;

import com.zylex.carecooker.model.Dish;
import com.zylex.carecooker.model.Recipe;
import com.zylex.carecooker.model.Section;
import com.zylex.carecooker.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    Page<Recipe> findAllByToPublicationIsTrue(Pageable pageable);

    Page<Recipe> findByToPublicationIsFalse(Pageable pageable);

    @Query("FROM Recipe r " +
            "WHERE r.name = '' or r.name IS NULL " +
            "or r.mainImage = '' or r.mainImage IS NULL " +
            "or r.cookTime = '00:00:00' " +
            "or r.serving = 0 " +
            "or r.ingredientAmounts.size = 0 " +
            "or r.method.size = 0 " +
            "or r.sections.size = 0 " +
            "or r.dishes.size = 0")
    Page<Recipe> findIncomplete(Pageable pageable);

    Page<Recipe> findByNameContainingIgnoreCase(String namePart, Pageable pageable);

    Page<Recipe> findBySectionsContainingAndToPublicationIsTrue(Section section, Pageable pageable);

    Page<Recipe> findBySectionsContainingAndDishesContainingAndToPublicationIsTrue(Section section, Dish dish, Pageable pageable);

    List<Recipe> findBySectionsContaining(Section section);

    List<Recipe> findByDishesContaining(Dish dish);

    List<Recipe> findTop7BySectionsContaining(Section section);

    @Query("SELECT COUNT(r) FROM Recipe r WHERE r.author=?1 and (r.source IS NULL OR r.source = '')")
    Long countByAuthorAndSourceIsNull(User author);
}

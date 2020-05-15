package com.zylex.carecooker.repository;

import com.zylex.carecooker.model.Category;
import com.zylex.carecooker.model.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    Page<Recipe> findAll(Pageable pageable);

    Page<Recipe> findByCategoriesContaining(Category category, Pageable pageable);

    Page<Recipe> findByNameContainingIgnoreCase(String namePart, Pageable pageable);
}

package com.zylex.carecooker.repository;

import com.zylex.carecooker.model.Recipe;
import com.zylex.carecooker.model.Section;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    Page<Recipe> findAllByToPublicationIsTrue(Pageable pageable);

    Page<Recipe> findBySectionsIsNull(Pageable pageable);

    Page<Recipe> findByToPublicationIsFalse(Pageable pageable);

    Page<Recipe> findByNameContainingIgnoreCase(String namePart, Pageable pageable);

    Page<Recipe> findBySectionsContainingAndToPublicationIsTrue(Section section, Pageable pageable);

    List<Recipe> findBySectionsContaining(Section section);

    List<Recipe> findTop6BySectionsContaining(Section section);
}

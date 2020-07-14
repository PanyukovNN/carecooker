package com.zylex.carecooker.repository;

import com.zylex.carecooker.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    Ingredient findByName(String name);
}

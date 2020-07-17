package com.zylex.carecooker.repository;

import com.zylex.carecooker.model.IngredientAmount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientAmountRepository extends JpaRepository<IngredientAmount, Long> {
}

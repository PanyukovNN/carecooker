package com.zylex.carecooker.repository;

import com.zylex.carecooker.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DishRepository extends JpaRepository<Dish, Long> {

    Dish findByName(String name);
}

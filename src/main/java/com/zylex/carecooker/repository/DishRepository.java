package com.zylex.carecooker.repository;

import com.zylex.carecooker.model.Dish;
import com.zylex.carecooker.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DishRepository extends JpaRepository<Dish, Long> {

    Dish findByName(String name);

    List<Dish> findBySection(Section section);
}

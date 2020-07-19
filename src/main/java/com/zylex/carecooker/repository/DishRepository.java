package com.zylex.carecooker.repository;

import com.zylex.carecooker.model.Dish;
import com.zylex.carecooker.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DishRepository extends JpaRepository<Dish, Long> {

    Dish findByName(String name);

    List<Dish> findBySection(Section section);

    @Query(value = "SELECT DISTINCT dish.id, dish.name, dish.title_description, dish.section_id FROM dish " +
            "INNER JOIN recipe_dishes rd " +
            "ON dish.id = rd.dishes_id " +
            "WHERE dish.section_id = ?1",
        nativeQuery = true)
    List<Dish> findWhereRecipesExists(long sectionId);
}

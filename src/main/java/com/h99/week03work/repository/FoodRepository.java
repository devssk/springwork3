package com.h99.week03work.repository;

import com.h99.week03work.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FoodRepository extends JpaRepository<Food, Long> {
    Optional<Food> findByRestaurantIdAndName(Long restaurantId, String name);
    List<Food> findAllByRestaurantId(Long restaurantId);
}

package com.recipeai.ingredientservice.repository;

import com.recipeai.ingredientservice.entity.PantryItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PantryItemRepository extends JpaRepository<PantryItem, Long> {

    List<PantryItem> findByUserId(Long userId);

    boolean existsByIngredientId(Long ingredientId);
}

package com.recipeai.ingredientservice.repository;

import com.recipeai.ingredientservice.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    boolean existsByNom(String nom);
}

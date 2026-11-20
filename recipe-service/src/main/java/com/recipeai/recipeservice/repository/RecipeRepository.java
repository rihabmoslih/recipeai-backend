package com.recipeai.recipeservice.repository;

import com.recipeai.recipeservice.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    List<Recipe> findByUserIdOrderByScorePourcentageDesc(Long userId);
}

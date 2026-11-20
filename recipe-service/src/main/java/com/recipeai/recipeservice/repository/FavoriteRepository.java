package com.recipeai.recipeservice.repository;

import com.recipeai.recipeservice.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    List<Favorite> findByUserId(Long userId);

    boolean existsByUserIdAndRecipeId(Long userId, Long recipeId);

    void deleteByRecipeId(Long recipeId);
}

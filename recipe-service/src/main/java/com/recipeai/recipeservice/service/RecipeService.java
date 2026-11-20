package com.recipeai.recipeservice.service;

import com.recipeai.recipeservice.dto.RecipeRequestDTO;
import com.recipeai.recipeservice.dto.RecipeResponseDTO;

import java.util.List;

public interface RecipeService {

    RecipeResponseDTO create(RecipeRequestDTO dto);

    RecipeResponseDTO findById(Long id);

    List<RecipeResponseDTO> findAll();

    List<RecipeResponseDTO> findByUserId(Long userId);

    RecipeResponseDTO update(Long id, RecipeRequestDTO dto);

    void delete(Long id);
}

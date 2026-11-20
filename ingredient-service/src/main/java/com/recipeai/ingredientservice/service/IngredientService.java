package com.recipeai.ingredientservice.service;

import com.recipeai.ingredientservice.dto.IngredientRequestDTO;
import com.recipeai.ingredientservice.dto.IngredientResponseDTO;

import java.util.List;

public interface IngredientService {

    IngredientResponseDTO create(IngredientRequestDTO dto);

    IngredientResponseDTO findById(Long id);

    List<IngredientResponseDTO> findAll();

    IngredientResponseDTO update(Long id, IngredientRequestDTO dto);

    void delete(Long id);
}

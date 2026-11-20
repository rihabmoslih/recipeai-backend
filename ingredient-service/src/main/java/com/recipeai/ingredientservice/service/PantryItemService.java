package com.recipeai.ingredientservice.service;

import com.recipeai.ingredientservice.dto.PantryItemRequestDTO;
import com.recipeai.ingredientservice.dto.PantryItemResponseDTO;

import java.util.List;

public interface PantryItemService {

    PantryItemResponseDTO create(PantryItemRequestDTO dto);

    PantryItemResponseDTO findById(Long id);

    List<PantryItemResponseDTO> findByUserId(Long userId);

    PantryItemResponseDTO update(Long id, PantryItemRequestDTO dto);

    void delete(Long id);
}

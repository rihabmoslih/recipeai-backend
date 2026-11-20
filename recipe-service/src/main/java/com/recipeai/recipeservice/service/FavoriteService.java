package com.recipeai.recipeservice.service;

import com.recipeai.recipeservice.dto.FavoriteRequestDTO;
import com.recipeai.recipeservice.dto.FavoriteResponseDTO;

import java.util.List;

public interface FavoriteService {

    FavoriteResponseDTO add(FavoriteRequestDTO dto);

    List<FavoriteResponseDTO> findByUserId(Long userId);

    void delete(Long id);
}

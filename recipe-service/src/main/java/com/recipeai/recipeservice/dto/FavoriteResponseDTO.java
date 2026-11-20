package com.recipeai.recipeservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FavoriteResponseDTO {

    private Long id;
    private Long userId;
    private Long recipeId;
    private String recipeTitre;
}

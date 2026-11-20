package com.recipeai.recipeservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FavoriteRequestDTO {

    @NotNull(message = "userId est obligatoire")
    private Long userId;

    @NotNull(message = "recipeId est obligatoire")
    private Long recipeId;
}

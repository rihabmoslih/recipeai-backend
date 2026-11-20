package com.recipeai.recipeservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecipeIngredientDTO {

    @NotNull(message = "ingredientId est obligatoire")
    private Long ingredientId;

    @NotNull(message = "La quantite est obligatoire")
    @Positive(message = "La quantite doit etre positive")
    private Double quantite;

    private boolean disponible;
}

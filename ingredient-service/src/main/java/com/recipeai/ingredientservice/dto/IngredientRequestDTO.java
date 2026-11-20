package com.recipeai.ingredientservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IngredientRequestDTO {

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    private String categorie;

    private String unite;
}

package com.recipeai.ingredientservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IngredientResponseDTO {

    private Long id;
    private String nom;
    private String categorie;
    private String unite;
}

package com.recipeai.recipeservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IngredientDTO {
    private Long id;
    private String nom;
    private String categorie;
    private String unite;
}

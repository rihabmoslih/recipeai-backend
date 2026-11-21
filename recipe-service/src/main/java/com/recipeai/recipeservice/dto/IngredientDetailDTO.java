package com.recipeai.recipeservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IngredientDetailDTO {
    private Long ingredientId;
    private String nom;
    private String categorie;
    private String unite;
    private Double quantite;
    private boolean disponible;
}

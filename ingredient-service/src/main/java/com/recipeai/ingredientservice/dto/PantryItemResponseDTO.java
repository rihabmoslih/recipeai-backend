package com.recipeai.ingredientservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PantryItemResponseDTO {

    private Long id;
    private Long userId;
    private Long ingredientId;
    private String ingredientNom;
    private Double quantite;
    private LocalDate dateExpiration;
}

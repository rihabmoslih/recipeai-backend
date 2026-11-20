package com.recipeai.ingredientservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PantryItemRequestDTO {

    @NotNull(message = "userId est obligatoire")
    private Long userId;

    @NotNull(message = "ingredientId est obligatoire")
    private Long ingredientId;

    @NotNull(message = "La quantite est obligatoire")
    @Positive(message = "La quantite doit etre positive")
    private Double quantite;

    private LocalDate dateExpiration;
}

package com.recipeai.recipeservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "recipe_ingredients")
@Getter
@Setter
@NoArgsConstructor
public class RecipeIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Reference distante : l'ingredient est dans ingredient-service (autre base).
    @Column(nullable = false)
    private Long ingredientId;

    private Double quantite;

    // true si l'utilisateur possede deja cet ingredient (sert au calcul du score)
    private boolean disponible;
}

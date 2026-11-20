package com.recipeai.ingredientservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "pantry_items")
@Getter
@Setter
@NoArgsConstructor
public class PantryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Reference distante : l'utilisateur est dans user-service (autre base).
    // Simple identifiant, pas de relation JPA ni de cle etrangere.
    @Column(nullable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    @Column(nullable = false)
    private Double quantite;

    private LocalDate dateExpiration;
}

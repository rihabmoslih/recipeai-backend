package com.recipeai.recipeservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "recipes")
@Getter
@Setter
@NoArgsConstructor
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titre;

    @Column(length = 2000)
    private String description;

    private Integer duree;

    private Integer scorePourcentage;

    // Reference distante : l'utilisateur est dans user-service (autre base).
    // Simple identifiant, pas de relation JPA ni de cle etrangere.
    @Column(nullable = false)
    private Long userId;

    private LocalDateTime dateCreation;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "recipe_id")
    private List<RecipeIngredient> ingredients = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "recipe_id")
    @OrderBy("ordre ASC")
    private List<Step> steps = new ArrayList<>();
}

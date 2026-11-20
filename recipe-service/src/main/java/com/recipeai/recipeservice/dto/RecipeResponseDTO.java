package com.recipeai.recipeservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class RecipeResponseDTO {

    private Long id;
    private String titre;
    private String description;
    private Integer duree;
    private Integer scorePourcentage;
    private Long userId;
    private LocalDateTime dateCreation;
    private List<RecipeIngredientDTO> ingredients = new ArrayList<>();
    private List<StepDTO> steps = new ArrayList<>();
}

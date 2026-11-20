package com.recipeai.recipeservice.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class RecipeRequestDTO {

    @NotBlank(message = "Le titre est obligatoire")
    private String titre;

    private String description;

    @Positive(message = "La duree doit etre positive")
    private Integer duree;

    @NotNull(message = "userId est obligatoire")
    private Long userId;

    @NotEmpty(message = "Au moins un ingredient est obligatoire")
    @Valid
    private List<RecipeIngredientDTO> ingredients = new ArrayList<>();

    @Valid
    private List<StepDTO> steps = new ArrayList<>();
}

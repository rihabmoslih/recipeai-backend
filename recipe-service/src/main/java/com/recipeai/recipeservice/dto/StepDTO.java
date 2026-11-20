package com.recipeai.recipeservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StepDTO {

    @NotNull(message = "L'ordre est obligatoire")
    @Positive(message = "L'ordre doit etre positif")
    private Integer ordre;

    @NotBlank(message = "L'instruction est obligatoire")
    private String instruction;
}

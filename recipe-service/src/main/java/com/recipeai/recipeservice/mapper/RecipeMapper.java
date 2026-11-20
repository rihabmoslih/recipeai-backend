package com.recipeai.recipeservice.mapper;

import com.recipeai.recipeservice.dto.RecipeIngredientDTO;
import com.recipeai.recipeservice.dto.RecipeRequestDTO;
import com.recipeai.recipeservice.dto.RecipeResponseDTO;
import com.recipeai.recipeservice.dto.StepDTO;
import com.recipeai.recipeservice.entity.Recipe;
import com.recipeai.recipeservice.entity.RecipeIngredient;
import com.recipeai.recipeservice.entity.Step;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RecipeMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "scorePourcentage", ignore = true)
    @Mapping(target = "dateCreation", ignore = true)
    Recipe toEntity(RecipeRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    RecipeIngredient toIngredientEntity(RecipeIngredientDTO dto);

    @Mapping(target = "id", ignore = true)
    Step toStepEntity(StepDTO dto);

    RecipeResponseDTO toDto(Recipe recipe);

    RecipeIngredientDTO toIngredientDto(RecipeIngredient ingredient);

    StepDTO toStepDto(Step step);
}

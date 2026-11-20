package com.recipeai.ingredientservice.mapper;

import com.recipeai.ingredientservice.dto.IngredientRequestDTO;
import com.recipeai.ingredientservice.dto.IngredientResponseDTO;
import com.recipeai.ingredientservice.entity.Ingredient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface IngredientMapper {

    @Mapping(target = "id", ignore = true)
    Ingredient toEntity(IngredientRequestDTO dto);

    IngredientResponseDTO toDto(Ingredient ingredient);

    @Mapping(target = "id", ignore = true)
    void updateEntity(IngredientRequestDTO dto, @MappingTarget Ingredient ingredient);
}

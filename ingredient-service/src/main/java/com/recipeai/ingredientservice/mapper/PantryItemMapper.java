package com.recipeai.ingredientservice.mapper;

import com.recipeai.ingredientservice.dto.PantryItemRequestDTO;
import com.recipeai.ingredientservice.dto.PantryItemResponseDTO;
import com.recipeai.ingredientservice.entity.PantryItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PantryItemMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ingredient", ignore = true)
    PantryItem toEntity(PantryItemRequestDTO dto);

    @Mapping(source = "ingredient.id", target = "ingredientId")
    @Mapping(source = "ingredient.nom", target = "ingredientNom")
    PantryItemResponseDTO toDto(PantryItem item);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ingredient", ignore = true)
    void updateEntity(PantryItemRequestDTO dto, @MappingTarget PantryItem item);
}

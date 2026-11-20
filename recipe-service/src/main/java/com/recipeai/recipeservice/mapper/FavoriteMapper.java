package com.recipeai.recipeservice.mapper;

import com.recipeai.recipeservice.dto.FavoriteResponseDTO;
import com.recipeai.recipeservice.entity.Favorite;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FavoriteMapper {

    @Mapping(source = "recipe.id", target = "recipeId")
    @Mapping(source = "recipe.titre", target = "recipeTitre")
    FavoriteResponseDTO toDto(Favorite favorite);
}

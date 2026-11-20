package com.recipeai.userservice.mapper;

import com.recipeai.userservice.dto.UserRequestDTO;
import com.recipeai.userservice.dto.UserResponseDTO;
import com.recipeai.userservice.entity.Allergie;
import com.recipeai.userservice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    User toEntity(UserRequestDTO dto);

    UserResponseDTO toDto(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    void updateEntity(UserRequestDTO dto, @MappingTarget User user);

    default Allergie toAllergie(String libelle) {
        Allergie allergie = new Allergie();
        allergie.setLibelle(libelle);
        return allergie;
    }

    default String toLibelle(Allergie allergie) {
        return allergie.getLibelle();
    }
}

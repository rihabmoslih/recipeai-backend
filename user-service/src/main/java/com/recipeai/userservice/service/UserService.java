package com.recipeai.userservice.service;

import com.recipeai.userservice.dto.UserRequestDTO;
import com.recipeai.userservice.dto.UserResponseDTO;

import java.util.List;

public interface UserService {

    UserResponseDTO create(UserRequestDTO dto);

    UserResponseDTO findById(Long id);

    List<UserResponseDTO> findAll();

    UserResponseDTO update(Long id, UserRequestDTO dto);

    void delete(Long id);
}

package com.recipeai.userservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserResponseDTO {

    private Long id;
    private String nom;
    private String email;
    private String role;
    private String regime;
    private List<String> allergies = new ArrayList<>();
}

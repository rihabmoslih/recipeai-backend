package com.recipeai.recipeservice.controller;

import com.recipeai.recipeservice.dto.RecipeDetailsDTO;
import com.recipeai.recipeservice.service.RecipeDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recipes")
@RequiredArgsConstructor
public class RecipeDetailsController {

    private final RecipeDetailsService detailsService;

    @GetMapping("/{id}/details")
    public RecipeDetailsDTO details(@PathVariable Long id) {
        return detailsService.getDetails(id);
    }
}

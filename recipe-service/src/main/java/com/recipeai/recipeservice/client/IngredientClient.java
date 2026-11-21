package com.recipeai.recipeservice.client;

import com.recipeai.recipeservice.dto.IngredientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "INGREDIENT-SERVICE")
public interface IngredientClient {

    @GetMapping("/ingredients/{id}")
    IngredientDTO findById(@PathVariable("id") Long id);
}

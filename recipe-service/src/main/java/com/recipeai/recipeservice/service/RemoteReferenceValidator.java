package com.recipeai.recipeservice.service;

import com.recipeai.recipeservice.client.IngredientClient;
import com.recipeai.recipeservice.client.UserClient;
import com.recipeai.recipeservice.dto.IngredientDTO;
import com.recipeai.recipeservice.dto.RecipeIngredientDTO;
import com.recipeai.recipeservice.dto.UserDTO;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RemoteReferenceValidator {

    private final UserClient userClient;
    private final IngredientClient ingredientClient;

    public UserDTO requireUser(Long userId) {
        try {
            return userClient.findById(userId);
        } catch (FeignException.NotFound e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur introuvable : " + userId);
        } catch (FeignException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "user-service indisponible");
        }
    }

    public IngredientDTO requireIngredient(Long ingredientId) {
        try {
            return ingredientClient.findById(ingredientId);
        } catch (FeignException.NotFound e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingredient introuvable : " + ingredientId);
        } catch (FeignException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "ingredient-service indisponible");
        }
    }

    public void validateIngredients(List<RecipeIngredientDTO> ingredients) {
        ingredients.stream()
                .map(RecipeIngredientDTO::getIngredientId)
                .distinct()
                .forEach(this::requireIngredient);
    }

    public void validateRecipe(Long userId, List<RecipeIngredientDTO> ingredients) {
        requireUser(userId);
        validateIngredients(ingredients);
    }
}

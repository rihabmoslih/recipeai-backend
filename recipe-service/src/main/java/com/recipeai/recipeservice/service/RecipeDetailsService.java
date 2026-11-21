package com.recipeai.recipeservice.service;

import com.recipeai.recipeservice.dto.IngredientDTO;
import com.recipeai.recipeservice.dto.IngredientDetailDTO;
import com.recipeai.recipeservice.dto.RecipeDetailsDTO;
import com.recipeai.recipeservice.dto.RecipeIngredientDTO;
import com.recipeai.recipeservice.dto.RecipeResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeDetailsService {

    private final RecipeService recipeService;
    private final RemoteReferenceValidator remote;

    public RecipeDetailsDTO getDetails(Long id) {
        RecipeResponseDTO recipe = recipeService.findById(id);

        RecipeDetailsDTO details = new RecipeDetailsDTO();
        details.setId(recipe.getId());
        details.setTitre(recipe.getTitre());
        details.setDescription(recipe.getDescription());
        details.setDuree(recipe.getDuree());
        details.setScorePourcentage(recipe.getScorePourcentage());
        details.setDateCreation(recipe.getDateCreation());
        details.setSteps(recipe.getSteps());
        details.setUser(remote.requireUser(recipe.getUserId()));

        List<IngredientDetailDTO> list = new ArrayList<>();
        for (RecipeIngredientDTO ri : recipe.getIngredients()) {
            IngredientDTO ing = remote.requireIngredient(ri.getIngredientId());
            IngredientDetailDTO d = new IngredientDetailDTO();
            d.setIngredientId(ing.getId());
            d.setNom(ing.getNom());
            d.setCategorie(ing.getCategorie());
            d.setUnite(ing.getUnite());
            d.setQuantite(ri.getQuantite());
            d.setDisponible(ri.isDisponible());
            list.add(d);
        }
        details.setIngredients(list);
        return details;
    }
}

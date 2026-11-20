package com.recipeai.recipeservice.service;

import com.recipeai.recipeservice.dto.RecipeRequestDTO;
import com.recipeai.recipeservice.dto.RecipeResponseDTO;
import com.recipeai.recipeservice.entity.Recipe;
import com.recipeai.recipeservice.entity.RecipeIngredient;
import com.recipeai.recipeservice.mapper.RecipeMapper;
import com.recipeai.recipeservice.repository.FavoriteRepository;
import com.recipeai.recipeservice.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final FavoriteRepository favoriteRepository;
    private final RecipeMapper recipeMapper;

    @Override
    public RecipeResponseDTO create(RecipeRequestDTO dto) {
        // Plus tard (V4) : verifier userId et ingredientId via OpenFeign.
        Recipe recipe = recipeMapper.toEntity(dto);
        recipe.setDateCreation(LocalDateTime.now());
        recipe.setScorePourcentage(computeScore(recipe));
        return recipeMapper.toDto(recipeRepository.save(recipe));
    }

    @Override
    @Transactional(readOnly = true)
    public RecipeResponseDTO findById(Long id) {
        return recipeMapper.toDto(getOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<RecipeResponseDTO> findAll() {
        return recipeRepository.findAll().stream().map(recipeMapper::toDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<RecipeResponseDTO> findByUserId(Long userId) {
        return recipeRepository.findByUserIdOrderByScorePourcentageDesc(userId)
                .stream().map(recipeMapper::toDto).toList();
    }

    @Override
    public RecipeResponseDTO update(Long id, RecipeRequestDTO dto) {
        Recipe recipe = getOrThrow(id);
        recipe.setTitre(dto.getTitre());
        recipe.setDescription(dto.getDescription());
        recipe.setDuree(dto.getDuree());
        recipe.getIngredients().clear();
        recipe.getIngredients().addAll(
                dto.getIngredients().stream().map(recipeMapper::toIngredientEntity).toList());
        recipe.getSteps().clear();
        recipe.getSteps().addAll(
                dto.getSteps().stream().map(recipeMapper::toStepEntity).toList());
        recipe.setScorePourcentage(computeScore(recipe));
        return recipeMapper.toDto(recipeRepository.save(recipe));
    }

    @Override
    public void delete(Long id) {
        getOrThrow(id);
        favoriteRepository.deleteByRecipeId(id);
        recipeRepository.deleteById(id);
    }

    // Score = pourcentage d'ingredients que l'utilisateur possede deja
    private int computeScore(Recipe recipe) {
        int total = recipe.getIngredients().size();
        if (total == 0) {
            return 0;
        }
        long disponibles = recipe.getIngredients().stream().filter(RecipeIngredient::isDisponible).count();
        return (int) Math.round(100.0 * disponibles / total);
    }

    private Recipe getOrThrow(Long id) {
        return recipeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Recette introuvable"));
    }
}

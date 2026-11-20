package com.recipeai.recipeservice.service;

import com.recipeai.recipeservice.dto.FavoriteRequestDTO;
import com.recipeai.recipeservice.dto.FavoriteResponseDTO;
import com.recipeai.recipeservice.entity.Favorite;
import com.recipeai.recipeservice.entity.Recipe;
import com.recipeai.recipeservice.mapper.FavoriteMapper;
import com.recipeai.recipeservice.repository.FavoriteRepository;
import com.recipeai.recipeservice.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final RecipeRepository recipeRepository;
    private final FavoriteMapper favoriteMapper;

    @Override
    public FavoriteResponseDTO add(FavoriteRequestDTO dto) {
        Recipe recipe = recipeRepository.findById(dto.getRecipeId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Recette introuvable"));
        if (favoriteRepository.existsByUserIdAndRecipeId(dto.getUserId(), dto.getRecipeId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Deja dans les favoris");
        }
        Favorite favorite = new Favorite();
        favorite.setUserId(dto.getUserId());
        favorite.setRecipe(recipe);
        return favoriteMapper.toDto(favoriteRepository.save(favorite));
    }

    @Override
    @Transactional(readOnly = true)
    public List<FavoriteResponseDTO> findByUserId(Long userId) {
        return favoriteRepository.findByUserId(userId).stream().map(favoriteMapper::toDto).toList();
    }

    @Override
    public void delete(Long id) {
        if (!favoriteRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Favori introuvable");
        }
        favoriteRepository.deleteById(id);
    }
}

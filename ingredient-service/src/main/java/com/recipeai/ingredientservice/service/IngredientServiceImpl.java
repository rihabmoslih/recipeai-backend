package com.recipeai.ingredientservice.service;

import com.recipeai.ingredientservice.dto.IngredientRequestDTO;
import com.recipeai.ingredientservice.dto.IngredientResponseDTO;
import com.recipeai.ingredientservice.entity.Ingredient;
import com.recipeai.ingredientservice.mapper.IngredientMapper;
import com.recipeai.ingredientservice.repository.IngredientRepository;
import com.recipeai.ingredientservice.repository.PantryItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;
    private final PantryItemRepository pantryItemRepository;
    private final IngredientMapper ingredientMapper;

    @Override
    public IngredientResponseDTO create(IngredientRequestDTO dto) {
        if (ingredientRepository.existsByNom(dto.getNom())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ingredient deja existant");
        }
        Ingredient ingredient = ingredientMapper.toEntity(dto);
        return ingredientMapper.toDto(ingredientRepository.save(ingredient));
    }

    @Override
    @Transactional(readOnly = true)
    public IngredientResponseDTO findById(Long id) {
        return ingredientMapper.toDto(getOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<IngredientResponseDTO> findAll() {
        return ingredientRepository.findAll().stream().map(ingredientMapper::toDto).toList();
    }

    @Override
    public IngredientResponseDTO update(Long id, IngredientRequestDTO dto) {
        Ingredient ingredient = getOrThrow(id);
        if (!ingredient.getNom().equals(dto.getNom()) && ingredientRepository.existsByNom(dto.getNom())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ingredient deja existant");
        }
        ingredientMapper.updateEntity(dto, ingredient);
        return ingredientMapper.toDto(ingredientRepository.save(ingredient));
    }

    @Override
    public void delete(Long id) {
        getOrThrow(id);
        if (pantryItemRepository.existsByIngredientId(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Ingredient utilise dans un garde-manger");
        }
        ingredientRepository.deleteById(id);
    }

    private Ingredient getOrThrow(Long id) {
        return ingredientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingredient introuvable"));
    }
}

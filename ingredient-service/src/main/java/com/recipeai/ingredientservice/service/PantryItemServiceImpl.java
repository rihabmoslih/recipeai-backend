package com.recipeai.ingredientservice.service;

import com.recipeai.ingredientservice.dto.PantryItemRequestDTO;
import com.recipeai.ingredientservice.dto.PantryItemResponseDTO;
import com.recipeai.ingredientservice.entity.Ingredient;
import com.recipeai.ingredientservice.entity.PantryItem;
import com.recipeai.ingredientservice.mapper.PantryItemMapper;
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
public class PantryItemServiceImpl implements PantryItemService {

    private final PantryItemRepository pantryItemRepository;
    private final IngredientRepository ingredientRepository;
    private final PantryItemMapper pantryItemMapper;
    private final RemoteUserValidator remoteUserValidator;

    @Override
    public PantryItemResponseDTO create(PantryItemRequestDTO dto) {
        remoteUserValidator.requireUser(dto.getUserId());
        PantryItem item = pantryItemMapper.toEntity(dto);
        item.setIngredient(getIngredientOrThrow(dto.getIngredientId()));
        return pantryItemMapper.toDto(pantryItemRepository.save(item));
    }

    @Override
    @Transactional(readOnly = true)
    public PantryItemResponseDTO findById(Long id) {
        return pantryItemMapper.toDto(getOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PantryItemResponseDTO> findByUserId(Long userId) {
        return pantryItemRepository.findByUserId(userId).stream().map(pantryItemMapper::toDto).toList();
    }

    @Override
    public PantryItemResponseDTO update(Long id, PantryItemRequestDTO dto) {
        PantryItem item = getOrThrow(id);
        remoteUserValidator.requireUser(dto.getUserId());
        pantryItemMapper.updateEntity(dto, item);
        item.setIngredient(getIngredientOrThrow(dto.getIngredientId()));
        return pantryItemMapper.toDto(pantryItemRepository.save(item));
    }

    @Override
    public void delete(Long id) {
        if (!pantryItemRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Element introuvable");
        }
        pantryItemRepository.deleteById(id);
    }

    private PantryItem getOrThrow(Long id) {
        return pantryItemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Element introuvable"));
    }

    private Ingredient getIngredientOrThrow(Long ingredientId) {
        return ingredientRepository.findById(ingredientId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingredient introuvable"));
    }
}


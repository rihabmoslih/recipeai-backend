package com.recipeai.ingredientservice.controller;

import com.recipeai.ingredientservice.dto.PantryItemRequestDTO;
import com.recipeai.ingredientservice.dto.PantryItemResponseDTO;
import com.recipeai.ingredientservice.service.PantryItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pantry")
@RequiredArgsConstructor
public class PantryItemController {

    private final PantryItemService pantryItemService;

    @PostMapping
    public ResponseEntity<PantryItemResponseDTO> create(@Valid @RequestBody PantryItemRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pantryItemService.create(dto));
    }

    @GetMapping("/user/{userId}")
    public List<PantryItemResponseDTO> findByUserId(@PathVariable Long userId) {
        return pantryItemService.findByUserId(userId);
    }

    @GetMapping("/{id}")
    public PantryItemResponseDTO findById(@PathVariable Long id) {
        return pantryItemService.findById(id);
    }

    @PutMapping("/{id}")
    public PantryItemResponseDTO update(@PathVariable Long id, @Valid @RequestBody PantryItemRequestDTO dto) {
        return pantryItemService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        pantryItemService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

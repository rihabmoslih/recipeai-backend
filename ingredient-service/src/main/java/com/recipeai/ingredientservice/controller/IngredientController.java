package com.recipeai.ingredientservice.controller;

import com.recipeai.ingredientservice.dto.IngredientRequestDTO;
import com.recipeai.ingredientservice.dto.IngredientResponseDTO;
import com.recipeai.ingredientservice.service.IngredientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ingredients")
@RequiredArgsConstructor
public class IngredientController {

    private final IngredientService ingredientService;

    @PostMapping
    public ResponseEntity<IngredientResponseDTO> create(@Valid @RequestBody IngredientRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ingredientService.create(dto));
    }

    @GetMapping
    public List<IngredientResponseDTO> findAll() {
        return ingredientService.findAll();
    }

    @GetMapping("/{id}")
    public IngredientResponseDTO findById(@PathVariable Long id) {
        return ingredientService.findById(id);
    }

    @PutMapping("/{id}")
    public IngredientResponseDTO update(@PathVariable Long id, @Valid @RequestBody IngredientRequestDTO dto) {
        return ingredientService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ingredientService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

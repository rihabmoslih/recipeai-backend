package com.recipeai.recipeservice.controller;

import com.recipeai.recipeservice.dto.RecipeRequestDTO;
import com.recipeai.recipeservice.dto.RecipeResponseDTO;
import com.recipeai.recipeservice.service.RecipeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipes")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    @PostMapping
    public ResponseEntity<RecipeResponseDTO> create(@Valid @RequestBody RecipeRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(recipeService.create(dto));
    }

    @GetMapping
    public List<RecipeResponseDTO> findAll() {
        return recipeService.findAll();
    }

    @GetMapping("/user/{userId}")
    public List<RecipeResponseDTO> findByUserId(@PathVariable Long userId) {
        return recipeService.findByUserId(userId);
    }

    @GetMapping("/{id}")
    public RecipeResponseDTO findById(@PathVariable Long id) {
        return recipeService.findById(id);
    }

    @PutMapping("/{id}")
    public RecipeResponseDTO update(@PathVariable Long id, @Valid @RequestBody RecipeRequestDTO dto) {
        return recipeService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        recipeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

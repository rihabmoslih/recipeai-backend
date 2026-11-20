package com.recipeai.recipeservice.controller;

import com.recipeai.recipeservice.dto.FavoriteRequestDTO;
import com.recipeai.recipeservice.dto.FavoriteResponseDTO;
import com.recipeai.recipeservice.service.FavoriteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favorites")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PostMapping
    public ResponseEntity<FavoriteResponseDTO> add(@Valid @RequestBody FavoriteRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(favoriteService.add(dto));
    }

    @GetMapping("/user/{userId}")
    public List<FavoriteResponseDTO> findByUserId(@PathVariable Long userId) {
        return favoriteService.findByUserId(userId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        favoriteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

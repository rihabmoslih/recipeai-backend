package com.recipeai.ingredientservice.service;

import com.recipeai.ingredientservice.client.UserClient;
import com.recipeai.ingredientservice.dto.UserDTO;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@RequiredArgsConstructor
public class RemoteUserValidator {

    private final UserClient userClient;

    public UserDTO requireUser(Long userId) {
        try {
            return userClient.findById(userId);
        } catch (FeignException.NotFound e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur introuvable : " + userId);
        } catch (FeignException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "user-service indisponible");
        }
    }
}

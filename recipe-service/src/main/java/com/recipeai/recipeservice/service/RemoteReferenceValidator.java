package com.recipeai.recipeservice.service;

import com.recipeai.recipeservice.client.IngredientClient;
import com.recipeai.recipeservice.client.UserClient;
import com.recipeai.recipeservice.dto.IngredientDTO;
import com.recipeai.recipeservice.dto.RecipeIngredientDTO;
import com.recipeai.recipeservice.dto.UserDTO;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.util.List;
import java.util.function.Supplier;

@Slf4j
@Component
public class RemoteReferenceValidator {

    private final UserClient userClient;
    private final IngredientClient ingredientClient;
    private final CircuitBreaker userBreaker;
    private final CircuitBreaker ingredientBreaker;
    private final Retry userRetry;
    private final Retry ingredientRetry;

    public RemoteReferenceValidator(UserClient userClient, IngredientClient ingredientClient) {
        this.userClient = userClient;
        this.ingredientClient = ingredientClient;

        CircuitBreakerConfig cbConfig = CircuitBreakerConfig.custom()
                .slidingWindowSize(5)
                .minimumNumberOfCalls(3)
                .failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofSeconds(10))
                .ignoreExceptions(FeignException.NotFound.class)
                .build();
        RetryConfig retryConfig = RetryConfig.custom()
                .maxAttempts(3)
                .waitDuration(Duration.ofMillis(300))
                .retryOnException(e -> !(e instanceof FeignException.NotFound)
                        && !(e instanceof CallNotPermittedException))
                .build();

        this.userBreaker = CircuitBreaker.of("user-service", cbConfig);
        this.ingredientBreaker = CircuitBreaker.of("ingredient-service", cbConfig);
        this.userRetry = Retry.of("user-service", retryConfig);
        this.ingredientRetry = Retry.of("ingredient-service", retryConfig);

        for (CircuitBreaker cb : List.of(userBreaker, ingredientBreaker)) {
            cb.getEventPublisher().onStateTransition(e ->
                    log.warn("Circuit breaker {} : {}", cb.getName(), e.getStateTransition()));
        }
        for (Retry r : List.of(userRetry, ingredientRetry)) {
            r.getEventPublisher().onRetry(e ->
                    log.warn("Retry {} : tentative {}", r.getName(), e.getNumberOfRetryAttempts()));
        }
    }

    private <T> T call(CircuitBreaker breaker, Retry retry, Supplier<T> action) {
        Supplier<T> decorated = Retry.decorateSupplier(retry,
                CircuitBreaker.decorateSupplier(breaker, action));
        return decorated.get();
    }

    public UserDTO requireUser(Long userId) {
        try {
            return call(userBreaker, userRetry, () -> userClient.findById(userId));
        } catch (FeignException.NotFound e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur introuvable : " + userId);
        } catch (Exception e) {
            log.warn("Repli user-service : {}", e.toString());
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                    "user-service indisponible, reessayez plus tard");
        }
    }

    public IngredientDTO requireIngredient(Long ingredientId) {
        try {
            return call(ingredientBreaker, ingredientRetry, () -> ingredientClient.findById(ingredientId));
        } catch (FeignException.NotFound e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingredient introuvable : " + ingredientId);
        } catch (Exception e) {
            log.warn("Repli ingredient-service : {}", e.toString());
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                    "ingredient-service indisponible, reessayez plus tard");
        }
    }

    public void validateIngredients(List<RecipeIngredientDTO> ingredients) {
        ingredients.stream()
                .map(RecipeIngredientDTO::getIngredientId)
                .distinct()
                .forEach(this::requireIngredient);
    }

    public void validateRecipe(Long userId, List<RecipeIngredientDTO> ingredients) {
        requireUser(userId);
        validateIngredients(ingredients);
    }
}

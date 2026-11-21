package com.recipeai.ingredientservice.client;

import com.recipeai.ingredientservice.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "USER-SERVICE")
public interface UserClient {

    @GetMapping("/users/{id}")
    UserDTO findById(@PathVariable("id") Long id);
}

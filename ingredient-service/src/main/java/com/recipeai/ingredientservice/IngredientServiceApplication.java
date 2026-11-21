package com.recipeai.ingredientservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@org.springframework.cloud.openfeign.EnableFeignClients
public class IngredientServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(IngredientServiceApplication.class, args);
	}

}


# Diagrammes de classes par microservice

Dans chaque diagramme, les champs `userId` et `ingredientId` sont des **références distantes** : de simples `Long`, sans clé étrangère ni relation JPA, car l'objet est dans la base d'un autre service.

## user-service

```mermaid
classDiagram
    class User {
        +Long id
        +String nom
        +String email
        +String motDePasse
        +String role
        +String regime
    }
    class Allergie {
        +Long id
        +String libelle
    }
    class UserRequestDTO {
        +String nom
        +String email
        +String motDePasse
        +String regime
        +List allergies
    }
    class UserResponseDTO {
        +Long id
        +String nom
        +String email
        +String role
        +String regime
        +List allergies
    }
    class UserController
    class UserService {
        <<interface>>
    }
    class UserServiceImpl
    class UserRepository {
        <<interface>>
    }
    class UserMapper {
        <<MapStruct>>
    }

    User "1" --> "*" Allergie : possède
    UserController --> UserService
    UserServiceImpl ..|> UserService
    UserServiceImpl --> UserRepository
    UserServiceImpl --> UserMapper
    UserMapper ..> UserRequestDTO
    UserMapper ..> UserResponseDTO
    UserMapper ..> User
```

## ingredient-service

```mermaid
classDiagram
    class Ingredient {
        +Long id
        +String nom
        +String categorie
        +String unite
    }
    class PantryItem {
        +Long id
        +Long userId
        +Double quantite
        +LocalDate dateExpiration
    }
    class PantryItemRequestDTO {
        +Long userId
        +Long ingredientId
        +Double quantite
        +LocalDate dateExpiration
    }
    class PantryItemResponseDTO {
        +Long id
        +Long userId
        +Long ingredientId
        +String ingredientNom
        +Double quantite
    }
    class UserClient {
        <<FeignClient USER-SERVICE>>
        +findById(Long id) UserDTO
    }
    class RemoteUserValidator {
        +requireUser(Long userId) UserDTO
    }
    class PantryItemServiceImpl
    class IngredientServiceImpl

    PantryItem "*" --> "1" Ingredient : même base, vraie relation
    PantryItemServiceImpl --> RemoteUserValidator
    PantryItemServiceImpl --> PantryItem
    IngredientServiceImpl --> Ingredient
    RemoteUserValidator --> UserClient

    note for PantryItem "userId : référence distante vers user-service, sans clé étrangère"
```

## recipe-service

```mermaid
classDiagram
    class Recipe {
        +Long id
        +String titre
        +String description
        +Integer duree
        +Integer scorePourcentage
        +Long userId
        +LocalDateTime dateCreation
    }
    class RecipeIngredient {
        +Long id
        +Long ingredientId
        +Double quantite
        +boolean disponible
    }
    class Step {
        +Long id
        +Integer ordre
        +String instruction
    }
    class Favorite {
        +Long id
        +Long userId
    }
    class UserClient {
        <<FeignClient USER-SERVICE>>
        +findById(Long id) UserDTO
    }
    class IngredientClient {
        <<FeignClient INGREDIENT-SERVICE>>
        +findById(Long id) IngredientDTO
    }
    class RemoteReferenceValidator {
        +requireUser(Long userId) UserDTO
        +requireIngredient(Long id) IngredientDTO
        +validateRecipe(Long userId, List ingredients)
    }
    class RecipeDetailsService {
        +getDetails(Long id) RecipeDetailsDTO
    }
    class RecipeDetailsDTO {
        +Long id
        +String titre
        +Integer scorePourcentage
        +UserDTO user
        +List ingredients
        +List steps
    }
    class RecipeServiceImpl
    class FavoriteServiceImpl
    class RecipeEventPublisher {
        +publishRecipeCreated(String email, String nom, String titre)
    }

    Recipe "1" --> "*" RecipeIngredient : composée de
    Recipe "1" --> "*" Step : étapes ordonnées
    Favorite "*" --> "1" Recipe : même base, vraie relation
    RecipeServiceImpl --> RemoteReferenceValidator
    RecipeServiceImpl --> RecipeEventPublisher
    FavoriteServiceImpl --> RemoteReferenceValidator
    RemoteReferenceValidator --> UserClient
    RemoteReferenceValidator --> IngredientClient
    RecipeDetailsService --> RemoteReferenceValidator
    RecipeDetailsService ..> RecipeDetailsDTO

    note for Recipe "userId : référence distante vers user-service, sans clé étrangère"
    note for RecipeIngredient "ingredientId : référence distante vers ingredient-service, sans clé étrangère"
    note for Favorite "userId : référence distante vers user-service, sans clé étrangère"
```

## notification-service

```mermaid
classDiagram
    class RabbitConfig {
        +String EXCHANGE = recipe.exchange
        +String QUEUE = recipe.created.queue
        +String ROUTING_KEY = recipe.created
        +DirectExchange recipeExchange()
        +Queue recipeCreatedQueue()
        +Binding recipeCreatedBinding()
    }
    class RecipeCreatedListener {
        <<RabbitListener>>
    }
    class RecipeEventPublisher {
        <<recipe-service>>
    }

    RecipeEventPublisher ..> RabbitConfig : publie sur recipe.exchange
    RecipeCreatedListener ..> RabbitConfig : écoute recipe.created.queue
```

Le message est une chaîne `email|nom|titre`. `notification-service` n'a pas de base de données.

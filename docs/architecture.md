# Schéma d'architecture globale

```mermaid
flowchart TD
    Client["Client<br/>Postman / Newman / futur front React"]
    GW["api-gateway<br/>port 8000"]

    subgraph INFRA["Services d'infrastructure"]
        EU["eureka-server<br/>port 8761<br/>Service Discovery"]
        CS["config-server<br/>port 8888"]
        CR[("config-repo<br/>user-service.yml<br/>ingredient-service.yml<br/>recipe-service.yml")]
    end

    subgraph SVC["Services métier (une base par service)"]
        US["user-service<br/>port 8091"]
        IS["ingredient-service<br/>port 8082"]
        RS["recipe-service<br/>ports 8083 et 8084"]
    end

    UDB[("user_db<br/>PostgreSQL 5433")]
    IDB[("ingredient_db<br/>PostgreSQL 5434")]
    RDB[("recipe_db<br/>PostgreSQL 5435")]

    Client -->|"HTTP"| GW
    GW -->|"lb://USER-SERVICE<br/>/users/**"| US
    GW -->|"lb://INGREDIENT-SERVICE<br/>/ingredients/** et /pantry/**"| IS
    GW -->|"lb://RECIPE-SERVICE<br/>/recipes/** et /favorites/**"| RS

    US --- UDB
    IS --- IDB
    RS --- RDB

    RS -->|"OpenFeign : valider userId"| US
    RS -->|"OpenFeign : valider ingredientId<br/>et agréger les détails"| IS
    IS -->|"OpenFeign : valider userId"| US

    SVC -.->|"enregistrement et découverte"| EU
    GW -.->|"découverte des instances"| EU
    CS --- CR
    CS -.->|"configuration au démarrage"| SVC

    subgraph ASYNC["Communication asynchrone (V5)"]
        MQ{{"RabbitMQ<br/>5672 et 15672<br/>recipe.exchange, recipe.created.queue"}}
        NS["notification-service<br/>port 8085"]
        MH["MailHog<br/>SMTP 1025, interface 8025"]
    end

    RS -.->|"publie, clé recipe.created"| MQ
    MQ -.->|"consomme"| NS
    NS -.->|"email"| MH
```

## Lecture du schéma

- **Traits pleins** : appels synchrones (HTTP via la gateway, OpenFeign entre services, accès aux bases).
- **Traits pointillés** : infrastructure (Eureka, Config Server) et messagerie asynchrone.
- **Références distantes** : `userId` et `ingredientId` sont stockés comme de simples `Long`, sans clé étrangère, car l'objet référencé est dans la base d'un autre service. La cohérence est assurée par les appels OpenFeign.
- **Load balancing** : la gateway résout `lb://NOM-SERVICE` via Eureka et répartit les requêtes entre les instances (exemple : recipe-service sur 8083 et 8084).

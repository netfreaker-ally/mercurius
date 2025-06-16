

````markdown
# 🧾 Account Service - Mercurius Microservices

The **Account Service** is a reactive microservice responsible for managing user account data. This module is built using **Spring WebFlux** and integrates with **MongoDB**, serving both as a core data service and a sandbox for learning new technologies like functional routing and reactive streams.

---

## 🎯 Purpose

- Manage account-related data (CRUD operations).
- Experiment and explore **MongoDB** integration with Spring Boot.
- Showcase **fully reactive architecture** using `RouterFunction` and `HandlerFunction`.
- Provide a **subscription-based reactive endpoint** to demonstrate server-sent events (SSE).

---

## 🚀 Features

- 🌐 **Reactive Functional Endpoints** using `RouterFunction`.
- 🧩 **MongoDB integration** with reactive repository support.
- 🔁 **Reactive streaming API** for real-time order updates (`/subscribe`).
- 🧪 **Test profile** for running without Eureka.
- 🛡️ Spring Actuator health probes enabled for readiness/liveness checks.

---

## 🗂️ Endpoint Summary

| Endpoint                              | Method | Description                                   |
|---------------------------------------|--------|-----------------------------------------------|
| `/api/accuonts/create`               | POST   | Create a new account                          |
| `/api/accuonts`                      | GET    | Get all accounts                              |
| `/api/accuonts/{accountId}`          | DELETE | Delete account by ID                          |
| `/api/accuonts/{accountId}`          | PUT    | Update account by ID                          |
| `/create-offer`                      | POST   | Create a new offer (experimental)             |
| `/subscribe`                         | GET    | Subscribe to order events via SSE             |
| `/hello`                             | GET    | Simple "Hello World" test endpoint            |

---

## ⚙️ Configuration

### 🔧 `application.yml`

```yaml
spring:
  application:
    name: account-service
  data:
    mongodb:
      host: localhost
      port: 27017
      database: local

eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:8761/eureka/
````

> The service also includes a **test profile** to run standalone without Eureka.

---

## 📡 Reactive Router

Instead of using traditional `@RestController`, routes are defined in a **functional style** via:

```java
@Bean
RouterFunction<ServerResponse> AccountesRoutes(AccountsHandler accountsHandler) {
    return route()
        .path("/api/accuonts", builder -> builder
            .POST("/create", accountsHandler::createAccount)
            .GET("", accountsHandler::getAccounts)
            .DELETE("/{accountId}", accountsHandler::deleteAccount)
            .PUT("/{accountId}", accountsHandler::updateAccount))
        .GET("/hello", request -> ServerResponse.ok().bodyValue("Hello World"))
        .POST("/create-offer", accountsHandler::createOffer)
        .GET("/subscribe", accountsHandler::subscribeToOrderEvents)
        .build();
}
```

---

## 🧪 MongoDB Logging (Debug Enabled)

To observe MongoDB queries for learning purposes, the following logging is configured:

```yaml
logging:
  level:
    org:
      springframework:
        data:
          mongodb:
            core: debug
    mongodb:
      driver: debug
```

---

## 🧰 Tech Stack

* Java 17
* Spring Boot 3.x
* Spring WebFlux
* MongoDB (Reactive)
* Eureka Client (except in test profile)
* Spring Actuator

---

## 🧪 Learning Goals Achieved

* ✅ Hands-on with reactive programming (WebFlux)
* ✅ Explored functional routing via `RouterFunction`
* ✅ Practiced real-time SSE-style subscriptions
* ✅ Integrated and debugged MongoDB with Spring Boot

---

## 👨‍💻 Developed by

**Hanuma Ramavath**
[AWS Certified | Java & Spring Enthusiast](https://github.com/netfreaker-ally)

---

## 🔗 Related Services

* [Bridge Service](../bridge)
* [Product Service](../productservice)
* [Order Service](../orderservice)
* [Notification Service](../notifications)

```


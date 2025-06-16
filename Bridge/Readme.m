Thanks for the background, Hanuma. Based on your description and the architectural purpose of the `bridge` service — designed initially as a central hub for inter-service communication using **FeignClients** and **WebClient**, even though it later became partially redundant due to API Gateway — here is a professional and honest `README.md` for the **Bridge Service** in your `mercurius` project:

---

````markdown
# 🌉 Bridge Service - Mercurius Microservices

The **Bridge Service** was initially created to centralize inter-service communication in the Mercurius architecture before introducing the API Gateway. It focuses on demonstrating the use of **Feign Clients** and **WebClient** to call downstream services such as Accounts, Orders, Product Offerings, and User Profiles.

While the service is not currently in active use due to the adoption of Spring Cloud Gateway, it is retained as a reference for using declarative and programmatic client mechanisms in a microservices setup.

---

## 📌 Purpose

- Serve as a central **orchestrator** to bridge calls between services.
- Showcase **FeignClient**-based interaction between services like:
  - Account Service
  - Order Service
  - Product Offerings
  - User Profile Service
- Support **WebClient-based reactive interaction** as an alternative.
- Useful during early development before implementing the API Gateway layer.

---

## 🚀 Features

- Uses **FeignClients** for synchronous, declarative REST calls.
- Implements **WebClient** for reactive, non-blocking service-to-service calls.
- Can act as a fallback route when API Gateway is unavailable or under maintenance.
- Handles multiple controllers for demonstration and testing purposes.

---

## 🧩 Controllers Included

### 1. `UserProfileController`
- Sample controller to fetch or update user profile data from other services.

### 2. `OrderController`
- Used to invoke order-related operations from the Order service.
- Helpful in early stage testing of FeignClient setup.

### 3. `ProductOfferingsController`
- Acts as a proxy to fetch product offerings.
- Built with Feign/WebClient as backend interactions.

### 4. `AccountsWebClientController`
- Showcases the usage of Spring `WebClient` to fetch account info reactively.
- Acts as a bridge to the Account service.

---

## 📡 Inter-Service Communication

### 🔗 FeignClients
- Defined interfaces using `@FeignClient(name="SERVICE-NAME")`
- Useful for developer-friendly, declarative HTTP clients.

### ⚙️ WebClient (Reactive)
- Used in `AccountsWebClientController` to handle non-blocking calls.

```java
@Autowired
private WebClient.Builder webClientBuilder;

webClientBuilder.build()
    .get()
    .uri("http://ACCOUNT-SERVICE/api/account/{id}", id)
    .retrieve()
    .bodyToMono(Account.class);
````

---

## 🛠️ Technologies Used

* Java 17
* Spring Boot
* Spring Cloud OpenFeign
* Spring WebFlux (WebClient)
* Spring Web
* Eureka Client

---

## 💡 When to Use Bridge

* When API Gateway is not available or not yet integrated.
* When demonstrating FeignClient or WebClient usage in isolation.
* As a fallback interface for internal dashboards or service testing.
* For integration/unit tests involving cross-service communication.

---

## ⚙️ Configuration

Ensure that the following properties exist in `application.yml`:

```yaml
spring:
  application:
    name: bridge
eureka:
  client:
    registerWithEureka: true
    fetchRegistry: true
    serviceUrl:
      defaultZone: http://localhost:8761/eureka/
```

---

## 📁 Folder Structure

```
bridge/
├── controller/
│   ├── UserProfileController.java
│   ├── OrderController.java
│   ├── ProductOfferingsController.java
│   └── AccountsWebClientController.java
├── client/
│   ├── AccountFeignClient.java
│   ├── OrderFeignClient.java
│   └── ProductFeignClient.java
├── config/
│   └── WebClientConfig.java
```

---

## 🧪 Status

> 🔸 **Experimental / Partially Deprecated**
> This service was created as a temporary bridge before API Gateway adoption. It is retained for reference and local testing purposes.

---

## 👨‍💻 Maintained by

**Hanuma Ramavath**
[GitHub](https://github.com/netfreaker-ally)

---

## 📎 Related Services

* [API Gateway](../apigateway)
* [Order Service](../orderservice)
* [Product Service](../productservice)
* [Account Service](../accountservice)

```




````markdown
# ✅ Eligibility Service - Mercurius Microservices

The **Eligibility Service** is responsible for evaluating and managing user eligibility for product offers. It integrates with external services (Account and Product) using **OpenFeign**, and supports dynamic offer management, eligibility checks, and inter-service product/account creation.

---

## 🎯 Purpose

- Evaluate user-product eligibility dynamically.
- Fetch eligible products and offers for a specific user.
- Create accounts/products from this service using Feign clients.
- Test various SQL setups including free MySQL hosting and H2 DB.

---

## 🔁 Key Functionalities

- 🧮 Evaluate product eligibility for a user
- 🎁 Create and fetch offers associated with an account
- 🔗 Connect to Account and Product services via Feign clients
- 💾 Switchable database config: external MySQL or local H2 (fallback)

---

## 📡 Interfaces

```java
public interface IEligibilityService {
    EligibilityStatusRepresentation evaluateEligibility(String accountId, String productId);
    List<ProductRepresentation> getEligibleProductsForUser(String accountId);
    boolean createOfferForUser(String accountId, OfferRepresentation offer);
    Set<OfferRepresentation> getUserOffers(String accountId);

    ResponseEntity<ResponseDto> createAccount(AccountRepresentation account);
    ResponseEntity<ResponseDto> createProduct(ProductRepresentation product);
}
````

---

## 📦 Feign Clients

### ➕ Product Client

```java
@FeignClient(name = "PRODUCT-SERVICE")
public interface ProductsClient {
    @PostMapping("/api/create")
    ResponseEntity<ResponseDto> createProduct(@Valid @RequestBody ProductRepresentation product);
}
```

### ➕ Account Client

```java
@FeignClient(name = "ACCOUNT-SERVICE")
public interface AccountsClient {
    @PostMapping("/api/create")
    ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody AccountRepresentation account);
}
```

---

## ⚙️ Configuration

### 📁 `application.yml`

```yaml
server:
  port: 8085

spring:
  application:
    name: eligibility-service
  datasource:
    url: jdbc:h2:mem:testdb
    driverClassName: org.h2.Driver
    username: sa
    password: ''
  h2:
    console:
      enabled: true
  jpa:
    database-platform: org.hibernate.dialect.H2Dialect
    hibernate:
      ddl-auto: update
    show-sql: true
```

### 🔄 Previous MySQL Hosting Setup (Commented)

```yaml
# spring:
#   datasource:
#     url: jdbc:mysql://sql12.freemysqlhosting.net:3306/sql12712947
#     driverClassName: com.mysql.cj.jdbc.Driver
#     username: sql12712947
#     password: Wygs35Nzqf
#     hikari:
#       minimumIdle: 5
#       maximumPoolSize: 10
#   jpa:
#     database-platform: org.hibernate.dialect.MySQL8Dialect
#     hibernate:
#       ddl-auto: update
```

> ✅ Switched back to H2 after free hosting trial expired. This shows flexibility and willingness to experiment with new setups.

---

## 🌐 Service Registration

```yaml
eureka:
  instance:
    preferIpAddress: true
  client:
    fetchRegistry: true
    registerWithEureka: true
    serviceUrl:
      defaultZone: http://localhost:8761/eureka/
```

---

## 🧰 Tech Stack

* Java 17
* Spring Boot 3.x
* Spring Data JPA
* H2 Database / MySQL (external optional)
* OpenFeign for inter-service communication
* Eureka Discovery Client

---

## 🧪 Learning Goals Achieved

* ✅ Experimented with external MySQL database hosting
* ✅ Integrated Feign clients with Product and Account services
* ✅ Worked with both H2 and MySQL (with switchable configs)
* ✅ Evaluated user-product eligibility in a clean, decoupled way

---

## 👨‍💻 Developed by

**Hanuma Ramavath**
[AWS Certified | Java + Spring + Cloud Enthusiast](https://github.com/netfreaker-ally)

---

## 🔗 Related Services

* [Account Service](../account-service)
* [Product Service](../product-service)
* [API Gateway](../apigateway)




````markdown
# 📦 Product Service

The **Product Service** is a microservice responsible for managing all product-related operations including creation, retrieval, update, deletion, and communication acknowledgment. It also manages associated account details and integrates with other services through messaging.

---

## 🎯 Purpose

- Manage products using CRUD operations.
- Send and receive asynchronous communication events using **Spring Cloud Stream** and **RabbitMQ**.
- Handle communication status updates when product-related events are acknowledged by downstream services.
- Perform internal account creation via Feign Client.

---

## ⚙️ Tech Stack

- Java 17+
- Spring Boot
- Spring Data JPA + H2 Database
- Spring Cloud Stream + RabbitMQ
- Eureka Discovery Client
- Spring Cloud OpenFeign

---

## 📁 Function Bean

The service listens to RabbitMQ for confirmation messages to update communication flags.

```java
@Bean
public Consumer<ProductRepresentation> updateCommunication(IProductManagementService productManagement) {
    return productRepresentaion -> {
        System.out.println("-----product confirmation received from rabbitmq----");
        productManagement.updateCommunicationStatus(productRepresentaion);
    };
}
````

---

## 🧠 Service Interface

```java
public interface IProductManagementService {
    ProductRepresentation getProductById(String productId);
    ProductRepresentation createProduct(ProductRepresentation product);
    boolean updateProduct(ProductRepresentation product);
    boolean deleteProduct(String productId);
    List<ProductRepresentation> getAllProducts();
    ProductRepresentation updateCommunicationStatus(ProductRepresentation product);
}
```

---

## 🧩 Repositories

### `ProductRepository`

```java
public interface ProductRepository extends JpaRepository<ProductRepresentation, Long> {
    Optional<ProductRepresentation> findByProductId(String productId);

    @Transactional
    @Modifying
    void deleteByProductId(String productId);
}
```

### `AccountsRepository`

```java
public interface AccountsRepository extends JpaRepository<AccountRepresentation, Long> {
    Optional<AccountRepresentation> findByAccountId(String accountId);

    @Transactional
    @Modifying
    void deleteByAccountId(String accountId);
}
```

---

## 🔄 Inter-service Communication

### Feign Client to Account Service

```java
@FeignClient(name = "ACCOUNT-SERVICE")
public interface AccountsClient {
    @PostMapping("/api/create")
    ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody AccountRepresentation account);
}
```

---

## 🔧 Configuration

### `application.yml` Highlights

```yaml
server:
  port: 8084

spring:
  application:
    name: product-service
  datasource:
    url: jdbc:h2:mem:testdb
    driverClassName: org.h2.Driver
    username: sa
    password: ''
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
  cloud:
    function:
      definition: updateCommunication
    stream:
      bindings:
        updateCommunication-in-0:
          destination: communication-sent
          group: ${spring.application.name}
        sendCommunication-out-0:
          destination: sendCommunicationAsProductCreated

rabbitmq:
  host: localhost
  port: 5672
  username: guest
  password: guest

eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:8761/eureka/
```

---

## 📦 Messaging Flow

| Direction | Binding Name               | Queue/Topic                         | Purpose                                    |
| --------- | -------------------------- | ----------------------------------- | ------------------------------------------ |
| IN        | `updateCommunication-in-0` | `communication-sent`                | Receive product acknowledgment messages    |
| OUT       | `sendCommunication-out-0`  | `sendCommunicationAsProductCreated` | Notify other services that product created |

---

## 🧪 Testing & Debugging

* H2 console: [http://localhost:8084/h2-console](http://localhost:8084/h2-console)
* RabbitMQ management UI: [http://localhost:15672](http://localhost:15672)
* Use Postman to test product endpoints and monitor logs for messaging confirmation.

---

## 🚀 Run Locally

1. Start Eureka and RabbitMQ.
2. Run the service using:

   ```bash
   ./mvnw spring-boot:run
   ```

---

## 👨‍💻 Author

**Hanuma Ramavath**
Backend Developer | Microservices | Messaging | Cloud Native Java
🔗 GitHub: [@hanumaramavath](https://github.com/netfreaker-ally)

---



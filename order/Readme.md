
````markdown
# 🛒 Order Service

The **Order Service** is a core microservice in the system responsible for managing user **orders**, **cart items**, and processing **order confirmations** through asynchronous event-driven communication using Spring Cloud Stream and RabbitMQ.

---

## 🎯 Purpose

- Create and persist new orders and cart items.
- Fetch all orders or cart items by account.
- Listen to order confirmation messages from other services (e.g., Notifications).
- Update order communication status when external confirmation is received.

---

## ⚙️ Tech Stack

- Java 17+
- Spring Boot
- Spring Data JPA
- Spring Cloud Stream (RabbitMQ binder)
- H2 (in-memory DB for dev)
- RabbitMQ for messaging
- Eureka Client for service discovery

---

## 🧩 Features

- Create orders and associated order items via `OrderService`.
- Use **Consumer function** `orderconfirmed` to receive messages from a RabbitMQ queue.
- Automatically update communication status when messages are received.
- Expose data operations using JPA repositories:
  - `CartRepository`
  - `OrderRepository`
  - `OrderItemRepository`

---

## 📁 Function Bean

```java
@Bean
public Consumer<Order> orderconfirmed(OrderService orderService) {
    return order -> {
        orderService.UpdateCommunicationStatus(order);
    };
}
````

This consumer listens to the `received-order-confirmation` queue and updates communication flags on matching orders.

---

## 🧠 Interfaces

```java
public interface OrderService {
    String createOrder(Order order);
    String createOrderItem(OrderItem orderItem);
    List<Order> getAllOrders();
    List<OrderItem> getAllCartItems(String accountId);
    Order UpdateCommunicationStatus(Order order);
}
```

---

## 🔧 Configuration

### `application.yml` Highlights:

```yaml
server:
  port: 9092

spring:
  application:
    name: order
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
    definition: orderconfirmed
  stream:
    bindings:
      orderconfirmed-in-0:
        destination: received-order-confirmation
        group: ${spring.application.name}
      sendOrderConfirmation-out-0:
        destination: Order-Confirmed

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

## 📦 Events & Messaging

| Direction | Binding Name                  | Exchange/Queue Name           | Purpose                                     |
| --------- | ----------------------------- | ----------------------------- | ------------------------------------------- |
| IN        | `orderconfirmed-in-0`         | `received-order-confirmation` | Receive confirmation from notification      |
| OUT       | `sendOrderConfirmation-out-0` | `Order-Confirmed`             | Publish order confirmation to notify others |

---

## 🧪 Testing & Debugging

* Access H2 console at: `http://localhost:9092/h2-console`
* Use Spring Actuator to check health probes.
* RabbitMQ messages can be inspected using the RabbitMQ management UI (if enabled).

---

## 🚀 Run Locally

1. Ensure RabbitMQ is running on `localhost:5672`.
2. Start Eureka Server.
3. Run the Order Service:

   ```bash
   ./mvnw spring-boot:run
   ```
4. POST sample orders or simulate RabbitMQ messages to test full event flow.

---

## 👨‍💻 Author

**Hanuma Ramavath**
Backend Developer | Spring • Microservices • Messaging • Java
🔗 GitHub: [@hanumaramavath](https://github.com/netfreaker-ally)

---



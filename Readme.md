# 🚀 Mercurious Microservices Platform

**Mercurious** is a distributed microservices platform built to demonstrate a fully integrated product-account-order-eligibility management ecosystem. It utilizes Spring Cloud, RabbitMQ, Eureka, Keycloak, and a suite of internal REST APIs to simulate real-world production-like architecture.

---

## 🧭 Architecture Overview

```
+-------------------+        +----------------+         +----------------+
|   API Gateway     +------->  Auth Service   +<--------+   Keycloak     |
+--------+----------+        +--------+-------+         +----------------+
         |                            |
         v                            v
+--------+----------+      +---------+--------+
|  Account Service  |      |  Product Service |
+--------+----------+      +---------+--------+
         |                            |
         v                            v
+--------+----------+      +---------+--------+
| Eligibility Svc   |<----->  Order Service   |
+-------------------+      +------------------+
         ^                            |
         |                            v
         +----------------------> RabbitMQ Queue
```

---

## 🧩 Services Summary

### 1. 🌐 API Gateway (BSyncOAuth)

- Built using **Spring Cloud Gateway**.
- Handles request routing, authentication, session validation.
- Authenticates incoming requests using OAuth2 (Keycloak).

### 2. 🔐 KeyAuth Service

- Internal service for **Keycloak admin operations**.
- Programmatically creates/updates/deletes clients and users.
- Secured and used only by authorized devs/admins.

### 3. 👤 Account Service

- Manages account creation and lookup.
- Exposes endpoints for user provisioning.
- Communicates with Product and Eligibility services.
- Uses JPA repository and Feign clients.

### 4. 📦 Product Service

- CRUD operations on products.
- Publishes events to RabbitMQ after product creation.
- Listens for communication acknowledgment events.
- Maintains product status and communication flags.

### 5. 🧾 Order Service

- Maintains order and cart details.
- Listens for order confirmations via RabbitMQ.
- Updates communication status based on downstream success.

### 6. ✅ Eligibility Service

- Determines eligibility based on account and product data.
- Exposes endpoints to get eligible products per account.
- Has internal logic for offer creation and validation.

### 7. 🔑 Keycloak (Authentication Server)

- Hosted locally at `http://localhost:8080/auth`.
- Realm: `master`
- Used for issuing JWT access tokens.
- Secures all protected APIs.

### 8. 📍 Eureka Server

- Central **Service Registry**.
- All services are registered here.
- Enables service discovery for Feign clients and Gateway.

---

## 📡 Event-Driven Messaging (RabbitMQ)

| Producer Service | Destination Queue                 | Consumer Service | Purpose                               |
| ---------------- | --------------------------------- | ---------------- | ------------------------------------- |
| Product Service  | sendCommunicationAsProductCreated | Order Service    | Product created communication trigger |
| Order Service    | Order-Confirmed                   | Eligibility Svc  | Order success message for eligibility |
| External System  | communication-sent                | Product Service  | Acknowledge product communication     |

---

## ⚙️ Common Config Patterns

### Spring Boot + H2 (Dev mode)

```yaml
spring:
  datasource:
    url: jdbc:h2:mem:testdb
  h2:
    console:
      enabled: true
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```

### Eureka Config (Client)

```yaml
eureka:
  client:
    registerWithEureka: true
    fetchRegistry: true
    serviceUrl:
      defaultZone: http://localhost:8761/eureka/
```

### RabbitMQ Config

```yaml
spring:
  rabbitmq:
    host: localhost
    port: 5672
    username: guest
    password: guest
```

---

## 🔐 Security

- All user-facing endpoints are secured via **Keycloak**.
- Gateway performs OAuth2 token validation and forwards only authorized requests.
- Internal service-to-service communication is trusted inside the secure network.

---

## 🌱 Dev & Environment Setup

1. **Start RabbitMQ**

   - Use Docker or local install:
     ```bash
     docker run -d --hostname rabbit --name rabbit -p 5672:5672 -p 15672:15672 rabbitmq:3-management
     ```

2. **Start Eureka Server**

   - Run the Eureka Server on port `8761`.

3. **Start Keycloak** (Standalone or Docker)

   - Add realm `master`, create admin user (`admin` / `admin`).
   - Client: `admin-cli`

4. **Run All Services**

   - Ensure order: Eureka > Keycloak > Gateway > Others

5. **Access UIs**

   - H2 Consoles: `http://localhost:<port>/h2-console`
   - RabbitMQ: `http://localhost:15672`
   - Keycloak: `http://localhost:8080/auth`

---

## 📖 How Services Talk

- Services like Product, Account, Eligibility use **OpenFeign** to communicate via logical service names registered on Eureka.
- Services communicate asynchronously using **Cloud Stream (RabbitMQ bindings)**.
- Auth flows handled via **Keycloak** access tokens + gateway filtering.

---

## 🧪 Testing

- Use Postman or Swagger (if enabled) to hit:

  - `/api/create` (Accounts, Products)
  - `/api/orders` (Orders)
  - `/api/eligible` (Eligibility)

- Log output will show real-time message events (e.g., order confirmed, communication sent).

---

## 📂 Repository Structure (Suggestion)

```
mercurius/
├── api-gateway/
├── keyAuth/
├── account-service/
├── product-service/
├── eligibility-service/
├── order-service/
├── eureka-server/
├── docker/
└── README.md (this file)
```

---

## 👨‍💻 Author

**Hanuma Ramavath**\
Java | Spring Boot | Microservices | AWS | Project Reactor | Keycloak\
🔗 GitHub: [@netfreaker-ally](https://github.com/netfreaker-ally)

---

## 📝 Final Notes

- This project demonstrates a production-ready microservices foundation.
- It includes domain-driven design, functional messaging, programmatic identity management, and real-world async orchestration.
- Easily extensible to integrate more services or migrate to full cloud (AWS/GCP).

> 🔒 Secured, 🧠 Logical, and 💡 Educational. That’s Mercurious.


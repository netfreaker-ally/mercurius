
````markdown
# 🔐 KeyAuth - Keycloak Admin Integration Service

The **KeyAuth** service acts as a secure middleware between your application ecosystem and **Keycloak**. It enables **programmatic management** of Keycloak entities like clients and users — especially useful when direct access to the Keycloak Admin Console is restricted.

---

## 🎯 Purpose

KeyAuth is designed to:
- Provide programmatic access to **create**, **update**, **delete**, and **fetch** Keycloak clients.
- Retrieve **admin access tokens** securely.
- Register new users or service clients on the fly.
- Reduce the need to access the Keycloak admin dashboard.

---

## 🧩 Features

- Create new clients using Keycloak Admin REST API.
- Fetch all registered clients or specific client metadata.
- Delete or update existing Keycloak clients.
- Register new users programmatically.
- Retrieve admin access tokens securely.

---

## 📁 Configuration Overview

### 🔐 `application.yml`

```yaml
keycloak:
  auth-server-url: http://localhost:8080/auth
  realm: master
  admin-user: admin
  admin-password: admin
  client-id: admin-cli
  token-endpoint: http://localhost:8080/realms/master/protocol/openid-connect/token
  clients:
    default:
      service-accounts-enabled: true
      public-client: false
      enabled: true

server:
  port: 8081

spring:
  application:
    name: keyAuth
````

* Uses `admin-cli` to obtain a management token.
* Configured to interact with the default `master` realm.

---

## 🔧 Key Interface

```java
public interface IkeycloakService {
    ResponseEntity<String> createClient(String accessToken);
    boolean deleteClient(String clientId);
    boolean updateClient(String clientId);
    ClientRepresentation getClient(String clientId);
    List<ClientRepresentation> getAllClients();
    String getAdminAccessToken();
    ResponseEntity<String> registerUser();
}
```

This interface defines all Keycloak operations supported by the service.

---

## ▶️ How to Use

1. Start your Keycloak instance at `http://localhost:8080`
2. Run this service:

   ```bash
   ./mvnw spring-boot:run
   ```
3. Call exposed endpoints (or service layer) to create, update, delete Keycloak clients or users.

> ✅ Ideal for DevOps automation, custom admin dashboards, or backend service provisioning flows.

---

## 📦 Dependencies

* Spring Boot
* Keycloak Admin Client
* Spring Web
* Jackson / RESTTemplate / Feign (if used internally)

---

## 🌐 Security Note

* Only authenticated users (admin credentials in config) can obtain tokens.
* This service should be **internally accessible only** in production setups.

---

## 📖 Example Use Case

* During tenant onboarding in a multi-tenant app, you can call `createClient()` to register a new client in Keycloak.
* While spinning up microservices, use `getAdminAccessToken()` to fetch a token for seeding client roles or permissions.

---

## 👨‍💻 Developed by

**Hanuma Ramavath**
Backend Developer | Spring • Keycloak • Microservices • AWS
🔗 GitHub: [@hanumaramavath](https://github.com/netfreaker-ally)

---


# 🛡️ Mercurius - API Gateway

The **API Gateway** acts as a single entry point to all microservices in the *Mercurius* system. It handles authentication via Keycloak (OAuth2), service routing, circuit breaking, retries, token relaying, custom filtering, and basic session management.

---

## 🚀 Tech Stack

- **Spring Cloud Gateway**
- **Spring Boot**
- **Spring Security (OAuth2 Resource Server + Token Relay)**
- **Resilience4j** (Circuit Breaker, Retry)
- **Eureka Service Discovery**
- **Keycloak** (Authentication/Authorization)
- **Spring Session (Reactive)**
- **Custom Filters**
- **Cloud-native Configs & Health Probes**

---

## ⚙️ Features

### ✅ Gateway Routing Configuration
All incoming requests are routed based on path prefixes like `/mercurius/products/**`. The configuration uses `RouteLocator` bean to define routes programmatically.

Key capabilities:
- 🔁 **Path Rewrite**: Strip prefix (`/mercurius`) to call downstream services.
- 🧠 **Circuit Breaker**: Uses `Resilience4j` to provide fallback URIs.
- 🔁 **Retry Logic**: For `GET` requests with exponential backoff.
- 📅 **Custom Headers**: Adds `X-Response-Time`, `Responsible`, etc.
- 🧭 **Service Discovery**: Routes use `lb://` (load-balanced via Eureka).

### 🔐 Spring Security + Keycloak
Configured as a **resource server and client**:
- Handles JWT validation with `TokenRelay` to downstream services.
- Uses `authorization_code` grant type with redirect-based login.
- Exposes Keycloak endpoints via `application.yml`.

### 💡 Custom Filters

- **CustomRedirectFilter**:  
  Redirects root (`/`) path to `/services`.

- **WebSessionTimeoutFilter** (Reactive WebFilter):  
  - Stores user sessions in memory via `ConcurrentHashMap`.
  - Invalidates old session if the user logs in again.
  - Sets session timeout (30 mins).

- **CsrfCheckFilter**:  
  (Pluggable CSRF protection filter — implementation details can be expanded.)

---

## 🔐 Key Configurations (`application.yml`)

```yaml
server:
  port: 9000

spring:
  application:
    name: ApiGateway

  security:
    oauth2:
      resourceserver:
        jwt:
          jwk-set-uri: http://localhost:8080/realms/master/protocol/openid-connect/certs
      client:
        registration:
          keycloak:
            client-id: APIGateway
            client-secret: <redacted>
            authorization-grant-type: authorization_code
            redirect-uri: http://localhost:9000/login/oauth2/code/keycloak
            scope: openid
        provider:
          keycloak:
            issuer-uri: http://localhost:8080/realms/master
            authorization-uri: http://localhost:8080/realms/master/protocol/openid-connect/auth
            token-uri: http://localhost:8080/realms/master/protocol/openid-connect/token
            user-info-uri: http://localhost:8080/realms/master/protocol/openid-connect/userinfo
            jwk-set-uri: http://localhost:8080/realms/master/protocol/openid-connect/certs
            user-name-attribute: preferred_username

  cloud:
    gateway:
      default-filters:
        - TokenRelay
      discovery:
        locator:
          enabled: true
          lowerCaseServiceId: true

resilience4j.circuitbreaker:
  configs:
    default:
      slidingWindowSize: 10
      permittedNumberOfCallsInHalfOpenState: 2
      failureRateThreshold: 50
      waitDurationInOpenState: 10000

eureka:
  instance:
    preferIpAddress: true
  client:
    fetchRegistry: true
    registerWithEureka: true
    serviceUrl:
      defaultZone: http://localhost:8761/eureka/

management:
  health:
    readiness-state:
      enabled: true
    liveness-state:
      enabled: true
  endpoint:
    health:
      probes:
        enabled: true
  endpoints:
    web:
      exposure:
        include: "*"

logging:
  level:
    org:
      springframework:
        security: DEBUG
        cloud:
          gateway: TRACE
        web: DEBUG
```

---

## 🧪 Sample Routes

| Route Path                        | Forwarded To            | Fallback URI         | Description                                |
|----------------------------------|--------------------------|----------------------|--------------------------------------------|
| `/mercurius/accounts/**`         | `ACCOUNT-SERVICE`        | `/accounts`          | Handles account-related APIs               |
| `/mercurius/products/**`         | `PRODUCT-SERVICE`        | `/product`           | Handles product data                       |
| `/mercurius/eligibility-service/**` | `ELIGIBILITY-SERVICE`  | `/eligibility-service` | Eligibility-related APIs              |
| `/mercurius/bridge/**`           | `BRIDGE`                 | `/bridge`            | Service coordination logic                 |
| `/mercurius/orders/**`           | `ORDER`                  | `/order`             | Order-related services                     |

---

## 📌 To Do / Improvements

- Integrate Redis for distributed session store.
- Add API Rate Limiting via Redis/Token Bucket.
- Implement real CSRF Filter (`CsrfCheckFilter`) with conditional logic.
- Add Swagger Gateway aggregation if needed.
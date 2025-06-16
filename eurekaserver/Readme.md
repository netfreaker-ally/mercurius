
````markdown
# 🧭 Eureka Server - Mercurius Microservices

The **Eureka Server** acts as the central **service registry** in the Mercurius microservices ecosystem. It enables **service discovery** by allowing microservices (clients) to register themselves and discover others dynamically without hardcoding IPs or ports.

---

## 🔧 What It Does

- Accepts service registrations from all client microservices.
- Maintains a **registry** of available services and their instance metadata.
- Enables **load-balanced communication** using logical service names.
- Hosts a web UI to view and manage service instances.

---

## 🌐 Accessing the Eureka Dashboard

Once the server is running:

🔗 Open in browser:  
`http://localhost:8761`

You’ll see a list of all registered client services (e.g., account-service, product-service, api-gateway, etc.) and their status.

---

## ⚙️ Configuration Summary

### 📁 `application.yml`

```yaml
server:
  port: 8761

spring:
  application:
    name: eureka-server

eureka:
  client:
    registerWithEureka: false
    fetchRegistry: false
  server:
    waitTimeInMsWhenSyncEmpty: 0
````

* `registerWithEureka: false` → since this is the registry itself.
* `fetchRegistry: false` → no need to fetch other instances.
* Port `8761` is commonly used for Eureka.

---

## 📦 Dependencies

* Spring Boot
* Spring Cloud Netflix Eureka Server

---

## 🚀 How to Run

1. Make sure the Eureka server is started **before** other services.
2. Run the application:

   ```bash
   ./mvnw spring-boot:run
   ```
3. Open the dashboard at [http://localhost:8761](http://localhost:8761).

---

## 📡 Clients Registering with Eureka

Every microservice that registers itself must have the following in its `application.yml`:

```yaml
eureka:
  client:
    registerWithEureka: true
    fetchRegistry: true
    serviceUrl:
      defaultZone: http://localhost:8761/eureka/
```

---

## 📖 Notes

* Eureka automatically removes instances that stop sending **heartbeat pings**.
* Useful for **load balancing** and **fault tolerance** with tools like Spring Cloud Gateway or Ribbon (deprecated).
* Can be scaled and clustered for production setups (using peer awareness).

---

## 👨‍💻 Developed by

**Hanuma Ramavath**
Backend Developer | Java • Spring • AWS • Microservices

---


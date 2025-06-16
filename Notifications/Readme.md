Here’s a complete and professional `README.md` content for your **NotificationService** that integrates RabbitMQ with Spring Cloud Stream and handles order/product notifications via email and PDF invoice generation:

---

````markdown
# 📨 Notification Service - Mercurius Microservices

The **Notification Service** is responsible for consuming domain events (like product creation or order confirmation) and triggering corresponding user notifications. It is built using **Spring Cloud Stream**, **RabbitMQ**, and integrates **Email + PDF invoice generation** for real-world notification delivery.

---

## 🚀 Features

- Listens to **product creation** and **order confirmation** events via RabbitMQ.
- Sends **email notifications** for both types of events.
- Generates and sends **PDF invoices** as attachments for orders.
- Uses **StreamBridge** to forward or acknowledge processed events.
- Integrates with the system via **Spring Cloud Function** for reactive stream processing.

---

## 🔧 Technologies Used

- Java 17
- Spring Boot
- Spring Cloud Stream
- RabbitMQ
- Spring Mail (JavaMailSender)
- StreamBridge
- Apache PDFBox (for PDF generation)

---

## 🗂️ Spring Cloud Stream Bindings

```yaml
spring:
  cloud:
    function:
      definition: orderCreated;productcreated
    stream:
      bindings:
        productcreated-in-0:
          destination: sendCommunicationAsProductCreated
          group: ${spring.application.name}
        productcreated-out-0:
          destination: communication-sent
        orderCreated-in-0:
          destination: Order-Confirmed
          group: ${spring.application.name}
        orderCreated-out-0:
          destination: received-order-confirmation
````

* `productcreated-in-0`: receives product creation events.
* `orderCreated-in-0`: receives order confirmation events.
* `productcreated-out-0` & `orderCreated-out-0`: can be used to forward/acknowledge processed messages.

---

## ✉️ Email Notifications

### Product Created

* Sends an email when a new product is added.
* Customizes product name before sending via `StreamBridge`.

### Order Confirmed

* Sends an email + PDF invoice as attachment to the customer.
* PDF generated using a helper class `PdfGenerator`.

---

## 📦 Message Processing

### Functions

* **productcreated**:
  Triggered when a product creation event is received. It:

  * Modifies product name (demo purpose).
  * Sends acknowledgment message via `StreamBridge`.
  * Sends email notification to admin.

* **orderCreated**:
  Triggered on order confirmation. It:

  * Generates a PDF invoice.
  * Sends email with PDF invoice attached to customer.
  * Sends message back to another stream via `StreamBridge`.

---

## 📁 Sample Class Highlight

```java
@Bean
public Function<Order, Order> orderCreated() {
    return order -> {
        // Process order and send email + invoice
    };
}
```

```java
@Bean
public Function<ProductRepresentation, ProductRepresentation> productcreated() {
    return product -> {
        // Modify product and notify via email + RabbitMQ
    };
}
```

---

## 📜 Future Improvements

* Store event logs in a centralized DB or ELK for auditing.
* Retry/Dead-letter queue configuration for failed messages.
* Integration with Notification Microservice Registry.
* Use secret manager for email credentials.

---

## 📬 Email Delivery

> Uses JavaMailSender to send simple and attachment-based emails. PDF generation uses ByteArray + FileOutputStream before sending.

---

## 🐇 RabbitMQ Setup (Local)

```bash
docker run -d --hostname my-rabbit --name some-rabbit \
  -p 5672:5672 -p 15672:15672 \
  rabbitmq:3-management
```

Access:

* Web UI: [http://localhost:15672](http://localhost:15672)
* Username: `guest`
* Password: `guest`

---

## ✅ Health Check

This service exposes basic `/actuator/health` endpoints and integrates with other services via service discovery (Eureka).

---

## 👨‍💻 Maintained by

**Hanuma Ramavath (NetFreaker-Ally)**
[GitHub](https://github.com/netfreaker-ally)

```

---

Let me know if you'd like the same for another service, or a combined `README.md` for the entire project.
```

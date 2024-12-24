# README
#
This README would normally document whatever steps are necessary to get your application up and running.

### What is this repository for?

Mercurius is a microservices-based demo project designed to demonstrate how multiple microservices work together in a distributed system. It integrates several services to showcase key microservices concepts such as communication, scalability, resiliency, service discovery, and secure interactions in a cloud-native environment.

The project is ideal for learning how microservices interact in practice, handling real-world concerns like fault tolerance, high availability, and smooth communication between services.
### Key Features

### Authentication Service

User registration, login, and session management with integrated social login options for convenience.
This service issues access tokens to authorize secure interactions with other microservices.

### API Gateway/Edge Server

A single entry point for all incoming requests, responsible for initial validation and routing to the appropriate microservices.

### Service Interaction

Handles various business logic tasks such as product offerings, account management, and eligibility checks.

### BaseProduct Service

Manages all services related to new users and ensures seamless integration with other components.

### Bundled Product Service

Deals with products and offers, determining the best options for each user and collaborating with the Eligibility Service.

### Message Microservice

Sends alerts and notifications to users about various events or offers through Kafka event broker.

### Eligibility Service

Updates user information with offers and eligibility status. It communicates with other services to ensure consistent data flow.

### Eureka Server

Acts as a service discovery and load balancing component, ensuring reliable connections between microservices.

### Database Layer

Supports the microservices architecture with local data storage and retrieval.
Infrastructure
Mercurius runs in a Kubernetes cluster, ensuring scalability, high availability, and ease of deployment. It uses Feign Client for synchronous communication between services and Kafka for asynchronous event-driven communication.

### Monitoring and Observability

To maintain reliability and performance, Mercurius incorporates monitoring tools like Prometheus and Grafana, as well as centralized logging with ELK stack. Distributed tracing is implemented to track the flow of requests across services, aiding in debugging and optimization.

### Security and Compliance

Mercurius adheres to modern security best practices, ensuring proper authentication, authorization, and data encryption. Compliance with regulations such as GDPR and HIPAA is considered where applicable.

### Getting Started

To set up and run Mercurius, follow the detailed installation and deployment instructions in the repository's documentation.
### Technology Stack
Backend: Java, Spring Boot
Microservices Communication: REST APIs, Message Queues (e.g., RabbitMQ)
Security: JWT Authentication, OAuth 2.0
Database: H2 (for development), MySQL (for production)
//Cloud: AWS (Elastic Beanstalk, RDS, EC2)
Other Tools: Docker, Kubernetes (for containerization and orchestration)



### Who do I talk to?

netfreaker

- Repo owner 

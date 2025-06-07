# 🛍️ E-commerce Backend Microservices (Dockerized)

## 🚀 Overview

Welcome to the **E-commerce Backend Microservices Project**! This backend system is built using a microservices architecture, promoting modularity, scalability, and ease of maintenance.

Leveraging **Docker** and **Docker Compose**, developers can effortlessly orchestrate multiple services such as:

- **API Gateway**
- **Service Registry (Eureka)**
- **Product Service**
- **Order Service**
- **Email Service**
- **Identity Service**
- **Payment Service**

The architecture is enriched with technologies like **Kafka**, **Redis**, **Zipkin**, and **OpenFeign** to provide a robust, observable, and loosely coupled system.

---

## 🧱 Microservices Overview

Each service is independently deployable and manages a distinct domain in the e-commerce workflow:

### 1. 🌐 API Gateway
- **Purpose**: Acts as the unified entry point for all client requests.
- **Tech Stack**: Spring Cloud Gateway
- **Key Features**: Routing, security enforcement, load balancing, rate limiting.

### 2. 📜 Service Registry
- **Purpose**: Manages service registration and discovery dynamically.
- **Tech Stack**: Eureka Server (Spring Cloud Netflix)
- **Key Features**: Discovery, health checks, fault tolerance.

### 3. 🛒 Product Service
- **Purpose**: Handles product catalog management and inventory.
- **Tech Stack**: Spring Boot, MySQL, Redis
- **Key Features**: Product CRUD, inventory management, Redis-based caching.

### 4. 🧾 Order Service
- **Purpose**: Manages user orders and their lifecycle.
- **Tech Stack**: Spring Boot, MySQL, Zipkin
- **Key Features**: Order creation, processing, history tracking, distributed tracing.

### 5. 📧 Email Service
- **Purpose**: Sends user notifications like order confirmations and password resets.
- **Tech Stack**: Spring Boot, MySQL
- **Key Features**: Email templating, scheduling, and status tracking.

### 6. 👤 Identity Service
- **Purpose**: Handles authentication and authorization.
- **Tech Stack**: Spring Boot, MySQL, Redis
- **Key Features**: User login, JWT auth, role management, session storage.

### 7. 💳 Payment Service
- **Purpose**: Processes payments and handles invoicing.
- **Tech Stack**: Spring Boot, MySQL, Zipkin
- **Key Features**: Payment handling, invoice generation, transaction history, tracing.

---

## 🛠️ Technologies Used

| Technology     | Purpose |
|----------------|---------|
| **Spring Boot**     | Core framework for building each service |
| **Spring Cloud**    | Distributed system support (Eureka, Gateway, etc.) |
| **Apache Kafka**    | Asynchronous messaging between services |
| **OpenFeign**       | Declarative HTTP client for inter-service communication |
| **MySQL**           | Persistent relational data storage |
| **Redis**           | In-memory data store used for caching/session |
| **Zipkin**          | Distributed tracing and monitoring |
| **Docker**          | Containerization of services for isolation and consistency |
| **Docker Compose**  | Orchestration of multi-container Docker environments |


## Frontend - React Application
The frontend for this e-commerce platform is developed using React and provides a seamless and responsive user experience.

🔑 Features
- **User authentication (login/register) using JWT tokens**
- **Product browsing and search**
- **Shopping cart functionality**

## Tech Stack
React frontend built with:

- **React 18**

- **TypeScript**
- **React Router for navigation**
- **Axios for API calls**
- **Bootstrap for styling**
- **React Hook Form for form handling**
- **Yup for form validation**


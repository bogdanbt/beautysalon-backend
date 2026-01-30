# Beauty Salon Backend

Backend service for a beauty salon booking system. Built with **Java + Spring Boot**, secured with **JWT-based authentication**, and backed by **MongoDB**.

The system provides REST APIs for user authentication, service discovery, master availability, and appointment management with role-based access control.

---

## Tech Stack

* **Java 17+**
* **Spring Boot**
* **Spring Security** (JWT, role-based authorization)
* **MongoDB**
* **BCrypt** (password hashing)
* **REST API**

---

## Architecture

The project follows a classic layered architecture:

```
controller  →  service  →  repository  →  database
      ↓
     dto
```

* **controller** – REST endpoints
* **service** – business logic
* **repository** – MongoDB access
* **dto** – request/response models
* **config** – security, CORS, JWT configuration

---

## Security

* JWT-based stateless authentication
* Role-based authorization (`CLIENT`, `ADMIN`)
* Passwords stored using **BCrypt hashing**
* Custom JWT filter integrated into Spring Security filter chain
* CORS configuration for frontend integration

### Public Endpoints

* `POST /api/auth/**` – registration & login
* `GET /api/services/**` – list of services
* `GET /api/masters/by-service/**` – masters by service
* `GET /api/masters/available-slots` – available time slots

### Protected Endpoints

* `GET /api/appointments/my` – client appointments (CLIENT, ADMIN)
* `GET /api/appointments/admin/all` – all appointments (ADMIN only)

All other endpoints require a valid JWT token.

---

## Features

* User registration and login
* JWT authentication & authorization
* Service catalog
* Master availability lookup
* Appointment booking
* Role-based access control

---

## Environment Configuration

Create a `.env` file in the project root:

```env
MONGODB_URI=mongodb://localhost:27017/beautysalon
JWT_SECRET=your_jwt_secret
JWT_EXPIRATION=86400000
```

---

## Running the Application

### Prerequisites

* Java 17+
* MongoDB
* Maven

### Run locally

```bash
mvn clean install
mvn spring-boot:run
```

The application will start on:

```
http://localhost:8080
```

---

## API Documentation

The API is designed to be frontend-ready and follows REST principles. Example requests can be found in the controller classes.

---

## Status

This project is an **MVP backend**, designed to be extended with:

* Swagger / OpenAPI documentation
* Refresh tokens
* Appointment cancellation & rescheduling
* Admin management features

---

## Author

Developed by **Bohdan Tarasenko**

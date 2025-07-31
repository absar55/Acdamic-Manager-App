# 🎓 School Management System

A full-stack web application for managing schools, classes, subjects, teachers, students, and enrollments.

---

## 🧰 Tech Stack

| Layer       | Technology                  |
| ----------- | --------------------------- |
| Frontend    | Angular 20.1.3              |
| Backend     | Java Spring Boot 3+         |
| Database    | PostgreSQL                  |
| ORM         | Spring Data JPA (Hibernate) |
| Build Tool  | Maven                       |
| HTTP Client | Angular `HttpClient`        |
| Protocol    | REST (JSON)                 |

---

## 🏁 Project Setup & Running Instructions

### 📌 Prerequisites

Make sure you have the following installed:

- Node.js 18+
- Angular CLI 20.1.3
- Java JDK 17+
- Maven 3.8+
- PostgreSQL (latest)

---

## 🛠 Backend Setup (Spring Boot + PostgreSQL)

### 1. Create Database

Open pgAdmin or terminal and run:

```sql
CREATE DATABASE school_management;

Set PostgreSQL Credentials

src/main/resources/application.properties

spring.datasource.url=jdbc:postgresql://localhost:5432/school_management
spring.datasource.username=postgres
spring.datasource.password=admin

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
server.port=9090




Run the Backend
./mvnw spring-boot:run




🌐 Frontend Setup (Angular)
cd Frontend (Angular)
npm install
ng serve
Frontend will be available at:
http://localhost:4200




📂 Project Structure
📦 Backend (/src/main/java/com/example/school/)
domain/ – JPA entities: School, Student, ClassSection, Subject, Teacher, Enrollment

repo/ – Spring Data JPA repositories

web/ – REST Controllers

SchoolApp.java – Entry point

💻 Frontend (/frontend/src/app/)
school-list.component.ts

create-school.component.ts

create-section.component.ts

enroll-student.component.ts
```

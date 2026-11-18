# CampusHub — Backend REST API
> **Full Stack Capstone Project · University Partner Programme**  
> Layer: **Backend API (Java + Spring Boot 2.7 + MongoDB Atlas + Spring Security JWT)**

---

## 📌 Overview

This repository contains the enterprise Spring Boot REST API for **CampusHub**, a multi-role course enrollment and learning management platform. It handles authentication, course catalog management, student enrollment with duplicate conflict checks, role-based authorization, and administrative statistics.

---

## 👥 Three Roles & Capabilities

- **Admin**: Assign roles, activate/deactivate accounts, moderate/archive courses, view platform metrics.
- **Instructor**: Author courses, manage curriculum/lessons, view student enrollment rosters.
- **Student**: Register, view catalog, self-enroll with concurrency locks, track learning progress (0-100%).

---

## 🍃 MongoDB Atlas Free Cluster Setup (M0 Sandbox)

1. Sign up for a free account at [MongoDB Atlas](https://cloud.mongodb.com).
2. Click **Create Deployment** &rarr; Select **M0 (Shared / Free)** &rarr; Choose an AWS or GCP region.
3. In **Security Quickstart**:
   - Create a Database User (e.g. `campushub_user` with password `CampusHub2026!`).
   - Under **Network Access**, click **Add IP Address** &rarr; select **Allow Access from Anywhere (`0.0.0.0/0`)**.
4. Click **Connect** &rarr; **Drivers** (Java) &rarr; Copy the connection URI:
   ```text
   mongodb+srv://<username>:<password>@cluster0.xxxx.mongodb.net/campushub?retryWrites=true&w=majority
   ```
5. Set this URI as an environment variable (`MONGO_URI`) or in `src/main/resources/application.properties`.

---

## ⚡ Pre-Seeded Demo Accounts

When the application boots for the first time against an empty database, `DataSeeder.java` automatically creates:

| Role | Email | Password |
|---|---|---|
| **Admin** | `admin@campushub.edu` | `Admin@123` |
| **Instructor** | `instructor@campushub.edu` | `Instructor@123` |
| **Student** | `student@campushub.edu` | `Student@123` |

---

## 🚀 Local Development Setup

### Prerequisites
- **JDK 8 or 17** (Eclipse Temurin or OpenJDK)
- **Apache Maven 3.8+**
- Free MongoDB Atlas URI

### 1. Configure Environment Variables
You can export them in your terminal:
```bash
# Windows PowerShell
$env:MONGO_URI="mongodb+srv://<user>:<password>@cluster0.xxxx.mongodb.net/campushub?retryWrites=true&w=majority"
$env:JWT_SECRET="404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970"
$env:CLIENT_URL="http://localhost:5173"

# Linux / macOS
export MONGO_URI="mongodb+srv://<user>:<password>@cluster0.xxxx.mongodb.net/campushub?retryWrites=true&w=majority"
export JWT_SECRET="404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970"
export CLIENT_URL="http://localhost:5173"
```

### 2. Build and Run
```bash
mvn clean install -DskipTests
mvn spring-boot:run
```
The server will start on port `8080`.

### 3. Verify Health Check
```bash
curl http://localhost:8080/api/health
```
Response:
```json
{
  "ok": true,
  "app": "CampusHub API",
  "status": "UP",
  "database": "MongoDB Atlas Connected"
}
```

---

## 📦 15 REST Endpoints Specification

| Method | Endpoint | Required Role | Description |
|---|---|---|---|
| `POST` | `/api/auth/register` | Public | Register new student (role locked to `STUDENT`) |
| `POST` | `/api/auth/login` | Public | Authenticate user & return signed JWT token |
| `GET` | `/api/auth/me` | Authenticated | Retrieve profile for authenticated user |
| `GET` | `/api/courses` | Public | Get courses (published for public/student; all for admin) |
| `GET` | `/api/courses/{id}` | Public | Get full course syllabus and details |
| `POST` | `/api/courses` | `ADMIN`, `INSTRUCTOR` | Create new course with lessons |
| `PUT` | `/api/courses/{id}` | `ADMIN`, Owner | Update course details, syllabus, or status |
| `DELETE` | `/api/courses/{id}` | `ADMIN`, Owner | Delete course |
| `POST` | `/api/courses/{id}/enroll` | `STUDENT` | Enroll in course (returns 409 if duplicate) |
| `GET` | `/api/courses/{id}/students`| `ADMIN`, Owner | View roster of enrolled students |
| `GET` | `/api/enrollments/me` | `STUDENT` | View current student's enrolled courses |
| `PATCH` | `/api/enrollments/{id}/progress`| `STUDENT` | Update progress percentage (0-100%) |
| `GET` | `/api/users` | `ADMIN` | List all registered users |
| `PATCH` | `/api/users/{id}/role` | `ADMIN` | Change role (`STUDENT`, `INSTRUCTOR`, `ADMIN`) or status |
| `GET` | `/api/admin/stats` | `ADMIN` | Platform metrics & category distribution |

---

## 🧪 Postman API Testing
Import `postman/CampusHub_API_Collection.json` into Postman. It includes pre-configured tests that automatically capture the JWT token on login and populate the `{{token}}` variable for subsequent requests.

---

## ☁️ Cloud Deployment (Render via Docker)

1. Push this repository to GitHub.
2. In [Render](https://render.com), click **New** &rarr; **Web Service**.
3. Select your repository:
   - **Runtime**: `Docker`
   - **Environment Variables**:
     - `MONGO_URI`: Your MongoDB Atlas connection URI
     - `JWT_SECRET`: 64-character secret
     - `CLIENT_URL`: Your production frontend URL (e.g. `https://campushub.vercel.app`)
4. Click **Create Web Service**. Render builds the multi-stage Dockerfile and hosts your API live over HTTPS.

---

## 📤 Push to GitHub (Instructor / Student Setup)

```bash
git remote add origin https://github.com/<YOUR_ORGANIZATION_OR_USERNAME>/campushub-backend.git
git branch -M main
git push -u origin main
```

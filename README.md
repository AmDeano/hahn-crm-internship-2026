# 🚀 Hahn CRM - Task Management System

A modern full-stack task management application built for the **Hahn Software Morocco 2026 Internship Program**.

![React](https://img.shields.io/badge/React-18.2.0-blue?logo=react)
![TypeScript](https://img.shields.io/badge/TypeScript-5.3.3-blue?logo=typescript)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.0-green?logo=spring)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue?logo=postgresql)
![Docker](https://img.shields.io/badge/Docker-Ready-blue?logo=docker)

---

## 📋 Table of Contents

- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [Project Architecture](#-project-architecture)
- [Prerequisites](#-prerequisites)
- [Getting Started](#-getting-started)
    - [Clone Repository](#1-clone-the-repository)
    - [Using Docker Compose](#2-using-docker-compose-recommended)
    - [Manual Setup](#3-manual-setup-without-docker)
- [Accessing the Application](#-accessing-the-application)
- [API Documentation](#-api-documentation)
- [Project Structure](#-project-structure)
- [Environment Variables](#-environment-variables)
- [Demo Credentials](#-demo-credentials)
- [Contributing](#-contributing)
- [License](#-license)

---

## ✨ Features

- 🔐 **JWT Authentication** - Secure user authentication
- 📁 **Project Management** - Create, view, and manage projects
- ✅ **Task Tracking** - Add tasks to projects with due dates
- 📊 **Progress Monitoring** - Track completion percentage
- 🎨 **Modern UI** - Beautiful, responsive design with Tailwind CSS
- 🐳 **Docker Ready** - Easy deployment with Docker Compose
- 🔄 **RESTful API** - Well-structured backend API
- 📱 **Responsive Design** - Works on desktop, tablet, and mobile

---

## 🛠 Tech Stack

### **Frontend**
- **React 18** - UI library
- **TypeScript** - Type-safe JavaScript
- **Vite** - Fast build tool
- **Tailwind CSS** - Utility-first CSS framework
- **React Router** - Client-side routing
- **Axios** - HTTP client

### **Backend**
- **Spring Boot 4** - Java framework
- **Spring Security** - Authentication & authorization
- **JWT** - Token-based authentication
- **JPA/Hibernate** - ORM
- **PostgreSQL** - Database
- **Maven** - Build tool

### **DevOps**
- **Docker** - Containerization
- **Docker Compose** - Multi-container orchestration
- **Nginx** - Reverse proxy (for frontend)

---

## 🏗 Project Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                         Client Browser                       │
│                     http://localhost:3000                    │
└───────────────────────────┬─────────────────────────────────┘
                            │
                            │ HTTP/HTTPS
                            │
┌───────────────────────────▼─────────────────────────────────┐
│                    Frontend (React + Vite)                   │
│                         Port: 3000                           │
│  ┌─────────────────────────────────────────────────────┐   │
│  │  • Login Page                                       │   │
│  │  • Dashboard (Projects List)                        │   │
│  │  • Project Detail (Tasks Management)                │   │
│  │  • Protected Routes                                 │   │
│  └─────────────────────────────────────────────────────┘   │
└───────────────────────────┬─────────────────────────────────┘
                            │
                            │ REST API
                            │ (Axios)
                            │
┌───────────────────────────▼─────────────────────────────────┐
│                  Backend (Spring Boot)                       │
│                         Port: 8080                           │
│  ┌─────────────────────────────────────────────────────┐   │
│  │  Controllers:                                       │   │
│  │  • AuthController    → /api/auth/**                │   │
│  │  • ProjectController → /api/projects/**            │   │
│  │  • TaskController    → /api/projects/{id}/tasks/** │   │
│  │                                                      │   │
│  │  Security:                                          │   │
│  │  • JWT Authentication Filter                        │   │
│  │  • CORS Configuration                               │   │
│  │                                                      │   │
│  │  Services:                                          │   │
│  │  • AuthService                                      │   │
│  │  • ProjectService                                   │   │
│  │  • TaskService                                      │   │
│  └─────────────────────────────────────────────────────┘   │
└───────────────────────────┬─────────────────────────────────┘
                            │
                            │ JDBC
                            │
┌───────────────────────────▼─────────────────────────────────┐
│                  Database (PostgreSQL)                       │
│                         Port: 5432                           │
│  ┌─────────────────────────────────────────────────────┐   │
│  │  Tables:                                            │   │
│  │  • users                                            │   │
│  │  • projects                                         │   │
│  │  • tasks                                            │   │
│  └─────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────┘
```

---

## 📦 Prerequisites

Before you begin, ensure you have the following installed:

- **Docker** (20.10+) and **Docker Compose** (2.0+)
    - [Install Docker Desktop](https://www.docker.com/products/docker-desktop/)

**OR** for manual setup:

- **Java 17+** - [Download JDK](https://www.oracle.com/java/technologies/downloads/)
- **Node.js 18+** and **npm** - [Download Node.js](https://nodejs.org/)
- **PostgreSQL 13+** - [Download PostgreSQL](https://www.postgresql.org/download/)
- **Maven 3.8+** (or use Maven Wrapper included)

---

## 🚀 Getting Started

### 1. Clone the Repository

```bash
# Clone the repository
git clone https://github.com/AmDeano/hahn-crm-internship-2026.git

# Navigate to project directory
cd hahn-crm-internship-2026
```

---

### 2. Using Docker Compose (Recommended)

The easiest way to run the entire application with one command.

#### **Step 1: Configure Environment Variables**

**Backend:**
```bash
# Create .env file for backend
cd backend
cp .env.example .env
```

Edit `backend/.env`:
```bash
# Database Configuration
POSTGRES_DB=taskmanagement
POSTGRES_USER=postgres
POSTGRES_PASSWORD=postgres

# JWT Configuration
JWT_SECRET=YourSuperSecretKeyThatIsAtLeast32CharactersLongForJWTTokenGeneration
JWT_EXPIRATION=604800000

# CORS Configuration
CORS_ALLOWED_ORIGINS=http://localhost:3000
```

**Frontend:**
```bash
# Create .env file for frontend
cd ../frontend
cp .env.example .env
```

Edit `frontend/.env`:
```bash
VITE_API_URL=/api
```

#### **Step 2: Build and Run with Docker Compose**

```bash
# Return to project root
cd ..

# Build and start all services
docker-compose up --build

# Or run in detached mode (background)
docker-compose up -d --build
```

#### **Step 3: Wait for Services to Start**

The first time might take 2-3 minutes. You'll see:

```
✅ Database started on port 5432
✅ Backend started on port 8080
✅ Frontend started on port 3000
```

#### **Step 4: Access the Application**

Open your browser and go to:

🌐 **Frontend:** [http://localhost:3000](http://localhost:3000)

#### **Useful Docker Commands**

```bash
# View logs
docker-compose logs -f

# View specific service logs
docker-compose logs -f backend
docker-compose logs -f frontend

# Stop all services
docker-compose down

# Stop and remove volumes (reset database)
docker-compose down -v

# Restart a specific service
docker-compose restart backend

# Check running containers
docker-compose ps
```

---

### 3. Manual Setup (Without Docker)

If you prefer to run services manually:

#### **Step 1: Setup Database**

```bash
# Start PostgreSQL service
# Windows:
net start postgresql-x64-16

# macOS:
brew services start postgresql

# Linux:
sudo systemctl start postgresql
```

Create database:
```bash
psql -U postgres
CREATE DATABASE taskmanagement;
\q
```

#### **Step 2: Setup Backend**

```bash
cd backend

# Create .env file
cp .env.example .env

# Edit .env with your database credentials

# Run backend (using Maven Wrapper)
# Git Bash / Linux / macOS:
export $(cat .env | grep -v '^#' | grep -v '^$' | xargs)
./mvnw spring-boot:run

# Windows PowerShell:
Get-Content .env | ForEach-Object { if ($_ -match '^([^#][^=]+)=(.*)$') { [Environment]::SetEnvironmentVariable($matches[1].Trim(), $matches[2].Trim(), "Process") } }
./mvnw spring-boot:run
```

Backend will start on **http://localhost:8080**

#### **Step 3: Setup Frontend**

Open a new terminal:

```bash
cd frontend

# Install dependencies
npm install

# Create .env file
cp .env.example .env

# Start development server
npm run dev
```

Frontend will start on **http://localhost:3000**

---

## 🌐 Accessing the Application

### **Frontend (User Interface)**

🔗 **URL:** [http://localhost:3000](http://localhost:3000)

**Pages:**
- `/login` - Login page
- `/projects` - Dashboard with all projects
- `/projects/:id` - Project detail with tasks

### **Backend (API)**

🔗 **URL:** [http://localhost:8080](http://localhost:8080)

**Health Check:**
```bash
curl http://localhost:8080/api/projects
# Should return 401 Unauthorized (authentication required)
```

### **Database**

🔗 **Connection:** `localhost:5432`

**Credentials:**
- **Host:** localhost
- **Port:** 5432
- **Database:** taskmanagement
- **User:** postgres
- **Password:** postgres (or your configured password)

**Connect with psql:**
```bash
psql -h localhost -U postgres -d taskmanagement
```

---

## 📚 API Documentation

### **Authentication Endpoints**

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/auth/login` | Login with email and password |

**Login Request:**
```json
{
  "email": "demo@hahn.com",
  "password": "demo123"
}
```

**Login Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "email": "demo@hahn.com",
  "name": "Demo User"
}
```

### **Project Endpoints**

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| GET | `/api/projects` | Get all user projects | ✅ Yes |
| GET | `/api/projects/{id}` | Get project details with tasks | ✅ Yes |
| POST | `/api/projects` | Create new project | ✅ Yes |
| PUT | `/api/projects/{id}` | Update project | ✅ Yes |
| DELETE | `/api/projects/{id}` | Delete project | ✅ Yes |

### **Task Endpoints**

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/api/projects/{projectId}/tasks` | Create new task | ✅ Yes |
| PUT | `/api/projects/{projectId}/tasks/{taskId}` | Update task | ✅ Yes |
| PATCH | `/api/projects/{projectId}/tasks/{taskId}/complete` | Mark task as complete | ✅ Yes |
| DELETE | `/api/projects/{projectId}/tasks/{taskId}` | Delete task | ✅ Yes |

**Example: Create Project**
```bash
curl -X POST http://localhost:8080/api/projects \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "My New Project",
    "description": "Project description"
  }'
```

---

## 📁 Project Structure

```
hahn-crm-internship-2026/
│
├── backend/                          # Spring Boot Backend
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/internship/taskmanager/
│   │   │   │   ├── config/           # Configuration classes
│   │   │   │   │   └── DataInitializer.java
│   │   │   │   ├── controller/       # REST Controllers
│   │   │   │   │   ├── AuthController.java
│   │   │   │   │   ├── ProjectController.java
│   │   │   │   │   └── TaskController.java
│   │   │   │   ├── dto/              # Data Transfer Objects
│   │   │   │   ├── model/            # JPA Entities
│   │   │   │   │   ├── User.java
│   │   │   │   │   ├── Project.java
│   │   │   │   │   └── Task.java
│   │   │   │   ├── repository/       # JPA Repositories
│   │   │   │   ├── security/         # Security Configuration
│   │   │   │   │   ├── SecurityConfig.java
│   │   │   │   │   ├── JwtUtil.java
│   │   │   │   │   └── JwtAuthenticationFilter.java
│   │   │   │   └── service/          # Business Logic
│   │   │   └── resources/
│   │   │       └── application.properties
│   │   └── test/
│   ├── .env.example                  # Environment template
│   ├── .gitignore
│   ├── Dockerfile                    # Backend Docker image
│   ├── pom.xml                       # Maven dependencies
│   └── README.md
│
├── frontend/                         # React Frontend
│   ├── public/
│   ├── src/
│   │   ├── components/               # Reusable components
│   │   │   └── ProtectedRoute.tsx
│   │   ├── pages/                    # Page components
│   │   │   ├── Login.tsx
│   │   │   ├── Projects.tsx
│   │   │   └── ProjectDetail.tsx
│   │   ├── api.ts                    # API service layer
│   │   ├── types.ts                  # TypeScript types
│   │   ├── App.tsx                   # Main app component
│   │   ├── main.tsx                  # Entry point
│   │   └── index.css                 # Global styles
│   ├── .env.example                  # Environment template
│   ├── .gitignore
│   ├── Dockerfile                    # Frontend Docker image
│   ├── nginx.conf                    # Nginx configuration
│   ├── package.json
│   ├── tailwind.config.js
│   ├── tsconfig.json
│   └── vite.config.ts
│
├── docker-compose.yml                # Docker Compose configuration
├── .gitignore
└── README.md                         # This file
```

---

## 🔐 Environment Variables

### **Backend (.env)**

```bash
# Database Configuration
POSTGRES_DB=taskmanagement
POSTGRES_USER=postgres
POSTGRES_PASSWORD=your_password_here

# JWT Configuration
JWT_SECRET=your_secret_key_at_least_32_characters_long
JWT_EXPIRATION=604800000

# CORS Configuration
CORS_ALLOWED_ORIGINS=http://localhost:3000

# Demo Users (for development)
DEMO_USER_EMAIL=demo@hahn.com
DEMO_USER_PASSWORD=demo123
DEMO_USER_NAME=Demo User

TEST_USER_EMAIL=test@hahn.com
TEST_USER_PASSWORD=test123
TEST_USER_NAME=Test User
```

### **Frontend (.env)**

```bash
# API Configuration
VITE_API_URL=/api
```

---

## 🔑 Demo Credentials

The application comes with pre-configured demo users:

| Email | Password | Description |
|-------|----------|-------------|
| demo@hahn.com | demo123 | Demo user account |
| test@hahn.com | test123 | Test user account |

**⚠️ Note:** These are for development only. Change passwords in production!

---


## 🧪 Testing

### **Backend Tests**

```bash
cd backend
./mvnw test
```

### **Frontend Tests**

```bash
cd frontend
npm run test
```

---

## 🛠 Development

### **Backend Development**

```bash
cd backend

# Hot reload with Spring DevTools
./mvnw spring-boot:run

# Build JAR
./mvnw clean package

# Run JAR
java -jar target/task-management-api-1.0.0.jar
```

### **Frontend Development**

```bash
cd frontend

# Development server with hot reload
npm run dev

# Build for production
npm run build

# Preview production build
npm run preview
```

---

## 🐛 Troubleshooting

### **Port Already in Use**

```bash
# Check what's using the port
# Windows:
netstat -ano | findstr :8080
netstat -ano | findstr :3000

# Linux/macOS:
lsof -i :8080
lsof -i :3000

# Kill the process
# Windows:
taskkill /PID <PID> /F

# Linux/macOS:
kill -9 <PID>
```

### **Database Connection Failed**

```bash
# Check PostgreSQL is running
# Windows:
net start postgresql-x64-16

# Verify connection
psql -U postgres -d taskmanagement
```

### **Backend Won't Start**

```bash
# Clean and rebuild
cd backend
./mvnw clean install -DskipTests
./mvnw spring-boot:run
```

### **Frontend Build Errors**

```bash
# Clear cache and reinstall
cd frontend
rm -rf node_modules package-lock.json
npm install
npm run dev
```

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 👥 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## 📧 Contact

**Abdelilah Melhaoui** - profreelance.service@gmail.com

**Project Link:** [https://github.com/AmDeano/hahn-crm-internship-2026.git]

---

## 🙏 Acknowledgments

- [Hahn Software Morocco](https://hahn-software.com/) - For the internship opportunity
- [Spring Boot](https://spring.io/projects/spring-boot) - Backend framework
- [React](https://reactjs.org/) - Frontend library
- [Tailwind CSS](https://tailwindcss.com/) - CSS framework
- [Vite](https://vitejs.dev/) - Build tool

---

**Made with ❤️ for Hahn Software Morocco**
# NutriEM Backend

Spring Boot 3.3 · Java 21 · MySQL 8 · JWT Auth

## Quick Start

### 1. Create MySQL database
```sql
CREATE DATABASE nutriem_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 2. Set your password in `src/main/resources/application.properties`
```properties
spring.datasource.password=YOUR_PASSWORD
```

### 3. Run
```bash
mvn spring-boot:run
```
App starts at → http://localhost:8080

## Test Endpoints

```bash
# Health check
curl http://localhost:8080/api/health

# Register
curl -s -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"firstName":"Laura","lastName":"Nunez","email":"laura@nutriem.com","password":"password123"}'

# Login
curl -s -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"laura@nutriem.com","password":"password123"}'
```

## Push to GitHub
```bash
git init
git add .
git commit -m "feat: initial project - models, JWT auth, security"
git remote add origin https://github.com/IngMiguelRmz/nutriEM-backend.git
git branch -M main
git push -u origin main
```

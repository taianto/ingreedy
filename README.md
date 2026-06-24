# ingreedy

## Stack
- Frontend: React + Vite
- Backend: Spring Boot + Spring Data JPA + Hibernate
- Database: PostgreSQL
- Migrations: Flyway

## Run With Docker (Recommended)
From project root:

```bash
docker compose up --build
```

Services:
- Frontend: http://localhost:3000
- Backend: http://localhost:8080
- PostgreSQL: localhost:5432

Stop services:

```bash
docker compose down
```

Stop and remove DB volume:

```bash
docker compose down -v
```

## Run Locally Without Docker
1. Start PostgreSQL and create database `ingreedy`.
2. Configure connection using environment variables (or rely on defaults):
	 - `SPRING_DATASOURCE_URL` (default: `jdbc:postgresql://localhost:5432/ingreedy`)
	 - `SPRING_DATASOURCE_USERNAME` (default: `ingreedy`)
	 - `SPRING_DATASOURCE_PASSWORD` (default: `ingreedy`)
3. Start backend:

```bash
cd backend
./mvnw spring-boot:run
```

4. Start frontend:

```bash
cd frontend
npm install
npm run dev
```

## User CRUD API
- `POST /users`
- `GET /users`
- `GET /users/{id}`
- `PUT /users/{id}`
- `DELETE /users/{id}`

Sample payload for create/update:

```json
{
	"email": "user@example.com",
	"displayName": "Alex"
}
```
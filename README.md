# Quiz Backend — Spring Boot REST API

API REST para la gestión de quizzes con preguntas y respuestas, desarrollada siguiendo una arquitectura en capas con Spring Boot y persistencia en PostgreSQL.

## Stack tecnológico

- **Java 21** · **Spring Boot 3.5.6**
- **Spring Data JPA** + **Hibernate** — relaciones `@OneToMany` / `@ManyToOne` bidireccionales
- **PostgreSQL 15**
- **Lombok** · **Jackson**
- **Docker** + **Docker Compose**
- **Testcontainers** — tests de integración con PostgreSQL real

## Arquitectura

Arquitectura en capas estándar de Spring Boot:

```
Controller  →  Service  →  Repository  →  Entity (JPA)
```

Tres recursos con sus relaciones:

```
Quiz (1) ──── (N) Pregunta (1) ──── (N) Respuesta
```

## Levantar el proyecto

```bash
cp .env.example .env
docker-compose up
```

La aplicación queda disponible en `http://localhost:8081`.

> El servicio de base de datos incluye healthcheck: la app espera a que PostgreSQL esté listo antes de arrancar.

## API Endpoints

### Quizzes `/api/quizzes`

| Método | Ruta | Descripción |
|--------|------|-------------|
| `GET` | `/api/quizzes` | Listar todos |
| `GET` | `/api/quizzes/{id}` | Obtener por ID |
| `POST` | `/api/quizzes` | Crear |
| `PUT` | `/api/quizzes/{id}` | Actualizar |
| `DELETE` | `/api/quizzes/{id}` | Eliminar |

### Preguntas `/api/preguntas`

| Método | Ruta | Descripción |
|--------|------|-------------|
| `GET` | `/api/preguntas` | Listar todas |
| `GET` | `/api/preguntas/{id}` | Obtener por ID |
| `POST` | `/api/preguntas` | Crear |
| `PUT` | `/api/preguntas/{id}` | Actualizar |
| `DELETE` | `/api/preguntas/{id}` | Eliminar |

### Respuestas `/api/respuestas`

| Método | Ruta | Descripción |
|--------|------|-------------|
| `GET` | `/api/respuestas` | Listar todas |
| `GET` | `/api/respuestas/{id}` | Obtener por ID |
| `POST` | `/api/respuestas` | Crear |
| `PUT` | `/api/respuestas/{id}` | Actualizar |
| `DELETE` | `/api/respuestas/{id}` | Eliminar |

## Variables de entorno

| Variable | Descripción | Default |
|----------|-------------|---------|
| `DB_URL` | URL de conexión JDBC | `jdbc:postgresql://localhost:5433/quiz_backend_db` |
| `DB_USER` | Usuario PostgreSQL | `postgres` |
| `DB_PASSWORD` | Contraseña PostgreSQL | — |

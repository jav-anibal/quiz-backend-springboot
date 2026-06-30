# Quiz Backend — Spring Boot REST API

API REST para la gestión de quizzes con preguntas y respuestas, construida con Spring Boot y PostgreSQL.
Diseñada para ser consumida por un cliente **Angular** como parte de un stack Java + Angular.

## Stack tecnológico

- **Java 21** · **Spring Boot 3.5.6**
- **Spring Data JPA** + **Hibernate** — relaciones `@OneToMany` / `@ManyToOne` bidireccionales
- **PostgreSQL 15**
- **Lombok** · **Jackson** · **Spring Validation**
- **Docker** + **Docker Compose** con healthcheck
- **Testcontainers** — tests de integración con PostgreSQL real

## Arquitectura

```
Controller  →  Service  →  Repository  →  Entity (JPA)
```

```
Quiz (1) ──── (N) Pregunta (1) ──── (N) Respuesta
```

## Levantar el proyecto

```bash
cp .env.example .env
mvn clean package -DskipTests
docker-compose up --build
```

Disponible en `http://localhost:8081`. PostgreSQL se levanta con healthcheck — la app espera a que la BD esté lista antes de arrancar.

## API Endpoints

### `/api/quizzes`
| Método | Ruta | Descripción |
|--------|------|-------------|
| `GET` | `/api/quizzes` | Listar todos |
| `GET` | `/api/quizzes/{id}` | Obtener por ID |
| `POST` | `/api/quizzes` | Crear |
| `PUT` | `/api/quizzes/{id}` | Actualizar |
| `DELETE` | `/api/quizzes/{id}` | Eliminar |

### `/api/preguntas`
| Método | Ruta | Descripción |
|--------|------|-------------|
| `GET` | `/api/preguntas` | Listar todas |
| `GET` | `/api/preguntas/{id}` | Obtener por ID |
| `POST` | `/api/preguntas` | Crear |
| `PUT` | `/api/preguntas/{id}` | Actualizar |
| `DELETE` | `/api/preguntas/{id}` | Eliminar |

### `/api/respuestas`
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

## Integración con Angular

El backend está preparado para ser consumido desde Angular (`http://localhost:4200`).
Próximamente: configuración CORS, Swagger/OpenAPI y endpoint de sesión de quiz.



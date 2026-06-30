# root.md — Contexto completo del proyecto

> Este documento existe para que puedas retomar el proyecto después de meses sin tocarlo.
> Lee esto antes de abrir cualquier archivo de código.

---

## ¿Qué es este proyecto?

Un **backend REST API** construido con Spring Boot para gestionar quizzes de estudio.
Permite crear quizzes con preguntas y respuestas, consultarlos y eliminarlos.

El objetivo final es que una aplicación cliente (Angular, Android, web) consuma esta API
y muestre una experiencia de quiz completa al usuario.

**No es un producto terminado. Es un proyecto de portfolio** construido para demostrar
conocimientos de backend Java a reclutadores y academias.

---

## Stack tecnológico

| Tecnología | Versión | Para qué |
|-----------|---------|---------|
| Java | 21 | Lenguaje principal |
| Spring Boot | 3.5.6 | Framework base |
| Spring Data JPA | — | Acceso a base de datos |
| Hibernate | 6.x | ORM (mapeo objetos ↔ tablas) |
| PostgreSQL | 15 | Base de datos relacional |
| Lombok | — | Eliminar boilerplate (getters, setters, constructores) |
| Jackson | — | Serialización/deserialización JSON |
| Spring Validation | — | Validación de datos de entrada |
| Spring Actuator | — | Endpoints de salud (/actuator/health) |
| Docker | — | Contenedorización del proyecto |
| Docker Compose | — | Orquestar app + base de datos juntos |
| Testcontainers | — | Tests de integración con PostgreSQL real |
| Maven | 3.9 | Gestión de dependencias y build |

---

## Arquitectura

Arquitectura en capas estándar de Spring Boot (Layered Architecture):

```
HTTP Request
     ↓
[ Controller ]   → Recibe la petición, valida con @Valid, devuelve ResponseEntity
     ↓
[   Service  ]   → Lógica de negocio (ahora delegación directa, pendiente enriquecer)
     ↓
[ Repository ]   → Interfaz JPA, Spring genera las queries automáticamente
     ↓
[   Entity   ]   → Clase Java mapeada a tabla de PostgreSQL con @Entity
     ↓
[ PostgreSQL ]   → Base de datos relacional
```

---

## Modelo de datos

Tres entidades con relaciones bidireccionales:

```
Quiz
├── id         (Integer, autoincremental)
├── titulo     (String, obligatorio)
├── categoria  (Enum: FUNDAMENTOS | OBJETOS | HERENCIA | POLIMORFISMO)
└── preguntaList → List<Pregunta>  (@OneToMany)

Pregunta
├── id         (Integer, autoincremental)
├── enunciado  (String, obligatorio)
├── quiz       → Quiz              (@ManyToOne)
└── respuestaList → List<Respuesta> (@OneToMany)

Respuesta
├── id         (Integer, autoincremental)
├── texto      (String, obligatorio)
├── opcion     (Enum: A | B | C | D, obligatorio)
├── esCorrecta (boolean)
└── pregunta   → Pregunta          (@ManyToOne)
```

Las relaciones JSON se gestionan con `@JsonManagedReference` / `@JsonBackReference`
para evitar bucles infinitos en la serialización.

---

## Endpoints disponibles

La API escucha en `http://localhost:8081` (con Docker Compose) o `http://localhost:8080` (directo).

### Quizzes
```
GET    /api/quizzes          Lista todos los quizzes
GET    /api/quizzes/{id}     Obtiene un quiz por ID
POST   /api/quizzes          Crea un nuevo quiz
PUT    /api/quizzes/{id}     Actualiza un quiz
DELETE /api/quizzes/{id}     Elimina un quiz
```

### Preguntas
```
GET    /api/preguntas
GET    /api/preguntas/{id}
POST   /api/preguntas
PUT    /api/preguntas/{id}
DELETE /api/preguntas/{id}
```

### Respuestas
```
GET    /api/respuestas
GET    /api/respuestas/{id}
POST   /api/respuestas
PUT    /api/respuestas/{id}
DELETE /api/respuestas/{id}
```

---

## Cómo levantar el proyecto

### Con Docker Compose (recomendado)

```bash
# 1. Copiar variables de entorno
cp .env.example .env

# 2. Compilar el JAR (necesario antes del build de Docker)
mvn clean package -DskipTests

# 3. Levantar todo
docker-compose up --build
```

La app espera a que PostgreSQL pase su healthcheck antes de arrancar.
Esto evita el error de "conexión rechazada" que tenía antes.

### Variables de entorno (.env)

```
DB_URL=jdbc:postgresql://db:5432/quiz_backend_db  ← dentro de Docker
DB_USER=postgres
DB_PASSWORD=admin  ← cambiar en producción
```

En desarrollo local sin Docker, la URL es `localhost:5433` (el puerto mapeado).

---

## Estructura de carpetas

```
quiz-backend-springboot/
├── src/main/java/org/javanibal/quiz/
│   ├── QuizBackendSpringbootApplication.java   ← punto de entrada
│   ├── config/
│   │   └── DataSeeder.java                     ← carga datos desde quizzes.json al arrancar
│   ├── controller/
│   │   ├── QuizController.java
│   │   ├── PreguntaController.java
│   │   └── RespuestaController.java
│   ├── service/
│   │   ├── QuizService.java
│   │   ├── PreguntaService.java
│   │   └── RespuestaService.java
│   ├── repository/
│   │   ├── QuizRepository.java
│   │   ├── PreguntaRepository.java
│   │   └── RespuestaRepository.java
│   ├── model/
│   │   ├── Quiz.java
│   │   ├── Pregunta.java
│   │   └── Respuesta.java
│   ├── enums/
│   │   ├── Categoria.java
│   │   └── Opcion.java
│   └── exception/
│       └── GlobalExceptionHandler.java         ← manejo global de errores HTTP
├── src/main/resources/
│   ├── application.properties                  ← configuración con variables de entorno
│   └── data/
│       └── quizzes.json                        ← datos iniciales (seed)
├── src/test/
│   └── QuizBackendSpringbootApplicationTests   ← test de contexto con Testcontainers
├── dockerfile                                  ← imagen de la app
├── docker-compose.yml                          ← app + PostgreSQL
├── .env                                        ← credenciales locales (no commitear)
├── .env.example                                ← plantilla para nuevos desarrolladores
└── docs/
    ├── root.md                                 ← este archivo
    └── _visual/
        └── home.png                            ← diseño visual del producto objetivo
```

---

## Estado actual del proyecto

### Lo que funciona
- CRUD completo para Quiz, Pregunta y Respuesta
- Relaciones JPA bidireccionales funcionando
- Validaciones de entrada (`@Valid`, `@NotBlank`, `@NotNull`)
- Manejo global de errores con respuestas JSON estructuradas
- Docker Compose levanta app + BD con healthcheck
- Variables de entorno — sin credenciales hardcodeadas
- DataSeeder carga preguntas de Java desde `quizzes.json` al arrancar

### Lo que falta (deuda técnica)

| Funcionalidad | Prioridad | Notas |
|--------------|-----------|-------|
| Sesión de quiz (responder preguntas, calcular score) | Alta | Sin esto no hay "producto" |
| DTOs (separar entidad de respuesta API) | Alta | Ahora se expone la entidad directamente |
| Documentación Swagger/OpenAPI | Alta | Necesario para mostrar la API en entrevistas |
| CORS configurado para Angular | Alta | Próximo paso inmediato |
| Autenticación (Spring Security + JWT) | Media | Necesaria para multi-usuario |
| Progreso del usuario por quiz | Media | Requiere auth primero |
| Paginación en listados | Baja | `findAll()` sin límite |
| Tests reales (unitarios e integración) | Media | Solo existe `contextLoads()` |

---

## Próximo paso: preparar para Angular

El mercado laboral español en 2025 pide **Java + Spring Boot + Angular** como stack
estándar para perfiles junior. Angular aparece en el 70% de las ofertas combinadas con Java.

Para que un frontend Angular pueda consumir este backend necesitamos:

1. **Configurar CORS** — permitir peticiones desde `http://localhost:4200` (puerto de Angular)
2. **Añadir Swagger/OpenAPI** — documentación interactiva de la API
3. **Revisar respuestas JSON** — asegurar consistencia en todos los endpoints
4. **Endpoint de sesión** — poder "jugar" un quiz desde el frontend

---

## Contexto de mercado

Investigación realizada en junio 2025 sobre ofertas de trabajo en España:

- Las ofertas junior de Java **casi siempre son fullstack** (Java + Angular)
- Angular aparece en ~70% de las ofertas, React en ~25%
- Consultoras como Indra, Sopra, Serem, Grupo Digital contratan con Java + Angular
- Backend puro es para perfiles mid/senior con 3-5 años de experiencia
- **Conclusión:** Angular es la inversión más rentable de tiempo para conseguir trabajo junior en España

---

## Ramas del proyecto

| Rama | Estado | Contenido |
|------|--------|-----------|
| `main` | Producción | Estado estable del proyecto |
| `develop` | Integración | Acumula features antes de ir a main |
| `feat/mvn-test` | Mergeada | Docker, .env, corrección arranque |
| `feat/validations-exception-handling` | Mergeada | Validaciones + GlobalExceptionHandler |
| `feat/angular-setup` | En curso | Preparación del backend para Angular |

---

*Última actualización: junio 2025*

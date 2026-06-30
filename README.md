# Quiz Backend - Spring Boot

Aplicación backend para gestionar quizzes construida con Spring Boot 3.5.6 y PostgreSQL.

## Requisitos

- Java 21
- Maven 3.9+
- Docker & Docker Compose
- PostgreSQL 15 (para desarrollo local sin Docker)

## Configuración de Ambiente

### Variables de Entorno

El proyecto usa variables de entorno para la configuración:

- `DB_NAME`: Nombre de la base de datos (default: `quiz_backend_db`)
- `DB_USER`: Usuario de PostgreSQL (default: `postgres`)
- `DB_PASSWORD`: Contraseña de PostgreSQL (default: `admin`)

### Desarrollo Local

1. Copiar `.env.example` a `.env`:
```bash
cp .env.example .env
```

2. Actualizar valores en `.env` según tu ambiente

3. Compilar el proyecto:
```bash
mvn clean package -DskipTests
```

## Ejecutar con Docker Compose

La forma más fácil es usar `docker-compose`, que levanta tanto la BD como la aplicación:

```bash
docker-compose up
```

Esto:
- Levanta PostgreSQL en `localhost:5433`
- Levanta la aplicación en `localhost:8081`
- Lee las credenciales desde `.env`

Para detener:
```bash
docker-compose down
```

## Ejecutar con Docker (manual)

### Construcción de imagen

```bash
docker build -t quiz-backend:latest .
```

### Ejecutar contenedor

Con archivo `.env`:
```bash
docker run --env-file .env -p 8080:8080 quiz-backend:latest
```

Con variables individuales:
```bash
docker run \
  -e DB_NAME=quiz_backend_db \
  -e DB_USER=postgres \
  -e DB_PASSWORD=admin \
  -p 8080:8080 \
  quiz-backend:latest
```

## API Endpoints

La aplicación se ejecuta en `http://localhost:8081` (docker-compose) o `http://localhost:8080` (docker run)

### Quizzes
- `GET /api/quizzes` - Obtener todos los quizzes
- `GET /api/quizzes/{id}` - Obtener quiz por ID
- `POST /api/quizzes` - Crear nuevo quiz
- `PUT /api/quizzes/{id}` - Actualizar quiz
- `DELETE /api/quizzes/{id}` - Eliminar quiz

## Estructura del Proyecto

```
src/
  main/
    java/org/javanibal/quiz/
      config/          - Configuración
      controller/      - REST Controllers
      model/           - Entidades JPA
      repository/      - Data Access
      service/         - Lógica de negocio
      enums/           - Enumeraciones
    resources/
      application.properties
  test/
    java/org/javanibal/quiz/
```

## Notas de Seguridad

- El archivo `.env` contiene credenciales locales y NO debe commiterse (está en `.gitignore`)
- Usar `.env.example` como referencia para nuevas configuraciones
- Para producción, usar variables de entorno seguras del platform de deployment (Heroku, AWS, etc.)
- Las credenciales por defecto (`admin`) son SOLO para desarrollo

## Troubleshooting

### Puerto 5433 ya está en uso
```bash
docker-compose down  # Detener todos los contenedores
# o cambiar puerto en docker-compose.yml
```

### Conexión rechazada a la BD
- Verificar que PostgreSQL esté corriendo: `docker ps`
- Verificar credenciales en `.env`
- Esperar a que la BD inicie (puede tomar 5-10 segundos)

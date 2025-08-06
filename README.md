# ForoHub - API REST con Spring Boot

ForoHub es una API RESTful desarrollada con Spring Boot, Spring Security y JWT para la gestión de tópicos, usuarios, cursos y respuestas en un foro académico.

## Características principales
- CRUD de tópicos, usuarios, cursos y respuestas
- Autenticación con JWT (token)
- Validación de datos y reglas de negocio
- Migraciones automáticas con Flyway
- Datos dummy para pruebas iniciales

## Requisitos
- Java 21
- Maven 3.8+
- MySQL 8+

## Instalación y ejecución
1. Clona el repositorio y entra al directorio del proyecto.
2. Configura tu base de datos en `src/main/resources/application.properties`.
3. Ejecuta las migraciones y dependencias:
   ```bash
   ./mvnw clean install
   # o en Windows
   mvnw.cmd clean install
   ```
4. Inicia la aplicación:
   ```bash
   ./mvnw spring-boot:run
   # o en Windows
   mvnw.cmd spring-boot:run
   ```

## Endpoints principales

### Autenticación
- `POST /auth` - Devuelve un token JWT
  ```json
  {
    "correoElectronico": "juan@email.com",
    "contrasena": "123456"
  }
  ```

### Tópicos
- `GET /topicos` - Listar tópicos (paginado)
- `GET /topicos/{id}` - Detalle de tópico
- `POST /topicos` - Crear tópico (requiere JWT)
- `PUT /topicos/{id}` - Actualizar tópico (requiere JWT)
- `DELETE /topicos/{id}` - Eliminar tópico (requiere JWT)

### Ejemplo de uso con cURL
1. Obtener token:
   ```bash
   curl -X POST http://localhost:8080/auth \
     -H "Content-Type: application/json" \
     -d '{"correoElectronico": "juan@email.com", "contrasena": "123456"}'
   ```
2. Usar token en endpoints protegidos:
   ```bash
   curl -X POST http://localhost:8080/topicos \
     -H "Content-Type: application/json" \
     -H "Authorization: Bearer TU_TOKEN_AQUI" \
     -d '{"titulo": "Nuevo tópico", "mensaje": "Mensaje", "autorId": 1, "cursoId": 1}'
   ```

## Estructura de la base de datos
- Perfiles, usuarios, cursos, tópicos y respuestas
- Relaciones y restricciones según el diagrama entidad-relación
- Migraciones en `src/main/resources/db/migration`

## Seguridad
- Autenticación JWT
- PasswordEncoder configurado (usa texto plano solo para pruebas, en producción usa BCrypt)

## Licencia
MIT

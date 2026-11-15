# usuarios-service

Microservicio de **Autenticación y Usuarios** del proyecto SiniestroFácil (Evaluación Parcial 2, JVY0101).
Gestiona usuarios y roles, guarda las contraseñas con BCrypt y entrega un token JWT al hacer login.

**Stack:** Java 17 · Spring Boot 3.4.4 · Spring Data JPA (Hibernate) · MySQL · Maven · JWT (jjwt 0.12.5)

## Arquitectura

```
controller  →  service  →  repository  →  MySQL (BD usuarios_db)
   (REST)      (negocio)    (Spring Data)
              dto / model / exception / config
```

Relación JPA: un `Rol` tiene muchos `Usuario` (`@OneToMany`) y cada `Usuario` pertenece a un `Rol` (`@ManyToOne`).

## Requisitos

- JDK 17 o superior
- Maven 3.9+
- MySQL 8 corriendo en `localhost:3306`

## Cómo ejecutarlo

```bash
git clone <URL_DEL_REPOSITORIO>
cd usuarios-service
```

1. Revisa `src/main/resources/application.properties`. Por defecto usa usuario `root` y contraseña `root`.
   Si tu MySQL tiene otras credenciales, puedes cambiarlas sin editar el archivo:

   ```bash
   # Linux / macOS
   export DB_USER=mi_usuario
   export DB_PASSWORD=mi_clave

   # Windows (PowerShell)
   $env:DB_USER="mi_usuario"
   $env:DB_PASSWORD="mi_clave"
   ```

2. La base `usuarios_db` y las tablas se crean solas al iniciar. Los roles `ADMIN`, `ASEGURADO` y `TALLER` se cargan automáticamente.

3. Compilar, ejecutar pruebas y empaquetar:

   ```bash
   mvn clean install
   mvn package
   ```

4. Ejecutar:

   ```bash
   java -jar target/usuarios-service-1.0.0.jar
   ```

   o directamente con Maven: `mvn spring-boot:run`

El servicio queda en **http://localhost:8081**.

## Endpoints

| Método | URL | Descripción | Respuestas |
|--------|-----|-------------|------------|
| GET | `/api/roles` | Listar roles | 200 |
| GET | `/api/roles/{id}` | Obtener un rol | 200, 404 |
| POST | `/api/roles` | Crear rol | 201, 400, 409 |
| PUT | `/api/roles/{id}` | Actualizar rol | 200, 400, 404, 409 |
| DELETE | `/api/roles/{id}` | Eliminar rol (si no tiene usuarios) | 204, 404, 409 |
| GET | `/api/usuarios` | Listar usuarios (opcional `?rolId=2`) | 200 |
| GET | `/api/usuarios/{id}` | Obtener un usuario | 200, 404 |
| POST | `/api/usuarios` | Crear usuario | 201, 400, 404, 409 |
| PUT | `/api/usuarios/{id}` | Actualizar usuario | 200, 400, 404, 409 |
| DELETE | `/api/usuarios/{id}` | Eliminar usuario | 204, 404 |
| POST | `/api/auth/login` | Login, devuelve token JWT | 200, 400, 401 |

### Ejemplos para Postman

Crear usuario (`POST /api/usuarios`, body JSON):

```json
{
  "nombre": "Ana",
  "apellido": "Soto",
  "email": "ana@correo.cl",
  "password": "123456",
  "rolId": 2
}
```

Login (`POST /api/auth/login`, body JSON):

```json
{
  "email": "ana@correo.cl",
  "password": "123456"
}
```

Errores de ejemplo: email repetido → `409`, email con formato inválido → `400` (con detalle por campo), usuario inexistente → `404`, contraseña incorrecta → `401`.

## Flujo de Git

```bash
git init
git checkout -b main
git add .
git commit -m "chore: estructura inicial del microservicio de usuarios"
git checkout -b develop
git checkout -b feature/crud-usuarios
# ...trabajar y hacer commits descriptivos...
git checkout develop
git merge feature/crud-usuarios
git remote add origin <URL_DEL_REPOSITORIO>
git push -u origin main develop feature/crud-usuarios
```

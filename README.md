usuarios-service

Microservicio de Autenticación y Usuarios del proyecto SiniestroFácil (Evaluación Parcial 2, JVY0101).

Gestiona usuarios y roles, almacena las contraseñas utilizando BCrypt y entrega un token JWT al iniciar sesión.

Stack: Java 17 · Spring Boot 3.4.4 · Spring Data JPA (Hibernate) · MySQL · Maven · JWT (jjwt 0.12.5)

Arquitectura

controller  →  service  →  repository  →  MySQL (BD usuarios_db)
   (REST)      (negocio)    (Spring Data)
              dto / model / exception / config

Relación JPA: un Rol tiene muchos Usuario (@OneToMany) y cada Usuario pertenece a un Rol (@ManyToOne).

Requisitos

JDK 17 o superior

Maven 3.9+

MySQL 8 en ejecución en localhost:3306

Cómo ejecutarlo

Clonar el repositorio:

git clone https://github.com/g4t4/usuarios-service.git
cd usuarios-service

1. Configuración de MySQL

Revisa el archivo src/main/resources/application.properties y configura el usuario y la contraseña de MySQL según tus credenciales locales.

No publiques contraseñas reales en GitHub.

Si el proyecto utiliza las variables de entorno DB_USER y DB_PASSWORD, puedes configurarlas así:

Windows (PowerShell):

$env:DB_USER="root"
$env:DB_PASSWORD="tu_clave_local"

Linux / macOS:

export DB_USER=mi_usuario
export DB_PASSWORD=mi_clave

2. Base de datos

La aplicación utiliza la base de datos usuarios_db. La creación de la base de datos, tablas y carga inicial de roles depende de la configuración y del código de inicialización del proyecto.

3. Compilar y empaquetar

Desde la carpeta del proyecto, ejecuta:

mvn clean install
mvn package

4. Ejecutar

Puedes iniciar la aplicación directamente con Maven:

mvn spring-boot:run

O ejecutar el archivo JAR generado dentro de la carpeta target:

java -jar target/nombre-del-archivo.jar

El servicio está configurado para ejecutarse en:

http://localhost:8081

Endpoints

Método

URL

Descripción

Respuestas

GET

/api/roles

Listar roles

200

GET

/api/roles/{id}

Obtener un rol

200, 404

POST

/api/roles

Crear rol

201, 400, 409

PUT

/api/roles/{id}

Actualizar rol

200, 400, 404, 409

DELETE

/api/roles/{id}

Eliminar rol (si no tiene usuarios)

204, 404, 409

GET

/api/usuarios

Listar usuarios (opcional ?rolId=2)

200

GET

/api/usuarios/{id}

Obtener un usuario

200, 404

POST

/api/usuarios

Crear usuario

201, 400, 404, 409

PUT

/api/usuarios/{id}

Actualizar usuario

200, 400, 404, 409

DELETE

/api/usuarios/{id}

Eliminar usuario

204, 404

POST

/api/auth/login

Iniciar sesión y obtener token JWT

200, 400, 401

Ejemplos para Postman

Crear usuario

POST /api/usuarios

Body JSON:

{
  "nombre": "Ana",
  "apellido": "Soto",
  "email": "ana@correo.cl",
  "password": "123456",
  "rolId": 2
}

Iniciar sesión

POST /api/auth/login

Body JSON:

{
  "email": "ana@correo.cl",
  "password": "123456"
}

Ejemplos de errores

Email repetido: 409 Conflict.

Email con formato inválido: 400 Bad Request.

Usuario inexistente: 404 Not Found.

Contraseña incorrecta: 401 Unauthorized.

Flujo de Git

El proyecto utiliza las ramas main, develop y feature/usuarios-roles.

Ejemplo de comandos:

git checkout main
git checkout -b develop
git checkout -b feature/usuarios-roles

# Agregar cambios y realizar commits descriptivos
git add .
git commit -m "feat: implementar gestion de usuarios y roles"

git checkout develop
git merge feature/usuarios-roles

Repositorio

https://github.com/g4t4/usuarios-service

# Manual de Instalación - AlMaximo API

## Requisitos Previos
1. **Java JDK 17** o superior instalado.
2. **Maven** instalado (o usar wrapper).
3. **MySQL Server** instalado y ejecutándose.
4. **Tomcat** o **Glassfish** (opcional si se usa el servidor embebido de Spring Boot, pero requerido para despliegue WAR).

## Pasos de Instalación

### 1. Base de Datos
1. Abra su cliente de MySQL (Workbench, CLI, etc.).
2. Ejecute el script `db_script.sql` ubicado en la raíz del proyecto.
   - Esto creará la base de datos `almaximo_db`.
   - Creará las tablas necesarias.
   - Creará los Procedimientos Almacenados.
   - Insertará datos de prueba.

### 2. Configuración
1. Abra el archivo `src/main/resources/application.properties`.
2. Verifique o modifique las credenciales de base de datos:
   ```properties
   spring.datasource.username=root
   spring.datasource.password=SU_PASSWORD
   ```

### 3. Compilación y Empaquetado
1. Abra una terminal en la carpeta `AlMaximoApi`.
2. Ejecute el comando:
   ```bash
   mvn clean package
   ```
3. Esto generará un archivo `.war` en la carpeta `target/`, por ejemplo `almaximo-api-0.0.1-SNAPSHOT.war`.

### 4. Despliegue (Opción Servidor Externo)
1. Copie el archivo `.war` generado a la carpeta `webapps` de su Tomcat o `autodeploy` de Glassfish.
2. Inicie el servidor.
3. La aplicación estará disponible en `http://localhost:8080/almaximo-api-0.0.1-SNAPSHOT/` (o el nombre que le asigne al war).

### 5. Ejecución Local (Opción Desarrollo)
1. Puede ejecutar directamente con Maven:
   ```bash
   mvn spring-boot:run
   ```
2. Acceda a `http://localhost:8080/` para ver la interfaz de usuario.

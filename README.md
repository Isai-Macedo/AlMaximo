# Proyecto AlMaximo - Sistema de Gestión de Productos

Este directorio contiene la solución completa para la Práctica Web-Java de AlMaximo Consultoría TI.

## Estructura de Carpetas

- **AlMaximoApi/**: Contiene el código fuente del proyecto (Backend Java Spring Boot + Frontend integrado).
  - `src/main/java`: Código fuente Java (Controladores, Servicios, Modelos).
  - `src/main/resources`: Configuración y archivos estáticos del Frontend (HTML, CSS, JS).
  - `db_script.sql`: Script SQL completo para crear la base de datos, tablas y procedimientos almacenados.
  - `MANUAL_INSTALACION.md`: Instrucciones detalladas para instalar y ejecutar.
  - `MANUAL_USUARIO.md`: Guía de uso de la aplicación.
  - `pom.xml`: Archivo de gestión de dependencias Maven.

- **Proyecto_AlMaximo/**: (Carpeta original) Contiene los archivos base de referencia proporcionados inicialmente.

## Pasos Rápidos para Iniciar

1. **Base de Datos**: 
   - Asegúrese de tener MySQL ejecutándose.
   - Ejecute el script `AlMaximoApi/db_script.sql` en su cliente MySQL.

2. **Ejecución**:
   - Necesita tener Java 17 y Maven instalados.
   - Entre a la carpeta `AlMaximoApi`.
   - Ejecute `mvn spring-boot:run` en su terminal.
   - O use el archivo `run_dev.bat` incluido (si tiene Maven en su PATH).

3. **Acceso**:
   - Abra `http://localhost:8080/` en su navegador.

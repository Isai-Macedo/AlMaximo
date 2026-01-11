@echo off
echo ==========================================
echo Iniciando AlMaximo API (Modo Desarrollo)
echo ==========================================
echo.
echo Verificando instalacion de Maven...
call mvn -version >nul 2>&1
if %errorlevel% neq 0 (
    echo [ERROR] No se encontro 'mvn' en el PATH.
    echo Por favor instale Apache Maven y agreguelo a sus variables de entorno.
    echo O asegurese de tener el Java Development Kit (JDK) correctamente configurado.
    pause
    exit /b
)

echo Compilando y Ejecutando con Spring Boot...
call mvn spring-boot:run

if %errorlevel% neq 0 (
    echo.
    echo [ERROR] Ocurrio un error al ejecutar la aplicacion.
    pause
)

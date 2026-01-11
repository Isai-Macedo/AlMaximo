-- Creación de la Base de Datos
CREATE DATABASE IF NOT EXISTS almaximo_db;
USE almaximo_db;

-- Tabla: Tipos de Producto
CREATE TABLE IF NOT EXISTS tipos_producto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255)
);

-- Tabla: Proveedores
CREATE TABLE IF NOT EXISTS proveedores (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255)
);

-- Tabla: Productos
-- "Clave" es la que maneja internamente nuestra organización.
-- Usaremos un VARCHAR para la clave, aunque podría ser INT. Asumiré VARCHAR por flexibilidad.
CREATE TABLE IF NOT EXISTS productos (
    clave VARCHAR(50) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    precio DECIMAL(10, 2) NOT NULL,
    id_tipo_producto INT,
    FOREIGN KEY (id_tipo_producto) REFERENCES tipos_producto(id)
);

-- Tabla: Relación Producto - Proveedor
-- Relaciona productos con proveedores, con costo y clave específica del proveedor.
CREATE TABLE IF NOT EXISTS producto_proveedores (
    id INT AUTO_INCREMENT PRIMARY KEY,
    clave_producto VARCHAR(50),
    id_proveedor INT,
    costo DECIMAL(10, 2) NOT NULL,
    clave_proveedor VARCHAR(50) NOT NULL, -- Clave que usa el proveedor para este producto
    FOREIGN KEY (clave_producto) REFERENCES productos(clave) ON DELETE CASCADE,
    FOREIGN KEY (id_proveedor) REFERENCES proveedores(id)
);

-- Insertar Datos de Prueba - Tipos de Producto
INSERT INTO tipos_producto (nombre, descripcion) VALUES 
('Electrónica', 'Dispositivos electrónicos y gadgets'),
('Hogar', 'Artículos para el hogar y decoración'),
('Alimentos', 'Comestibles y bebidas');

-- Insertar Datos de Prueba - Proveedores
INSERT INTO proveedores (nombre, descripcion) VALUES 
('Samsung', 'Proveedor de electrónica'),
('Sony', 'Proveedor de electrónica y entretenimiento'),
('Nestlé', 'Proveedor de alimentos'),
('IKEA', 'Muebles y hogar');

-- PROCEDIMIENTOS ALMACENADOS

DELIMITER //

-- 1. Listar Productos (con filtros opcionales)
CREATE PROCEDURE sp_ListarProductos(
    IN p_clave VARCHAR(50),
    IN p_id_tipo INT
)
BEGIN
    SELECT p.clave, p.nombre, p.precio, p.id_tipo_producto, tp.nombre as nombre_tipo
    FROM productos p
    LEFT JOIN tipos_producto tp ON p.id_tipo_producto = tp.id
    WHERE (p_clave IS NULL OR p_clave = '' OR p.clave LIKE CONCAT('%', p_clave, '%'))
      AND (p_id_tipo IS NULL OR p_id_tipo = 0 OR p.id_tipo_producto = p_id_tipo);
END //

-- 2. Obtener Producto por Clave (Detalle)
CREATE PROCEDURE sp_ObtenerProducto(
    IN p_clave VARCHAR(50)
)
BEGIN
    SELECT p.clave, p.nombre, p.precio, p.id_tipo_producto, tp.nombre as nombre_tipo
    FROM productos p
    LEFT JOIN tipos_producto tp ON p.id_tipo_producto = tp.id
    WHERE p.clave = p_clave;
END //

-- 3. Insertar Producto
CREATE PROCEDURE sp_InsertarProducto(
    IN p_clave VARCHAR(50),
    IN p_nombre VARCHAR(100),
    IN p_precio DECIMAL(10, 2),
    IN p_id_tipo INT
)
BEGIN
    INSERT INTO productos (clave, nombre, precio, id_tipo_producto)
    VALUES (p_clave, p_nombre, p_precio, p_id_tipo);
END //

-- 4. Actualizar Producto
CREATE PROCEDURE sp_ActualizarProducto(
    IN p_clave VARCHAR(50),
    IN p_nombre VARCHAR(100),
    IN p_precio DECIMAL(10, 2),
    IN p_id_tipo INT
)
BEGIN
    UPDATE productos
    SET nombre = p_nombre,
        precio = p_precio,
        id_tipo_producto = p_id_tipo
    WHERE clave = p_clave;
END //

-- 5. Eliminar Producto
CREATE PROCEDURE sp_EliminarProducto(
    IN p_clave VARCHAR(50)
)
BEGIN
    DELETE FROM productos WHERE clave = p_clave;
END //

-- 6. Listar Tipos de Producto (Para llenar combos)
CREATE PROCEDURE sp_ListarTiposProducto()
BEGIN
    SELECT id, nombre, descripcion FROM tipos_producto;
END //

-- 7. Listar Proveedores (Para llenar combos y modales)
CREATE PROCEDURE sp_ListarProveedores()
BEGIN
    SELECT id, nombre, descripcion FROM proveedores;
END //

-- 8. Asignar Proveedor a Producto
CREATE PROCEDURE sp_AsignarProveedorProducto(
    IN p_clave_producto VARCHAR(50),
    IN p_id_proveedor INT,
    IN p_costo DECIMAL(10, 2),
    IN p_clave_proveedor VARCHAR(50)
)
BEGIN
    INSERT INTO producto_proveedores (clave_producto, id_proveedor, costo, clave_proveedor)
    VALUES (p_clave_producto, p_id_proveedor, p_costo, p_clave_proveedor);
END //

-- 9. Eliminar Proveedores de un Producto (Para limpiar antes de re-asignar o al borrar)
-- Nota: La FK tiene ON DELETE CASCADE, así que al borrar producto se borran sus relaciones.
-- Pero esto es útil para el modo edición si queremos reemplazar la lista.
CREATE PROCEDURE sp_EliminarProveedoresProducto(
    IN p_clave_producto VARCHAR(50)
)
BEGIN
    DELETE FROM producto_proveedores WHERE clave_producto = p_clave_producto;
END //

-- 10. Obtener Proveedores de un Producto
CREATE PROCEDURE sp_ObtenerProveedoresProducto(
    IN p_clave_producto VARCHAR(50)
)
BEGIN
    SELECT pp.id, pp.clave_producto, pp.id_proveedor, pp.costo, pp.clave_proveedor, prov.nombre as nombre_proveedor
    FROM producto_proveedores pp
    INNER JOIN proveedores prov ON pp.id_proveedor = prov.id
    WHERE pp.clave_producto = p_clave_producto;
END //

DELIMITER ;

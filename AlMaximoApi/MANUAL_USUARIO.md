# Manual de Usuario - Gestión de Productos AlMaximo

## Acceso
Ingrese a la aplicación a través de su navegador web (ej. `http://localhost:8080/`).

## Funcionalidades

### 1. Listado de Productos
- Al ingresar, verá la lista de productos existentes.
- **Buscar**: Puede filtrar por "Clave" (texto parcial) o por "Tipo de Producto" usando los controles superiores y haciendo clic en "Buscar".
- **Agregar Nuevo**: Botón verde para crear un nuevo producto.
- **Acciones**: En cada fila de producto existen botones para "Editar" (modificar) o "Eliminar" (borrar).

### 2. Agregar / Editar Producto
Al seleccionar "Agregar Nuevo" o "Editar", la pantalla cambiará al modo de edición.

- **Datos Generales**: Ingrese o modifique la Clave, Nombre, Precio y Tipo.
  - *Nota*: La Clave no es editable una vez creado el producto.
- **Proveedores Asignados**:
  - Se muestra una tabla con los proveedores que surten este producto.
  - **Agregar Proveedor**: Abre una ventana modal para vincular un nuevo proveedor.
  - **Eliminar (X)**: Quita la asignación del proveedor para este producto.
- **Guardar**: Guarda todos los cambios (datos del producto y lista de proveedores) en la base de datos.
- **Cancelar**: Regresa al listado sin guardar cambios.

### 3. Asignar Proveedor (Modal)
Desde la pantalla de edición, al hacer clic en "Agregar Proveedor":
1. Seleccione el **Proveedor** de la lista desplegable.
2. Ingrese la **Clave del Proveedor** (código que usa el proveedor para el producto).
3. Ingrese el **Costo** de adquisición.
4. Haga clic en "Agregar" para añadirlo a la lista temporal (debe dar clic en Guardar en la pantalla principal para persistir los cambios).

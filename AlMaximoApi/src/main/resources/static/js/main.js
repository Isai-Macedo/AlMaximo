const API_URL = 'api';
let proveedoresTemporales = [];
let isEditing = false;

document.addEventListener('DOMContentLoaded', () => {
    mostrarModoEdicion(false); // Asegurar que iniciamos en modo listado
    cargarCatalogos();
    buscarProductos();
});

async function cargarCatalogos() {
    try {
        const tiposRes = await fetch(`${API_URL}/catalogos/tipos-producto`);
        const tipos = await tiposRes.json();
        
        const provRes = await fetch(`${API_URL}/catalogos/proveedores`);
        const proveedores = await provRes.json();

        // Llenar selects de Tipos
        const filtroTipo = document.getElementById('filtro-tipo');
        const prodTipo = document.getElementById('prod-tipo');
        
        tipos.forEach(t => {
            filtroTipo.innerHTML += `<option value="${t.id}">${t.nombre}</option>`;
            prodTipo.innerHTML += `<option value="${t.id}">${t.nombre}</option>`;
        });

        // Llenar select de Proveedores en Modal
        const modalProvSelect = document.getElementById('modal-prov-select');
        proveedores.forEach(p => {
            modalProvSelect.innerHTML += `<option value="${p.id}">${p.nombre}</option>`;
        });

    } catch (error) {
        console.error('Error cargando catálogos', error);
    }
}

async function buscarProductos() {
    const clave = document.getElementById('filtro-clave').value;
    const tipo = document.getElementById('filtro-tipo').value;
    
    let url = `${API_URL}/productos?`;
    if(clave) url += `clave=${clave}&`;
    if(tipo) url += `idTipo=${tipo}`;

    try {
        const res = await fetch(url);
        const productos = await res.json();
        renderTablaProductos(productos);
    } catch (error) {
        console.error('Error buscando productos', error);
    }
}

function renderTablaProductos(productos) {
    const tbody = document.querySelector('#tabla-productos tbody');
    tbody.innerHTML = '';
    productos.forEach(p => {
        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td>${p.clave}</td>
            <td>${p.nombre}</td>
            <td>$${p.precio}</td>
            <td>${p.nombreTipo || ''}</td>
            <td>
                <button type="button" class="btn btn-secondary" onclick="editarProducto('${p.clave}')">Editar</button>
                <button type="button" class="btn btn-danger" onclick="eliminarProducto('${p.clave}')">Eliminar</button>
            </td>
        `;
        tbody.appendChild(tr);
    });
}

function nuevoProducto() {
    mensajeAlerta('success', 'Hola');
    isEditing = false;
    document.getElementById('titulo-edicion').innerText = 'Nuevo Producto';
    document.getElementById('form-producto').reset();
    document.getElementById('prod-clave').disabled = false;
    proveedoresTemporales = [];
    renderProveedoresTemporales();
    mostrarModoEdicion(true);
}

async function editarProducto(clave) {
    isEditing = true;
    document.getElementById('titulo-edicion').innerText = 'Editar Producto';
    document.getElementById('prod-clave').disabled = true;
    
    try {
        const res = await fetch(`${API_URL}/productos/${clave}`);
        if (!res.ok) throw new Error('No encontrado');
        const p = await res.json();
        
        document.getElementById('prod-clave').value = p.clave;
        document.getElementById('prod-nombre').value = p.nombre;
        document.getElementById('prod-precio').value = p.precio;
        document.getElementById('prod-tipo').value = p.idTipoProducto;
        
        proveedoresTemporales = p.proveedores || [];
        renderProveedoresTemporales();
        
        mostrarModoEdicion(true);
    } catch (error) {
        alert('Error cargando producto: ' + error.message);
    }
}

async function eliminarProducto(clave) {
    if(!confirm('¿Estás seguro de eliminar este producto?')) return;
    
    try {
        const res = await fetch(`${API_URL}/productos/${clave}`, { method: 'DELETE' });
        if(res.ok) {
            buscarProductos();
        } else {
            //mensajeAlerta('success', 'Error al eliminar');
            alert('Error al eliminar');
        }
    } catch (error) {
        console.error(error);
    }
}

function mostrarModoEdicion(mostrar) {
    if(mostrar) {
        document.getElementById('modo-listado').classList.add('hidden');
        document.getElementById('modo-edicion').classList.remove('hidden');
    } else {
        document.getElementById('modo-listado').classList.remove('hidden');
        document.getElementById('modo-edicion').classList.add('hidden');
    }
}

function cancelarEdicion() {
    mostrarModoEdicion(false);
}

// Lógica de Proveedores en Edición
function abrirModalProveedor() {
    document.getElementById('modal-proveedor').classList.remove('hidden');
}

function cerrarModalProveedor() {
    document.getElementById('modal-proveedor').classList.add('hidden');
    document.getElementById('modal-prov-clave').value = '';
    document.getElementById('modal-prov-costo').value = '';
}

function agregarProveedorLista() {
    const select = document.getElementById('modal-prov-select');
    const idProv = parseInt(select.value);
    const nombreProv = select.options[select.selectedIndex].text;
    const claveProv = document.getElementById('modal-prov-clave').value;
    const costo = parseFloat(document.getElementById('modal-prov-costo').value);

    //mensajeAlerta('success', 'Completa los campos');

    if(!claveProv || !costo) {
        //mensajeAlerta('success', 'Completa los campos');
        alert('Completa los campos');
        return;
    }

    // Agregar a temporal
    proveedoresTemporales.push({
        idProveedor: idProv,
        nombreProveedor: nombreProv,
        claveProveedor: claveProv,
        costo: costo
    });

    renderProveedoresTemporales();
    cerrarModalProveedor();
}

function eliminarProveedorLista(index) {
    if(!confirm('¿Estás seguro de eliminar el proveedor?')) return;

    proveedoresTemporales.splice(index, 1);
    renderProveedoresTemporales();
}

function renderProveedoresTemporales() {
    const tbody = document.querySelector('#tabla-prod-proveedores tbody');
    tbody.innerHTML = '';
    proveedoresTemporales.forEach((pp, index) => {
        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td>${pp.nombreProveedor || 'ID: ' + pp.idProveedor}</td>
            <td>${pp.claveProveedor}</td>
            <td>$${pp.costo}</td>
            <td>
                <button type="button" class="btn btn-danger" onclick="eliminarProveedorLista(${index})">Eliminar</button>
            </td>
        `;
        tbody.appendChild(tr);
    });
}

async function guardarProducto() {
    const p = {
        clave: document.getElementById('prod-clave').value,
        nombre: document.getElementById('prod-nombre').value,
        precio: parseFloat(document.getElementById('prod-precio').value),
        idTipoProducto: parseInt(document.getElementById('prod-tipo').value),
        proveedores: proveedoresTemporales
    };

    const method = isEditing ? 'PUT' : 'POST';
    const url = isEditing ? `${API_URL}/productos/${p.clave}` : `${API_URL}/productos`;

    try {
        const res = await fetch(url, {
            method: method,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(p)
        });

        if(res.ok) {
            //mensajeAlerta('success', 'Guardado correctamente');
            alert('Guardado correctamente');
            cancelarEdicion();
            buscarProductos();
        } else {
            //mensajeAlerta('success', 'Error al guardar');
            alert('Error al guardar');
        }
    } catch (error) {
        console.error(error);
        //mensajeAlerta('success', 'Error de conexión');
        alert('Error de conexión');
    }
}

function mensajeAlerta(tipo, mensaje) {
    const alerta = document.createElement("div");

    const tipoBootstrap = tipo === 'success' ? 'success' : 'danger';
    const titulo = tipo === 'success' ? 'Éxito' : 'Error';

    alerta.className = `alert alert-${tipoBootstrap}`;
    alerta.innerHTML = `
        <span class="close-btn">&times;</span>
        <strong>${titulo}:</strong> ${mensaje}.
    `;

    document.getElementById("alertaMensaje").appendChild(alerta);

    alerta.querySelector(".close-btn")
        .addEventListener("click", () => alerta.remove());

    setTimeout(() => alerta.remove(), 4000);
}
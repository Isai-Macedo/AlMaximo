package com.almaximo.api.model;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class Producto {
    private String clave;
    private String nombre;
    private BigDecimal precio;
    private int idTipoProducto;
    private String nombreTipo; // Campo auxiliar para mostrar el nombre del tipo
    private List<ProductoProveedor> proveedores;
}

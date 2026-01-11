package com.almaximo.api.model;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductoProveedor {
    private int id;
    private String claveProducto;
    private int idProveedor;
    private String nombreProveedor; // Campo auxiliar para mostrar el nombre
    private BigDecimal costo;
    private String claveProveedor;
}

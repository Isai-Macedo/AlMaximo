package com.almaximo.api.service;

import com.almaximo.api.model.Producto;
import com.almaximo.api.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> listar(String clave, Integer idTipo) {
        return productoRepository.listar(clave, idTipo);
    }

    public Producto obtener(String clave) {
        return productoRepository.obtener(clave);
    }

    public void guardar(Producto producto) {
        // Verificar si existe para decidir si insertar o actualizar es responsabilidad del cliente en REST generalmente,
        // pero aquí podemos checar si existe la clave.
        // Dado que la clave es PK, si intentamos insertar y existe, fallará.
        // Asumiremos que el Controller decide llamar a insertar o actualizar basado en el verbo HTTP.
        productoRepository.insertar(producto);
    }

    public void actualizar(Producto producto) {
        productoRepository.actualizar(producto);
    }

    public void eliminar(String clave) {
        productoRepository.eliminar(clave);
    }
}

package com.almaximo.api.service;

import com.almaximo.api.model.Proveedor;
import com.almaximo.api.model.TipoProducto;
import com.almaximo.api.repository.ProveedorRepository;
import com.almaximo.api.repository.TipoProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatalogoService {

    @Autowired
    private TipoProductoRepository tipoProductoRepository;

    @Autowired
    private ProveedorRepository proveedorRepository;

    public List<TipoProducto> listarTiposProducto() {
        return tipoProductoRepository.listar();
    }

    public List<Proveedor> listarProveedores() {
        return proveedorRepository.listar();
    }
}

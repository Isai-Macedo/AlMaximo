package com.almaximo.api.controller;

import com.almaximo.api.model.Proveedor;
import com.almaximo.api.model.TipoProducto;
import com.almaximo.api.service.CatalogoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/catalogos")
@CrossOrigin(origins = "*") // Permitir acceso desde cualquier origen (para desarrollo)
public class CatalogoController {

    @Autowired
    private CatalogoService catalogoService;

    @GetMapping("/tipos-producto")
    public List<TipoProducto> listarTiposProducto() {
        return catalogoService.listarTiposProducto();
    }

    @GetMapping("/proveedores")
    public List<Proveedor> listarProveedores() {
        return catalogoService.listarProveedores();
    }
}

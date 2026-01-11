package com.almaximo.api.controller;

import com.almaximo.api.model.Producto;
import com.almaximo.api.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public List<Producto> listar(@RequestParam(required = false) String clave,
                                 @RequestParam(required = false) Integer idTipo) {
        return productoService.listar(clave, idTipo);
    }

    @GetMapping("/{clave}")
    public ResponseEntity<Producto> obtener(@PathVariable String clave) {
        Producto p = productoService.obtener(clave);
        if (p != null) {
            return ResponseEntity.ok(p);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> insertar(@RequestBody Producto producto) {
        productoService.guardar(producto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{clave}")
    public ResponseEntity<Void> actualizar(@PathVariable String clave, @RequestBody Producto producto) {
        // Asegurar que la clave coincida
        if (!clave.equals(producto.getClave())) {
            return ResponseEntity.badRequest().build();
        }
        productoService.actualizar(producto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{clave}")
    public ResponseEntity<Void> eliminar(@PathVariable String clave) {
        productoService.eliminar(clave);
        return ResponseEntity.ok().build();
    }
}

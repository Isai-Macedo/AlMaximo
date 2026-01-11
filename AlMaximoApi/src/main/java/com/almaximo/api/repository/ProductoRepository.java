package com.almaximo.api.repository;

import com.almaximo.api.model.Producto;
import com.almaximo.api.model.ProductoProveedor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class ProductoRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Producto> listar(String clave, Integer idTipo) {
        String sql = "CALL sp_ListarProductos(?, ?)";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Producto.class), clave, idTipo);
    }

    public Producto obtener(String clave) {
        String sql = "CALL sp_ObtenerProducto(?)";
        List<Producto> productos = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Producto.class), clave);
        
        if (productos.isEmpty()) {
            return null;
        }
        
        Producto producto = productos.get(0);
        
        // Obtener proveedores
        String sqlProv = "CALL sp_ObtenerProveedoresProducto(?)";
        List<ProductoProveedor> proveedores = jdbcTemplate.query(sqlProv, new BeanPropertyRowMapper<>(ProductoProveedor.class), clave);
        producto.setProveedores(proveedores);
        
        return producto;
    }

    @Transactional
    public void insertar(Producto p) {
        String sql = "CALL sp_InsertarProducto(?, ?, ?, ?)";
        jdbcTemplate.update(sql, p.getClave(), p.getNombre(), p.getPrecio(), p.getIdTipoProducto());
        
        if (p.getProveedores() != null) {
            String sqlProv = "CALL sp_AsignarProveedorProducto(?, ?, ?, ?)";
            for (ProductoProveedor pp : p.getProveedores()) {
                jdbcTemplate.update(sqlProv, p.getClave(), pp.getIdProveedor(), pp.getCosto(), pp.getClaveProveedor());
            }
        }
    }

    @Transactional
    public void actualizar(Producto p) {
        String sql = "CALL sp_ActualizarProducto(?, ?, ?, ?)";
        jdbcTemplate.update(sql, p.getClave(), p.getNombre(), p.getPrecio(), p.getIdTipoProducto());
        
        // Eliminar proveedores actuales y reinsertar
        String sqlDel = "CALL sp_EliminarProveedoresProducto(?)";
        jdbcTemplate.update(sqlDel, p.getClave());
        
        if (p.getProveedores() != null) {
            String sqlProv = "CALL sp_AsignarProveedorProducto(?, ?, ?, ?)";
            for (ProductoProveedor pp : p.getProveedores()) {
                jdbcTemplate.update(sqlProv, p.getClave(), pp.getIdProveedor(), pp.getCosto(), pp.getClaveProveedor());
            }
        }
    }

    public void eliminar(String clave) {
        String sql = "CALL sp_EliminarProducto(?)";
        jdbcTemplate.update(sql, clave);
    }
}

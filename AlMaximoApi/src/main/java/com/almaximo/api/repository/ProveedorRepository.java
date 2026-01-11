package com.almaximo.api.repository;

import com.almaximo.api.model.Proveedor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProveedorRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Proveedor> listar() {
        String sql = "CALL sp_ListarProveedores()";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Proveedor.class));
    }
}

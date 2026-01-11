package com.almaximo.api.repository;

import com.almaximo.api.model.TipoProducto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TipoProductoRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<TipoProducto> listar() {
        String sql = "CALL sp_ListarTiposProducto()";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(TipoProducto.class));
    }
}

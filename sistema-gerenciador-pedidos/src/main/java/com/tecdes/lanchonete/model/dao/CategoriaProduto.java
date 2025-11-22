package com.tecdes.lanchonete.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.tecdes.lanchonete.config.ConnectionFactory;

public class CategoriaProduto implements InterfaceDAO<CategoriaProduto> {

    @Override
    public void create(CategoriaProduto t) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql;
            PreparedStatement pr;

            sql = "INSERT INTO T_SGP_CLIENTE (nm_categoria, sg_categoria) VALUES (?,?,?)";
            pr = conn.prepareStatement(sql);
            pr.setString(1, t.getNome());
            pr.setString(2, t.getEmail());
            pr.setString(3, t.getTelefone());

            pr.executeQuery();
        } catch (SQLException e) {

        }
    }

    @Override
    public void delete(Long id) {
        
    }

    @Override
    public void update(CategoriaProduto t) {
        
    }

    @Override
    public CategoriaProduto getById(Long id) {
        
    }

    @Override
    public List<CategoriaProduto> getAll() {
        
    }
    
}

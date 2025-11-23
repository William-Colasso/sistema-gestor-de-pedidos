package com.tecdes.lanchonete.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.tecdes.lanchonete.config.ConnectionFactory;
import com.tecdes.lanchonete.model.entity.CategoriaProduto;

public class CategoriaProdutoDAO implements InterfaceDAO<CategoriaProduto> {

    @Override
    public CategoriaProduto create(CategoriaProduto t) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql;
            PreparedStatement pr;

            sql = "INSERT INTO T_SGP_CATEGORIA_PRODUTO (nm_categoria, sg_categoria) VALUES (?,?)";
            pr = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pr.setString(1, t.getNome());
            pr.setString(2, t.getSigla());

            pr.executeQuery();
            ResultSet rs = pr.getResultSet();
            while (rs.next()) {
                t.setId(rs.getLong(1));
            }
            return t;
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql;
            PreparedStatement pr;

            sql = "DELETE FROM T_SGP_CATEGORIA_PRODUTO where id_categoria = ?";
            pr = conn.prepareStatement(sql);
            pr.setLong(1, id);

            pr.executeQuery();
        } catch (SQLException e) {

        }
    }

    @Override
    public void update(CategoriaProduto t) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql;
            PreparedStatement pr;

            sql = "UPDATE T_SGP_CATEGORIA_PRODUTO SET nm_categoria = ?, sg_categoria = ? where id_categoria = ?";
            pr = conn.prepareStatement(sql);
            pr.setLong(1, t.getId());
            pr.setString(2, t.getNome());
            pr.setString(3, t.getSigla());

            pr.executeQuery();
        } catch (SQLException e) {

        }
    }

    @Override
    public CategoriaProduto getById(Long id) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql;
            PreparedStatement pr;

            sql = "select * T_SGP_CATEGORIA_PRODUTO where id_categoria = ?";
            pr = conn.prepareStatement(sql);
            pr.setLong(1, id);

            ResultSet rs = pr.executeQuery();

            CategoriaProduto categoria = new CategoriaProduto();
            categoria.setId(rs.getLong("id_parceiro"));
            categoria.setNome(rs.getString("nm_categoria"));
            categoria.setSigla(rs.getString("sg_categoria"));

            return categoria;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public List<CategoriaProduto> getAll() {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql;
            PreparedStatement pr;

            sql = "select * T_SGP_CATEGORIA_PRODUTO";
            pr = conn.prepareStatement(sql);

            ResultSet rs = pr.executeQuery();
            List<CategoriaProduto> listaCategoria = new ArrayList<>();
            while (rs.next()) {
                CategoriaProduto categoria = new CategoriaProduto();
                categoria.setId(rs.getLong("id_parceiro"));
                categoria.setNome(rs.getString("nm_categoria"));
                categoria.setSigla(rs.getString("sg_categoria"));
                listaCategoria.add(categoria);
            }

            return listaCategoria;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

}

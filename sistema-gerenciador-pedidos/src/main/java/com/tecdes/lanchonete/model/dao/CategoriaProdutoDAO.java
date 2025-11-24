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

            pr.executeUpdate();
            ResultSet rs = pr.getGeneratedKeys();
            if (rs.next()) {
                t.setId(rs.getLong(1));
            }
            return t;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao criar Categoria: " + e);
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

            pr.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao deletar Categoria: " + e);
        }
    }

    @Override
    public void update(CategoriaProduto t) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql;
            PreparedStatement pr;

            sql = "UPDATE T_SGP_CATEGORIA_PRODUTO SET nm_categoria = ?, sg_categoria = ? where id_categoria = ?";
            pr = conn.prepareStatement(sql);
            
            pr.setString(1, t.getNome());
            pr.setString(2, t.getSigla());
            pr.setLong(3, t.getId());

            pr.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao atualizar Categoria: " + e);
        }
    }

    @Override
    public CategoriaProduto getById(Long id) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql;
            PreparedStatement pr;

            sql = "SELECT * FROM T_SGP_CATEGORIA_PRODUTO where id_categoria = ?";
            pr = conn.prepareStatement(sql);
            pr.setLong(1, id);

            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                return mapCategoriaProduto(rs);
            } else {
                throw new RuntimeException("Erro ao buscar Categoria por ID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao obter Categoria: " + e);
        }
    }

    @Override
    public List<CategoriaProduto> getAll() {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql;
            PreparedStatement pr;

            sql = "SELECT * FROM T_SGP_CATEGORIA_PRODUTO";
            pr = conn.prepareStatement(sql);

            ResultSet rs = pr.executeQuery();
            List<CategoriaProduto> listaCategoria = new ArrayList<>();
            while (rs.next()) {
                listaCategoria.add(mapCategoriaProduto(rs));
            }

            return listaCategoria;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao obter Categorias: " + e);
        }
    }

    private CategoriaProduto mapCategoriaProduto(ResultSet rs) throws SQLException{
        CategoriaProduto categoria = new CategoriaProduto();
        categoria.setId(rs.getLong("id_categoria"));
        categoria.setNome(rs.getString("nm_categoria"));
        categoria.setSigla(rs.getString("sg_categoria"));
        return categoria;
    }

}

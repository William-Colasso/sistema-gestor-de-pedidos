package com.tecdes.lanchonete.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.tecdes.lanchonete.config.ConnectionFactory;
import com.tecdes.lanchonete.model.entity.Midia;
import com.tecdes.lanchonete.model.enums.TipoMidia;

public class MidiaDAO implements InterfaceDAO<Midia> {

    @Override
    public Midia create(Midia t) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql;
            PreparedStatement pr;

            sql = "INSERT INTO T_SGP_MIDIA  (ITEM_id_item, ds_midia, sq_midia, tp_midia) VALUES (?,?,?,?)";
            pr = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pr.setLong(1, t.getIdItem());
            pr.setString(2, t.getDescricao());
            pr.setBytes(3, t.getArquivo());
            pr.setString(4, String.valueOf(t.getTipo().getValue()));

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

            sql = "DELETE FROM T_SGP_MIDIA where id_cupom = ?";
            pr = conn.prepareStatement(sql);
            pr.setLong(1, id);

            pr.executeQuery();
        } catch (SQLException e) {

        }
    }

    @Override
    public void update(Midia t) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql;
            PreparedStatement pr;

            sql = "UPDATE T_SGP_MIDIA SET ITEM_id_item = ?, ds_midia = ?, sq_midia = ?, tp_midia = ? where id_midia = ?";
            pr = conn.prepareStatement(sql);
            pr.setLong(1, t.getId());
            pr.setLong(1, t.getIdItem());
            pr.setString(2, t.getDescricao());
            pr.setBytes(3, t.getArquivo());
            pr.setString(4, String.valueOf(t.getTipo().getValue()));

            pr.executeQuery();
        } catch (SQLException e) {

        }
    }

    @Override
    public Midia getById(Long id) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql;
            PreparedStatement pr;

            sql = "select * T_SGP_MIDIA where id_midia = ?";
            pr = conn.prepareStatement(sql);
            pr.setLong(1, id);

            ResultSet rs = pr.executeQuery();

            Midia midia = new Midia();
            midia.setId(rs.getLong("id_parceiro"));
            midia.setDescricao(rs.getString("ds_midia"));
            midia.setArquivo(rs.getBytes("sq_midia"));
            midia.setTipo(TipoMidia.valueOf(rs.getString("tp_midia")));
            midia.setIdItem(rs.getLong("ITEM_id_item"));

            return midia;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public List<Midia> getAll() {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql;
            PreparedStatement pr;

            sql = "select * T_SGP_CATEGORIA_PRODUTO";
            pr = conn.prepareStatement(sql);

            ResultSet rs = pr.executeQuery();
            List<Midia> listaMidia = new ArrayList<>();
            while (rs.next()) {
                Midia midia = new Midia();
                midia.setId(rs.getLong("id_parceiro"));
                midia.setDescricao(rs.getString("ds_midia"));
                midia.setArquivo(rs.getBytes("sq_midia"));
                midia.setTipo(TipoMidia.valueOf(rs.getString("tp_midia")));
                midia.setIdItem(rs.getLong("ITEM_id_item"));
                listaMidia.add(midia);
            }

            return listaMidia;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

}

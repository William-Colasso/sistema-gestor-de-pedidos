package com.tecdes.lanchonete.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.tecdes.lanchonete.config.ConnectionFactory;
import com.tecdes.lanchonete.generalinterfaces.crud.Crud;
import com.tecdes.lanchonete.model.entity.Item;
import com.tecdes.lanchonete.model.entity.Midia;
import com.tecdes.lanchonete.model.enums.TipoMidia;

public class MidiaDAO implements Crud<Midia> {

    @Override
    public Midia create(Midia t) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql;
            PreparedStatement pr;

            sql = "INSERT INTO T_SGP_MIDIA  (id_item, ds_midia, sq_midia, tp_midia) VALUES (?,?,?,?)";
            pr = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pr.setLong(1, t.getItem().getId());
            pr.setString(2, t.getDescricao());
            pr.setBytes(3, t.getArquivo());
            pr.setString(4, String.valueOf(t.getTipo().getValue()));

            pr.executeUpdate();
            ResultSet rs = pr.getGeneratedKeys();
            if (rs.next()) {
                t.setId(rs.getLong(1));
            }
            return t;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar mídia: " + e);
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql;
            PreparedStatement pr;

            sql = "DELETE FROM T_SGP_MIDIA where id_midia = ?";
            pr = conn.prepareStatement(sql);
            pr.setLong(1, id);

            pr.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar mídia: " + e);
        }
    }

    @Override
    public void update(Midia t) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql;
            PreparedStatement pr;

            sql = "UPDATE T_SGP_MIDIA SET id_item = ?, ds_midia = ?, sq_midia = ?, tp_midia = ? where id_midia = ?";
            pr = conn.prepareStatement(sql);
            
            pr.setLong(1, t.getItem().getId());
            pr.setString(2, t.getDescricao());
            pr.setBytes(3, t.getArquivo());
            pr.setString(4, String.valueOf(t.getTipo().getValue()));
            pr.setLong(5, t.getId());

            pr.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar mídia: " + e);
        }
    }

    @Override
    public Midia getById(Long id) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql;
            PreparedStatement pr;

            sql = "select * FROM T_SGP_MIDIA where id_midia = ?";
            pr = conn.prepareStatement(sql);
            pr.setLong(1, id);

            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                return mapMidia(rs);
            } 
            return null;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao obter mídia: " + e);
        }
    }

    @Override
    public List<Midia> getAll() {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql;
            PreparedStatement pr;

            sql = "select * FROM T_SGP_MIDIA";
            pr = conn.prepareStatement(sql);

            ResultSet rs = pr.executeQuery();
            List<Midia> listaMidia = new ArrayList<>();
            while (rs.next()) {
                listaMidia.add(mapMidia(rs));
            }

            return listaMidia;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao obter mídias: " + e);
        }
    }

    private Midia mapMidia(ResultSet rs) throws SQLException{
        Midia midia = new Midia();
        midia.setId(rs.getLong("id_midia"));
        midia.setDescricao(rs.getString("ds_midia"));
        midia.setArquivo(rs.getBytes("sq_midia"));
        midia.setTipo(TipoMidia.fromValue(rs.getString("tp_midia").charAt(0)));
        Item item = new Item();
        item.setId(rs.getLong("id_item"));
        midia.setItem(item);
        return midia;
    }
}

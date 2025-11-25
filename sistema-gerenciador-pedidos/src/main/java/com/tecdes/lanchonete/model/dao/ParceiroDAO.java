package com.tecdes.lanchonete.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.tecdes.lanchonete.config.ConnectionFactory;
import com.tecdes.lanchonete.model.entity.Parceiro;

public class ParceiroDAO implements InterfaceDAO<Parceiro> {

    @Override
    public Parceiro create(Parceiro t) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql;
            PreparedStatement pr;

            sql = "INSERT INTO T_SGP_PARCEIRO (nm_parceiro, ds_email, nr_telefone) VALUES (?,?,?)";
            pr = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pr.setString(1, t.getNome());
            pr.setString(2, t.getEmail());
            pr.setString(3, t.getTelefone());

            pr.executeUpdate();

            ResultSet rs = pr.getGeneratedKeys();
            if (rs.next()) {
                t.setId(rs.getLong(1));
            }

            return t;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar Parceiro: " + e);
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql;
            PreparedStatement pr;

            sql = "DELETE FROM T_SGP_PARCEIRO where id_parceiro = ?";
            pr = conn.prepareStatement(sql);
            pr.setLong(1, id);

            pr.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar Parceiro: " + e);
        }
    }        

    @Override
    public void update(Parceiro t) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql;
            PreparedStatement pr;

            sql = "UPDATE T_SGP_PARCEIRO SET nm_parceiro = ?, ds_email = ?, nr_telefone = ? where id_parceiro = ?";
            pr = conn.prepareStatement(sql);
            pr.setString(1, t.getNome());
            pr.setString(2, t.getEmail());
            pr.setString(3, t.getTelefone());
            pr.setLong(4, t.getId());

            pr.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar Parceiro: " + e);
        }
    }

    @Override
    public Parceiro getById(Long id) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql;
            PreparedStatement pr;

            sql = "select * from T_SGP_PARCEIRO where id_parceiro = ?";
            pr = conn.prepareStatement(sql);
            pr.setLong(1, id);

            ResultSet rs = pr.executeQuery();

            if (rs.next()) {
                return mapParceiro(rs);
            } 
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao obter Parceiro: " + e);
        }
    }

    @Override
    public List<Parceiro> getAll() {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql;
            PreparedStatement pr;

            sql = "select * from T_SGP_PARCEIRO";
            pr = conn.prepareStatement(sql);

            ResultSet rs = pr.executeQuery();
            List<Parceiro> listaParceiro = new ArrayList<>();
            while(rs.next()){
                listaParceiro.add(mapParceiro(rs));
            }

            return listaParceiro;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao criar Parceiros: " + e);
        }
    }

    private Parceiro mapParceiro(ResultSet rs) throws SQLException{
        Parceiro parceiro = new Parceiro();
        parceiro.setId(rs.getLong("id_parceiro"));
        parceiro.setNome(rs.getString("nm_parceiro"));
        parceiro.setEmail(rs.getString("ds_email"));
        parceiro.setTelefone(rs.getString("nr_telefone"));
        return parceiro;
    }
    
}

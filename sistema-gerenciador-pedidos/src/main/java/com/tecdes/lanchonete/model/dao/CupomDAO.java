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
import com.tecdes.lanchonete.model.entity.Cupom;
import com.tecdes.lanchonete.model.entity.Parceiro;

public class CupomDAO implements Crud<Cupom> {

    @Override
    public Cupom create(Cupom t) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql;
            PreparedStatement pr;

            sql = "INSERT INTO T_SGP_CUPOM (id_parceiro, vl_desconto, ds_cupom, nm_cupom, st_valido) VALUES (?,?,?,?,?)";
            pr = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pr.setLong(1, t.getParceiro().getId());
            pr.setInt(2, t.getValorDesconto());
            pr.setString(3, t.getDescricao());
            pr.setString(4, t.getNome());
            pr.setInt(5, t.getValido());

            pr.executeUpdate();
            ResultSet rs = pr.getGeneratedKeys();
            if (rs.next()) {
                t.setId(rs.getLong(1));
            }
            return t;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar Cupom: " + e);
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql;
            PreparedStatement pr;

            sql = "DELETE FROM T_SGP_CUPOM where id_cupom = ?";
            pr = conn.prepareStatement(sql);
            pr.setLong(1, id);

            pr.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar Cupom: " + e);
        }
    }

    @Override
    public void update(Cupom t) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql;
            PreparedStatement pr;

            sql = "UPDATE T_SGP_CUPOM SET id_parceiro = ?, vl_desconto = ?, ds_cupom = ?, nm_cupom = ?, st_valido = ? where id_cupom = ?";
            pr = conn.prepareStatement(sql);
            pr.setLong(1, t.getParceiro().getId());
            pr.setInt(2, t.getValorDesconto());
            pr.setString(3, t.getDescricao());
            pr.setString(4, t.getNome());
            pr.setInt(5, t.getValido());
            pr.setLong(6, t.getId());

            pr.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar Cupom: " + e);
        }
    }

    @Override
    public Cupom getById(Long id) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql;
            PreparedStatement pr;

            sql = "select * from T_SGP_CUPOM c INNER JOIN T_SGP_PARCEIRO p on c.id_parceiro = p.id_parceiro where id_cupom = ?";
            pr = conn.prepareStatement(sql);
            pr.setLong(1, id);

            ResultSet rs = pr.executeQuery();

            if (rs.next()) {
                return mapCupom(rs);
            } 
            return null;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao obter Cupom: " + e);
        }
    }

    @Override
    public List<Cupom> getAll() {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql;
            PreparedStatement pr;

            sql = "select * from T_SGP_CUPOM c INNER JOIN T_SGP_PARCEIRO p on c.id_parceiro = p.id_parceiro";
            pr = conn.prepareStatement(sql);

            ResultSet rs = pr.executeQuery();
            List<Cupom> listaCupom = new ArrayList<>();
            while (rs.next()) {
                listaCupom.add(mapCupom(rs));
            }

            return listaCupom;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao obter Cupons: " + e);
        }
    }

    private Cupom mapCupom(ResultSet rs) {
        try{
            Cupom cupom = new Cupom();
            cupom.setId(rs.getLong("id_cupom"));
            cupom.setNome(rs.getString("nm_cupom"));
            cupom.setDescricao(rs.getString("ds_cupom"));
            cupom.setValorDesconto(rs.getInt("vl_desconto"));
            cupom.setValido(rs.getInt("st_valido"));
            cupom.setParceiro(getParceiro(rs));
            return cupom;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao popular Cupom: " + e);
        }
    }

    private Parceiro getParceiro(ResultSet rs){
        try {
            Parceiro parceiro = new Parceiro();
            parceiro.setId(rs.getLong("id_parceiro"));
            parceiro.setNome(rs.getString("nm_parceiro"));
            parceiro.setEmail(rs.getString("ds_email"));
            parceiro.setTelefone(rs.getString("nr_telefone"));
            return parceiro;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao obter parceiro do cupom: " + e);
        }
    }
    
}
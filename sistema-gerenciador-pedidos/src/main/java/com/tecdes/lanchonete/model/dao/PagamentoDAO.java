package com.tecdes.lanchonete.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.tecdes.lanchonete.config.ConnectionFactory;
import com.tecdes.lanchonete.model.entity.Pagamento;

public class PagamentoDAO implements InterfaceDAO<Pagamento> {

    @Override
    public Pagamento create(Pagamento t) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql;
            PreparedStatement pr;

            sql = "INSERT INTO T_SGP_FORMA_PAGAMENTO  (nm_pagamento, sg_pagamento, sq_midia) VALUES (?,?,?)";
            pr = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pr.setString(1, t.getNome());
            pr.setString(2, t.getSigla());
            pr.setBytes(3, t.getMidia());

            pr.executeUpdate();
            ResultSet rs = pr.getGeneratedKeys();
            if (rs.next()) {
                t.setId(rs.getLong(1));
            }
            return t;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar pagamento: " + e);
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql;
            PreparedStatement pr;

            sql = "DELETE FROM T_SGP_FORMA_PAGAMENTO where id_pagamento = ?";
            pr = conn.prepareStatement(sql);
            pr.setLong(1, id);

            pr.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar pagamento: " + e);
        }
    }

    @Override
    public void update(Pagamento t) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql;
            PreparedStatement pr;

            sql = "UPDATE T_SGP_FORMA_PAGAMENTO SET nm_pagamento = ?, sg_pagamento = ?, sq_midia = ? where id_pagamento = ?";
            pr = conn.prepareStatement(sql);
            
            pr.setString(1, t.getNome());
            pr.setString(2, t.getSigla());
            pr.setBytes(3, t.getMidia());
            pr.setLong(4, t.getId());

            pr.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar pagamento: " + e);
        }
    }

    @Override
    public Pagamento getById(Long id) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql;
            PreparedStatement pr;

            sql = "select * from T_SGP_FORMA_PAGAMENTO where id_pagamento = ?";
            pr = conn.prepareStatement(sql);
            pr.setLong(1, id);

            ResultSet rs = pr.executeQuery();

            if (rs.next()) {
                return mapPagamento(rs);
            } 
            return null;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao obter pagamento: " + e);
        }
    }

    @Override
    public List<Pagamento> getAll() {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql;
            PreparedStatement pr;

            sql = "select * from T_SGP_FORMA_PAGAMENTO";
            pr = conn.prepareStatement(sql);

            ResultSet rs = pr.executeQuery();
            List<Pagamento> listaPagamento = new ArrayList<>();
            while (rs.next()) {
                listaPagamento.add(mapPagamento(rs));
            }

            return listaPagamento;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao obter pagamentos: " + e);
        }
    }
    
    private Pagamento mapPagamento(ResultSet rs) throws SQLException {
        Pagamento pagamento = new Pagamento();
        pagamento.setId(rs.getLong("id_pagamento"));
        pagamento.setNome(rs.getString("nm_pagamento"));
        pagamento.setSigla(rs.getString("sg_pagamento"));
        pagamento.setMidia(rs.getBytes("sq_midia"));
        return pagamento;
    }
}

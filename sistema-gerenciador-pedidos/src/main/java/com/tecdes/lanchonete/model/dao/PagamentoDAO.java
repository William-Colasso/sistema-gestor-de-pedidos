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

            sql = "INSERT INTO T_SGP_FORMA_PAGAMENTO  (nm_pagamento, sg_pagamento) VALUES (?,?)";
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

            sql = "DELETE FROM T_SGP_FORMA_PAGAMENTO where id_pagamento = ?";
            pr = conn.prepareStatement(sql);
            pr.setLong(1, id);

            pr.executeQuery();
        } catch (SQLException e) {

        }
    }

    @Override
    public void update(Pagamento t) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql;
            PreparedStatement pr;

            sql = "UPDATE T_SGP_FORMA_PAGAMENTO SET nm_pagamento = ?, sg_pagamento = ? where id_pagamento = ?";
            pr = conn.prepareStatement(sql);
            pr.setLong(1, t.getId());
            pr.setString(2, t.getNome());
            pr.setString(3, t.getSigla());

            pr.executeQuery();
        } catch (SQLException e) {

        }
    }

    @Override
    public Pagamento getById(Long id) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql;
            PreparedStatement pr;

            sql = "select * T_SGP_FORMA_PAGAMENTO where id_pagamento = ?";
            pr = conn.prepareStatement(sql);
            pr.setLong(1, id);

            ResultSet rs = pr.executeQuery();

            Pagamento pagamento = new Pagamento();
            pagamento.setId(rs.getLong("id_parceiro"));
            pagamento.setNome(rs.getString("nm_categoria"));
            pagamento.setSigla(rs.getString("sg_categoria"));

            return pagamento;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public List<Pagamento> getAll() {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql;
            PreparedStatement pr;

            sql = "select * T_SGP_CATEGORIA_PRODUTO";
            pr = conn.prepareStatement(sql);

            ResultSet rs = pr.executeQuery();
            List<Pagamento> listaPagamento = new ArrayList<>();
            while (rs.next()) {
                Pagamento pagamento = new Pagamento();
                pagamento.setId(rs.getLong("id_parceiro"));
                pagamento.setNome(rs.getString("nm_categoria"));
                pagamento.setSigla(rs.getString("sg_categoria"));
                listaPagamento.add(pagamento);
            }

            return listaPagamento;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
    
}

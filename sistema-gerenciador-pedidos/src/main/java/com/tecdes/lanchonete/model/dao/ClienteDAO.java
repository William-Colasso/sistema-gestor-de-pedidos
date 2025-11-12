package com.tecdes.lanchonete.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.tecdes.lanchonete.config.ConnectionFactory;
import com.tecdes.lanchonete.model.dao.InterfaceDAO;
import com.tecdes.lanchonete.model.entity.Cliente;

public class ClienteDAO implements InterfaceDAO<Cliente> {

    @Override
    public void delete(Long id) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql;
            PreparedStatement pr;

            sql = "Delete T_SGP_CLIENTE where id_cliente = ?";
            pr = conn.prepareStatement(sql);
            pr.setLong(1, id);

            pr.executeUpdate();
        } catch (SQLException e) {

        }
    }

    @Override
    public void update(Cliente t) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql;
            PreparedStatement pr;

            sql = "UPDATE T_SGP_CLIENTE SET nm_cliente = ?, nr_telefone = ?, nr_cpf = ?, dt_registro =? where id_cliente = ?";
            pr = conn.prepareStatement(sql);
            pr.setString(1, t.getNome());
            pr.setString(2, t.getTelefone());
            pr.setString(3, t.getCpf());
            pr.setDate(4, t.getDataRegistro());
            pr.setLong(5, t.getId());

            pr.executeQuery();
        } catch (SQLException e) {

        }
    }

    @Override
    public Cliente getById(Long id) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql;
            PreparedStatement pr;

            sql = "select * T_SGP_CLIENTE where id_cliente = ?";
            pr = conn.prepareStatement(sql);
            pr.setLong(1, id);

            ResultSet rs = pr.executeQuery();

            return new Cliente();

        } catch (SQLException e) {
            e.printStackTrace();
            return new Cliente();
        }
    }

    @Override
    public List<Cliente> getAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAll'");
    }

    @Override
    public void create(Cliente t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }
}

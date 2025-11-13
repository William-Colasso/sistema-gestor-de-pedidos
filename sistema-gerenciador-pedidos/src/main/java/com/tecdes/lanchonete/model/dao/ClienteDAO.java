package com.tecdes.lanchonete.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

            Cliente cliente = new Cliente();
                cliente.setId(rs.getLong("id_cliente"));
                cliente.setNome(rs.getString("nm_cliente"));
                cliente.setCpf(rs.getString("nr_cpf"));
                cliente.setTelefone(rs.getString("nr_telefone"));
                cliente.setDataRegistro(rs.getDate("dt_registro"));

            return cliente;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public List<Cliente> getAll() {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql;
            PreparedStatement pr;

            sql = "select * T_SGP_CLIENTE";
            pr = conn.prepareStatement(sql);

            ResultSet rs = pr.executeQuery();
            List<Cliente> listaCliente = new ArrayList();
            while(rs.next()){
                Cliente cliente = new Cliente();
                cliente.setId(rs.getLong("id_cliente"));
                cliente.setNome(rs.getString("nm_cliente"));
                cliente.setCpf(rs.getString("nr_cpf"));
                cliente.setTelefone(rs.getString("nr_telefone"));
                cliente.setDataRegistro(rs.getDate("dt_registro"));
                listaCliente.add(cliente);
            }

            return listaCliente;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public void create(Cliente t) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql;
            PreparedStatement pr;

            sql = "INSERT INTO T_SGP_CLIENTE (nm_cliente, nr_telefone, nr_cpf , dt_registro) VALUES (?,?,?,?)";
            pr = conn.prepareStatement(sql);
            pr.setString(1, t.getNome());
            pr.setString(2, t.getTelefone());
            pr.setString(3, t.getCpf());
            pr.setDate(4, t.getDataRegistro());

            pr.executeQuery();
        } catch (SQLException e) {

        }
    }
}

package main.java.com.tecdes.lanchonete.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.tecdes.lanchonete.model.dao.InterfaceDAO;
import com.tecdes.lanchonete.model.entity.Cliente;

public class ClienteDAO implements InterfaceDAO<Cliente> {

    public void create(Cliente t) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql;
            PreparedStatement pr;

            if (t.getTelefone() != null) {
                sql = "INSERT INTO T_SGP_CLIENTE (nm_cliente, nr_telefone, nr_cpf, dt_registro) VALUES (?, ?, ?, ?)";
                pr = conn.prepareStatement(sql);
                pr.setString(1, t.getNome());
                pr.setString(2, t.getTelefone());
                pr.setString(3, t.getCpf());
                pr.setDate(4, java.sql.Date.valueOf(t.getDataRegistro()));
            } else {
                sql = "INSERT INTO T_SGP_CLIENTE (nm_cliente, nr_cpf, dt_registro) VALUES (?, ?, ?)";
                pr = conn.prepareStatement(sql);
                pr.setString(1, t.getNome());
                pr.setString(2, t.getCpf());
                pr.setDate(3, java.sql.Date.valueOf(t.getDataRegistro()));
            }

            pr.executeUpdate();
        } catch (SQLException e) {

        }
    }

    public void delete(Long id) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql;
            PreparedStatement pr;

            sql = "Delete T_SGP_CLIENTE where id_cliente = ?";
            pr = conn.prepareStatement(sql);
            pr.setString(1, id);

            pr.executeUpdate();
        } catch (SQLException e) {

        }
    }

    public void update(Cliente t) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql;
            PreparedStatement pr;

            sql = "UPDATE T_SGP_CLIENTE SET nm_cliente = ?, nr_telefone = ?, nr_cpf = ?, dt_registro =? where id_cliente = ?";
                pr = conn.prepareStatement(sql);
                pr.setString(1, t.getNome());
                pr.setString(2, t.getTelefone());
                pr.setString(3, t.getCpf());
                pr.setDate(4, java.sql.Date.valueOf(t.getDataRegistro()));
                pr.setString(5, t.getId());

            pr.executeQuery();
        } catch (SQLException e) {

        }
    }

    public T getById(Long id) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql;
            PreparedStatement pr;

            sql = "select T_SGP_CLIENTE where id_cliente = ?";
            pr = conn.prepareStatement(sql);
            pr.setString(1, id);

            pr.executeQuery();
        } catch (SQLException e) {

        }
    }
}

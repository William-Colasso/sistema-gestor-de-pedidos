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
import com.tecdes.lanchonete.model.entity.Cliente;
import com.tecdes.lanchonete.model.entity.Cupom;

public class ClienteDAO implements Crud<Cliente> {

    @Override
    public void delete(Long id) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql;
            PreparedStatement pr;

            sql = "DELETE FROM T_SGP_CLIENTE WHERE id_cliente = ?";
            pr = conn.prepareStatement(sql);
            pr.setLong(1, id);

            pr.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar Cliente: "+e);
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

            pr.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar Cliente: "+e);
        }
    }

    public List<Cupom> getCuponsByIdCliente(Long id){
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql;
            PreparedStatement pr;

            sql = "select id_cupom from T_CUPOM_CLIENTE where id_cliente = ?";
            pr =  conn.prepareStatement(sql);
            pr.setLong(1, id);

            ResultSet rs = pr.executeQuery();

            List<Cupom> listaCupom = new ArrayList<>();
            if(rs.next()){
                Cupom c  = new Cupom();
                c.setId(rs.getLong("id_cupom"));
                listaCupom.add(c);
            }
            return listaCupom;
        } catch(SQLException e){
            e.printStackTrace();
            throw new RuntimeException("Erro ao obter Cliente: "+e);
        }
    }

    @Override
    public Cliente getById(Long id) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql;
            PreparedStatement pr;

            sql = "select * from T_SGP_CLIENTE where id_cliente = ?";
            pr = conn.prepareStatement(sql);
            pr.setLong(1, id);

            ResultSet rs = pr.executeQuery();

            if (rs.next()) {
                return mapCliente(rs);
            } 
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao obter Cliente: "+e);
        }
    }

    @Override
    public List<Cliente> getAll() {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql;
            PreparedStatement pr;

            sql = "select * from T_SGP_CLIENTE";
            pr = conn.prepareStatement(sql);

            ResultSet rs = pr.executeQuery();
            List<Cliente> listaCliente = new ArrayList<>();
            while(rs.next()){
                listaCliente.add(mapCliente(rs));
            }

            return listaCliente;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao obter Clientes: "+e);
        }
    }

    @Override
    public Cliente create(Cliente t) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql;
            PreparedStatement pr;

            sql = "INSERT INTO T_SGP_CLIENTE (nm_cliente, nr_telefone, nr_cpf , dt_registro) VALUES (?,?,?,?)";
            pr = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pr.setString(1, t.getNome());
            pr.setString(2, t.getTelefone());
            pr.setString(3, t.getCpf());
            pr.setDate(4, t.getDataRegistro());

            pr.executeUpdate();

            ResultSet rs = pr.getGeneratedKeys();
            if (rs.next()) {
                t.setId(rs.getLong(1));
            } else {
                throw new RuntimeException("Erro ao obter primary key gerada.");
            }

            return t;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar Cliente: "+e);
        }
    }

    private Cliente mapCliente(ResultSet rs) throws SQLException{
        Cliente cliente = new Cliente();
        cliente.setId(rs.getLong("id_cliente"));
        cliente.setNome(rs.getString("nm_cliente"));
        cliente.setCpf(rs.getString("nr_cpf"));
        cliente.setTelefone(rs.getString("nr_telefone"));
        cliente.setDataRegistro(rs.getDate("dt_registro"));
        return cliente;
    }
}

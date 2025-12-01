package com.tecdes.lanchonete.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tecdes.lanchonete.config.ConnectionFactory;
import com.tecdes.lanchonete.generalinterfaces.crud.Crud;
import com.tecdes.lanchonete.model.entity.Gerente;

public class GerenteDAO implements Crud<Gerente> {

    @Override
    public Gerente create(Gerente t) {
        String sql = """
            INSERT INTO t_sgp_gerente (
                id_funcionario, ds_senha, nm_login
            ) VALUES (?, ?, ?)     
        """;

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement pr = conn.prepareStatement(sql)) {
            fillInsertStatementParameters(pr, t);
            pr.executeUpdate();
            return t;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao inserir Gerente: " + e);
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM t_sgp_gerente WHERE id_funcionario = ?";
        try (Connection conn = ConnectionFactory.getConnection();
        PreparedStatement pr = conn.prepareStatement(sql)) {
            pr.setLong(1, id);
            pr.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao remover Gerente: " + e);
        }
    }

    @Override
    public void update(Gerente t) {
        String sql = """
            UPDATE t_sgp_gerente   
            SET nm_login = ?, ds_senha = ?   
            WHERE id_funcionario = ?
        """;
        try (Connection conn = ConnectionFactory.getConnection();
        PreparedStatement pr = conn.prepareStatement(sql)) {
            pr.setString(1, t.getLogin());
            pr.setString(2, t.getSenha());
            pr.setLong(3, t.getId());

            pr.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar Gerente: " + e);
        }
    }

    @Override
    public Gerente getById(Long id) {
        String sql = """
            SELECT nm_login, ds_senha FROM t_sgp_gerente WHERE id_funcionario = ?
        """;

        try (Connection conn = ConnectionFactory.getConnection();
        PreparedStatement pr = conn.prepareStatement(sql)) {
            pr.setLong(1, id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                return mapGerente(rs);
            } 
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao obter Gerente: " + e);
        }
    }

    @Override
    public List<Gerente> getAll() {
        String sql = """
            SELECT nm_login, ds_senha FROM t_sgp_gerente 
        """;

        try (Connection conn = ConnectionFactory.getConnection();
        PreparedStatement pr = conn.prepareStatement(sql)) {
            ResultSet rs = pr.executeQuery();
            List<Gerente> gerentes = new ArrayList<>();
            while (rs.next()) {
                gerentes.add(mapGerente(rs));
            }
            return gerentes;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao obter Gerentes: " + e);
        }
    }

    public Gerente getByLogin(String login){
        String sql = """
            SELECT nm_login, ds_senha FROM t_sgp_gerente WHERE nm_login = ?
        """;

        try (Connection conn = ConnectionFactory.getConnection();
        PreparedStatement pr = conn.prepareStatement(sql)) {
            pr.setString(1, login);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                return mapGerente(rs);
            } 
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao obter Gerente: " + e);
        }
    }


    private void fillInsertStatementParameters(PreparedStatement pr, Gerente gerente) throws SQLException{
        pr.setLong(1, gerente.getId());
        pr.setString(2, gerente.getSenha());
        pr.setString(3, gerente.getLogin());
    }

    private Gerente mapGerente(ResultSet rs) throws SQLException{
        Gerente gerente = new Gerente();
        gerente.setLogin(rs.getString("nm_login"));
        gerente.setSenha(rs.getString("ds_senha"));
        return gerente;
    }
}
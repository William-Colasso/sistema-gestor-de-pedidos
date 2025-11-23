package com.tecdes.lanchonete.model.dao;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.tecdes.lanchonete.config.ConnectionFactory;
import com.tecdes.lanchonete.model.entity.Funcionario;
import com.tecdes.lanchonete.model.entity.Gerente;

public class FuncionarioDAO implements InterfaceDAO<Funcionario> {

    @Override
    public Funcionario create(Funcionario t) {
        try(Connection conn = ConnectionFactory.getConnection()) {
            t.setId(insertFuncionario(t, conn));
            return t;
        } catch (Exception e) {
            throw new RuntimeException("Erro DAO ao criar funcionário: " + e);
        }
    }
    
    @Override
    public void delete(Long id) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            // Deletar da tabela de funcionários
            String sql = "DELETE FROM t_sgp_funcionario WHERE id_funcionario = ?";
            try (PreparedStatement pr = conn.prepareStatement(sql)) {
                pr.setLong(1, id);
                pr.executeUpdate();
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar Funcionario: " + e);
        }
    }

    @Override
    public void update(Funcionario t) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            updateFuncionario(t, conn);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar Funcionario: " + e);
        }
    }

    @Override
    public Funcionario getById(Long id) {
        String sql = """
            SELECT f.*, 
            FROM t_sgp_funcionario f 
            WHERE id_funcionario = ? 
        """;
        try(Connection conn = ConnectionFactory.getConnection();
        PreparedStatement pr = conn.prepareStatement(sql)) {
            pr.setLong(1, id);
            ResultSet rs = pr.executeQuery();
            
            if (rs.next()) {
                return mapFuncionario(rs);
            }

            return null;
        } catch (Exception e) {
            throw new RuntimeException("Erro DAO ao obter Funcionario por id: " + e);
        }
    }

    @Override
    public List<Funcionario> getAll() {
        String sql = """
            SELECT f.*
            FROM t_sgp_funcionario f 
        """;

        try(Connection conn = ConnectionFactory.getConnection();
        PreparedStatement pr = conn.prepareStatement(sql)) {
            ResultSet rs = pr.executeQuery();

            List<Funcionario> listFuncionarios = new ArrayList<>();

            while (rs.next()) {
                listFuncionarios.add(mapFuncionario(rs));
            }

            return listFuncionarios;
        } catch (Exception e) {
            throw new RuntimeException("Erro DAO ao buscar todos os funcionários: " + e);
        }
    }

    private void fillInsertStatementParameters(PreparedStatement pr, Funcionario funcionario) throws SQLException{
        if (funcionario.getGerente() != null) {
            pr.setLong(1, funcionario.getGerente().getId());
        } else {
            pr.setNull(1, Types.INTEGER);

        }
        pr.setString(2, funcionario.getNome());
        pr.setDate(3, funcionario.getDataNascimento());
        pr.setString(4, funcionario.getCpf());
    }

    private Long insertFuncionario(Funcionario funcionario, Connection conn) {
        String sql = """
            INSERT INTO t_sgp_funcionario (
                id_gerente, nm_funcionario, dt_nascimento, nr_cpf
            ) VALUES (?, ?, ?, ?)
        """;

        try(PreparedStatement pr = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            fillInsertStatementParameters(pr, funcionario);
            pr.executeUpdate();
            ResultSet rs = pr.getGeneratedKeys();
            if (rs.next()) {
                return rs.getLong(1);
            }

            return null;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao inserir funcionário: " + e);
        }
    }

    private void updateFuncionario(Funcionario funcionario, Connection conn) {
        String sql = """
            UPDATE t_sgp_funcionario 
            SET id_gerente = ?, nm_funcionario = ?, dt_nascimento = ?, nr_cpf = ? 
            WHERE id_funcionario = ?
        """;
        try(PreparedStatement pr = conn.prepareStatement(sql)) {
            fillUpdateStatementParameters(pr, funcionario);
            pr.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar Funcionario: " + e);
        }
    }

    private void fillUpdateStatementParameters(PreparedStatement pr, Funcionario funcionario) throws SQLException {
        fillInsertStatementParameters(pr, funcionario);
        pr.setLong(5, funcionario.getId());
    }

    private Funcionario mapFuncionario(ResultSet rs) throws SQLException{
        Funcionario funcionario = new Funcionario();

        funcionario.setId(rs.getLong("id_funcionario"));
        funcionario.setNome(rs.getString("nm_funcionario"));
        funcionario.setCpf(rs.getString("nr_cpf"));
        funcionario.setDataNascimento(rs.getDate("dt_nascimento"));

        long idGerente = rs.getLong("id_gerente");
        if (!rs.wasNull()) {  // Verifica se existe gerente
            Gerente chefe = new Gerente();
            chefe.setId(idGerente); 
            funcionario.setGerente(chefe);
        }

        return funcionario;
    }
}

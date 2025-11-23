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
            if (t instanceof Gerente) {
                insertGerente((Gerente) t, conn);
            }
            return t;
        } catch (Exception e) {
            throw new RuntimeException("Erro DAO ao criar funcionário: " + e);
        }
    }
    
    @Override
    public void delete(Long id) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            // Primeiro, deletar da tabela de gerentes caso exista
            String deleteGerenteSql = "DELETE FROM t_sgp_gerente WHERE FUNCIONARIO_id_funcionario = ?";
            try (PreparedStatement pr = conn.prepareStatement(deleteGerenteSql)) {
                pr.setLong(1, id);
                pr.executeUpdate();
            }

            // Deletar da tabela de funcionários
            String deleteFuncionarioSql = "DELETE FROM t_sgp_funcionario WHERE id_funcionario = ?";
            try (PreparedStatement pr = conn.prepareStatement(deleteFuncionarioSql)) {
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
            if (t instanceof Gerente) {
                updateGerente((Gerente) t, conn);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar Funcionario: " + e);
        }
    }

    @Override
    public Funcionario getById(Long id) {
        String sql = """
            SELECT f.*, 
                gf.ds_senha AS chefe_senha, gf.nm_login AS chefe_login,   -- Gerente do funcionário
                g.ds_senha AS gerente_senha, g.nm_login AS gerente_login  -- Se o funcionário for gerente
            FROM t_sgp_funcionario f 
            LEFT JOIN t_sgp_gerente gf ON gf.FUNCIONARIO_id_funcionario = f.FUNCIONARIO_id_gerente
            LEFT JOIN t_sgp_gerente g ON g.FUNCIONARIO_id_funcionario = f.id_funcionario
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
            SELECT f.*, 
                gf.ds_senha AS chefe_senha, gf.nm_login AS chefe_login,   -- Gerente do funcionário
                g.ds_senha AS gerente_senha, g.nm_login AS gerente_login -- Se o funcionário for gerente
            FROM t_sgp_funcionario f 
            LEFT JOIN t_sgp_gerente gf ON gf.FUNCIONARIO_id_funcionario = f.FUNCIONARIO_id_gerente
            LEFT JOIN t_sgp_gerente g ON g.FUNCIONARIO_id_funcionario = f.id_funcionario
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
                FUNCIONARIO_id_gerente, nm_funcionario, dt_nascimento, nr_cpf
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

    private void insertGerente(Gerente gerente, Connection conn) {
        String sql = """
            INSERT INTO t_sgp_gerente (
                FUNCIONARIO_id_funcionario, ds_senha, nm_login
            ) VALUES (?, ?, ?)     
        """;

        try (PreparedStatement pr = conn.prepareStatement(sql)) {
            pr.setLong(1, gerente.getId());
            pr.setString(2, gerente.getSenha());
            pr.setString(3, gerente.getLogin());

            pr.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao inserir Gerente: " + e);
        }
    }

    private void updateFuncionario(Funcionario funcionario, Connection conn) {
        String sql = """
            UPDATE t_sgp_funcionario 
            SET FUNCIONARIO_id_gerente = ?, nm_funcionario = ?, dt_nascimento = ?, nr_cpf = ? 
            WHERE id_funcionario = ?
        """;
        try(PreparedStatement pr = conn.prepareStatement(sql)) {
            fillUpdateStatementParameters(pr, funcionario);
            pr.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar Funcionario: " + e);
        }
    }

    private void updateGerente(Gerente gerente, Connection conn) {
        String sql = """
            UPDATE t_sgp_gerente   
            SET nm_login = ?, ds_senha = ?   
            WHERE FUNCIONARIO_id_funcionario = ?
        """;
        try (PreparedStatement pr = conn.prepareStatement(sql)) {
            pr.setString(1, gerente.getLogin());
            pr.setString(2, gerente.getSenha());
            pr.setLong(3, gerente.getId());

            pr.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar Gerente: " + e);
        }
    }

    private void fillUpdateStatementParameters(PreparedStatement pr, Funcionario funcionario) throws SQLException {
        fillInsertStatementParameters(pr, funcionario);
        pr.setLong(5, funcionario.getId());
    }

    private Funcionario mapFuncionario(ResultSet rs) throws SQLException{
        Funcionario funcionario;

        if (rs.getString("gerente_login") != null) {
            Gerente gerente = new Gerente();
            gerente.setLogin(rs.getString("gerente_login"));
            gerente.setSenha(rs.getString("gerente_senha"));
            funcionario = gerente;
        } else {
            funcionario = new Funcionario();
        }

        funcionario.setId(rs.getLong("id_funcionario"));
        funcionario.setNome(rs.getString("nm_funcionario"));
        funcionario.setCpf(rs.getString("nr_cpf"));
        funcionario.setDataNascimento(rs.getDate("dt_nascimento"));

        if (rs.getString("chefe_login") != null) {
            Gerente chefe = new Gerente();
            chefe.setLogin(rs.getString("chefe_login"));
            chefe.setSenha(rs.getString("chefe_senha"));
            chefe.setId(rs.getLong("FUNCIONARIO_id_gerente"));
            funcionario.setGerente(chefe);
        }

        return funcionario;
    }
}

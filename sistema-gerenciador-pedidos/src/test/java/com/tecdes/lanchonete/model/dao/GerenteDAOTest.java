package com.tecdes.lanchonete.model.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

import org.h2.tools.RunScript;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.tecdes.lanchonete.config.ConnectionFactory;
import com.tecdes.lanchonete.model.entity.Funcionario;
import com.tecdes.lanchonete.model.entity.Gerente;

@DisplayName("Testes de Integração para GerenteDAO")
public class GerenteDAOTest {

    private GerenteDAO gerenteDAO;

    @BeforeEach
    void setup() throws Exception {
        gerenteDAO = new GerenteDAO();

        try (Connection conn = ConnectionFactory.getConnection()) {
            RunScript.execute(conn, new FileReader("src/test/resources/script.sql"));
        }
    }

    @Test
    @DisplayName("Deve criar gerente corretamente")
    void deveCriarGerente() {
        // Arrange
        Gerente gerente = criarGerente("admin", "123");

        // Act
        gerenteDAO.create(gerente);
        Gerente criado = gerenteDAO.getById(gerente.getId());

        // Assert
        verificarGerente(gerente, criado);
    }

    @Test
    @DisplayName("Não deve criar gerente com campos obrigatórios nulos")
    void naoDeveCriarGerenteComCamposNulos() {
        Gerente loginNulo = criarGerente(null, "123");
        Gerente senhaNula = criarGerente("admin", null);

        assertThrows(Exception.class, () -> gerenteDAO.create(loginNulo), "Login nulo deve gerar exceção");
        assertThrows(Exception.class, () -> gerenteDAO.create(senhaNula), "Senha nula deve gerar exceção");
    }

    @Test
    @DisplayName("Deve atualizar gerente existente")
    void deveAtualizarGerente() {
        // Arrange
        Gerente gerente = criarGerente("admin", "123");
        gerenteDAO.create(gerente);

        // Act
        gerente.setLogin("novoLogin");
        gerente.setSenha("novaSenha");
        gerenteDAO.update(gerente);

        // Assert
        Gerente atualizado = gerenteDAO.getById(gerente.getId());
        verificarGerente(gerente, atualizado);
    }

    @Test
    @DisplayName("Deve deletar gerente existente")
    void deveDeletarGerente() {
        // Arrange
        Gerente gerente = criarGerente("admin", "123");
        gerenteDAO.create(gerente);

        // Act
        gerenteDAO.delete(gerente.getId());
        List<Gerente> lista = gerenteDAO.getAll();

        // Assert
        assertEquals(0, lista.size(), "Não deve haver nenhum gerente no banco");
    }

    @Test
    @DisplayName("Deve buscar gerente por ID")
    void deveBuscarGerentePorId() {
        // Arrange
        Gerente gerente = criarGerente("admin", "123");
        gerenteDAO.create(gerente);

        // Act
        Gerente encontrado = gerenteDAO.getById(gerente.getId());

        // Assert
        verificarGerente(gerente, encontrado);
    }

    @Test
    @DisplayName("Deve buscar todos os gerentes corretamente")
    void deveBuscarTodosGerentes() {
        // Arrange
        Gerente g1 = criarGerente("login1", "senha1");
        Gerente g2 = criarGerente("login2", "senha2");
        Gerente g3 = criarGerente("login3", "senha3");

        gerenteDAO.create(g1);
        gerenteDAO.create(g2);
        gerenteDAO.create(g3);

        // Act
        List<Gerente> todos = gerenteDAO.getAll();
        todos.sort((a,b) -> a.getLogin().compareTo(b.getLogin()));

        List<Gerente> esperado = Arrays.asList(g1, g2, g3);
        esperado.sort((a,b) -> a.getLogin().compareTo(b.getLogin()));

        // Assert
        for (int i = 0; i < esperado.size(); i++) {
            verificarGerente(esperado.get(i), todos.get(i));
        }
    }

    // ----------------------------------------------------
    // MÉTODOS AUXILIARES
    // ----------------------------------------------------

    private Gerente criarGerente(String login, String senha) {
        Gerente g = new Gerente();
        g.setId(mapFuncinarioBase());
        g.setLogin(login);
        g.setSenha(senha);
        return g;
    }

    private void verificarGerente(Gerente esperado, Gerente atual) {
        assertEquals(esperado.getLogin(), atual.getLogin(), "Login incorreto");
        assertEquals(esperado.getSenha(), atual.getSenha(), "Senha incorreta");
    }

    private Long mapFuncinarioBase() {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome("Funcionario");
        funcionario.setCpf("123.123.123-12");
        funcionario.setDataNascimento(Date.valueOf("2000-11-12"));
        return salvarFuncionarioBase(funcionario);
    }

    private Long salvarFuncionarioBase(Funcionario funcionario) {
        String sql = """
            INSERT INTO t_sgp_funcionario (
                nm_funcionario, dt_nascimento, nr_cpf
            ) VALUES (?, ?, ?)
        """;

        try (Connection conn = ConnectionFactory.getConnection()) {
            PreparedStatement pr = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pr.setString(1, funcionario.getNome());
            pr.setDate(2, funcionario.getDataNascimento());
            pr.setString(3, funcionario.getCpf());
            pr.executeUpdate();

            ResultSet rs = pr.getGeneratedKeys();
            if (rs.next()) {
                return rs.getLong(1);
            }
            throw new RuntimeException("Erro ao gerar ID de funcionário");
        } catch (Exception e) {
            throw new RuntimeException("Erro ao inserir funcionário base: " + e);
        }
    }
}

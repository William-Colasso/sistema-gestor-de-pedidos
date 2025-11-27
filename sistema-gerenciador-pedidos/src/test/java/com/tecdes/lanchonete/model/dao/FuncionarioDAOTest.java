package com.tecdes.lanchonete.model.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.h2.tools.RunScript;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.tecdes.lanchonete.config.ConnectionFactory;
import com.tecdes.lanchonete.model.entity.Funcionario;
import com.tecdes.lanchonete.model.entity.Gerente;

@DisplayName("Testes de Integração para FuncionarioDAO - GetAll")
public class FuncionarioDAOTest {
    /*
     * Teste de integração para FuncionarioDAO
     * - Usa H2 em memória (definido em src/test/resources/db.properties)
     * - Não altera o código e o banco real
     */

    private FuncionarioDAO funcionarioDAO;

    @BeforeEach
    void setup() throws Exception {
        funcionarioDAO = new FuncionarioDAO();

        try (Connection conn = ConnectionFactory.getConnection();
             Statement st = conn.createStatement()) {
            RunScript.execute(conn, new FileReader("src/test/resources/script.sql"));
        }
    }

    @Test
    @DisplayName("Deve criar funcionário simples corretamente")
    void deveCriarFuncionarioSimples() {
        // Arrange
        Gerente g = criarGerente("Gerente");
        funcionarioDAO.create(g);
        Funcionario esperado = criarFuncionario("Funcionário Simples", g);

        // Act
        funcionarioDAO.create(esperado);
        Funcionario criado = funcionarioDAO.getById(esperado.getId());

        // Assert
        verificarFuncionario(esperado, criado);
    }

    @Test
    @DisplayName("Não deve criar funcionário com campos obrigatórios nulos")
    void naoDeveCriarFuncionarioComCamposNulos() {
        // Arrange
        Funcionario nomeNulo = criarFuncionario(null, criarGerente("Gerente"));

        Funcionario cpfNulo = criarFuncionario("Cpf nulo", criarGerente("Gerente"));
        cpfNulo.setCpf(null);

        Funcionario dataNascNulo = criarFuncionario("Data nulo", criarGerente("Gerente"));
        dataNascNulo.setDataNascimento(null);

        // Act / Assert
        assertThrows(Exception.class, () -> funcionarioDAO.create(nomeNulo), "Nome nulo deve gerar exceções");
        assertThrows(Exception.class, () -> funcionarioDAO.create(cpfNulo), "CPF nulo deve gerar exceções");
        assertThrows(Exception.class, () -> funcionarioDAO.create(dataNascNulo), "Data de nascimento nulo deve gerar exceções");
    }

    @Test
    @DisplayName("Deve atualizar funcionário existente")
    void deveAtualizarFuncionario() {
        // Arrange
        Gerente g = criarGerente("Gerente");
        funcionarioDAO.create(g);
        Funcionario f = criarFuncionario("Antigo Nome", g);
        funcionarioDAO.create(f);

        // Act
        f.setNome("Nome Atualizado");
        funcionarioDAO.update(f);

        // Assert
        Funcionario atualizado = funcionarioDAO.getById(f.getId());
        assertEquals("Nome Atualizado", atualizado.getNome());
    }

    @Test
    @DisplayName("Deve deletar funcionário existente")
    void deveDeletarFuncionario() {
        // Arrange
        Gerente g = criarGerente("Gerente");
        funcionarioDAO.create(g);
        Funcionario f = criarFuncionario("Funcionário para deletar", g);

        // Act
        funcionarioDAO.create(f);
        funcionarioDAO.delete(f.getId());

        List<Funcionario> lista = funcionarioDAO.getAll();

        // Assert
        assertEquals(1, lista.size(), "Deve haver apenas o Gerente no banco, o Funcionário foi removido");

        // Act
        funcionarioDAO.delete(g.getId());
        lista = funcionarioDAO.getAll();

        // Assert
        assertEquals(0, lista.size(), "Não deve haver nenhum funcionário no banco");
    }

    @Test
    @DisplayName("Deve buscar funcionário por ID")
    void deveBuscarFuncionarioPorId() {
        // Arrange
        Gerente g = criarGerente("Gerente");
        funcionarioDAO.create(g);
        Funcionario f = criarFuncionario("Funcionario Id", g);

        // Act
        funcionarioDAO.create(f);
        Funcionario encontrado = funcionarioDAO.getById(f.getId());

        // Assert
        assertEquals(f.getNome(), encontrado.getNome());
        assertEquals(f.getCpf(), encontrado.getCpf());
    }

    @Test
    @DisplayName("Deve buscar todos os funcionários corretamente com gerentes")
    void deveBuscarTodosFuncionarios() {
        // Arrange
        Gerente gerente1 = criarGerente("Gerente 1");

        Funcionario func1 = criarFuncionario("Funcionario 1", gerente1);
        Funcionario func2 = criarFuncionario("Funcionario 2", gerente1);

        // Act
        funcionarioDAO.create(gerente1);
        funcionarioDAO.create(func1);
        funcionarioDAO.create(func2);
        List<Funcionario> todos = funcionarioDAO.getAll();
        todos.sort(Comparator.comparing(Funcionario::getId));
        List<Funcionario> esperado = Arrays.asList(gerente1, func1, func2);
        esperado.sort(Comparator.comparing(Funcionario::getId));

        // Assert: Verifica campos de cada funcionário, incluindo gerente 
        for (int i = 0; i < esperado.size(); i++) {
            verificarFuncionario(esperado.get(i), todos.get(i));
        }
    }

    // ---------------- MÉTODOS AUXILIARES ----------------

    private Funcionario criarFuncionario(String nome, Gerente gerente) {
        Funcionario f = new Funcionario();
        f.setNome(nome);
        f.setCpf("123.123.123-12");
        f.setDataNascimento(Date.valueOf("1990-01-01"));
        f.setGerente(gerente);
        return f;
    }

    private Gerente criarGerente(String nome) {
        Gerente g = new Gerente();
        g.setNome(nome);
        g.setCpf("999.999.999-99");
        g.setDataNascimento(Date.valueOf("1985-01-01"));
        g.setLogin("Gerente");
        g.setSenha("123");
        g.setGerente(null);
        return g;
    }

    private void verificarFuncionario(Funcionario esperado, Funcionario atual) {
        assertEquals(esperado.getNome(), atual.getNome());
        assertEquals(esperado.getCpf(), atual.getCpf());
        assertEquals(esperado.getDataNascimento(), atual.getDataNascimento());
        if (esperado.getGerente() != null) {
            assertEquals(esperado.getGerente().getId(), atual.getGerente().getId());
        } else {
            assertEquals(null, atual.getGerente());
        }
    }
}

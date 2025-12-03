package com.tecdes.lanchonete.model.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
import com.tecdes.lanchonete.model.entity.Cliente;

@DisplayName("Testes de Integração para ClienteDAO")
public class ClienteDAOTest {

    private ClienteDAO clienteDAO;

    @BeforeEach
    void setup() throws Exception {
        clienteDAO = new ClienteDAO();

        try (Connection conn = ConnectionFactory.getConnection();
             Statement st = conn.createStatement()) {
            RunScript.execute(conn, new FileReader("src/test/resources/script.sql"));
        }
    }

    @Test
    @DisplayName("Deve criar cliente corretamente")
    void deveCriarCliente() {
        // Arrange
        Cliente cliente = criarCliente("João Silva", "123456789", "123.456.789-00");

        // Act
        clienteDAO.create(cliente);
        assertNotNull(cliente.getId(), "ID deve ser gerado pelo banco");
        Cliente criado = clienteDAO.getById(cliente.getId());

        // Assert
        verificarCliente(cliente, criado);
    }

    
    @Test
    @DisplayName("Deve atualizar cliente existente")
    void deveAtualizarCliente() {
        // Arrange
        Cliente cliente = criarCliente("Ana", "111222333", "111.222.333-44");
        clienteDAO.create(cliente);

        // Act
        cliente.setNome("Ana Atualizada");
        cliente.setTelefone("999888777");
        clienteDAO.update(cliente);

        // Assert
        Cliente atualizado = clienteDAO.getById(cliente.getId());
        assertEquals("Ana Atualizada", atualizado.getNome());
        assertEquals("999888777", atualizado.getTelefone());
        assertEquals("111.222.333-44", atualizado.getCpf());
    }

    @Test
    @DisplayName("Deve deletar cliente existente")
    void deveDeletarCliente() {
        // Arrange
        Cliente cliente = criarCliente("Pedro", "123123123", "123.123.123-99");
        clienteDAO.create(cliente);

        // Act
        clienteDAO.delete(cliente.getId());
        List<Cliente> lista = clienteDAO.getAll();

        // Assert
        assertEquals(0, lista.size(), "Não deve haver nenhum cliente no banco");
    }

    @Test
    @DisplayName("Deve buscar cliente por ID")
    void deveBuscarClientePorId() {
        // Arrange
        Cliente cliente = criarCliente("Lucas", "456456456", "456.456.456-11");
        clienteDAO.create(cliente);

        // Act
        Cliente encontrado = clienteDAO.getById(cliente.getId());

        // Assert
        verificarCliente(cliente, encontrado);
    }

    @Test
    @DisplayName("Deve buscar todos os clientes corretamente")
    void deveBuscarTodosClientes() {
        // Arrange
        Cliente c1 = criarCliente("Cliente 1", "111111111", "111.111.111-11");
        Cliente c2 = criarCliente("Cliente 2", "222222222", "222.222.222-22");
        Cliente c3 = criarCliente("Cliente 3", "333333333", "333.333.333-33");

        // Act
        clienteDAO.create(c1);
        clienteDAO.create(c2);
        clienteDAO.create(c3);

        List<Cliente> todos = clienteDAO.getAll();
        todos.sort(Comparator.comparing(Cliente::getId));
        List<Cliente> esperado = Arrays.asList(c1, c2, c3);
        esperado.sort(Comparator.comparing(Cliente::getId));

        // Assert
        for (int i = 0; i < esperado.size(); i++) {
            verificarCliente(esperado.get(i), todos.get(i));
        }
    }

    // ---------------- MÉTODOS AUXILIARES ----------------

    private Cliente criarCliente(String nome, String telefone, String cpf) {
        Cliente c = new Cliente();
        c.setNome(nome);
        c.setTelefone(telefone);
        c.setCpf(cpf);
        c.setDataRegistro(Date.valueOf("2025-01-01"));
        return c;
    }

    private void verificarCliente(Cliente esperado, Cliente atual) {
        assertEquals(esperado.getNome(), atual.getNome());
        assertEquals(esperado.getCpf(), atual.getCpf());
        assertEquals(esperado.getTelefone(), atual.getTelefone());
        assertEquals(esperado.getDataRegistro(), atual.getDataRegistro());
    }
}

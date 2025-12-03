package com.tecdes.lanchonete.model.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.h2.tools.RunScript;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.tecdes.lanchonete.config.ConnectionFactory;
import com.tecdes.lanchonete.model.entity.Parceiro;

@DisplayName("Testes de Integração para ParceiroDAO")
public class ParceiroDAOTest {

    private ParceiroDAO parceiroDAO;

    @BeforeEach
    void setup() throws Exception {
        parceiroDAO = new ParceiroDAO();

        try (Connection conn = ConnectionFactory.getConnection();
             Statement st = conn.createStatement()) {
            RunScript.execute(conn, new FileReader("src/test/resources/script.sql"));
        }
    }

    @Test
    @DisplayName("Deve criar parceiro corretamente")
    void deveCriarParceiro() {
        // Arrange
        Parceiro parceiro = criarParceiro("Parceiro A", "parceiroA@email.com", "111111111");

        // Act
        parceiroDAO.create(parceiro);
        assertNotNull(parceiro.getId(), "ID deve ser gerado pelo banco");
        Parceiro criado = parceiroDAO.getById(parceiro.getId());

        // Assert
        verificarParceiro(parceiro, criado);
    }

    @Test
    @DisplayName("Não deve criar parceiro com campos obrigatórios nulos")
    void naoDeveCriarParceiroComCamposNulos() {
        Parceiro nomeNulo = criarParceiro(null, "email@email.com", "123456789");
        Parceiro emailNulo = criarParceiro("Parceiro B", null, "123456789");
        Parceiro telefoneNulo = criarParceiro("Parceiro C", "email@email.com", null);

        assertThrows(Exception.class, () -> parceiroDAO.create(nomeNulo), "Nome nulo deve gerar exceção");
        assertThrows(Exception.class, () -> parceiroDAO.create(emailNulo), "Email nulo deve gerar exceção");
        assertThrows(Exception.class, () -> parceiroDAO.create(telefoneNulo), "Telefone nulo deve gerar exceção");
    }

    @Test
    @DisplayName("Deve atualizar parceiro existente")
    void deveAtualizarParceiro() {
        // Arrange
        Parceiro parceiro = criarParceiro("Parceiro D", "d@email.com", "444444444");
        parceiroDAO.create(parceiro);

        // Act
        parceiro.setNome("Parceiro D Atualizado");
        parceiro.setEmail("d_atualizado@email.com");
        parceiro.setTelefone("555555555");
        parceiroDAO.update(parceiro);

        // Assert
        Parceiro atualizado = parceiroDAO.getById(parceiro.getId());
        assertEquals("Parceiro D Atualizado", atualizado.getNome());
        assertEquals("d_atualizado@email.com", atualizado.getEmail());
        assertEquals("555555555", atualizado.getTelefone());
    }

    @Test
    @DisplayName("Deve deletar parceiro existente")
    void deveDeletarParceiro() {
        // Arrange
        Parceiro parceiro = criarParceiro("Parceiro E", "e@email.com", "666666666");
        parceiroDAO.create(parceiro);

        // Act
        parceiroDAO.delete(parceiro.getId());
        List<Parceiro> lista = parceiroDAO.getAll();

        // Assert
        assertEquals(0, lista.size(), "Não deve haver nenhum parceiro no banco");
    }

    @Test
    @DisplayName("Deve buscar parceiro por ID")
    void deveBuscarParceiroPorId() {
        // Arrange
        Parceiro parceiro = criarParceiro("Parceiro F", "f@email.com", "777777777");
        parceiroDAO.create(parceiro);

        // Act
        Parceiro encontrado = parceiroDAO.getById(parceiro.getId());

        // Assert
        verificarParceiro(parceiro, encontrado);
    }

    @Test
    @DisplayName("Deve buscar todos os parceiros corretamente")
    void deveBuscarTodosParceiros() {
        // Arrange
        Parceiro p1 = criarParceiro("Parceiro 1", "p1@email.com", "111111111");
        Parceiro p2 = criarParceiro("Parceiro 2", "p2@email.com", "222222222");
        Parceiro p3 = criarParceiro("Parceiro 3", "p3@email.com", "333333333");

        // Act
        parceiroDAO.create(p1);
        parceiroDAO.create(p2);
        parceiroDAO.create(p3);

        List<Parceiro> todos = parceiroDAO.getAll();
        todos.sort(Comparator.comparing(Parceiro::getId));
        List<Parceiro> esperado = Arrays.asList(p1, p2, p3);
        esperado.sort(Comparator.comparing(Parceiro::getId));

        // Assert
        for (int i = 0; i < esperado.size(); i++) {
            verificarParceiro(esperado.get(i), todos.get(i));
        }
    }

    // ---------------- MÉTODOS AUXILIARES ----------------

    private Parceiro criarParceiro(String nome, String email, String telefone) {
        Parceiro p = new Parceiro();
        p.setNome(nome);
        p.setEmail(email);
        p.setTelefone(telefone);
        return p;
    }

    private void verificarParceiro(Parceiro esperado, Parceiro atual) {
        assertEquals(esperado.getNome(), atual.getNome());
        assertEquals(esperado.getEmail(), atual.getEmail());
        assertEquals(esperado.getTelefone(), atual.getTelefone());
    }
}

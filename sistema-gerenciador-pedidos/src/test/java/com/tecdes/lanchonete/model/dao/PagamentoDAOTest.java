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
import com.tecdes.lanchonete.model.entity.Pagamento;

@DisplayName("Testes de Integração para PagamentoDAO")
public class PagamentoDAOTest {

    private PagamentoDAO pagamentoDAO;

    @BeforeEach
    void setup() throws Exception {
        pagamentoDAO = new PagamentoDAO();

        try (Connection conn = ConnectionFactory.getConnection();
             Statement st = conn.createStatement()) {
            RunScript.execute(conn, new FileReader("src/test/resources/script.sql"));
        }
    }

    @Test
    @DisplayName("Deve criar pagamento corretamente")
    void deveCriarPagamento() {
        // Arrange
        Pagamento pagamento = criarPagamento("Cartão de Crédito", "CC");

        // Act
        pagamentoDAO.create(pagamento);
        assertNotNull(pagamento.getId(), "ID deve ser gerado pelo banco");
        Pagamento criado = pagamentoDAO.getById(pagamento.getId());

        // Assert
        verificarPagamento(pagamento, criado);
    }

    @Test
    @DisplayName("Não deve criar pagamento com campos obrigatórios nulos")
    void naoDeveCriarPagamentoComCamposNulos() {
        Pagamento nomeNulo = criarPagamento(null, "CC");
        Pagamento siglaNula = criarPagamento("Cartão Débito", null);

        assertThrows(Exception.class, () -> pagamentoDAO.create(nomeNulo), "Nome nulo deve gerar exceção");
        assertThrows(Exception.class, () -> pagamentoDAO.create(siglaNula), "Sigla nula deve gerar exceção");
    }

    @Test
    @DisplayName("Deve atualizar pagamento existente")
    void deveAtualizarPagamento() {
        // Arrange
        Pagamento pagamento = criarPagamento("Pix", "PX");
        pagamentoDAO.create(pagamento);

        // Act
        pagamento.setNome("Pix Atualizado");
        pagamento.setSigla("PXU");
        pagamentoDAO.update(pagamento);

        // Assert
        Pagamento atualizado = pagamentoDAO.getById(pagamento.getId());
        assertEquals("Pix Atualizado", atualizado.getNome());
        assertEquals("PXU", atualizado.getSigla());
    }

    @Test
    @DisplayName("Deve deletar pagamento existente")
    void deveDeletarPagamento() {
        // Arrange
        Pagamento pagamento = criarPagamento("Boleto", "BL");
        pagamentoDAO.create(pagamento);

        // Act
        pagamentoDAO.delete(pagamento.getId());
        List<Pagamento> lista = pagamentoDAO.getAll();

        // Assert
        assertEquals(0, lista.size(), "Não deve haver nenhum pagamento no banco");
    }

    @Test
    @DisplayName("Deve buscar pagamento por ID")
    void deveBuscarPagamentoPorId() {
        // Arrange
        Pagamento pagamento = criarPagamento("Dinheiro", "DN");
        pagamentoDAO.create(pagamento);

        // Act
        Pagamento encontrado = pagamentoDAO.getById(pagamento.getId());

        // Assert
        verificarPagamento(pagamento, encontrado);
    }

    @Test
    @DisplayName("Deve buscar todos os pagamentos corretamente")
    void deveBuscarTodosPagamentos() {
        // Arrange
        Pagamento p1 = criarPagamento("Cartão Crédito", "CC");
        Pagamento p2 = criarPagamento("Cartão Débito", "CD");
        Pagamento p3 = criarPagamento("Pix", "PX");

        // Act
        pagamentoDAO.create(p1);
        pagamentoDAO.create(p2);
        pagamentoDAO.create(p3);

        List<Pagamento> todos = pagamentoDAO.getAll();
        todos.sort(Comparator.comparing(Pagamento::getId));
        List<Pagamento> esperado = Arrays.asList(p1, p2, p3);
        esperado.sort(Comparator.comparing(Pagamento::getId));

        // Assert
        for (int i = 0; i < esperado.size(); i++) {
            verificarPagamento(esperado.get(i), todos.get(i));
        }
    }

    // ---------------- MÉTODOS AUXILIARES ----------------

    private Pagamento criarPagamento(String nome, String sigla) {
        Pagamento p = new Pagamento();
        p.setNome(nome);
        p.setSigla(sigla);
        return p;
    }

    private void verificarPagamento(Pagamento esperado, Pagamento atual) {
        assertEquals(esperado.getNome(), atual.getNome());
        assertEquals(esperado.getSigla(), atual.getSigla());
    }
}

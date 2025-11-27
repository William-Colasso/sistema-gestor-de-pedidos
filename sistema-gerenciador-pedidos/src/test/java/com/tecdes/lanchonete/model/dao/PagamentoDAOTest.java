package com.tecdes.lanchonete.model.dao;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    @DisplayName("Deve buscar pagamento por ID")
    void deveBuscarPagamentoPorId() {
        // Arrange
        Pagamento pagamento = criarPagamento("Dinheiro", "DN");

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
        p.setImagem("midia-teste".getBytes());
        salvarPagamento(p);
        return p;
    }

    private void verificarPagamento(Pagamento esperado, Pagamento atual) {
        assertEquals(esperado.getNome(), atual.getNome());
        assertEquals(esperado.getSigla(), atual.getSigla());
        assertArrayEquals(esperado.getImagem(), atual.getImagem());
    }


    private Pagamento salvarPagamento(Pagamento t) {
        try (Connection conn = ConnectionFactory.getConnection()) {

            String sql = "INSERT INTO T_SGP_FORMA_PAGAMENTO (nm_pagamento, sg_pagamento, sq_imagem) VALUES (?,?,?)";
            PreparedStatement pr = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pr.setString(1, t.getNome());
            pr.setString(2, t.getSigla());
            pr.setBytes(3, t.getImagem()); 

            pr.executeUpdate();

            try (ResultSet rs = pr.getGeneratedKeys()) {
                if (rs.next()) {
                    t.setId(rs.getLong(1));
                }
            }

            return t;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

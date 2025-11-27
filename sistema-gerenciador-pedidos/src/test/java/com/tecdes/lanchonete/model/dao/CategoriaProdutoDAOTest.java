package com.tecdes.lanchonete.model.dao;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
import com.tecdes.lanchonete.model.entity.CategoriaProduto;

@DisplayName("Testes de Integração para CategoriaProdutoDAO")
public class CategoriaProdutoDAOTest {

    private CategoriaProdutoDAO categoriaDAO;

    @BeforeEach
    void setup() throws Exception {
        categoriaDAO = new CategoriaProdutoDAO();

        try (Connection conn = ConnectionFactory.getConnection();
             Statement st = conn.createStatement()) {
            RunScript.execute(conn, new FileReader("src/test/resources/script.sql"));
        }
    }

    @Test
    @DisplayName("Deve criar categoria de produto corretamente")
    void deveCriarCategoria() {
        // Arrange
        CategoriaProduto categoria = criarCategoria("Bebidas", "BB");

        // Act
        categoriaDAO.create(categoria);
        CategoriaProduto criado = categoriaDAO.getById(categoria.getId());

        // Assert
        verificarCategoria(categoria, criado);
        assertNotNull(criado.getId(), "ID deve ser gerado pelo banco");
    }

    @Test
    @DisplayName("Não deve criar categoria com campos obrigatórios nulos")
    void naoDeveCriarCategoriaComCamposNulos() {
        CategoriaProduto nomeNulo = criarCategoria(null, "SG");

        assertThrows(Exception.class, () -> categoriaDAO.create(nomeNulo), "Nome nulo deve gerar exceção");

        CategoriaProduto siglaNula = criarCategoria("nome", null);

        assertThrows(Exception.class, () -> categoriaDAO.create(siglaNula), "Nome nulo deve gerar exceção");
    }

    @Test
    @DisplayName("Deve atualizar categoria existente")
    void deveAtualizarCategoria() {
        // Arrange
        CategoriaProduto categoria = criarCategoria("Doces", "DC");
        categoriaDAO.create(categoria);

        // Act
        categoria.setNome("Doces Atualizados");
        categoria.setSigla("DA");
        categoriaDAO.update(categoria);

        // Assert
        CategoriaProduto atualizado = categoriaDAO.getById(categoria.getId());
        assertEquals("Doces Atualizados", atualizado.getNome());
        assertEquals("DA", atualizado.getSigla());
    }

    @Test
    @DisplayName("Deve deletar categoria existente")
    void deveDeletarCategoria() {
        // Arrange
        CategoriaProduto categoria = criarCategoria("Salgados", "SG");
        categoriaDAO.create(categoria);

        // Act
        categoriaDAO.delete(categoria.getId());
        List<CategoriaProduto> lista = categoriaDAO.getAll();

        // Assert
        assertEquals(0, lista.size(), "Não deve haver nenhuma categoria no banco");
    }

    @Test
    @DisplayName("Deve buscar categoria por ID")
    void deveBuscarCategoriaPorId() {
        // Arrange
        CategoriaProduto categoria = criarCategoria("Laticínios", "LT");
        categoriaDAO.create(categoria);

        // Act
        CategoriaProduto encontrado = categoriaDAO.getById(categoria.getId());

        // Assert
        verificarCategoria(categoria, encontrado);
    }

    @Test
    @DisplayName("Deve buscar todas as categorias corretamente")
    void deveBuscarTodasCategorias() {
        // Arrange
        CategoriaProduto cat1 = criarCategoria("Bebidas", "BB");
        CategoriaProduto cat2 = criarCategoria("Doces", "DC");
        CategoriaProduto cat3 = criarCategoria("Salgados", "SG");

        // Act
        categoriaDAO.create(cat1);
        categoriaDAO.create(cat2);
        categoriaDAO.create(cat3);

        List<CategoriaProduto> todas = categoriaDAO.getAll();
        todas.sort(Comparator.comparing(CategoriaProduto::getId));
        List<CategoriaProduto> esperado = Arrays.asList(cat1, cat2, cat3);
        esperado.sort(Comparator.comparing(CategoriaProduto::getId));

        // Assert
        for (int i = 0; i < esperado.size(); i++) {
            verificarCategoria(esperado.get(i), todas.get(i));
        }
    }

    // ---------------- MÉTODOS AUXILIARES ----------------

    private CategoriaProduto criarCategoria(String nome, String sigla) {
        CategoriaProduto c = new CategoriaProduto();
        c.setNome(nome);
        c.setSigla(sigla);
        c.setImagem("midia-teste".getBytes());
        return c;
    }

    private void verificarCategoria(CategoriaProduto esperado, CategoriaProduto atual) {
        assertEquals(esperado.getNome(), atual.getNome());
        assertEquals(esperado.getSigla(), atual.getSigla());
        assertArrayEquals(esperado.getImagem(), atual.getImagem());
    }
}

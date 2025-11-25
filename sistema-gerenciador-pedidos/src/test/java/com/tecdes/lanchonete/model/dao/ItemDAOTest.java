package com.tecdes.lanchonete.model.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.Date;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.h2.tools.RunScript;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.tecdes.lanchonete.config.ConnectionFactory;
import com.tecdes.lanchonete.model.entity.Combo;
import com.tecdes.lanchonete.model.entity.Item;
import com.tecdes.lanchonete.model.entity.Produto;
import com.tecdes.lanchonete.model.enums.TipoItem;

@DisplayName("Testes de Integração para ItemDAO")
public class ItemDAOTest {

    private ItemDAO itemDAO;

    @BeforeEach
    void setup() throws Exception {
        itemDAO = new ItemDAO();
        try (Connection conn = ConnectionFactory.getConnection()) {
            RunScript.execute(conn, new FileReader("src/test/resources/script.sql"));
        }
    }

    @Test
    @DisplayName("Deve criar produto corretamente")
    void deveCriarProduto() {
        Produto produto = criarProduto("Produto Teste");

        itemDAO.create(produto);

        Produto criado = (Produto) itemDAO.getById(produto.getId());
        verificar(produto, criado);
    }

    @Test
    @DisplayName("Deve criar combo corretamente")
    void deveCriarCombo() {
        Combo combo = criarCombo("Combo Teste");

        itemDAO.create(combo);

        Combo criado = (Combo) itemDAO.getById(combo.getId());
        verificar(combo, criado);
    }

    @Test
    @DisplayName("Deve atualizar item existente")
    void deveAtualizarItem() {
        Produto produto = criarProduto("Produto Atualizar");
        itemDAO.create(produto);

        produto.setNome("Produto Atualizado");
        produto.setDescricao("Descrição Atualizada");
        itemDAO.update(produto);

        Produto atualizado = (Produto) itemDAO.getById(produto.getId());
        assertEquals("Produto Atualizado", atualizado.getNome());
        assertEquals("Descrição Atualizada", atualizado.getDescricao());
    }

    @Test
    @DisplayName("Deve deletar item existente")
    void deveDeletarItem() {
        Produto produto = criarProduto("Produto Delete");
        itemDAO.create(produto);

        itemDAO.delete(produto.getId());
        Item deletado = itemDAO.getById(produto.getId());
        assertNull(deletado, "Item deve ser removido do banco");
    }

    @Test
    @DisplayName("Deve buscar todos os itens")
    void deveBuscarTodosItens() {
        Produto p1 = criarProduto("Produto 1");
        Combo c1 = criarCombo("Combo 1");

        itemDAO.create(p1);
        itemDAO.create(c1);

        List<Item> atual = itemDAO.getAll();
        List<Item> esperado = Arrays.asList(p1, c1);
        esperado.sort(Comparator.comparing(Item::getId));
        atual.sort(Comparator.comparing(Item::getId));

        // Assert
        for (int i = 0; i < esperado.size(); i++) {
            verificar(esperado.get(i), atual.get(i));
        }
    }

    // ---------------- MÉTODOS AUXILIARES ----------------

    private Produto criarProduto(String nome) {
        Produto p = new Produto();
        p.setNome(nome);
        p.setDescricao("Descrição " + nome);
        p.setTipoItem(TipoItem.PRODUTO);
        p.setDataCriacao(new Date(System.currentTimeMillis()));
        p.setStatusAtivo(1);
        return p;
    }

    private Combo criarCombo(String nome) {
        Combo c = new Combo();
        c.setNome(nome);
        c.setDescricao("Descrição " + nome);
        c.setTipoItem(TipoItem.COMBO);
        c.setDataCriacao(new Date(System.currentTimeMillis()));
        c.setStatusAtivo(1);
        return c;
    }

    private void verificar(Item esperado, Item atual) {
        assertNotNull(atual, "O item retornado não pode ser nulo");

        assertEquals(esperado.getId(), atual.getId(), "IDs devem coincidir");
        assertEquals(esperado.getNome(), atual.getNome(), "Nomes devem coincidir");
        assertEquals(esperado.getDescricao(), atual.getDescricao(), "Descrições devem coincidir");
        assertEquals(esperado.getTipoItem(), atual.getTipoItem(), "Tipos devem coincidir");
        assertEquals(esperado.getStatusAtivo(), atual.getStatusAtivo(), "Status deve coincidir");
    }
}

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
import com.tecdes.lanchonete.model.entity.Item;
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
    @DisplayName("Deve criar item do tipo Produto corretamente")
    void deveCriarProduto() {
        Item produto = criarItem("Produto Teste", TipoItem.PRODUTO);

        itemDAO.create(produto);

        Item criado = itemDAO.getById(produto.getId());
        verificar(produto, criado);
    }

    @Test
    @DisplayName("Deve criar item do tipo Combo corretamente")
    void deveCriarCombo() {
        Item combo = criarItem("Combo Teste", TipoItem.COMBO);

        itemDAO.create(combo);

        Item criado = itemDAO.getById(combo.getId());
        verificar(combo, criado);
    }

    @Test
    @DisplayName("Deve atualizar item existente")
    void deveAtualizarItem() {
        Item item = criarItem("Item Atualizar", TipoItem.PRODUTO);
        itemDAO.create(item);

        item.setNome("Item Atualizado");
        item.setDescricao("Descrição Atualizada");
        itemDAO.update(item);

        Item atualizado = itemDAO.getById(item.getId());
        assertEquals("Item Atualizado", atualizado.getNome());
        assertEquals("Descrição Atualizada", atualizado.getDescricao());
    }

    @Test
    @DisplayName("Deve deletar item existente")
    void deveDeletarItem() {
        Item item = criarItem("Item Delete", TipoItem.PRODUTO);
        itemDAO.create(item);

        itemDAO.delete(item.getId());
        Item deletado = itemDAO.getById(item.getId());
        assertNull(deletado, "Item deve ser removido do banco");
    }

    @Test
    @DisplayName("Deve buscar todos os itens")
    void deveBuscarTodosItens() {
        Item i1 = criarItem("Item 1", TipoItem.PRODUTO);
        Item i2 = criarItem("Item 2", TipoItem.COMBO);

        itemDAO.create(i1);
        itemDAO.create(i2);

        List<Item> atual = itemDAO.getAll();
        List<Item> esperado = Arrays.asList(i1, i2);
        esperado.sort(Comparator.comparing(Item::getId));
        atual.sort(Comparator.comparing(Item::getId));

        for (int i = 0; i < esperado.size(); i++) {
            verificar(esperado.get(i), atual.get(i));
        }
    }

    // ---------------- MÉTODOS AUXILIARES ----------------

    private Item criarItem(String nome, TipoItem tipo) {
        Item item = new Item();
        item.setNome(nome);
        item.setDescricao("Descrição " + nome);
        item.setTipoItem(tipo);
        item.setDataCriacao(new Date(System.currentTimeMillis()));
        item.setStatusAtivo(1);
        return item;
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

package com.tecdes.lanchonete.repository.implementation;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tecdes.lanchonete.model.entity.Item;
import com.tecdes.lanchonete.model.entity.Produto;
import com.tecdes.lanchonete.model.entity.Combo;
import com.tecdes.lanchonete.model.dao.ItemDAO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Testes de Repositório para ItemRepository")
public class ItemRepositoryTest {

    @Mock
    private ItemDAO itemDAO;

    @InjectMocks
    private IItemRepository iItemRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve retornar um Produto pelo ID corretamente")
    void deveRetornarProdutoPorId() {
        Produto produto = new Produto();
        Long id = 1L;
        produto.setId(id);
        when(itemDAO.getById(id)).thenReturn(produto);

        Item actual = iItemRepository.getById(id);

        verify(itemDAO, times(1)).getById(id);
        assertEquals(id, actual.getId());
    }

    @Test
    @DisplayName("Deve retornar um Combo pelo ID corretamente")
    void deveRetornarComboPorId() {
        Combo combo = new Combo();
        Long id = 2L;
        combo.setId(id);
        when(itemDAO.getById(id)).thenReturn(combo);

        Item actual = iItemRepository.getById(id);

        verify(itemDAO, times(1)).getById(id);
        assertEquals(id, actual.getId());
    }

    @Test
    @DisplayName("Deve retornar todos os itens corretamente com Produto e Combo")
    void deveRetornarTodosItens() {
        Produto produto = new Produto();
        produto.setId(1L);
        Combo combo = new Combo();
        combo.setId(2L);
        when(itemDAO.getAll()).thenReturn(Arrays.asList(produto, combo));

        List<Item> actualList = iItemRepository.getAll();

        verify(itemDAO, times(1)).getAll();
        assertEquals(2, actualList.size());
        assertEquals(1L, actualList.get(0).getId());
        assertEquals(2L, actualList.get(1).getId());
    }

    @Test
    @DisplayName("Deve atualizar um Produto corretamente")
    void deveAtualizarProduto() {
        Produto produto = new Produto();

        iItemRepository.update(produto);

        verify(itemDAO, times(1)).update(produto);
    }

    @Test
    @DisplayName("Deve atualizar um Combo corretamente")
    void deveAtualizarCombo() {
        Combo combo = new Combo();

        iItemRepository.update(combo);

        verify(itemDAO, times(1)).update(combo);
    }

    @Test
    @DisplayName("Deve deletar um item pelo ID")
    void deveDeletarItem() {
        Long id = 1L;

        iItemRepository.delete(id);

        verify(itemDAO, times(1)).delete(id);
    }
}

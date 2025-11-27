package com.tecdes.lanchonete.repository.implementation;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tecdes.lanchonete.model.entity.Produto;
import com.tecdes.lanchonete.model.dao.ProdutoDAO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Testes de Repositório para ProdutoRepository")
public class ProdutoRepositoryTest {

    @Mock
    private ProdutoDAO produtoDAO;

    @InjectMocks
    private IProdutoRepository iProdutoRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve retornar um produto pelo ID corretamente")
    void deveRetornarProdutoPorId() {
        Produto produto = new Produto();
        Long id = 1L;
        produto.setId(id);
        when(produtoDAO.getById(id)).thenReturn(produto);

        Produto actual = iProdutoRepository.getById(id);

        verify(produtoDAO, times(1)).getById(id);
        assertEquals(id, actual.getId());
    }

    @Test
    @DisplayName("Deve retornar todos os produtos corretamente")
    void deveRetornarTodosProdutos() {
        Produto produto1 = new Produto();
        produto1.setId(1L);
        Produto produto2 = new Produto();
        produto2.setId(2L);
        when(produtoDAO.getAll()).thenReturn(Arrays.asList(produto1, produto2));

        List<Produto> actualList = iProdutoRepository.getAll();

        verify(produtoDAO, times(1)).getAll();
        assertEquals(2, actualList.size());
        assertEquals(1L, actualList.get(0).getId());
        assertEquals(2L, actualList.get(1).getId());
    }

    @Test
    @DisplayName("Deve atualizar um produto corretamente")
    void deveAtualizarProduto() {
        Produto produto = new Produto();

        iProdutoRepository.update(produto);

        verify(produtoDAO, times(1)).update(produto);
    }

    @Test
    @DisplayName("Deve deletar um produto pelo ID")
    void deveDeletarProduto() {
        Long id = 1L;

        iProdutoRepository.delete(id);

        verify(produtoDAO, times(1)).delete(id);
    }
}

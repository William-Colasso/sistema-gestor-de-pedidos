package com.tecdes.lanchonhete.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tecdes.lanchonete.model.entity.CategoriaProduto;
import com.tecdes.lanchonete.model.entity.Produto;
import com.tecdes.lanchonete.model.enums.TipoItem;
import com.tecdes.lanchonete.repository.implementation.IItemRepository;
import com.tecdes.lanchonete.repository.implementation.IProdutoRepository;
import com.tecdes.lanchonete.service.MidiaService;
import com.tecdes.lanchonete.service.ProdutoService;

public class ProdutoServiceTest {

    @Mock
    private IProdutoRepository produtoRepository;

    @Mock
    private IItemRepository itemRepository;

    @Mock
    private MidiaService midiaService;

    @InjectMocks 
    private ProdutoService produtoService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveCriarProduto() {
        // Arrange
        Produto esperado = criarProdutoGenerico();
        when(produtoRepository.create(esperado)).thenReturn(esperado);
        when(itemRepository.create(esperado)).thenReturn(esperado);

        // Act
        Produto atual = produtoService.create(esperado);

        // Assert
        verify(produtoRepository, times(1)).create(esperado);
        verify(itemRepository, times(1)).create(esperado);
        verificarProduto(esperado, atual);
    }

    @Test
    void deveObterProduto() {
        // Arrange
        Produto esperado = criarProdutoGenerico();
        esperado.setId(1L);
        when(itemRepository.getById(esperado.getId())).thenReturn(esperado);
        when(produtoRepository.getById(esperado.getId())).thenReturn(esperado);

        // Act
        Produto atual = produtoService.getById(esperado.getId());

        // Assert
        verify(produtoRepository, times(1)).getById(esperado.getId());
        verify(itemRepository, times(1)).getById(esperado.getId());
        verificarProduto(esperado, atual);
    }

    @Test
    void deveObterProdutos() {
        // Arrange
        Produto esperado1 = criarProdutoGenerico();
        esperado1.setId(1L);
        Produto esperado2 = criarProduto("Segundo Produto", "O segundo produto", 22, 1, "2025-10-10");
        esperado2.setId(2L);
        List<Produto> esperados = Arrays.asList(esperado1, esperado2);
        when(produtoRepository.getAll()).thenReturn(esperados);
        when(itemRepository.getById(esperado1.getId())).thenReturn(esperado1);
        when(itemRepository.getById(esperado2.getId())).thenReturn(esperado2);

        // Act
        List<Produto> atuais = produtoService.getAll();

        // Assert
        verify(produtoRepository, times(1)).getAll();
        verify(itemRepository, times(1)).getById(esperado1.getId());
        verify(itemRepository, times(1)).getById(esperado2.getId());

        for (int i=0; i<2; i++) {
            verificarProduto(esperados.get(i), atuais.get(i));
        }
        
    }

    @Test
    void deveAtualizarProduto() {
        // Arrange
        Produto produto = criarProdutoGenerico();
        produto.setId(1L);

        // Act
        produtoService.update(produto);

        // Assert
        verify(produtoRepository, times(1)).update(produto);
        verify(itemRepository, times(1)).update(produto);
    }

    @Test
    void deveRemoverProdutoComId() {
        // Arrange
        Produto produto = criarProdutoGenerico();
        produto.setId(1L);

        // Act
        produtoService.delete(produto.getId());

        // Assert
        verify(produtoRepository, times(1)).delete(produto.getId());
        verify(itemRepository, times(1)).delete(produto.getId());
    }

    // --------------------------- Métodos auxiliares -----------------

    private Produto criarProdutoGenerico() {
        return criarProduto("Produto", "Um produto", 32, 1, "2025-10-10");
    }

    private Produto criarProduto(String nome, String descricao, double valor, int quantidade, String dataCriacao) {
        Produto p = new Produto();
        p.setNome(nome);
        p.setDescricao(descricao);
        p.setValor(valor);
        p.setQuantidade(quantidade);
        p.setTipoItem(TipoItem.PRODUTO);
        p.setDataCriacao(Date.valueOf(dataCriacao));
        p.setCategoria(criarCategoriaGenerica());
        return p;
    }

    private CategoriaProduto criarCategoriaGenerica() {
        return criarCategoria("Categoria Teste", "CT", "midia-teste".getBytes());
    }
    private CategoriaProduto criarCategoria(String nome, String sigla, byte[] imagem) {
        CategoriaProduto categoria = new CategoriaProduto();
        categoria.setNome(nome);
        categoria.setSigla(sigla);
        categoria.setImagem(imagem);
        return categoria;
    }

    private void verificarProduto(Produto esperado, Produto atual) {
        assertEquals(esperado.getId(), atual.getId());
        assertEquals(esperado.getNome(), atual.getNome());
        assertEquals(esperado.getDescricao(), atual.getDescricao());
        assertEquals(esperado.getTipoItem(), atual.getTipoItem());
        assertEquals(esperado.getQuantidade(), atual.getQuantidade());
        assertEquals(esperado.getValor(), atual.getValor());
        assertEquals(esperado.getCategoria().getId(), atual.getCategoria().getId());
        assertEquals(
            esperado.getCombos() != null ? esperado.getCombos().size() : null,
            esperado.getCombos() != null ? atual.getCombos().size() : null
        );
    }
}

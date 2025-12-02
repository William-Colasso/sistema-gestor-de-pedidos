package com.tecdes.lanchonhete.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tecdes.lanchonete.exception.InvalidDeleteOperationException;
import com.tecdes.lanchonete.exception.InvalidFieldException;
import com.tecdes.lanchonete.exception.InvalidIdException;
import com.tecdes.lanchonete.model.entity.CategoriaProduto;
import com.tecdes.lanchonete.repository.implementation.ICategoriaProdutoRepository;
import com.tecdes.lanchonete.service.CategoriaProdutoService;
import com.tecdes.lanchonete.service.ProdutoService;

public class CategoriaProdutoServiceTest {
    @Mock
    private ICategoriaProdutoRepository categoriaProdutoRepository;
    
    @Mock
    private ProdutoService produtoService;

    @InjectMocks
    private CategoriaProdutoService categoriaProdutoService;


    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveCriarCategoria() {
        // Arrange
        CategoriaProduto esperado = criarCategoriaGenerica();
        when(categoriaProdutoRepository.create(esperado)).thenReturn(esperado);

        // Act
        CategoriaProduto atual = categoriaProdutoService.create(esperado);

        // Assert
        verify(categoriaProdutoRepository, times(1)).create(esperado);
        verificarCategoria(esperado, atual);
    }

    @Test
    void deveObterCategoria() {
        // Arrange
        CategoriaProduto esperado = criarCategoriaGenerica();
        esperado.setId(1L);
        when(categoriaProdutoRepository.getById(esperado.getId())).thenReturn(esperado);

        // Act
        CategoriaProduto atual = categoriaProdutoService.getById(esperado.getId());

        // Assert
        verify(categoriaProdutoRepository, times(1)).getById(esperado.getId());
        verificarCategoria(esperado, atual);
    }

    @Test
    void deveObterCategorias() {
        // Arrange
        CategoriaProduto esperado1 = criarCategoriaGenerica();
        CategoriaProduto esperado2 = criarCategoria("Bebidas", "BE");
        List<CategoriaProduto> esperados = Arrays.asList(esperado1, esperado2);
        when(categoriaProdutoRepository.getAll()).thenReturn(esperados);

        // Act
        List<CategoriaProduto> atuais = categoriaProdutoService.getAll();

        // Assert
        verify(categoriaProdutoRepository, times(1)).getAll();
        for (int i=0; i<2; i++) {
            verificarCategoria(esperados.get(i), atuais.get(i));
        }
    }

    @Test
    void deveAtualizarCategoria() {
        // Arrange
        CategoriaProduto categoriaProduto = criarCategoriaGenerica();
        categoriaProduto.setId(1L);

        // Act
        categoriaProdutoService.update(categoriaProduto);

        // Assert
        verify(categoriaProdutoRepository, times(1)).update(categoriaProduto);
    }

    @Test
    void deveRemoverCategoriaComId() {
        // Arrange
        CategoriaProduto categoriaProduto = criarCategoria("Lanches", "LA");
        categoriaProduto.setId(1L);

        // Act
        categoriaProdutoService.delete(categoriaProduto.getId());

        // Assert
        verify(categoriaProdutoRepository, times(1)).delete(categoriaProduto.getId());
    }

    @Test 
    void naoDeveCriarCategoriaComCamposNulos() {
        // Arrange
        CategoriaProduto nomeNulo = criarCategoria(null, "NN");
        CategoriaProduto siglaNula = criarCategoria("SiglaNula", null);
        CategoriaProduto imagemNula = criarCategoria("ImagemNula", "IN");
        imagemNula.setImagem(null);

        // Act / Assert
        assertThrows(InvalidFieldException.class, () -> categoriaProdutoService.create(nomeNulo));
        verify(categoriaProdutoRepository, times(0)).create(nomeNulo);
        assertThrows(InvalidFieldException.class, () -> categoriaProdutoService.create(siglaNula));
        verify(categoriaProdutoRepository, times(0)).create(siglaNula);
        assertThrows(InvalidFieldException.class, () -> categoriaProdutoService.create(imagemNula));
        verify(categoriaProdutoRepository, times(0)).create(imagemNula);
    }

    @Test
    void naoDeveAtualizarCategoriaComCategoriaNula() {
        // Arrange
        CategoriaProduto nula = criarCategoriaGenerica();
        nula.setId(null);

        // Act / Assert
        assertThrows(InvalidIdException.class, () -> categoriaProdutoService.update(nula));
        verify(categoriaProdutoRepository, times(0)).update(nula);
    }

    @Test
    void naoDeveRemoverCategoriaComIdNulo() {
        // Arrange
        CategoriaProduto nula = criarCategoria(null, null);

        // Act / Assert
        assertThrows(InvalidDeleteOperationException.class, () -> categoriaProdutoService.delete(nula.getId()));
        verify(categoriaProdutoRepository, times(0)).delete(nula.getId());
    }


    // --------------------------- Métodos auxiliares -----------------

    private CategoriaProduto criarCategoriaGenerica() {
        return criarCategoria("Lanches", "LA");
    }

    private CategoriaProduto criarCategoria(String nome, String sigla) {
        CategoriaProduto categoriaProduto = new CategoriaProduto();
        categoriaProduto.setNome(nome);
        categoriaProduto.setSigla(sigla);
        categoriaProduto.setImagem("midia-test".getBytes());
        return categoriaProduto;
    }

    private void verificarCategoria(CategoriaProduto esperado, CategoriaProduto atual ) {
        assertEquals(esperado.getId(), atual.getId());
        assertEquals(esperado.getNome(), atual.getNome());
        assertEquals(esperado.getSigla(), atual.getSigla());
        assertEquals(esperado.getImagem(), atual.getImagem());
    }
}

package com.tecdes.lanchonete.repository.implementation;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tecdes.lanchonete.model.entity.CategoriaProduto;
import com.tecdes.lanchonete.model.dao.CategoriaProdutoDAO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Testes de Repositório para CategoriaProdutoRepository")
public class CategoriaProdutoRepositoryTest {

    @Mock
    private CategoriaProdutoDAO categoriaProdutoDAO; // Mock do DAO para simular interações com o banco

    @InjectMocks
    private ICategoriaProduto iCategoriaProduto; // Repositório a ser testado, com o DAO injetado

    @BeforeEach
    void setup() {
        // Inicializa os mocks antes de cada teste
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve retornar categoria de produto pelo ID corretamente")
    void deveRetornarCategoriaProdutoPorId() {
        // Arrange
        CategoriaProduto expected = new CategoriaProduto();
        Long expectedId = 1L;
        expected.setId(expectedId);
        when(categoriaProdutoDAO.getById(expectedId)).thenReturn(expected);

        // Act
        CategoriaProduto actual = iCategoriaProduto.getById(expectedId);

        // Assert
        verify(categoriaProdutoDAO, times(1)).getById(expectedId); // Deve chamar getById() com o ID correto
        assertEquals(expectedId, actual.getId(), "O ID da categoria de produto retornado deve ser igual ao ID esperado");
    }

    @Test
    @DisplayName("Deve retornar todas as categorias de produto corretamente")
    void deveRetornarTodasCategoriasProduto() {
        // Arrange
        CategoriaProduto expected1 = new CategoriaProduto();
        expected1.setId(1L);
        CategoriaProduto expected2 = new CategoriaProduto();
        expected2.setId(2L);
        when(categoriaProdutoDAO.getAll()).thenReturn(Arrays.asList(expected1, expected2));

        // Act
        List<CategoriaProduto> actualList = iCategoriaProduto.getAll();

        // Assert
        verify(categoriaProdutoDAO, times(1)).getAll(); // Deve chamar getAll() exatamente 1 vez
        assertEquals(2, actualList.size(), "A lista de categorias de produto retornada deve conter 2 elementos");
        assertEquals(expected1.getId(), actualList.get(0).getId(), "A primeira categoria de produto deve ter o ID esperado");
        assertEquals(expected2.getId(), actualList.get(1).getId(), "A segunda categoria de produto deve ter o ID esperado");
    }

    @Test
    @DisplayName("Deve atualizar uma categoria de produto corretamente")
    void deveAtualizarCategoriaProduto() {
        // Arrange
        CategoriaProduto categoriaProduto = new CategoriaProduto();

        // Act
        categoriaProdutoDAO.update(categoriaProduto);

        // Assert
        verify(categoriaProdutoDAO, times(1)).update(categoriaProduto); // Deve chamar update() exatamente 1 vez
    }

    @Test
    @DisplayName("Deve deletar uma categoria de produto pelo ID corretamente")
    void deveDeletarCategoriaProduto() {
        // Arrange
        Long idCategoriaProduto = 1L;

        // Act
        iCategoriaProduto.delete(idCategoriaProduto);

        // Assert
        verify(categoriaProdutoDAO, times(1)).delete(idCategoriaProduto); // Deve chamar delete() exatamente 1 vez com o ID
    }
}

package com.tecdes.lanchonete.repository.implementation;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tecdes.lanchonete.model.entity.Gerente;
import com.tecdes.lanchonete.model.dao.GerenteDAO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Testes de Repositório para GerenteRepository")
public class GerenteRepositoryTest {

    @Mock
    private GerenteDAO gerenteDAO; // Mock do DAO para simular interações com o banco

    @InjectMocks
    private IGerenteRepository iGerenteRepository; // Repositório a ser testado, com o DAO injetado

    @BeforeEach
    void setup() {
        // Inicializa os mocks antes de cada teste
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve retornar gerente pelo ID corretamente")
    void deveRetornarGerentePorId() {
        // Arrange
        Gerente expected = new Gerente();
        Long expectedId = 1L;
        expected.setId(expectedId);
        when(gerenteDAO.getById(expectedId)).thenReturn(expected);

        // Act
        Gerente actual = iGerenteRepository.getById(expectedId);

        // Assert
        verify(gerenteDAO, times(1)).getById(expectedId);
        assertEquals(expectedId, actual.getId(), "O ID do gerente retornado deve ser igual ao ID esperado");
    }

    @Test
    @DisplayName("Deve retornar todos os gerentes corretamente")
    void deveRetornarTodosGerentes() {
        // Arrange
        Gerente expected1 = new Gerente();
        expected1.setId(1L);
        Gerente expected2 = new Gerente();
        expected2.setId(2L);
        when(gerenteDAO.getAll()).thenReturn(Arrays.asList(expected1, expected2));

        // Act
        List<Gerente> actualList = iGerenteRepository.getAll();

        // Assert
        verify(gerenteDAO, times(1)).getAll();
        assertEquals(2, actualList.size(), "A lista de gerentes retornada deve conter 2 elementos");
        assertEquals(expected1.getId(), actualList.get(0).getId(), "O primeiro gerente deve ter o ID esperado");
        assertEquals(expected2.getId(), actualList.get(1).getId(), "O segundo gerente deve ter o ID esperado");
    }

    @Test
    @DisplayName("Deve atualizar um gerente corretamente")
    void deveAtualizarGerente() {
        // Arrange
        Gerente gerente = new Gerente();

        // Act
        gerenteDAO.update(gerente);

        // Assert
        verify(gerenteDAO, times(1)).update(gerente);
    }

    @Test
    @DisplayName("Deve deletar um gerente pelo ID corretamente")
    void deveDeletarGerente() {
        // Arrange
        Long idGerente = 1L;

        // Act
        iGerenteRepository.delete(idGerente);

        // Assert
        verify(gerenteDAO, times(1)).delete(idGerente);
    }
}

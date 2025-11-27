package com.tecdes.lanchonete.repository.implementation;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tecdes.lanchonete.model.entity.Combo;
import com.tecdes.lanchonete.model.dao.ComboDAO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Testes de Repositório para ComboRepository")
public class ComboRepositoryTest {

    @Mock
    private ComboDAO comboDAO; // Mock do DAO para simular interações com o banco

    @InjectMocks
    private IComboRepository iComboRepository; // Repositório a ser testado, com o DAO injetado

    @BeforeEach
    void setup() {
        // Inicializa os mocks antes de cada teste
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve retornar combo pelo ID corretamente")
    void deveRetornarComboPorId() {
        // Arrange
        Combo expected = new Combo();
        Long expectedId = 1L;
        expected.setId(expectedId);
        when(comboDAO.getById(expectedId)).thenReturn(expected);

        // Act
        Combo actual = iComboRepository.getById(expectedId);

        // Assert
        verify(comboDAO, times(1)).getById(expectedId); // Deve chamar getById() com o ID correto
        assertEquals(expectedId, actual.getId(), "O ID do combo retornado deve ser igual ao ID esperado");
    }

    @Test
    @DisplayName("Deve retornar todos os combos corretamente")
    void deveRetornarTodosCombos() {
        // Arrange
        Combo expected1 = new Combo();
        expected1.setId(1L);
        Combo expected2 = new Combo();
        expected2.setId(2L);
        when(comboDAO.getAll()).thenReturn(Arrays.asList(expected1, expected2));

        // Act
        List<Combo> actualList = iComboRepository.getAll();

        // Assert
        verify(comboDAO, times(1)).getAll(); // Deve chamar getAll() exatamente 1 vez
        assertEquals(2, actualList.size(), "A lista de combos retornada deve conter 2 elementos");
        assertEquals(expected1.getId(), actualList.get(0).getId(), "O primeiro combo deve ter o ID esperado");
        assertEquals(expected2.getId(), actualList.get(1).getId(), "O segundo combo deve ter o ID esperado");
    }

    @Test
    @DisplayName("Deve atualizar um combo corretamente")
    void deveAtualizarCombo() {
        // Arrange
        Combo combo = new Combo();

        // Act
        comboDAO.update(combo);

        // Assert
        verify(comboDAO, times(1)).update(combo); // Deve chamar update() exatamente 1 vez
    }

    @Test
    @DisplayName("Deve deletar um combo pelo ID corretamente")
    void deveDeletarCombo() {
        // Arrange
        Long idCombo = 1L;

        // Act
        iComboRepository.delete(idCombo);

        // Assert
        verify(comboDAO, times(1)).delete(idCombo); // Deve chamar delete() exatamente 1 vez com o ID
    }
}

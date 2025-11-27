package com.tecdes.lanchonete.repository.implementation;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tecdes.lanchonete.model.entity.Cupom;
import com.tecdes.lanchonete.model.dao.CupomDAO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Testes de Repositório para CupomRepository")
public class CupomRepositoryTest {

    @Mock
    private CupomDAO cupomDAO; // Mock do DAO para simular interações com o banco

    @InjectMocks
    private ICupomRepository iCupomRepository; // Repositório a ser testado, com o DAO injetado

    @BeforeEach
    void setup() {
        // Inicializa os mocks antes de cada teste
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve retornar cupom pelo ID corretamente")
    void deveRetornarCupomPorId() {
        // Arrange
        Cupom expected = new Cupom();
        Long expectedId = 1L;
        expected.setId(expectedId);
        when(cupomDAO.getById(expectedId)).thenReturn(expected);

        // Act
        Cupom actual = iCupomRepository.getById(expectedId);

        // Assert
        verify(cupomDAO, times(1)).getById(expectedId); // Deve chamar getById() com o ID correto
        assertEquals(expectedId, actual.getId(), "O ID do cupom retornado deve ser igual ao ID esperado");
    }

    @Test
    @DisplayName("Deve retornar todos os cupons corretamente")
    void deveRetornarTodosCupons() {
        // Arrange
        Cupom expected1 = new Cupom();
        expected1.setId(1L);
        Cupom expected2 = new Cupom();
        expected2.setId(2L);
        when(cupomDAO.getAll()).thenReturn(Arrays.asList(expected1, expected2));

        // Act
        List<Cupom> actualList = iCupomRepository.getAll();

        // Assert
        verify(cupomDAO, times(1)).getAll(); // Deve chamar getAll() exatamente 1 vez
        assertEquals(2, actualList.size(), "A lista de cupons retornada deve conter 2 elementos");
        assertEquals(expected1.getId(), actualList.get(0).getId(), "O primeiro cupom deve ter o ID esperado");
        assertEquals(expected2.getId(), actualList.get(1).getId(), "O segundo cupom deve ter o ID esperado");
    }

    @Test
    @DisplayName("Deve atualizar um cupom corretamente")
    void deveAtualizarCupom() {
        // Arrange
        Cupom cupom = new Cupom();

        // Act
        cupomDAO.update(cupom);

        // Assert
        verify(cupomDAO, times(1)).update(cupom); // Deve chamar update() exatamente 1 vez
    }

    @Test
    @DisplayName("Deve deletar um cupom pelo ID corretamente")
    void deveDeletarCupom() {
        // Arrange
        Long idCupom = 1L;

        // Act
        iCupomRepository.delete(idCupom);

        // Assert
        verify(cupomDAO, times(1)).delete(idCupom); // Deve chamar delete() exatamente 1 vez com o ID
    }
}

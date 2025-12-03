package com.tecdes.lanchonete.repository.implementation;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tecdes.lanchonete.model.entity.Funcionario;
import com.tecdes.lanchonete.model.dao.FuncionarioDAO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Testes de Repositório para FuncionarioRepository")
public class FuncionarioRepositoryTest {

    @Mock
    private FuncionarioDAO funcionarioDAO; // Mock do DAO para simular interações com o banco

    @InjectMocks
    private IFuncionarioRepository iFuncionarioRepository; // Repositório a ser testado, com o DAO injetado

    @BeforeEach
    void setup() {
        // Inicializa os mocks antes de cada teste
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve retornar funcionário pelo ID corretamente")
    void deveRetornarFuncionarioPorId() {
        // Arrange
        Funcionario expected = new Funcionario();
        Long expectedId = 1L;
        expected.setId(expectedId);
        when(funcionarioDAO.getById(expectedId)).thenReturn(expected);

        // Act
        Funcionario actual = iFuncionarioRepository.getById(expectedId);

        // Assert
        verify(funcionarioDAO, times(1)).getById(expectedId); // Deve chamar getById() com o ID correto
        assertEquals(expectedId, actual.getId(), "O ID do funcionário retornado deve ser igual ao ID esperado");
    }

    @Test
    @DisplayName("Deve retornar todos os funcionários corretamente")
    void deveRetornarTodosFuncionarios() {
        // Arrange
        Funcionario expected1 = new Funcionario();
        expected1.setId(1L);
        Funcionario expected2 = new Funcionario();
        expected2.setId(2L);
        when(funcionarioDAO.getAll()).thenReturn(Arrays.asList(expected1, expected2));

        // Act
        List<Funcionario> actualList = iFuncionarioRepository.getAll();

        // Assert
        verify(funcionarioDAO, times(1)).getAll(); // Deve chamar getAll() exatamente 1 vez
        assertEquals(2, actualList.size(), "A lista de funcionários retornada deve conter 2 elementos");
        assertEquals(expected1.getId(), actualList.get(0).getId(), "O primeiro funcionário deve ter o ID esperado");
        assertEquals(expected2.getId(), actualList.get(1).getId(), "O segundo funcionário deve ter o ID esperado");
    }

    @Test
    @DisplayName("Deve atualizar um funcionário corretamente")
    void deveAtualizarFuncionario() {
        // Arrange
        Funcionario funcionario = new Funcionario();

        // Act
        funcionarioDAO.update(funcionario);

        // Assert
        verify(funcionarioDAO, times(1)).update(funcionario); // Deve chamar update() exatamente 1 vez
    }

    @Test
    @DisplayName("Deve deletar um funcionário pelo ID corretamente")
    void deveDeletarFuncionario() {
        // Arrange
        Long idFuncionario = 1L;

        // Act
        iFuncionarioRepository.delete(idFuncionario);

        // Assert
        verify(funcionarioDAO, times(1)).delete(idFuncionario); // Deve chamar delete() exatamente 1 vez com o ID
    }
}

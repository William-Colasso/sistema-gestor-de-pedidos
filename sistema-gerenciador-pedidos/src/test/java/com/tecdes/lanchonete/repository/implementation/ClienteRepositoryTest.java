package com.tecdes.lanchonete.repository.implementation;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tecdes.lanchonete.model.entity.Cliente;
import com.tecdes.lanchonete.model.dao.ClienteDAO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Testes de Repositório para ClienteRepository")
public class ClienteRepositoryTest {

    @Mock
    private ClienteDAO clienteDAO; // Mock do DAO para simular interações com o banco

    @InjectMocks
    private IClienteRepository iClienteRepository; // Repositório a ser testado, com o DAO injetado

    @BeforeEach
    void setup() {
        // Inicializa os mocks antes de cada teste
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve retornar cliente pelo ID corretamente")
    void deveRetornarClientePorId() {
        // Arrange
        Cliente expected = new Cliente();
        Long expectedId = 1L;
        expected.setId(expectedId);
        when(clienteDAO.getById(expectedId)).thenReturn(expected);

        // Act
        Cliente actual = iClienteRepository.getById(expectedId);

        // Assert
        verify(clienteDAO, times(1)).getById(expectedId); // Deve chamar getById() com o ID correto
        assertEquals(expectedId, actual.getId(), "O ID do cliente retornado deve ser igual ao ID esperado");
    }

    @Test
    @DisplayName("Deve retornar todos os clientes corretamente")
    void deveRetornarTodosClientes() {
        // Arrange
        Cliente expected1 = new Cliente();
        expected1.setId(1L);
        Cliente expected2 = new Cliente();
        expected2.setId(2L);
        when(clienteDAO.getAll()).thenReturn(Arrays.asList(expected1, expected2));

        // Act
        List<Cliente> actualList = iClienteRepository.getAll();

        // Assert
        verify(clienteDAO, times(1)).getAll(); // Deve chamar getAll() exatamente 1 vez
        assertEquals(2, actualList.size(), "A lista de clientes retornada deve conter 2 elementos");
        assertEquals(expected1.getId(), actualList.get(0).getId(), "O primeiro cliente deve ter o ID esperado");
        assertEquals(expected2.getId(), actualList.get(1).getId(), "O segundo cliente deve ter o ID esperado");
    }

    @Test
    @DisplayName("Deve atualizar um cliente corretamente")
    void deveAtualizarCliente() {
        // Arrange
        Cliente cliente = new Cliente();

        // Act
        clienteDAO.update(cliente);

        // Assert
        verify(clienteDAO, times(1)).update(cliente); // Deve chamar update() exatamente 1 vez
    }

    @Test
    @DisplayName("Deve deletar um cliente pelo ID corretamente")
    void deveDeletarCliente() {
        // Arrange
        Long idCliente = 1L;

        // Act
        iClienteRepository.delete(idCliente);

        // Assert
        verify(clienteDAO, times(1)).delete(idCliente); // Deve chamar delete() exatamente 1 vez com o ID
    }
}

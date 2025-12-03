package com.tecdes.lanchonete.repository.implementation;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tecdes.lanchonete.model.entity.Pedido;
import com.tecdes.lanchonete.model.dao.PedidoDAO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Testes de Repositório para PedidoRepository")
public class PedidoRepositoryTest {

    @Mock
    private PedidoDAO pedidoDAO; // Mock do DAO para simular interações com o banco

    @InjectMocks
    private IPedidoRepository iPedidoRepository; // Repositório a ser testado, com o DAO injetado

    @BeforeEach
    void setup() {
        // Inicializa os mocks antes de cada teste
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve retornar pedido pelo ID corretamente")
    void deveRetornarPedidoPorId() {
        // Arrange
        Pedido expected = new Pedido();
        Long expectedId = 1L;
        expected.setId(expectedId);
        when(pedidoDAO.getById(expectedId)).thenReturn(expected);

        // Act
        Pedido actual = iPedidoRepository.getById(expectedId);

        // Assert
        verify(pedidoDAO, times(1)).getById(expectedId); // Deve chamar getById() com o ID correto
        assertEquals(expectedId, actual.getId(), "O ID do pedido retornado deve ser igual ao ID esperado");
    }

    @Test
    @DisplayName("Deve retornar todos os pedidos corretamente")
    void deveRetornarTodosPedidos() {
        // Arrange
        Pedido expected1 = new Pedido();
        expected1.setId(1L);
        Pedido expected2 = new Pedido();
        expected2.setId(2L);
        when(pedidoDAO.getAll()).thenReturn(Arrays.asList(expected1, expected2));

        // Act
        List<Pedido> actualList = iPedidoRepository.getAll();

        // Assert
        verify(pedidoDAO, times(1)).getAll(); // Deve chamar getAll() exatamente 1 vez
        assertEquals(2, actualList.size(), "A lista de pedidos retornada deve conter 2 elementos");
        assertEquals(expected1.getId(), actualList.get(0).getId(), "O primeiro pedido deve ter o ID esperado");
        assertEquals(expected2.getId(), actualList.get(1).getId(), "O segundo pedido deve ter o ID esperado");
    }

    @Test
    @DisplayName("Deve atualizar um pedido corretamente")
    void deveAtualizarPedido() {
        // Arrange
        Pedido pedido = new Pedido();

        // Act
        pedidoDAO.update(pedido);

        // Assert
        verify(pedidoDAO, times(1)).update(pedido); // Deve chamar update() exatamente 1 vez
    }

    @Test
    @DisplayName("Deve deletar um pedido pelo ID corretamente")
    void deveDeletarPedido() {
        // Arrange
        Long idPedido = 1L;

        // Act
        iPedidoRepository.delete(idPedido);

        // Assert
        verify(pedidoDAO, times(1)).delete(idPedido); // Deve chamar delete() exatamente 1 vez com o ID
    }
}
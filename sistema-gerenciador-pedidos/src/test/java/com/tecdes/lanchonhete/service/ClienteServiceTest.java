package com.tecdes.lanchonhete.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

import com.tecdes.lanchonete.model.entity.Cliente;
import com.tecdes.lanchonete.model.entity.Cupom;
import com.tecdes.lanchonete.repository.implementation.IClienteRepository;
import com.tecdes.lanchonete.service.ClienteService;

public class ClienteServiceTest {
    @Mock
    private IClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveCriarCliente() {
        // Arrange
        Cliente esperado = criarClienteGenerico();
        when(clienteRepository.create(esperado)).thenReturn(esperado);

        // Act
        Cliente atual = clienteService.create(esperado);

        // Assert
        verify(clienteRepository, times(1)).create(esperado);
        verificarCliente(esperado, atual);
    }

    @Test
    void deveObterCliente() {
        // Arrange
        Cliente esperado = criarClienteGenerico();
        esperado.setId(1L);
        when(clienteRepository.getById(esperado.getId())).thenReturn(esperado);

        // Act
        Cliente atual = clienteService.getById(esperado.getId());

        // Assert
        verify(clienteRepository, times(1)).getById(esperado.getId());
        verificarCliente(esperado, atual);
    }

    @Test
    void deveObterClientes() {
        // Arrange
        Cliente esperado1 = criarClienteGenerico();
        Cliente esperado2 = criarCliente("SegundoCliente", "321.321.321-32", "47977123321");
        List<Cliente> esperados = Arrays.asList(esperado1, esperado2);
        when(clienteRepository.getAll()).thenReturn(esperados);

        // Act
        List<Cliente> atuais = clienteService.getAll();

        // Assert
        verify(clienteRepository, times(1)).getAll();
        for (int i=0; i<2; i++) {
            verificarCliente(esperados.get(i), atuais.get(i));
        }
    }

    @Test
    void deveAtualizarCliente() {
        // Arrange
        Cliente cliente = criarClienteGenerico();
        cliente.setId(1L);

        // Act
        clienteService.update(cliente);

        // Assert
        verify(clienteRepository, times(1)).update(cliente);
    }

    @Test
    void deveRemoverClienteComId() {
        // Arrange
        Cliente cliente = criarClienteGenerico();
        cliente.setId(1L);

        // Act
        clienteService.delete(cliente.getId());

        // Assert
        verify(clienteRepository, times(1)).delete(cliente.getId());
    }

    @Test 
    void naoDeveCriarClienteComCamposNulos() {
        // Arrange
        Cliente nomeNulo = criarCliente(null, "123.123.123.12", "47912341234");
        Cliente cpfNulo = criarCliente("Cliente", null, "47912341234");


        // Act / Assert
        assertThrows(Exception.class, () -> clienteService.create(nomeNulo));
        verify(clienteRepository, times(0)).create(nomeNulo);
        assertThrows(Exception.class, () -> clienteService.create(cpfNulo));
        verify(clienteRepository, times(0)).create(cpfNulo);
    }

    @Test
    void naoDeveAtualizarClienteComClienteNulo() {
        // Arrange
        Cliente nulo = null;

        // Act / Assert
        assertThrows(Exception.class, () -> clienteService.update(nulo));
        verify(clienteRepository, times(0)).update(nulo);
    }

    @Test
    void naoDeveRemoverClienteComIdNulo() {
        // Arrange
        Cliente nulo = criarCliente(null, null, null);

        // Act / Assert
        assertThrows(Exception.class, () -> clienteService.delete(nulo.getId()));
        verify(clienteRepository, times(0)).delete(nulo.getId());
    }

    @Test
    void deveRetornarTrueQuandoCupomJaFoiUtilizado() {
        // Arrange
        Cliente cliente = criarClienteGenerico();
        cliente.setId(1L);
        Cupom cupom = new Cupom();
        cupom.setId(10L);

        when(clienteRepository.getCuponsByIdCliente(1L))
                .thenReturn(Arrays.asList(cupom));

        // Act
        boolean result = clienteService.verifyUsedCupom(cliente, cupom);

        // Assert
        assertTrue(result);
        verify(clienteRepository, times(1)).getCuponsByIdCliente(1L);
    }

    @Test
    void deveRetornarFalseQuandoCupomNaoFoiUtilizado() {
        // Arrange
        Cliente cliente = criarClienteGenerico();
        cliente.setId(1L);

        Cupom cupom = new Cupom();
        cupom.setId(10L);

        Cupom outroCupom = new Cupom();
        outroCupom.setId(20L);

        when(clienteRepository.getCuponsByIdCliente(1L)).thenReturn(Arrays.asList(outroCupom));

        // Act
        boolean result = clienteService.verifyUsedCupom(cliente, cupom);

        // Assert
        assertFalse(result);
        verify(clienteRepository, times(1)).getCuponsByIdCliente(1L);
    }

    @Test
    void deveRetornarFalseQuandoClienteNaoTemCupom() {
        // Arrange
        Cliente cliente = new Cliente();
        cliente.setId(1L);

        Cupom cupom = new Cupom();
        cupom.setId(10L);

        when(clienteRepository.getCuponsByIdCliente(1L)).thenReturn(Arrays.asList()); // lista vazia

        // Act
        boolean result = clienteService.verifyUsedCupom(cliente, cupom);

        // Assert
        assertFalse(result);
        verify(clienteRepository).getCuponsByIdCliente(1L);
    }

    @Test
    void naoDeveCriarClienteComCpfInvalido() {
        // Arrange
        Cliente cpfInvalido = criarClienteGenerico();
        cpfInvalido.setCpf("123.123.123-123"); // 15 dígitos

        // Act / Assert
        assertThrows(Exception.class, () -> clienteService.create(cpfInvalido));
        verify(clienteRepository, times(0)).create(cpfInvalido);
    }

    @Test
    void naoDeveCriarClienteComTelefoneInvalido() {
        // Arrange
        Cliente telefoneInvalido = criarClienteGenerico();
        telefoneInvalido.setTelefone("479123412341"); // 12 dígitos

        // Act / Assert
        assertThrows(Exception.class, () -> clienteService.create(telefoneInvalido));
        verify(clienteRepository, times(0)).create(telefoneInvalido);
    }

    // --------------------------- Métodos auxiliares -----------------

    private Cliente criarClienteGenerico() {
        return criarCliente("Cliente", "123.123.123-12", "47987654321");
    }

    private Cliente criarCliente(String nome, String cpf, String telefone) {
        Cliente cliente = new Cliente();
        cliente.setNome(nome);
        cliente.setCpf(cpf);
        cliente.setTelefone(telefone);
        cliente.setDataRegistro(new Date(System.currentTimeMillis()));
        return cliente;
    }

    private void verificarCliente(Cliente esperado, Cliente atual ) {
        assertEquals(esperado.getId(), atual.getId());
        assertEquals(esperado.getNome(), atual.getNome());
        assertEquals(esperado.getCpf(), atual.getCpf());
        assertEquals(esperado.getTelefone(), atual.getTelefone());
    }
}

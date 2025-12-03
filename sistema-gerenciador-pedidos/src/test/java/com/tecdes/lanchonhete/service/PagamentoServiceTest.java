package com.tecdes.lanchonhete.service;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

import com.tecdes.lanchonete.model.entity.Pagamento;
import com.tecdes.lanchonete.repository.implementation.IPagamentoRepository;
import com.tecdes.lanchonete.service.PagamentoService;

public class PagamentoServiceTest {
    @Mock
    private IPagamentoRepository pagamentoRepository;

    @InjectMocks
    private PagamentoService pagamentoService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveObterPagamento() {
        // Arrange
        Pagamento esperado = criarPagamentoGenerico();
        esperado.setId(1L);
        when(pagamentoRepository.getById(esperado.getId())).thenReturn(esperado);

        // Act
        Pagamento atual = pagamentoService.getById(esperado.getId());

        // Assert
        verify(pagamentoRepository, times(1)).getById(esperado.getId());
        verificarPagamento(esperado, atual);
    }

    @Test
    void deveObterPagamentos() {
        // Arrange
        Pagamento esperado1 = criarPagamentoGenerico();
        Pagamento esperado2 = criarPagamento("Dinheiro", "DI");
        List<Pagamento> esperados = Arrays.asList(esperado1, esperado2);
        when(pagamentoRepository.getAll()).thenReturn(esperados);

        // Act
        List<Pagamento> atuais = pagamentoService.getAll();

        // Assert
        verify(pagamentoRepository, times(1)).getAll();
        for (int i=0; i<2; i++) {
            verificarPagamento(esperados.get(i), atuais.get(i));
        }
    }

    

    // --------------------------- Métodos auxiliares -----------------

    private Pagamento criarPagamentoGenerico() {
        return criarPagamento("Cartão de Débito", "CD");
    }

    private Pagamento criarPagamento(String nome, String sigla) {
        Pagamento p = new Pagamento();
        p.setNome(nome);
        p.setSigla(sigla);
        p.setImagem("midia-teste".getBytes());
        return p;
    }

    private void verificarPagamento(Pagamento esperado, Pagamento atual) {
        assertEquals(esperado.getNome(), atual.getNome());
        assertEquals(esperado.getSigla(), atual.getSigla());
        assertArrayEquals(esperado.getImagem(), atual.getImagem());
    }
}

package com.tecdes.lanchonete.repository.implementation;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tecdes.lanchonete.model.entity.Pagamento;
import com.tecdes.lanchonete.model.dao.PagamentoDAO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Testes de Repositório para PagamentoRepository")
public class PagamentoRepositoryTest {

    @Mock
    private PagamentoDAO pagamentoDAO;

    @InjectMocks
    private IPagamentoRepository iPagamentoRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve retornar um pagamento pelo ID corretamente")
    void deveRetornarPagamentoPorId() {
        Pagamento pagamento = new Pagamento();
        Long id = 1L;
        pagamento.setId(id);
        when(pagamentoDAO.getById(id)).thenReturn(pagamento);

        Pagamento actual = iPagamentoRepository.getById(id);

        verify(pagamentoDAO, times(1)).getById(id);
        assertEquals(id, actual.getId());
    }

    @Test
    @DisplayName("Deve retornar todos os pagamentos corretamente")
    void deveRetornarTodosPagamentos() {
        Pagamento pagamento1 = new Pagamento();
        pagamento1.setId(1L);
        Pagamento pagamento2 = new Pagamento();
        pagamento2.setId(2L);
        when(pagamentoDAO.getAll()).thenReturn(Arrays.asList(pagamento1, pagamento2));

        List<Pagamento> actualList = iPagamentoRepository.getAll();

        verify(pagamentoDAO, times(1)).getAll();
        assertEquals(2, actualList.size());
        assertEquals(1L, actualList.get(0).getId());
        assertEquals(2L, actualList.get(1).getId());
    }

    @Test
    @DisplayName("Deve atualizar um pagamento corretamente")
    void deveAtualizarPagamento() {
        Pagamento pagamento = new Pagamento();

        iPagamentoRepository.update(pagamento);

        verify(pagamentoDAO, times(1)).update(pagamento);
    }

    @Test
    @DisplayName("Deve deletar um pagamento pelo ID")
    void deveDeletarPagamento() {
        Long id = 1L;

        iPagamentoRepository.delete(id);

        verify(pagamentoDAO, times(1)).delete(id);
    }
}

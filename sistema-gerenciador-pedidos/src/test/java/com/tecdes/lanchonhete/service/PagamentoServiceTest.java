package com.tecdes.lanchonhete.service;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
}

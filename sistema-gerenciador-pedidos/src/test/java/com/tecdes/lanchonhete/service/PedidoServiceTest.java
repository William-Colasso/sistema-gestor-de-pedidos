package com.tecdes.lanchonhete.service;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tecdes.lanchonete.repository.implementation.IPedidoRepository;
import com.tecdes.lanchonete.service.PedidoService;

public class PedidoServiceTest {
    @Mock
    private IPedidoRepository pedidoRepository;
    
    @InjectMocks
    private PedidoService pedidoService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }
}

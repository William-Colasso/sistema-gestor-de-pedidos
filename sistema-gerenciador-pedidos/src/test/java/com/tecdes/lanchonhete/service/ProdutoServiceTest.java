package com.tecdes.lanchonhete.service;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tecdes.lanchonete.repository.implementation.IProdutoRepository;
import com.tecdes.lanchonete.service.ProdutoService;

public class ProdutoServiceTest {

    @Mock
    private IProdutoRepository produtoRepository;

    @InjectMocks 
    private ProdutoService produtoService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }
}

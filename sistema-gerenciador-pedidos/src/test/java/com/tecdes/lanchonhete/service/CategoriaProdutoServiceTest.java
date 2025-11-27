package com.tecdes.lanchonhete.service;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tecdes.lanchonete.repository.implementation.ICategoriaProdutoRepository;

public class CategoriaProdutoServiceTest {
    @Mock
    private ICategoriaProdutoRepository categoriaProdutoRepository;

    @InjectMocks
    private CategoriaProdutoServiceTest categoriaProdutoServiceTest;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }
}

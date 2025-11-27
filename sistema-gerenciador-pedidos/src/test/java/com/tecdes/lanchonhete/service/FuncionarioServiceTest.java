package com.tecdes.lanchonhete.service;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tecdes.lanchonete.repository.implementation.IFuncionarioRepository;
import com.tecdes.lanchonete.service.FuncionarioService;

public class FuncionarioServiceTest {

    @Mock
    private IFuncionarioRepository funcionarioRepository;

    @InjectMocks
    private FuncionarioService funcionarioService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }
}

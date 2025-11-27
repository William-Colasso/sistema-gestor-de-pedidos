package com.tecdes.lanchonhete.service;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tecdes.lanchonete.repository.implementation.IGerenteRepository;
import com.tecdes.lanchonete.service.GerenteService;

public class GerenteServiceTest {
    @Mock
    private IGerenteRepository gerenteRepository;

    @InjectMocks
    private GerenteService gerenteService;
    
    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }
}

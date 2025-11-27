package com.tecdes.lanchonhete.service;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tecdes.lanchonete.repository.implementation.IParceiroRepository;
import com.tecdes.lanchonete.service.ParceiroService;

public class ParceiroServiceTest {
    @Mock
    private IParceiroRepository parceiroRepository;

    @InjectMocks
    private ParceiroService parceiroService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }
}

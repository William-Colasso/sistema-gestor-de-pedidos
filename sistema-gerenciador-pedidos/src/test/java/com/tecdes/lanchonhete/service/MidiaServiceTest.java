package com.tecdes.lanchonhete.service;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tecdes.lanchonete.repository.implementation.IMidiaRepository;
import com.tecdes.lanchonete.service.MidiaService;

public class MidiaServiceTest {
    @Mock
    private IMidiaRepository midiaRepository;

    @InjectMocks
    private MidiaService midiaService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }
}

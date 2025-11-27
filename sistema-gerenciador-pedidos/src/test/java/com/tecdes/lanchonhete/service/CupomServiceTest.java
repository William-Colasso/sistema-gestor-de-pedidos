package com.tecdes.lanchonhete.service;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tecdes.lanchonete.repository.implementation.ICupomRepository;
import com.tecdes.lanchonete.service.CupomService;

public class CupomServiceTest {
    @Mock
    private ICupomRepository cupomRepository;

    @InjectMocks
    private CupomService cupomService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }
}

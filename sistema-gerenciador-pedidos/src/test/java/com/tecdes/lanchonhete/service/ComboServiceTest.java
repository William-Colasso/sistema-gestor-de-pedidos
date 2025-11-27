package com.tecdes.lanchonhete.service;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tecdes.lanchonete.repository.implementation.IComboRepository;
import com.tecdes.lanchonete.service.ComboService;

public class ComboServiceTest {
    @Mock
    private IComboRepository comboRepository;

    @InjectMocks
    private ComboService comboService;


    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }
}

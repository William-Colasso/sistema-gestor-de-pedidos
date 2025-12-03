package com.tecdes.lanchonete.repository.implementation;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tecdes.lanchonete.model.entity.Midia;
import com.tecdes.lanchonete.model.dao.MidiaDAO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Testes de Repositório para MidiaRepository")
public class MidiaRepositoryTest {

    @Mock
    private MidiaDAO midiaDAO;

    @InjectMocks
    private IMidiaRepository iMidiaRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve retornar uma mídia pelo ID corretamente")
    void deveRetornarMidiaPorId() {
        Midia midia = new Midia();
        Long id = 1L;
        midia.setId(id);
        when(midiaDAO.getById(id)).thenReturn(midia);

        Midia actual = iMidiaRepository.getById(id);

        verify(midiaDAO, times(1)).getById(id);
        assertEquals(id, actual.getId());
    }

    @Test
    @DisplayName("Deve retornar todas as mídias corretamente")
    void deveRetornarTodasMidias() {
        Midia midia1 = new Midia();
        midia1.setId(1L);
        Midia midia2 = new Midia();
        midia2.setId(2L);
        when(midiaDAO.getAll()).thenReturn(Arrays.asList(midia1, midia2));

        List<Midia> actualList = iMidiaRepository.getAll();

        verify(midiaDAO, times(1)).getAll();
        assertEquals(2, actualList.size());
        assertEquals(1L, actualList.get(0).getId());
        assertEquals(2L, actualList.get(1).getId());
    }

    @Test
    @DisplayName("Deve atualizar uma mídia corretamente")
    void deveAtualizarMidia() {
        Midia midia = new Midia();

        iMidiaRepository.update(midia);

        verify(midiaDAO, times(1)).update(midia);
    }

    @Test
    @DisplayName("Deve deletar uma mídia pelo ID")
    void deveDeletarMidia() {
        Long id = 1L;

        iMidiaRepository.delete(id);

        verify(midiaDAO, times(1)).delete(id);
    }
}

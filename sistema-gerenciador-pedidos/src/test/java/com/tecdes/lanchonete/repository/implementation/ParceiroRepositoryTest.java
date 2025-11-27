package com.tecdes.lanchonete.repository.implementation;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tecdes.lanchonete.model.entity.Parceiro;
import com.tecdes.lanchonete.model.dao.ParceiroDAO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Testes de Repositório para ParceiroRepository")
public class ParceiroRepositoryTest {

    @Mock
    private ParceiroDAO parceiroDAO;

    @InjectMocks
    private IParceiroRepository iParceiroRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve retornar um parceiro pelo ID corretamente")
    void deveRetornarParceiroPorId() {
        Parceiro parceiro = new Parceiro();
        Long id = 1L;
        parceiro.setId(id);
        when(parceiroDAO.getById(id)).thenReturn(parceiro);

        Parceiro actual = iParceiroRepository.getById(id);

        verify(parceiroDAO, times(1)).getById(id);
        assertEquals(id, actual.getId());
    }

    @Test
    @DisplayName("Deve retornar todos os parceiros corretamente")
    void deveRetornarTodosParceiros() {
        Parceiro parceiro1 = new Parceiro();
        parceiro1.setId(1L);
        Parceiro parceiro2 = new Parceiro();
        parceiro2.setId(2L);
        when(parceiroDAO.getAll()).thenReturn(Arrays.asList(parceiro1, parceiro2));

        List<Parceiro> actualList = iParceiroRepository.getAll();

        verify(parceiroDAO, times(1)).getAll();
        assertEquals(2, actualList.size());
        assertEquals(1L, actualList.get(0).getId());
        assertEquals(2L, actualList.get(1).getId());
    }

    @Test
    @DisplayName("Deve atualizar um parceiro corretamente")
    void deveAtualizarParceiro() {
        Parceiro parceiro = new Parceiro();

        iParceiroRepository.update(parceiro);

        verify(parceiroDAO, times(1)).update(parceiro);
    }

    @Test
    @DisplayName("Deve deletar um parceiro pelo ID")
    void deveDeletarParceiro() {
        Long id = 1L;

        iParceiroRepository.delete(id);

        verify(parceiroDAO, times(1)).delete(id);
    }
}

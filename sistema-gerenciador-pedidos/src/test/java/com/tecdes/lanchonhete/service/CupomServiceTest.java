package com.tecdes.lanchonhete.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tecdes.lanchonete.model.entity.Cupom;
import com.tecdes.lanchonete.model.entity.Parceiro;
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

    @Test
    void deveCriarCupom() {
        // Arrange
        Cupom esperado = criarCupomGenerico();
        when(cupomRepository.create(esperado)).thenReturn(esperado);

        // Act
        Cupom atual = cupomService.create(esperado);

        // Assert
        verify(cupomRepository, times(1)).create(esperado);
        verificarCupom(esperado, atual);
    }

    @Test
    void deveObterCupom() {
        // Arrange
        Cupom esperado = criarCupomGenerico();
        esperado.setId(1L);
        when(cupomRepository.getById(esperado.getId())).thenReturn(esperado);

        // Act
        Cupom atual = cupomService.getById(esperado.getId());

        // Assert
        verify(cupomRepository, times(1)).getById(esperado.getId());
        verificarCupom(esperado, atual);
    }

    @Test
    void deveObterCupoms() {
        // Arrange
        Cupom esperado1 = criarCupomGenerico();
        Cupom esperado2 = criarCupom(criarParceiroGenerico(), "Segundo cupom", "Cupom 2", 15, 0);
        List<Cupom> esperados = Arrays.asList(esperado1, esperado2);
        when(cupomRepository.getAll()).thenReturn(esperados);

        // Act
        List<Cupom> atuais = cupomService.getAll();

        // Assert
        verify(cupomRepository, times(1)).getAll();
        for (int i=0; i<2; i++) {
            verificarCupom(esperados.get(i), atuais.get(i));
        }
    }

    @Test
    void deveAtualizarCupom() {
        // Arrange
        Cupom Cupom = criarCupomGenerico();
        Cupom.setId(1L);

        // Act
        cupomService.update(Cupom);

        // Assert
        verify(cupomRepository, times(1)).update(Cupom);
    }

    @Test
    void deveRemoverCupomComId() {
        // Arrange
        Cupom Cupom = criarCupomGenerico();
        Cupom.setId(1L);

        // Act
        cupomService.delete(Cupom.getId());

        // Assert
        verify(cupomRepository, times(1)).delete(Cupom.getId());
    }

    // --------------------------- Métodos auxiliares -----------------

    private Cupom criarCupomGenerico() {
        return criarCupom(criarParceiroGenerico(), "Um cupom", "Cupom", 10, 1);
    }

    private Parceiro criarParceiroGenerico() {
        return criarParceiro("Parceiro", "parceiro@gmail.com", "4712341234");
    }

    private Parceiro criarParceiro(String nome, String email, String telefone) {
        Parceiro p = new Parceiro();
        p.setNome(nome);
        p.setEmail(email);
        p.setTelefone(telefone);
        return p;
    }

    private Cupom criarCupom(Parceiro parceiro, String descricao, String nome, int desconto, int valido) {
        Cupom c = new Cupom();
        c.setParceiro(parceiro);
        c.setDescricao(descricao);
        c.setNome(nome);
        c.setValorDesconto(desconto);
        c.setValido(valido);
        return c;
    }

    private void verificarCupom(Cupom esperado, Cupom atual ) {
        assertEquals(esperado.getId(), atual.getId());
        assertEquals(esperado.getNome(), atual.getNome());
        assertEquals(esperado.getDescricao(), atual.getDescricao());
        assertEquals(esperado.getValorDesconto(), atual.getValorDesconto());
        assertEquals(esperado.getValido(), atual.getValido());
        assertEquals(esperado.getParceiro().getId(), atual.getParceiro().getId());
        assertEquals(esperado.getParceiro().getNome(), atual.getParceiro().getNome());
        assertEquals(esperado.getParceiro().getEmail(), atual.getParceiro().getEmail());
        assertEquals(esperado.getParceiro().getTelefone(), atual.getParceiro().getTelefone());

    }
}

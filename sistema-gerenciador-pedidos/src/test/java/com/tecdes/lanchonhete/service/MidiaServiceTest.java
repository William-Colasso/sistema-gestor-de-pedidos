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

import com.tecdes.lanchonete.model.entity.Midia;
import com.tecdes.lanchonete.model.enums.TipoMidia;
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

    @Test
    void deveCriarMidia() {
        // Arrange
        Midia esperado = criarMidiaGenerico();
        when(midiaRepository.create(esperado)).thenReturn(esperado);

        // Act
        Midia atual = midiaService.create(esperado);

        // Assert
        verify(midiaRepository, times(1)).create(esperado);
        verificarMidia(esperado, atual);
    }

    @Test
    void deveObterMidia() {
        // Arrange
        Midia esperado = criarMidiaGenerico();
        esperado.setId(1L);
        when(midiaRepository.getById(esperado.getId())).thenReturn(esperado);

        // Act
        Midia atual = midiaService.getById(esperado.getId());

        // Assert
        verify(midiaRepository, times(1)).getById(esperado.getId());
        verificarMidia(esperado, atual);
    }

    @Test
    void deveObterMidias() {
        // Arrange
        Midia esperado1 = criarMidiaGenerico();
        Midia esperado2 = criarMidia("Segunda mídia", "midia-teste2".getBytes(), TipoMidia.VIDEO);
        List<Midia> esperados = Arrays.asList(esperado1, esperado2);
        when(midiaRepository.getAll()).thenReturn(esperados);

        // Act
        List<Midia> atuais = midiaService.getAll();

        // Assert
        verify(midiaRepository, times(1)).getAll();
        for (int i=0; i<2; i++) {
            verificarMidia(esperados.get(i), atuais.get(i));
        }
    }

    @Test
    void deveAtualizarMidia() {
        // Arrange
        Midia midia = criarMidiaGenerico();
        midia.setId(1L);

        // Act
        midiaService.update(midia);

        // Assert
        verify(midiaRepository, times(1)).update(midia);
    }

    @Test
    void deveRemoverMidiaComId() {
        // Arrange
        Midia midia = criarMidiaGenerico();
        midia.setId(1L);

        // Act
        midiaService.delete(midia.getId());

        // Assert
        verify(midiaRepository, times(1)).delete(midia.getId());
    }

    // --------------------------- Métodos auxiliares -----------------

    private Midia criarMidiaGenerico() {
        return criarMidia("Midia", "midia-teste".getBytes(), TipoMidia.IMAGEM);
    }

    private Midia criarMidia(String descricao, byte[] arquivo, TipoMidia tipo) {
        Midia m = new Midia();
        m.setIdItem(1L);
        m.setDescricao(descricao);
        m.setArquivo(arquivo);
        m.setTipo(tipo);
        return m;
    }

    private void verificarMidia(Midia esperado, Midia atual) {
        assertEquals(esperado.getIdItem(), atual.getIdItem());
        assertEquals(esperado.getDescricao(), atual.getDescricao());
        assertEquals(esperado.getTipo(), atual.getTipo());
        assertEquals(esperado.getArquivo().length, atual.getArquivo().length);
        assertEquals(esperado.getIdItem(), atual.getIdItem());
    }
}

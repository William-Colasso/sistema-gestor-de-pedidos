package com.tecdes.lanchonhete.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tecdes.lanchonete.exception.InvalidDeleteOperationException;
import com.tecdes.lanchonete.exception.InvalidFieldException;
import com.tecdes.lanchonete.exception.InvalidIdException;
import com.tecdes.lanchonete.model.entity.Item;
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

    @Test 
    void naoDeveCriarMidiaComCamposNulos() {
        // Arrange
        Midia itemNulo = criarMidiaGenerico();
        itemNulo.setItem(null);
        Midia descricaoMidia = criarMidiaGenerico();
        descricaoMidia.setDescricao(null);
        Midia imagemNula = criarMidia("Descricao", null, TipoMidia.IMAGEM);
        Midia tipoNulo = criarMidiaGenerico();
        tipoNulo.setTipo(null);

        // Act / Assert
        assertThrows(InvalidFieldException.class, () -> midiaService.create(itemNulo));
        verify(midiaRepository, times(0)).create(itemNulo);
        assertThrows(InvalidFieldException.class, () -> midiaService.create(descricaoMidia));
        verify(midiaRepository, times(0)).create(descricaoMidia);
        assertThrows(InvalidFieldException.class, () -> midiaService.create(imagemNula));
        verify(midiaRepository, times(0)).create(imagemNula);
        assertThrows(InvalidFieldException.class, () -> midiaService.create(tipoNulo));
        verify(midiaRepository, times(0)).create(tipoNulo);
    }

    @Test
    void naoDeveAtualizarMidiaComMidiaNula() {
        // Arrange
        Midia nula = criarMidiaGenerico();
        nula.setId(null);

        // Act / Assert
        assertThrows(InvalidIdException.class, () -> midiaService.update(nula));
        verify(midiaRepository, times(0)).update(nula);
    }

    @Test
    void naoDeveRemoverMidiaComIdNulo() {
        // Arrange
        Midia nula = criarMidiaGenerico();
        nula.setId(null);

        // Act / Assert
        assertThrows(InvalidDeleteOperationException.class, () -> midiaService.delete(nula.getId()));
        verify(midiaRepository, times(0)).delete(nula.getId());
    }

    @Test
    void deveRetornarApenasMidiasDoItemInformado() {
        // Arrange
        Midia m1 = new Midia();
        Item i1 = new Item();
        i1.setId(1L);
        m1.setItem(i1);

        Midia m2 = new Midia();
        Item i2 = new Item();
        i2.setId(2L);
        m2.setItem(i2);

        Midia m3 = new Midia();
        Item i3 = new Item();
        i3.setId(1L);
        m3.setItem(i3);

        when(midiaRepository.getAll()).thenReturn(new ArrayList<>(Arrays.asList(m1, m2, m3)));

        // Act
        List<Midia> result = midiaService.getMidiasByIdItem(1L);

        // Assert
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getItem().getId());
        assertEquals(1L, result.get(1).getItem().getId());

        verify(midiaRepository, times(1)).getAll();
    }

    @Test
    void deveRetornarListaVaziaSeNaoExistirMidiaDoItem() {
        // Arrange
        Midia m1 = new Midia();
        Item i1 = new Item();
        i1.setId(2L);
        m1.setItem(i1);

        when(midiaRepository.getAll()).thenReturn(new ArrayList<>(Arrays.asList(m1)));

        // Act
        List<Midia> result = midiaService.getMidiasByIdItem(1L);

        // Assert
        assertTrue(result.isEmpty());
        verify(midiaRepository).getAll();
    }

    // --------------------------- Métodos auxiliares -----------------

    private Midia criarMidiaGenerico() {
        return criarMidia("Midia", "midia-teste".getBytes(), TipoMidia.IMAGEM);
    }

    private Midia criarMidia(String descricao, byte[] arquivo, TipoMidia tipo) {
        Midia m = new Midia();
        Item i = new Item();
        i.setId(1L);
        m.setItem(i);
        m.setDescricao(descricao);
        m.setArquivo(arquivo);
        m.setTipo(tipo);
        return m;
    }

    private void verificarMidia(Midia esperado, Midia atual) {
        assertEquals(esperado.getItem().getId(), atual.getItem().getId());
        assertEquals(esperado.getDescricao(), atual.getDescricao());
        assertEquals(esperado.getTipo(), atual.getTipo());
        assertEquals(esperado.getArquivo().length, atual.getArquivo().length);
        assertEquals(esperado.getItem().getId(), atual.getItem().getId());
    }
}

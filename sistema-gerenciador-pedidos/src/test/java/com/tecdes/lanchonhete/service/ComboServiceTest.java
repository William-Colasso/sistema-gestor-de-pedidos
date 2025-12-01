package com.tecdes.lanchonhete.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
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
import com.tecdes.lanchonete.model.entity.Combo;
import com.tecdes.lanchonete.model.enums.TipoItem;
import com.tecdes.lanchonete.repository.implementation.IComboRepository;
import com.tecdes.lanchonete.repository.implementation.IItemRepository;
import com.tecdes.lanchonete.service.ComboService;
import com.tecdes.lanchonete.service.MidiaService;

public class ComboServiceTest {
    @Mock
    private IComboRepository comboRepository;

    @Mock
    private IItemRepository itemRepository;

    @Mock
    private MidiaService midiaService;

    @InjectMocks
    private ComboService comboService;


    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveCriarCombo() {
        // Arrange
        Combo esperado = criarComboGenerico();
        when(comboRepository.create(esperado)).thenReturn(esperado);
        when(itemRepository.create(esperado)).thenReturn(esperado);

        // Act
        Combo atual = comboService.create(esperado);

        // Assert
        verify(comboRepository, times(1)).create(esperado);
        verify(itemRepository, times(1)).create(esperado);
        verificarCombo(esperado, atual);
    }

    @Test
    void deveObterCombo() {
        // Arrange
        Combo esperado = criarComboGenerico();
        esperado.setId(1L);
        when(itemRepository.getById(esperado.getId())).thenReturn(esperado);
        when(comboRepository.getById(esperado.getId())).thenReturn(esperado);

        // Act
        Combo atual = comboService.getById(esperado.getId());

        // Assert
        verify(comboRepository, times(1)).getById(esperado.getId());
        verify(itemRepository, times(1)).getById(esperado.getId());
        verificarCombo(esperado, atual);
    }

    @Test
    void deveObterCombos() {
        // Arrange
        Combo esperado1 = criarComboGenerico();
        esperado1.setId(1L);
        Combo esperado2 = criarCombo("SegundoCombo");
        esperado2.setId(2L);
        List<Combo> esperados = Arrays.asList(esperado1, esperado2);
        when(comboRepository.getAll()).thenReturn(esperados);
        when(itemRepository.getById(esperado1.getId())).thenReturn(esperado1);
        when(itemRepository.getById(esperado2.getId())).thenReturn(esperado2);

        // Act
        List<Combo> atuais = comboService.getAll();

        // Assert
        verify(comboRepository, times(1)).getAll();
        verify(itemRepository, times(1)).getById(esperado1.getId());
        verify(itemRepository, times(1)).getById(esperado2.getId());

        for (int i=0; i<2; i++) {
            verificarCombo(esperados.get(i), atuais.get(i));
        }
        
    }

    @Test
    void deveAtualizarCombo() {
        // Arrange
        Combo combo = criarComboGenerico();
        combo.setId(1L);

        // Act
        comboService.update(combo);

        // Assert
        verify(comboRepository, times(1)).update(combo);
        verify(itemRepository, times(1)).update(combo);
    }

    @Test
    void deveRemoverComboComId() {
        // Arrange
        Combo combo = criarComboGenerico();
        combo.setId(1L);

        // Act
        comboService.delete(combo.getId());

        // Assert
        verify(comboRepository, times(1)).delete(combo.getId());
        verify(itemRepository, times(1)).delete(combo.getId());
    }

    @Test 
    void naoDeveCriarComboComCamposNulos() {
        // Arrange
        Combo nomeNulo = criarCombo(null);
        Combo descricaoNula = criarComboGenerico();
        descricaoNula.setDescricao(null);
        Combo tipoNulo = criarComboGenerico();
        tipoNulo.setTipoItem(null);
        when(comboRepository.create(tipoNulo)).thenReturn(tipoNulo);
        when(itemRepository.create(tipoNulo)).thenReturn(tipoNulo);

        // Act / Assert
        assertThrows(InvalidFieldException.class, () -> comboService.create(nomeNulo));
        verify(comboRepository, times(0)).create(nomeNulo);
        assertThrows(InvalidFieldException.class, () -> comboService.create(descricaoNula));
        verify(comboRepository, times(0)).create(descricaoNula);
        Combo retornoTipoNulo = comboService.create(tipoNulo); // Apenas atribui o tipo
        assertEquals(TipoItem.COMBO, retornoTipoNulo.getTipoItem());
        verify(comboRepository, times(1)).create(tipoNulo);
    }

    @Test
    void naoDeveAtualizarComboComComboNulo() {
        // Arrange
        Combo nulo = criarComboGenerico();
        nulo.setId(null);

        // Act / Assert
        assertThrows(InvalidIdException.class, () -> comboService.update(nulo));
        verify(comboRepository, times(0)).update(nulo);
    }

    @Test
    void naoDeveRemoverComboComIdNulo() {
        // Arrange
        Combo nulo = criarComboGenerico();

        // Act / Assert
        assertThrows(InvalidDeleteOperationException.class, () -> comboService.delete(nulo.getId()));
        verify(comboRepository, times(0)).delete(nulo.getId());
    }

    @Test
    void naoDeveCriarComboComDescontoNegativo() {
        // Arrange
        Combo descontoNegativo = criarComboGenerico();
        descontoNegativo.setDesconto(-10);

        // Act / Assert
        assertThrows(InvalidFieldException.class, () -> comboService.create(descontoNegativo));
        verify(comboRepository, times(0)).create(descontoNegativo);
    }

    // --------------------------- Métodos auxiliares -----------------

    private Combo criarComboGenerico() {
        return criarCombo("Combo");
    }

    private Combo criarCombo(String nome) {
        Combo combo = new Combo();
        combo.setNome(nome);
        combo.setDescricao("Descrição " + nome);
        combo.setTipoItem(TipoItem.COMBO);
        combo.setDataCriacao(new Date(System.currentTimeMillis()));
        combo.setStatusAtivo(1);
        combo.setDesconto(10);
        return combo;
    }

    private void verificarCombo(Combo esperado, Combo atual ) {
        assertEquals(esperado.getId(), atual.getId());
        assertEquals(esperado.getNome(), atual.getNome());
        assertEquals(esperado.getTipoItem(), atual.getTipoItem());
        assertEquals(esperado.getDesconto(), atual.getDesconto());
        assertEquals(esperado.getStatusAtivo(), atual.getStatusAtivo());
    }
}

package com.tecdes.lanchonhete.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

import com.tecdes.lanchonete.exception.InvalidDeleteOperationException;
import com.tecdes.lanchonete.exception.InvalidFieldException;
import com.tecdes.lanchonete.exception.InvalidIdException;
import com.tecdes.lanchonete.model.entity.Parceiro;
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

    @Test
    void deveCriarParceiro() {
        // Arrange
        Parceiro esperado = criarParceiroGenerico();
        when(parceiroRepository.create(esperado)).thenReturn(esperado);

        // Act
        Parceiro atual = parceiroService.create(esperado);

        // Assert
        verify(parceiroRepository, times(1)).create(esperado);
        verificarParceiro(esperado, atual);
    }

    @Test
    void deveObterParceiro() {
        // Arrange
        Parceiro esperado = criarParceiroGenerico();
        esperado.setId(1L);
        when(parceiroRepository.getById(esperado.getId())).thenReturn(esperado);

        // Act
        Parceiro atual = parceiroService.getById(esperado.getId());

        // Assert
        verify(parceiroRepository, times(1)).getById(esperado.getId());
        verificarParceiro(esperado, atual);
    }

    @Test
    void deveObterParceiros() {
        // Arrange
        Parceiro esperado1 = criarParceiroGenerico();
        Parceiro esperado2 = criarParceiro("SegundoParceiro", "321.321.321-32", "47977123321");
        List<Parceiro> esperados = Arrays.asList(esperado1, esperado2);
        when(parceiroRepository.getAll()).thenReturn(esperados);

        // Act
        List<Parceiro> atuais = parceiroService.getAll();

        // Assert
        verify(parceiroRepository, times(1)).getAll();
        for (int i=0; i<2; i++) {
            verificarParceiro(esperados.get(i), atuais.get(i));
        }
    }

    @Test
    void deveAtualizarParceiro() {
        // Arrange
        Parceiro parceiro = criarParceiroGenerico();
        parceiro.setId(1L);

        // Act
        parceiroService.update(parceiro);

        // Assert
        verify(parceiroRepository, times(1)).update(parceiro);
    }

    @Test
    void deveRemoverParceiroComId() {
        // Arrange
        Parceiro parceiro = criarParceiroGenerico();
        parceiro.setId(1L);

        // Act
        parceiroService.delete(parceiro.getId());

        // Assert
        verify(parceiroRepository, times(1)).delete(parceiro.getId());
    }

    @Test 
    void naoDeveCriarParceiroComCamposNulos() {
        // Arrange
        Parceiro nomeNulo = criarParceiro(null, "a@gmail.com", "47912341234");
        Parceiro emailNulo = criarParceiro("emailNulo", null, "47912341234");
        Parceiro telefoneNulo = criarParceiro("telefoneNulo", "a@gmail.com", null);

        // Act / Assert
        assertThrows(InvalidFieldException.class, () -> parceiroService.create(nomeNulo));
        verify(parceiroRepository, times(0)).create(nomeNulo);
        assertThrows(InvalidFieldException.class, () -> parceiroService.create(emailNulo));
        verify(parceiroRepository, times(0)).create(emailNulo);
        assertThrows(InvalidFieldException.class, () -> parceiroService.create(telefoneNulo));
        verify(parceiroRepository, times(0)).create(telefoneNulo);
    }

    @Test
    void naoDeveAtualizarParceiroComParceiroNulo() {
        // Arrange
        Parceiro nulo = criarParceiroGenerico();
        nulo.setId(null);

        // Act / Assert
        assertThrows(InvalidIdException.class, () -> parceiroService.update(nulo));
        verify(parceiroRepository, times(0)).update(nulo);
    }

    @Test
    void naoDeveRemoverParceiroComIdNulo() {
        // Arrange
        Parceiro nula = criarParceiroGenerico();

        // Act / Assert
        assertThrows(InvalidDeleteOperationException.class, () -> parceiroService.delete(nula.getId()));
        verify(parceiroRepository, times(0)).delete(nula.getId());
    }

    @Test
    void naoDeveCriarParceiroComTelefoneInvalido() {
        // Arrange
        Parceiro telefoneInvalido = criarParceiroGenerico();
        telefoneInvalido.setTelefone("479123412341"); // 12 dígitos

        // Act / Assert
        assertThrows(InvalidFieldException.class, () -> parceiroService.create(telefoneInvalido));
        verify(parceiroRepository, times(0)).create(telefoneInvalido);
    }

    // --------------------------- Métodos auxiliares -----------------

    private Parceiro criarParceiroGenerico() {
        return criarParceiro("Parceiro", "123.123.123-12", "47987654321");
    }

    private Parceiro criarParceiro(String nome, String email, String telefone) {
        Parceiro p = new Parceiro();
        p.setNome(nome);
        p.setEmail(email);
        p.setTelefone(telefone);
        return p;
    }

    private void verificarParceiro(Parceiro esperado, Parceiro atual) {
        assertEquals(esperado.getNome(), atual.getNome());
        assertEquals(esperado.getEmail(), atual.getEmail());
        assertEquals(esperado.getTelefone(), atual.getTelefone());
    }
}

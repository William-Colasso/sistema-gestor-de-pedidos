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

import com.tecdes.lanchonete.model.entity.Gerente;
import com.tecdes.lanchonete.repository.implementation.IGerenteRepository;
import com.tecdes.lanchonete.service.FuncionarioService;
import com.tecdes.lanchonete.service.GerenteService;

public class GerenteServiceTest {
    @Mock
    private IGerenteRepository gerenteRepository;

    @Mock
    private FuncionarioService funcionarioService;

    @InjectMocks
    private GerenteService gerenteService;
    
    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveCriarGerente() {
        // Arrange
        Gerente esperado = criarGerenteGenerico();
        when(gerenteRepository.create(esperado)).thenReturn(esperado);
        when(funcionarioService.create(esperado)).thenReturn(esperado);

        // Act
        Gerente atual = gerenteService.create(esperado);

        // Assert
        verify(gerenteRepository, times(1)).create(esperado);
        verify(funcionarioService, times(1)).create(esperado);
        verificarGerente(esperado, atual);
    }

    @Test
    void deveObterGerente() {
        // Arrange
        Gerente esperado = criarGerenteGenerico();
        esperado.setId(1L);
        when(gerenteRepository.getById(esperado.getId())).thenReturn(esperado);

        // Act
        Gerente atual = gerenteService.getById(esperado.getId());

        // Assert
        verify(gerenteRepository, times(1)).getById(esperado.getId());
        verificarGerente(esperado, atual);
    }

    @Test
    void deveObterGerentes() {
        // Arrange
        Gerente esperado1 = criarGerenteGenerico();
        esperado1.setId(1L);
        Gerente esperado2 = criarGerente("SegundoGerente", "321");
        esperado2.setId(2L);
        List<Gerente> esperados = Arrays.asList(esperado1, esperado2);
        when(gerenteRepository.getAll()).thenReturn(esperados);

        // Act
        List<Gerente> atuais = gerenteService.getAll();

        // Assert
        verify(gerenteRepository, times(1)).getAll();
        for (int i=0; i<2; i++) {
            verificarGerente(esperados.get(i), atuais.get(i));
        }
        
    }

    @Test
    void deveAtualizarGerente() {
        // Arrange
        Gerente combo = criarGerenteGenerico();
        combo.setId(1L);

        // Act
        gerenteService.update(combo);

        // Assert
        verify(gerenteRepository, times(1)).update(combo);
        verify(funcionarioService, times(1)).update(combo);
    }

    @Test
    void deveRemoverGerenteComId() {
        // Arrange
        Gerente combo = criarGerenteGenerico();
        combo.setId(1L);

        // Act
        gerenteService.delete(combo.getId());

        // Assert
        verify(gerenteRepository, times(1)).delete(combo.getId());
        verify(funcionarioService, times(1)).delete(combo.getId());
    }

    @Test 
    void naoDeveCriarGerenteComCamposNulos() {
        // Arrange
        Gerente nomeNulo = criarGerenteGenerico();
        nomeNulo.setNome(null);
        Gerente dtNacimentoNula = criarGerenteGenerico();
        dtNacimentoNula.setDataNascimento(null);
        Gerente cpfNulo = criarGerenteGenerico();
        cpfNulo.setCpf(null);
        Gerente loginNulo = criarGerente(null, "senha");
        Gerente senhaNula = criarGerente("login", null);

        // Act / Assert
        assertThrows(Exception.class, () -> gerenteService.create(nomeNulo));
        verify(gerenteRepository, times(0)).create(nomeNulo);
        assertThrows(Exception.class, () -> gerenteService.create(dtNacimentoNula));
        verify(gerenteRepository, times(0)).create(dtNacimentoNula);
        assertThrows(Exception.class, () -> gerenteService.create(cpfNulo));
        verify(gerenteRepository, times(0)).create(cpfNulo);
        assertThrows(Exception.class, () -> gerenteService.create(loginNulo));
        verify(gerenteRepository, times(0)).create(loginNulo);
        assertThrows(Exception.class, () -> gerenteService.create(senhaNula));
        verify(gerenteRepository, times(0)).create(senhaNula);
    }

    @Test
    void naoDeveAtualizarGerenteComGerenteNulo() {
        // Arrange
        Gerente nulo = null;

        // Act / Assert
        assertThrows(Exception.class, () -> gerenteService.update(nulo));
        verify(gerenteRepository, times(0)).update(nulo);
    }

    @Test
    void naoDeveRemoverCategoriaComIdNulo() {
        // Arrange
        Gerente nulo = criarGerenteGenerico();

        // Act / Assert
        assertThrows(Exception.class, () -> gerenteService.delete(nulo.getId()));
        verify(gerenteRepository, times(0)).delete(nulo.getId());
    }

    @Test
    void naoDeveCriarGerenteComCpfInvalido() {
        // Arrange
        Gerente cpfInvalido = criarGerenteGenerico();
        cpfInvalido.setCpf("123.123.123-123"); // 15 dígitos

        // Act / Assert
        assertThrows(Exception.class, () -> gerenteService.create(cpfInvalido));
        verify(gerenteRepository, times(0)).create(cpfInvalido);
    }

    // --------------------------- Métodos auxiliares -----------------

    private Gerente criarGerenteGenerico() {
        return criarGerente("Gerente", "123");
    }

    private Gerente criarGerente(String login, String senha) {
        Gerente g = new Gerente();
        g.setNome("Gerente");
        g.setCpf("123.123.123-12");
        g.setDataNascimento(Date.valueOf("2000-11-12"));
        g.setLogin(login);
        g.setSenha(senha);
        return g;
    }

    private void verificarGerente(Gerente esperado, Gerente atual) {
        assertEquals(esperado.getNome(), atual.getNome(), "Nome incorreto");
        assertEquals(esperado.getCpf(), atual.getCpf(), "Cpf incorreto");
        assertEquals(esperado.getDataNascimento(), atual.getDataNascimento(), "Login incorreto");
        assertEquals(esperado.getSenha(), atual.getSenha(), "Senha incorreta");
    }
}   

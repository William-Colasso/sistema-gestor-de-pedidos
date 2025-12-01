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

import com.tecdes.lanchonete.model.entity.Funcionario;
import com.tecdes.lanchonete.repository.implementation.IFuncionarioRepository;
import com.tecdes.lanchonete.service.FuncionarioService;

public class FuncionarioServiceTest {

    @Mock
    private IFuncionarioRepository funcionarioRepository;

    @InjectMocks
    private FuncionarioService funcionarioService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }@Test
    void deveCriarFuncionario() {
        // Arrange
        Funcionario esperado = criarFuncionarioGenerico();
        when(funcionarioRepository.create(esperado)).thenReturn(esperado);

        // Act
        Funcionario atual = funcionarioService.create(esperado);

        // Assert
        verify(funcionarioRepository, times(1)).create(esperado);
        verificarFuncionario(esperado, atual);
    }

    @Test
    void deveObterFuncionario() {
        // Arrange
        Funcionario esperado = criarFuncionarioGenerico();
        esperado.setId(1L);
        when(funcionarioRepository.getById(esperado.getId())).thenReturn(esperado);

        // Act
        Funcionario atual = funcionarioService.getById(esperado.getId());

        // Assert
        verify(funcionarioRepository, times(1)).getById(esperado.getId());
        verificarFuncionario(esperado, atual);
    }

    @Test
    void deveObterFuncionarios() {
        // Arrange
        Funcionario esperado1 = criarFuncionarioGenerico();
        Funcionario esperado2 = criarFuncionario("SegundoFuncionario", "321.321.321-32", "2000-10-10");
        List<Funcionario> esperados = Arrays.asList(esperado1, esperado2);
        when(funcionarioRepository.getAll()).thenReturn(esperados);

        // Act
        List<Funcionario> atuais = funcionarioService.getAll();

        // Assert
        verify(funcionarioRepository, times(1)).getAll();
        for (int i=0; i<2; i++) {
            verificarFuncionario(esperados.get(i), atuais.get(i));
        }
    }

    @Test
    void deveAtualizarFuncionario() {
        // Arrange
        Funcionario funcionario = criarFuncionarioGenerico();
        funcionario.setId(1L);

        // Act
        funcionarioService.update(funcionario);

        // Assert
        verify(funcionarioRepository, times(1)).update(funcionario);
    }

    @Test
    void deveRemoverFuncionarioComId() {
        // Arrange
        Funcionario funcionario = criarFuncionarioGenerico();
        funcionario.setId(1L);

        // Act
        funcionarioService.delete(funcionario.getId());

        // Assert
        verify(funcionarioRepository, times(1)).delete(funcionario.getId());
    }

    @Test 
    void naoDeveCriarFuncionarioComCamposNulos() {
        // Arrange
        Funcionario nomeNulo = criarFuncionarioGenerico();
        nomeNulo.setNome(null);
        Funcionario dataNascimentoNulo = criarFuncionarioGenerico();
        nomeNulo.setDataNascimento(null);
        Funcionario cpfNulo = criarFuncionarioGenerico();
        cpfNulo.setCpf(null);

        // Act / Assert
        assertThrows(Exception.class, () -> funcionarioService.create(nomeNulo));
        verify(funcionarioRepository, times(0)).create(nomeNulo);
        assertThrows(Exception.class, () -> funcionarioService.create(dataNascimentoNulo));
        verify(funcionarioRepository, times(0)).create(dataNascimentoNulo);
        assertThrows(Exception.class, () -> funcionarioService.create(cpfNulo));
        verify(funcionarioRepository, times(0)).create(cpfNulo);
    }

    @Test
    void naoDeveAtualizarFuncionarioComFuncionarioNulo() {
        // Arrange
        Funcionario nulo = null;

        // Act / Assert
        assertThrows(Exception.class, () -> funcionarioService.update(nulo));
        verify(funcionarioRepository, times(0)).update(nulo);
    }

    @Test
    void naoDeveRemoverFuncionarioComIdNulo() {
        // Arrange
        Funcionario nulo = criarFuncionarioGenerico();

        // Act / Assert
        assertThrows(Exception.class, () -> funcionarioService.delete(nulo.getId()));
        verify(funcionarioRepository, times(0)).delete(nulo.getId());
    }

    @Test
    void naoDeveCriarFuncionarioComCpfInvalido() {
        // Arrange
        Funcionario cpfInvalido = criarFuncionarioGenerico();
        cpfInvalido.setCpf("123.123.123-123"); // 15 dígitos

        // Act / Assert
        assertThrows(Exception.class, () -> funcionarioService.create(cpfInvalido));
        verify(funcionarioRepository, times(0)).create(cpfInvalido);
    }

    // --------------------------- Métodos auxiliares -----------------

    private Funcionario criarFuncionarioGenerico() {
        return criarFuncionario("Funcionario", "123.123.123-12", "1990-01-01");
    }

    private Funcionario criarFuncionario(String nome, String cpf, String dataNascimento) {
        Funcionario f = new Funcionario();
        f.setNome(nome);
        f.setCpf(cpf);
        f.setDataNascimento(Date.valueOf(dataNascimento));
        return f;
    }
    private void verificarFuncionario(Funcionario esperado, Funcionario atual) {
        assertEquals(esperado.getNome(), atual.getNome());
        assertEquals(esperado.getCpf(), atual.getCpf());
        assertEquals(esperado.getDataNascimento(), atual.getDataNascimento());
    }
}

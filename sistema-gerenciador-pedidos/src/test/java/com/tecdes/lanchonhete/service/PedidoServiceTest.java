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

import com.tecdes.lanchonete.model.entity.CategoriaProduto;
import com.tecdes.lanchonete.model.entity.Cliente;
import com.tecdes.lanchonete.model.entity.Combo;
import com.tecdes.lanchonete.model.entity.Cupom;
import com.tecdes.lanchonete.model.entity.Funcionario;
import com.tecdes.lanchonete.model.entity.Item;
import com.tecdes.lanchonete.model.entity.Pagamento;
import com.tecdes.lanchonete.model.entity.Parceiro;
import com.tecdes.lanchonete.model.entity.Pedido;
import com.tecdes.lanchonete.model.entity.Produto;
import com.tecdes.lanchonete.model.enums.TipoItem;
import com.tecdes.lanchonete.repository.implementation.IPedidoRepository;
import com.tecdes.lanchonete.service.PedidoService;

public class PedidoServiceTest {
    @Mock
    private IPedidoRepository pedidoRepository;
    
    @InjectMocks
    private PedidoService pedidoService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveCriarPedido() {
        // Arrange
        Pedido esperado = criarPedidoGenerico();
        when(pedidoRepository.create(esperado)).thenReturn(esperado);

        // Act
        Pedido atual = pedidoService.create(esperado);

        // Assert
        verify(pedidoRepository, times(1)).create(esperado);
        verificarPedido(esperado, atual);
    }

    @Test
    void deveObterPedido() {
        // Arrange
        Pedido esperado = criarPedidoGenerico();
        esperado.setId(1L);
        when(pedidoRepository.getById(esperado.getId())).thenReturn(esperado);

        // Act
        Pedido atual = pedidoService.getById(esperado.getId());

        // Assert
        verify(pedidoRepository, times(1)).getById(esperado.getId());
        verificarPedido(esperado, atual);
    }

    @Test
    void deveObterPedidos() {
        // Arrange
        Pedido esperado1 = criarPedidoGenerico();
        Pedido esperado2 = criarPedido(true, true);
        List<Pedido> esperados = Arrays.asList(esperado1, esperado2);
        when(pedidoRepository.getAll()).thenReturn(esperados);

        // Act
        List<Pedido> atuais = pedidoService.getAll();

        // Assert
        verify(pedidoRepository, times(1)).getAll();
        for (int i=0; i<2; i++) {
            verificarPedido(esperados.get(i), atuais.get(i));
        }
    }

    @Test
    void deveAtualizarPedido() {
        // Arrange
        Pedido cliente = criarPedidoGenerico();
        cliente.setId(1L);

        // Act
        pedidoService.update(cliente);

        // Assert
        verify(pedidoRepository, times(1)).update(cliente);
    }

    @Test
    void deveRemoverPedidoComId() {
        // Arrange
        Pedido cliente = criarPedidoGenerico();
        cliente.setId(1L);

        // Act
        pedidoService.delete(cliente.getId());

        // Assert
        verify(pedidoRepository, times(1)).delete(cliente.getId());
    }

    @Test 
    void naoDeveCriarPedidoComCamposNulos() {
        // Arrange
        Pedido funcionarioNulo = criarPedidoGenerico();
        funcionarioNulo.setFuncionario(null);
        Pedido pagamentoNulo = criarPedidoGenerico();
        pagamentoNulo.setPagamento(null);
        // Cliente e nome cliente nulo
        Pedido clienteNulo = criarPedidoGenerico();
        clienteNulo.setCliente(null);
        clienteNulo.setNomeCliente(null);
        Pedido dataPedidoNulo = criarPedidoGenerico();
        dataPedidoNulo.setDataPedido(null);

        // Act / Assert
        assertThrows(Exception.class, () -> pedidoService.create(funcionarioNulo));
        verify(pedidoRepository, times(0)).create(funcionarioNulo);
        assertThrows(Exception.class, () -> pedidoService.create(pagamentoNulo));
        verify(pedidoRepository, times(0)).create(pagamentoNulo);
        assertThrows(Exception.class, () -> pedidoService.create(clienteNulo));
        verify(pedidoRepository, times(0)).create(clienteNulo);
        assertThrows(Exception.class, () -> pedidoService.create(dataPedidoNulo));
        verify(pedidoRepository, times(0)).create(dataPedidoNulo);
    }

    @Test
    void naoDeveAtualizarPedidoComPedidoNula() {
        // Arrange
        Pedido nula = null;

        // Act / Assert
        assertThrows(Exception.class, () -> pedidoService.update(nula));
        verify(pedidoRepository, times(0)).update(nula);
    }

    @Test
    void naoDeveRemoverPedidoComIdNulo() {
        // Arrange
        Pedido nula = criarPedidoGenerico();

        // Act / Assert
        assertThrows(Exception.class, () -> pedidoService.delete(nula.getId()));
        verify(pedidoRepository, times(0)).delete(nula.getId());
    }

    // --------------------------- Métodos auxiliares -----------------

    private Pedido criarPedidoGenerico() {
        return criarPedido(false, false);
    }

    
    private void verificarPedido(Pedido esperado, Pedido atual) {
        assertEquals(
            esperado.getFuncionario() != null ? esperado.getFuncionario().getId() : null,
            atual.getFuncionario() != null ? atual.getFuncionario().getId() : null
        );
        assertEquals(esperado.getPagamento().getId(), atual.getPagamento().getId());
        assertEquals(
            esperado.getCliente() != null ? esperado.getCliente().getId() : null,
            atual.getCliente() != null ? atual.getCliente().getId() : null
        );

        assertEquals(
            esperado.getCupom() != null ? esperado.getCupom().getId() : null,
            atual.getCupom() != null ? atual.getCupom().getId() : null
        );

        assertEquals(esperado.getNomeCliente(), atual.getNomeCliente());
    }

    // Cria um Pedido de teste com opções de ter Cliente e Cupom
    private Pedido criarPedido(boolean clienteCadastrado, boolean cupomUtilizado) {
        Pedido pedido = new Pedido();

        // Define o funcionario e forma de pagamento
        pedido.setFuncionario(criarFuncionario());
        pedido.setPagamento(criarPagamento());

        // Define cliente ou apenas nome do cliente
        if (clienteCadastrado) pedido.setCliente(criarCliente());
        else pedido.setNomeCliente("Carlos João");

        // Define cupom se necessário
        if (cupomUtilizado) pedido.setCupom(c());

        // Data do pedido e status padrão
        pedido.setDataPedido(new Date(System.currentTimeMillis()));
        pedido.setStatusPedido('P');

        // Define os itens do pedido
        pedido.setItens(criarItens());

        return pedido;
    }
    private Funcionario criarFuncionario() {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome("João Silva");
        funcionario.setDataNascimento(Date.valueOf("1990-05-15"));
        funcionario.setCpf("123.456.789-00");
        funcionario.setGerente(null); 
        return funcionario;
    }

    // Cria um Pagamento de teste e persiste no banco
    private Pagamento criarPagamento() {
        Pagamento pagamento = new Pagamento();
        pagamento.setNome("Cartão de Crédito");
        pagamento.setSigla("CC");
        pagamento.setImagem("midia-teste".getBytes());
        return pagamento;
    }


    // Cria um Cliente de teste e persiste no banco
    private Cliente criarCliente() {
        Cliente cliente = new Cliente();
        cliente.setNome("João Pereira");
        cliente.setTelefone("11999999999");
        cliente.setCpf("123.456.789-99");
        cliente.setDataRegistro(Date.valueOf("2024-01-15"));
        return cliente;
    }


    // Cria um Cupom de teste (com Parceiro) e persiste no banco
    private Cupom c() {
        Cupom cupom = new Cupom();
        cupom.setNome("DESCONTO10");
        cupom.setValorDesconto(10);
        cupom.setDescricao("Cupom de 10% de desconto");
        cupom.setValido('V');
        cupom.setParceiro(mapParceiro());
        return cupom;
    }

    // Cria um Parceiro de teste e persiste no banco
    private Parceiro mapParceiro() {
        Parceiro parceiro = new Parceiro();
        parceiro.setNome("Cupons");
        parceiro.setEmail("cupons@gmail.com");
        parceiro.setTelefone("11333334444");
        return parceiro;
    }


    // Cria os itens de teste (Produtos e Combo)
    private List<Item> criarItens() {
        // Produto 1
        Produto produto1 = new Produto();
        produto1.setNome("Hambúrguer Clássico");
        produto1.setDescricao("Um hambúrguer tradicional preparado com pão macio, carne grelhada e ingredientes frescos.");
        produto1.setTipoItem(TipoItem.PRODUTO); 
        produto1.setDataCriacao(Date.valueOf("2025-01-10"));
        produto1.setStatusAtivo(1);
        produto1.setValor(20);
        produto1.setCategoria(criarCategoria());
        produto1.setQuantidade(3);


        // Produto 2
        Produto produto2 = new Produto();
        produto2.setNome("Batata Frita Média");
        produto2.setDescricao("Porção de batatas crocantes por fora e macias por dentro.");
        produto2.setTipoItem(TipoItem.PRODUTO);
        produto2.setDataCriacao(Date.valueOf("2025-01-10"));
        produto2.setStatusAtivo(1);
        produto2.setValor(5);
        produto2.setCategoria(criarCategoria());
        produto2.setQuantidade(2);

        // Combo
        Combo combo = new Combo();
        combo.setNome("Combo Clássico");
        combo.setDescricao("Combo completo com hambúrguer tradicional e batata frita.");
        combo.setTipoItem(TipoItem.COMBO); 
        combo.setDataCriacao(Date.valueOf("2025-01-10"));
        combo.setStatusAtivo(1);
        combo.setDesconto(10);
        combo.setProdutos(Arrays.asList(produto1, produto2));

        return Arrays.asList(produto1, produto2, combo);
    }

    // Cria uma Categoria de Produto e persiste no banco
    private CategoriaProduto criarCategoria() {
        CategoriaProduto categoria = new CategoriaProduto();
        categoria.setNome("Comidas");
        categoria.setSigla("CM");
        categoria.setImagem("midia-teste".getBytes());
        return categoria;
    }
}

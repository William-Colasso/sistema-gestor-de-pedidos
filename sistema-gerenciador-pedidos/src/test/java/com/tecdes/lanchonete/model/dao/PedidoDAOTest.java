package com.tecdes.lanchonete.model.dao;

import java.sql.Statement;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.h2.tools.RunScript;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import com.tecdes.lanchonete.config.ConnectionFactory;
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


@DisplayName("Testes de Integração para PedidoDAO")
public class PedidoDAOTest {
    /*
     * Teste de integração para PedidoDAO
     * - Usa H2 em memória (definido em src/test/resources/db.properties)
     * - Não altera o código e o banco real
     */


    private PedidoDAO pedidoDAO;

    @BeforeEach
    void setup() throws Exception {
        // Inicializa a DAO antes de cada teste
        pedidoDAO = new PedidoDAO();

        // Roda script SQL para limpar/inicializar banco H2
        try (Connection conn = ConnectionFactory.getConnection();
             Statement st = conn.createStatement()) {
            RunScript.execute(conn, new FileReader("src/test/resources/script.sql"));
        }
    }

    @Test
    @DisplayName("Deve criar pedido sem cliente e sem cupom")
    void deveCriarPedidoSemClienteESemCupom() {
        // Arrange
        Pedido pedidoEsperado = criarPedido(false, false);

        // Act
        pedidoDAO.create(pedidoEsperado);
        List<Pedido> pedidos = pedidoDAO.getAll();
        Pedido pedidoAtual = pedidos.get(0);

        // Assert
        assertEquals(1, pedidos.size(), "Deve existir apenas 1 pedido no banco");
        verificarPedido(pedidoEsperado, pedidoAtual);
    }

    @Test
    @DisplayName("Deve criar pedido com cliente cadastrado e cupom")
    void deveCriarPedidoComClienteECupom() {
        // Arrange
        Pedido pedidoEsperado = criarPedido(true, true);

        // Act
        pedidoDAO.create(pedidoEsperado);
        List<Pedido> pedidos = pedidoDAO.getAll();
        Pedido pedidoAtual = pedidos.get(0);

        // Assert
        assertEquals(1, pedidos.size(), "Deve existir apenas 1 pedido no banco");
        verificarPedido(pedidoEsperado, pedidoAtual);
    }

    @Test
    @DisplayName("Não deve criar pedido com campos obrigatórios nulos")
    void naoDeveCriarPedidoComCampoObrigatoriosNulos() {
        // Arrange
        Pedido pedidoSemFuncionario = criarPedido(false, false);
        pedidoSemFuncionario.setFuncionario(null);

        Pedido pedidoSemPagamento = criarPedido(false, false);
        pedidoSemPagamento.setPagamento(null);

        Pedido pedidoSemData = criarPedido(false, false);
        pedidoSemData.setDataPedido(null);

        // Act / Assert
        assertThrows(Exception.class, () -> pedidoDAO.create(pedidoSemFuncionario), "Funcionario nulo deve gerar exceção");
        assertThrows(Exception.class, () -> pedidoDAO.create(pedidoSemPagamento), "Pagamento nulo deve gerar exceção");
        assertThrows(Exception.class, () -> pedidoDAO.create(pedidoSemData), "Data do pedido nula deve gerar exceção");
    }

    @Test
    @DisplayName("Deve obter pedido pelo ID")
    void deveObterPedidoPeloID() {
        // Arrange
        Pedido pedido1 = criarPedido(false, false);
        Pedido pedido2 = criarPedido(true, false);
        pedidoDAO.create(pedido1);
        pedidoDAO.create(pedido2);

        // Act
        Pedido resultado1 = pedidoDAO.getById(pedido1.getId());
        Pedido resultado2 = pedidoDAO.getById(pedido2.getId());

        // Assert
        verificarPedido(pedido1, resultado1);
        verificarPedido(pedido2, resultado2);
    }

    @Test
    @DisplayName("Deve obter todos os pedidos")
    void deveObterTodosOsPedidos() {
        // Arrange
        Pedido pedido1 = criarPedido(false, false);
        Pedido pedido2 = criarPedido(true, false);
        Pedido pedido3 = criarPedido(true, true);
        pedidoDAO.create(pedido1);
        pedidoDAO.create(pedido2);
        pedidoDAO.create(pedido3);

        List<Pedido> esperado = Arrays.asList(pedido1, pedido2, pedido3);

        // Act
        List<Pedido> atual = pedidoDAO.getAll();
        esperado.sort(Comparator.comparing(Pedido::getId));
        atual.sort(Comparator.comparing(Pedido::getId));

        // Assert
        for (int i = 0; i < esperado.size(); i++) {
            verificarPedido(esperado.get(i), atual.get(i));
        }
    }

    @Test
    @DisplayName("Deve atualizar pedido existente")
    void deveAtualizarPedidoExistente() {
        // Arrange
        Pedido pedido = criarPedido(false, false);
        pedidoDAO.create(pedido);
        Long id = pedido.getId();

        assertEquals("Carlos João", pedido.getNomeCliente());

        // Act
        pedido.setNomeCliente("Arthur");
        pedidoDAO.update(pedido);
        Pedido atualizado = pedidoDAO.getById(id);

        // Assert
        assertEquals("Arthur", atualizado.getNomeCliente(), "Nome do cliente deve ser atualizado");
    }

    @Test
    @DisplayName("Deve deletar pedido")
    void deveDeletarPedido() {
        // Arrange
        Pedido pedido = criarPedido(false, false);
        pedidoDAO.create(pedido);
        List<Pedido> pedidos = pedidoDAO.getAll();
        assertEquals(1, pedidos.size());

        // Act
        pedidoDAO.delete(pedidos.get(0).getId());
        List<Pedido> pedidosAposDelete = pedidoDAO.getAll();

        // Assert
        assertEquals(0, pedidosAposDelete.size(), "Todos os pedidos devem ser removidos");
    }

    // ------------------------ MÉTODOS AUXILIARES ------------------------

    // Método de verificação para o "Assert"
    // Compara um Pedido esperado com o Pedido atual do banco para garantir que todos os atributos importantes coincidem
    private void verificarPedido(Pedido esperado, Pedido atual) {
        // Verifica se o Funcionario associado é o mesmo
        assertEquals(esperado.getFuncionario().getId(), atual.getFuncionario().getId());

        // Verifica se a forma de Pagamento é a mesma
        assertEquals(esperado.getPagamento().getId(), atual.getPagamento().getId());

        // Verifica o Cliente (pode ser null, então usa operador ternário)
        assertEquals(
            esperado.getCliente() != null ? esperado.getCliente().getId() : null,
            atual.getCliente() != null ? atual.getCliente().getId() : null
        );

        // Verifica o Cupom (pode ser null)
        assertEquals(
            esperado.getCupom() != null ? esperado.getCupom().getId() : null,
            atual.getCupom() != null ? atual.getCupom().getId() : null
        );

        // Verifica o nome do cliente (para pedidos sem cliente cadastrado)
        assertEquals(esperado.getNomeCliente(), atual.getNomeCliente());

        // Verifica os itens do pedido
        List<Item> esperadoItens = esperado.getItens();
        List<Item> atualItens = atual.getItens();

        // Ordena por ID para garantir comparação consistente
        esperadoItens.sort(Comparator.comparing(Item::getId));
        atualItens.sort(Comparator.comparing(Item::getId));

        // Comparação profunda de todos os atributos dos itens
        assertThat(atualItens).usingRecursiveComparison().isEqualTo(esperadoItens);
    }

    // ------------------------ MÉTODOS DE MAPEAMENTO ------------------------

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

    // ------------------------ MÉTODOS DE ENTIDADES ------------------------

    // Cria um Funcionario de teste e persiste no banco
    private Funcionario criarFuncionario() {
        Funcionario funcionario = new Funcionario();
        funcionario.setNome("João Silva");
        funcionario.setDataNascimento(Date.valueOf("1990-05-15"));
        funcionario.setCpf("123.456.789-00");
        funcionario.setGerente(null); // sem gerente por simplicidade

        // Persiste no banco e define ID gerado
        funcionario.setId(salvarFuncionarioNoBanco(funcionario));
        return funcionario;
    }

    // Persiste o Funcionario no banco e retorna o ID gerado
    private Long salvarFuncionarioNoBanco(Funcionario funcionario) {
        String sql = """
            INSERT INTO t_sgp_funcionario(
                id_gerente, nm_funcionario, dt_nascimento, nr_cpf
            ) VALUES (?, ?, ?, ?)
        """;

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement pr = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            if (funcionario.getGerente() != null) {
                pr.setLong(1, funcionario.getGerente().getId());
            } else {
                pr.setNull(1, java.sql.Types.INTEGER); // valor NULL para coluna de gerente
            }

            pr.setString(2, funcionario.getNome());
            pr.setDate(3, funcionario.getDataNascimento());
            pr.setString(4, funcionario.getCpf());

            pr.executeUpdate();

            ResultSet rs = pr.getGeneratedKeys();
            if (rs.next()) return rs.getLong(1);
            else throw new RuntimeException("Nenhuma chave gerada ao salvar Funcionario.");

        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar funcionario no Banco durante testes de Pedido DAO: " + e);
        }
    }

    // Cria um Pagamento de teste e persiste no banco
    private Pagamento criarPagamento() {
        Pagamento pagamento = new Pagamento();
        pagamento.setNome("Cartão de Crédito");
        pagamento.setSigla("CC");

        pagamento.setId(salvarPagamentoNoBanco(pagamento));
        return pagamento;
    }

    // Persiste o Pagamento no banco e retorna ID
    private Long salvarPagamentoNoBanco(Pagamento pagamento) {
        String sql = """
            INSERT INTO t_sgp_forma_pagamento (
                nm_pagamento, sg_pagamento
            ) VALUES (?, ?)
        """;

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement pr = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pr.setString(1, pagamento.getNome());
            pr.setString(2, pagamento.getSigla());

            pr.executeUpdate();

            ResultSet rs = pr.getGeneratedKeys();
            if (rs.next()) return rs.getLong(1);
            else throw new RuntimeException("Nenhuma chave gerada ao salvar Pagamento.");
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar pagamento no Banco durante testes de Pedido DAO: " + e);
        }
    }

    // Cria um Cliente de teste e persiste no banco
    private Cliente criarCliente() {
        Cliente cliente = new Cliente();
        cliente.setNome("João Pereira");
        cliente.setTelefone("11999999999");
        cliente.setCpf("123.456.789-99");
        cliente.setDataRegistro(Date.valueOf("2024-01-15"));

        cliente.setId(salvarClienteNoBanco(cliente));
        return cliente;
    }

    // Persiste o Cliente no banco e retorna ID
    private Long salvarClienteNoBanco(Cliente cliente) {
        String sql = """
            INSERT INTO t_sgp_cliente (
                nm_cliente, nr_telefone, nr_cpf, dt_registro
            ) VALUES (?, ?, ?, ?)
        """;

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement pr = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pr.setString(1, cliente.getNome());
            pr.setString(2, cliente.getTelefone());
            pr.setString(3, cliente.getCpf());
            pr.setDate(4, cliente.getDataRegistro());

            pr.executeUpdate();

            ResultSet rs = pr.getGeneratedKeys();
            if (rs.next()) return rs.getLong(1);
            else throw new RuntimeException("Nenhuma chave gerada ao salvar Cliente.");
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar cliente no Banco durante testes de Pedido DAO: " + e);
        }
    }

    // Cria um Cupom de teste (com Parceiro) e persiste no banco
    private Cupom c() {
        Cupom cupom = new Cupom();
        cupom.setNome("DESCONTO10");
        cupom.setValorDesconto(10);
        cupom.setDescricao("Cupom de 10% de desconto");
        cupom.setValido('V');
        cupom.setParceiro(mapParceiro());

        cupom.setId(salvarCupomNoBanco(cupom));
        return cupom;
    }

    // Persiste o Cupom no banco e retorna ID
    private Long salvarCupomNoBanco(Cupom cupom) {
        String sql = """
            INSERT INTO t_sgp_cupom (
                id_parceiro, vl_desconto, ds_cupom, nm_cupom, st_valido
            ) VALUES (?, ?, ?, ?, ?)
        """;

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement pr = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pr.setLong(1, cupom.getParceiro().getId());
            pr.setInt(2, cupom.getValorDesconto());
            pr.setString(3, cupom.getDescricao());
            pr.setString(4, cupom.getNome());
            pr.setString(5, String.valueOf(cupom.getValido()));

            pr.executeUpdate();

            ResultSet rs = pr.getGeneratedKeys();
            if (rs.next()) return rs.getLong(1);
            else throw new RuntimeException("Nenhuma chave gerada ao salvar Cupom.");
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar cupom no Banco durante testes de Pedido DAO: " + e);
        }
    }

    // Cria um Parceiro de teste e persiste no banco
    private Parceiro mapParceiro() {
        Parceiro parceiro = new Parceiro();
        parceiro.setNome("Cupons");
        parceiro.setEmail("cupons@gmail.com");
        parceiro.setTelefone("11333334444");

        parceiro.setId(salvarParceiroNoBanco(parceiro));
        return parceiro;
    }

    // Persiste o Parceiro no banco e retorna ID
    private Long salvarParceiroNoBanco(Parceiro parceiro) {
        String sql = """
            INSERT INTO t_sgp_parceiro (
                nm_parceiro, ds_email, nr_telefone
            ) VALUES (?, ?, ?)
        """;

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement pr = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pr.setString(1, parceiro.getNome());
            pr.setString(2, parceiro.getEmail());
            pr.setString(3, parceiro.getTelefone());

            pr.executeUpdate();

            ResultSet rs = pr.getGeneratedKeys();
            if (rs.next()) return rs.getLong(1);
            else throw new RuntimeException("Nenhuma chave gerada ao salvar parceiro.");
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar parceiro no Banco durante testes de Pedido DAO: " + e);
        }
    }

    // ------------------------ MÉTODOS DE ITENS E COMBOS ------------------------

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
        produto1.setId(salvarItemNoBanco(produto1));


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
        produto2.setId(salvarItemNoBanco(produto2));

        // Combo
        Combo combo = new Combo();
        combo.setNome("Combo Clássico");
        combo.setDescricao("Combo completo com hambúrguer tradicional e batata frita.");
        combo.setTipoItem(TipoItem.COMBO); 
        combo.setDataCriacao(Date.valueOf("2025-01-10"));
        combo.setStatusAtivo(1);
        combo.setDesconto(10);
        combo.setProdutos(Arrays.asList(produto1, produto2));
        salvarItemNoBanco(combo); // salva combo e atribui ID

        return Arrays.asList(produto1, produto2, combo);
    }

    // Cria uma Categoria de Produto e persiste no banco
    private CategoriaProduto criarCategoria() {
        CategoriaProduto categoria = new CategoriaProduto();
        categoria.setNome("Comidas");
        categoria.setSigla("CM");

        categoria.setId(salvarCategoriaNoBanco(categoria));
        return categoria;
    }

    // Persiste a Categoria no banco e retorna ID
    private Long salvarCategoriaNoBanco(CategoriaProduto categoria) {
        String sql = """
            INSERT INTO t_sgp_categoria_produto (
                nm_categoria, sg_categoria
            ) VALUES (?, ?)
        """;

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement pr = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pr.setString(1, categoria.getNome());
            pr.setString(2, categoria.getSigla());

            pr.executeUpdate();

            ResultSet rs = pr.getGeneratedKeys();
            if (rs.next()) return rs.getLong(1);
            else throw new RuntimeException("Erro ao retornar PK gerada para Categoria");

        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar categoria no banco: " + e);
        }
    }

    // Persiste um Item (Produto ou Combo) no banco e retorna ID
    private Long salvarItemNoBanco(Item item) {
        String sql = """
            INSERT INTO t_sgp_item (
                nm_item, ds_item, tp_item, dt_criacao, st_ativo
            ) VALUES (?, ?, ?, ?, ?) 
        """;

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement pr = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pr.setString(1, item.getNome());
            pr.setString(2, item.getDescricao());
            pr.setString(3, String.valueOf(item.getTipoItem().getValue()));
            pr.setDate(4, item.getDataCriacao());
            pr.setInt(5, item.getStatusAtivo());

            pr.executeUpdate();

            Long id;
            ResultSet rs = pr.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getLong(1);
                item.setId(id);
            } else throw new RuntimeException("Erro ao obter chave PK de item.");

            // Persistir detalhes específicos dependendo do tipo
            if (item instanceof Produto) salvarProdutoNoBanco((Produto) item, conn);
            if (item instanceof Combo) salvarComboNoBanco((Combo) item, conn);

            return id;

        } catch (Exception e) {
            throw new RuntimeException("Falha ao salvar item no banco: " + e);
        }
    }

    // Persiste Produto no banco (relacionamento Item->Produto)
    private void salvarProdutoNoBanco(Produto produto, Connection conn) {
        String sql = """
            INSERT INTO t_sgp_produto (
                id_item, id_categoria, vl_produto
            ) VALUES (?, ?, ?)        
        """;

        try (PreparedStatement pr = conn.prepareStatement(sql)) {
            pr.setLong(1, produto.getId());
            pr.setLong(2, produto.getCategoria().getId());
            pr.setDouble(3, produto.getValor());

            pr.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar Produto no banco: " + e);
        }
    }

    // Persiste Combo no banco (relacionamento Item->Combo) e seus produtos
    private void salvarComboNoBanco(Combo combo, Connection conn) {
        String sql = """
            INSERT INTO t_sgp_combo (
                id_item, vl_desconto
            ) VALUES (?, ?)
        """;

        try (PreparedStatement pr = conn.prepareStatement(sql)) {
            pr.setLong(1, combo.getId());
            pr.setInt(2, combo.getDesconto());
            pr.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar combo no banco: " + e);
        }

        // Persiste os produtos que fazem parte do combo
        salvarItensComboNoBanco(combo, conn);
    }

    // Persiste relação Combo->Produto no banco
    private void salvarItensComboNoBanco(Combo combo, Connection conn) {
        String sql = """
            INSERT INTO t_produto_combo (
                id_item_combo, id_item_produto, nr_quantidade
            ) VALUES (?, ?, ?)
        """;

        try (PreparedStatement pr = conn.prepareStatement(sql)) {
            for (Produto p : combo.getProdutos()) {
                pr.setLong(1, combo.getId());
                pr.setLong(2, p.getId());
                pr.setLong(3, p.getQuantidade());
                pr.executeUpdate();
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar itens do combo no banco: " + e);
        }
    }

}

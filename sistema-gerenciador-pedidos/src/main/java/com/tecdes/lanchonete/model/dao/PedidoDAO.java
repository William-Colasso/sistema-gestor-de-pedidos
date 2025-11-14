package com.tecdes.lanchonete.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tecdes.lanchonete.config.ConnectionFactory;
import com.tecdes.lanchonete.model.entity.*;
import com.tecdes.lanchonete.model.enums.TipoItem;
import com.tecdes.lanchonete.model.enums.TipoProduto;

public class PedidoDAO implements InterfaceDAO<Pedido> {

    @Override
    public void create(Pedido t) {
        String sql = """
            INSERT INTO t_sgp_pedido (
                id_funcionario, id_pagamento, id_cliente, id_cupom, dt_pedido, nm_cliente, st_pedido
            ) VALUES (?, ?, ?, ?, ?, ?, ?);
        """;

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement pr = conn.prepareStatement(sql)){

            fillInsertStatementParameters(pr, t);

            pr.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Erro DAO: Falha ao inserir Pedido: ", e);
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM t_sgp_pedido WHERE id_pedido = ?";

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement pr = conn.prepareStatement(sql)) {
            
            pr.setLong(1, id);

            pr.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Erro DAO: Falha ao deletar Pedido: ", e);
        }
    }

    @Override
    public void update(Pedido t) {
        String sql = """
            UPDATE t_sgp_pedido 
            SET id_funcionario = ?, id_pagamento = ?, id_cliente = ?, id_cupom = ?, dt_pedido = ?, nm_cliente = ?, st_pedido = ? 
            WHERE id_pedido = ?
        """;
        
        try (Connection conn = ConnectionFactory.getConnection();
        PreparedStatement pr = conn.prepareStatement(sql)) {
            
            fillUpdateStatementParameters(pr, t);

            pr.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Erro DAO: Falha ao atualizar Pedido: ", e);
        }
    }

    @Override
    public Pedido getById(Long id) {
        String sql = """
            SELECT 
                p.*, f.id_gerente, f.nm_funcionario, f.dt_nascimento, f.nr_cpf, g.ds_senha, g.nm_login, pag.nm_pagamento, pag.sg_pagamento,   
                c.nm_cliente AS nm_cliente_cadastrado, c.nr_telefone AS tel_cliente, c.nr_cpf AS cpf_cliente, c.dt_registro,   
                cup.id_parceiro, cup.vl_desconto AS desconto_cup, cup.ds_cupom, cup.nm_cupom, cup.st_valido,  
                par.nm_parceiro, par.ds_email AS email_parceiro, par.nr_telefone AS tel_parceiro  
            FROM t_sgp_pedido p  
            INNER JOIN t_sgp_funcionario f ON p.id_funcionario = f.id_funcionario  
            LEFT JOIN t_sgp_gerente g ON f.id_gerente = g.id_funcionario   
            INNER JOIN t_sgp_pagamento pag ON p.id_pagamento = pag.id_pagamento   
            LEFT JOIN t_sgp_cliente c ON p.id_cliente = c.id_cliente   
            LEFT JOIN t_sgp_cupom cup ON p.id_cupom = cup.id_cupom  
            LEFT JOIN t_sgp_parceiro par ON cup.id_parceiro = par.id_parceiro  
            WHERE id_pedido = ?
        """;

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement pr = conn.prepareStatement(sql)){
            
            pr.setLong(1, id);

            ResultSet rs = pr.executeQuery();

            if (rs.next()) {
                return mapPedido(rs);
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Erro DAO: Falha ao buscar Pedido: ", e);
        }
    }


    @Override
    public List<Pedido> getAll() {
        String sql = """
            SELECT 
                p.*, f.id_gerente, f.nm_funcionario, f.dt_nascimento, f.nr_cpf, g.ds_senha, g.nm_login, 
                pag.nm_pagamento, pag.sg_pagamento, 
                c.nm_cliente AS nm_cliente_cadastrado, c.nr_telefone AS tel_cliente, c.nr_cpf AS cpf_cliente, c.dt_registro, 
                cup.id_parceiro, cup.vl_desconto AS desconto_cup, cup.ds_cupom, cup.nm_cupom, cup.st_valido, 
                par.nm_parceiro, par.ds_email AS email_parceiro, par.nr_telefone AS tel_parceiro 
            FROM t_sgp_pedido p 
            INNER JOIN t_sgp_funcionario f ON p.id_funcionario = f.id_funcionario 
            LEFT JOIN t_sgp_gerente g ON f.id_gerente = g.id_funcionario 
            INNER JOIN t_sgp_pagamento pag ON p.id_pagamento = pag.id_pagamento 
            LEFT JOIN t_sgp_cliente c ON p.id_cliente = c.id_cliente 
            LEFT JOIN t_sgp_cupom cup ON p.id_cupom = cup.id_cupom 
            LEFT JOIN t_sgp_parceiro par ON cup.id_parceiro = par.id_parceiro
        """;

        List<Pedido> pedidos = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement pr = conn.prepareStatement(sql)) {

            ResultSet rs = pr.executeQuery();
            
            // Loop que percorre enquanto houver elementos no ResultSet
            while (rs.next()) {
                pedidos.add(mapPedido(rs));
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro DAO: Falha ao buscar Pedido: ", e);
        }
        return pedidos;
    }

    private void fillInsertStatementParameters(PreparedStatement pr, Pedido pedido) throws SQLException {
        pr.setLong(1, pedido.getFuncionario().getId());
        pr.setLong(2, pedido.getPagamento().getId());
        pr.setLong(3, pedido.getCliente().getId());
        pr.setLong(4, pedido.getCupom().getId());
        pr.setDate(5, pedido.getDataPedido());
        pr.setString(6, pedido.getNomeCliente());
        pr.setString(7, String.valueOf(pedido.getStatusPedido())); // Convertendo char para String
    }

    private void fillUpdateStatementParameters(PreparedStatement pr, Pedido pedido) throws SQLException {
        fillInsertStatementParameters(pr, pedido);
        pr.setLong(8, pedido.getId());
    }


    private Pedido mapPedido(ResultSet rs) throws SQLException {
        Pedido pedido = new Pedido();

        pedido.setId(rs.getLong("id_pedido"));
        pedido.setFuncionario(mapFuncionario(rs));
        pedido.setPagamento(mapPagamento(rs));

        // getObject() é usado aqui como mecanismo de verificação de NULL
        // pois tipo int não pode receber null
        if (rs.getObject("id_cliente") != null) {
            pedido.setCliente(mapCliente(rs));
        } else {
            pedido.setNomeCliente(rs.getString("nm_cliente"));
        }

        if (rs.getObject("id_cupom") != null) {
            pedido.setCupom(mapCupom(rs));
        }

        pedido.setItens(getItensByPedido(pedido.getId()));
        pedido.setDataPedido(rs.getDate("dt_pedido"));
        pedido.setStatusPedido(rs.getString("st_pedido").charAt(0));
        return pedido;
    }

    private Funcionario mapFuncionario(ResultSet rs) throws SQLException{
        Funcionario funcionario = new Funcionario();

        funcionario.setId(rs.getLong("id_funcionario"));
        
        if (rs.getObject("id_gerente") != null) {
            Gerente gerente = new Gerente();
            gerente.setLogin(rs.getString("nm_login"));
            gerente.setSenha(rs.getString("ds_senha"));
            funcionario.setGerente(gerente);
        }

        funcionario.setNome(rs.getString("nm_funcionario"));
        funcionario.setDataNascimento(rs.getDate("dt_nascimento"));
        funcionario.setCpf(rs.getString("nr_cpf"));

        return funcionario;
    }

    private Pagamento mapPagamento(ResultSet rs) throws SQLException{
        Pagamento pagamento = new Pagamento();

        pagamento.setId(rs.getLong("id_pagamento"));
        pagamento.setNome(rs.getString("nm_pagamento"));
        pagamento.setSigla(rs.getString("sg_pagamento"));

        return pagamento;
    }

    private Cliente mapCliente(ResultSet rs) throws SQLException {
        Cliente cliente = new Cliente();

        cliente.setId(rs.getLong("id_cliente"));
        cliente.setNome(rs.getString("nm_cliente_cadastrado"));
        cliente.setTelefone(rs.getString("tel_cliente"));
        cliente.setCpf(rs.getString("cpf_cliente"));
        cliente.setDataRegistro(rs.getDate("dt_registro"));

        return cliente;
    }

    private Cupom mapCupom(ResultSet rs) throws SQLException {
        Cupom cupom = new Cupom();

        cupom.setId(rs.getLong("id_cupom"));
        cupom.setParceiro(mapParceiro(rs));
        cupom.setValorDesconto(rs.getInt("desconto_cup"));
        cupom.setDescricao(rs.getString("ds_cupom"));
        cupom.setNome(rs.getString("nm_cupom"));
        cupom.setValido(rs.getBoolean("st_valido"));

        return cupom;
    }

    private Parceiro mapParceiro(ResultSet rs) throws SQLException{
        Parceiro parceiro = new Parceiro();

        parceiro.setId(rs.getLong("id_parceiro"));
        parceiro.setNome(rs.getString("nm_parceiro"));
        parceiro.setEmail(rs.getString("email_parceiro"));
        parceiro.setTelefone(rs.getString("tel_parceiro"));

        return parceiro;
    }


    private List<Item> getItensByPedido(Long id) {
        String sql = """
            SELECT 
                ip.id_item, i.nm_item, i.ds_item, i.tp_item, i.dt_criacao, i.st_ativo,  
                p.vl_produto, p.tp_produto, p.id_categoria, c.vl_desconto, ct.nm_categoria, ct.sg_categoria 
            FROM t_item_pedido ip 
            INNER JOIN t_sgp_item i ON ip.id_item = i.id_item 
            LEFT JOIN t_sgp_produto p ON i.id_item = p.id_item  
            LEFT JOIN t_sgp_combo c ON i.id_item = c.id_item 
            LEFT JOIN t_sgp_categoria_produto ct ON p.id_categoria = ct.id_categoria WHERE ip.id_pedido = ?
        """;

        List<Item> itens = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement pr = conn.prepareStatement(sql)) {
                pr.setLong(1, id);

                ResultSet rs = pr.executeQuery();

                while (rs.next()) {
                    Item item = null;
                    char tipo = rs.getString("tp_item").charAt(0);

                    if (tipo == TipoItem.PRODUTO.getValue()) {
                        item = mapProduto(rs);
                    }

                    if (tipo == TipoItem.COMBO.getValue()) {
                        item = mapCombo(rs);
                    }

                    itens.add(item);
                }
        } catch (Exception e) {
            throw new RuntimeException("Erro DAO: Falha ao obter itens do pedido: ", e);
        }
        return itens;
    }


    private Produto mapProduto(ResultSet rs) throws SQLException {
        Produto produto = new Produto();

        produto.setId(rs.getLong("id_item"));
        produto.setNome(rs.getString("nm_item"));
        produto.setDescricao(rs.getString("ds_item"));
        produto.setTipoItem(TipoItem.PRODUTO);
        produto.setDataCriacao(rs.getDate("dt_criacao"));
        produto.setAtivo(rs.getBoolean("st_ativo"));
        produto.setValor(rs.getDouble("vl_produto"));
        produto.setTipoProduto(TipoProduto.fromValue(rs.getInt("tp_produto")));
        produto.setCategoria(mapCategoriaProduto(rs));

        return produto;
    }

    private CategoriaProduto mapCategoriaProduto(ResultSet rs) throws SQLException{
        CategoriaProduto categoria = new CategoriaProduto();

        categoria.setId(rs.getLong("id_categoria"));
        categoria.setNome(rs.getString("nm_categoria"));
        categoria.setSigla(rs.getString("sg_categoria"));

        return categoria;
    }

    private Combo mapCombo(ResultSet rs) throws SQLException {
        Combo combo = new Combo();

        combo.setId(rs.getLong("id_item"));
        combo.setNome(rs.getString("nm_item"));
        combo.setDescricao(rs.getString("ds_item"));
        combo.setTipoItem(TipoItem.COMBO);
        combo.setDataCriacao(rs.getDate("dt_criacao"));
        combo.setAtivo(rs.getBoolean("st_ativo"));
        combo.setDesconto(rs.getInt("vl_desconto"));

        List<Produto> produtos = getProdutosFromCombo(rs.getLong("id_item"));

        combo.setProdutos(produtos);

        return combo;
    }

    private List<Produto> getProdutosFromCombo (Long id) {

        String sql = """
            SELECT 
                i.*, p.vl_produto, p.tp_produto, p.id_categoria, ct.nm_categoria, ct.sg_categoria   
                FROM t_produto_combo pc INNER JOIN t_sgp_produto p ON pc.id_produto = p.id_item   
                INNER JOIN t_sgp_item i ON p.id_item = i.id_item 
                INNER JOIN t_sgp_categoria_produto ct ON p.id_categoria = ct.id_categoria 
            WHERE pc.id_combo = ?
        """;

        List<Produto> produtos = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
        PreparedStatement pr = conn.prepareStatement(sql)) {

            pr.setLong(1, id);

            ResultSet rs = pr.executeQuery();

            while (rs.next()) {
                produtos.add(mapProduto(rs));
            }
            
        } catch (Exception e) {
            throw new RuntimeException("Erro DAO: Falha ao obter Produtos do Combo: ", e);
        }


        return produtos;
    }

}

package com.tecdes.lanchonete.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.tecdes.lanchonete.config.ConnectionFactory;
import com.tecdes.lanchonete.model.entity.*;
import com.tecdes.lanchonete.model.enums.TipoItem;

public class PedidoDAO implements InterfaceDAO<Pedido> {

    @Override
    public Pedido create(Pedido t) {
        String sql = """
            INSERT INTO t_sgp_pedido (
                id_funcionario, id_pagamento, id_cliente, id_cupom, dt_pedido, nm_cliente, st_pedido
            ) VALUES (?, ?, ?, ?, ?, ?, ?);
        """;

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement pr = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

            fillInsertStatementParameters(pr, t);

            pr.executeUpdate();

            ResultSet rs = pr.getGeneratedKeys();
            if (rs.next()) {
                Long idPedido = rs.getLong(1);
                t.setId(idPedido);
                insertItens(t, conn);
            } else {
                throw new RuntimeException("Falha ao obter chave primária de pedido: ");
            }
            return t;
        } catch (Exception e) {
            throw new RuntimeException("Erro DAO: Falha ao inserir Pedido: " + e);
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            deleteItensPedido(conn, id);
            deletePedido(conn, id);
        } catch (Exception e) {
            throw new RuntimeException("Erro DAO: Falha ao deletar Pedido: " + e);
        }
    }

    @Override
    public void update(Pedido t) {
        
        
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sqlUpdate = """
                UPDATE t_sgp_pedido 
                SET id_funcionario = ?, id_pagamento = ?, id_cliente = ?, id_cupom = ?, dt_pedido = ?, nm_cliente = ?, st_pedido = ? 
                WHERE id_pedido = ?
            """;

            try(PreparedStatement pr = conn.prepareStatement(sqlUpdate)) {
                fillUpdateStatementParameters(pr, t);
                pr.executeUpdate();
            }
            
            String deleteItems = "DELETE FROM t_item_pedido WHERE id_pedido = ?";
            try (PreparedStatement ps = conn.prepareStatement(deleteItems)) {
                ps.setLong(1, t.getId());
                ps.executeUpdate();
                
                insertItens(t, conn);
            }


        } catch (Exception e) {
            throw new RuntimeException("Erro DAO: Falha ao atualizar Pedido: " + e);
        }
    }

    @Override
    public Pedido getById(Long id) {
        String sql = """
            SELECT 
                p.*, f.id_funcionario, f.id_gerente, f.nm_funcionario, f.dt_nascimento, f.nr_cpf,
                pag.nm_pagamento, pag.sg_pagamento, 
                c.nm_cliente AS nm_cliente_cadastrado, c.nr_telefone AS tel_cliente, c.nr_cpf AS cpf_cliente, c.dt_registro, 
                cup.id_parceiro, cup.vl_desconto AS desconto_cup, cup.ds_cupom, cup.nm_cupom, cup.st_valido, 
                par.nm_parceiro, par.ds_email AS email_parceiro, par.nr_telefone AS tel_parceiro 
            FROM t_sgp_pedido p 
            INNER JOIN t_sgp_funcionario f ON p.id_funcionario = f.id_funcionario 
            INNER JOIN t_sgp_forma_pagamento pag ON p.id_pagamento = pag.id_pagamento 
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
                return mapPedido(rs, conn);
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Erro DAO: Falha ao buscar Pedido: " + e);
        }
    }


    @Override
    public List<Pedido> getAll() {
        String sql = """
            SELECT 
                p.*, f.id_funcionario, f.id_gerente, f.nm_funcionario, f.dt_nascimento, f.nr_cpf,
                pag.nm_pagamento, pag.sg_pagamento, 
                c.nm_cliente AS nm_cliente_cadastrado, c.nr_telefone AS tel_cliente, c.nr_cpf AS cpf_cliente, c.dt_registro, 
                cup.id_parceiro, cup.vl_desconto AS desconto_cup, cup.ds_cupom, cup.nm_cupom, cup.st_valido, 
                par.nm_parceiro, par.ds_email AS email_parceiro, par.nr_telefone AS tel_parceiro 
            FROM t_sgp_pedido p 
            INNER JOIN t_sgp_funcionario f ON p.id_funcionario = f.id_funcionario 
            INNER JOIN t_sgp_forma_pagamento pag ON p.id_pagamento = pag.id_pagamento 
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
                pedidos.add(mapPedido(rs, conn));
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro DAO: Falha ao buscar todos os Pedidos: " + e);
        }
        return pedidos;
    }

    private void fillInsertStatementParameters(PreparedStatement pr, Pedido pedido) throws SQLException {
        pr.setLong(1, pedido.getFuncionario().getId());
        pr.setLong(2, pedido.getPagamento().getId());

        if (pedido.getCliente() != null) {
            pr.setLong(3, pedido.getCliente().getId());
        } else {
            pr.setNull(3, Types.BIGINT);
        }

        if (pedido.getCupom() != null) {
            pr.setLong(4, pedido.getCupom().getId());
        } else {
            pr.setNull(4, Types.BIGINT);
        }
        
        pr.setDate(5, pedido.getDataPedido());
        pr.setString(6, pedido.getNomeCliente());
        pr.setString(7, String.valueOf(pedido.getStatusPedido())); // Convertendo char para String
    }

    private void fillUpdateStatementParameters(PreparedStatement pr, Pedido pedido) throws SQLException {
        fillInsertStatementParameters(pr, pedido);
        pr.setLong(8, pedido.getId());
    }

    private void insertItens(Pedido t, Connection conn) {
        List<Item> itens = t.getItens();

        for (Item item : itens) {
            insertItemPedido(item.getId(), t.getId(), item.getQuantidade(), conn);
        }
    }

    private void insertItemPedido(Long item, Long pedido, int qtd, Connection conn) {
        String sql = """
            INSERT INTO t_item_pedido (
                id_item, id_pedido, nr_quantidade
            ) VALUES (?, ?, ?)
        """;

        try (PreparedStatement pr = conn.prepareStatement(sql)) {
            pr.setLong(1, item);
            pr.setLong(2, pedido);
            pr.setLong(3, qtd);

            pr.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao inserir item pedido: " + e);
        }
    }


    private Pedido mapPedido(ResultSet rs, Connection conn) throws SQLException {
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

        pedido.setItens(getItensByPedido(pedido.getId(), conn));
        pedido.setDataPedido(rs.getDate("dt_pedido"));
        pedido.setStatusPedido(rs.getString("st_pedido").charAt(0));
        return pedido;
    }

    private Funcionario mapFuncionario(ResultSet rs) throws SQLException{
        Funcionario funcionario = new Funcionario();

        funcionario.setId(rs.getLong("id_funcionario"));
        
        long idGerente = rs.getLong("id_gerente");
        if (!rs.wasNull()) {  // Verifica se existe gerente
            Gerente chefe = new Gerente();
            chefe.setId(idGerente); 
            funcionario.setGerente(chefe);
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
        cupom.setValido(rs.getString("st_valido").charAt(0));

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


    private List<Item> getItensByPedido(Long id, Connection conn) {
        String sql = """
            SELECT 
                ip.nr_quantidade,
                i.id_item, i.nm_item, i.ds_item, i.tp_item, i.dt_criacao, i.st_ativo,  
                p.vl_produto, p.id_categoria, c.vl_desconto, ct.nm_categoria, ct.sg_categoria 
            FROM t_item_pedido ip 
            INNER JOIN t_sgp_item i ON ip.id_item = i.id_item 
            LEFT JOIN t_sgp_produto p ON i.id_item = p.id_item  
            LEFT JOIN t_sgp_combo c ON i.id_item = c.id_item 
            LEFT JOIN t_sgp_categoria_produto ct ON p.id_categoria = ct.id_categoria WHERE id_pedido = ?
        """;

        List<Item> itens = new ArrayList<>();

        try (PreparedStatement pr = conn.prepareStatement(sql)) {
                pr.setLong(1, id);

                ResultSet rs = pr.executeQuery();

                while (rs.next()) {
                    Item item = null;
                    char tipo = rs.getString("tp_item").charAt(0);

                    if (tipo == TipoItem.PRODUTO.getValue()) {
                        item = mapProduto(rs);
                    }

                    if (tipo == TipoItem.COMBO.getValue()) {
                        item = mapCombo(rs, conn);
                    }

                    itens.add(item);
                }
        } catch (Exception e) {
            throw new RuntimeException("Erro DAO: Falha ao obter itens do pedido: "+ e);
        }
        return itens;
    }


    private Produto mapProduto(ResultSet rs) throws SQLException {
        Produto produto = new Produto();

        mapItemBase(rs, produto);
        produto.setTipoItem(TipoItem.PRODUTO);
        produto.setValor(rs.getDouble("vl_produto"));
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

    private Combo mapCombo(ResultSet rs, Connection conn) throws SQLException {
        Combo combo = new Combo();

        mapItemBase(rs, combo);
        combo.setTipoItem(TipoItem.COMBO);
        combo.setDesconto(rs.getInt("vl_desconto"));

        List<Produto> produtos = getProdutosFromCombo(rs.getLong("id_item"), conn);

        combo.setProdutos(produtos);

        return combo;
    }

    private void mapItemBase(ResultSet rs, Item item) throws SQLException {
        item.setId(rs.getLong("id_item"));
        item.setNome(rs.getString("nm_item"));
        item.setDescricao(rs.getString("ds_item"));
        item.setDataCriacao(rs.getDate("dt_criacao"));
        item.setStatusAtivo(rs.getInt("st_ativo"));
        item.setQuantidade(rs.getInt("nr_quantidade"));
    }


    private List<Produto> getProdutosFromCombo (Long id, Connection conn) {

        String sql = """
            SELECT 
                pc.nr_quantidade,
                i.*, p.vl_produto, p.id_categoria, ct.nm_categoria, ct.sg_categoria   
                FROM t_produto_combo pc INNER JOIN t_sgp_produto p ON pc.id_item_produto = p.id_item   
                INNER JOIN t_sgp_item i ON p.id_item = i.id_item 
                INNER JOIN t_sgp_categoria_produto ct ON p.id_categoria = ct.id_categoria 
            WHERE pc.id_item_combo = ?
        """;

        List<Produto> produtos = new ArrayList<>();

        try (PreparedStatement pr = conn.prepareStatement(sql)) {

            pr.setLong(1, id);

            ResultSet rs = pr.executeQuery();

            while (rs.next()) {
                produtos.add(mapProduto(rs));
            }
            
        } catch (Exception e) {
            throw new RuntimeException("Erro DAO: Falha ao obter Produtos do Combo: " + e);
        }


        return produtos;
    }

    private void deleteItensPedido(Connection conn, Long id){
        String sql = "DELETE from t_item_pedido WHERE id_pedido = ?";
        try (PreparedStatement pr = conn.prepareStatement(sql)) {
            pr.setLong(1, id);
            pr.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar itens do pedido: " + e);
        }
    }

    private void deletePedido(Connection conn, Long id) {
        String sql = "DELETE FROM t_sgp_pedido WHERE id_pedido = ?";
        try (PreparedStatement pr = conn.prepareStatement(sql)) {
            pr.setLong(1, id);
            pr.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar pedido: " + e);
        }
    }
}

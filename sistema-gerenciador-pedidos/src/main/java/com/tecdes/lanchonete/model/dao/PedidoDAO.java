package com.tecdes.lanchonete.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.tecdes.lanchonete.config.ConnectionFactory;
import com.tecdes.lanchonete.model.entity.*;
import com.tecdes.lanchonete.model.enums.TipoItem;
import com.tecdes.lanchonete.model.enums.TipoProduto;

public class PedidoDAO implements InterfaceDAO<Pedido> {


    
    @Override
    public void create(Pedido t) {
        String sql = "INSERT INTO t_sgp_pedido (id_funcionario, id_pagamento, " +
                     "id_cliente, id_cupom, dt_pedido, nm_cliente, st_pedido) " + 
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement pr = conn.prepareStatement(sql)){

            pr.setLong(1, t.getFuncionario().getId());
            pr.setLong(2, t.getPagamento().getId());
            pr.setLong(3, t.getCliente().getId());
            pr.setLong(4, t.getCupom().getId());
            pr.setDate(5, t.getDataPedido());
            pr.setString(6, t.getNomeCliente());
            pr.setString(7, String.valueOf(t.getStatusPedido())); // Convertendo char para String

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
        
        String sql = "UPDATE t_sgp_pedido SET id_funcionario = ?, " + 
                     "id_pagamento = ?, id_cliente = ?, id_cupom = ?, dt_pedido = ?, " + 
                     "nm_cliente = ?, st_pedido = ? WHERE id_pedido = ?";
        
        try (Connection conn = ConnectionFactory.getConnection();
        PreparedStatement pr = conn.prepareStatement(sql)) {
            
            pr.setLong(1, t.getFuncionario().getId());
            pr.setLong(2, t.getPagamento().getId());
            pr.setLong(3, t.getCliente().getId());
            pr.setLong(4, t.getCupom().getId());
            pr.setDate(5, t.getDataPedido());
            pr.setString(6, t.getNomeCliente());
            pr.setString(7, String.valueOf(t.getStatusPedido())); // Convertendo char para String
            pr.setLong(8, t.getId());

            pr.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Erro DAO: Falha ao atualizar Pedido: ", e);
        }
    }




    @Override
    public Pedido getById(Long id) {


        // Verificar nome da coluna de auto relacionamento 

        String sql = "SELECT p.id_pedido, p.id_funcionario, p.id_pagamento, p.id_cliente, " + 
                     "p.id_cupom, p.dt_pedido, p.nm_cliente AS nm_cliente_pedido, p.st_pedido, " + 
                     "f.id_gerente, f.nm_funcionario, f.dt_nascimento, f.nr_cpf, "  + 
                     "g.ds_senha, g.nm_login, " +
                     "pag.nm_pagamento, pag.sg_pagamento, " + 
                     "c.nm_cliente AS nm_cliente_cadastrado, c.nr_telefone AS tel_cliente, c.nr_cpf AS cpf_cliente, c.dt_registro, " + 
                     "cup.id_parceiro, cup.vl_desconto AS desconto_cup, cup.ds_cupom, cup.nm_cupom, cup.st_valido, " +
                     "par.nm_parceiro, par.ds_email AS email_parceiro, par.nr_telefone AS tel_parceiro " +
                     "FROM t_sgp_pedido p " +
                     "INNER JOIN t_sgp_funcionario f ON p.id_funcionario = f.id_funcionario " +
                     "LEFT JOIN t_sgp_gerente g ON f.id_gerente = g.id_funcionario " + 
                     "INNER JOIN t_sgp_pagamento pag ON p.id_pagamento = pag.id_pagamento " + 
                     "LEFT JOIN t_sgp_cliente c ON p.id_cliente = c.id_cliente " + 
                     "LEFT JOIN t_sgp_cupom cup ON p.id_cupom = cup.id_cupom " +
                     "LEFT JOIN t_sgp_parceiro par ON cup.id_parceiro = par.id_parceiro " +
                     "WHERE id_pedido = ?";

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement pr = conn.prepareStatement(sql)){
            
            pr.setLong(1, id);

            ResultSet rs = pr.executeQuery();

            if (rs.next()) {
                Pedido p = new Pedido();

                p.setId(rs.getLong("id_pedido"));

                Funcionario f = new Funcionario();

                f.setId(rs.getLong("id_funcionario"));
                
                if (rs.getObject("id_gerente") != null) {
                    Gerente gerente = new Gerente();
                    gerente.setLogin(rs.getString("nm_login"));
                    gerente.setSenha(rs.getString("ds_senha"));
                    f.setGerente(gerente);
                }

                f.setNome(rs.getString("nm_funcionario"));
                f.setDataNascimento(rs.getDate("dt_nascimento"));
                f.setCpf(rs.getString("nr_cpf"));

                p.setFuncionario(f);

                Pagamento pag = new Pagamento();

                pag.setId(rs.getLong("id_pagamento"));
                pag.setNome(rs.getString("nm_pagamento"));
                pag.setSigla(rs.getString("sg_pagamento"));

                p.setPagamento(pag);

                // getObject() é usado aqui como mecanismo de verificação de NULL
                // pois tipo int não pode receber null
                if (rs.getObject("id_cliente") != null) {
                    Cliente c = new Cliente();

                    c.setId(rs.getLong("id_cliente"));
                    c.setNome(rs.getString("nm_cliente_cadastrado"));
                    c.setTelefone(rs.getString("tel_cliente"));
                    c.setCpf(rs.getString("cpf_cliente"));
                    c.setDataRegistro(rs.getDate("dt_registro"));

                    p.setCliente(c);
                } else {
                    p.setNomeCliente(rs.getString("nm_cliente_pedido"));
                }

                if (rs.getObject("id_cupom") != null) {
                    Cupom cup = new Cupom();

                    cup.setId(rs.getLong("id_cupom"));
                    
                    Parceiro par = new Parceiro();

                    par.setId(rs.getLong("id_parceiro"));
                    par.setNome(rs.getString("nm_parceiro"));
                    par.setEmail(rs.getString("email_parceiro"));
                    par.setTelefone(rs.getString("tel_parceiro"));

                    cup.setParceiro(par);
                    cup.setValorDesconto(rs.getInt("desconto_cup"));
                    cup.setDescricao(rs.getString("ds_cupom"));
                    cup.setNome(rs.getString("nm_cupom"));
                    cup.setValido(rs.getBoolean("st_valido"));

                    p.setCupom(cup);
                }

                p.setItens(getItensByPedido(p.getId()));

                p.setDataPedido(rs.getDate("dt_pedido"));
                p.setStatusPedido(rs.getString("st_pedido").charAt(0));

                return p;
            }

            return null;

        } catch (Exception e) {
            throw new RuntimeException("Erro DAO: Falha ao buscar Pedido: ", e);
        }
    }


    @Override
    public List<Pedido> getAll() {

        // Verificar coluna de auto relacionamento de funcionário

        String sql = "SELECT p.id_pedido, p.id_funcionario, p.id_pagamento, p.id_cliente, " + 
                     "p.id_cupom, p.dt_pedido, p.nm_cliente AS nm_cliente_pedido, p.st_pedido, " + 
                     "f.id_gerente, f.nm_funcionario, f.dt_nascimento, f.nr_cpf, "  + 
                     "g.ds_senha, g.nm_login, " +
                     "pag.nm_pagamento, pag.sg_pagamento, " + 
                     "c.nm_cliente AS nm_cliente_cadastrado, c.nr_telefone AS tel_cliente, c.nr_cpf AS cpf_cliente, c.dt_registro, " + 
                     "cup.id_parceiro, cup.vl_desconto AS desconto_cup, cup.ds_cupom, cup.nm_cupom, cup.st_valido, " +
                     "par.nm_parceiro, par.ds_email AS email_parceiro, par.nr_telefone AS tel_parceiro " +
                     "FROM t_sgp_pedido p " +
                     "INNER JOIN t_sgp_funcionario f ON p.id_funcionario = f.id_funcionario " +
                     "LEFT JOIN t_sgp_gerente g ON f.id_gerente = g.id_funcionario " + 
                     "INNER JOIN t_sgp_pagamento pag ON p.id_pagamento = pag.id_pagamento " + 
                     "LEFT JOIN t_sgp_cliente c ON p.id_cliente = c.id_cliente " + 
                     "LEFT JOIN t_sgp_cupom cup ON p.id_cupom = cup.id_cupom " +
                     "LEFT JOIN t_sgp_parceiro par ON cup.id_parceiro = par.id_parceiro";

        List<Pedido> pedidos = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement pr = conn.prepareStatement(sql)) {

            ResultSet rs = pr.executeQuery();
            
            // Loop que percorre enquanto houver elementos no ResultSet
            while (rs.next()) {
                Pedido p = new Pedido();

                p.setId(rs.getLong("id_pedido"));

                Funcionario f = new Funcionario();

                f.setId(rs.getLong("id_funcionario"));
                
                if (rs.getObject("id_gerente") != null) {
                    Gerente gerente = new Gerente();
                    gerente.setLogin(rs.getString("nm_login"));
                    gerente.setSenha(rs.getString("ds_senha"));
                    f.setGerente(gerente);
                }

                f.setNome(rs.getString("nm_funcionario"));
                f.setDataNascimento(rs.getDate("dt_nascimento"));
                f.setCpf(rs.getString("nr_cpf"));

                p.setFuncionario(f);

                Pagamento pag = new Pagamento();

                pag.setId(rs.getLong("id_pagamento"));
                pag.setNome(rs.getString("nm_pagamento"));
                pag.setSigla(rs.getString("sg_pagamento"));

                p.setPagamento(pag);

                // getObject() é usado aqui como mecanismo de verificação de NULL
                // pois tipo int não pode receber null
                if (rs.getObject("id_cliente") != null) {
                    Cliente c = new Cliente();

                    c.setId(rs.getLong("id_cliente"));
                    c.setNome(rs.getString("nm_cliente_cadastrado"));
                    c.setTelefone(rs.getString("tel_cliente"));
                    c.setCpf(rs.getString("cpf_cliente"));
                    c.setDataRegistro(rs.getDate("dt_registro"));

                    p.setCliente(c);
                } else {
                    p.setNomeCliente(rs.getString("nm_cliente_pedido"));
                }

                if (rs.getObject("id_cupom") != null) {
                    Cupom cup = new Cupom();

                    cup.setId(rs.getLong("id_cupom"));
                    
                    Parceiro par = new Parceiro();

                    par.setId(rs.getLong("id_parceiro"));
                    par.setNome(rs.getString("nm_parceiro"));
                    par.setEmail(rs.getString("email_parceiro"));
                    par.setTelefone(rs.getString("tel_parceiro"));

                    cup.setParceiro(par);
                    cup.setValorDesconto(rs.getInt("desconto_cup"));
                    cup.setDescricao(rs.getString("ds_cupom"));
                    cup.setNome(rs.getString("nm_cupom"));
                    cup.setValido(rs.getBoolean("st_valido"));
                }


                p.setItens(getItensByPedido(p.getId()));


                p.setDataPedido(rs.getDate("dt_pedido"));
                p.setStatusPedido(rs.getString("st_pedido").charAt(0));


                pedidos.add(p);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro DAO: Falha ao buscar Pedido: ", e);
        }

        return pedidos;
    }



    private List<Item> getItensByPedido(Long id) {

        String sql = "SELECT ip.id_item, i.nm_item, i.ds_item, i.tp_item, i.dt_criacao, i.st_ativo, " +
                     "p.vl_produto, p.tp_produto, p.id_categoria, c.vl_desconto, ct.nm_categoria, " + 
                     "ct.sg_categoria FROM t_item_pedido ip INNER JOIN t_sgp_item i " + 
                     "ON ip.id_item = i.id_item LEFT JOIN t_sgp_produto p ON i.id_item = p.id_item " +
                     "LEFT JOIN t_sgp_combo c ON i.id_item = c.id_item LEFT JOIN t_sgp_categoria_produto ct " + 
                     "ON p.id_categoria = ct.id_categoria WHERE ip.id_pedido = ?";


        List<Item> itens = new ArrayList<>();


        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement pr = conn.prepareStatement(sql)) {

                pr.setLong(1, id);

                ResultSet rs = pr.executeQuery();

                

                while (rs.next()) {
                    Item item = null;
                    char tipo = rs.getString("tp_item").charAt(0);

                    if (tipo == TipoItem.PRODUTO.getValue()) {
                        Produto p = new Produto();

                        p.setId(rs.getLong("id_item"));
                        p.setNome(rs.getString("nm_item"));
                        p.setDescricao(rs.getString("ds_item"));
                        p.setTipoItem(TipoItem.PRODUTO);
                        p.setDataCriacao(rs.getDate("dt_criacao"));
                        p.setAtivo(rs.getBoolean("st_ativo"));
                        p.setValor(rs.getDouble("vl_produto"));
                        p.setTipoProduto(TipoProduto.fromValue(rs.getInt("tp_produto")));

                        CategoriaProduto ct = new CategoriaProduto();

                        ct.setId(rs.getLong("id_categoria"));
                        ct.setNome(rs.getString("nm_categoria"));
                        ct.setSigla(rs.getString("sg_categoria"));

                        p.setCategoria(ct);

                        item = p;
                    }


                    if (tipo == TipoItem.COMBO.getValue()) {
                        Combo c = new Combo();

                        c.setId(rs.getLong("id_item"));
                        c.setNome(rs.getString("nm_item"));
                        c.setDescricao(rs.getString("ds_item"));
                        c.setTipoItem(TipoItem.COMBO);
                        c.setDataCriacao(rs.getDate("dt_criacao"));
                        c.setAtivo(rs.getBoolean("st_ativo"));
                        c.setDesconto(rs.getInt("vl_desconto"));

                        List<Produto> produtos = getProdutosFromCombo(rs.getLong("id_item"));

                        c.setProdutos(produtos);

                        item = c;
                    }

                    
                    itens.add(item);

                }


            
        } catch (Exception e) {
            throw new RuntimeException("Erro DAO: Falha ao obter itens do pedido: ", e);
        }


        return itens;
    }


    private List<Produto> getProdutosFromCombo (Long id) {

        String sql = "SELECT pc.id_produto, i.nm_item, i.ds_item, i.tp_item, i.dt_criacao, i.st_ativo, " + 
                     "p.vl_produto, p.tp_produto, p.id_categoria, ct.nm_categoria, ct.sg_categoria " + 
                     "FROM t_produto_combo pc INNER JOIN t_sgp_produto p ON pc.id_produto = p.id_item " + 
                     "INNER JOIN t_sgp_item i ON p.id_item = i.id_item INNER JOIN t_sgp_categoria_produto ct " + 
                     "ON p.id_categoria = ct.id_categoria WHERE pc.id_combo = ?";

        List<Produto> produtos = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
        PreparedStatement pr = conn.prepareStatement(sql)) {

            pr.setLong(1, id);

            ResultSet rs = pr.executeQuery();

            while (rs.next()) {
                Produto p = new Produto();

                p.setId(rs.getLong("id_produto"));
                p.setNome(rs.getString("nm_item"));
                p.setDescricao(rs.getString("ds_item"));
                p.setTipoItem(TipoItem.PRODUTO);
                p.setDataCriacao(rs.getDate("dt_criacao"));
                p.setAtivo(rs.getBoolean("st_ativo"));
                p.setValor(rs.getDouble("vl_produto"));
                p.setTipoProduto(TipoProduto.fromValue(rs.getInt("tp_produto")));

                CategoriaProduto ct = new CategoriaProduto();

                ct.setId(rs.getLong("id_categoria"));
                ct.setNome(rs.getString("nm_categoria"));
                ct.setSigla(rs.getString("sg_categoria"));

                p.setCategoria(ct);

                produtos.add(p);
            }
            
        } catch (Exception e) {
            throw new RuntimeException("Erro DAO: Falha ao obter Produtos do Combo: ", e);
        }


        return produtos;
    }



    
}

package com.tecdes.lanchonete.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.tecdes.lanchonete.config.ConnectionFactory;
import com.tecdes.lanchonete.generalinterfaces.crud.Crud;
import com.tecdes.lanchonete.model.entity.CategoriaProduto;
import com.tecdes.lanchonete.model.entity.Combo;
import com.tecdes.lanchonete.model.entity.Produto;
import com.tecdes.lanchonete.model.enums.TipoItem;

public class ProdutoDAO implements Crud<Produto> {

    @Override
    public Produto create(Produto t) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            insertProduto(t, conn);
            if (t.getCombos() == null || t.getCombos().isEmpty()) {
                insertCombos(t, conn);
            }   
            
            return t;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar produto: " + e);
        }
        
    }

    @Override
    public void delete(Long id) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            deleteProdutoCombo(conn, id);
            deleteProduto(conn, id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar Produto: " + e);
        }
    }

    @Override
    public void update(Produto t) {
        String sql = """
            UPDATE t_sgp_produto 
            SET id_categoria = ?, vl_produto = ?
            WHERE id_item = ?
        """;

        try(Connection conn =  ConnectionFactory.getConnection();
        PreparedStatement pr = conn.prepareStatement(sql)){
            fillUpdateStatementParameters(pr, t);
            pr.executeUpdate();
        }    catch(SQLException e){
            throw new RuntimeException("Erro ao atualizar Produto: " + e);
        }    
    }

    @Override
    public Produto getById(Long id) {
        String sql = """
            SELECT
                p.*, cp.*
            FROM t_sgp_produto p
            INNER JOIN t_sgp_categoria_produto cp ON p.id_categoria = cp.id_categoria
            WHERE id_item = ?
        """;

        try (Connection conn = ConnectionFactory.getConnection();
        PreparedStatement pr = conn.prepareStatement(sql)) {
            pr.setLong(1, id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                return mapProduto(rs, conn);
            } else {
                throw new RuntimeException("Erro na query para obter produto por ID");
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao obter Produto: " + e);
        }
    }

    @Override
    public List<Produto> getAll() {
        String sql = """
            SELECT
                p.*, cp.*
            FROM t_sgp_produto p
            INNER JOIN t_sgp_categoria_produto cp ON p.id_categoria = cp.id_categoria
        """;

        try (Connection conn = ConnectionFactory.getConnection();
        PreparedStatement pr = conn.prepareStatement(sql)) {
            ResultSet rs = pr.executeQuery();
            List<Produto> produtos = new ArrayList<>();
            while (rs.next()) {
                produtos.add(mapProduto(rs, conn));
            }
            return produtos;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao obter Produtos: " + e);
        }
    }

    public List<Produto> getByCategoriaProduto(CategoriaProduto categoriaProduto){
        String sql = """
            SELECT * FROM t_sgp_produto
            where id_categoria = ?
        """;

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement pr = conn.prepareStatement(sql)) {
            pr.setLong(1, categoriaProduto.getId());
            ResultSet rs = pr.executeQuery();
            List<Produto> produtos = new ArrayList<>();
            while (rs.next()) {
                produtos.add(mapProduto(rs, conn));
            }
            return produtos;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao obter Produtos: " + e);
        }
    }

    private void deleteProduto(Connection conn, Long id) {
        String sql = """
            DELETE FROM t_sgp_produto WHERE id_item = ?
        """;

        try(PreparedStatement pr = conn.prepareStatement(sql)) {
            pr.setLong(1, id);
            pr.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao deletar produto: " + e);
        }
    }

    private void deleteProdutoCombo(Connection conn, Long id) {
        String sql = "DELETE FROM t_produto_combo WHERE id_item_produto = ?";
        try (PreparedStatement pr = conn.prepareStatement(sql)) {
            pr.setLong(1, id);
            pr.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao deletar produto combo: " + e);
        }
    }

    private void insertProduto(Produto produto, Connection conn) {
        String sql = """
            INSERT INTO t_sgp_produto (
                id_item, id_categoria, vl_produto
            ) VALUES (?, ?, ?)
        """;

        try(PreparedStatement pr = conn.prepareStatement(sql)){
            fillInsertStatementParameters(pr, produto);
            pr.executeUpdate();
            System.out.println("Produto inserido!");
        }    catch(SQLException e){
            throw new RuntimeException("Erro ao inserir Produto: " + e);
        }     
    }

    private void insertCombos(Produto produto, Connection conn) {
        String sql = """
            INSERT INTO t_produto_combo (
                id_item_produto, id_item_combo, nr_quantidade
            ) VALUES (?, ?, ?)
        """;

        try (PreparedStatement pr = conn.prepareStatement(sql)) {
            for (Combo combo : produto.getCombos()) {
                pr.setLong(1, produto.getId());
                pr.setLong(2, combo.getId());
                pr.setLong(3, produto.getQuantidade());

                pr.executeUpdate();
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao inserir Produto: " + e);
        }
    }

    private void fillInsertStatementParameters(PreparedStatement pr, Produto produto) throws SQLException{
        pr.setLong(1, produto.getId());
        pr.setLong(2, produto.getCategoria().getId());
        pr.setDouble(3, produto.getValor());
    }

    private void fillUpdateStatementParameters(PreparedStatement pr, Produto produto) throws SQLException {
        pr.setLong(1, produto.getCategoria().getId());
        pr.setDouble(2, produto.getValor());
        pr.setLong(3, produto.getId());
    }

    private Produto mapProduto(ResultSet rs, Connection conn) throws SQLException{
        Produto produto = new Produto();

        produto.setId(rs.getLong("id_item"));
        produto.setCategoria(mapCategoriaProduto(rs));
        produto.setValor(rs.getDouble("vl_produto"));
        produto.setCombos(getCombosFromProduto(produto.getId(), conn));

        return produto;
    }

    private CategoriaProduto mapCategoriaProduto(ResultSet rs) throws SQLException{
        CategoriaProduto categoriaProduto = new CategoriaProduto();

        categoriaProduto.setId(rs.getLong("id_categoria"));
        categoriaProduto.setNome(rs.getString("nm_categoria"));
        categoriaProduto.setSigla(rs.getString("sg_categoria"));

        return categoriaProduto;
    }

    private List<Combo> getCombosFromProduto(Long idProduto, Connection conn) {
        String sql = """
            SELECT 
                c.id_item, i.nm_item, i.ds_item, i.dt_criacao, i.st_ativo,
                c.vl_desconto
            FROM t_produto_combo pc
            INNER JOIN t_sgp_combo c ON pc.id_item_combo = c.id_item
            INNER JOIN t_sgp_item i ON c.id_item = i.id_item
            WHERE pc.id_item_produto = ?
        """;

        List<Combo> combos = new ArrayList<>();

        try (PreparedStatement pr = conn.prepareStatement(sql)) {
            pr.setLong(1, idProduto);

            ResultSet rs = pr.executeQuery();

            while (rs.next()) {
                combos.add(mapCombo(rs, conn));
            }

        } catch (Exception e) {
            throw new RuntimeException("Erro DAO: Falha ao mapear Combos do Produto: " + e);
        }

        return combos;
    }

    // Método para mapear Combo completo
    private Combo mapCombo(ResultSet rs, Connection conn) throws SQLException {
        Combo combo = new Combo();

        combo.setId(rs.getLong("id_item"));
        combo.setTipoItem(TipoItem.COMBO);
        combo.setDesconto(rs.getInt("vl_desconto"));

        // Mapear informações básicas do item
        combo.setNome(rs.getString("nm_item"));
        combo.setDescricao(rs.getString("ds_item"));
        combo.setDataCriacao(rs.getDate("dt_criacao"));
        combo.setStatusAtivo(rs.getInt("st_ativo"));

        return combo;
    }
}

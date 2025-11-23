package com.tecdes.lanchonete.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.tecdes.lanchonete.config.ConnectionFactory;
import com.tecdes.lanchonete.model.entity.CategoriaProduto;
import com.tecdes.lanchonete.model.entity.Combo;
import com.tecdes.lanchonete.model.entity.Item;
import com.tecdes.lanchonete.model.entity.Produto;
import com.tecdes.lanchonete.model.enums.TipoItem;

public class ComboDAO implements InterfaceDAO<Combo> {

    @Override
    public Combo create(Combo t) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            insertItem(conn, t);
            insertCombo(conn, t);
            insertProdutos(conn, t);
            return t;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar Combo: " + e);
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            removerProdutosCombo(conn, id);
            removerCombo(conn, id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar Combo: " + e);
        }
    }

    @Override
    public void update(Combo t) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            updateItem(t, conn);
            updateCombo(t, conn);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar Combo: " + e);
        }
    }

    @Override
    public Combo getById(Long id) {
        String sql = """
            SELECT i.*, c.vl_desconto 
            FROM t_sgp_combo c
            INNER JOIN t_sgp_item i ON i.id_item = c.ITEM_id_item
            WHERE c.ITEM_id_item = ?
        """;
        try (Connection conn = ConnectionFactory.getConnection();
        PreparedStatement pr = conn.prepareStatement(sql)) {
            pr.setLong(1, id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                Combo combo = mapCombo(rs, conn);
                return combo;
            }

            return null;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao obter Combo");
        }
    }

    @Override
    public List<Combo> getAll() {
        return null;
    }

    private void fillInsertItemParameters(PreparedStatement pr, Item item) throws SQLException{
        pr.setString(1, item.getNome());
        pr.setString(2, item.getDescricao());
        pr.setString(3, String.valueOf(item.getTipoItem().getValue()));
        pr.setDate(4, item.getDataCriacao());
        pr.setInt(5, item.getStatusAtivo());
    }

    private void fillUpdateItemParameters(PreparedStatement pr, Item item) throws SQLException{
        fillInsertItemParameters(pr, item);
        pr.setLong(6, item.getId());
    }

    private void insertItem(Connection conn, Item item) {
        String sqlItem = """
            INSERT INTO t_sgp_item (
                nm_item, ds_item, tp_item, dt_criacao, st_ativo
            ) VALUES (?, ?, ?, ?, ?)
        """;

        try (PreparedStatement pr = conn.prepareStatement(sqlItem, Statement.RETURN_GENERATED_KEYS)) {
            fillInsertItemParameters(pr, item);
            pr.executeUpdate();
            ResultSet rs = pr.getGeneratedKeys();
            if (rs.next()) {
                item.setId(rs.getLong(1));
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao inserir item Combo no banco: " + e);
        }
    }

    private void insertCombo(Connection conn, Combo combo) {
        String sqlCombo = """
            INSERT INTO t_sgp_combo (
                ITEM_id_item, vl_desconto
            ) VALUES (?, ?)   
        """;

        try (PreparedStatement pr = conn.prepareStatement(sqlCombo)) {
            pr.setLong(1, combo.getId());
            pr.setInt(2, combo.getDesconto());

            pr.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao inserir combo no banco: " + e);
        }
    }

    private void insertProdutos(Connection conn, Combo combo) {
        String sql = """
            INSERT INTO t_produto_combo (
                PRODUTO_id_item, COMBO_ITEM_id_item
            ) VALUES (?, ?)
        """;

        try (PreparedStatement pr = conn.prepareStatement(sql)) {
            for (Produto p : combo.getProdutos()) {
                pr.setLong(1, p.getId());
                pr.setLong(2, combo.getId());

                pr.executeUpdate();
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    private void removerProdutosCombo(Connection conn, Long id) {
        String sql = "DELETE FROM t_produto_combo WHERE COMBO_ITEM_id_item = ?";

        try (PreparedStatement pr = conn.prepareStatement(sql)) {
            pr.setLong(1, id);

            pr.executeUpdate();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    private void removerCombo(Connection conn, Long id) {
        String sql = "DELETE FROM t_sgp_combo WHERE ITEM_id_item = ?";

        try (PreparedStatement pr = conn.prepareStatement(sql)) {
            pr.setLong(1, id);

            pr.executeUpdate();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    private void updateItem(Item item, Connection conn) {
        String sql = """
            UPDATE t_sgp_item
            SET nm_item = ?, ds_item = ?, tp_item = ?, dt_criacao = ?, st_ativo = ?
            WHERE id_item = ?
        """;

        try (PreparedStatement pr = conn.prepareStatement(sql)) {
            fillUpdateItemParameters(pr, item);
            pr.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar item combo: " + e);
        }
    }

    private void updateCombo(Combo combo, Connection conn) {
        String sql = """
            UPDATE t_sgp_combo
            SET vl_desconto = ?
            WHERE ITEM_id_item = ?        
        """;

        try (PreparedStatement pr = conn.prepareStatement(sql)) {
            pr.setInt(1, combo.getDesconto());
            pr.setLong(2, combo.getId());
            pr.executeUpdate();
        } catch (Exception e) {
            // TODO: handle exception
        }
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
    }

    private List<Produto> getProdutosFromCombo (Long id, Connection conn) {

        String sql = """
            SELECT 
                i.*, p.vl_produto, p.CATEGORIA_PRODUTO_id_categoria, ct.nm_categoria, ct.sg_categoria   
                FROM t_produto_combo pc INNER JOIN t_sgp_produto p ON pc.PRODUTO_id_item = p.ITEM_id_item   
                INNER JOIN t_sgp_item i ON p.ITEM_id_item = i.id_item 
                INNER JOIN t_sgp_categoria_produto ct ON p.CATEGORIA_PRODUTO_id_categoria = ct.id_categoria 
            WHERE pc.COMBO_ITEM_id_item = ?
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

        categoria.setId(rs.getLong("CATEGORIA_PRODUTO_id_categoria"));
        categoria.setNome(rs.getString("nm_categoria"));
        categoria.setSigla(rs.getString("sg_categoria"));

        return categoria;
    }

}

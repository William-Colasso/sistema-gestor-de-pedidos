package com.tecdes.lanchonete.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.tecdes.lanchonete.config.ConnectionFactory;
import com.tecdes.lanchonete.model.entity.Combo;
import com.tecdes.lanchonete.model.entity.Item;
import com.tecdes.lanchonete.model.entity.Produto;
import com.tecdes.lanchonete.model.enums.TipoItem;

public class ItemDAO implements InterfaceDAO<Item> {

    @Override
    public Item create(Item t) {
        String sqlItem = """
            INSERT INTO t_sgp_item (
                nm_item, ds_item, tp_item, dt_criacao, st_ativo
            ) VALUES (?, ?, ?, ?, ?)
        """;
        
        try (Connection conn = ConnectionFactory.getConnection()) {
            PreparedStatement pr = conn.prepareStatement(sqlItem, Statement.RETURN_GENERATED_KEYS);
            pr.setString(1, t.getNome());
            pr.setString(2, t.getDescricao());
            pr.setString(3, String.valueOf(t.getTipoItem().getValue()));
            pr.setDate(4, t.getDataCriacao());
            pr.setInt(5, t.getStatusAtivo());
            pr.executeUpdate();
            ResultSet rs = pr.getGeneratedKeys();
            if (rs.next()) {
                t.setId(rs.getLong(1));
            }
            return t;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao inserir item Combo no banco: " + e);
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM t_sgp_item WHERE id_item = ?";

        try (Connection conn = ConnectionFactory.getConnection()) {
            PreparedStatement pr = conn.prepareStatement(sql);
            pr.setLong(1, id);

            pr.executeUpdate();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    @Override
    public void update(Item t) {
        String sql = """
                    UPDATE t_sgp_item
                    SET nm_item = ?, ds_item = ?, tp_item = ?, dt_criacao = ?, st_ativo = ?
                    WHERE id_item = ?
                """;

        try (Connection conn = ConnectionFactory.getConnection()) {
            PreparedStatement pr = conn.prepareStatement(sql);
            pr.setString(1, t.getNome());
            pr.setString(2, t.getDescricao());
            pr.setString(3, String.valueOf(t.getTipoItem().getValue()));
            pr.setDate(4, t.getDataCriacao());
            pr.setInt(5, t.getStatusAtivo());
            pr.setLong(6, t.getId());
            pr.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar item combo: " + e);
        }
    }

    @Override
    public Item getById(Long id) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql;
            PreparedStatement pr;

            sql = "select * t_sgp_item where id_item = ?";
            pr = conn.prepareStatement(sql);
            pr.setLong(1, id);

            ResultSet rs = pr.executeQuery();
            Item item = (Item) instanciateItem(rs);

            item.setId(rs.getLong("id_item"));
            item.setNome(rs.getString("nm_item"));
            item.setDescricao(rs.getString("ds_item"));
            item.setTipoItem(TipoItem.valueOf(rs.getString("tp_item")));
            item.setDataCriacao(rs.getDate("dt_criacao"));
            item.setStatusAtivo(rs.getInt("st_ativo"));

            return item;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public List<Item> getAll() {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql;
            PreparedStatement pr;

            sql = "select * t_sgp_item";
            pr = conn.prepareStatement(sql);

            ResultSet rs = pr.executeQuery();
            List<Item> listaItem = new ArrayList<>();
            while (rs.next()) {
                Item item = (Item) instanciateItem(rs);

                item.setId(rs.getLong("id_item"));
                item.setNome(rs.getString("nm_item"));
                item.setDescricao(rs.getString("ds_item"));
                item.setTipoItem(TipoItem.valueOf(rs.getString("tp_item")));
                item.setDataCriacao(rs.getDate("dt_criacao"));
                item.setStatusAtivo(rs.getInt("st_ativo"));
                listaItem.add(item);
            }

            return listaItem;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    private Object instanciateItem(ResultSet rs) {
        try {
            return TipoItem.valueOf(rs.getString("tp_item")).getValue() == 'P' ? new Produto() : new Combo();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Vosh deui ruim");
        }
    }

}

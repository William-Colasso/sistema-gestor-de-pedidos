package com.tecdes.lanchonete.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.tecdes.lanchonete.config.ConnectionFactory;
import com.tecdes.lanchonete.model.entity.Produto;

public class ProdutoDAO implements InterfaceDAO<Produto> {

    @Override
    public void create(Produto t) {
        try(Connection conn =  ConnectionFactory.getConnection()){
            String sql = "INSERT INTO T_SGP_PRODUTO nm_produto,vl_preco,tp_produto VALUES  (?,?,?)";
           
            PreparedStatement pr = conn.prepareStatement(sql);

            pr.setString(1, t.getNome());
            pr.setDouble(2, t.getPreco());
            pr.setInt(3, t.getTipoProduto().getValue());

            pr.executeUpdate();

            System.out.println("Produto inserido!");
        }    catch(SQLException e){

        }     
        
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void update(Produto t) {

    }

    @Override
    public Produto getById(Long id) {
        
        return new Produto();
    }


    public void teste(String... a){

    }

}

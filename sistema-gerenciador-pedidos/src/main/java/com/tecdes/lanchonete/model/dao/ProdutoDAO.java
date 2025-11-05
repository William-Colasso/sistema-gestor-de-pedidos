package com.tecdes.lanchonete.model.dao;

import java.sql.Connection;

import com.tecdes.lanchonete.config.ConnectionFactory;
import com.tecdes.lanchonete.model.entity.Produto;

public class ProdutoDAO implements InterfaceDAO<Produto>{

    @Override
    public void create(Produto t) {
        
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

   
    
}

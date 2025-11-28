package com.tecdes.lanchonete.controller;

import java.util.ArrayList;
import java.util.List;

import com.tecdes.lanchonete.generalinterfaces.crud.Crud;
import com.tecdes.lanchonete.model.entity.CategoriaProduto;

public class CategoriaProdutoController implements Crud<CategoriaProduto> {

    @Override
    public CategoriaProduto create(CategoriaProduto t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    @Override
    public CategoriaProduto getById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getById'");
    }

    @Override
    public List<CategoriaProduto> getAll() {
       return new ArrayList<CategoriaProduto>();
    }

    @Override
    public void update(CategoriaProduto t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

}

package com.tecdes.lanchonete.service;

import java.util.List;

import com.tecdes.lanchonete.generalinterfaces.crud.Crud;
import com.tecdes.lanchonete.model.entity.CategoriaProduto;
import com.tecdes.lanchonete.repository.implementation.ICategoriaProduto;

public class CategoriaProdutoService implements Crud<CategoriaProduto> {

    private final ICategoriaProduto iCategoriaProduto;

    public CategoriaProdutoService(ICategoriaProduto iCategoriaProduto){
        this.iCategoriaProduto = iCategoriaProduto;
    }

    @Override
    public CategoriaProduto create(CategoriaProduto t) {
        if(t.getNome() == null){

        }
        if(t.getSigla() == null){

        }
        return iCategoriaProduto.create(t);
    }

    @Override
    public CategoriaProduto getById(Long id) {
        return iCategoriaProduto.getById(id);
    }

    @Override
    public List<CategoriaProduto> getAll() {
        return iCategoriaProduto.getAll();
    }

    @Override
    public void update(CategoriaProduto t) {
        if(t.getId() == null){

        }
        iCategoriaProduto.update(t);
    }

    @Override
    public void delete(Long id) {
        if(id == null){

        }
        iCategoriaProduto.delete(id);
    }
    
}

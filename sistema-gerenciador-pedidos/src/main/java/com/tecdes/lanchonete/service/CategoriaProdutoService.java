package com.tecdes.lanchonete.service;

import java.util.List;

import com.tecdes.lanchonete.generalinterfaces.crud.Crud;
import com.tecdes.lanchonete.model.entity.CategoriaProduto;
import com.tecdes.lanchonete.repository.implementation.ICategoriaProdutoRepository;

public class CategoriaProdutoService implements Crud<CategoriaProduto> {

    private final ICategoriaProdutoRepository iCategoriaProduto;

    public CategoriaProdutoService(ICategoriaProdutoRepository iCategoriaProduto){
        this.iCategoriaProduto = iCategoriaProduto;
    }

    @Override
    public CategoriaProduto create(CategoriaProduto t) {
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
        iCategoriaProduto.update(t);
    }

    @Override
    public void delete(Long id) {
        iCategoriaProduto.delete(id);
    }
    
}

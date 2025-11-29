package com.tecdes.lanchonete.controller;

import java.util.List;

import com.tecdes.lanchonete.generalinterfaces.crud.Crud;
import com.tecdes.lanchonete.model.entity.CategoriaProduto;
import com.tecdes.lanchonete.service.CategoriaProdutoService;

public class CategoriaProdutoController implements Crud<CategoriaProduto> {

    private CategoriaProdutoService categoriaProdutoService;

    

    public CategoriaProdutoController(CategoriaProdutoService categoriaProdutoService) {
        this.categoriaProdutoService = categoriaProdutoService;
    }

    @Override
    public CategoriaProduto create(CategoriaProduto t) {
        return categoriaProdutoService.create(t);
    }

    @Override
    public CategoriaProduto getById(Long id) {
        return categoriaProdutoService.getById(id);
    }

    @Override
    public List<CategoriaProduto> getAll() {
       return categoriaProdutoService.getAll();
    }

    @Override
    public void update(CategoriaProduto t) {
        categoriaProdutoService.update(t);
    }

    @Override
    public void delete(Long id) {
        categoriaProdutoService.delete(id);
    }

}

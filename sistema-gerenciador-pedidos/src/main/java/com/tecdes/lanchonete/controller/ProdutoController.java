package com.tecdes.lanchonete.controller;

import java.util.List;

import com.tecdes.lanchonete.generalinterfaces.crud.Crud;
import com.tecdes.lanchonete.model.entity.CategoriaProduto;
import com.tecdes.lanchonete.model.entity.Produto;
import com.tecdes.lanchonete.service.ProdutoService;

public class ProdutoController implements Crud<Produto> {

    private ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @Override
    public Produto create(Produto t) {
        return produtoService.create(t);
    }

    @Override
    public Produto getById(Long id) {
        return produtoService.getById(id);
    }

    @Override
    public List<Produto> getAll() {
        return produtoService.getAll();
    }

    @Override
    public void update(Produto t) {
        produtoService.update(t);
    }

    @Override
    public void delete(Long id) {
        produtoService.delete(id);
    }

    public List<Produto> getByCategoriaProduto(CategoriaProduto categoriaProduto) {
        return produtoService.getByCategoriaProduto(categoriaProduto);
    }

}

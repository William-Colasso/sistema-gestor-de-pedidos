package com.tecdes.lanchonete.repository.implementation;

import java.util.List;

import com.tecdes.lanchonete.model.dao.ProdutoDAO;
import com.tecdes.lanchonete.model.entity.CategoriaProduto;
import com.tecdes.lanchonete.model.entity.Produto;
import com.tecdes.lanchonete.repository.interfaces.ProdutoRepository;

public class IProdutoRepository implements ProdutoRepository {

    private final ProdutoDAO produtoDAO;

    public IProdutoRepository(ProdutoDAO produtoDAO) {
        this.produtoDAO = produtoDAO;
    }

    public IProdutoRepository() {
        this.produtoDAO = new ProdutoDAO();
    }

    @Override
    public Produto create(Produto t) {
        return produtoDAO.create(t);
    }

    @Override
    public void delete(Long id) {
        produtoDAO.delete(id);
    }

    @Override
    public void update(Produto t) {
        produtoDAO.update(t);
    }

    @Override
    public Produto getById(Long id) {
        return produtoDAO.getById(id);
    }

    @Override
    public List<Produto> getAll() {
        return produtoDAO.getAll();
    }

    @Override
    public List<Produto> getByCategoria(CategoriaProduto categoriaProduto) {
        return produtoDAO.getByCategoriaProduto(categoriaProduto);
    }
}

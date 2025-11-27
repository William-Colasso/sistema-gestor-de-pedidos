package com.tecdes.lanchonete.repository.implementation;

import java.util.List;

import com.tecdes.lanchonete.model.dao.CategoriaProdutoDAO;
import com.tecdes.lanchonete.model.entity.CategoriaProduto;
import com.tecdes.lanchonete.repository.interfaces.CategoriaProdutoRepository;

public class ICategoriaProdutoRepository implements CategoriaProdutoRepository{

    private final CategoriaProdutoDAO categoriaProdutoDAO;

    public ICategoriaProdutoRepository(CategoriaProdutoDAO categoriaProdutoDAO) {
        this.categoriaProdutoDAO = categoriaProdutoDAO;
    }

    public ICategoriaProdutoRepository() {
        this.categoriaProdutoDAO = new CategoriaProdutoDAO();
    }


    @Override
    public void delete(Long id) {
        categoriaProdutoDAO.delete(id);
    }

    @Override
    public void update(CategoriaProduto t) {
        categoriaProdutoDAO.update(t);
    }

    @Override
    public CategoriaProduto getById(Long id) {
        return categoriaProdutoDAO.getById(id);
    }

    @Override
    public List<CategoriaProduto> getAll() {
        return categoriaProdutoDAO.getAll();
    }

    @Override
    public CategoriaProduto create(CategoriaProduto t) {
        return categoriaProdutoDAO.create(t);
    }

}

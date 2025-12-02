package com.tecdes.lanchonete.service;

import java.util.List;

import com.tecdes.lanchonete.exception.InvalidDeleteOperationException;
import com.tecdes.lanchonete.exception.InvalidFieldException;
import com.tecdes.lanchonete.exception.InvalidIdException;
import com.tecdes.lanchonete.generalinterfaces.crud.Crud;
import com.tecdes.lanchonete.model.entity.CategoriaProduto;
import com.tecdes.lanchonete.repository.interfaces.CategoriaProdutoRepository;

public class CategoriaProdutoService implements Crud<CategoriaProduto> {

    private final CategoriaProdutoRepository categoriaProdutoRepository;

    public CategoriaProdutoService(CategoriaProdutoRepository categoriaProdutoRepository){
        this.categoriaProdutoRepository = categoriaProdutoRepository;
    }

    @Override
    public CategoriaProduto create(CategoriaProduto t) {
        if(t.getNome() == null){
            throw new InvalidFieldException("O nome da categoria não pode ser nulo");
        }
        if(t.getSigla() == null){
            throw new InvalidFieldException("A sigla da categoria não pode ser nulo");
        }
        if(t.getImagem() == null){
            throw new InvalidFieldException("A imagem da categoria não pode ser nulo");
        }
        return categoriaProdutoRepository.create(t);
    }

    @Override
    public CategoriaProduto getById(Long id) {
        return categoriaProdutoRepository.getById(id);
    }

    @Override
    public List<CategoriaProduto> getAll() {
        return categoriaProdutoRepository.getAll();
    }

    @Override
    public void update(CategoriaProduto t) {
        if(t.getId() == null){
            throw new InvalidIdException("Não é possível atualizar Categoria com ID nulo");
        }
        categoriaProdutoRepository.update(t);
    }

    @Override
    public void delete(Long id) {
        if(id == null){
            throw new InvalidDeleteOperationException("Não é possível deletar Categoria nula.");
        }
        categoriaProdutoRepository.delete(id);
    }
    
}

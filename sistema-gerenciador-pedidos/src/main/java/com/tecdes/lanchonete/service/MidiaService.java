package com.tecdes.lanchonete.service;


import java.util.List;

import com.tecdes.lanchonete.exception.InvalidDeleteOperationException;
import com.tecdes.lanchonete.exception.InvalidFieldException;
import com.tecdes.lanchonete.exception.InvalidIdException;
import com.tecdes.lanchonete.generalinterfaces.crud.Crud;

import com.tecdes.lanchonete.model.entity.Midia;
import com.tecdes.lanchonete.repository.implementation.IMidiaRepository;

public class MidiaService implements Crud<Midia> {

    private final IMidiaRepository iMidiaRepository;

    public MidiaService(IMidiaRepository iMidiaRepository){
        this.iMidiaRepository = iMidiaRepository;
    }

    @Override
    public void delete(Long id) {
        if(id == null){
            throw new InvalidDeleteOperationException("Não é possível deletar Mídia nula.");
        }
        iMidiaRepository.delete(id);
    }

    @Override
    public void update(Midia t) {
        if(t.getId() == null){
            throw new InvalidIdException("Não é possível atualizar item com ID nulo");
        }
        iMidiaRepository.update(t);
    }

    @Override
    public Midia create(Midia t) {
        if(t.getArquivo()==null){
            throw new InvalidFieldException("Arquivo da mídia não pode ser nulo");
        }   
        if(t.getIdItem()==null){
            throw new InvalidFieldException("ID do item da mídia não pode ser nulo");
        }
        if(t.getDescricao() == null){
            throw new InvalidFieldException("Descrição da mídia não pode ser nulo");
        }
        if(t.getTipo() == null){
            throw new InvalidFieldException("Tipo da mídia não pode ser nulo");
        }
        return iMidiaRepository.create(t);
    }

    @Override
    public Midia getById(Long id) {
        return iMidiaRepository.getById(id);
    }

    @Override
    public List<Midia> getAll() {
        return iMidiaRepository.getAll();
    }

    public List<Midia> getMidiasByIdItem(Long idItem){
        List<Midia> listaMidias = iMidiaRepository.getAll();
        listaMidias.removeIf(m -> !m.getIdItem().equals(idItem));
        return listaMidias;
    }

}

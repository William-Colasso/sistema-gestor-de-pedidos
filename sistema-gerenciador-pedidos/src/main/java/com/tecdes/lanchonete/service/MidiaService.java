package com.tecdes.lanchonete.service;

import java.util.List;
import java.util.Objects;

import com.tecdes.lanchonete.exception.InvalidDeleteOperationException;
import com.tecdes.lanchonete.exception.InvalidFieldException;
import com.tecdes.lanchonete.exception.InvalidIdException;
import com.tecdes.lanchonete.generalinterfaces.crud.Crud;
import com.tecdes.lanchonete.model.entity.Midia;
import com.tecdes.lanchonete.repository.interfaces.MidiaRepository;

public class MidiaService implements Crud<Midia> {

    private final MidiaRepository midiaRepository;

    public MidiaService(MidiaRepository midiaRepository) {
        this.midiaRepository = midiaRepository;
    }

    @Override
    public void delete(Long id) {
        if(id == null){
            throw new InvalidDeleteOperationException("Não é possível deletar Mídia nula.");
        }
        midiaRepository.delete(id);
    }

    @Override
    public void update(Midia t) {
        if(t.getId() == null){
            throw new InvalidIdException("Não é possível atualizar item com ID nulo");
        }
        midiaRepository.update(t);
    }

    @Override
    public Midia create(Midia t) {
        if(t.getArquivo()==null){
            throw new InvalidFieldException("Arquivo da mídia não pode ser nulo");
        }   
        if(t.getItem().getId()==null){
            throw new InvalidFieldException("ID do item da mídia não pode ser nulo");
        }
        if(t.getDescricao() == null){
            throw new InvalidFieldException("Descrição da mídia não pode ser nulo");
        }
        if(t.getTipo() == null){
            throw new InvalidFieldException("Tipo da mídia não pode ser nulo");
        }
        return midiaRepository.create(t);
    }

    @Override
    public Midia getById(Long id) {
        return midiaRepository.getById(id);
    }

    @Override
    public List<Midia> getAll() {
        return midiaRepository.getAll();
    }

    public List<Midia> getMidiasByIdItem(Long idItem) {
        return midiaRepository.getAll().stream()
                .filter(m -> Objects.equals(m.getItem().getId(), idItem))
                .toList();
    }

}

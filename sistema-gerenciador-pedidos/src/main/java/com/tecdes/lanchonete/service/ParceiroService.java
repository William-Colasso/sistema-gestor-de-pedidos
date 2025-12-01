package com.tecdes.lanchonete.service;

import java.util.List;

import com.tecdes.lanchonete.exception.InvalidDeleteOperationException;
import com.tecdes.lanchonete.exception.InvalidFieldException;
import com.tecdes.lanchonete.exception.InvalidIdException;
import com.tecdes.lanchonete.generalinterfaces.crud.Crud;
import com.tecdes.lanchonete.model.entity.Parceiro;
import com.tecdes.lanchonete.repository.implementation.IParceiroRepository;

public class ParceiroService implements Crud<Parceiro> {
    private final IParceiroRepository iParceiroRepository;

    public ParceiroService(IParceiroRepository iParceiroRepository) {
        this.iParceiroRepository = iParceiroRepository;
    }

    @Override
    public Parceiro create(Parceiro t) {
        if(t.getNome() == null){
            throw new InvalidFieldException("O nome do parceiro não pode ser nulo");
        }
        if(t.getEmail() == null){
            throw new InvalidFieldException("O email do parceiro não pode ser nulo");
        }
        if(t.getTelefone() == null || t.getTelefone().trim().length() != 11){
            throw new InvalidFieldException("O telefone do parceiro não pode ser nulo");
        }
        return iParceiroRepository.create(t);
    }

    @Override
    public Parceiro getById(Long id) {
        return iParceiroRepository.getById(id);
    }

    @Override
    public List<Parceiro> getAll() {
        return iParceiroRepository.getAll();
    }

    @Override
    public void update(Parceiro t) {
        if(t.getId() == null){
            throw new InvalidIdException("Não é possível atualizar o parceiro com ID nulo");
        }
        iParceiroRepository.update(t);
    }

    @Override
    public void delete(Long id) {
        if(id == null){
            throw new InvalidDeleteOperationException("Não é possível deletar Parceiro nulo.");
        }
        iParceiroRepository.delete(id);
    }

}

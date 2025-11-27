package com.tecdes.lanchonete.service;

import java.util.List;

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

        }
        if(t.getEmail() == null){

        }
        if(t.getTelefone() == null){

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

        }
        iParceiroRepository.update(t);
    }

    @Override
    public void delete(Long id) {
        if(id == null){
            
        }
        iParceiroRepository.delete(id);
    }

}

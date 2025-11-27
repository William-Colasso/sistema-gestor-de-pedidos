package com.tecdes.lanchonete.service;

import java.util.List;

import com.tecdes.lanchonete.generalinterfaces.crud.Crud;
import com.tecdes.lanchonete.model.entity.Cupom;
import com.tecdes.lanchonete.repository.implementation.ICupomRepository;

public class CupomService implements Crud<Cupom> {

    private final ICupomRepository iCupomRepository;

    public CupomService(ICupomRepository iCupomRepository) {
        this.iCupomRepository = iCupomRepository;
    }

    @Override
    public Cupom create(Cupom t) {
        if(t.getParceiro() == null){
            throw new IllegalArgumentException("Parceiro não pode ser null");
        }
        return iCupomRepository.create(t);
    }

    @Override
    public Cupom getById(Long id) {
        return iCupomRepository.getById(id);
    }

    @Override
    public List<Cupom> getAll() {
        return iCupomRepository.getAll();
    }

    @Override
    public void update(Cupom t) {
        iCupomRepository.update(t);
    }

    @Override
    public void delete(Long id) {
        iCupomRepository.delete(id);
    }
    
}

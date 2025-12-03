package com.tecdes.lanchonete.service;

import java.util.List;

import com.tecdes.lanchonete.exception.InvalidDeleteOperationException;
import com.tecdes.lanchonete.exception.InvalidFieldException;
import com.tecdes.lanchonete.exception.InvalidIdException;
import com.tecdes.lanchonete.generalinterfaces.crud.Crud;
import com.tecdes.lanchonete.model.entity.Cupom;
import com.tecdes.lanchonete.repository.interfaces.CupomRepository;

public class CupomService implements Crud<Cupom> {

    private final CupomRepository cupomRepository;

    public CupomService(CupomRepository cupomRepository) {
        this.cupomRepository = cupomRepository;
    }

    @Override
    public Cupom create(Cupom t) {
        if(t.getParceiro() == null){
            throw new InvalidFieldException("Parceiro do cupom não pode ser nulo");
        }
        if(t.getNome() == null){
            throw new InvalidFieldException("Nome do cupom não pode ser nulo");
        }
        if(t.getValorDesconto() <= 0){
            throw new InvalidFieldException("Desconto do cupom não pode ser negativo");
        }
        if(t.getDescricao() == null){
            throw new InvalidFieldException("Descrição do cupom não pode ser nula");
        }
        return cupomRepository.create(t);
    }

    @Override
    public Cupom getById(Long id) {
        return cupomRepository.getById(id);
    }

    @Override
    public List<Cupom> getAll() {
        return cupomRepository.getAll();
    }

    @Override
    public void update(Cupom t) {
        if(t.getId() == null){
            throw new InvalidIdException("Não é possível atualizar Cupom com ID nulo");
        }
        cupomRepository.update(t);
    }

    @Override
    public void delete(Long id) {
        if(id == null){
            throw new InvalidDeleteOperationException("Não é possível deletar Cupom nulo.");
        }
        cupomRepository.delete(id);
    }
    
}

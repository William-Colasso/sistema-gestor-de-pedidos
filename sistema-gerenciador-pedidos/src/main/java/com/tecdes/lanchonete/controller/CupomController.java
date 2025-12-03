package com.tecdes.lanchonete.controller;

import java.util.List;

import com.tecdes.lanchonete.generalinterfaces.crud.Crud;
import com.tecdes.lanchonete.model.entity.Cupom;
import com.tecdes.lanchonete.service.CupomService;

public class CupomController implements Crud<Cupom> {

    private CupomService cupomService;

    public CupomController(CupomService cupomService) {
        this.cupomService = cupomService;
    }

    @Override
    public Cupom create(Cupom t) {
        return cupomService.create(t);
    }

    @Override
    public Cupom getById(Long id) {
        return cupomService.getById(id);
    }

    @Override
    public List<Cupom> getAll() {
        return cupomService.getAll();
    }

    @Override
    public void update(Cupom t) {
        cupomService.update(t);
    }

    @Override
    public void delete(Long id) {
        cupomService.delete(id);
    }

}

package com.tecdes.lanchonete.controller;

import java.util.List;

import com.tecdes.lanchonete.generalinterfaces.crud.Crud;
import com.tecdes.lanchonete.model.entity.Midia;
import com.tecdes.lanchonete.service.MidiaService;

public class MidiaController implements Crud<Midia> {

    private MidiaService midiaService;

    public MidiaController(MidiaService midiaService) {
        this.midiaService = midiaService;
    }

    @Override
    public Midia create(Midia t) {
        return midiaService.create(t);
    }

    @Override
    public Midia getById(Long id) {
        return midiaService.getById(id);
    }

    @Override
    public List<Midia> getAll() {
        return midiaService.getAll();
    }

    @Override
    public void update(Midia t) {
        midiaService.update(t);
    }

    @Override
    public void delete(Long id) {
        midiaService.delete(id);
    }

}

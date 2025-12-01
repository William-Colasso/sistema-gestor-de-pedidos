package com.tecdes.lanchonete.controller;

import java.util.List;

import com.tecdes.lanchonete.generalinterfaces.crud.Crud;
import com.tecdes.lanchonete.model.entity.Parceiro;
import com.tecdes.lanchonete.service.ParceiroService;

public class ParceiroController implements Crud<Parceiro> {

    private ParceiroService parceiroService;

    public ParceiroController(ParceiroService parceiroService) {
        this.parceiroService = parceiroService;
    }

    @Override
    public Parceiro create(Parceiro t) {
        return parceiroService.create(t);
    }

    @Override
    public Parceiro getById(Long id) {
        return parceiroService.getById(id);
    }

    @Override
    public List<Parceiro> getAll() {
        return parceiroService.getAll();
    }

    @Override
    public void update(Parceiro t) {
        parceiroService.update(t);
    }

    @Override
    public void delete(Long id) {
        parceiroService.delete(id);
    }

}

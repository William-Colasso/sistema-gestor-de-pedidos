package com.tecdes.lanchonete.controller;

import java.util.List;

import com.tecdes.lanchonete.generalinterfaces.crud.Crud;
import com.tecdes.lanchonete.model.entity.Combo;
import com.tecdes.lanchonete.service.ComboService;

public class ComboController implements Crud<Combo>{

    private ComboService comboService;

    public ComboController(ComboService comboService) {
        this.comboService = comboService;
    }

    @Override
    public Combo create(Combo t) {
        return comboService.create(t);
    }

    @Override
    public Combo getById(Long id) {
        return comboService.getById(id);
    }

    @Override
    public List<Combo> getAll() {
        return comboService.getAll();
    }

    @Override
    public void update(Combo t) {
        comboService.update(t);
    }

    @Override
    public void delete(Long id) {
        comboService.delete(id);
    }

}

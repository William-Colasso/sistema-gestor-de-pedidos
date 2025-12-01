package com.tecdes.lanchonete.controller;

import java.util.List;

import com.tecdes.lanchonete.generalinterfaces.crud.Crud;
import com.tecdes.lanchonete.model.entity.Gerente;
import com.tecdes.lanchonete.service.GerenteService;

public class GerenteController implements Crud<Gerente> {

    private GerenteService gerenteService;

    public GerenteController(GerenteService gerenteService) {
        this.gerenteService = gerenteService;
    }

    public boolean checkLoginPassword(String login, String senha) {
        return gerenteService.login(login, senha);
    }

    @Override
    public Gerente create(Gerente t) {
        return gerenteService.create(t);
    }

    @Override
    public Gerente getById(Long id) {
        return gerenteService.getById(id);
    }

    @Override
    public List<Gerente> getAll() {
        return gerenteService.getAll();
    }

    @Override
    public void update(Gerente t) {
        gerenteService.update(t);
    }

    @Override
    public void delete(Long id) {
        gerenteService.delete(id);
    }


    public boolean login(String login, String senha){
        return gerenteService.login(login, senha);
    }
}

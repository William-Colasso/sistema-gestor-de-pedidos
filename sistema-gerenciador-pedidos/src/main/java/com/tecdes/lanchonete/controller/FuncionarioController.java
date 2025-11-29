package com.tecdes.lanchonete.controller;

import java.util.List;

import com.tecdes.lanchonete.generalinterfaces.crud.Crud;
import com.tecdes.lanchonete.model.entity.Funcionario;
import com.tecdes.lanchonete.service.FuncionarioService;

public class FuncionarioController implements Crud<Funcionario> {

    private FuncionarioService funcionarioService;

    public FuncionarioController(FuncionarioService funcionarioService){
        this.funcionarioService = funcionarioService;
    }

    @Override
    public Funcionario create(Funcionario t) {
        return funcionarioService.create(t);
    }

    @Override
    public Funcionario getById(Long id) {
        return funcionarioService.getById(id);
    }

    @Override
    public List<Funcionario> getAll() {
        return funcionarioService.getAll();
    }

    @Override
    public void update(Funcionario t) {
        update(t);
    }

    @Override
    public void delete(Long id) {
        delete(id);
    }

}

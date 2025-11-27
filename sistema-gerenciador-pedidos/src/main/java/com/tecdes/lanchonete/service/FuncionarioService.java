package com.tecdes.lanchonete.service;

import java.util.List;

import com.tecdes.lanchonete.generalinterfaces.crud.Crud;
import com.tecdes.lanchonete.model.entity.Funcionario;
import com.tecdes.lanchonete.repository.implementation.IFuncionarioRepository;

public class FuncionarioService implements Crud<Funcionario> {

    private final IFuncionarioRepository iFuncionarioRepository;

    public FuncionarioService(IFuncionarioRepository iFuncionarioRepository){
        this.iFuncionarioRepository = iFuncionarioRepository;
    }

    @Override
    public Funcionario create(Funcionario t) {
        return iFuncionarioRepository.create(t);
    }

    @Override
    public Funcionario getById(Long id) {
        return iFuncionarioRepository.getById(id);
    }

    @Override
    public List<Funcionario> getAll() {
        return iFuncionarioRepository.getAll();
    }

    @Override
    public void update(Funcionario t) {
        iFuncionarioRepository.update(t);
    }

    @Override
    public void delete(Long id) {
        iFuncionarioRepository.delete(id);
    }
    
}

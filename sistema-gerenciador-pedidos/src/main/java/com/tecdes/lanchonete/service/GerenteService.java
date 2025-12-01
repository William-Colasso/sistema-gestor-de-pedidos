package com.tecdes.lanchonete.service;

import java.util.List;

import com.tecdes.lanchonete.generalinterfaces.crud.Crud;
import com.tecdes.lanchonete.model.entity.Gerente;
import com.tecdes.lanchonete.repository.implementation.IGerenteRepository;

public class GerenteService implements Crud<Gerente> {

    private final IGerenteRepository iGerenteRepository;
    private final FuncionarioService funcionarioService;

    public GerenteService(IGerenteRepository iGerenteRepository, FuncionarioService funcionarioService){
        this.iGerenteRepository = iGerenteRepository;
        this.funcionarioService = funcionarioService;
    }

    @Override
    public Gerente create(Gerente t) {
        if(t.getLogin() == null){

        }
        if(t.getSenha() == null){

        }
        
        funcionarioService.create(t);
        return iGerenteRepository.create(t);
    }

    @Override
    public Gerente getById(Long id) {
        return iGerenteRepository.getById(id);
    }

    @Override
    public List<Gerente> getAll() {
        return iGerenteRepository.getAll();
    }

    @Override
    public void update(Gerente t) {
        funcionarioService.update(t);
        iGerenteRepository.update(t);
    }

    @Override
    public void delete(Long id) {
        iGerenteRepository.delete(id);
        funcionarioService.delete(id);
    }

    public boolean login(String login, String senha){
        return (iGerenteRepository.getByLogin(login).getSenha().equals(senha)) ? true : false;
    }
    
}

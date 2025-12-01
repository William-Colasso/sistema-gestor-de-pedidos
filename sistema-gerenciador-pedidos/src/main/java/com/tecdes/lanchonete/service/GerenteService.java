package com.tecdes.lanchonete.service;

import java.util.List;

import com.tecdes.lanchonete.exception.InvalidDeleteOperationException;
import com.tecdes.lanchonete.exception.InvalidFieldException;
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
            throw new InvalidFieldException("Login do gerente não pode ser nulo");
        }
        if(t.getSenha() == null){
            throw new InvalidFieldException("Senha do gerente não pode ser nula");
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
        funcionarioService.update(t); // Funcionário service já verifica se id é null
        iGerenteRepository.update(t);
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new InvalidDeleteOperationException("Não é possível deletar Gerente nulo.");
        }
        iGerenteRepository.delete(id);
        funcionarioService.delete(id);
    }

    public boolean login(String login, String senha){
        return (iGerenteRepository.getByLogin(login).getSenha().equals(senha));
    }
    
}


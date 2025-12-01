package com.tecdes.lanchonete.service;

import java.util.List;

import com.tecdes.lanchonete.exception.InvalidDeleteOperationException;
import com.tecdes.lanchonete.exception.InvalidFieldException;
import com.tecdes.lanchonete.exception.InvalidIdException;
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
        if(t.getCpf() == null || t.getCpf().trim().length() != 14){
            throw new InvalidFieldException("CPF do funcionário não pode ser nulo ou inválido");
        }
        if(t.getDataNascimento() == null){
            throw new InvalidFieldException("Data de nascimento do funcionário não pode ser nula");
        }
        if(t.getNome() == null){
            throw new InvalidFieldException("Nome do funcionário não pode ser nula");
        }
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
        if(t.getId() == null){
            throw new InvalidIdException("Não é possível atualizar funcionário com ID nulo");
        }
        iFuncionarioRepository.update(t);
    }

    @Override
    public void delete(Long id) {
        if(id == null){
            throw new InvalidDeleteOperationException("Não é possível deletar Funcionário nulo.");
        }
        iFuncionarioRepository.delete(id);
    }
    
}

package com.tecdes.lanchonete.service;

import java.util.List;

import com.tecdes.lanchonete.exception.InvalidDeleteOperationException;
import com.tecdes.lanchonete.exception.InvalidFieldException;
import com.tecdes.lanchonete.exception.InvalidIdException;
import com.tecdes.lanchonete.generalinterfaces.crud.Crud;
import com.tecdes.lanchonete.model.entity.Funcionario;
import com.tecdes.lanchonete.repository.interfaces.FuncionarioRepository;

public class FuncionarioService implements Crud<Funcionario> {

    private final FuncionarioRepository funcionarioRepository;

    public FuncionarioService(FuncionarioRepository funcionarioRepository){
        this.funcionarioRepository = funcionarioRepository;
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
        return funcionarioRepository.create(t);
    }

    @Override
    public Funcionario getById(Long id) {
        return funcionarioRepository.getById(id);
    }

    @Override
    public List<Funcionario> getAll() {
        return funcionarioRepository.getAll();
    }

    @Override
    public void update(Funcionario t) {
        if(t.getId() == null){
            throw new InvalidIdException("Não é possível atualizar funcionário com ID nulo");
        }
        funcionarioRepository.update(t);
    }

    @Override
    public void delete(Long id) {
        if(id == null){
            throw new InvalidDeleteOperationException("Não é possível deletar Funcionário nulo.");
        }
        funcionarioRepository.delete(id);
    }
    
}

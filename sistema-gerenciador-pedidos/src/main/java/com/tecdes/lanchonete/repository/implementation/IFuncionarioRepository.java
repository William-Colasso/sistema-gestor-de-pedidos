package com.tecdes.lanchonete.repository.implementation;

import java.util.List;

import com.tecdes.lanchonete.model.dao.FuncionarioDAO;
import com.tecdes.lanchonete.model.entity.Funcionario;
import com.tecdes.lanchonete.repository.interfaces.FuncionarioRepository;

public class IFuncionarioRepository implements FuncionarioRepository {

    private final FuncionarioDAO funcionarioDAO;

    public IFuncionarioRepository(FuncionarioDAO funcionarioDAO) {
        this.funcionarioDAO = funcionarioDAO;
    }

    public IFuncionarioRepository() {
        this.funcionarioDAO = new FuncionarioDAO();
    }

    @Override
    public Funcionario create(Funcionario t) {
        return funcionarioDAO.create(t);
    }

    @Override
    public void delete(Long id) {
        funcionarioDAO.delete(id);
    }

    @Override
    public void update(Funcionario t) {
        funcionarioDAO.update(t);
    }

    @Override
    public Funcionario getById(Long id) {
        return funcionarioDAO.getById(id);
    }

    @Override
    public List<Funcionario> getAll() {
        return funcionarioDAO.getAll();
    }
}

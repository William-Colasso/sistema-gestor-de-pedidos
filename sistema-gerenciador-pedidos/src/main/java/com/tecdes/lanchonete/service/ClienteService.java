package com.tecdes.lanchonete.service;

import java.util.List;

import com.tecdes.lanchonete.exception.InvalidDeleteOperationException;
import com.tecdes.lanchonete.exception.InvalidFieldException;
import com.tecdes.lanchonete.exception.InvalidIdException;
import com.tecdes.lanchonete.generalinterfaces.crud.Crud;
import com.tecdes.lanchonete.model.entity.Cliente;
import com.tecdes.lanchonete.model.entity.Cupom;
import com.tecdes.lanchonete.repository.implementation.IClienteRepository;

public class ClienteService implements Crud<Cliente> {

    private final IClienteRepository iClienteRepository;

    public ClienteService(IClienteRepository iClienteRepository) {
        this.iClienteRepository = iClienteRepository;
    }

    @Override
    public Cliente create(Cliente t) {
        if(t.getCpf()==null || t.getCpf().trim().length() != 14){
            throw new InvalidFieldException("O CPF do cliente não pode ser nulo ou inválido");
        }
        if(t.getNome() == null){
            throw new InvalidFieldException("O nome da do cliente não pode ser nulo");
        }
        if(t.getTelefone().length() != 11 && !t.getTelefone().trim().isEmpty()) {
            throw new InvalidFieldException("O telefone precisa ser válido");
        }
        return iClienteRepository.create(t);
    }

    @Override
    public Cliente getById(Long id) {
        return iClienteRepository.getById(id);
    }

    @Override
    public List<Cliente> getAll() {
        return iClienteRepository.getAll();
    }

    @Override
    public void update(Cliente t) {
        if(t.getId()==null){
            throw new InvalidIdException("Não é possível atualizar cliente com ID nulo");
        }
        iClienteRepository.update(t);
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new InvalidDeleteOperationException("Não é possível deletar Cliente nulo");
        }
        iClienteRepository.delete(id);
    }

    public boolean verifyUsedCupom(Cliente cliente, Cupom cupom){
        List<Cupom> listaCupomUtilizados = iClienteRepository.getCuponsByIdCliente(cliente.getId());
        return listaCupomUtilizados.stream()
        .anyMatch(c ->c.getId().equals(cupom.getId()));
    }
    
}

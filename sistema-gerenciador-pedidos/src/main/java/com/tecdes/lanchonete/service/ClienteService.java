package com.tecdes.lanchonete.service;

import java.util.List;

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
        if(t.getCpf()==null){
            throw new IllegalArgumentException("Cpf não pode ser nulo");
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
            throw new IllegalArgumentException("Cliente não identificado");
        }
        iClienteRepository.update(t);
    }

    @Override
    public void delete(Long id) {
        iClienteRepository.delete(id);
    }

    public boolean verifyUsedCupom(Cliente cliente, Cupom cupom){
        List<Cupom> listaCupomUtilizados = iClienteRepository.getCuponsByIdCliente(cliente.getId());
        return listaCupomUtilizados.stream()
        .anyMatch(c ->c.getId().equals(cupom.getId()));
    }
    
}

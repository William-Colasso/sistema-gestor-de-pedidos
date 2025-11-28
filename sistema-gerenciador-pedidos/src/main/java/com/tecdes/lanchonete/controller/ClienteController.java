package com.tecdes.lanchonete.controller;

import java.util.List;

import com.tecdes.lanchonete.generalinterfaces.crud.Crud;
import com.tecdes.lanchonete.model.entity.Cliente;
import com.tecdes.lanchonete.service.ClienteService;

public class ClienteController implements Crud<Cliente>{

    private ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @Override
    public Cliente create(Cliente t) {
        return clienteService.create(t);
    }

    @Override
    public Cliente getById(Long id) {
        return clienteService.getById(id);
    }

    @Override
    public List<Cliente> getAll() {
        return clienteService.getAll();
    }

    @Override
    public void update(Cliente t) {
        clienteService.update(t);
    }

    @Override
    public void delete(Long id) {
        clienteService.delete(id);
    }

}

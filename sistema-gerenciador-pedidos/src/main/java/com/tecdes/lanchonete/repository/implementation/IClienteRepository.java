package com.tecdes.lanchonete.repository.implementation;

import java.util.List;

import com.tecdes.lanchonete.model.dao.ClienteDAO;
import com.tecdes.lanchonete.model.entity.Cliente;
import com.tecdes.lanchonete.model.entity.Cupom;
import com.tecdes.lanchonete.repository.interfaces.ClienteRepository;

public class IClienteRepository implements ClienteRepository {

    private final ClienteDAO clienteDAO;

    public IClienteRepository(ClienteDAO clienteDAO) {
        this.clienteDAO = clienteDAO;
    }

    public IClienteRepository() {
        this.clienteDAO = new ClienteDAO();
    }

    @Override
    public Cliente create(Cliente t) {
        return clienteDAO.create(t);
    }

    @Override
    public void delete(Long id) {
        clienteDAO.delete(id);
    }

    @Override
    public void update(Cliente t) {
        clienteDAO.update(t);
    }

    @Override
    public Cliente getById(Long id) {
        return clienteDAO.getById(id);
    }

    @Override
    public List<Cliente> getAll() {
        return clienteDAO.getAll();
    }

    @Override
    public List<Cupom> getCuponsByIdCliente(Long id) {
        return clienteDAO.getCuponsByIdCliente(id);
    }
}

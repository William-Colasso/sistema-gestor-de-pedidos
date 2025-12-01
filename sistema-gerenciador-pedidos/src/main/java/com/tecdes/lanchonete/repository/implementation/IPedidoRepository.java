package com.tecdes.lanchonete.repository.implementation;

import java.util.List;

import com.tecdes.lanchonete.model.dao.PedidoDAO;
import com.tecdes.lanchonete.model.entity.Pedido;
import com.tecdes.lanchonete.repository.interfaces.PedidoRepository;

public class IPedidoRepository implements PedidoRepository {

    private final PedidoDAO pedidoDAO;

    public IPedidoRepository(PedidoDAO pedidoDAO) {
        this.pedidoDAO = pedidoDAO;
    }

    public IPedidoRepository() {
        this.pedidoDAO = new PedidoDAO();
    }

    @Override
    public Pedido create(Pedido t) {
        return pedidoDAO.create(t);
    }

    @Override
    public void delete(Long id) {
        pedidoDAO.delete(id);
    }

    @Override
    public void update(Pedido t) {
        pedidoDAO.update(t);
    }

    @Override
    public Pedido getById(Long id) {
        return pedidoDAO.getById(id);
    }

    @Override
    public List<Pedido> getAll() {
        return pedidoDAO.getAll();
    }

    public List<Pedido> getByCliente(Long id) {
        return pedidoDAO.getByCliente(id);
    }

    public List<Pedido> getByStatusPedido(char status) {
        return pedidoDAO.getByStatusPedido(status);
    }

    public List<Pedido> getByFuncionario(Long id) {
        return pedidoDAO.getByFuncionario(id);
    }
}
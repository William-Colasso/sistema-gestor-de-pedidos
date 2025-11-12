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

    @Override
    public void create(Pedido t) {
        pedidoDAO.create(t);
    }

    @Override
    public void delete(Long id) {
        pedidoDAO.delete(id);        
    }

    @Override
    public List<Pedido> getAll() {
        return pedidoDAO.getAll();
    }

    @Override
    public Pedido getById(Long id) {
        return pedidoDAO.getById(id);
    }

    @Override
    public void update(Pedido t) {
        pedidoDAO.update(t);
    }

}

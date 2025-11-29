package com.tecdes.lanchonete.controller;

import java.util.List;

import com.tecdes.lanchonete.generalinterfaces.crud.Crud;
import com.tecdes.lanchonete.model.entity.Pedido;
import com.tecdes.lanchonete.service.PedidoService;

public class PedidoController implements Crud<Pedido> {

    private PedidoService pedidoService;

    public PedidoController (PedidoService pedidoService){
        this.pedidoService = pedidoService;
    }

    @Override
    public Pedido create(Pedido t) {
        return pedidoService.create(t);
    }

    @Override
    public Pedido getById(Long id) {
        return pedidoService.getById(id);
    }

    @Override
    public List<Pedido> getAll() {
        return pedidoService.getAll();
    }

    @Override
    public void update(Pedido t) {
        pedidoService.update(t);
    }

    @Override
    public void delete(Long id) {
        pedidoService.delete(id);
    }

}

package com.tecdes.lanchonete.service;

import java.util.List;

import com.tecdes.lanchonete.generalinterfaces.crud.Crud;
import com.tecdes.lanchonete.model.entity.Pedido;
import com.tecdes.lanchonete.repository.implementation.IPedidoRepository;

public class PedidoService implements Crud<Pedido> {

    private final IPedidoRepository iPedidoRepository;

    public PedidoService(IPedidoRepository iPedidoRepository){
        this.iPedidoRepository = iPedidoRepository;
    }

    @Override
    public Pedido create(Pedido t) {
        if(t.getCliente() == null && t.getNomeCliente() == null){
            throw new IllegalArgumentException("O cliente deve ser especificado");
        }
        if(t.getItens() == null){
            throw new IllegalArgumentException("Os itens do pedido devem ser especificados");
        }
        if(t.getDataPedido() == null){

        }
        if(t.getFuncionario() == null){

        }
        if(t.getPagamento() == null){

        }
        return iPedidoRepository.create(t);
    }

    @Override
    public Pedido getById(Long id) {
        return iPedidoRepository.getById(id);
    }

    @Override
    public List<Pedido> getAll() {
        return iPedidoRepository.getAll();
    }

    @Override
    public void update(Pedido t) {
        if(t.getId() == null){

        }
        iPedidoRepository.update(t);
    }

    @Override
    public void delete(Long id) {
        if(id == null){
            
        }
        iPedidoRepository.delete(id);
    }
    
}

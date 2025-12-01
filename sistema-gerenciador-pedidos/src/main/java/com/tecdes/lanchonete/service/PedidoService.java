package com.tecdes.lanchonete.service;

import java.util.List;

import com.tecdes.lanchonete.exception.InvalidDeleteOperationException;
import com.tecdes.lanchonete.exception.InvalidFieldException;
import com.tecdes.lanchonete.exception.InvalidIdException;
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
            throw new InvalidFieldException("Nome do cliente ou cliente do pedido precisam ser especificados");
        }
        if(t.getItens() == null){
            throw new InvalidFieldException("Itens do pedido precisam ser especificados");
        }
        if(t.getDataPedido() == null){
            throw new InvalidFieldException("Data do pedido precisa ser especificada");
        }
        if(t.getFuncionario() == null){
            throw new InvalidFieldException("Funcionário do pedido precisa ser especificado");
        }
        if(t.getPagamento() == null){
            throw new InvalidFieldException("Pagamento do pedido precisa ser especificado");
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
            throw new InvalidIdException("Não é possível atualizar pedido com ID nulo");
        }
        iPedidoRepository.update(t);
    }

    @Override
    public void delete(Long id) {
        if(id == null){
            throw new InvalidDeleteOperationException("Não é possível deletar Pedido nulo.");
        }
        iPedidoRepository.delete(id);
    }
    
}

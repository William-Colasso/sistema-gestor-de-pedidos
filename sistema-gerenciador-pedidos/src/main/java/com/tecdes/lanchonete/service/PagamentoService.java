package com.tecdes.lanchonete.service;

import java.util.List;

import com.tecdes.lanchonete.generalinterfaces.crud.Readable;
import com.tecdes.lanchonete.model.entity.Pagamento;
import com.tecdes.lanchonete.repository.interfaces.PagamentoRepository;

public class PagamentoService implements Readable<Pagamento> {

    private final PagamentoRepository pagamentoRepository;

    public PagamentoService(PagamentoRepository pagamentoRepository){
        this.pagamentoRepository = pagamentoRepository;
    }

    @Override
    public Pagamento getById(Long id) {
        return pagamentoRepository.getById(id);
    }  

    @Override
    public List<Pagamento> getAll() {
        return pagamentoRepository.getAll();
    }
    
}

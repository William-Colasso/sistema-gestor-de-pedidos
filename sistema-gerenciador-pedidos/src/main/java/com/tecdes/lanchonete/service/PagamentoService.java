package com.tecdes.lanchonete.service;

import java.util.List;

import com.tecdes.lanchonete.generalinterfaces.crud.Readable;
import com.tecdes.lanchonete.model.entity.Pagamento;
import com.tecdes.lanchonete.repository.implementation.IPagamentoRepository;

public class PagamentoService implements Readable<Pagamento> {

    private final IPagamentoRepository iPagamentoRepository;

    public PagamentoService(IPagamentoRepository iPagamentoRepository){
        this.iPagamentoRepository = iPagamentoRepository;
    }

    @Override
    public Pagamento getById(Long id) {
        return iPagamentoRepository.getById(id);
    }  

    @Override
    public List<Pagamento> getAll() {
        return iPagamentoRepository.getAll();
    }
    
}

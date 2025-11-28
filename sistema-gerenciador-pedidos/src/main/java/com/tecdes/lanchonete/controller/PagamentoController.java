package com.tecdes.lanchonete.controller;

import java.util.List;

import com.tecdes.lanchonete.model.entity.Pagamento;
import com.tecdes.lanchonete.service.PagamentoService;
import com.tecdes.lanchonete.generalinterfaces.crud.Readable;

public class PagamentoController implements Readable<Pagamento> {

    private PagamentoService pagamentoService;

    public PagamentoController(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    @Override
    public Pagamento getById(Long id) {
        return pagamentoService.getById(id);
    }

    @Override
    public List<Pagamento> getAll() {
        return pagamentoService.getAll();
    }

}



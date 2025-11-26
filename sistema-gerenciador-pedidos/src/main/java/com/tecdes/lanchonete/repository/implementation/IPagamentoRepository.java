package com.tecdes.lanchonete.repository.implementation;

import java.util.List;

import com.tecdes.lanchonete.model.dao.PagamentoDAO;
import com.tecdes.lanchonete.model.entity.Pagamento;
import com.tecdes.lanchonete.repository.interfaces.PagamentoRepository;

public class IPagamentoRepository implements PagamentoRepository {

    private final PagamentoDAO pagamentoDAO;

    public IPagamentoRepository(PagamentoDAO pagamentoDAO) {
        this.pagamentoDAO = pagamentoDAO;
    }

    public IPagamentoRepository() {
        this.pagamentoDAO = new PagamentoDAO();
    }
    @Override
    public Pagamento getById(Long id) {
        return pagamentoDAO.getById(id);
    }

    @Override
    public List<Pagamento> getAll() {
        return pagamentoDAO.getAll();
    }
}

package com.tecdes.lanchonete.repository.implementation;

import java.util.List;

import com.tecdes.lanchonete.model.dao.ParceiroDAO;
import com.tecdes.lanchonete.model.entity.Parceiro;
import com.tecdes.lanchonete.repository.interfaces.ParceiroRepository;

public class IParceiroRepository implements ParceiroRepository {

    private final ParceiroDAO parceiroDAO;

    public IParceiroRepository(ParceiroDAO parceiroDAO) {
        this.parceiroDAO = parceiroDAO;
    }

    public IParceiroRepository() {
        this.parceiroDAO = new ParceiroDAO();
    }

    @Override
    public Parceiro create(Parceiro t) {
        return parceiroDAO.create(t);
    }

    @Override
    public void delete(Long id) {
        parceiroDAO.delete(id);
    }

    @Override
    public void update(Parceiro t) {
        parceiroDAO.update(t);
    }

    @Override
    public Parceiro getById(Long id) {
        return parceiroDAO.getById(id);
    }

    @Override
    public List<Parceiro> getAll() {
        return parceiroDAO.getAll();
    }
}

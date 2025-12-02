package com.tecdes.lanchonete.repository.implementation;

import java.util.List;

import com.tecdes.lanchonete.model.dao.MidiaDAO;
import com.tecdes.lanchonete.model.entity.Midia;
import com.tecdes.lanchonete.repository.interfaces.MidiaRepository;

public class IMidiaRepository implements MidiaRepository {

    private final MidiaDAO midiaDAO;

    public IMidiaRepository(MidiaDAO midiaDAO) {
        this.midiaDAO = midiaDAO;
    }

    public IMidiaRepository() {
        this.midiaDAO = new MidiaDAO();
    }

    @Override
    public Midia create(Midia t) {
        return midiaDAO.create(t);
    }

    @Override
    public void delete(Long id) {
        midiaDAO.delete(id);
    }

    @Override
    public void update(Midia t) {
        midiaDAO.update(t);
    }

    @Override
    public Midia getById(Long id) {
        return midiaDAO.getById(id);
    }

    @Override
    public List<Midia> getAll() {
        return midiaDAO.getAll();
    }

    @Override
    public Midia getMidiaByItem(Long id) {
        return midiaDAO.getMidiaByItem(id);
    }
}

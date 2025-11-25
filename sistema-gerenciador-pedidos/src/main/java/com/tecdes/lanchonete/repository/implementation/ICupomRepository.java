package com.tecdes.lanchonete.repository.implementation;

import java.util.List;

import com.tecdes.lanchonete.model.dao.CupomDAO;
import com.tecdes.lanchonete.model.entity.Cupom;
import com.tecdes.lanchonete.repository.interfaces.CupomRepository;

public class ICupomRepository implements CupomRepository {

    private final CupomDAO cupomDAO;

    public ICupomRepository(CupomDAO cupomDAO) {
        this.cupomDAO = cupomDAO;
    }

    public ICupomRepository() {
        this.cupomDAO = new CupomDAO();
    }

    @Override
    public Cupom create(Cupom t) {
        return cupomDAO.create(t);
    }

    @Override
    public void delete(Long id) {
        cupomDAO.delete(id);
    }

    @Override
    public void update(Cupom t) {
        cupomDAO.update(t);
    }

    @Override
    public Cupom getById(Long id) {
        return cupomDAO.getById(id);
    }

    @Override
    public List<Cupom> getAll() {
        return cupomDAO.getAll();
    }
}

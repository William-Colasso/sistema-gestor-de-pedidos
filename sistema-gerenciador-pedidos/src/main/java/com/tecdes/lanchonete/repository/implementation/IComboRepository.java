package com.tecdes.lanchonete.repository.implementation;

import java.util.List;

import com.tecdes.lanchonete.model.dao.ComboDAO;
import com.tecdes.lanchonete.model.entity.Combo;
import com.tecdes.lanchonete.repository.interfaces.ComboRepository;

public class IComboRepository implements ComboRepository {

    private final ComboDAO comboDAO;

    public IComboRepository(ComboDAO comboDAO) {
        this.comboDAO = comboDAO;
    }

    public IComboRepository() {
        this.comboDAO = new ComboDAO();
    }

    @Override
    public Combo create(Combo t) {
        return comboDAO.create(t);
    }

    @Override
    public void delete(Long id) {
        comboDAO.delete(id);
    }

    @Override
    public void update(Combo t) {
        comboDAO.update(t);
    }

    @Override
    public Combo getById(Long id) {
        return comboDAO.getById(id);
    }

    @Override
    public List<Combo> getAll() {
        return comboDAO.getAll();
    }
}

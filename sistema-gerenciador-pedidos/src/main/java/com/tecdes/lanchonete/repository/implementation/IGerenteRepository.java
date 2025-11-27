package com.tecdes.lanchonete.repository.implementation;

import java.util.List;

import com.tecdes.lanchonete.model.dao.GerenteDAO;
import com.tecdes.lanchonete.model.entity.Gerente;
import com.tecdes.lanchonete.repository.interfaces.GerenteRepository;

public class IGerenteRepository implements GerenteRepository {

    private final GerenteDAO gerenteDAO;

    public IGerenteRepository(GerenteDAO gerenteDAO) {
        this.gerenteDAO = gerenteDAO;
    }

    public IGerenteRepository() {
        this.gerenteDAO = new GerenteDAO();
    }

    @Override
    public Gerente create(Gerente t) {
        return gerenteDAO.create(t);
    }

    @Override
    public void delete(Long id) {
        gerenteDAO.delete(id);
    }

    @Override
    public void update(Gerente t) {
        gerenteDAO.update(t);
    }

    @Override
    public Gerente getById(Long id) {
        return gerenteDAO.getById(id);
    }

    @Override
    public List<Gerente> getAll() {
        return gerenteDAO.getAll();
    }
}

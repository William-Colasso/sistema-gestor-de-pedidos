package com.tecdes.lanchonete.repository.implementation;

import java.util.List;

import com.tecdes.lanchonete.model.dao.ItemDAO;
import com.tecdes.lanchonete.model.entity.Item;
import com.tecdes.lanchonete.repository.interfaces.ItemRepository;

public class IItemRepository implements ItemRepository {

    private final ItemDAO itemDAO;

    public IItemRepository(ItemDAO itemDAO) {
        this.itemDAO = itemDAO;
    }

    public IItemRepository() {
        this.itemDAO = new ItemDAO();
    }

    @Override
    public Item create(Item t) {
        return itemDAO.create(t);
    }

    @Override
    public void delete(Long id) {
        itemDAO.delete(id);
    }

    @Override
    public void update(Item t) {
        itemDAO.update(t);
    }

    @Override
    public Item getById(Long id) {
        return itemDAO.getById(id);
    }

    @Override
    public List<Item> getAll() {
        return itemDAO.getAll();
    }
}

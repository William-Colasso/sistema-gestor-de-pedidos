package com.tecdes.lanchonete.repository;

import java.util.List;

public interface EntityRepository<T> {
    

    public T create(T t);
    public void delete(Long id);
    public void update(T t);
    public T getById(Long id);
    public List<T> getAll();

}

package com.tecdes.lanchonete.model.dao;

import java.util.List;

public interface InterfaceDAO<T> {


    public void create(T t);
    public void delete(Long id);
    public void update(T t);
    public T getById(Long id);
    public List<T> getAll();
     

}

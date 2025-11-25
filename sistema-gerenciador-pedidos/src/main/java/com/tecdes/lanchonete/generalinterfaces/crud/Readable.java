package com.tecdes.lanchonete.generalinterfaces.crud;

import java.util.List;

public interface Readable<T> {
    public T getById(Long id);
    public List<T> getAll();
}

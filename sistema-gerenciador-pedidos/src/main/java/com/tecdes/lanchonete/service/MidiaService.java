package com.tecdes.lanchonete.service;


import java.util.List;

import com.tecdes.lanchonete.generalinterfaces.crud.Crud;

import com.tecdes.lanchonete.model.entity.Midia;
import com.tecdes.lanchonete.repository.implementation.IMidiaRepository;

public class MidiaService implements Crud<Midia> {

    private final IMidiaRepository iMidiaRepository;

    public MidiaService(IMidiaRepository IMidiaRepository){
        this.iMidiaRepository = new IMidiaRepository();
    }

    @Override
    public void delete(Long id) {
        iMidiaRepository.delete(id);
    }

    @Override
    public void update(Midia t) {
        if(t.getId() == null){
            throw new IllegalArgumentException("Midia não identificada");
        }
        iMidiaRepository.update(t);
    }

    @Override
    public Midia create(Midia t) {
        if(t.getArquivo()==null){
            throw new RuntimeException("A mídia é necessário ter um arquivo para salvar");
        }
        if(t.getIdItem()==null){
            throw new IllegalArgumentException("Item não identificado");
        }
        return iMidiaRepository.create(t);
    }

    @Override
    public Midia getById(Long id) {
        return iMidiaRepository.getById(id);
    }

    @Override
    public List<Midia> getAll() {
        return iMidiaRepository.getAll();
    }

    public List<Midia> getMidiasByIdItem(Long idItem){
        List<Midia> listaMidias = iMidiaRepository.getAll();
        listaMidias.forEach((m) -> {if(m.getIdItem() != idItem) listaMidias.remove(m);});
        return listaMidias;
    }

}

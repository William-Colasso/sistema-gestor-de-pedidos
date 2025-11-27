package com.tecdes.lanchonete.service;

import java.util.List;

import com.tecdes.lanchonete.generalinterfaces.crud.Crud;
import com.tecdes.lanchonete.model.entity.Combo;
import com.tecdes.lanchonete.model.entity.Item;
import com.tecdes.lanchonete.model.enums.TipoItem;
import com.tecdes.lanchonete.repository.implementation.IComboRepository;
import com.tecdes.lanchonete.repository.implementation.IItemRepository;

public class ComboService implements Crud<Combo> {

    private final IComboRepository iComboRepository;
    private final IItemRepository iItemRepository;
    private final MidiaService midiaService;

    public ComboService(IComboRepository iComboRepository, IItemRepository iItemRepository, MidiaService midiaService) {
        this.iComboRepository = iComboRepository;
        this.iItemRepository = iItemRepository;
        this.midiaService = midiaService;
    }


    @Override
    public Combo create(Combo t) {
        if(t.getNome() == null){

        }
        if(t.getDescricao() == null){

        }
        if(t.getTipoItem() == null){
            t.setTipoItem(TipoItem.COMBO);
        }
        if(t.getDesconto() <= 0){

        }
        t.setId(iItemRepository.create(t).getId());
        return iComboRepository.create(t);
    }

    @Override
    public void update(Combo t) {
        if(t.getId() == null){

        }
        iItemRepository.update(t);
        iComboRepository.update(t);
    }

    @Override
    public void delete(Long id) {
        if(id == null){

        }
        iComboRepository.delete(id);
        iItemRepository.delete(id);
    }
    
    @Override
    public Combo getById(Long id) {
        Combo c = iComboRepository.getById(id);
        mapCombo(iItemRepository.getById(id), c);
        return c;
    }

    @Override
    public List<Combo> getAll() {
        List<Combo> listaCombos = iComboRepository.getAll();
        listaCombos.forEach((c) -> {mapCombo(iItemRepository.getById(c.getId()), c);});
        return listaCombos;
    }

    private Combo mapCombo(Item item, Combo combo){
        combo.setId(item.getId());
        combo.setNome(item.getNome());
        combo.setTipoItem(item.getTipoItem());
        combo.setStatusAtivo(item.getStatusAtivo());
        combo.setDataCriacao(item.getDataCriacao());
        combo.setDescricao(item.getDescricao());
        combo.setPedidos(item.getPedidos());
        combo.setQuantidade(item.getQuantidade());
        combo.setMidias(midiaService.getMidiasByIdItem(combo.getId()));
        return combo;
    }

}

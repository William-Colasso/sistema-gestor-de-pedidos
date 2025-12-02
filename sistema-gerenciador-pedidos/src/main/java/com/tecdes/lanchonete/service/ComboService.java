package com.tecdes.lanchonete.service;

import java.util.List;

import com.tecdes.lanchonete.exception.InvalidDeleteOperationException;
import com.tecdes.lanchonete.exception.InvalidFieldException;
import com.tecdes.lanchonete.exception.InvalidIdException;
import com.tecdes.lanchonete.generalinterfaces.crud.Crud;
import com.tecdes.lanchonete.model.entity.Combo;
import com.tecdes.lanchonete.model.entity.Item;
import com.tecdes.lanchonete.model.enums.TipoItem;
import com.tecdes.lanchonete.repository.interfaces.ComboRepository;
import com.tecdes.lanchonete.repository.interfaces.ItemRepository;

public class ComboService implements Crud<Combo> {

    private final ComboRepository comboRepository;
    private final ItemRepository itemRepository;
    private final MidiaService midiaService;

    public ComboService(ComboRepository comboRepository, ItemRepository itemRepository, MidiaService midiaService) {
        this.comboRepository = comboRepository;
        this.itemRepository = itemRepository;
        this.midiaService = midiaService;
    }


    @Override
    public Combo create(Combo t) {
        if(t.getNome() == null){
            throw new InvalidFieldException("O nome do combo não pode ser nulo");
        }
        if(t.getDescricao() == null){
            throw new InvalidFieldException("A descrição do combo não pode ser nula");
        }
        if(t.getTipoItem() == null){
            t.setTipoItem(TipoItem.COMBO);
        }
        if(t.getDesconto() <= 0){
            throw new InvalidFieldException("O desconto não pode ser negativo");
        }
        t.setId(itemRepository.create(t).getId());
        return comboRepository.create(t);
    }

    @Override
    public void update(Combo t) {
        if(t.getId() == null){
            throw new InvalidIdException("Não é possível atualizar combo com ID nulo");
        }
        itemRepository.update(t);
        comboRepository.update(t);
    }

    @Override
    public void delete(Long id) {
        if(id == null){
            throw new InvalidDeleteOperationException("Não é possível deletar Combo nulo.");
        }
        comboRepository.delete(id);
        itemRepository.delete(id);
    }
    
    @Override
    public Combo getById(Long id) {
        Combo c = comboRepository.getById(id);
        mapCombo(itemRepository.getById(id), c);
        return c;
    }

    @Override
    public List<Combo> getAll() {
        List<Combo> listaCombos = comboRepository.getAll();
        listaCombos.forEach((c) -> {mapCombo(itemRepository.getById(c.getId()), c);});
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

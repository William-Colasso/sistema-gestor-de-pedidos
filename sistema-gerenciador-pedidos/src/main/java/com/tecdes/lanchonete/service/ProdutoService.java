package com.tecdes.lanchonete.service;

import java.util.List;

import com.tecdes.lanchonete.exception.InvalidDeleteOperationException;
import com.tecdes.lanchonete.exception.InvalidFieldException;
import com.tecdes.lanchonete.exception.InvalidIdException;
import com.tecdes.lanchonete.generalinterfaces.crud.Crud;
import com.tecdes.lanchonete.model.entity.CategoriaProduto;
import com.tecdes.lanchonete.model.entity.Item;
import com.tecdes.lanchonete.model.entity.Produto;
import com.tecdes.lanchonete.model.enums.TipoItem;
import com.tecdes.lanchonete.repository.implementation.IItemRepository;
import com.tecdes.lanchonete.repository.implementation.IProdutoRepository;

public class ProdutoService implements Crud<Produto> {

    private final IProdutoRepository iProdutoRepository;
    private final IItemRepository iItemRepository;
    private final MidiaService midiaService;

    public ProdutoService(IProdutoRepository iProdutoRepository, IItemRepository iItemRepository, MidiaService midiaService) {
        this.iProdutoRepository = iProdutoRepository;
        this.iItemRepository = iItemRepository;
        this.midiaService = midiaService;
    }

    @Override
    public Produto create(Produto t) {
        if(t.getNome() == null){
            throw new InvalidFieldException("Nome do produto não pode ser nulo");
        }
        if(t.getDescricao() == null){
            throw new InvalidFieldException("Descrição do produto não pode ser nulo");
        }
        if(t.getTipoItem() == null){
            t.setTipoItem(TipoItem.PRODUTO);
        }
        if(t.getValor() <= 0){
            throw new InvalidFieldException("Valor do produto não pode ser negativo");
        }
        if(t.getCategoria() == null){
            throw new InvalidFieldException("Categoria do produto não pode ser nula");
        }
        if(t.getDataCriacao() == null){
            throw new InvalidFieldException("Data de criação do produto não pode ser nula");
        }
        t.setId(iItemRepository.create(t).getId());
        return iProdutoRepository.create(t);
    }

    @Override
    public Produto getById(Long id) {
        Produto p = iProdutoRepository.getById(id);
        mapProduto(iItemRepository.getById(id), p);
        return p;
    }

    @Override
    public List<Produto> getAll() {
        List<Produto> listaProdutos = iProdutoRepository.getAll();
        listaProdutos.forEach((p) -> {mapProduto(iItemRepository.getById(p.getId()), p);});
        return listaProdutos;
    }

    @Override
    public void update(Produto t) {
        if(t.getId() == null){
            throw new InvalidIdException("Não é possível deletar Produto nulo");
        }
        iItemRepository.update(t);
        iProdutoRepository.update(t);
    }

    @Override
    public void delete(Long id) {
        if(id == null){
            throw new InvalidDeleteOperationException("Não é possível deletar Produto nulo.");
        }
        iProdutoRepository.delete(id);
        iItemRepository.delete(id);
    }

    public List<Produto> getByCategoriaProduto(CategoriaProduto categoriaProduto){
        List<Produto> listaProdutos = iProdutoRepository.getByCategoria(categoriaProduto);
        listaProdutos.forEach((p) -> {mapProduto(iItemRepository.getById(p.getId()), p);});
        return listaProdutos;
    }


    private Produto mapProduto(Item item, Produto prod){
        prod.setId(item.getId());
        prod.setNome(item.getNome());
        prod.setTipoItem(item.getTipoItem());
        prod.setStatusAtivo(item.getStatusAtivo());
        prod.setDataCriacao(item.getDataCriacao());
        prod.setDescricao(item.getDescricao());
        prod.setPedidos(item.getPedidos());
        prod.setQuantidade(item.getQuantidade());
        prod.setMidias(midiaService.getMidiasByIdItem(prod.getId()));
        return prod;
    }
}

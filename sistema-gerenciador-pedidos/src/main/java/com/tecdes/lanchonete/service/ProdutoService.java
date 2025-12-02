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
import com.tecdes.lanchonete.repository.interfaces.ItemRepository;
import com.tecdes.lanchonete.repository.interfaces.ProdutoRepository;

public class ProdutoService implements Crud<Produto> {

    private final ProdutoRepository produtoRepository;
    private final ItemRepository itemRepository;
    private final MidiaService midiaService;

    public ProdutoService(ProdutoRepository produtoRepository, ItemRepository itemRepository, MidiaService midiaService) {
        this.produtoRepository = produtoRepository;
        this.itemRepository = itemRepository;
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
        t.setId(itemRepository.create(t).getId());
        return produtoRepository.create(t);
    }

    @Override
    public Produto getById(Long id) {
        Produto p = produtoRepository.getById(id);
        mapProduto(itemRepository.getById(id), p);
        return p;
    }

    @Override
    public List<Produto> getAll() {
        List<Produto> listaProdutos = produtoRepository.getAll();
        listaProdutos.forEach((p) -> {mapProduto(itemRepository.getById(p.getId()), p);});
        return listaProdutos;
    }

    @Override
    public void update(Produto t) {
        if(t.getId() == null){
            throw new InvalidIdException("Não é possível deletar Produto nulo");
        }
        itemRepository.update(t);
        produtoRepository.update(t);
    }

    @Override
    public void delete(Long id) {
        if(id == null){
            throw new InvalidDeleteOperationException("Não é possível deletar Produto nulo.");
        }
        produtoRepository.delete(id);
        itemRepository.delete(id);
    }

    public List<Produto> getByCategoriaProduto(CategoriaProduto categoriaProduto){
        List<Produto> listaProdutos = produtoRepository.getByCategoria(categoriaProduto);
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

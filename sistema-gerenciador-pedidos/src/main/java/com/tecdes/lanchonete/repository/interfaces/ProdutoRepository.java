package com.tecdes.lanchonete.repository.interfaces;

import java.util.List;

import com.tecdes.lanchonete.generalinterfaces.crud.Crud;
import com.tecdes.lanchonete.model.entity.CategoriaProduto;
import com.tecdes.lanchonete.model.entity.Produto;


public interface ProdutoRepository extends Crud<Produto> {
    List<Produto> getByCategoria(CategoriaProduto categoriaProduto);
}

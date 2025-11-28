package com.tecdes.lanchonete.model.entity;

import java.sql.Date;
import java.util.List;

import com.tecdes.lanchonete.model.enums.TipoItem;

public class Combo extends Item {
    
    private int desconto;
    private List<Produto> produtos;


    public Combo() {
    }

    public Combo(Long id, String nome, double valor, String descricao, TipoItem tipoItem, Date dataCriacao, int statusAtivo,
            List<Pedido> pedidos, int desconto, List<Produto> produtos, List<Midia> midias) {
        super(id, nome, valor, descricao, tipoItem, dataCriacao, statusAtivo, pedidos, midias);
        this.desconto = desconto;
        this.produtos = produtos;
    }
    public int getDesconto() {
        return desconto;
    }
    public void setDesconto(int desconto) {
        this.desconto = desconto;
    }
    public List<Produto> getProdutos() {
        return produtos;
    }
    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }


}

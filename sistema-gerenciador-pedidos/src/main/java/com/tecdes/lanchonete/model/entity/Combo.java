package com.tecdes.lanchonete.model.entity;

import java.util.List;

public class Combo extends Item {
    
    private int desconto;
    private List<Produto> produtos;




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

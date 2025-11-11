package com.tecdes.lanchonete.model.entity;

import com.tecdes.lanchonete.model.enums.TipoProduto;

public class Produto extends Item {

    private double valor;
    private TipoProduto tipoProduto;
    private CategoriaProduto categoria;



    

    public Produto(double valor, TipoProduto tipoProduto, CategoriaProduto categoria) {
        this.valor = valor;
        this.tipoProduto = tipoProduto;
        this.categoria = categoria;
    }

    public Produto() {
    }




    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public TipoProduto getTipoProduto() {
        return tipoProduto;
    }

    public void setTipoProduto(TipoProduto tipoProduto) {
        this.tipoProduto = tipoProduto;
    }

    public CategoriaProduto getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaProduto categoria) {
        this.categoria = categoria;
    }

}

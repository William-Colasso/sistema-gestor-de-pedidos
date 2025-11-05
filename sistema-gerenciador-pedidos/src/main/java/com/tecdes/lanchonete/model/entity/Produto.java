package com.tecdes.lanchonete.model.entity;

import com.tecdes.lanchonete.model.enums.TipoProduto;

public class Produto {

    private Long id;
    private String nome;
    private double preco;
    private TipoProduto tipoProduto;

    public Produto() {
    }

    public Produto(Long id, String nome, double preco, TipoProduto tipoProduto) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.tipoProduto = tipoProduto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public TipoProduto getTipoProduto() {
        return tipoProduto;
    }

    public void setTipoProduto(TipoProduto tipoProduto) {
        this.tipoProduto = tipoProduto;
    }
}

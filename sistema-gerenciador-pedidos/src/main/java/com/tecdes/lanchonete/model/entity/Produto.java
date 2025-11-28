package com.tecdes.lanchonete.model.entity;

import java.util.List;

public class Produto extends Item {

 
    private CategoriaProduto categoria;
    private List<Combo> combos;


    public Produto(double valor, CategoriaProduto categoria, List<Combo> combos) {
        this.categoria = categoria;
        this.combos = combos;
    }

    public Produto() {
    }

    public CategoriaProduto getCategoria() {
        return categoria;
    }
    public void setCategoria(CategoriaProduto categoria) {
        this.categoria = categoria;
    }
    public List<Combo> getCombos() {
        return combos;
    }
    public void setCombos(List<Combo> combos) {
        this.combos = combos;
    }
}

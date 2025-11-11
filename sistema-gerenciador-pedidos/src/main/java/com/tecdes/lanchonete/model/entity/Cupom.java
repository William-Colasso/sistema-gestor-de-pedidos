package com.tecdes.lanchonete.model.entity;

public class Cupom {



    private Long id;
    private String nome;
    private int valorDesconto;
    private String descricao;
    private boolean valido;
    private Parceiro parceiro;



    
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
    public int getValorDesconto() {
        return valorDesconto;
    }
    public void setValorDesconto(int valorDesconto) {
        this.valorDesconto = valorDesconto;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public boolean isValido() {
        return valido;
    }
    public void setValido(boolean valido) {
        this.valido = valido;
    }
    public Parceiro getParceiro() {
        return parceiro;
    }
    public void setParceiro(Parceiro parceiro) {
        this.parceiro = parceiro;
    }

}

package com.tecdes.lanchonete.model.entity;

import java.util.List;

public class Cupom {

    private Long id;
    private String nome;
    private int valorDesconto;
    private String descricao;
    private int valido;
    private Parceiro parceiro;
    private List<Cliente> clientes;

    public Cupom() {
    }
    public Cupom(Long id, String nome, int valorDesconto, String descricao, int valido, Parceiro parceiro,
            List<Cliente> clientes) {
        this.id = id;
        this.nome = nome;
        this.valorDesconto = valorDesconto;
        this.descricao = descricao;
        this.valido = valido;
        this.parceiro = parceiro;
        this.clientes = clientes;
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
    public Parceiro getParceiro() {
        return parceiro;
    }
    public void setParceiro(Parceiro parceiro) {
        this.parceiro = parceiro;
    }
    public int getValido() {
        return valido;
    }
    public void setValido(int valido) {
        this.valido = valido;
    }
    public List<Cliente> getClientes() {
        return clientes;
    }
    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }
}

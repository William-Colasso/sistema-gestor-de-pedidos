package com.tecdes.lanchonete.model.entity;

import java.sql.Date;
import java.util.List;

public class Cliente {

    private Long id;
    private String nome;
    private String telefone;
    private String cpf;
    private Date dataRegistro;
    private List<Cupom> cupons;

    public Cliente() {
    }
    public Cliente(Long id, String nome, String telefone, String cpf, Date dataRegistro, List<Cupom> cupons) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.cpf = cpf;
        this.dataRegistro = dataRegistro;
        this.cupons = cupons;
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
    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public Date getDataRegistro() {
        return dataRegistro;
    }
    public void setDataRegistro(Date dataRegistro) {
        this.dataRegistro = dataRegistro;
    }
    public List<Cupom> getCupons() {
        return cupons;
    }
    public void setCupons(List<Cupom> cupons) {
        this.cupons = cupons;
    }
}

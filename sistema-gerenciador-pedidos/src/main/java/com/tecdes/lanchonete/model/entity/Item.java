package com.tecdes.lanchonete.model.entity;

import java.sql.Date;

import com.tecdes.lanchonete.model.enums.TipoItem;

public abstract class Item {
    private Long id;
    private String nome;
    private String descricao;
    private TipoItem tipoItem;
    private Date dataCriacao;
    private boolean ativo;


    
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
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public TipoItem getTipoItem() {
        return tipoItem;
    }
    public void setTipoItem(TipoItem tipoItem) {
        this.tipoItem = tipoItem;
    }
    public Date getDataCriacao() {
        return dataCriacao;
    }
    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
    public boolean isAtivo() {
        return ativo;
    }
    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}

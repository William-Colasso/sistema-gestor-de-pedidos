package com.tecdes.lanchonete.model.entity;

import com.tecdes.lanchonete.model.enums.TipoMidia;

public class Midia {
    private Long id;
    private Long idItem;
    private String descricao;
    private byte[] arquivo;
    private TipoMidia tipo;

    
    public Midia() {
    }
    public Midia(Long id, Long idItem, String descricao, byte[] arquivo, TipoMidia tipo) {
        this.id = id;
        this.idItem = idItem;
        this.descricao = descricao;
        this.arquivo = arquivo;
        this.tipo = tipo;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getIdItem() {
        return idItem;
    }
    public void setIdItem(Long idItem) {
        this.idItem = idItem;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public byte[] getArquivo() {
        return arquivo;
    }
    public void setArquivo(byte[] arquivo) {
        this.arquivo = arquivo;
    }
    public TipoMidia getTipo() {
        return tipo;
    }
    public void setTipo(TipoMidia tipo) {
        this.tipo = tipo;
    }
}

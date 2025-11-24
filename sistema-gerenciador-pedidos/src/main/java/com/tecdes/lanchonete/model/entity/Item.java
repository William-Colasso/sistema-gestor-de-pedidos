package com.tecdes.lanchonete.model.entity;

import java.sql.Date;
import java.util.List;

import com.tecdes.lanchonete.model.enums.TipoItem;

public abstract class Item {
    private Long id;
    private String nome;
    private String descricao;
    private TipoItem tipoItem;
    private Date dataCriacao;
    private int statusAtivo;
    private List<Pedido> pedidos;
    private int quantidade;

    public Item() {
    }
    public Item(Long id, String nome, String descricao, TipoItem tipoItem, Date dataCriacao, int statusAtivo,
            List<Pedido> pedidos) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.tipoItem = tipoItem;
        this.dataCriacao = dataCriacao;
        this.statusAtivo = statusAtivo;
        this.pedidos = pedidos;
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
    public int getStatusAtivo() {
        return statusAtivo;
    }
    public void setStatusAtivo(int ativo) {
        this.statusAtivo = ativo;
    }
    public List<Pedido> getPedidos() {
        return pedidos;
    }
    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }
    public int getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}

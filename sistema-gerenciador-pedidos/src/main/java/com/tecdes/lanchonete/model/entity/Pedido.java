package com.tecdes.lanchonete.model.entity;

import java.sql.Date;
import java.util.List;

public class Pedido {

    private Long id;
    private Funcionario funcionario;
    private Pagamento pagamento;
    private Cliente cliente;
    private String nomeCliente;
    private Cupom cupom;
    private Date dataPedido;
    private char statusPedido;

    private List<Item> itens;



    public Pedido(Long id, Funcionario funcionario, Pagamento pagamento, Cliente cliente, String nomeCliente,
            Cupom cupom, Date dataPedido, char statusPedido, List<Item> itens) {
        this.id = id;
        this.funcionario = funcionario;
        this.pagamento = pagamento;
        this.cliente = cliente;
        this.nomeCliente = nomeCliente;
        this.cupom = cupom;
        this.dataPedido = dataPedido;
        this.statusPedido = statusPedido;
        this.itens = itens;
    }

    public Pedido() {
    }
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Funcionario getFuncionario() {
        return funcionario;
    }
    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }
    public Pagamento getPagamento() {
        return pagamento;
    }
    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }
    public Cliente getCliente() {
        return cliente;
    }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    public String getNomeCliente() {
        return nomeCliente;
    }
    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }
    public Cupom getCupom() {
        return cupom;
    }
    public void setCupom(Cupom cupom) {
        this.cupom = cupom;
    }
    public Date getDataPedido() {
        return dataPedido;
    }
    public void setDataPedido(Date dataPedido) {
        this.dataPedido = dataPedido;
    }
    public char getStatusPedido() {
        return statusPedido;
    }
    public void setStatusPedido(char statusPedido) {
        this.statusPedido = statusPedido;
    }
    public List<Item> getItens() {
        return itens;
    }
    public void setItens(List<Item> itens) {
        this.itens = itens;
    }
    

}

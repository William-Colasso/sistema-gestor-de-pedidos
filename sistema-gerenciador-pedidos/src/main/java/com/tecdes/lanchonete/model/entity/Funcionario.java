package com.tecdes.lanchonete.model.entity;

import java.sql.Date;

public class Funcionario {


    private Long id;
    private String nome;
    private Date dataNascimento;
    private String cpf;
    private Gerente gerente;

    

    public Funcionario() {
    }

    public Funcionario(Long id, String nome, Date dataNascimento, String cpf, Gerente gerente) {
        this.id = id;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.cpf = cpf;
        this.gerente = gerente;
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

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    
    public Gerente getGerente() {
        return gerente;
    }

    public void setGerente(Gerente gerente) {
        this.gerente = gerente;
    }


    
}

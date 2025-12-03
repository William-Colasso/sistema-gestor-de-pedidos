package com.tecdes.lanchonete.model.entity;

import java.sql.Date;

public class Gerente extends Funcionario {
    
    private String login;
    private String senha;


    public Gerente() {
    }
    
    public Gerente(Long id, String nome, Date dataNascimento, String cpf, Gerente gerente, String login, String senha) {
        super(id, nome, dataNascimento, cpf, gerente);
        this.login = login;
        this.senha = senha;
    }

    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
}

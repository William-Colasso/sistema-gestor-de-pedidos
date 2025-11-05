package com.tecdes.lanchonete.model.entity;

import java.sql.Date;

public class Caixa extends Funcionario {

    public Caixa() {
    }

    public Caixa(int id, String nome, Date dataNascimento, String cpf, String senha) {
        super(id, nome, dataNascimento, cpf, senha);
    }
    
}

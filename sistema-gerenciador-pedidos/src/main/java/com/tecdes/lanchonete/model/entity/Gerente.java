package com.tecdes.lanchonete.model.entity;

import java.sql.Date;

public class Gerente extends Funcionario {

    public Gerente() {
    }

    public Gerente(int id, String nome, Date dataNascimento, String cpf, String senha) {
        super(id, nome, dataNascimento, cpf, senha);
    }
    
}

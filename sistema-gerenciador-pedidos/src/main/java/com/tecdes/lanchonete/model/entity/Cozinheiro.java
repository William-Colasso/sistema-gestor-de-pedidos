package com.tecdes.lanchonete.model.entity;

import java.sql.Date;

public class Cozinheiro extends Funcionario {

    public Cozinheiro() {
    }

    public Cozinheiro(int id, String nome, Date dataNascimento, String cpf, String senha) {
        super(id, nome, dataNascimento, cpf, senha);
    }
    
}

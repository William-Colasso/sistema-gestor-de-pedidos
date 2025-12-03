package com.tecdes.lanchonete.model.enums;

public enum StatusPedido {
    PREPARANDO('P'),
    ENTREGUE('E'),
    CANCELADO('C'),
    DEVOLVIDO('D');


    private char value;

    
    StatusPedido(char value) {
        this.value = value;
    }

    public char getValue(){
        return this.value;
    }


}

package com.tecdes.lanchonete.model.enums;

public enum TipoMidia {

    IMAGEM('I'),
    VIDEO('V'),
    AUDIO('A');

    private char value;

    
    TipoMidia(char value) {
        this.value = value;
    }

    public char getValue(){
        return this.value;
    }


}

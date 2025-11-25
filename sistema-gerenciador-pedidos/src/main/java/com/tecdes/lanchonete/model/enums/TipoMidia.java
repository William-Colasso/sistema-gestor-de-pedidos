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

    public static TipoMidia fromValue(char value) {
        for (TipoMidia t : TipoMidia.values()) {
            if (t.value == value) return t;
        }
        throw new IllegalArgumentException("Valor inválido para TipoMidia: " + value);
    }
}

package com.tecdes.lanchonete.model.enums;

public enum TipoItem {
    
    PRODUTO('P'),
    COMBO('C');

    private char value;

    
    TipoItem(char value) {
        this.value = value;
    }

    public char getValue(){
        return this.value;
    }

    public static TipoItem fromValue(char value) {
        for (TipoItem t : TipoItem.values()) {
            if (t.value == value) return t;
        }
        throw new IllegalArgumentException("Valor inválido para TipoItem: " + value);
    }
}

package com.tecdes.lanchonete.model.enums;

public enum TipoItem {
    
    PRODUTO('P'),
    COMBO('C');

    private int value;

    
    TipoItem(int value) {
        this.value = value;
    }

    public int getValue(){
        return this.value;
    }


}

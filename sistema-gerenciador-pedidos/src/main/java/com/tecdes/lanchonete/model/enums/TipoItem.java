package com.tecdes.lanchonete.model.enums;

import com.tecdes.lanchonete.model.entity.Combo;
import com.tecdes.lanchonete.model.entity.Item;
import com.tecdes.lanchonete.model.entity.Produto;

public enum TipoItem {
    
    PRODUTO('P') {
        @Override
        public Item create() { return new Produto(); }
    },
    COMBO('C') {
        @Override
        public Item create() { return new Combo(); }
    };

    private char value;

    
    TipoItem(char value) {
        this.value = value;
    }

    public char getValue(){
        return this.value;
    }

    public abstract Item create();

    public static TipoItem fromValue(char value) {
        for (TipoItem t : TipoItem.values()) {
            if (t.value == value) return t;
        }
        throw new IllegalArgumentException("Valor inválido para TipoItem: " + value);
    }
}

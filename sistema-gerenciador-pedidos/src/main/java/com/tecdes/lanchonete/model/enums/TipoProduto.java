package com.tecdes.lanchonete.model.enums;

public enum TipoProduto {


    LANCHE(1),
    PRATO_FEITO(2),
    PORCAO(3),
    OUTRO(4);
   

    private int value;

    
    TipoProduto(int value) {
        this.value = value;
    }

    public int getValue(){
        return this.value;
    }


    // Método que retorna o enum a partir do valor
    public static TipoProduto fromValue(int value) {
        for (TipoProduto tipo : values()) {
            if (tipo.value == value) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Valor inválido para TipoProduto: " + value);
    }




}

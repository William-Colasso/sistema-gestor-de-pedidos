package com.tecdes.lanchonete.view.custom.abstracts;

public abstract class CardLayoutable extends AbstractFrame {

    public CardLayoutable(String nameFrame){
        super(nameFrame);
    }


    public abstract  void showPanel(String namePanel);
}

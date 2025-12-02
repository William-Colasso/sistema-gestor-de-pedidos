package com.tecdes.lanchonete.view.logical.abstracts.card;


import com.tecdes.lanchonete.view.logical.custom.LayeredOverlayPane;
import com.tecdes.lanchonete.view.logical.interfaces.Card;
import com.tecdes.lanchonete.view.logical.interfaces.CardLayoutable;

import java.awt.Component;
import java.util.List;


public class LayeredOverlayCard extends  LayeredOverlayPane implements Card {

    

    private  final CardLayoutable cardLayoutbleFrame;
    private  final String cardName;



    
    public LayeredOverlayCard(CardLayoutable cardLayoutbleFrame, String cardName){
        super();
        this.cardLayoutbleFrame = cardLayoutbleFrame;
        this.cardName = cardName;
    }

    @Override
    public CardLayoutable getCardLayoutbleFrame() {
        return this.cardLayoutbleFrame;
    }

    @Override
    public String getCardName() {
        return  this.cardName;
    }



}

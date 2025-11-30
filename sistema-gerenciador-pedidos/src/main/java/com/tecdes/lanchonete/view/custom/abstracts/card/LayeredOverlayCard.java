package com.tecdes.lanchonete.view.custom.abstracts.card;

import com.tecdes.lanchonete.view.custom.abstracts.CardLayoutbleFrame;
import com.tecdes.lanchonete.view.custom.interfaces.Card;
import com.tecdes.lanchonete.view.custom.util.LayeredOverlayPane;

public class LayeredOverlayCard extends  LayeredOverlayPane implements Card {

    

    private  final CardLayoutbleFrame cardLayoutbleFrame;
    private  final String cardName;



    
    public LayeredOverlayCard(CardLayoutbleFrame cardLayoutbleFrame, String cardName){
        super();
        this.cardLayoutbleFrame = cardLayoutbleFrame;
        this.cardName = cardName;
    }

    @Override
    public CardLayoutbleFrame getCardLayoutbleFrame() {
        return this.cardLayoutbleFrame;
    }

    @Override
    public String getCardName() {
        return  this.cardName;
    }

}

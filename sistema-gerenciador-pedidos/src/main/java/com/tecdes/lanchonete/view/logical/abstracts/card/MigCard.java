package com.tecdes.lanchonete.view.logical.abstracts.card;

import com.tecdes.lanchonete.view.logical.abstracts.DeckFrame;
import com.tecdes.lanchonete.view.logical.custom.panel.MigPanel;
import com.tecdes.lanchonete.view.logical.interfaces.Card;
import com.tecdes.lanchonete.view.logical.interfaces.CardLayoutable;



public class MigCard extends  MigPanel implements Card {

    protected  final String cardName;
    protected  final CardLayoutable cardLayoutbleFrame;

    


    public MigCard(String layoutConstraints, DeckFrame cardLayoutbleFrame, String cardName) {
        super(layoutConstraints);
        this.cardName = cardName;
        this.cardLayoutbleFrame = cardLayoutbleFrame;
    }

     public MigCard(String layoutConstraints, String columnConstraints, String cardName, DeckFrame cardLayoutbleFrame) {
        super(layoutConstraints, columnConstraints);
        this.cardName = cardName;
        this.cardLayoutbleFrame = cardLayoutbleFrame;
        
    }

    public MigCard(String layoutConstraints, String columnConstraints, String rowConstraints,  String cardName, DeckFrame cardLayoutbleFrame) {
        super(layoutConstraints, columnConstraints, rowConstraints);
        this.cardName = cardName;
        this.cardLayoutbleFrame = cardLayoutbleFrame;
        
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

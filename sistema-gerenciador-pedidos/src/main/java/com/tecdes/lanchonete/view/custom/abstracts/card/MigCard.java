package com.tecdes.lanchonete.view.custom.abstracts.card;

import com.tecdes.lanchonete.view.custom.MigPanel;
import com.tecdes.lanchonete.view.custom.abstracts.CardLayoutbleFrame;
import com.tecdes.lanchonete.view.custom.interfaces.Card;

public class MigCard extends  MigPanel implements Card {

    protected  final String cardName;
    protected  final CardLayoutbleFrame cardLayoutbleFrame;



    public MigCard(String layoutConstraints, CardLayoutbleFrame cardLayoutbleFrame, String cardName) {
        super(layoutConstraints);
        this.cardName = cardName;
        this.cardLayoutbleFrame = cardLayoutbleFrame;
    }

     public MigCard(String layoutConstraints, String columnConstraints, String cardName, CardLayoutbleFrame cardLayoutbleFrame) {
        super(layoutConstraints, columnConstraints);
        this.cardName = cardName;
        this.cardLayoutbleFrame = cardLayoutbleFrame;
        
    }

    public MigCard(String layoutConstraints, String columnConstraints, String rowConstraints,  String cardName, CardLayoutbleFrame cardLayoutbleFrame) {
        super(layoutConstraints, columnConstraints, rowConstraints);
        this.cardName = cardName;
        this.cardLayoutbleFrame = cardLayoutbleFrame;
        
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

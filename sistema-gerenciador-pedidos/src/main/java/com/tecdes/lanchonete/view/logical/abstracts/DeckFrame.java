package com.tecdes.lanchonete.view.logical.abstracts;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.JPanel;

import com.tecdes.lanchonete.view.logical.interfaces.Card;
import com.tecdes.lanchonete.view.logical.interfaces.CardLayoutable;


public abstract  class DeckFrame extends  AbstractFrame implements  CardLayoutable {


    protected CardLayout cardLayout = new CardLayout();

    protected JPanel root =  new JPanel();

    public DeckFrame(String title) {
        super(title);
        setLayout(new GridLayout(1,1));
        
        
        root.setLayout(cardLayout);
        add(root);
    }


    @Override
    public void showCard(String name){
        cardLayout.show(root,name);
    }

    @Override
    public void showCard(Card card){
        cardLayout.show(root, card.getCardName());
    };

    @Override
    public void addCard(Card card){
        root.add((Component) card, card.getCardName());
    };


}

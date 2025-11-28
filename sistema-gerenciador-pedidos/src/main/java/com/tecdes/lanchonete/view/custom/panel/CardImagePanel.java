package com.tecdes.lanchonete.view.custom.panel;

import javax.swing.JPanel;

import com.tecdes.lanchonete.view.custom.MigPanel;

public class CardImagePanel extends MigPanel{

    public CardImagePanel(ImagePanel imagePanel, JPanel bellowPanel){
        super("wrap","[grow]","[75%][25%]");

        setRoundedBorder(14);
        add(imagePanel, "grow");
        add(bellowPanel, "grow");


        
    }

}

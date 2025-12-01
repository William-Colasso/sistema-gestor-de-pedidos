package com.tecdes.lanchonete.view.logical.custom.panel;




import javax.swing.JPanel;



public class CardImagePanel extends MigPanel{

    public CardImagePanel(ImagePanel imagePanel, JPanel bellowPanel){
        super("wrap","[grow]","[75%][25%]");

        setRoundedBorder(14);
        add(imagePanel, "grow");
        add(bellowPanel, "grow");


        
    }

}

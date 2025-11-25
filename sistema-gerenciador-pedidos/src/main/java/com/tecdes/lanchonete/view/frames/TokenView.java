package com.tecdes.lanchonete.view.frames;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.tecdes.lanchonete.view.AbstractFrame;

import net.miginfocom.swing.MigLayout;

public final class TokenView extends AbstractFrame {



    public TokenView() {
        super("Token view");
        initComponents();
    }

    @Override
    protected void initComponents() {
        setLayout(new MigLayout("wrap","[grow]", "[grow]"));

        JPanel marketPane = new JPanel();

        instantiateMarket(marketPane);

        

        add(marketPane, "grow");

    }

    private void instantiateMarket(JPanel market) {
        
        market.setLayout(new MigLayout("wrap", "[25%][75%]", "[grow]"));

        JButton b = new JButton("abacate");


        
        JScrollPane categoriesScrollPane = new JScrollPane(new JButton("Livro"));

        market.add(b, "grow");
        market.add(categoriesScrollPane, "grow");

    }

}

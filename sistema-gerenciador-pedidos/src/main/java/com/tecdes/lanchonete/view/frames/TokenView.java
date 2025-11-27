package com.tecdes.lanchonete.view.frames;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.Color;
import com.tecdes.lanchonete.view.AbstractFrame;
import com.tecdes.lanchonete.view.custom.MigPanel;
import com.tecdes.lanchonete.view.custom.util.ColorTheme;
import com.tecdes.lanchonete.view.custom.util.RoundedBorder;

import net.miginfocom.swing.MigLayout;

public final class TokenView extends AbstractFrame {

    public TokenView() {
        super("Token view");
        initComponents();
    }

    @Override
    protected void initComponents() {
        setLayout(new MigLayout("wrap, insets 0", "[grow]", "[grow]"));

        MigPanel marketPane = new MigPanel("wrap, insets 5", "[25%][75%]", "[grow]");

        instantiateMarket(marketPane);

        add(marketPane, "grow");

    }

    private void instantiateMarket(JPanel market) {

        market.setBackground(ColorTheme.PRIMARY);

        MigPanel categoriesPanel = new MigPanel(
                "wrap, insets 5",
                "[grow]",
                "[grow,fill]");
        JScrollPane categoriesScrollPane = new JScrollPane(categoriesPanel);

       
            

        categoriesPanel.setBackground(ColorTheme.WHITE);
        for (int i = 0; i <= 50; i++) {
            JButton b = new JButton("Test Button " + i);
            categoriesPanel.add(b, "grow");
        }

        MigPanel panelRight = new MigPanel("wrap, insets 5", "[grow]", "[90%][10%]");

        MigPanel itensPanel = new MigPanel("wrap 4, insets 5", "[grow,fill]", "[grow,fill]");
        JScrollPane itensScrollPanel = new JScrollPane(itensPanel);

        panelRight.add(itensScrollPanel, "grow");

        MigPanel selectedItemPanel = new MigPanel("gap 25%, insets 5", "[grow]", "[grow]");
        selectedItemPanel.setBackground(ColorTheme.CONSTRAST);

        panelRight.add(selectedItemPanel, "grow");

        market.add(categoriesScrollPane, "grow");
        market.add(panelRight, "grow");

    }

}

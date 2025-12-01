package com.tecdes.lanchonete.view.physical.panel;

import javax.swing.JLabel;

import com.tecdes.lanchonete.view.logical.abstracts.DeckFrame;
import com.tecdes.lanchonete.view.logical.abstracts.DeckPanel;
import com.tecdes.lanchonete.view.logical.abstracts.card.MigCard;
import com.tecdes.lanchonete.view.logical.custom.panel.ImagePanel;
import com.tecdes.lanchonete.view.logical.custom.panel.MigPanel;
import com.tecdes.lanchonete.view.logical.custom.util.ImageService;
import com.tecdes.lanchonete.view.logical.custom.util.color.ColorTheme;

public class AdminCard extends MigCard {

    private final MigPanel menu;
    private final DeckPanel deck;
    private final ImageService imgS;
    private final ColorTheme colorTheme;

    public AdminCard(DeckFrame cardLayoutableFrame, String cardName, ImageService imgS, ColorTheme colorTheme) {
        super("", "[20%]5%[75%]", "[grow]", cardName, cardLayoutableFrame);
        this.colorTheme = colorTheme;
        this.imgS = imgS;

        menu = new MigPanel("wrap, insets 5", "[grow, fill]", "");

        instantiateMenu();

        deck = new DeckPanel();
        instantiateDeck();

        add(menu, "grow");

    }

    private void instantiateMenu() {

        String[] labelTexts = { "Cliente", "Funcionário", "Gerente", "Produto", "Categorias", "Combo", "Pedido",
                "Cupom",
                "Parceiro" };

        for (String nameMenuItem : labelTexts) {
            MigPanel mgPanel = new MigPanel("", "[20%]10%[60%][10%]", "[grow]");

            mgPanel.add(new ImagePanel(imgS.getStaticImageByName("admin/" + nameMenuItem + ".png")), "grow");

            JLabel labelNameItemMenu = new JLabel(nameMenuItem);
            mgPanel.setOpaque(true);
            mgPanel.setRoundedBorder(14, colorTheme.getShadowSoft());
            mgPanel.setBackground(colorTheme.getWhite());
            mgPanel.setDefaultBackground(colorTheme.getWhite());
            mgPanel.setHoverBackground(colorTheme.getShadowSoft());

            mgPanel.setAction(()->{
                System.out.println("Menu item clicado: "+nameMenuItem);
            });
            mgPanel.add(labelNameItemMenu, "grow");
            menu.add(mgPanel, "grow");

        }

    }

    private void instantiateDeck() {

    }

}

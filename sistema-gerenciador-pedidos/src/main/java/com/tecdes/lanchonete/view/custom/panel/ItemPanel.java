package com.tecdes.lanchonete.view.custom.panel;

import javax.swing.JLabel;

import com.tecdes.lanchonete.model.entity.Item;
import com.tecdes.lanchonete.view.custom.MigPanel;
import com.tecdes.lanchonete.view.custom.util.ImageService;

public class ItemPanel extends CardImagePanel {

    public ItemPanel(Item item, ImageService imageService) {
        super(new ImagePanel(item.getMidias().get(0).getArquivo(), imageService), new MigPanel(
                "", "[30%][40%][30%]", "[grow]", new JLabel(String.valueOf(item.getValor())),
                new JLabel(item.getNome())));

    }

}

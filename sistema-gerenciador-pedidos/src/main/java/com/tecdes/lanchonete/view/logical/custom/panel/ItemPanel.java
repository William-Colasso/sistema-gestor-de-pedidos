package com.tecdes.lanchonete.view.logical.custom.panel;

import javax.swing.JLabel;

import com.tecdes.lanchonete.model.entity.Item;
import com.tecdes.lanchonete.view.logical.custom.util.ImageService;
public class ItemPanel extends CardImagePanel {

    public ItemPanel(Item item, ImageService imageService) {

        ImagePanel imagePanel = new ImagePanel(
            item.getMidia().getArquivo(),
            imageService
        );

        MigPanel info = new MigPanel(
            "insets 5, gapx 5", 
            "[grow][right]", 
            "[grow]"
        );

        info.add(new JLabel(item.getNome()), "growx");
        info.add(new JLabel("R$ " + item.getValor()), "growx");

        super.setContent(imagePanel, info);
    }
}

package com.tecdes.lanchonete.view.logical.custom.panel;

import javax.swing.JLabel;

import com.tecdes.lanchonete.model.entity.CategoriaProduto;
import com.tecdes.lanchonete.view.logical.custom.util.ImageService;



public class CategoriePanel extends CardImagePanel {

    public CategoriePanel(CategoriaProduto categoriaProduto, ImageService imageService) {
       
        ImagePanel imagePanel = new ImagePanel(
            categoriaProduto.getImagem(),
            imageService
        );

        MigPanel info = new MigPanel(
            "insets 5, align 50% 50%", 
            "[grow][center]", 
            "[grow]"
        );

        info.add(new JLabel(categoriaProduto.getNome()));

        add(imagePanel, "grow");
        add(info, "grow");


    }



    
}

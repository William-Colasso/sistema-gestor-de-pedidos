package com.tecdes.lanchonete.view.custom.panel;

import javax.swing.JLabel;
import com.tecdes.lanchonete.model.entity.CategoriaProduto;
import com.tecdes.lanchonete.view.custom.MigPanel;
import com.tecdes.lanchonete.view.custom.util.ImageService;

public class CategoriePanel extends CardImagePanel {

    public CategoriePanel(CategoriaProduto categoriaProduto, ImageService imageService) {
        super(new ImagePanel(categoriaProduto.getImagem(), imageService),
                new MigPanel("", "[grow]", "[grow]", new JLabel(categoriaProduto.getNome())));
    }



    
}

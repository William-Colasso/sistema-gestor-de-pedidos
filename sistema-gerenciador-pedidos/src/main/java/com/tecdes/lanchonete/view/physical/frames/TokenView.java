package com.tecdes.lanchonete.view.physical.frames;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JScrollPane;

import com.tecdes.lanchonete.controller.CategoriaProdutoController;
import com.tecdes.lanchonete.controller.ComboController;
import com.tecdes.lanchonete.controller.ProdutoController;
import com.tecdes.lanchonete.model.entity.CategoriaProduto;
import com.tecdes.lanchonete.model.entity.Item;
import com.tecdes.lanchonete.view.logical.abstracts.AbstractFrame;
import com.tecdes.lanchonete.view.logical.abstracts.DeckFrame;
import com.tecdes.lanchonete.view.logical.abstracts.card.MigCard;
import com.tecdes.lanchonete.view.logical.custom.panel.CategoriePanel;
import com.tecdes.lanchonete.view.logical.custom.panel.ItemPanel;
import com.tecdes.lanchonete.view.logical.custom.panel.MigPanel;
import com.tecdes.lanchonete.view.logical.custom.util.ImageService;
import com.tecdes.lanchonete.view.logical.custom.util.color.ColorTheme;

import net.miginfocom.swing.MigLayout;

public final class TokenView extends DeckFrame {

    private final ColorTheme colorTheme;
    private final CategoriaProdutoController categoriaProdutoController;
    private final ProdutoController produtoController;
    
    private final ImageService imageService;

    private MigCard marketCard;
    private MigPanel categoriesPanel;
    private MigPanel panelRight;
    private MigPanel itensPanel;
    private MigPanel selectedItemPanel;

    public TokenView(
            ColorTheme colorTheme,
            CategoriaProdutoController categoriaProdutoController,
            ProdutoController produtoController,
            ComboController comboController,
            ImageService imageService) {

        super("Token view");
        this.colorTheme = colorTheme;
        this.categoriaProdutoController = categoriaProdutoController;
        this.produtoController = produtoController;
        
        this.imageService = imageService;

        initComponents();
    }

    @Override
    protected void initComponents() {
        

        marketCard = new MigCard("wrap, insets 5", "[25%][75%]", "[grow]", this);

        instantiateMarket();

        addCard(marketCard);
    }

    private void instantiateMarket() {

        marketCard.setBackground(colorTheme.getPrimary());

        categoriesPanel = new MigPanel("wrap, insets 5", "[grow]", "[grow]");
        categoriesPanel.setBackground(colorTheme.getConstrast());
        categoriesPanel.setDefaultBackground(colorTheme.getConstrast());
        categoriesPanel.setHoverBackground(colorTheme.getConstrast());
        instantiateCategories();

        JScrollPane categoriesScrollPane = new JScrollPane(categoriesPanel);

        panelRight = new MigPanel("wrap, insets 5", "[grow]", "[90%][10%]");
        instantiateRigthPanel();

        marketCard.add(categoriesScrollPane, "grow");
        marketCard.add(panelRight, "grow");
    }

    private void instantiateCategories() {
        List<CategoriaProduto> categoriasProdutos = categoriaProdutoController.getAll();
        JButton buttonCategorias = new JButton("Categorias");
        buttonCategorias.setEnabled(false);
        categoriesPanel.add(buttonCategorias, "grow");
        categoriasProdutos.forEach((categoria) -> {
            CategoriePanel categoriePanel = new CategoriePanel(categoria, imageService);
            categoriePanel.setBackground(colorTheme.getWhite());
            categoriePanel.setDefaultBackground(colorTheme.getWhite());
            categoriePanel.setHoverBackground(colorTheme.getShadowSoft());
            categoriePanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            categoriePanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    fillItensPanel(produtoController.getAll()); 
                }
            });

            categoriesPanel.add(categoriePanel, "grow");
        });
    }

    private void instantiateRigthPanel() {

        // Painel de itens agora é atributo
        itensPanel = new MigPanel("wrap 4, insets 5", "[grow]", "[grow]");

        fillItensPanel(produtoController.getAll());

        JScrollPane itensScrollPanel = new JScrollPane(itensPanel);
        panelRight.add(itensScrollPanel, "grow");

        // Painel do item selecionado agora é atributo
        selectedItemPanel = new MigPanel("gap 25%, insets 5", "[grow]", "[grow]");
        selectedItemPanel.setBackground(colorTheme.getConstrast());

        panelRight.add(selectedItemPanel, "grow");
    }

    private void fillItensPanel(List<? extends Item> itens) {
        itensPanel.removeAll();
        itens.forEach((item) -> {
            ItemPanel  itemPanel = new ItemPanel(item, imageService);
            itemPanel.setBackground(colorTheme.getWhite());
            itemPanel.setDefaultBackground(colorTheme.getWhite());
            itemPanel.setHoverBackground(colorTheme.getShadowSoft());
            itemPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            itensPanel.add(itemPanel, "grow, w 150!, h 150!");
        });
    }

}

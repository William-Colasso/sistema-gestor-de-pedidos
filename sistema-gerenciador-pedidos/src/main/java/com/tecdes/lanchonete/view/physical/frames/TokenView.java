package com.tecdes.lanchonete.view.physical.frames;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JScrollPane;

import com.tecdes.lanchonete.controller.CategoriaProdutoController;
import com.tecdes.lanchonete.controller.ComboController;
import com.tecdes.lanchonete.controller.ProdutoController;
import com.tecdes.lanchonete.model.entity.CategoriaProduto;
import com.tecdes.lanchonete.model.entity.Item;
import com.tecdes.lanchonete.view.logical.abstracts.AbstractFrame;
import com.tecdes.lanchonete.view.logical.custom.panel.CategoriePanel;
import com.tecdes.lanchonete.view.logical.custom.panel.ItemPanel;
import com.tecdes.lanchonete.view.logical.custom.panel.MigPanel;
import com.tecdes.lanchonete.view.logical.custom.util.ImageService;
import com.tecdes.lanchonete.view.logical.custom.util.color.ColorTheme;

import net.miginfocom.swing.MigLayout;

public final class TokenView extends AbstractFrame {

    private final ColorTheme colorTheme;
    private final CategoriaProdutoController categoriaProdutoController;
    private final ProdutoController produtoController;
    private final ComboController comboController;
    private final ImageService imageService;

    private MigPanel marketPane;
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
        this.comboController = comboController;
        this.imageService = imageService;

        initComponents();
    }

    @Override
    protected void initComponents() {

        setLayout(new MigLayout("wrap, insets 0", "[grow]", "[grow]"));

        marketPane = new MigPanel("wrap, insets 5", "[25%][75%]", "[grow]");

        instantiateMarket();

        add(marketPane, "grow");
    }

    private void instantiateMarket() {

        marketPane.setBackground(colorTheme.getPrimary());

        categoriesPanel = new MigPanel("wrap, insets 5", "[grow]", "[grow,fill]");
        categoriesPanel.setBackground(colorTheme.getConstrast());
        instantiateCategories();

        JScrollPane categoriesScrollPane = new JScrollPane(categoriesPanel);

        panelRight = new MigPanel("wrap, insets 5", "[grow]", "[90%][10%]");
        instantiateRigthPanel();

        marketPane.add(categoriesScrollPane, "grow");
        marketPane.add(panelRight, "grow");
    }

    private void instantiateCategories() {
        List<CategoriaProduto> categoriasProdutos = categoriaProdutoController.getAll();

        categoriasProdutos.forEach((categoria) -> {
            CategoriePanel categoriePanel = new CategoriePanel(categoria, imageService);

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
            itensPanel.add(new ItemPanel(item, imageService));
        });
    }

}

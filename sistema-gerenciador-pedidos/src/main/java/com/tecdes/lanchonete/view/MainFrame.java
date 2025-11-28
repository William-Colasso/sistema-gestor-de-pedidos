package com.tecdes.lanchonete.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.tecdes.lanchonete.controller.CategoriaProdutoController;
import com.tecdes.lanchonete.controller.ComboController;
import com.tecdes.lanchonete.controller.ProdutoController;
import com.tecdes.lanchonete.view.custom.RedirectButton;
import com.tecdes.lanchonete.view.custom.util.ImageService;
import com.tecdes.lanchonete.view.custom.util.color.ColorTheme;
import com.tecdes.lanchonete.view.frames.AdminView;
import com.tecdes.lanchonete.view.frames.CheckoutView;
import com.tecdes.lanchonete.view.frames.CookView;
import com.tecdes.lanchonete.view.frames.MenuBoardView;
import com.tecdes.lanchonete.view.frames.TokenView;

public final class MainFrame extends AbstractFrame {

     private final ColorTheme colorTheme;
    private final CategoriaProdutoController categoriaProdutoController;
    private final ProdutoController produtoController;
    private final ComboController comboController;
    private final ImageService imageService;



    public MainFrame(ColorTheme colorTheme, ImageService imageService, CategoriaProdutoController categoriaProdutoController, ComboController comboController, ProdutoController produtoController) {
        super("Escolha a aplicação desejada");
        this.colorTheme = colorTheme;
        this.categoriaProdutoController = categoriaProdutoController;
        this.produtoController = produtoController;
        this.comboController = comboController;
        this.imageService = imageService;
        setLayout(new BorderLayout());
        initComponents();
    }

    @Override
    protected void initComponents() {

        JPanel panel = new JPanel();
        JPanel wrapperPanel = new JPanel();
        wrapperPanel.setLayout(new GridLayout(1, 1));
        panel.setLayout(new GridLayout(6, 1));

        JLabel labelAcessView = new JLabel("Selecione a view desejada:", JLabel.CENTER);

        
        RedirectButton rdBToken = new RedirectButton("TOKEN", new TokenView(colorTheme, categoriaProdutoController, produtoController, comboController, imageService));
        RedirectButton rdBAdmin = new RedirectButton("ADMIN", new AdminView());
        RedirectButton rdBCheckout = new RedirectButton("CHECKOUT", new CheckoutView());
        RedirectButton rdBCook = new RedirectButton("COOK", new CookView());
        RedirectButton rdBMenu = new RedirectButton("MENU", new MenuBoardView());
        
        panel.setAlignmentX(CENTER_ALIGNMENT);
        panel.add(labelAcessView);
        panel.add(rdBToken);
        panel.add(rdBAdmin);
        panel.add(rdBCheckout);
        panel.add(rdBCook);
        panel.add(rdBMenu);
        wrapperPanel.add(panel);

        add(wrapperPanel, BorderLayout.CENTER);

    }

}

package com.tecdes.lanchonete.view.frames;

import java.awt.CardLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;

import com.tecdes.lanchonete.controller.GerenteController;
import com.tecdes.lanchonete.view.custom.abstracts.CardLayoutable;
import com.tecdes.lanchonete.view.custom.panel.AdminPanel;
import com.tecdes.lanchonete.view.custom.panel.LoginPanel;
import com.tecdes.lanchonete.view.custom.util.ImageService;
import com.tecdes.lanchonete.view.custom.util.color.ColorTheme;

public final class AdminView extends CardLayoutable {

    private JPanel root;
    private AdminPanel adminPanel;
    private LoginPanel loginPanel;
    private CardLayout cardLayout;
    private final ColorTheme colorTheme;
    private final GerenteController gerenteController;
    private  final ImageService imageService;

    public AdminView(GerenteController gerenteController, ColorTheme colorTheme, ImageService imageService) {
        super("Admin View");
        this.colorTheme = colorTheme;
        this.gerenteController = gerenteController;
        this.imageService = imageService;

        setLayout(new GridLayout(1, 1));
        initComponents();

    }

    @Override
    protected void initComponents() {
        root = new JPanel();
        adminPanel = new AdminPanel();
        loginPanel = new LoginPanel(gerenteController, this, colorTheme, imageService);
        cardLayout = new CardLayout();
        root.setLayout(cardLayout);

        // add(new RedirectButton("MainFrame", new MainFrame()));

        root.add(loginPanel, "login");
        root.add(adminPanel, "admin");

        add(root);

        showPanel("login");
    }

    @Override
    public void showPanel(String namePanel) {
        cardLayout.show(root, namePanel);
    }

}

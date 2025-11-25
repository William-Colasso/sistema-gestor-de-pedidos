package com.tecdes.lanchonete.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.tecdes.lanchonete.view.custom.RedirectButton;
import com.tecdes.lanchonete.view.frames.AdminView;
import com.tecdes.lanchonete.view.frames.CheckoutView;
import com.tecdes.lanchonete.view.frames.CookView;
import com.tecdes.lanchonete.view.frames.MenuBoardView;
import com.tecdes.lanchonete.view.frames.TokenView;

public final class MainFrame extends AbstractFrame {



    public MainFrame() {
        super("Escolha a aplicação desejada");
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

        
        RedirectButton rdBToken = new RedirectButton("Token", new TokenView());
        RedirectButton rdBAdmin = new RedirectButton("Admin", new AdminView());
        RedirectButton rdBCheckout = new RedirectButton("Checkout", new CheckoutView());
        RedirectButton rdBCook = new RedirectButton("Cook", new CookView());
        RedirectButton rdBMenu = new RedirectButton("Menu", new MenuBoardView());
        
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

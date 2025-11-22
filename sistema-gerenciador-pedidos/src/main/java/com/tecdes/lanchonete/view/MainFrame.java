package com.tecdes.lanchonete.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
        wrapperPanel.setLayout(new GridLayout(1,1));
        panel.setLayout(new GridLayout(6,1));
        panel.setBorder(BorderFactory.createRaisedSoftBevelBorder());

        JLabel labelAcessView = new JLabel("Selecione a view desejada:", JLabel.CENTER);
        JButton tokenAcessButton = new JButton("Token ");
        JButton adminAcessButton = new JButton("Admin ");
        JButton checkoutAcessButton = new JButton("Caixa ");
        JButton cookAcessButton = new JButton("Cozinheiro ");
        JButton menuBoardAcessButton = new JButton("Menu ");

        panel.setAlignmentX(CENTER_ALIGNMENT);
        panel.add(labelAcessView);
        panel.add(tokenAcessButton);
        panel.add(adminAcessButton);
        panel.add(checkoutAcessButton);
        panel.add(cookAcessButton);
        panel.add(menuBoardAcessButton);
        wrapperPanel.add(panel);



        add(wrapperPanel, BorderLayout.CENTER);

    }

}

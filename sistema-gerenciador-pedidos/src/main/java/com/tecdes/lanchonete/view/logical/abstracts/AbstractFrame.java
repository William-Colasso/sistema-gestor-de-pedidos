package com.tecdes.lanchonete.view.logical.abstracts;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public abstract class AbstractFrame extends JFrame {

    private final Dimension DEFAULT_DIMENSION = new Dimension(1200, 675);

    {
        setIconImage(
                new ImageIcon(getClass().getResource("/img/icon_app - Copy.png")).getImage());
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(DEFAULT_DIMENSION);
        pack();
        setLocationRelativeTo(null);
    }

  

    public AbstractFrame(String title) {
        setTitle(title);
        setLayout(null);
        
        

    }

    protected abstract void initComponents();
}
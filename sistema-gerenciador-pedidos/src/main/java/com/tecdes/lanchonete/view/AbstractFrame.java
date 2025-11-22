package com.tecdes.lanchonete.view;

import java.awt.Dimension;

import javax.swing.JFrame;

public abstract class AbstractFrame extends JFrame {
    public  AbstractFrame(String title){
        setTitle(title);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(500,500));
        pack();
        setLocationRelativeTo(null);
        
    }

     protected abstract void initComponents();
}
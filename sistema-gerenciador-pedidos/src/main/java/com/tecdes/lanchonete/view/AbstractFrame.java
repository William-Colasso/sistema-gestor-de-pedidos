package com.tecdes.lanchonete.view;

import java.awt.Dimension;

import javax.swing.JFrame;

public abstract class AbstractFrame extends JFrame {


    private final Dimension DEFAULT_DIMENSION = new Dimension(1200,675);

    
    public AbstractFrame(){
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(DEFAULT_DIMENSION);
        setLocationRelativeTo(null);
        
    }



    public  AbstractFrame(String title){
        setTitle(title);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(DEFAULT_DIMENSION);
        pack();
        setLocationRelativeTo(null);
        
    }

     protected abstract void initComponents();
}
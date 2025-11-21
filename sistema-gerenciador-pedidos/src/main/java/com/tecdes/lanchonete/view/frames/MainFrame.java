package com.tecdes.lanchonete.view.frames;

import java.awt.GridLayout;

import javax.swing.JButton;

import com.tecdes.lanchonete.view.AbstractFrame;

public class MainFrame extends AbstractFrame {

    public MainFrame() {
        super("Escolha a aplicação desejada");

        setLayout(new GridLayout());

        initComponents();
    }

    @Override
    protected void initComponents() {


        
        JButton jButton1 = new JButton("Token");
        JButton jButton2 = new JButton("Admin");
        JButton jButton3 = new JButton("Caixa");
        JButton jButton4 = new JButton("Visor");

        add(jButton1);
        add(jButton2);
        add(jButton3);
        add(jButton4);



    }

}

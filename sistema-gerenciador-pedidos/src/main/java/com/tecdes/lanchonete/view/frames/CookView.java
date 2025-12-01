package com.tecdes.lanchonete.view.frames;

import java.awt.FlowLayout;

import com.tecdes.lanchonete.view.AbstractFrame;

public final class CookView extends AbstractFrame {

    public CookView() {
        super("Cook View");
        initComponents();
    }

    public CookView(String title) {
        super(title);
        initComponents();
    }

    @Override
    protected void initComponents() {
        setLayout(new FlowLayout());

        //add(new RedirectButton("MainFrame", new MainFrame()));

    }

}

package com.tecdes.lanchonete.view.frames;

import java.awt.FlowLayout;

import com.tecdes.lanchonete.view.AbstractFrame;

public final class AdminView extends AbstractFrame {

    public AdminView() {
        super("Admin View");
        initComponents();

    }

    public AdminView(String title) {
        super(title);
        initComponents();

    }

    @Override
    protected void initComponents() {
        setLayout(new FlowLayout());

        //add(new RedirectButton("MainFrame", new MainFrame()));

    }

}

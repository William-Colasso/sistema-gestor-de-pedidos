package com.tecdes.lanchonete.view.frames;

import java.awt.FlowLayout;

import com.tecdes.lanchonete.view.AbstractFrame;

public final class MenuBoardView extends AbstractFrame {

    public MenuBoardView() {
        super("Menu Board");
        initComponents();
    }

    public MenuBoardView(String title) {
        super(title);
        initComponents();
    }

    @Override
    protected void initComponents() {
        setLayout(new FlowLayout());

        //add(new RedirectButton("MainFrame", new MainFrame()));

    }

}

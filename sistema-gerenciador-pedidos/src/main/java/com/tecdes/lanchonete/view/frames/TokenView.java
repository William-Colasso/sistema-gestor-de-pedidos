package com.tecdes.lanchonete.view.frames;

import java.awt.FlowLayout;

import com.tecdes.lanchonete.view.AbstractFrame;

public final class TokenView extends AbstractFrame {

    public TokenView() {
        super("Token view");
        initComponents();
    }

    public TokenView(String title) {
        super(title);
        initComponents();

    }

    @Override
    protected void initComponents() {
        setLayout(new FlowLayout());
        //add(new RedirectButton("MainFrame", new MainFrame()));
    }

}

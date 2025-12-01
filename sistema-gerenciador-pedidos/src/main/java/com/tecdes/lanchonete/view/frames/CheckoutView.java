package com.tecdes.lanchonete.view.frames;

import java.awt.FlowLayout;

import com.tecdes.lanchonete.view.AbstractFrame;

public final class CheckoutView extends AbstractFrame {

    public CheckoutView() {
        super("Checkout View");
        initComponents();

    }

    public CheckoutView(String title) {
        super(title);
        initComponents();

    }

    @Override
    protected void initComponents() {
        setLayout(new FlowLayout());

        //add(new RedirectButton("MainFrame", new MainFrame()));

    }

}

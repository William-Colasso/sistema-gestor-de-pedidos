package com.tecdes.lanchonete.view.logical.custom;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

import com.tecdes.lanchonete.view.logical.abstracts.AbstractFrame;

public class RedirectButton extends JButton {

    private final AbstractFrame abs;

    public RedirectButton(String nameLabel, AbstractFrame abs) {
        super(nameLabel);
        this.abs = abs;
        addActionListener((ActionEvent e) -> {

            Component c = this;

            while(!(c.getParent() instanceof JFrame)){
                c = c.getParent();
            }
            c = c.getParent();

            JFrame frame = (JFrame)c;
            System.out.println(frame.getTitle());
            c.setVisible(false);
            abs.setVisible(true);
        });

    }

    public AbstractFrame getAbstractFrame() {
        return abs;
    }

}

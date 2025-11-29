package com.tecdes.lanchonete.view.custom;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

import com.tecdes.lanchonete.view.custom.abstracts.AbstractFrame;

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

    public AbstractFrame getAbs() {
        return abs;
    }

}

package com.tecdes.lanchonete.view.custom;

import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;

import javax.swing.JPanel;

import com.tecdes.lanchonete.view.custom.util.RoundedBorder;

import net.miginfocom.swing.MigLayout;

public class MigPanel extends JPanel {

    public MigPanel(String layoutConstraints, String columnConstraints, String rowConstraints, Component... components) {
        super(new MigLayout(layoutConstraints, columnConstraints, rowConstraints));
        for (Component component : components) {
            add(component);
        }
    }



    public MigPanel(String layoutConstraints, String columnConstraints, String rowConstraints) {
        super(new MigLayout(layoutConstraints, columnConstraints, rowConstraints));
    }

    public MigPanel(String layoutConstraints, String columnConstraints) {
        super(new MigLayout(layoutConstraints, columnConstraints));
    }

    public MigPanel(String layoutConstraints) {
        super(new MigLayout(layoutConstraints));
    }

    public void setMigLayout(MigLayout migLayout){
        setLayout(migLayout);
    }
   

    public void setRoundedBorder(int pxRadius, Color color, Insets insets) {
        setBorder(new RoundedBorder(pxRadius, color, insets));
    }

    public void setRoundedBorder(int pxRadius, Color color) {
        setBorder(new RoundedBorder(pxRadius, color));
    }

    public void setRoundedBorder(int pxRadius) {
        setBorder(new RoundedBorder(pxRadius, getBackground()));
    }

}

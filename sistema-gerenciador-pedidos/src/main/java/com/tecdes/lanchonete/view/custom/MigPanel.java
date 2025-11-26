package com.tecdes.lanchonete.view.custom;

import java.awt.Color;

import javax.swing.JPanel;

import com.tecdes.lanchonete.view.util.ColorTheme;

import net.miginfocom.swing.MigLayout;

public class MigPanel extends JPanel{


    public MigPanel(String layoutConstraints, String columnConstraints, String rowConstraints){
        super(new MigLayout(layoutConstraints, columnConstraints, rowConstraints));
        setDefaultColor();
    }


    public MigPanel(String layoutConstraints, String columnConstraints){
        super(new MigLayout(layoutConstraints, columnConstraints));
        setDefaultColor();
    }

    public MigPanel(String layoutConstraints){
        super(new MigLayout(layoutConstraints));
        setDefaultColor();
    }


    private void setDefaultColor(){
        setBackground(ColorTheme.TRANSPARENT);
    }

    
}

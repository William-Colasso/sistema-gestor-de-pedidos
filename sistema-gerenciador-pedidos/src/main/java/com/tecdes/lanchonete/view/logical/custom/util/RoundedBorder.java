package com.tecdes.lanchonete.view.logical.custom.util;


import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;

import javax.swing.border.AbstractBorder;

public class RoundedBorder extends AbstractBorder {


    private final int radius;
    private final Color color;
    private final Insets insets;



    public RoundedBorder(int radius, Color color, Insets insets) {
        this.radius = radius;
        this.color = color;
        this.insets = insets;
    }

    public RoundedBorder(int radius, Color color) {
        this.radius = radius;
        this.color = color;
        this.insets = new Insets(0  , 0 , 0, 0);
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(color); // Cor da borda
        g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return this.insets;
    }

}

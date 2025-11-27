package com.tecdes.lanchonete.view.custom.panel;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class ImagePanel extends JPanel {
    private BufferedImage imagem;

    public ImagePanel(BufferedImage imagem) {
        this.imagem = imagem;
        setPreferredSize(new Dimension(imagem.getWidth(), imagem.getHeight()));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagem, 0, 0, getWidth(), getHeight(), this);
    }
}

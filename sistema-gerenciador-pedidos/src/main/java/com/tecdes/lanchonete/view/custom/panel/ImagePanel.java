package com.tecdes.lanchonete.view.custom.panel;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import com.tecdes.lanchonete.view.custom.util.ImageService;

public class ImagePanel extends JPanel {
    private BufferedImage image;

    public ImagePanel(byte[] imageBytes, ImageService imageService){
        
        this.image = imageService.rawImageToBufferedImage(imageBytes);
    }

    public ImagePanel(BufferedImage image) {
        this.image = image;
        setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
    }
}

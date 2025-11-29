package com.tecdes.lanchonete.view.custom.panel;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import com.tecdes.lanchonete.view.custom.util.ImageService;

public class ImagePanel extends JPanel {
    private final Image image;

    public ImagePanel(byte[] imageBytes, ImageService imageService){
        
        this.image = imageService.rawImageToBufferedImage(imageBytes);
        
    }

    public ImagePanel(Image image){
        this.image = image;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
    }
}

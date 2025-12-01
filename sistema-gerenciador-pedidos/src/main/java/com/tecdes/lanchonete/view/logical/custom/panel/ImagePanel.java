package com.tecdes.lanchonete.view.logical.custom.panel;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import com.tecdes.lanchonete.view.logical.custom.util.ImageService;

public class ImagePanel extends JPanel {
    private final Image image;
    private final int originalWidth;
    private final int originalHeight;

    public ImagePanel(byte[] imageBytes, ImageService imageService){
        this(imageService.rawImageToBufferedImage(imageBytes));
    }

    public ImagePanel(Image image){
        this.image = image;

        if (image != null) {
            this.originalWidth = image.getWidth(null);
            this.originalHeight = image.getHeight(null);
        } else {
            this.originalWidth = 100;
            this.originalHeight = 100;
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(originalWidth, originalHeight);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (image == null) return;

        int panelWidth = getWidth();
        int panelHeight = getHeight();

        double imgAspect = (double) originalWidth / originalHeight;
        double panelAspect = (double) panelWidth / panelHeight;

        int drawWidth, drawHeight;

        if (panelAspect > imgAspect) {
            drawHeight = panelHeight;
            drawWidth = (int) (drawHeight * imgAspect);
        } else {
            drawWidth = panelWidth;
            drawHeight = (int) (drawWidth / imgAspect);
        }

        int x = (panelWidth - drawWidth) / 2;
        int y = (panelHeight - drawHeight) / 2;

        g.drawImage(image, x, y, drawWidth, drawHeight, this);
    }
}

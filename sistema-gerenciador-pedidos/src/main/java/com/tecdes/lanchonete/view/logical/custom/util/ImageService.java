package com.tecdes.lanchonete.view.logical.custom.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public final class ImageService {

    public BufferedImage rawImageToBufferedImage(byte[] bytes) {

        try (InputStream is = new ByteArrayInputStream(bytes)) {
            BufferedImage imagem = ImageIO.read(is);
            return imagem;
        } catch (IOException e) {
            System.out.println("Erro ao converter imagem");
            e.printStackTrace();
        }
        return null;
    }

    public Image getStaticImageByName(String name) {
        return new ImageIcon(getClass().getResource("/img/"+name)).getImage();
    }



}

package com.tecdes.lanchonete.view.custom.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public final class ImageService {

    public BufferedImage rawImageToBufferedImage(byte[] bytes)  {

        try( InputStream is = new ByteArrayInputStream(bytes)){
            BufferedImage imagem = ImageIO.read(is);
            return imagem;
        }catch (IOException e) {
            System.out.println("Erro ao converter imagem");
            e.printStackTrace();
        }
        return null;
    }

}

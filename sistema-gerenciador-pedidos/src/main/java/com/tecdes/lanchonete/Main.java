package com.tecdes.lanchonete;

import javax.swing.SwingUtilities;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.tecdes.lanchonete.config.FlatLafConfig;
import com.tecdes.lanchonete.controller.CategoriaProdutoController;
import com.tecdes.lanchonete.controller.ComboController;
import com.tecdes.lanchonete.controller.ProdutoController;
import com.tecdes.lanchonete.view.MainFrame;
import com.tecdes.lanchonete.view.custom.util.ImageService;
import com.tecdes.lanchonete.view.custom.util.color.LightTheme;

public class Main {

    public static void main(String[] args) {
        FlatLafConfig.setLookAndFeel(new FlatMacLightLaf());
        SwingUtilities.invokeLater(() -> new MainFrame(new LightTheme(), new ImageService(), new CategoriaProdutoController(), new ComboController(), new ProdutoController()).setVisible(true));
        
        
    }
}
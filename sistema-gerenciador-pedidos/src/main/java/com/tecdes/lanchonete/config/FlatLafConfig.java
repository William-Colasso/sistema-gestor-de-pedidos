package com.tecdes.lanchonete.config;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.formdev.flatlaf.FlatLaf;


public class FlatLafConfig {

    private static final String PATH = "com.tecdes.lachonete.view.themes";

    static {
        // registra onde estão seus .properties
         FlatLaf.registerCustomDefaultsSource(PATH);

    }

    public static void setLookAndFeel(FlatLaf laF) {
        try {
            UIManager.setLookAndFeel(laF);
            FlatLaf.updateUI();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }
}

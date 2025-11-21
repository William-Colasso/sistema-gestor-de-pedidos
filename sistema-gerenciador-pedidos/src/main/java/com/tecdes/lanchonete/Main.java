package com.tecdes.lanchonete;

import javax.swing.SwingUtilities;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.tecdes.lanchonete.config.FlatLafConfig;
import com.tecdes.lanchonete.view.frames.MainFrame;

public class Main {

    public static void main(String[] args) {
        FlatLafConfig.setLookAndFeel(new FlatMacLightLaf());
        
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}
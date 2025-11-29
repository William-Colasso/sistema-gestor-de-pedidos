package com.tecdes.lanchonete.view.custom.panel;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.tecdes.lanchonete.controller.GerenteController;
import com.tecdes.lanchonete.view.custom.MigPanel;
import com.tecdes.lanchonete.view.custom.abstracts.CardLayoutable;
import com.tecdes.lanchonete.view.custom.util.ImageService;
import com.tecdes.lanchonete.view.custom.util.RoundedBorder;
import com.tecdes.lanchonete.view.custom.util.color.ColorTheme;

public class LoginPanel extends JLayeredPane {

    private final GerenteController gerenteController;
    private final ColorTheme colorTheme;
    private final ImageService imageService;

    public LoginPanel(GerenteController gerenteController, CardLayoutable cardLayoutable,
            ColorTheme colorTheme, ImageService imageService) {

        setLayout(null);

        this.gerenteController = gerenteController;
        this.colorTheme = colorTheme;
        this.imageService = imageService;

        // --- BACKGROUND ---
        ImagePanel bg = new ImagePanel(imageService.getStaticImageByName("pattern_login_opacity.png"));
        bg.setBounds(0, 0, cardLayoutable.getWidth(), cardLayoutable.getHeight());

        // --- FORM WRAPPER ---
        MigPanel mpPai = new MigPanel("align 50% 50%", "30%[grow]30%", "30%[grow]30%");
        mpPai.setOpaque(false);
        // posição inicial
        mpPai.setBounds(0, 0, cardLayoutable.getWidth(), cardLayoutable.getHeight());

        // --- FORM ---
        MigPanel mp = new MigPanel("fill, wrap 2", "[20%][80%]", "[]10[]10[]10[]10[]");

        JLabel loginLabel = new JLabel("Login:");
        JTextField loginInput = new JTextField();

        JLabel passwordLabel = new JLabel("Senha:");
        JPasswordField passwordInput = new JPasswordField();

        JButton send = new JButton("Logar");

        mp.add(loginLabel, "growx");
        mp.add(loginInput, "growx");
        mp.add(passwordLabel, "growx");
        mp.add(passwordInput, "growx");
        mp.add(send, "span 2, growx");

        mp.setBorder(new RoundedBorder(14, colorTheme.getConstrast()));
        mpPai.setBorder(new RoundedBorder(14, colorTheme.getConstrast()));
        mpPai.add(mp, "grow");

        add(bg, DEFAULT_LAYER);
        add(mpPai, PALETTE_LAYER);

        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                boolean isFullscreen = cardLayoutable.getExtendedState() == java.awt.Frame.MAXIMIZED_BOTH;

                 int width = cardLayoutable.getWidth();
                 int height = cardLayoutable.getHeight();

                System.out.println("Fullscreen? " + isFullscreen);
                bg.setBounds(0, 0, width, height);
                mpPai.setBounds(0, 0, width, height);
                mpPai.setLocation(
                        (width - mpPai.getWidth()) / 2,
                        (height - mpPai.getHeight()) / 2);

            }

        });
    }
}

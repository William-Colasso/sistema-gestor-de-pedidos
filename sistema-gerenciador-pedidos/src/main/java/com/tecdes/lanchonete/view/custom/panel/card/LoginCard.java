package com.tecdes.lanchonete.view.custom.panel.card;

import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.tecdes.lanchonete.controller.GerenteController;
import com.tecdes.lanchonete.view.custom.MigPanel;
import com.tecdes.lanchonete.view.custom.abstracts.CardLayoutbleFrame;
import com.tecdes.lanchonete.view.custom.abstracts.card.LayeredOverlayCard;
import com.tecdes.lanchonete.view.custom.panel.ImagePanel;
import com.tecdes.lanchonete.view.custom.util.ImageService;
import com.tecdes.lanchonete.view.custom.util.RoundedBorder;
import com.tecdes.lanchonete.view.custom.util.color.ColorTheme;

public class LoginCard extends LayeredOverlayCard {

    private final GerenteController gerenteController;
    private final ColorTheme colorTheme;
    private final CardLayoutbleFrame cardLayoutable;

    private final ImagePanel bg;
    private final MigPanel mpPai;
    private final MigPanel mp;

    public LoginCard(CardLayoutbleFrame cardLayoutable, String cardName,GerenteController gerenteController, 
            ColorTheme colorTheme, ImageService imageService) {
        super(cardLayoutable, cardName);

        this.gerenteController = gerenteController;
        this.colorTheme = colorTheme;
        this.cardLayoutable = cardLayoutable;

        // --- BACKGROUND ---
        bg = new ImagePanel(imageService.getStaticImageByName("pattern_login_opacity.png"));
        bg.setBounds(0, 0, cardLayoutable.getWidth(), cardLayoutable.getHeight());

        // --- FORM WRAPPER ---
        mpPai = new MigPanel("align 50% 50%", "40%[grow]40%", "40%[grow]40%");
        mpPai.setOpaque(false);
        // posição inicial
        mpPai.setBounds(0, 0, cardLayoutable.getWidth(), cardLayoutable.getHeight());

        // --- FORM ---
        mp = new MigPanel("fill, wrap 2, gapy 10%", "[10%][90%]", "");

        JLabel loginLabel = new JLabel("Login:");
        JTextField loginInput = new JTextField();

        JLabel passwordLabel = new JLabel("Senha:");
        JPasswordField passwordInput = new JPasswordField();

        JButton send = new JButton("Logar");
        send.addActionListener((ActionEvent e) -> {
            Boolean isLoginValid = login(loginInput, passwordInput);

            System.out.println(isLoginValid);
            if (isLoginValid)
                cardLayoutable.showCard("admin");
        });

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

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {

                mpPai.revalidate(); // força MigLayout a definir tamanhos corretos
                mp.revalidate();
                reSize();
            }

            @Override
            public void componentShown(ComponentEvent e) {
                mpPai.revalidate(); // força MigLayout a definir tamanhos corretos
                mp.revalidate();
                reSize();

            }
        });

        reSize();

    }

    private void reSize() {
        int width = cardLayoutable.getWidth();
        int height = cardLayoutable.getHeight();

        bg.setBounds(0, 0, width, height);
        mpPai.setBounds(0, 0, width, height);

        mp.setLocation(
                (mpPai.getWidth() - mp.getWidth()) / 2,
                (mpPai.getHeight() - mp.getHeight()) / 2);
    }

    private boolean login(JTextField jLogin, JPasswordField jPassword) {

        final String login = jLogin.getText();
        final String password = new String(jPassword.getPassword());

        return gerenteController.login(login, password);
    }

    @Override
    public CardLayoutbleFrame getCardLayoutbleFrame() {
        return this.cardLayoutable;
    }

}

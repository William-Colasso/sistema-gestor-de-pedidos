package com.tecdes.lanchonete.view.frames;

import com.tecdes.lanchonete.controller.GerenteController;
import com.tecdes.lanchonete.view.custom.abstracts.CardLayoutbleFrame;
import com.tecdes.lanchonete.view.custom.panel.card.AdminCard;
import com.tecdes.lanchonete.view.custom.panel.card.LoginCard;
import com.tecdes.lanchonete.view.custom.util.ImageService;
import com.tecdes.lanchonete.view.custom.util.color.ColorTheme;

public final class AdminView extends CardLayoutbleFrame{

    private AdminCard adminCard;
    private LoginCard loginCard;
    private final ColorTheme colorTheme;
    private final GerenteController gerenteController;
    private  final ImageService imageService;

    public AdminView(GerenteController gerenteController, ColorTheme colorTheme, ImageService imageService) {
        super("Admin View");
        this.colorTheme = colorTheme;
        this.gerenteController = gerenteController;
        this.imageService = imageService;

        initComponents();

    }

    @Override
    protected void initComponents() {
        
        adminCard = new AdminCard(this, "admin");
        loginCard = new LoginCard(this,"login",gerenteController, colorTheme, imageService);
       

        // add(new RedirectButton("MainFrame", new MainFrame()));

        addCard(adminCard);
        addCard(loginCard);

        showCard("login");
    }

 

  
   
    

}

package com.tecdes.lanchonete.view.physical.frames;

import com.tecdes.lanchonete.controller.ClienteController;
import com.tecdes.lanchonete.controller.FuncionarioController;
import com.tecdes.lanchonete.controller.GerenteController;
import com.tecdes.lanchonete.view.logical.abstracts.DeckFrame;
import com.tecdes.lanchonete.view.logical.custom.util.ImageService;
import com.tecdes.lanchonete.view.logical.custom.util.color.ColorTheme;
import com.tecdes.lanchonete.view.physical.panel.AdminCard;
import com.tecdes.lanchonete.view.physical.panel.LoginCard;

public final class AdminView extends DeckFrame{

    private AdminCard adminCard;
    private LoginCard loginCard;
    private final ColorTheme colorTheme;
    private final GerenteController gerenteController;
    private final ClienteController clienteController;
    private final FuncionarioController funcionarioController;
    private  final ImageService imageService;

    public AdminView(GerenteController gerenteController, ColorTheme colorTheme, ImageService imageService, ClienteController clienteController, FuncionarioController funcionarioController) {
        super("Admin View");
        this.colorTheme = colorTheme;
        this.gerenteController = gerenteController;
        this.clienteController = clienteController;
        this.funcionarioController = funcionarioController;
        this.imageService = imageService;

        initComponents();

    }

    @Override
    protected void initComponents() {
        
        adminCard = new AdminCard(this, "admin", imageService, colorTheme, clienteController, funcionarioController, gerenteController);
        loginCard = new LoginCard(this,"login",gerenteController, colorTheme, imageService);
       

        // add(new RedirectButton("MainFrame", new MainFrame()));

        addCard(adminCard);
        addCard(loginCard);

        showCard("admin");
    }

 

  
   
    

}

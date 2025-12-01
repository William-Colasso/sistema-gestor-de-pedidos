package com.tecdes.lanchonete.view.physical.panel;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.tecdes.lanchonete.controller.ClienteController;
import com.tecdes.lanchonete.model.entity.Cliente;
import com.tecdes.lanchonete.view.logical.abstracts.DeckFrame;
import com.tecdes.lanchonete.view.logical.abstracts.DeckPanel;
import com.tecdes.lanchonete.view.logical.abstracts.card.MigCard;
import com.tecdes.lanchonete.view.logical.custom.panel.ImagePanel;
import com.tecdes.lanchonete.view.logical.custom.panel.MigPanel;
import com.tecdes.lanchonete.view.logical.custom.util.ImageService;
import com.tecdes.lanchonete.view.logical.custom.util.RoundedBorder;
import com.tecdes.lanchonete.view.logical.custom.util.color.ColorTheme;

public class AdminCard extends MigCard {

    private final MigPanel menu;
    private final DeckPanel deck;
    private final ImageService imgS;
    private final ColorTheme colorTheme;
    private final ClienteController clienteController;

    public AdminCard(DeckFrame cardLayoutableFrame, String cardName, ImageService imgS, ColorTheme colorTheme,
            ClienteController clienteController) {
        super("", "[20%][80%]", "[grow]", cardName, cardLayoutableFrame);
        this.colorTheme = colorTheme;
        this.imgS = imgS;
        this.clienteController = clienteController;

        menu = new MigPanel("wrap, insets 5", "[grow, fill]", "");

        instantiateMenu();

        deck = new DeckPanel();
        instantiateDeck();

        add(menu, "grow");
        add(deck, "grow");

    }

    private void instantiateMenu() {
        menu.setRoundedBorder(5, colorTheme.getConstrast());

        String[] labelTexts = { "Cliente", "Funcionário", "Gerente", "Produto", "Categorias", "Combo", "Pedido",
                "Cupom",
                "Parceiro" };

        for (String nameMenuItem : labelTexts) {
            MigPanel mgPanel = new MigPanel("", "[20%]10%[60%][10%]", "[grow]");

            mgPanel.add(new ImagePanel(imgS.getStaticImageByName("admin/" + nameMenuItem + ".png")), "grow");

            JLabel labelNameItemMenu = new JLabel(nameMenuItem);
            mgPanel.setOpaque(true);
            mgPanel.setRoundedBorder(14, colorTheme.getShadowSoft());
            mgPanel.setBackground(colorTheme.getWhite());
            mgPanel.setDefaultBackground(colorTheme.getWhite());
            mgPanel.setHoverBackground(colorTheme.getShadowSoft());

            mgPanel.setAction(() -> {
                System.out.println("Menu item clicado: " + nameMenuItem);
                deck.showCard(nameMenuItem);
            });
            mgPanel.add(labelNameItemMenu, "grow");
            menu.add(mgPanel, "grow");

        }

    }

    private void instantiateDeck() {

        deck.setBorder(new RoundedBorder(5, colorTheme.getConstrast()));
        deck.setBackground(colorTheme.getDark());

        MigCard cardClientes = new MigCard("wrap 2", "[15%][grow]", "[][grow]", "Cliente", deck);
        instantiateCardCliente(cardClientes);

        MigCard cardFuncionarios = new MigCard("", "5%[90%]5%", "5%[15%][5%][70%]5%", "Funcionário", deck);
       
        deck.addCard(cardFuncionarios);
        deck.addCard(cardClientes);
    }

    private void instantiateCardCliente(MigCard cardClientes) {

        // CAMPO DE FILTRO
        JLabel labelFiltro = new JLabel("Filtro:");
        JFormattedTextField txtFiltro = new JFormattedTextField();
        cardClientes.add(labelFiltro );
        cardClientes.add(txtFiltro, " growx");

        // LISTA ORIGINAL
        List<Cliente> clientes = clienteController.getAll();

        MigPanel mgTable = new MigPanel("wrap, gapy 1%", "[grow]", "");

        JScrollPane jScrollPaneTable = new JScrollPane(mgTable);
        
        // FUNÇÃO PARA CARREGAR LISTA
        Runnable loadTable = () -> {
            String filtro = txtFiltro.getText().toLowerCase();
            mgTable.removeAll();
            for (Cliente cliente : clientes) {
                if (cliente.getNome().toLowerCase().contains(filtro) ||
                        cliente.getCpf().toLowerCase().contains(filtro)) {

                    MigPanel mgRow = new MigPanel("fill", "", "[grow]");

                    JLabel labelName = new JLabel(cliente.getNome());
                    JLabel labelCpf = new JLabel(cliente.getCpf());
                    JLabel labelTele = new JLabel(cliente.getTelefone());

                    JLabel labelDate = new JLabel(cliente.getDataRegistro().toString());

                    JButton excluir = new JButton("Excluir");
                    excluir.addActionListener((ActionEvent e) -> clienteController.delete(cliente.getId()));

                    mgRow.add(labelName, "grow");
                    mgRow.add(labelCpf, "grow");
                    mgRow.add(labelTele, "grow");
                    mgRow.add(labelDate, "grow");
                    mgRow.add(excluir, "grow");

                    mgTable.add(mgRow, "grow");
                }
            }
            cardClientes.add(jScrollPaneTable,"grow, span 2");
        };

        loadTable.run();

        // FILTRO DINÂMICO
        txtFiltro.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                loadTable.run();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                loadTable.run();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                loadTable.run();
            }
        });
    }




}

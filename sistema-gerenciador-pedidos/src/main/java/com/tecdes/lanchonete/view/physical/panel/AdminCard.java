package com.tecdes.lanchonete.view.physical.panel;

import java.awt.event.ActionEvent;
import java.sql.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.tecdes.lanchonete.controller.ClienteController;
import com.tecdes.lanchonete.controller.FuncionarioController;
import com.tecdes.lanchonete.controller.GerenteController;
import com.tecdes.lanchonete.model.entity.Cliente;
import com.tecdes.lanchonete.model.entity.Funcionario;
import com.tecdes.lanchonete.model.entity.Gerente;
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
    private final FuncionarioController funcionarioController;
    private final GerenteController gerenteController;

    public AdminCard(DeckFrame cardLayoutableFrame, String cardName, ImageService imgS, ColorTheme colorTheme,
            ClienteController clienteController, FuncionarioController funcionarioController,
            GerenteController gerenteController) {
        super("", "[20%][80%]", "[grow]", cardName, cardLayoutableFrame);
        this.colorTheme = colorTheme;
        this.imgS = imgS;
        this.clienteController = clienteController;
        this.gerenteController = gerenteController;
        menu = new MigPanel("wrap, insets 5", "[grow, fill]", "");

        instantiateMenu();

        deck = new DeckPanel();
        this.funcionarioController = funcionarioController;
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

        MigCard cardFuncionarios = new MigCard("wrap", "[15%][grow]", "[][grow][]", "Funcionário", deck);
        
        MigCard cardCriarFuncionario = new MigCard("wrap 2", "[30%][grow]", "[][grow][]", "CriarFuncionario", deck);
        instantiateCardFuncionarios(cardFuncionarios, cardCriarFuncionario);
        instantiateCardCriarFuncionario(cardCriarFuncionario, cardFuncionarios);

        deck.addCard(cardFuncionarios);
        deck.addCard(cardClientes);
        deck.addCard(cardCriarFuncionario);

    }

    private void instantiateCardCliente(MigCard cardClientes) {

        // CAMPO DE FILTRO
        JLabel labelFiltro = new JLabel("Filtro:");
        JFormattedTextField txtFiltro = new JFormattedTextField();
        cardClientes.add(labelFiltro);
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
            cardClientes.add(jScrollPaneTable, "grow, span 2");
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

    private void instantiateCardFuncionarios(MigCard cardFuncionarios, MigCard cardCriarFuncionario) {

        JLabel labelFiltro = new JLabel("Filtro:");
        JFormattedTextField txtFiltro = new JFormattedTextField();
        MigPanel mgFiltro = new MigPanel("", "[15%][85%]", "[grow]");
        mgFiltro.add(labelFiltro);
        mgFiltro.add(txtFiltro, "grow");
        cardFuncionarios.add(mgFiltro, "grow, span 2");

        List<Funcionario> funcionarios = funcionarioController.getAll();

        MigPanel mgTable = new MigPanel("wrap, gapy 1%", "[grow]", "");

        JScrollPane jScrollPaneTable = new JScrollPane(mgTable);

        Runnable loadTable = () -> {
            String filtro = txtFiltro.getText().toLowerCase();
            mgTable.removeAll();
            for (Funcionario funcionario : funcionarios) {
                if (funcionario.getNome().toLowerCase().contains(filtro)
                        || funcionario.getCpf().toLowerCase().contains(filtro)) {

                    MigPanel mgRow = new MigPanel("", "[grow]", "[grow]");

                    JLabel labelName = new JLabel(funcionario.getNome());
                    JLabel labelCpf = new JLabel(funcionario.getCpf());
                    JLabel labelDate = new JLabel(funcionario.getDataNascimento().toString());

                    JLabel labelGerente = new JLabel(
                            funcionario.getGerente() != null
                                    ? funcionario.getGerente().getNome()
                                    : "Sem gerente");

                    JButton excluir = new JButton("Excluir");
                    excluir.addActionListener((ActionEvent e) -> funcionarioController.delete(funcionario.getId()));

                    mgRow.add(labelName, "grow");
                    mgRow.add(labelCpf, "grow");
                    mgRow.add(labelDate, "grow");
                    mgRow.add(labelGerente, "grow");
                    mgRow.add(excluir, "grow");

                    mgTable.add(mgRow, "grow");
                }
            }
            cardFuncionarios.add(jScrollPaneTable, "grow, span 2");
            revalidate();
            repaint();

        };

        JButton changeFunction = new JButton(("Cadastrar Novo Funcionário"));
        changeFunction.addActionListener((ActionEvent e) -> deck.showCard(cardCriarFuncionario));

        cardFuncionarios.add(changeFunction, "grow, span 2");

        loadTable.run();

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

    private void instantiateCardCriarFuncionario(MigCard cardCriarFuncionario, MigCard cardFuncionarios) {

    JLabel labelNome = new JLabel("Nome:");
    JFormattedTextField txtNome = new JFormattedTextField();

    JLabel labelCpf = new JLabel("CPF:");
    JFormattedTextField txtCpf = new JFormattedTextField();

    JLabel labelData = new JLabel("Data Nascimento (AAAA-MM-DD):");
    JFormattedTextField txtData = new JFormattedTextField();

    JLabel labelGerente = new JLabel("Gerente:");
    List<Gerente> gerentes = gerenteController.getAll();
    String[] nomesGerentes = gerentes.stream().map(Gerente::getNome).toArray(String[]::new);
    JComboBox<String> comboGerente = new JComboBox<>(nomesGerentes);

    MigPanel form = new MigPanel("wrap 2", "[30%][70%]", "");

    form.add(labelNome);
    form.add(txtNome, "growx");

    form.add(labelCpf);
    form.add(txtCpf, "growx");

    form.add(labelData);
    form.add(txtData, "growx");

    form.add(labelGerente);
    form.add(comboGerente, "growx");

    JButton btnSalvar = new JButton("Salvar");
    JButton btnVoltar = new JButton("Voltar");

    btnSalvar.addActionListener((ActionEvent e) -> {

        Funcionario funcionario = new Funcionario();
        funcionario.setNome(txtNome.getText());
        funcionario.setCpf(txtCpf.getText());
        funcionario.setDataNascimento(Date.valueOf(txtData.getText()));

        if (comboGerente.getSelectedIndex() >= 0)
            funcionario.setGerente(gerentes.get(comboGerente.getSelectedIndex()));

        funcionarioController.create(funcionario);

        deck.showCard("Funcionário");


        
    });

    btnVoltar.addActionListener((ActionEvent e) -> deck.showCard("Funcionário"));

    cardCriarFuncionario.add(form, "grow, span 2");
    cardCriarFuncionario.add(btnSalvar, "grow");
    cardCriarFuncionario.add(btnVoltar, "grow");
}


}

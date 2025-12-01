package com.tecdes.lanchonete.view.physical.panel;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.sql.Date;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.NumberFormatter;

import com.tecdes.lanchonete.controller.CategoriaProdutoController;
import com.tecdes.lanchonete.controller.ClienteController;
import com.tecdes.lanchonete.controller.FuncionarioController;
import com.tecdes.lanchonete.controller.GerenteController;
import com.tecdes.lanchonete.controller.MidiaController;
import com.tecdes.lanchonete.controller.ProdutoController;
import com.tecdes.lanchonete.model.entity.CategoriaProduto;
import com.tecdes.lanchonete.model.entity.Cliente;
import com.tecdes.lanchonete.model.entity.Funcionario;
import com.tecdes.lanchonete.model.entity.Gerente;
import com.tecdes.lanchonete.model.entity.Midia;
import com.tecdes.lanchonete.model.entity.Combo;
import com.tecdes.lanchonete.model.entity.Produto;
import com.tecdes.lanchonete.model.enums.TipoMidia;
import com.tecdes.lanchonete.view.logical.abstracts.DeckFrame;
import com.tecdes.lanchonete.view.logical.abstracts.DeckPanel;
import com.tecdes.lanchonete.view.logical.abstracts.card.MigCard;
import com.tecdes.lanchonete.view.logical.custom.panel.ImagePanel;
import com.tecdes.lanchonete.view.logical.custom.panel.ItemPanel;
import com.tecdes.lanchonete.view.logical.custom.panel.MigPanel;
import com.tecdes.lanchonete.view.logical.custom.util.ImageService;
import com.tecdes.lanchonete.view.logical.custom.util.RoundedBorder;
import com.tecdes.lanchonete.view.logical.custom.util.color.ColorTheme;

import net.miginfocom.swing.MigLayout;

public class AdminCard extends MigCard {

    private final MigPanel menu;
    private final DeckPanel deck;
    private final ImageService imgS;
    private final ColorTheme colorTheme;
    private final ClienteController clienteController;
    private final FuncionarioController funcionarioController;
    private final GerenteController gerenteController;
    private final ProdutoController produtoController;
    private final CategoriaProdutoController categoriaProdutoController;
    private final MidiaController midiaController;

    public AdminCard(DeckFrame cardLayoutableFrame, String cardName, ImageService imgS, ColorTheme colorTheme,
            ClienteController clienteController, FuncionarioController funcionarioController,
            GerenteController gerenteController, ProdutoController produtoController,
            CategoriaProdutoController categoriaProdutoController, MidiaController midiaController) {
        super("", "[20%][80%]", "[grow]", cardName, cardLayoutableFrame);
        this.colorTheme = colorTheme;
        this.imgS = imgS;
        this.clienteController = clienteController;
        this.gerenteController = gerenteController;
        menu = new MigPanel("wrap, insets 5", "[grow, fill]", "");

        instantiateMenu();

        deck = new DeckPanel();
        this.funcionarioController = funcionarioController;
        this.produtoController = produtoController;
        this.categoriaProdutoController = categoriaProdutoController;
        this.midiaController = midiaController;
        instantiateDeck();

        add(menu, "grow");
        add(deck, "grow");

    }

    private void instantiateMenu() {

        String[] labelTexts = { "Cliente", "Funcionário", "Produto", "Categorias", "Combo", "Pedido",
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

        MigCard cardCliente = new MigCard("wrap 2", "[15%][grow]", "[][grow]", "Cliente", deck);
        instantiateCardCliente(cardCliente);

        MigCard cardFuncionario = new MigCard("wrap", "[15%][grow]", "[][][]", "Funcionário", deck);

        MigCard cardCriarFuncionario = new MigCard("wrap 2", "[30%][grow]", "[][grow][]", "CriarFuncionario", deck);
        instantiatecardFuncionario(cardFuncionario, cardCriarFuncionario);
        instantiateCardCriarFuncionario(cardCriarFuncionario, cardFuncionario);

        MigCard cardProduto = new MigCard("wrap", "[][grow]", "[][][grow]", "Produto", deck);

        MigCard criarProduto = new MigCard("wrap 2", "[][]", "", "CriarAtualizarProduto", deck);
        instantiateCriarAtualizarProduto(criarProduto, cardProduto, null);
        instantiateCardProduto(cardProduto, criarProduto);

        deck.addCard(cardFuncionario);
        deck.addCard(cardCliente);
        deck.addCard(cardCriarFuncionario);
        deck.addCard(cardProduto);
        deck.addCard(criarProduto);
    }

    private void instantiateCardCliente(MigCard cardCliente) {

        // CAMPO DE FILTRO
        JLabel labelFiltro = new JLabel("Filtro:");
        JFormattedTextField txtFiltro = new JFormattedTextField();
        cardCliente.add(labelFiltro);
        cardCliente.add(txtFiltro, " growx");

        MigPanel mgTable = new MigPanel("wrap, gapy 1%", "[grow]", "");

        JScrollPane jScrollPaneTable = new JScrollPane(mgTable);

        // FUNÇÃO PARA CARREGAR LISTA
        Runnable loadTable = () -> {
            String filtro = txtFiltro.getText().toLowerCase();
            mgTable.removeAll();
            for (Cliente cliente : clienteController.getAll()) {
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
            cardCliente.add(jScrollPaneTable, "grow, span 2");
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

    private void instantiatecardFuncionario(MigCard cardFuncionario, MigCard cardCriarFuncionario) {

        JLabel labelFiltro = new JLabel("Filtro:");
        JFormattedTextField txtFiltro = new JFormattedTextField();
        MigPanel mgFiltro = new MigPanel("", "[15%][85%]", "[grow]");
        mgFiltro.add(labelFiltro);
        mgFiltro.add(txtFiltro, "grow");
        cardFuncionario.add(mgFiltro, "grow, span 2");

        MigPanel mgTable = new MigPanel("wrap", "[grow]", "");

        JScrollPane jScrollPaneTable = new JScrollPane(mgTable);

        Runnable loadTable = () -> {
            String filtro = txtFiltro.getText().toLowerCase();
            mgTable.removeAll();
            for (Funcionario funcionario : funcionarioController.getAll()) {
                if (funcionario.getNome().toLowerCase().contains(filtro)
                        || funcionario.getCpf().toLowerCase().contains(filtro)) {

                    MigPanel mgRow = new MigPanel("", "[grow][15%]", "[grow]");

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
            cardFuncionario.add(jScrollPaneTable, "grow, span 2");
            revalidate();
            repaint();

        };

        JButton changeFunction = new JButton(("Cadastrar Novo Funcionário"));
        changeFunction.addActionListener((ActionEvent e) -> deck.showCard(cardCriarFuncionario));

        cardFuncionario.add(changeFunction, "span 2, growx");
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

    private void instantiateCardCriarFuncionario(MigCard cardCriarFuncionario, MigCard cardFuncionario) {

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

        btnVoltar.addActionListener((ActionEvent e) -> deck.showCard(cardFuncionario));

        cardCriarFuncionario.add(form, "grow, span 2");
        cardCriarFuncionario.add(btnSalvar, "grow");
        cardCriarFuncionario.add(btnVoltar, "grow");
    }
    private void instantiateCardProduto(MigCard cardProduto, MigCard criarProduto) {

        // CAMPO DE FILTRO
        JLabel labelFiltro = new JLabel("Filtro:");
        JFormattedTextField txtFiltro = new JFormattedTextField();
        cardProduto.add(labelFiltro);
        cardProduto.add(txtFiltro, "growx");
    
        // GRID DE PRODUTOS
        MigPanel mgGrid = new MigPanel("wrap 4", "[grow]", "[grow]");
        JScrollPane jScrollPaneGrid = new JScrollPane(mgGrid);
    
        JButton buttonCriar = new JButton("Criar Novo Produto");
        buttonCriar.addActionListener((ActionEvent e) -> {
            instantiateCriarAtualizarProduto(criarProduto, cardProduto, null);
            deck.showCard(criarProduto);
        });
    
        cardProduto.add(buttonCriar, "growx, span 2");
    
        // ADICIONA UMA VEZ SÓ
        cardProduto.add(jScrollPaneGrid, "grow, span 2, push");
    
        // CACHE DE PRODUTOS (evita chamar o banco a cada letra)
        List<Produto> produtosCache = produtoController.getAll();
    
        // FUNÇÃO DE CARREGAMENTO DO GRID
        Runnable loadTable = () -> {
            String filtro = txtFiltro.getText().toLowerCase();
            mgGrid.removeAll();
    
            for (Produto produto : produtosCache) {
                if (produto.getNome().toLowerCase().contains(filtro) ||
                    produto.getDescricao().toLowerCase().contains(filtro)) {
    
                    ItemPanel produtoPanel = new ItemPanel(produto, imgS);
                    produtoPanel.setHoverBackground(colorTheme.getShadowSoft());
    
                    produtoPanel.setAction(() -> {
                        instantiateCriarAtualizarProduto(criarProduto, cardProduto, produto);
                        deck.showCard(criarProduto);
                    });
    
                    mgGrid.add(produtoPanel, "grow, w 135, h 140");
                }
            }
    
            mgGrid.revalidate();
            mgGrid.repaint();
        };
    
        // PRIMEIRA CARGA
        loadTable.run();
    
        // DEBOUNCE DO CAMPO DE FILTRO (evita lentidão)
        txtFiltro.getDocument().addDocumentListener(new DocumentListener() {
    
            private Timer debounceTimer;
    
            private void handleChange() {
                if (debounceTimer != null) {
                    debounceTimer.stop();
                }
    
                debounceTimer = new Timer(150, e -> loadTable.run());
                debounceTimer.setRepeats(false);
                debounceTimer.start();
            }
    
            @Override
            public void insertUpdate(DocumentEvent e) { handleChange(); }
    
            @Override
            public void removeUpdate(DocumentEvent e) { handleChange(); }
    
            @Override
            public void changedUpdate(DocumentEvent e) { handleChange(); }
        });
    }
    

    private void instantiateCriarAtualizarProduto(MigCard criarProduto, MigCard cardProduto, Produto produto) {

        criarProduto.removeAll();
        criarProduto.setLayout(new MigLayout("wrap 2", "[right][grow]", "[][][][][][]20[]"));

        // Nome
        JLabel labelNome = new JLabel("Nome:");
        JTextField inputNome = new JTextField();
        criarProduto.add(labelNome);
        criarProduto.add(inputNome, "growx");

        // Valor
        JLabel labelValor = new JLabel("Valor:");
        NumberFormat format = NumberFormat.getNumberInstance();
        format.setMinimumFractionDigits(0);
        format.setMaximumFractionDigits(2);

        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Double.class);
        formatter.setAllowsInvalid(false);
        formatter.setMinimum(0.0);
        formatter.setMaximum(99999.99);

        JFormattedTextField inputValor = new JFormattedTextField(formatter);
        inputValor.setColumns(7);
        criarProduto.add(labelValor);
        criarProduto.add(inputValor, "growx");

        // Descrição
        JLabel labelDescricao = new JLabel("Descrição:");
        JTextField inputDescricao = new JTextField();
        criarProduto.add(labelDescricao);
        criarProduto.add(inputDescricao, "growx");

        // Categoria
        JLabel labelCategoria = new JLabel("Categoria:");
        JComboBox<CategoriaProduto> inputCategoria = new JComboBox<>();
        categoriaProdutoController.getAll().forEach(inputCategoria::addItem);

        criarProduto.add(labelCategoria);
        criarProduto.add(inputCategoria, "growx");

        // Quantidade
        JLabel labelQuantidade = new JLabel("Qtd Inicial:");
        JSpinner inputQuantidade = new JSpinner(new SpinnerNumberModel(0, 0, 9999, 1));
        criarProduto.add(labelQuantidade);
        criarProduto.add(inputQuantidade, "growx");

        // STATUS ATIVO
        JLabel labelStatus = new JLabel("Ativo:");
        JCheckBox checkAtivo = new JCheckBox();
        criarProduto.add(labelStatus);
        criarProduto.add(checkAtivo, "left");

        // IMAGEM
        JLabel labelImagem = new JLabel("Imagem:");
        JButton btnSelecionarImagem = new JButton("Selecionar");

        JLabel preview = new JLabel();
        preview.setPreferredSize(new Dimension(120, 120));
        preview.setBorder(new RoundedBorder(5, colorTheme.getShadowSoft()));
        preview.setHorizontalAlignment(JLabel.CENTER);

        criarProduto.add(labelImagem);
        criarProduto.add(btnSelecionarImagem, "growx");

        criarProduto.add(new JLabel("Preview:"));
        criarProduto.add(preview, "grow");

        final BufferedImage[] imagemSelecionada = { null };

        btnSelecionarImagem.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(new FileNameExtensionFilter(
                    "Imagens", "png", "jpg", "jpeg", "jfif"));

            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                try {
                    File file = chooser.getSelectedFile();
                    BufferedImage img = ImageIO.read(file);

                    imagemSelecionada[0] = img;

                    Image scaled = img.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
                    preview.setIcon(new ImageIcon(scaled));

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        // BOTÕES FINAL
        JButton btnVoltar = new JButton("Voltar");
        JButton btnSalvar = new JButton("Salvar");

        criarProduto.add(btnVoltar, "growx");
        criarProduto.add(btnSalvar, "growx");

        // Preencher caso esteja editando
        if (produto != null) {
            inputNome.setText(produto.getNome());
            inputValor.setValue(produto.getValor());
            inputDescricao.setText(produto.getDescricao());
            inputCategoria.setSelectedItem(produto.getCategoria());
            inputQuantidade.setValue(produto.getQuantidade());

            checkAtivo.setSelected(produto.getStatusAtivo() == 1);

            if (produto.getMidias() != null && !produto.getMidias().isEmpty()) {
                try {
                    Midia m = produto.getMidias().get(0);
                    BufferedImage img = imgS.rawImageToBufferedImage(m.getArquivo());
                    imagemSelecionada[0] = img;
                    preview.setIcon(new ImageIcon(img.getScaledInstance(120, 120, Image.SCALE_SMOOTH)));
                } catch (Exception ignored) {
                }
            }
        }
        btnSalvar.addActionListener(e -> {

            Produto p = (produto == null ? new Produto() : produto);

            p.setNome(inputNome.getText());
            p.setValor(((Number) inputValor.getValue()).doubleValue());
            p.setDescricao(inputDescricao.getText());
            p.setCategoria((CategoriaProduto) inputCategoria.getSelectedItem());
            p.setQuantidade((Integer) inputQuantidade.getValue());
            p.setStatusAtivo(checkAtivo.isSelected() ? 1 : 0);
            LocalDate now = LocalDate.now();
            p.setDataCriacao(new Date(now.getYear(), now.getMonthValue(), now.getDayOfMonth()));
            p.setCombos(p.getCombos() == null ? new ArrayList<Combo>() : p.getCombos());

            boolean novo = (produto == null);

            // 1) Primeiro salva o produto (para garantir que ele tenha ID)
            if (novo) {
                p = produtoController.create(p); // IMPORTANTE: pega o produto já com ID do BD
            } else {
                produtoController.update(p);
            }

            // 2) Salvar a mídia SE uma imagem tiver sido selecionada
            if (imagemSelecionada[0] != null) {
                try {

                    // Converte BufferedImage → byte[]
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ImageIO.write(imagemSelecionada[0], "png", baos);
                    baos.flush();
                    byte[] bytesImagem = baos.toByteArray();
                    baos.close();

                    // Criar objeto Midia
                    Midia midia = new Midia();
                    midia.setIdItem(p.getId()); // produto dono da mídia
                    midia.setDescricao("Imagem do produto " + p.getNome());
                    midia.setArquivo(bytesImagem);
                    midia.setTipo(TipoMidia.IMAGEM); // Enum definido no seu projeto

                    // 3) Persiste a mídia no banco
                    midia = midiaController.create(midia);

                    // 4) Atualiza o produto com a mídia
                    p.setMidias(List.of(midia));

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            deck.showCard(cardProduto);
        });

        // BOTÃO VOLTAR
        btnVoltar.addActionListener((ActionEvent e) -> deck.showCard(cardProduto));

        criarProduto.revalidate();
        criarProduto.repaint();
    }

}

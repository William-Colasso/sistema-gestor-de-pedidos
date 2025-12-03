package com.tecdes.lanchonete.view.physical.panel;

import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.Date;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.Document;
import javax.swing.text.NumberFormatter;

import com.tecdes.lanchonete.controller.CategoriaProdutoController;
import com.tecdes.lanchonete.controller.ClienteController;
import com.tecdes.lanchonete.controller.FuncionarioController;
import com.tecdes.lanchonete.controller.GerenteController;
import com.tecdes.lanchonete.controller.MidiaController;
import com.tecdes.lanchonete.controller.ProdutoController;
import com.tecdes.lanchonete.controller.RelatorioController;
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
import com.tecdes.lanchonete.view.logical.abstracts.card.LayeredOverlayCard;
import com.tecdes.lanchonete.view.logical.abstracts.card.MigCard;
import com.tecdes.lanchonete.view.logical.custom.panel.CategoriePanel;
import com.tecdes.lanchonete.view.logical.custom.panel.ImagePanel;
import com.tecdes.lanchonete.view.logical.custom.panel.ItemPanel;
import com.tecdes.lanchonete.view.logical.custom.panel.MigPanel;
import com.tecdes.lanchonete.view.logical.custom.util.ImageService;
import com.tecdes.lanchonete.view.logical.custom.util.RoundedBorder;
import com.tecdes.lanchonete.view.logical.custom.util.color.ColorTheme;

import net.miginfocom.swing.MigLayout;

public class AdminCard extends MigCard {

    private final MigPanel menu;
    private final DeckPanel deck = new DeckPanel();
    private final ImageService imgS;
    private final ColorTheme colorTheme;
    private final ClienteController clienteController;
    private final FuncionarioController funcionarioController;
    private final GerenteController gerenteController;
    private final ProdutoController produtoController;
    private final CategoriaProdutoController categoriaProdutoController;
    private final MidiaController midiaController;
    private final RelatorioController relatorioController;

    public AdminCard(DeckFrame frame, String cardName, ImageService imgS, ColorTheme colorTheme,
            ClienteController clienteController, FuncionarioController funcionarioController,
            GerenteController gerenteController, ProdutoController produtoController,
            CategoriaProdutoController categoriaProdutoController, MidiaController midiaController, RelatorioController relatorioController) {
        super("", "[20%][80%]", "[grow]", cardName, frame);
        this.imgS = imgS;
        this.colorTheme = colorTheme;
        this.clienteController = clienteController;
        this.funcionarioController = funcionarioController;
        this.gerenteController = gerenteController;
        this.produtoController = produtoController;
        this.categoriaProdutoController = categoriaProdutoController;
        this.midiaController = midiaController;
        this.relatorioController = relatorioController;

        menu = new MigPanel("wrap, insets 5", "[grow, fill]", "");
        instantiateMenu();
        instantiateDeck();

        add(menu, "grow");
        add(deck, "grow");
    }

    /* -------------------- Menu -------------------- */
    private void instantiateMenu() {
        String[] items = { "Cliente", "Funcionário", "Produto", "Categorias", "Combo", "Pedido", "Cupom", "Parceiro", "Relatório" };
        for (String name : items) {
            MigPanel item = new MigPanel("", "[20%]10%[60%][10%]", "[grow]");
            item.add(new ImagePanel(imgS.getStaticImageByName("admin/" + name + ".png")), "grow");
            JLabel lbl = new JLabel(name);
            item.setOpaque(true);
            item.setRoundedBorder(14, colorTheme.getShadowSoft());
            item.setBackground(colorTheme.getWhite());
            item.setDefaultBackground(colorTheme.getWhite());
            item.setHoverBackground(colorTheme.getShadowSoft());
            item.setAction(() -> {
                System.out.println("Menu item clicado: " + name);
                deck.showCard(name);
            });
            item.add(lbl, "grow");
            menu.add(item, "grow");
        }
    }

    /* -------------------- Deck -------------------- */
    private void instantiateDeck() {
        MigCard cardCliente = new MigCard("wrap 2", "[15%][grow]", "[][grow]", "Cliente", deck);
        instantiateCardCliente(cardCliente);

        MigCard cardFuncionario = new MigCard("wrap", "[15%][grow]", "[][][]", "Funcionário", deck);
        MigCard cardCriarFuncionario = new MigCard("wrap 2", "[30%][grow]", "[][grow][]", "CriarFuncionario", deck);
        instantiateCardFuncionario(cardFuncionario, cardCriarFuncionario);
        instantiateCardCriarFuncionario(cardCriarFuncionario, cardFuncionario);

        MigCard cardProduto = new MigCard("wrap", "[][grow]", "[][][grow]", "Produto", deck);
        MigCard criarProduto = new MigCard("wrap 2", "[][]", "", "CriarAtualizarProduto", deck);
        instantiateCardProduto(cardProduto, criarProduto);
        instantiateCriarAtualizarProduto(criarProduto, cardProduto, null);

        LayeredOverlayCard cardCategoria = new LayeredOverlayCard(deck, "Categorias");
        instantiateCardCategoria(cardCategoria);

        MigCard cardRelatorio = new MigCard("wrap", "[][grow]", "[][][grow]", "Relatório", deck);
        instantiateCardRelatorio(cardRelatorio);

        deck.addCard(cardFuncionario);
        deck.addCard(cardCliente);
        deck.addCard(cardCriarFuncionario);
        deck.addCard(cardProduto);
        deck.addCard(criarProduto);
        deck.addCard(cardCategoria);
        deck.addCard(cardRelatorio);
    }

    private void instantiateCardRelatorio(MigCard cardRelatorio) {

        cardRelatorio.setLayout(new MigLayout(
                "wrap 1, inset 20, gapy 15, align center", 
                "[grow, center]", 
                ""
        ));

        JLabel titulo = new JLabel("Gerar Relatórios");
        titulo.setFont(new Font("Arial", Font.BOLD, 18));

        JButton btnSemanal = new JButton("Gerar Relatório Semanal");
        JButton btnMensal = new JButton("Gerar Relatório Mensal");
        JButton btnAnual = new JButton("Gerar Relatório Anual");

        Dimension tamanhoBotao = new Dimension(250, 40);
        btnSemanal.setPreferredSize(tamanhoBotao);
        btnMensal.setPreferredSize(tamanhoBotao);
        btnAnual.setPreferredSize(tamanhoBotao);



        btnSemanal.addActionListener(e -> {
            File arquivo = escolherArquivoTXT("Salvar Relatório Semanal");

            if (arquivo != null) {
                String pathCompleto = arquivo.getAbsolutePath();

                // Aqui você manda para o controller:
                relatorioController.getRelatorioSemanal(pathCompleto);

                JOptionPane.showMessageDialog(
                        null,
                        "Relatório semanal gerado em:\n" + pathCompleto,
                        "Relatório Salvo",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });
        btnMensal.addActionListener(e -> {
            File arquivo = escolherArquivoTXT("Salvar Relatório Mensal");

            if (arquivo != null) {
                String pathCompleto = arquivo.getAbsolutePath();

                // Aqui você manda para o controller:
                relatorioController.getRelatorioMensal(pathCompleto);

                JOptionPane.showMessageDialog(
                        null,
                        "Relatório mensal gerado em:\n" + pathCompleto,
                        "Relatório Salvo",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });
        btnAnual.addActionListener(e -> {
            File arquivo = escolherArquivoTXT("Salvar Relatório Anual");

            if (arquivo != null) {
                String pathCompleto = arquivo.getAbsolutePath();

                // Aqui você manda para o controller:
                relatorioController.getRelatorioAnual(pathCompleto);

                JOptionPane.showMessageDialog(
                        null,
                        "Relatório anual gerado em:\n" + pathCompleto,
                        "Relatório Salvo",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });

        cardRelatorio.add(titulo, "gapbottom 20, center");
        cardRelatorio.add(btnSemanal, "center");
        cardRelatorio.add(btnMensal, "center");
        cardRelatorio.add(btnAnual, "center");
    }

    private File escolherArquivoTXT(String titulo) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle(titulo);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        // Filtro para arquivos .txt
        chooser.setFileFilter(new FileNameExtensionFilter("Arquivo .txt", "txt"));

        // Sugestão inicial
        chooser.setSelectedFile(new File("relatorio.txt"));

        int resultado = chooser.showSaveDialog(null);
        
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();

            // Garante que termina com .txt
            if (!file.getName().toLowerCase().endsWith(".txt")) {
                file = new File(file.getAbsolutePath() + ".txt");
            }

            return file;
        }
        return null;
    }



    /* -------------------- Cliente -------------------- */
    private void instantiateCardCliente(MigCard cardCliente) {
        JLabel labelFiltro = new JLabel("Filtro:");
        JFormattedTextField txtFiltro = new JFormattedTextField();
        cardCliente.add(labelFiltro);
        cardCliente.add(txtFiltro, " growx");

        MigPanel mgTable = new MigPanel("wrap, gapy 1%", "[grow]", "");
        JScrollPane sc = new JScrollPane(mgTable);
        Runnable loadTable = () -> {
            String filtro = txtFiltro.getText().toLowerCase();
            mgTable.removeAll();
            for (Cliente c : clienteController.getAll()) {
                if (c.getNome().toLowerCase().contains(filtro) || c.getCpf().toLowerCase().contains(filtro)) {
                    MigPanel row = new MigPanel("fill", "", "[grow]");
                    row.add(new JLabel(c.getNome()), "grow");
                    row.add(new JLabel(c.getCpf()), "grow");
                    row.add(new JLabel(c.getTelefone()), "grow");
                    row.add(new JLabel(c.getDataRegistro().toString()), "grow");
                    JButton excluir = new JButton("Excluir");
                    excluir.addActionListener(evt -> clienteController.delete(c.getId()));
                    row.add(excluir, "grow");
                    mgTable.add(row, "grow");
                }
            }
            cardCliente.add(sc, "grow, span 2");
        };
        loadTable.run();
        addDocumentListenerDebounced(txtFiltro.getDocument(), 150, loadTable);
    }

    /* -------------------- Funcionário -------------------- */
    private void instantiateCardFuncionario(MigCard cardFuncionario, MigCard cardCriarFuncionario) {
        JLabel labelFiltro = new JLabel("Filtro:");
        JFormattedTextField txtFiltro = new JFormattedTextField();
        MigPanel mgFiltro = new MigPanel("", "[15%][85%]", "[grow]");
        mgFiltro.add(labelFiltro);
        mgFiltro.add(txtFiltro, "grow");
        cardFuncionario.add(mgFiltro, "grow, span 2");

        MigPanel mgTable = new MigPanel("wrap", "[grow]", "");
        JScrollPane sc = new JScrollPane(mgTable);

        Runnable loadTable = () -> {
            String filtro = txtFiltro.getText().toLowerCase();
            mgTable.removeAll();
            for (Funcionario f : funcionarioController.getAll()) {
                if (f.getNome().toLowerCase().contains(filtro) || f.getCpf().toLowerCase().contains(filtro)) {
                    MigPanel row = new MigPanel("", "[grow][15%]", "[grow]");
                    row.add(new JLabel(f.getNome()), "grow");
                    row.add(new JLabel(f.getCpf()), "grow");
                    row.add(new JLabel(f.getDataNascimento().toString()), "grow");
                    row.add(new JLabel(f.getGerente() != null ? f.getGerente().getNome() : "Sem gerente"), "grow");
                    JButton excluir = new JButton("Excluir");
                    excluir.addActionListener(evt -> funcionarioController.delete(f.getId()));
                    row.add(excluir, "grow");
                    mgTable.add(row, "grow");
                }
            }
            cardFuncionario.add(sc, "grow, span 2");
            revalidate();
            repaint();
        };

        JButton changeFunction = new JButton("Cadastrar Novo Funcionário");
        changeFunction.addActionListener(e -> deck.showCard(cardCriarFuncionario));
        cardFuncionario.add(changeFunction, "span 2, growx");

        loadTable.run();
        addDocumentListenerDebounced(txtFiltro.getDocument(), 150, loadTable);
    }

    private void instantiateCardCriarFuncionario(MigCard cardCriarFuncionario, MigCard cardFuncionario) {
        JLabel labelNome = new JLabel("Nome:");
        JFormattedTextField txtNome = new JFormattedTextField();
        JLabel labelCpf = new JLabel("CPF:");
        JFormattedTextField txtCpf = new JFormattedTextField();
        JLabel labelData = new JLabel("Data Nascimento (AAAA-MM-DD):");
        JFormattedTextField txtData = new JFormattedTextField();

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
        form.add(new JLabel("Gerente:"));
        form.add(comboGerente, "growx");

        JButton btnSalvar = new JButton("Salvar");
        JButton btnVoltar = new JButton("Voltar");

        btnSalvar.addActionListener(e -> {
            Funcionario funcionario = new Funcionario();
            funcionario.setNome(txtNome.getText());
            funcionario.setCpf(txtCpf.getText());
            funcionario.setDataNascimento(Date.valueOf(txtData.getText()));
            if (comboGerente.getSelectedIndex() >= 0)
                funcionario.setGerente(gerentes.get(comboGerente.getSelectedIndex()));
            funcionarioController.create(funcionario);
            deck.showCard("Funcionário");
        });

        btnVoltar.addActionListener(e -> deck.showCard(cardFuncionario));

        cardCriarFuncionario.add(form, "grow, span 2");
        cardCriarFuncionario.add(btnSalvar, "grow");
        cardCriarFuncionario.add(btnVoltar, "grow");
    }

    /* -------------------- Produto -------------------- */
    private void instantiateCardProduto(MigCard cardProduto, MigCard criarProduto) {
        JLabel labelFiltro = new JLabel("Filtro:");
        JFormattedTextField txtFiltro = new JFormattedTextField();
        cardProduto.add(labelFiltro);
        cardProduto.add(txtFiltro, "growx");

        MigPanel mgGrid = new MigPanel("wrap 4", "[grow]", "[grow]");
        JScrollPane sc = new JScrollPane(mgGrid);

        JButton buttonCriar = new JButton("Criar Novo Produto");
        buttonCriar.addActionListener(e -> {
            instantiateCriarAtualizarProduto(criarProduto, cardProduto, null);
            deck.showCard(criarProduto);
        });

        cardProduto.add(buttonCriar, "growx, span 2");
        cardProduto.add(sc, "grow, span 2, push");

        final List<Produto> produtosCache = produtoController.getAll();

        Runnable loadGrid = () -> {
            String filtro = txtFiltro.getText().toLowerCase();
            mgGrid.removeAll();
            for (Produto produto : produtosCache) {
                if (produto.getNome().toLowerCase().contains(filtro)
                        || produto.getDescricao().toLowerCase().contains(filtro)) {
                    ItemPanel p = new ItemPanel(produto, imgS);
                    p.setHoverBackground(colorTheme.getShadowSoft());
                    p.setAction(() -> {
                        instantiateCriarAtualizarProduto(criarProduto, cardProduto, produto);
                        deck.showCard(criarProduto);
                    });
                    mgGrid.add(p, "grow, w 135, h 140");
                }
            }
            mgGrid.revalidate();
            mgGrid.repaint();
        };

        loadGrid.run();

        Timer timer = new Timer(2000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                final List<Produto> aux = produtosCache;
                produtosCache.removeAll(aux);
                produtosCache.addAll(produtoController.getAll());
            }

        });
        timer.start();

        addDocumentListenerDebounced(txtFiltro.getDocument(), 150, loadGrid);
    }

    /* -------------------- Criar / Atualizar Produto -------------------- */
    private void instantiateCriarAtualizarProduto(MigCard criarProduto, MigCard cardProduto, Produto produto) {
        criarProduto.removeAll();
        criarProduto.setLayout(new MigLayout("wrap 2, inset 10, gapx 10, gapy 8", "[right][grow, fill]", ""));

        JTextField inputNome = new JTextField();
        JFormattedTextField inputValor = formattedDoubleField(7, 0.0, 99999.99);
        JTextField inputDescricao = new JTextField();

        JComboBox<CategoriaProduto> inputCategoria = new JComboBox<>();
        categoriaProdutoController.getAll().forEach(inputCategoria::addItem);

        JSpinner inputQuantidade = new JSpinner(new SpinnerNumberModel(0, 0, 9999, 1));
        JCheckBox checkAtivo = new JCheckBox();

        // imagem
        JTextField descricaoImage = new JTextField();
        JButton btnSelecionarImagem = new JButton("Selecionar");
        JLabel preview = new JLabel();
        preview.setPreferredSize(new Dimension(150, 150));
        preview.setBorder(new RoundedBorder(5, colorTheme.getShadowSoft()));
        preview.setHorizontalAlignment(JLabel.CENTER);
        final AtomicReference<BufferedImage> imagemSelecionada = new AtomicReference<>();

        // layout add compact
        criarProduto.add(new JLabel("Nome:"));
        criarProduto.add(inputNome, "growx");
        criarProduto.add(new JLabel("Valor:"));
        criarProduto.add(inputValor, "growx");
        criarProduto.add(new JLabel("Descrição:"));
        criarProduto.add(inputDescricao, "growx");
        criarProduto.add(new JLabel("Categoria:"));
        criarProduto.add(inputCategoria, "growx");
        criarProduto.add(new JLabel("Qtd Inicial:"));
        criarProduto.add(inputQuantidade, "growx");
        criarProduto.add(new JLabel("Ativo:"));
        criarProduto.add(checkAtivo, "left");

        criarProduto.add(new JLabel("Imagem:"));
        criarProduto.add(descricaoImage, "growx");
        criarProduto.add(new JLabel("Selecionar arquivo:"));
        criarProduto.add(btnSelecionarImagem, "growx");
        criarProduto.add(new JLabel("Preview:"));
        criarProduto.add(preview, "grow");

        // selecionar imagem
        btnSelecionarImagem.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(new FileNameExtensionFilter("Imagens", "png", "jpg", "jpeg", "jfif"));
            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                try {
                    File file = chooser.getSelectedFile();
                    BufferedImage img = ImageIO.read(file);
                    imagemSelecionada.set(img);
                    descricaoImage.setText(file.getName());
                    Image scaled = img.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                    preview.setIcon(new ImageIcon(scaled));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        JButton btnVoltar = new JButton("Voltar");
        JButton btnSalvar = new JButton("Salvar");
        criarProduto.add(btnVoltar, "growx");
        criarProduto.add(btnSalvar, "growx");

        // preencher se editando
        if (produto != null) {
            inputNome.setText(produto.getNome());
            inputValor.setValue(produto.getValor());
            inputDescricao.setText(produto.getDescricao());
            inputCategoria.setSelectedItem(produto.getCategoria());
            inputQuantidade.setValue(produto.getQuantidade());
            checkAtivo.setSelected(produto.getStatusAtivo() == 1);
            if (produto.getMidia() != null) {
                try {
                    Midia m = produto.getMidia();
                    BufferedImage img = imgS.rawImageToBufferedImage(m.getArquivo());
                    imagemSelecionada.set(img);
                    preview.setIcon(new ImageIcon(img.getScaledInstance(120, 120, Image.SCALE_SMOOTH)));
                } catch (Exception ignored) {
                }
            }
        }

        btnSalvar.addActionListener(e -> {
            Produto p = produto == null ? new Produto() : produto;
            p.setNome(inputNome.getText());
            p.setValor(((Number) inputValor.getValue()).doubleValue());
            p.setDescricao(inputDescricao.getText());
            p.setCategoria((CategoriaProduto) inputCategoria.getSelectedItem());
            p.setQuantidade((Integer) inputQuantidade.getValue());
            p.setStatusAtivo(checkAtivo.isSelected() ? 1 : 0);
            p.setDataCriacao(Date.valueOf(LocalDate.now()));
            p.setCombos(p.getCombos() == null ? new ArrayList<Combo>() : p.getCombos());

            boolean novo = (produto == null);
            if (novo)
                p = produtoController.create(p);
            else
                produtoController.update(p);

            if (imagemSelecionada.get() != null) {
                try {
                    byte[] bytesImage = imgS.bufferedImageToBytes(imagemSelecionada.get(), "png");
                    Midia midia = new Midia();
                    midia.setItem(p);
                    midia.setDescricao(descricaoImage.getText());
                    midia.setArquivo(bytesImage);
                    midia.setTipo(TipoMidia.IMAGEM);
                    midia = midiaController.create(midia);
                    p.setMidia(midia);
                    produtoController.update(p);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            deck.showCard(cardProduto);
        });

        btnVoltar.addActionListener((ActionEvent e) -> deck.showCard(cardProduto));
        criarProduto.revalidate();
        criarProduto.repaint();
    }

    /* -------------------- Helpers -------------------- */
    private JFormattedTextField formattedDoubleField(int columns, double min, double max) {
        NumberFormat format = NumberFormat.getNumberInstance();
        format.setMinimumFractionDigits(0);
        format.setMaximumFractionDigits(2);
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Double.class);
        formatter.setAllowsInvalid(false);
        formatter.setMinimum(min);
        formatter.setMaximum(max);
        JFormattedTextField f = new JFormattedTextField(formatter);
        f.setColumns(columns);
        return f;
    }

    private void addDocumentListenerDebounced(Document doc, int delayMillis, Runnable action) {
        DocumentListener dl = new DocumentListener() {
            private Timer debounce;

            private void schedule() {
                if (debounce != null && debounce.isRunning())
                    debounce.stop();
                debounce = new Timer(delayMillis, e -> {
                    action.run();
                    debounce.stop();
                });
                debounce.setRepeats(false);
                debounce.start();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                schedule();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                schedule();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                schedule();
            }
        };
        doc.addDocumentListener(dl);
    }

    private void instantiateCardCategoria(LayeredOverlayCard cardCategoria) {

        MigPanel mpGrid = new MigPanel("wrap 4", "[grow]", "[][grow]");
        JButton buttonCriar = new JButton("Criar nova categoria.");
        buttonCriar.addActionListener((ActionEvent e) -> {
            MigPanel aux = new MigPanel("align 50% 50%", "[grow]", "[grow]");
            ModalCategoriaCRUD modal = new ModalCategoriaCRUD(null, cardCategoria);
            modal.setAlignmentX(LayeredOverlayCard.RIGHT_ALIGNMENT);
            modal.setAlignmentY(LayeredOverlayCard.RIGHT_ALIGNMENT);
            aux.add(modal, "grow");
            cardCategoria.add(aux, LayeredOverlayCard.SURFACE_LAYER);
        });

        JScrollPane scrollPane = new JScrollPane(mpGrid);

        cardCategoria.add(scrollPane, LayeredOverlayCard.CONTENT_LAYER);
        scrollPane.setAlignmentX(LayeredOverlayCard.CENTER_ALIGNMENT);
        scrollPane.setAlignmentY(LayeredOverlayCard.CENTER_ALIGNMENT);
       // scrollPane.setMaximumSize(new Dimension(cardCategoria.getWidth(), cardCategoria.getHeight()));
        Timer updateListTimer = new Timer(2000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                // Busca categorias do banco
                List<CategoriaProduto> categorias = categoriaProdutoController.getAll();

                // Limpa apenas o grid (não o card inteiro)
                mpGrid.removeAll();

                // Recria os itens
                categorias.forEach((categoria) -> {

                    CategoriePanel categoriePanel = new CategoriePanel(categoria, imgS);
                    categoriePanel.setBackground(colorTheme.getShadowSoft());
                    categoriePanel.setRoundedBorder(5, colorTheme.getAccent());

                    categoriePanel.setAction(() -> {
                        MigPanel aux = new MigPanel("align 50% 50%", "[grow]", "[grow]");
                        ModalCategoriaCRUD modal = new ModalCategoriaCRUD(categoria, cardCategoria);
                        modal.setAlignmentX(LayeredOverlayCard.RIGHT_ALIGNMENT);
                        modal.setAlignmentY(LayeredOverlayCard.RIGHT_ALIGNMENT);
                        aux.add(modal, "grow");
                        cardCategoria.add(aux, LayeredOverlayCard.SURFACE_LAYER);

                    });

                    categoriePanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    categoriePanel.setHoverBackground(colorTheme.getShadowSoft());

                    mpGrid.add(categoriePanel, "grow, w 200, h 200");
                });

                mpGrid.add(buttonCriar, "grow, w 200, h 200");
                // Atualiza a UI após a mudança
                mpGrid.revalidate();
                mpGrid.repaint();
            }
        });

        updateListTimer.start();
    }

    private class ModalCategoriaCRUD extends MigPanel {

        private  CategoriaProduto categoriaProduto;
        private  Boolean isNewCategoria = false; 
        public ModalCategoriaCRUD(CategoriaProduto categoriaProdutoP, LayeredOverlayCard container) {
            super("wrap 3", "[20%][60%][30%]", "[grow]");
            this.isNewCategoria = categoriaProdutoP == null;
            this.categoriaProduto = categoriaProdutoP;

            this.categoriaProduto = this.categoriaProduto == null ? new CategoriaProduto() : this.categoriaProduto;

            this.setRoundedBorder(14, colorTheme.getDark());

            JLabel labelNomeCategoria = new JLabel("Nome: ");
            JTextField inputNomeCategoria = new JTextField();
            JButton buttonFechar = new JButton("X");

            buttonFechar.addActionListener((ActionEvent e) -> {
                container.removeAllByLayer(LayeredOverlayCard.SURFACE_LAYER);

            });
            JButton buttonDeletar = new JButton("🗑");
            buttonDeletar
                    .addActionListener((ActionEvent e) -> {
                        if (JOptionPane.showConfirmDialog(null,
                                "Deseja apagar a categoria(todos os produtos serão deletados)?") == JOptionPane.YES_OPTION) {
                            categoriaProdutoController.delete(categoriaProduto.getId());
                        }
                    });

            JLabel labelSiglaCategoria = new JLabel("Sigla: ");
            JTextField inputSiglaCategoria = new JTextField(2);
            JLabel preview = new JLabel();
            preview.setPreferredSize(new Dimension(150, 150));
            preview.setBorder(new RoundedBorder(5, colorTheme.getShadowSoft()));
            preview.setHorizontalAlignment(JLabel.CENTER);
            final AtomicReference<BufferedImage> imagemSelecionada = new AtomicReference<>();

            JButton btnSelecionarImagem = new JButton("Selecionar Imagem");
            JButton btnSalvarCategoria = new JButton(isNewCategoria() ? "Salvar" : "Atualizar");

            add(labelNomeCategoria);
            add(inputNomeCategoria, "grow");
            add(buttonFechar, "grow");

            add(labelSiglaCategoria);
            add(inputSiglaCategoria, "grow");
            if (!isNewCategoria()) {
                add(buttonDeletar, "grow");
            }
            add(btnSelecionarImagem, "grow, span 3");
            add(preview, "grow, span 3");
            add(btnSalvarCategoria, "grow, span 3");

            // selecionar imagem
            btnSelecionarImagem.addActionListener(e -> {
                JFileChooser chooser = new JFileChooser();
                chooser.setFileFilter(new FileNameExtensionFilter("Imagens", "png", "jpg", "jpeg", "jfif"));
                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    try {
                        File file = chooser.getSelectedFile();
                        BufferedImage img = ImageIO.read(file);
                        imagemSelecionada.set(img);

                        Image scaled = img.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                        preview.setIcon(new ImageIcon(scaled));
                        categoriaProduto.setImagem(imgS.bufferedImageToBytes(imagemSelecionada.get(), "png" ));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });

            if (!isNewCategoria()) {
                inputNomeCategoria.setText(categoriaProduto.getNome());
                inputSiglaCategoria.setText(categoriaProduto.getSigla());
                if (categoriaProduto.getImagem() != null && categoriaProduto.getImagem().length > 0) {

                    BufferedImage buffered = imgS.rawImageToBufferedImage(categoriaProduto.getImagem());

                    if (buffered != null) {
                        Image scaled = buffered.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                        preview.setIcon(new ImageIcon(scaled));
                    } else {
                        // imagem no banco está corrompida ou não é imagem
                        preview.setIcon(null);
                    }
                }

            }

            btnSalvarCategoria.addActionListener((ActionEvent e) -> {
                categoriaProduto.setNome(inputNomeCategoria.getText());
                categoriaProduto.setSigla(inputSiglaCategoria.getText());

                if (isNewCategoria()) {

                    categoriaProdutoController.create(categoriaProduto);
                } else

                    categoriaProdutoController.update(categoriaProduto);

                container.removeAllByLayer(LayeredOverlayCard.SURFACE_LAYER);
            });

        }

        private boolean isNewCategoria() {
            return this.isNewCategoria;
        }

    }

}

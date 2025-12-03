package com.tecdes.lanchonete.view.physical.frames;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import com.tecdes.lanchonete.controller.CategoriaProdutoController;
import com.tecdes.lanchonete.controller.ComboController;
import com.tecdes.lanchonete.controller.PagamentoController;
import com.tecdes.lanchonete.controller.PedidoController;
import com.tecdes.lanchonete.controller.ProdutoController;
import com.tecdes.lanchonete.model.entity.CategoriaProduto;
import com.tecdes.lanchonete.model.entity.Funcionario;
import com.tecdes.lanchonete.model.entity.Item;
import com.tecdes.lanchonete.model.entity.Pagamento;
import com.tecdes.lanchonete.model.entity.Pedido;
import com.tecdes.lanchonete.view.logical.abstracts.DeckFrame;
import com.tecdes.lanchonete.view.logical.abstracts.card.MigCard;
import com.tecdes.lanchonete.view.logical.custom.panel.CategoriePanel;
import com.tecdes.lanchonete.view.logical.custom.panel.ImagePanel;
import com.tecdes.lanchonete.view.logical.custom.panel.ItemPanel;
import com.tecdes.lanchonete.view.logical.custom.panel.MigPanel;
import com.tecdes.lanchonete.view.logical.custom.util.ImageService;
import com.tecdes.lanchonete.view.logical.custom.util.color.ColorTheme;

public final class TokenView extends DeckFrame {

    private final ColorTheme colorTheme;
    private final CategoriaProdutoController categoriaProdutoController;
    private final ProdutoController produtoController;
    private final PedidoController pedidoController;
    private final PagamentoController pagamentoController;
    private final ImageService imageService;

    private MigCard marketCard;
    private MigPanel categoriesPanel;
    private MigPanel panelRight;
    private MigPanel itensPanel;
    private MigPanel selectedItemPanel;

    // --- carrinho ---
    private final List<Item> carrinho = new ArrayList<>();
    private MigPanel carrinhoPanel;
    private JScrollPane carrinhoScroll;

    public TokenView(
            ColorTheme colorTheme,
            CategoriaProdutoController categoriaProdutoController,
            ProdutoController produtoController,
            ComboController comboController,
            ImageService imageService,
            PagamentoController pagamentoController,
            PedidoController pedidoController) {

        super("Token view");
        this.colorTheme = colorTheme;
        this.categoriaProdutoController = categoriaProdutoController;
        this.produtoController = produtoController;
        this.pagamentoController = pagamentoController;
        this.imageService = imageService;
        this.pedidoController = pedidoController;
        initComponents();
    }

    @Override
    protected void initComponents() {
        marketCard = new MigCard("wrap, insets 5", "[25%][75%]", "[grow]", this);
        instantiateMarket();
        addCard(marketCard);
    }

    private void instantiateMarket() {
        marketCard.setBackground(colorTheme.getPrimary());

        // CATEGORIES
        categoriesPanel = new MigPanel("wrap, insets 5", "[grow]", "[grow]");
        categoriesPanel.setBackground(colorTheme.getConstrast());
        categoriesPanel.setDefaultBackground(colorTheme.getConstrast());
        categoriesPanel.setHoverBackground(colorTheme.getConstrast());
        instantiateCategories();

        JScrollPane categoriesScrollPane = new JScrollPane(categoriesPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        categoriesScrollPane.setBorder(null);
        categoriesScrollPane.getViewport().setBackground(colorTheme.getConstrast());

        // RIGHT PANEL
        panelRight = new MigPanel("wrap, insets 5", "[grow]", "[][70%][30%]"); // reserve space for carrinho + itens +
                                                                               // selected
        instantiateRigthPanel();

        marketCard.add(categoriesScrollPane, "grow, pushy");
        marketCard.add(panelRight, "grow, pushx");
    }

    private void instantiateCategories() {
        List<CategoriaProduto> categoriasProdutos = categoriaProdutoController.getAll();
        JButton buttonCategorias = new JButton("Categorias");
        buttonCategorias.setEnabled(false);
        categoriesPanel.add(buttonCategorias, "grow, span 1, wrap");
        categoriasProdutos.forEach((categoria) -> {
            CategoriePanel categoriePanel = new CategoriePanel(categoria, imageService);
            categoriePanel.setBackground(colorTheme.getWhite());
            categoriePanel.setDefaultBackground(colorTheme.getWhite());
            categoriePanel.setHoverBackground(colorTheme.getShadowSoft());
            categoriePanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            categoriePanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    fillItensPanel(produtoController.getByCategoriaProduto(categoria));
                    revalidate();
                    repaint();
                }
            });
            categoriesPanel.add(categoriePanel, "grow, wrap");
        });
    }

    private void instantiateRigthPanel() {
        // --- CARRINHO (inicialmente escondido) ---
        carrinhoPanel = new MigPanel("wrap, insets 5", "[grow]", "");
        carrinhoPanel.setBackground(colorTheme.getWhite());

        carrinhoScroll = new JScrollPane(carrinhoPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        carrinhoScroll.setBorder(null);
        carrinhoScroll.getViewport().setBackground(colorTheme.getWhite());
        carrinhoScroll.setVisible(false); // aparece quando o primeiro item é adicionado

        panelRight.add(carrinhoScroll, "growx, h 120!"); // altura fixa para não alterar demais layout

        // Painel de itens
        itensPanel = new MigPanel("wrap 4, insets 5", "[grow, grow, grow, grow]", "");
        itensPanel.setBackground(colorTheme.getWhite());
        fillItensPanel(produtoController.getAll());

        JScrollPane itensScrollPanel = new JScrollPane(itensPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        itensScrollPanel.setBorder(null);
        itensScrollPanel.getViewport().setBackground(colorTheme.getWhite());

        panelRight.add(itensScrollPanel, "grow, push");

        // Painel do item selecionado
        selectedItemPanel = new MigPanel("insets 10, align 50% 50%", "[grow][grow][grow]", "");
        selectedItemPanel.setBackground(colorTheme.getConstrast());
        panelRight.add(selectedItemPanel, "grow, pushy");
    }

    private void fillItensPanel(List<? extends Item> itens) {
        itensPanel.removeAll();

        if (itens == null || itens.isEmpty()) {
            JLabel vazio = new JLabel("Nenhum produto encontrado.");
            itensPanel.add(vazio, "grow, span 4, align center");
        } else {
            itens.forEach((item) -> {
                ItemPanel itemPanel = new ItemPanel(item, imageService);
                itemPanel.setBackground(colorTheme.getWhite());
                itemPanel.setDefaultBackground(colorTheme.getWhite());
                itemPanel.setHoverBackground(colorTheme.getShadowSoft());
                itemPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));

                // Ação ao clicar — ItemPanel já expõe setAction
                itemPanel.setAction(() -> cardBarSelectedItem(item));

                // cada item ocupa uma célula; wrap controlado por "wrap 4" do itensPanel
                itensPanel.add(itemPanel, "grow, w 150!, h 150!");
            });
        }

        // atualizar UI
        itensPanel.revalidate();
        itensPanel.repaint();
    }

    private void cardBarSelectedItem(Item item) {

        selectedItemPanel.removeAll();

        // ============================
        // BLOCO SUPERIOR (IMAGEM + NOME)
        // ============================
        MigPanel mg1 = new MigPanel("insets 0", "[grow][grow]", "");
        JLabel labelItem = new JLabel(item.getNome());

        ImagePanel itemPanel = new ImagePanel(
                imageService.rawImageToBufferedImage(item.getMidia().getArquivo()));
        itemPanel.setPreferredSize(new Dimension(150, 150));

        mg1.add(labelItem, "growx, align left");
        mg1.add(itemPanel, "growx, align center");

        // ============================
        // CONTROLE DE QUANTIDADE
        // ============================
        MigPanel mg2 = new MigPanel("insets 10 0", "[30%][70%]", "");

        JLabel labelQtd = new JLabel("Quantidade: ");

        // Spinner SUAVE + robusto
        SpinnerNumberModel model = new SpinnerNumberModel(1, 1, 9999, 1);
        JSpinner spinnerQtd = new JSpinner(model);
        ((JSpinner.DefaultEditor) spinnerQtd.getEditor()).getTextField().setEditable(false); // evita glitches

        mg2.add(labelQtd, "grow");
        mg2.add(spinnerQtd, "grow");

        // ============================
        // BOTÕES: VALOR + ADICIONAR
        // ============================
        MigPanel mg3 = new MigPanel("insets 0", "[grow]", "[grow][grow]");

        // formato seguro para valores
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        JButton buttonValor = new JButton(nf.format(item.getValor()));
        buttonValor.setEnabled(false);

        JButton buttonAdicionar = new JButton("Adicionar");

        mg3.add(buttonValor, "grow, gaptop 10");
        mg3.add(buttonAdicionar, "grow");

        // ============================
        // ATUALIZA VALOR AO TROCAR QUANTIDADE
        // ============================
        spinnerQtd.addChangeListener(e -> {
            int qtd = (Integer) spinnerQtd.getValue();
            double total = qtd * item.getValor();
            buttonValor.setText(nf.format(total));
        });

        // ============================
        // AÇÃO DO BOTAO ADICIONAR (carrinho)
        // ============================
        buttonAdicionar.addActionListener(ev -> {
            int qtd = (Integer) spinnerQtd.getValue();
            adicionarAoCarrinho(item, qtd);
            if (!carrinhoScroll.isVisible()) {
                carrinhoScroll.setVisible(true);
            }
            atualizarCarrinhoUI();
        });

        // ============================
        // ADICIONA TUDO NO PAI
        // ============================
        selectedItemPanel.add(mg1, "growx");
        selectedItemPanel.add(mg2, "growx, h 100!");
        selectedItemPanel.add(mg3, "growx, gapbottom 20");

        selectedItemPanel.revalidate();
        selectedItemPanel.repaint();
    }

    // --- adiciona item ao carrinho; agrega por id quando já existe ---
    private void adicionarAoCarrinho(Item itemOriginal, int quantidade) {
        if (itemOriginal == null)
            return;

        // procura item no carrinho pelo id
        Item existente = null;
        if (itemOriginal.getId() != null) {
            for (Item it : carrinho) {
                if (itemOriginal.getId().equals(it.getId())) {
                    existente = it;
                    break;
                }
            }
        }

        if (existente != null) {
            existente.setQuantidade(existente.getQuantidade() + quantidade);
        } else {
            // cria uma cópia leve para o carrinho (não altera o original da grade)
            Item cartItem = new Item();
            cartItem.setId(itemOriginal.getId());
            cartItem.setNome(itemOriginal.getNome());
            cartItem.setValor(itemOriginal.getValor());
            cartItem.setMidia(itemOriginal.getMidia());
            cartItem.setQuantidade(quantidade);
            carrinho.add(cartItem);
        }
    }

    // --- atualiza a UI do carrinho ---
    private void atualizarCarrinhoUI() {
        carrinhoPanel.removeAll();

        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        if (carrinho.isEmpty()) {
            carrinhoScroll.setVisible(false);
        } else {
            // lista de itens
            for (Item ic : carrinho) {
                MigPanel linha = new MigPanel("insets 5", "[30%][50%][20%]", "");
                linha.setBackground(colorTheme.getWhite());

                // mini-thumbnail
                ImagePanel thumb = new ImagePanel(imageService.rawImageToBufferedImage(
                        ic.getMidia() != null ? ic.getMidia().getArquivo() : null));
                thumb.setPreferredSize(new Dimension(50, 50));

                JLabel nome = new JLabel(ic.getNome() + " x" + ic.getQuantidade());
                double total = ic.getQuantidade() * ic.getValor();
                JLabel valor = new JLabel(nf.format(total));

                linha.add(thumb, "grow, w 50!, h 50!");
                linha.add(nome, "growx");
                linha.add(valor, "align right");

                carrinhoPanel.add(linha, "growx, wrap");
            }

            // botão de checkout no final
            JButton btnCheckout = new JButton("Ir para o checkout");
            btnCheckout.addActionListener(e -> abrirCheckout());

            carrinhoPanel.add(btnCheckout, "growx, gaptop 8");
        }

        carrinhoPanel.revalidate();
        carrinhoPanel.repaint();
    }

    private void abrirCheckout() {
        MigCard checkoutCard = new MigCard("wrap, insets 20", "[grow]", "[grow]", "Checkout", this);

        MigPanel content = new MigPanel("wrap, insets 10", "[grow]", "[grow]");
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        double soma = 0;
        for (Item it : carrinho) {
            soma += it.getQuantidade() * it.getValor();
        }

        content.add(new JLabel("Checkout"), "grow, wrap");
        content.add(new JLabel("Total: " + nf.format(soma)), "grow, wrap");

        // ======= MÉTODO DE PAGAMENTO DINÂMICO =======
        content.add(new JLabel("Selecione o método de pagamento:"), "grow, wrap");

        ButtonGroup grupoPagamento = new ButtonGroup();

        // Pega do banco
        List<Pagamento> pagamentos = pagamentoController.getAll();
        List<JRadioButton> radios = new ArrayList<>();

        for (Pagamento pg : pagamentos) {
            JRadioButton rb = new JRadioButton(pg.getNome());
            rb.setActionCommand(pg.getId().toString()); // salva o ID como actionCommand
            grupoPagamento.add(rb);
            radios.add(rb);
            content.add(rb, "grow, wrap");
        }

        // Botões
        JButton btnConfirmar = new JButton("Confirmar Pedido");
        JButton btnVoltar = new JButton("Voltar");

        content.add(btnConfirmar, "grow, split 2");
        content.add(btnVoltar, "grow");

        checkoutCard.add(content, "grow");
        addCard(checkoutCard);
        showCard("Checkout");

        // ======= AÇÃO VOLTAR =======
        btnVoltar.addActionListener(ev -> showCard(marketCard));

        // ======= AÇÃO CONFIRMAR =======
        btnConfirmar.addActionListener(ev -> {
            if (carrinho.isEmpty()) {
                JOptionPane.showMessageDialog(this, "O carrinho está vazio!", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Verifica método selecionado
            ButtonModel selectedModel = grupoPagamento.getSelection();
            if (selectedModel == null) {
                JOptionPane.showMessageDialog(this, "Selecione um método de pagamento!", "Aviso",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            Long pagamentoId = Long.parseLong(selectedModel.getActionCommand());
            Pagamento pagamentoEscolhido = pagamentoController.getById(pagamentoId);

            // Cria Pedido
            Pedido pedido = new Pedido();
            pedido.setItens(new ArrayList<>(carrinho));
            pedido.setPagamento(pagamentoEscolhido);
            pedido.setDataPedido(new java.sql.Date(System.currentTimeMillis()));
            pedido.setStatusPedido('P'); // P de Pendente
            pedido.setNomeCliente(""); // opcional: informar nome ou cliente logado
            pedido.setCliente(null); // opcional: cliente logado
            Funcionario f = new Funcionario();
            f.setId(4l);
            pedido.setFuncionario(f);
            pedidoController.create(pedido); // salva no banco

            JOptionPane.showMessageDialog(this, "Pedido realizado com sucesso!", "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);

            // Limpa carrinho
            carrinho.clear();
            atualizarCarrinhoUI();
            showCard(marketCard);
        });
    }

}

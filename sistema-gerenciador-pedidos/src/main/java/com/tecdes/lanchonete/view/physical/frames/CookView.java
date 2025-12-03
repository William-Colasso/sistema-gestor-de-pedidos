package com.tecdes.lanchonete.view.physical.frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Panel;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import java.awt.event.MouseEvent;

import com.tecdes.lanchonete.controller.PedidoController;
import com.tecdes.lanchonete.model.entity.Pedido;
import com.tecdes.lanchonete.view.logical.abstracts.AbstractFrame;

public final class CookView extends AbstractFrame {

    private final PedidoController pedidoController;

    public CookView(PedidoController pedidoController) {
        super("Cook View");
        this.pedidoController = pedidoController;
        initComponents();
    }

    public CookView(PedidoController pedidoController, String title) {
        super(title);
        this.pedidoController = pedidoController;
        initComponents();
    }

    @Override
    protected void initComponents() {
        setLayout(new BorderLayout());

        // Painel principal com BoxLayout em Y
        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));
        painelPrincipal.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Adiciona o painel dentro de um Scroll
        JScrollPane scroll = new JScrollPane(painelPrincipal);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        add(scroll, BorderLayout.CENTER);

        // Recupera pedidos pendentes
        List<Pedido> listaPedidos = pedidoController.getByStatusPedido('P');

        listaPedidos.sort(Comparator.comparing(Pedido::getId));


        if (listaPedidos.isEmpty()) {
            painelPrincipal.add(new JLabel("Nenhum pedido pendente."));
            return;
        }
        // Pedido mais antigo
        Pedido pedidoMaisAntigo = listaPedidos.get(0);
        // Pedido em destaque
        JPanel painelDestaque = createLargeCard(pedidoMaisAntigo);
        painelDestaque.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelPrincipal.add(painelDestaque);
        // Se houver mais pedidos, adiciona acima
        if (listaPedidos.size() > 1) {
            JPanel painelOutros = new JPanel();
            painelOutros.setLayout(new BoxLayout(painelOutros, BoxLayout.Y_AXIS));
            painelOutros.setAlignmentX(Component.CENTER_ALIGNMENT);

            painelOutros.add(new JLabel("Próximos pedidos:"));
            painelOutros.add(Box.createVerticalStrut(10));

            listaPedidos.stream()
                    .skip(1)
                    .forEach(p -> painelOutros.add(createSmallCard(p)));

            painelPrincipal.add(painelOutros);
            painelPrincipal.add(Box.createVerticalStrut(20));
        }

    }

    // =====================================================================================
    // POPUP DE AÇÕES DO PEDIDO
    // =====================================================================================
    private void abrirPopupAcoes(Pedido pedido) {
        String[] options = { "Marcar como Feito", "Cancelar Pedido", "Fechar" };

        int escolha = JOptionPane.showOptionDialog(
                this,
                "O que deseja fazer com o Pedido #" + pedido.getId() + "?",
                "Ações do Pedido",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]);

        switch (escolha) {
            case 0 -> marcarPedidoComoFeito(pedido);
            case 1 -> cancelarPedido(pedido);
            default -> {
                /* fechar */ }
        }
    }

    private void marcarPedidoComoFeito(Pedido pedido) {
        pedido.setStatusPedido('E');
        pedidoController.update(pedido); // Ajuste conforme sua lógica
        JOptionPane.showMessageDialog(this, "Pedido marcado como concluído.");
        dispose();
        new CookView(pedidoController).setVisible(true);
    }

    private void cancelarPedido(Pedido pedido) {
        pedido.setStatusPedido('C');
        pedidoController.update(pedido); // Ajuste conforme sua lógica
        JOptionPane.showMessageDialog(this, "Pedido cancelado.");
        dispose();
        new CookView(pedidoController).setVisible(true);
    }

    // =====================================================================================
    // CARDS DE PEDIDOS
    // =====================================================================================

    private JPanel createLargeCard(Pedido pedido) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2, true),
                new EmptyBorder(15, 20, 15, 20)));
        panel.setBackground(new Color(255, 245, 200)); // destaque

        JLabel titulo = new JLabel("Pedido Prioritário #" + pedido.getId());
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(titulo);
        panel.add(Box.createVerticalStrut(10));

        pedido.getItens().forEach(i -> {
            JLabel lbl = new JLabel("- " + i.getNome());
            lbl.setFont(new Font("Arial", Font.PLAIN, 16));
            panel.add(lbl);
        });

        adicionarCliquePanel(panel, pedido);

        return panel;
    }

    private JPanel createSmallCard(Pedido pedido) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1, true),
                new EmptyBorder(10, 15, 10, 15)));
        panel.setBackground(new Color(240, 240, 240));

        JLabel titulo = new JLabel("Pedido #" + pedido.getId());
        titulo.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(titulo);

        pedido.getItens().forEach(i -> panel.add(new JLabel(" • " + i.getNome())));

        panel.add(Box.createVerticalStrut(10));

        adicionarCliquePanel(panel, pedido);

        return panel;
    }

    // =====================================================================================
    // Listener de clique
    // =====================================================================================
    private void adicionarCliquePanel(JPanel panel, Pedido pedido) {
        panel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                abrirPopupAcoes(pedido);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                panel.setBackground(panel.getBackground().darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // retorna cor original dependendo do tipo
                if (panel.getBorder() instanceof javax.swing.border.CompoundBorder) {
                    if (((CompoundBorder) panel.getBorder()).getInsideBorder() instanceof EmptyBorder) {
                        panel.setBackground(panel.getBackground().brighter());
                    }
                }
            }
        });
    }
}

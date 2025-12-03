package com.tecdes.lanchonete.view.physical.frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Panel;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

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

        // Painel geral da tela
        setLayout(new BorderLayout());
        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));
        painelPrincipal.setBorder(new EmptyBorder(20, 20, 20, 20));
        add(painelPrincipal, BorderLayout.CENTER);

        // Recupera pedidos pendentes
        List<Pedido> listaPedidos = pedidoController.getByStatusPedido('P');

        if (listaPedidos.isEmpty()) {
            painelPrincipal.add(new JLabel("Nenhum pedido pendente."));
            return;
        }

        // O primeiro da lista é o mais antigo
        Pedido pedidoMaisAntigo = listaPedidos.get(0);

        // Se tiver mais pedidos, lista-os acima
        if (listaPedidos.size() > 1) {
            JPanel painelOutros = new JPanel();
            painelOutros.setLayout(new BoxLayout(painelOutros, BoxLayout.Y_AXIS));
            painelOutros.setAlignmentX(Component.CENTER_ALIGNMENT);

            painelOutros.add(new JLabel("Próximos pedidos:"));
            listaPedidos.stream()
                        .skip(1)
                        .forEach(p -> painelOutros.add(constructPedidoCardSmall(p)));

            painelPrincipal.add(painelOutros);
            painelPrincipal.add(Box.createVerticalStrut(20)); // espaço antes do principal
        }

        // Painel central para colocar o pedido mais antigo em destaque
        JPanel painelDestaque = constructPedidoCardLarge(pedidoMaisAntigo);
        painelDestaque.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelPrincipal.add(painelDestaque);
    }

    // Card para o pedido mais antigo (maior e destacado)
    private JPanel constructPedidoCardLarge(Pedido pedido) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2, true),
                new EmptyBorder(15, 20, 15, 20)
        ));
        panel.setBackground(new Color(255, 245, 200)); // leve amarelado para destaque

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

        return panel;
    }

    // Cards menores para lista de pedidos acima
    private JPanel constructPedidoCardSmall(Pedido pedido) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setBackground(new Color(240, 240, 240));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titulo = new JLabel("Pedido #" + pedido.getId());
        titulo.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(titulo);

        pedido.getItens().forEach(i -> panel.add(new JLabel(" • " + i.getNome())));

        panel.add(Box.createVerticalStrut(10));

        return panel;
    }

}

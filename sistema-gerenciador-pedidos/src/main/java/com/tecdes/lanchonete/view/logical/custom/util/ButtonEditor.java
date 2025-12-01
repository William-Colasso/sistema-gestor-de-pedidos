package com.tecdes.lanchonete.view.logical.custom.util;

import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.tecdes.lanchonete.controller.ClienteController;
import com.tecdes.lanchonete.model.entity.Cliente;

import net.miginfocom.swing.MigLayout;

public  class ButtonEditor extends DefaultCellEditor {

    private JPanel panel = new JPanel(new MigLayout("insets 0", "[grow][grow]", ""));
    private JButton btnAtualizar = new JButton("Atualizar");
    private JButton btnExcluir = new JButton("Excluir");

    private ClienteController controller;
    private JTable tabela;
    private DefaultTableModel model;

    public ButtonEditor(JCheckBox checkBox, ClienteController controller,
            JTable tabela, DefaultTableModel model) {

        super(checkBox);
        this.controller = controller;
        this.tabela = tabela;
        this.model = model;

        panel.add(btnAtualizar, "grow");
        panel.add(btnExcluir, "grow");

        btnAtualizar.addActionListener(e -> {
            int row = tabela.getSelectedRow();
            Long id = (Long) model.getValueAt(row, 0);

            Cliente cliente = controller.getById(id);
            System.out.println("Atualizar cliente: " + cliente.getNome());
            // aqui você pode abrir um modal MigCard para editar
        });

        btnExcluir.addActionListener(e -> {
            int row = tabela.getSelectedRow();
            Long id = (Long) model.getValueAt(row, 0);

            controller.delete(id);
            model.removeRow(row);

            System.out.println("Cliente removido: " + id);
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        return panel;
    }

    @Override
    public Object getCellEditorValue() {
        return "";
    }

    public JPanel getPanel() {
        return panel;
    }

    public void setPanel(JPanel panel) {
        this.panel = panel;
    }
}

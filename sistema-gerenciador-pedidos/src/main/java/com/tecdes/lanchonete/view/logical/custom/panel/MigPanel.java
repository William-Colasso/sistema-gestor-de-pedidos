package com.tecdes.lanchonete.view.logical.custom.panel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

import javax.swing.JPanel;


import com.tecdes.lanchonete.view.logical.custom.util.RoundedBorder;

import net.miginfocom.swing.MigLayout;

public class MigPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private Color hoverBackground;
    private Color hoverForeground;
    private Color defaultBackground;
    private Color defaultForeground;
    private Color pressedBackground;
    private Color pressedForeground;

    private Runnable action = null;

    public MigPanel(String layoutConstraints, String columnConstraints, String rowConstraints,
            Component... components) {
        super(new MigLayout(layoutConstraints, columnConstraints, rowConstraints));
        for (Component component : components) {
            add(component);
        }
        init();
    }

    public MigPanel(String layoutConstraints, String columnConstraints, String rowConstraints) {
        super(new MigLayout(layoutConstraints, columnConstraints, rowConstraints));
        init();
    }

    public MigPanel(String layoutConstraints, String columnConstraints) {
        super(new MigLayout(layoutConstraints, columnConstraints));
        init();
    }

    public MigPanel(String layoutConstraints) {
        super(new MigLayout(layoutConstraints));
        init();
    }

    private void init() {
        // garantir que valores default reflitam o estado atual do componente
        this.defaultBackground = super.getBackground();
        this.defaultForeground = super.getForeground();
        this.hoverBackground = this.defaultBackground;
        this.hoverForeground = this.defaultForeground;
        this.pressedBackground = this.defaultBackground;
        this.pressedForeground = this.defaultForeground;

        setOpaque(true);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                final Runnable r = getAction();
                if (r != null) {
                    try {
                        r.run();
                    } catch (Exception ex) {
                        // evitar que exceção quebre a UI; log simples
                        ex.printStackTrace();
                    }
                }
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                setForeground(getPressedForeground());
                setBackground(getPressedBackground());
                repaint();

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setBackground(getHoverBackground());
                setForeground(getHoverForeground());
                repaint();
                
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(getHoverBackground());
                setForeground(getHoverForeground());
                repaint();

            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(getDefaultBackground());
                setForeground(getDefaultForeground());
                repaint();

            }
        });
    }

    public Color getHoverBackground() {
        return hoverBackground;
    }

    public void setHoverBackground(Color hoverBackground) {
        this.hoverBackground = hoverBackground;
    }

    public Color getHoverForeground() {
        return hoverForeground;
    }

    public void setHoverForeground(Color hoverForeground) {
        this.hoverForeground = hoverForeground;
    }

    public Color getDefaultBackground() {
        return defaultBackground;
    }

    public void setDefaultBackground(Color defaultBackground) {
        this.defaultBackground = defaultBackground;
        // manter estado visual consistente
        super.setBackground(defaultBackground);
        revalidate();
        repaint();
    }

    public Color getDefaultForeground() {
        return defaultForeground;
    }

    public void setDefaultForeground(Color defaultForeground) {
        this.defaultForeground = defaultForeground;
        super.setForeground(defaultForeground);
        revalidate();
        repaint();
    }

    public Color getPressedBackground() {
        return pressedBackground;
    }

    public void setPressedBackground(Color pressedBackground) {
        this.pressedBackground = pressedBackground;
    }

    public Color getPressedForeground() {
        return pressedForeground;
    }

    public void setPressedForeground(Color pressedForeground) {
        this.pressedForeground = pressedForeground;
    }

    public Runnable getAction() {
        return action;
    }

    public void setAction(Runnable action) {
        this.action = action;
    }

    public void setMigLayout(MigLayout migLayout) {
        setLayout(migLayout);
    }

    public void setRoundedBorder(int pxRadius, Color color, Insets insets) {
        setBorder(new RoundedBorder(pxRadius, color, insets));
        revalidate();
        repaint();
    }

    public void setRoundedBorder(int pxRadius, Color color) {
        setBorder(new RoundedBorder(pxRadius, color));
        revalidate();
        repaint();
    }

    public void setRoundedBorder(int pxRadius) {
        setBorder(new RoundedBorder(pxRadius, getBackground()));
        revalidate();
        repaint();
    }

    @Override
    public void setBackground(Color background) {
        // null-safe comparison para evitar NPE
        if (Objects.equals(background, super.getBackground())) {
            repaint();
        } else {
            super.setBackground(background);
            repaint();
        }
    }

    @Override
    public void setForeground(Color foreground) {
        if (Objects.equals(foreground, super.getForeground())) {
            repaint();
        } else {
            super.setForeground(foreground);
            repaint();
        }
    }
}

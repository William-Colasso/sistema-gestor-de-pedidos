package com.tecdes.lanchonete.view.logical.custom.panel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import com.tecdes.lanchonete.view.logical.custom.util.RoundedBorder;

import net.miginfocom.swing.MigLayout;

public class MigPanel extends JPanel {

    private Color hoverBackground = getBackground();
    private Color hoverForeground = getForeground();
    private Color defaultBackground = getBackground();
    private Color defaultForeground = getForeground();
    private Color pressedBackground = getBackground();
    private Color pressedForeground = getForeground();

    private Runnable action = null;

    public MigPanel(String layoutConstraints, String columnConstraints, String rowConstraints,
            Component... components) {
        super(new MigLayout(layoutConstraints, columnConstraints, rowConstraints));
        for (Component component : components) {
            add(component);
        }
    }

    public MigPanel(String layoutConstraints, String columnConstraints, String rowConstraints) {
        super(new MigLayout(layoutConstraints, columnConstraints, rowConstraints));
    }

    public MigPanel(String layoutConstraints, String columnConstraints) {
        super(new MigLayout(layoutConstraints, columnConstraints));
    }

    public MigPanel(String layoutConstraints) {
        super(new MigLayout(layoutConstraints));
    }

    public void setMigLayout(MigLayout migLayout) {
        setLayout(migLayout);
    }

    public void setRoundedBorder(int pxRadius, Color color, Insets insets) {
        setBorder(new RoundedBorder(pxRadius, color, insets));
    }

    public void setRoundedBorder(int pxRadius, Color color) {
        setBorder(new RoundedBorder(pxRadius, color));
    }

    public void setRoundedBorder(int pxRadius) {
        setBorder(new RoundedBorder(pxRadius, getBackground()));
    }

    {
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

                final Runnable r = getAction();
                if (r != null) {
                    r.run();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                setForeground(getPressedForeground());
                setBackground(getPressedBackground());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setBackground(getHoverBackground());
                setForeground(getHoverForeground());
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(getHoverBackground());
                setForeground(getHoverForeground());
            }

            @Override
            public void mouseExited(MouseEvent e) {

                setBackground(getDefaultBackground());
                setForeground(getDefaultForeground());
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
    }

    public Color getDefaultForeground() {
        return defaultForeground;
    }

    public void setDefaultForeground(Color defaultForeground) {
        this.defaultForeground = defaultForeground;
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

    @Override
    public void setBackground(Color background) {

        if (background.equals(this.getBackground())) {
            
            repaint();
        } else {
            super.setBackground(background);
            revalidate();
            repaint();
        }
    }

    @Override
    public void setForeground(Color foreground) {
        if (foreground.equals(this.getForeground())) {
            
            repaint();
        } else {
            super.setForeground(foreground);
            revalidate();
            repaint();
        }

    }

}

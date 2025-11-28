package com.tecdes.lanchonete.view.custom.util.color;

import java.awt.Color;

public abstract class ColorTheme {

    public ColorTheme(Color constrast, Color dark, Color white, Color primary, Color secondary, Color transparent) {
        this.constrast = constrast;
        this.dark = dark;
        this.white = white;
        this.primary = primary;
        this.secondary = secondary;
        this.transparent = transparent;
    }

    private Color constrast;
    private Color dark;
    private Color white;
    private Color primary;
    private Color secondary;
    private Color transparent;

    public Color getConstrast() {
        return constrast;
    }

    public Color getDark() {
        return dark;
    }

    public Color getWhite() {
        return white;
    }

    public Color getPrimary() {
        return primary;
    }

    public Color getSecondary() {
        return secondary;
    }

    public Color getTransparent() {
        return transparent;
    }

}

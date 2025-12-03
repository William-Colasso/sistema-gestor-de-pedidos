package com.tecdes.lanchonete.view.logical.custom.util.color;

import java.awt.Color;

public abstract class ColorTheme {

    public ColorTheme(Color constrast, Color dark, Color white, Color primary, Color secondary, Color tertiary, Color transparent, Color shadowSoft, Color shadowStrong, Color accent) {
        this.constrast = constrast;
        this.dark = dark;
        this.white = white;
        this.primary = primary;
        this.secondary = secondary;
        this.transparent = transparent;
        this.shadowSoft =shadowSoft;
        this.shadowStrong = shadowStrong;
        this.tertiary = tertiary;
        this.accent = accent;
        
    }

    private final Color constrast;
    private final Color dark;
    private final Color white;
    private final Color primary;
    private final Color secondary;
    private final Color transparent;
    private final Color tertiary;
    private final Color shadowSoft;
    private final Color shadowStrong;
    private final Color accent;

    public  Color getConstrast() {
        return this.constrast;
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

  
    public Color getTertiary() {
        return tertiary;
    }

   
    public Color getShadowSoft() {
        return shadowSoft;
    }

    

    public Color getShadowStrong() {
        return shadowStrong;
    }

    public Color getAccent() {
        return accent;
    }

   

}

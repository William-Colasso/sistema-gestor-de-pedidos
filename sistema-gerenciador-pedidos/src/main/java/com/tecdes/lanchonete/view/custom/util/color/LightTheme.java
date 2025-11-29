package com.tecdes.lanchonete.view.custom.util.color;

import java.awt.Color;

public class LightTheme extends ColorTheme {

    public LightTheme() {
        super(
            new Color(200, 42, 58),          // contrast
            new Color(30, 27, 24),           // dark
            new Color(250, 250, 250),        // white
            new Color(250, 236, 221),        // primary
            new Color(232, 198, 173),        // secondary
            new Color(214, 168, 145),        // tertiary
            new Color(255, 255, 255, 0),     // transparent
            new Color(0, 0, 0, 20),          // shadowSoft
            new Color(0, 0, 0, 46),           // shadowStrong
            new Color(255, 116, 92)         //Accent
        );
    }

}

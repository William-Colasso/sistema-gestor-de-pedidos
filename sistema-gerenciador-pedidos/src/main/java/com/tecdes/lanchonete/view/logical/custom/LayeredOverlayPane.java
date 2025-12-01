package com.tecdes.lanchonete.view.logical.custom;

import java.awt.Component;

import javax.swing.JLayeredPane;
import javax.swing.OverlayLayout;

public class LayeredOverlayPane extends JLayeredPane {

    private final OverlayLayout overlayLayout = new OverlayLayout(this);
    public final Float CENTER = CENTER_ALIGNMENT;
    public final Float LEFT = LEFT_ALIGNMENT;
    public final Float TOP = TOP_ALIGNMENT;
    public final Float BOTTOM = BOTTOM_ALIGNMENT;
    public final Float RIGHT = RIGHT_ALIGNMENT;

    public final Integer BACKGROUND_LAYER = 0;
    public final Integer CONTENT_LAYER = 1;
    public final Integer SURFACE_LAYER = 2;


    public LayeredOverlayPane() {
        super();
        setLayout(overlayLayout);

    }

    public void add(Component component, Integer layer) {
        super.add(component, layer);
        revalidate();
    }

    

    
    

}

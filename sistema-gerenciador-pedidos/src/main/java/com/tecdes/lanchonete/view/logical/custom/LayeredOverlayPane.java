package com.tecdes.lanchonete.view.logical.custom;

import java.awt.Component;
import java.util.List;

import javax.swing.JLayeredPane;
import javax.swing.OverlayLayout;

public class LayeredOverlayPane extends JLayeredPane {

    private  final OverlayLayout overlayLayout = new OverlayLayout(this);
    public static final Float CENTER = CENTER_ALIGNMENT;
    public static final Float LEFT = LEFT_ALIGNMENT;
    public static final Float TOP = TOP_ALIGNMENT;
    public static final Float BOTTOM = BOTTOM_ALIGNMENT;
    public static final Float RIGHT = RIGHT_ALIGNMENT;

    public static final Integer BACKGROUND_LAYER = 0;
    public static final Integer CONTENT_LAYER = 1;
    public static final Integer SURFACE_LAYER = 2;


    public LayeredOverlayPane() {
        super();
        setLayout(overlayLayout);

    }

    public void add(Component component, Integer layer) {
        component.setSize(getWidth(), getHeight());
        super.add(component, layer);
        repaint();
    }

   
 



    public void removeAllByLayer(Integer layer){

        List<Component> componentes = List.of( this.getComponentsInLayer(layer));

        

        for (Component component : componentes) {
            remove(component);
        }

        repaint();
    }
    

    
    

}

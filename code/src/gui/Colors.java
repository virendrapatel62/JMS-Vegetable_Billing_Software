package gui;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public enum Colors {
    //BG(new Color(14 , 99,102)),
    BG(new Color(255, 148, 56)),
    TOOLBAR(new Color(139 , 216 , 242)),
    //BOTTON(new Color(14 , 99,102)),
    BOTTON(new Color(255, 148, 56)),
    FONT(new Color(139 , 216 , 242)),
    MENUBAR(new Color(14 , 99, 102)),
    SHADOW(new Color(14 , 99, 102)),
    BORDER(Color.white);
    
     private Color color;
    private Colors() {
    }

    private Colors(Color color ) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    
    
    

    
    
    
    
}


package gui;

import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.plaf.synth.SynthLookAndFeel;

public enum Theme {
    STYLE0(0 , UIManager.getSystemLookAndFeelClassName()) ,
    STYLE1(1 , new NimbusLookAndFeel()) ,
    STYLE2(2 ,"com.sun.java.swing.plaf.motif.MotifLookAndFeel"),
    STYLE3(3 , "com.sun.java.swing.plaf.windows.WindowsLookAndFeel"),
    STYLE4(4 , new MetalLookAndFeel()),
    STYLE5(5 , new SynthLookAndFeel());
    
    private int id;
    private Object theme;
    Theme(int id , Object theme){
        this.id  = id;
        this.theme = theme;
    }
    
    public static Object getTheme(int id){
        switch(id){
            case 0:
                return Theme.STYLE0.theme;
            case 1:
                return Theme.STYLE1.theme;
            case 2:
                return Theme.STYLE2.theme;
            case 3:
                return Theme.STYLE3.theme;
            case 4:
                return Theme.STYLE4.theme;
            case 5:
                return Theme.STYLE5.theme;
        }
        return Theme.STYLE1.theme;
        
    }
    
}

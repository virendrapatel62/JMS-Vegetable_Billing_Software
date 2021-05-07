package start;
import com.sun.prism.paint.Color;
import controllers.Excel;
import controllers.SendMail;
import gui.Colors;
import gui.MainFrame;
import gui.Theme;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.basic.BasicLookAndFeel;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.multi.MultiLookAndFeel;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.plaf.synth.SynthLookAndFeel;
import models.Address;
import models.Bill;
import models.BillDescription;
import models.Colony;
import models.Item;
import models.User;
public class JMS {
    private static Preferences prefs = Preferences.userRoot();
        public static void main(String[] args) throws UnsupportedLookAndFeelException{
            
            
              if(prefs.getBoolean("laf", false)){
               try{
                 UIManager.setLookAndFeel((LookAndFeel)Theme.getTheme(Preferences.userRoot().getInt("theme", 1)));
               }catch(Exception e){
                    try{
                 UIManager.setLookAndFeel((String)Theme.getTheme(Preferences.userRoot().getInt("theme", 1)));
                   }catch(Exception ex){

                   }   
               }
              }
            
            SwingUtilities.invokeLater(()->{
                    new MainFrame();
                
            });
        }
        
        
        
            
            
        
}


//save.setBackground(new Color(255, 148, 56));


package gui.menubars;

import gui.MainFrame;
import gui.OrderForm;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.prefs.Preferences;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultFormatter;

public class OrderFrameMenuBar extends JMenuBar{

    private final JMenu size;
    private final JMenu colors;
    private final JMenuItem billSize;
    private final JSpinner billSizeInput;
    private ActionListener actionListener;
    private Preferences prefs = Preferences.userRoot();
    private final JSpinner footerSizeInput;
    private final JSpinner logoSizeInput;
    private final JSpinner tablefontSizeInput;
    private final JSpinner dateSizeInput;
    private final JMenu logoSize;
    private final JMenu footerSize;
    private final JMenu dateSize;
    
    private final JMenu tableSize;
    private final JMenu recieptExpandSize;
    private final JSpinner recieptExpandSizeInput;
    
    public OrderFrameMenuBar(ActionListener actionListener){
        this.actionListener = actionListener;
        //======== size ===========
        size = new JMenu("Size");
        
        billSize = new JMenu("Bill size");
        logoSize = new JMenu("Logo size");
        footerSize = new JMenu("Footer size");
        tableSize = new JMenu("Table size");
        dateSize = new JMenu("Date size");
        recieptExpandSize = new JMenu("Reciept Auto Expand Size");
        billSizeInput = new JSpinner(new SpinnerNumberModel(20,2,5000,5));
        tablefontSizeInput = new JSpinner(new SpinnerNumberModel(1,1,20,1));
        logoSizeInput = new JSpinner(new SpinnerNumberModel(1,1,20,1));
        footerSizeInput = new JSpinner(new SpinnerNumberModel(1,1,20,1));
        dateSizeInput = new JSpinner(new SpinnerNumberModel(1,1,20,1));
        recieptExpandSizeInput = new JSpinner(new SpinnerNumberModel(50,50,600,5));
        billSize.add(billSizeInput);
        logoSize.add(logoSizeInput);
        dateSize.add(dateSizeInput);
        footerSize.add(footerSizeInput);
        tableSize.add(tablefontSizeInput);
        recieptExpandSize.add(recieptExpandSizeInput);
        billSizeInput.setValue(prefs.getInt("billSlideSize", 950));
        recieptExpandSizeInput.setValue(prefs.getInt("recieptExpandSize", 50));
        tablefontSizeInput.setValue(prefs.getInt("tableFont", 12));
        dateSizeInput.setValue(prefs.getInt("dateFont", 10));
        footerSizeInput.setValue(prefs.getInt("footerSize", 9));
        logoSizeInput.setValue(prefs.getInt("logoSize", 14));
        
        size.add(billSize);
        size.addSeparator();
        size.add(logoSize);
        size.addSeparator();
        size.add(tableSize);
        size.addSeparator();
        size.add(footerSize);
        size.addSeparator();
        size.add(dateSize);
        size.addSeparator();
        size.add(recieptExpandSize);
        
        //====== colors=======
        colors = new JMenu("Colors");
        
       for(JMenu x : new JMenu[]{size}){
           this.add(x);
       }
       setActionListener();
       setMnomonic();
       
      
    }

    private void setActionListener() {
        OrderForm.OrderFrameActionListener al = (OrderForm.OrderFrameActionListener)actionListener; 
     
        billSizeInput.addChangeListener((e)->{
                 al.changeBillSize((int)billSizeInput.getValue());
        });
        logoSizeInput.addChangeListener((e)->{
                 al.changeLogoSize((int)logoSizeInput.getValue());
        });
        dateSizeInput.addChangeListener((e)->{
                 al.changeDateSize((int)dateSizeInput.getValue());
        });
        tablefontSizeInput.addChangeListener((e)->{
                 al.changeTableSize((int)tablefontSizeInput.getValue());
        });
        footerSizeInput.addChangeListener((e)->{
                 al.changeFooterSize((int)footerSizeInput.getValue());
        });
        recieptExpandSizeInput.addChangeListener((e)->{
                 al.changerecieptExpandSize((int)recieptExpandSizeInput.getValue());
        });

    }

    private void setMnomonic() {
        size.setMnemonic('z');
        billSize.setMnemonic('b');
    }

    
}


package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class VegetableFormToolBar extends JToolBar {

    private  JCheckBox formCheckBox;
    private JCheckBox vegListCheckBox;
    private ButtonGroup buttonGrp;
    private ActionListener showHideListener;

    public VegetableFormToolBar() {
        initialization();
        settingClickListner();
    }
    
    private void initialization(){
        formCheckBox = new JCheckBox("Form");
        vegListCheckBox = new JCheckBox("Vegetable List");
        
        formCheckBox.setSelected(true);
        vegListCheckBox.setSelected(true);
        
        this.addSeparator();
        this.add(formCheckBox);
        this.addSeparator();
        this.add(vegListCheckBox);
    }

    public void setShowHideListener(ActionListener showHide) {
        this.showHideListener = showHide;
    }

    private void settingClickListner() {
        formCheckBox.setActionCommand("form");
        vegListCheckBox.setActionCommand("veglist");
        
        ActionListener al  = new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                showHideListener.actionPerformed(e);
            }
        };
        
        formCheckBox.addActionListener(al);
        vegListCheckBox.addActionListener(al);
    }
    
    
    
    
}

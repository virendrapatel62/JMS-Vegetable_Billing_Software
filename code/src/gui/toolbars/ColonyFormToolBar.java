
package gui.toolbars;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ColonyFormToolBar extends JToolBar {

    private  JCheckBox formCheckBox;
    private JCheckBox colonyCheckBox;
    private JButton refresh;
    private ButtonGroup buttonGrp;
    private ActionListener showHideListener;
    private ActionListener refreshListener;

    public ColonyFormToolBar() {
        initialization();
        settingClickListner();
    }
    
    private void initialization(){
        formCheckBox = new JCheckBox("Form");
        colonyCheckBox = new JCheckBox("Colony List");
        refresh = new JButton("Refresh");
        refresh.setMnemonic('r');
        
        formCheckBox.setSelected(true);
        colonyCheckBox.setSelected(true);
        
        this.addSeparator();
        this.add(formCheckBox);
        this.addSeparator();
        this.add(colonyCheckBox);
        this.addSeparator();
        this.add(refresh);
    }

    public void setRefreshListener(ActionListener refreshListener) {
        this.refreshListener = refreshListener;
        refresh.addActionListener(refreshListener);
    }
    

    public void setShowHideListener(ActionListener showHide) {
        this.showHideListener = showHide;
    }

    private void settingClickListner() {
        formCheckBox.setActionCommand("form");
        colonyCheckBox.setActionCommand("colList");
        
        ActionListener al  = new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                showHideListener.actionPerformed(e);
            }
        };
        
        
        formCheckBox.addActionListener(al);
        colonyCheckBox.addActionListener(al);
    }
    
    
    
    
}

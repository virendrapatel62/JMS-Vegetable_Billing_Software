            

package gui.toolbars;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class UserFormToolBar extends JToolBar {

    private  JCheckBox formCheckBox;
    private JCheckBox userListCheckBox;
    private ButtonGroup buttonGrp;
    private ActionListener showHideListener;

    public UserFormToolBar() {
        initialization();
        settingClickListner();
    }
    
    private void initialization(){
        formCheckBox = new JCheckBox("Form");
        userListCheckBox = new JCheckBox("UserList");
        
        formCheckBox.setSelected(true);
        userListCheckBox.setSelected(true);
        
        this.addSeparator();
        this.add(formCheckBox);
        this.addSeparator();
        this.add(userListCheckBox);
    }

    public void setShowHideListener(ActionListener showHide) {
        this.showHideListener = showHide;
    }

    private void settingClickListner() {
        formCheckBox.setActionCommand("form");
        userListCheckBox.setActionCommand("userlist");
        
        ActionListener al  = new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                showHideListener.actionPerformed(e);
            }
        };
        
        formCheckBox.addActionListener(al);
        userListCheckBox.addActionListener(al);
    }
    
    
    
    
}

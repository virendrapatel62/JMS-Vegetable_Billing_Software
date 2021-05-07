package gui.menubars;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.prefs.Preferences;
import javax.swing.*;

public class CustomerFrameMenuBar extends JMenuBar{

    private JMenu preference;
    private JMenu tools;
    private JMenuItem nor;
    private ActionListener actionListener; ;
    private JMenuItem print;

    public CustomerFrameMenuBar() {
        intialization();
        addActions();
    }

    public void setActionListener(ActionListener actionListener) {
        this.actionListener = actionListener;
    }

    private void intialization() {
        preference = new JMenu("Preferences");
        tools = new JMenu("Tools");
        tools.setMnemonic('t');
        nor = new JMenuItem("Number Of Rows");
        print = new JMenuItem("Print Table");
        nor.setActionCommand("nor");
        print.setActionCommand("print");
        print.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
        
        preference.add(nor);
        tools.add(print);
        
        this.add(preference);
        this.add(tools);
    }

    private void addActions() {
        nor.addActionListener((ActionEvent ev)->{
            String rowString = JOptionPane.showInputDialog(CustomerFrameMenuBar.this,
                    "Enter Number of Rows :", "Rows ", JOptionPane.INFORMATION_MESSAGE);
            if(rowString!=null){
                if(!rowString.isEmpty()){
                    try{
                        int rows = Integer.parseInt(rowString);
                        Preferences.userRoot().put("rowsCount", rows+"");
                    }catch(Exception ex){
                        ex.printStackTrace();
                    }
                }
            }
        });
        print.addActionListener((ActionEvent ev)->{
            actionListener.actionPerformed(null);
        });
    }
    
}

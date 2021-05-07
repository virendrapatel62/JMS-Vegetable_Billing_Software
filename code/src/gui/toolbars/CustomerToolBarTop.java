
package gui.toolbars;

import gui.tablemodels.UserTableModel;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import models.User;

public class CustomerToolBarTop extends JToolBar{

    private JTextField searchBox;
    private ActionListener searchListener;
    private JCheckBox ID;
    private JCheckBox mobil;
    private JCheckBox userName;
    private ButtonGroup buttonGrp;
    JCheckBox[] cb ;
    public CustomerToolBarTop() {
        this.setFloatable(false);
        initialization();
        
        manageLayout();
        settingActionListener();
        
    }

    private void initialization() {
        
        buttonGrp = new ButtonGroup();
        userName = new JCheckBox("Name");
        mobil = new JCheckBox("Mobile");
        userName.setSelected(true);
        ID = new JCheckBox("ID");
        
        cb =new JCheckBox[]{userName , ID , mobil};
        
        for(JCheckBox x: cb){
            buttonGrp.add(x);
        }
        searchBox = new JTextField(50);
        this.setMargin(new Insets(5,20,5,5));
    }

    public void setSearchListener(ActionListener searchListener) {
        this.searchListener = searchListener;
    }

    

    private void manageLayout() {
        
        for(JCheckBox x: cb){
            this.add(x);
        }
        this.add(searchBox);
    }

    private void settingActionListener() {
        searchBox.addKeyListener(new KeyListener(){
            public void keyTyped(KeyEvent e) {
                
            }
            public void keyPressed(KeyEvent e) {
                
            }
            public void keyReleased(KeyEvent e){
                new Thread(()->{
                    int id = 0;
                    if(userName.isSelected())
                        id = 1;
                    else if(ID.isSelected())
                        id = 2;
                    else if(mobil.isSelected())
                        id = 3;
                    synchronized(this){
                        System.out.println(Thread.currentThread());
                        searchListener.actionPerformed(new ActionEvent(e.getSource() , id ,searchBox.getText().trim()));
                    }
                }).start();
            }
        });
        
        ActionListener al = new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                new Thread(()->{
                searchBox.setText("");
                searchListener.actionPerformed(new ActionEvent(e.getSource() , 0 ,searchBox.getText().trim()));
                }).start();
            }
        };
        
        for(JCheckBox x: cb){
            x.addActionListener(al);
        }
    }

    
    
}

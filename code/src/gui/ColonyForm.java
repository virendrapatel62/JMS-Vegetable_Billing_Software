package gui;

import gui.toolbars.ColonyFormToolBar;
import gui.tablemodels.VegetableTableModel;
import controllers.Get;
import controllers.Save;
import controllers.Update;
import gui.tablemodels.ColonyTableModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import models.*;

public class ColonyForm extends JFrame{
    private Get getController = new Get();
    private Save saveController = new Save();
    private Update updateController = new Update();
    private Component parent;
    private JSplitPane splitPane;
    private JPanel formPanel;
    private JTable colTable;
    private GridBagConstraints gc;
    private JButton save;
    private JTextField colony ;
    private JTextField colonyCode ;
    private JLabel col , error , colCode;
    ArrayList<Colony> colonies = Colony.getColonies();
    ColonyTableModel vm = new ColonyTableModel(colonies);
    private JPanel tablePanel;
    private ColonyFormToolBar toolBar;
    
    public ColonyForm(Component parent){
        this.setTitle("Colony");
        this.parent = parent;
        initialize();
        
        setKeyListenerOnTable();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setMinimumSize(new Dimension(400,400));
        setMouseListener();
        this.addWindowListener(new WindowListener(){
            @Override
            public void windowOpened(WindowEvent e) {
            }
            public void windowClosing(WindowEvent e){
                ColonyForm.this.setVisible(false);
            }
            public void windowClosed(WindowEvent e) {
            }
            public void windowIconified(WindowEvent e) {
            }
            public void windowDeiconified(WindowEvent e) {
            }
            public void windowActivated(WindowEvent e) {
            }
            public void windowDeactivated(WindowEvent e) {
            }
        });
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize(); 
        this.setMinimumSize(new Dimension(d.width-300 , d.height-100));
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
    }
    private void initialize(){
        colony  = new JTextField(15);
        colonyCode  = new JTextField(15);
        
        col = new JLabel("Colony : ");
        colCode = new JLabel("Code : ");
        error = new JLabel("");
        save = new JButton("Save");
        save.setBackground(new Color(255, 148, 56));
        save.setForeground(Color.white);
//        /save.setPreferredSize(new Dimension(200,50));
        save.setFont(new Font(Font.SANS_SERIF , Font.PLAIN , 15));
        colTable = new JTable(vm);
        
        
        
        colTable.setRowHeight(25);
        colTable.setFont(new Font(Font.SANS_SERIF , Font.PLAIN , 15));
        JTableHeader header = colTable.getTableHeader();
        setHeaderMouseListener();
        header.setReorderingAllowed(false);
        header.setDefaultRenderer(new DefaultTableCellRenderer(){
            {
                this.setOpaque(true);
                this.setForeground(Color.white);
                //header.setFont(new Font(Font.SERIF , Font.BOLD , 25));
                this.setBackground(new Color(255, 148, 56));
            }
        });
       //header.setBackground(new Color(255, 148, 56));
        formPanel = new JPanel(){
            {
                this.setLayout(new GridBagLayout());
                gc = new GridBagConstraints();
                gc.insets = new Insets(5,5,5,5);
                gc.gridy=0;
                gc.gridx=0; this.add(col , gc);  
                gc.gridx ++; this.add(colony , gc); 
                gc.gridx++; this.add(colCode , gc);  
                gc.gridx ++; this.add(colonyCode , gc); 
                gc.gridx++; this.add(save , gc);
                setActionListener(this);
                setKeyListener(this);
                
                this.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5,5,5,5),
                             BorderFactory.createTitledBorder
                     (BorderFactory.createEtchedBorder(), "Add new Colony")));
            }

            private void setKeyListener(Component parent ) {
                KeyListener kl = new KeyListener(){
                    public void keyTyped(KeyEvent e) {
                    }
                    public void keyPressed(KeyEvent e) {
                    }
                    public void keyReleased(KeyEvent e) {
                        int keyCode = e.getKeyCode();
                        if(keyCode == KeyEvent.VK_ENTER){  
                            if(e.getSource()==colony){
                                colonyCode.requestFocus();
                            
                            }else if(e.getSource()==colonyCode){
                                saveButtonAction(parent);
                            }
                            else if(e.getSource()==save){
                                saveButtonAction(parent);
                            }
                                       
                        }
                   }
                        
                    
                };
                colony.addKeyListener(kl);
                colonyCode.addKeyListener(kl);
                save.addKeyListener(kl);
            }
            
        };
        manageLayout();
        
    }

    private void setActionListener(Component parent) {
        save.addActionListener((ActionEvent ev)->{
            saveButtonAction(parent);
        });
    }
    private void saveButtonAction(Component parent){
        new Thread(()->{    
            String tempcol = colony.getText().trim();
                String tempCode = colonyCode.getText().trim();
                String msg = null;
                if(tempcol.isEmpty()){
                    msg = "Please Enter Colony Name...";
                }else if(tempCode.isEmpty()){
                    msg = "Please Enter Colony Code ...";
                }else if (tempcol.length()<3){
                    msg = "Colony name is too short...";
                }

                if(msg!=null){
                    JOptionPane.showMessageDialog(parent, msg, "Colony ?", JOptionPane.WARNING_MESSAGE);
                }else{
                    Colony cl = new Colony(tempcol , 0 , tempCode); 
                    String er  = saveController.saveColony(cl); 
                    if(er != null){
                        JOptionPane.showMessageDialog(ColonyForm.this, er , "Error", JOptionPane.WARNING_MESSAGE);
                    }
                    colonies.clear();
                    colonies.addAll(getController.getColonies());
                    vm.refresh();
                    colony.requestFocus();
                    colony.setText("");
                    colonyCode.setText("");
                    colTable.scrollRectToVisible(colTable.getCellRect(colTable.getRowCount()-1, 0, true));
                }
            }).start();
        }
        private void setMouseListener(){
            colTable.addMouseListener(new MouseListener(){
                public void mouseClicked(MouseEvent e) {
                    int count  = e.getClickCount();
                    Point point = e.getPoint();
                    int row = colTable.rowAtPoint(point);
                    int id = (Integer)(colTable.getValueAt(row, 1));
                    
                    if(count ==2){
                        System.out.println("col form setMouselistener : " + id);
                        
                        while(true){
                                String ColonyName = JOptionPane.showInputDialog(ColonyForm.this, "Enter New Name:", "Name", JOptionPane.INFORMATION_MESSAGE);
                              if(ColonyName!=null){
                                  if(!ColonyName.isEmpty()){
                                      if(updateController.changeColonyName(new Colony(ColonyName ,id)))
                                      {
                                          colonies.clear();
                                          colonies.addAll(getController.getColonies());
                                          vm.refresh();
                                          break;
                                        
                                      }else{
                                          JOptionPane.showMessageDialog(ColonyForm.this, "Sorry ! can not change at this time","Sorry..", JOptionPane.ERROR_MESSAGE);
                                          break;
                                        }
                                  }else{
                                      break;
                                  }
                              }else{
                                  break;
                              }
                         } 
                    }
                }
                public void mousePressed(MouseEvent e) {
                }
                public void mouseReleased(MouseEvent e) {
                }
                public void mouseEntered(MouseEvent e) {
                }
                public void mouseExited(MouseEvent e) {
                }
            });
            
        }

    private void setHeaderMouseListener() {
        colTable.getTableHeader().addMouseListener(new MouseListener(){
            public void mouseClicked(MouseEvent e) {
                    Point point = e.getPoint();
                    int row = colTable.rowAtPoint(point);
                    int col = colTable.columnAtPoint(point);
                    Comparator<Colony> com = null;
                    switch(col){
                        
                        case 1:
                            //System.out.println(Item.sortByIdOrder);
                            if(Colony.sortByIdOrder)
                                com = new Colony.SortById(false);
                            else
                                com = new Colony.SortById(true);
                        break;
                        case 2:
                            
                            if(Colony.sortByNameOrder)
                                com = new Colony.SortByColony(false);
                            else
                                com = new Colony.SortByColony(true);
                        break;
                        default:
                        break;
                    }
                    if(com!=null)
                        Collections.sort(colonies, com);
                    vm.refresh();
            }
            public void mousePressed(MouseEvent e) {
            }
            public void mouseReleased(MouseEvent e) {
            }
            public void mouseEntered(MouseEvent e) {
            }
            public void mouseExited(MouseEvent e) {
            }
        });
    }

    private void setKeyListenerOnTable() {
        colTable.addKeyListener(new KeyListener(){
            public void keyTyped(KeyEvent e) {
            }
            public void keyPressed(KeyEvent e) {
            }
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_DELETE){
                    int[] row = colTable.getSelectedRows();
                        
                        if(JOptionPane.showConfirmDialog(ColonyForm.this, "Are you sure want to delete  ?")==JOptionPane.OK_OPTION)
                        {
                            for(int i=row.length-1; i>=0 ; i--){
                                int id = (Integer)colTable.getValueAt(row[i], 1);
                                String v  = (String)colTable.getValueAt(row[i], 2);
                                 Colony colony  = new Colony();
                                colony.setId(id);
                                if(colony.deleteColony()){
                                colonies.clear();
                                colonies.addAll(getController.getColonies());
                                vm.refresh();

                                }else{
                                    JOptionPane.showMessageDialog(ColonyForm.this, "\"" +v+ " \" cant be deleted ..","Can't delete", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        }
                    }
                    
                
            }
        });
    }

    private void manageLayout() {
        toolBar = new ColonyFormToolBar();
        tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.add(new JScrollPane(colTable));
        splitPane = new JSplitPane( JSplitPane.VERTICAL_SPLIT, formPanel,tablePanel);

        toolBar.setShowHideListener((ActionEvent ev)->{
            if(ev.getActionCommand().equalsIgnoreCase("form")){
                formPanel.setVisible(((JCheckBox)(ev.getSource())).isSelected());
            }else{
                tablePanel.setVisible(((JCheckBox)(ev.getSource())).isSelected());
            }
            splitPane.setDividerLocation(formPanel.getPreferredSize().height+10);
        });
        toolBar.setRefreshListener((ActionEvent ev)->{
            this.colonies.clear();
            this.colonies.addAll(Colony.getColonies());
            vm.refresh();
            System.out.println("Refreshed");
                    
        });
        
        this.setLayout(new BorderLayout());
        this.add(splitPane);
        this.add(toolBar , BorderLayout.NORTH);
    }
    
}

package gui;

import gui.tablemodels.VegetableTableModel;
import controllers.Get;
import controllers.Save;
import controllers.Update;
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
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
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
import java.util.prefs.Preferences;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import models.*;

public class VegetableForm extends JFrame{
    private Get getController = new Get();
    private Save saveController = new Save();
    private Update updateController = new Update();
    private Component parent;
    private JSplitPane splitPane;
    private JPanel formPanel;
    private JTable vegTable;
    private GridBagConstraints gc;
    private JButton save;
    private Preferences prefs =Preferences.systemRoot().node("VegetableFrameSize");;
    private JTextField vegetable ;
    private JTextField vegetableCode ;
    private JLabel veg , error , vegCode;
    ArrayList<Item> items = Item.getItems();
    VegetableTableModel vm = new VegetableTableModel(items);
    private VegetableFormToolBar toolBar;
    private JPanel vegTablePanel;
    
    public VegetableForm(Component parent){
        
        this.parent = parent;
        //this.setLocationRelativeTo(parent);
        initialize();
        setKeyListenerOnTable();
        this.setMinimumSize(new Dimension(400,400));
        setMouseListener();
        
        this.addWindowListener(new WindowListener(){
            @Override
            public void windowOpened(WindowEvent e) {
            }
            public void windowClosing(WindowEvent e){
                VegetableForm.this.setVisible(false);
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
        
        //Dimension d = Toolkit.getDefaultToolkit().getScreenSize(); 
        //setSize(d.width-200 , d.height-200);
        this.setMinimumSize(new Dimension(600,600));
        //this.setBounds(0,0,prefs.getInt("w", 600), prefs.getInt("h", 600));
        this.setVisible(true);
        this.setTitle("VegeTables");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        addingComponentListener();
        
    }
    private void initialize(){
        vegetable  = new JTextField(15);
        vegetableCode  = new JTextField(15);
        
        veg = new JLabel("Vegetable : ");
        vegCode = new JLabel("Code : ");
        error = new JLabel("");
        save = new JButton("Save");
        save.setBackground(new Color(255, 148, 56));
        save.setForeground(Color.white);
//        /save.setPreferredSize(new Dimension(200,50));
        save.setFont(new Font(Font.SANS_SERIF , Font.PLAIN , 15));
        vegTable = new JTable(vm);
        
        vegTable.setRowHeight(25);
        vegTable.setFont(new Font(Font.SANS_SERIF , Font.PLAIN , 15));
        JTableHeader header = vegTable.getTableHeader();
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
                this.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5,5,5,5),
                             BorderFactory.createTitledBorder
                     (BorderFactory.createEtchedBorder(), "Add New Item")));
                gc = new GridBagConstraints();
                gc.insets = new Insets(5,5,5,5);
                gc.gridy=0;
                gc.gridx=0; this.add(veg , gc);  
                gc.gridx ++; this.add(vegetable , gc); 
                gc.gridx++; this.add(vegCode , gc);  
                gc.gridx ++; this.add(vegetableCode , gc); 
                gc.gridx++; this.add(save , gc);
                setActionListener(this);
                setKeyListener(this);
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
                            if(e.getSource()==vegetable){
                                vegetableCode.requestFocus();
                            
                            }else if(e.getSource()==vegetableCode){
                                saveButtonAction(parent);
                            }
                            else if(e.getSource()==save){
                                saveButtonAction(parent);
                            }
                                       
                        }
                   }
                        
                    
                };
                vegetable.addKeyListener(kl);
                vegetableCode.addKeyListener(kl);
                save.addKeyListener(kl);
            }
            
        };
        
        settingLayout();
        
    }

    private void setActionListener(Component parent) {
        save.addActionListener((ActionEvent ev)->{
            saveButtonAction(parent);
        });
    }
    private void saveButtonAction(Component parent){
        new Thread(()->{    
            String tempveg = vegetable.getText().trim();
                String tempCode = vegetableCode.getText().trim();
                String msg = "";
                if(tempveg.isEmpty()){
                    msg = "Please Enter Vegetable...";
                }else if(tempCode.isEmpty()){
                    msg = "Please Enter Vegetable Code ...";
                }else if (tempveg.length()<3){
                    msg = "Vegetable name is too short...";
                }

                if(!msg.isEmpty()){
                    JOptionPane.showMessageDialog(parent, msg, "Vegetable ?", JOptionPane.WARNING_MESSAGE);
                }else{
                    Item item = new Item(tempveg , 0 , tempCode); 
                    String er = saveController.saveItem(item); 
                    if(er!=null){
                        JOptionPane.showMessageDialog(VegetableForm.this, er, "Error", JOptionPane.WARNING_MESSAGE);
                    }
                    items.clear();
                    items.addAll(getController.getItems());
                    vm.refresh();
                    vegetable.requestFocus();
                    vegetable.setText("");
                    vegetableCode.setText("");
                    vegTable.scrollRectToVisible(vegTable.getCellRect(vegTable.getRowCount()-1, 0, true));
                }
            }).start();
        }
        private void setMouseListener(){
            vegTable.addMouseListener(new MouseListener(){
                public void mouseClicked(MouseEvent e) {
                    int count  = e.getClickCount();
                    Point point = e.getPoint();
                    int row = vegTable.rowAtPoint(point);
                    int id = (Integer)(vegTable.getValueAt(row, 1));
                    String colName = vegTable.getColumnName(vegTable.columnAtPoint(point));
                    
                    if(count==2){
                        if(colName.equalsIgnoreCase("Vegetable")){
                            System.out.println("veg form setMouselistener : " + id);

                            while(true){
                                    String itemName = JOptionPane.showInputDialog(VegetableForm.this, "Enter New Name:", "Name", JOptionPane.INFORMATION_MESSAGE);
                                  if(itemName!=null){
                                      if(!itemName.isEmpty()){
                                          if(updateController.changeItemName(new Item(itemName ,id)))
                                          {
                                              items.clear();
                                              items.addAll(getController.getItems());
                                              vm.refresh();
                                              break;

                                          }else{
                                              JOptionPane.showMessageDialog(VegetableForm.this, "Sorry ! can not change at this time","Sorry..", JOptionPane.ERROR_MESSAGE);
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
                        if(colName.equalsIgnoreCase("unit prize")){
                            while(true){
                                    int prize = 0;
                                    String p = JOptionPane.showInputDialog(VegetableForm.this, "Enter Unit Prize:", "Change Unit Prize", JOptionPane.INFORMATION_MESSAGE);
                                  if(p!=null){
                                      if(!p.isEmpty()){
                                          try{
                                              prize = Integer.parseInt(p);
                                              if(updateController.changeUnitPrize(new Item(null , id , prize)))
                                              {
                                                  items.clear();
                                                  items.addAll(getController.getItems());
                                                  vm.refresh();
                                                  break;

                                              }else{
                                                  JOptionPane.showMessageDialog(VegetableForm.this, "Sorry ! can not change at this time","Sorry..", JOptionPane.ERROR_MESSAGE);
                                                  break;
                                                }
                                          }catch(NumberFormatException ex){
                                             JOptionPane.showMessageDialog(VegetableForm.this, "prize must contains only numbers ...","Wrong format", JOptionPane.ERROR_MESSAGE); 
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
        vegTable.getTableHeader().addMouseListener(new MouseListener(){
            public void mouseClicked(MouseEvent e) {
                    Point point = e.getPoint();
                    int row = vegTable.rowAtPoint(point);
                    int col = vegTable.columnAtPoint(point);
                    Comparator<Item> com = null;
                    switch(col){
                        
                        case 1:
                            //System.out.println(Item.sortByIdOrder);
                            if(Item.sortByIdOrder)
                                com = new Item.SortById(false);
                            else
                                com = new Item.SortById(true);
                        break;
                        case 2:
                            
                            if(Item.sortByNameOrder)
                                com = new Item.SortByItem(false);
                            else
                                com = new Item.SortByItem(true);
                        break;
                        default:
                        break;
                    }
                    if(com!=null)
                        Collections.sort(items, com);
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
        vegTable.addKeyListener(new KeyListener(){
            public void keyTyped(KeyEvent e) {
            }
            public void keyPressed(KeyEvent e) {
            }
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_DELETE){
                    int[] row = vegTable.getSelectedRows();
                        
                        if(JOptionPane.showConfirmDialog(VegetableForm.this, "Are you sure want to delete  ?")==JOptionPane.OK_OPTION)
                        {
                            for(int i=row.length-1; i>=0 ; i--){
                                int id = (Integer)vegTable.getValueAt(row[i], 1);
                                String v  = (String)vegTable.getValueAt(row[i], 2);
                                 Item item  = new Item();
                                item.setId(id);
                                if(item.deleteItem()){
                                items.clear();
                                items.addAll(getController.getItems());
                                vm.refresh();

                                }else{
                                    JOptionPane.showMessageDialog(VegetableForm.this, "\"" +v+ " \" cant be deleted ..","Can't delete", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        }
                    }
                    
                
            }
        });
    }

    private void settingLayout() {
        this.setLayout(new BorderLayout());
        vegTablePanel  = new JPanel();
        vegTablePanel.setLayout(new BorderLayout());
        vegTablePanel.add(new JScrollPane(vegTable));
        splitPane = new JSplitPane( JSplitPane.VERTICAL_SPLIT, formPanel, vegTablePanel );
        toolBar = new VegetableFormToolBar();
        this.add(splitPane , BorderLayout.CENTER);
        this.add(toolBar , BorderLayout.NORTH);
        
        toolBar.setShowHideListener((ActionEvent ev)->{
            if(ev.getActionCommand().equalsIgnoreCase("form")){
                formPanel.setVisible(((JCheckBox)(ev.getSource())).isSelected());
            }else{
                vegTablePanel.setVisible(((JCheckBox)(ev.getSource())).isSelected());
            }
            splitPane.setDividerLocation(formPanel.getPreferredSize().height+30);
        });
        
    }
    
    private void addingComponentListener(){
        this.addComponentListener(new ComponentListener(){
            
            @Override
            public void componentResized(ComponentEvent e) {
                JFrame f  = ((JFrame)(e.getSource()));
                Dimension d = f.getSize();
               
                
                
                System.out.println(prefs.getInt("w", 600));
                System.out.println(prefs.getInt("h", 600));
                prefs.putInt("h", d.height);
                prefs.putInt("w", d.width);
                
            }
            public void componentMoved(ComponentEvent e) {
            }
            public void componentShown(ComponentEvent e) {
                JFrame f  = ((JFrame)(e.getSource()));
                
                
            }
            public void componentHidden(ComponentEvent e) {
            }
        });
        
    }
    
}

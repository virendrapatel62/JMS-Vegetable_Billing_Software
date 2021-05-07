
package gui;

import gui.menubars.CustomerFrameMenuBar;
import gui.tablemodels.UserTableModel;
import gui.toolbars.CustomerToolBarBottom;
import gui.toolbars.CustomerToolBarTop;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.print.PrinterException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.prefs.Preferences;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import models.User;

public class CustomerFrame extends JFrame {

    private ArrayList<User> users;
    private UserTableModel utm;
    private JTable table;
    private JPanel tablePanel;
    private CustomerToolBarTop toolBarTop;
    private CustomerToolBarBottom toolBarBottom;
    private JPanel pagingPanel;
    private CustomerFrameMenuBar menuBar;
    private  int NUMBER_OF_ROWS =50;

    public CustomerFrame(){
        this.setTitle("All Customers");
        intialization();
        System.out.println("initialization compleate");
        layoutSetting();
        
        System.out.println("layout compleate");
        loadData();
        System.out.println("data loading compleate");
        addingActionListenerOnTableHeader();
       this.showFrame();
       settingListeners();
       settingKeyListener();
       settingActionListenerOnMenuBar();
       addMouseListenerOnTable();
       this.addWindowListener(new WindowListener(){
            public void windowOpened(WindowEvent e) {
            }
            public void windowClosing(WindowEvent e) {
                CustomerFrame.this.setVisible(false);
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
       
       
      
    }

    private void showFrame() {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize(); 
       //setBounds(10,10,d.width-300 , d.height-300);
       this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setMinimumSize(new Dimension(d.width-300 , d.height-100));
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setPagingButtons(User.getUserCount() , null);
        
    }
    
    private void intialization() {
        users = new ArrayList<User>();
        utm = new UserTableModel(users);
        table = new JTable(utm);
        tablePanel = new JPanel(new BorderLayout());
        
        table.setRowHeight(25);
        table.getTableHeader().setReorderingAllowed(false);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setFont(new Font(Font.SANS_SERIF , Font.PLAIN , 15));
        table.getTableHeader().setBackground(new Color(255, 148, 56));
        tablePanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5,5,5,5),
                             BorderFactory.createTitledBorder
                     (BorderFactory.createEtchedBorder(), "All Customers")));
        table.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer(){
            {
                this.setOpaque(true);
                this.setForeground(Color.white);
                this.setFont(new Font(Font.SERIF , Font.BOLD, 15));
                this.setBackground(new Color(255, 148, 56));
            }
        });
        pagingPanel = new JPanel(new FlowLayout( FlowLayout.LEFT) );
        
        
        menuBar = new CustomerFrameMenuBar();
        this.setJMenuBar(menuBar);
        try{
            NUMBER_OF_ROWS= Integer.parseInt(Preferences.userRoot().get("rowsCount" , "50"));
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
    }
    private void setPagingButtons(int count , String str){
        pagingPanel.removeAll();
        pagingPanel.revalidate();
        pagingPanel.repaint();
        for(int i = 0; i <= count/ NUMBER_OF_ROWS; i++){
           JButton b = new JButton((i+1)+"");
           b.addActionListener(new PaggingListener(str));
           b.setActionCommand(String.valueOf(i+1));
           pagingPanel.add(b);
        }
        pagingPanel.revalidate();
    }
    private void layoutSetting() {
        tablePanel.add(new JSplitPane(JSplitPane.VERTICAL_SPLIT ,new JScrollPane(table)
        ,new JScrollPane(pagingPanel , JScrollPane.VERTICAL_SCROLLBAR_NEVER
                ,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED )));
//tablePanel.add(new JScrollPane(table), BorderLayout.CENTER);
        //tablePanel.add(new JScrollPane(pagingPanel , JScrollPane.VERTICAL_SCROLLBAR_NEVER
               // ,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED ), BorderLayout.SOUTH);
        

        toolBarTop = new CustomerToolBarTop();
        toolBarBottom = new CustomerToolBarBottom();
        this.setLayout(new BorderLayout());
        this.add(tablePanel);
        this.add(toolBarTop , BorderLayout.NORTH);
        this.add(new JScrollPane(toolBarBottom) , BorderLayout.SOUTH);
        
        
    }

    private void loadData() {
        users.clear();
        users.addAll(User.getUsers(0,NUMBER_OF_ROWS));
        utm.refresh();
    }

    private void settingListeners() {
        toolBarTop.setSearchListener((ActionEvent ev)->{
            
            String searchText = ev.getActionCommand();
            users.clear();
            System.out.println(ev.getID());
            
                switch(ev.getID()){
                    case 0:
                        users.addAll(User.getUsers(0, NUMBER_OF_ROWS));
                    break;
                    case 1:
                        users.addAll(User.searchUsersByName(searchText, NUMBER_OF_ROWS));
                    break;
                    case 2:    
                        users.addAll(User.searchUsersById(searchText, NUMBER_OF_ROWS));
                    break;
                    case 3:    
                        users.addAll(User.searchUsersByMobile(searchText, NUMBER_OF_ROWS));
                    break; 
                }
            
            utm.refresh();
            System.out.println(users.size());
        });
        toolBarBottom.setFilterListener((ActionEvent ev)->{
            String searchText = ev.getActionCommand();
            if(searchText.equalsIgnoreCase("all")){
                searchText = null;
            }
            setPagingButtons(User.getUserCountByNameStartsWith(searchText) ,searchText );
            users.clear();
            users.addAll(User.searchUsersByName(searchText,NUMBER_OF_ROWS));
            utm.refresh();
            
        });
        
        
    }

    private void settingKeyListener() { 
        table.addKeyListener(new KeyListener(){ 
            public void keyTyped(KeyEvent e) {
            } 
            public void keyPressed(KeyEvent e) {
                if(e.isControlDown() && e.getKeyCode() == (int)'O' || e.getKeyCode() == (int)'o'){
                    int row = table.getSelectedRow();
                    User user = (User)table.getValueAt(row,1);
                    new OrderForm(user);
//JOptionPane.showMessageDialog(CustomerFrame.this, user.getAddress().getColony().getColonyCode());
                }
            }
            public void keyReleased(KeyEvent e) {
            }
        });
    }

    private void addingActionListenerOnTableHeader() {
        table.getTableHeader().addMouseListener(new MouseListener(){
            public void mouseClicked(MouseEvent e) {
                int col = table.columnAtPoint(e.getPoint());
                Comparator com = null;
                switch(col){
                    case 0:
                        com = new User.SortBySerialNumber(!User.SortBySerialNumber.order);
                    break;
                    case 1:
                        com = new User.SortById(!User.SortById.order);
                    break;
                    case 2:
                        com = new User.SortByName(!User.SortByName.order);
                    break;
                    case 3:
                        com = new User.SortByWhatsApp(!User.SortByWhatsApp.order);
                    break;
                    case 4:
                        com = new User.SortByMobile(!User.SortByMobile.order);
                    break;
                    case 5:
                        com = new User.SortByArea(!User.SortByArea.order);
                    break;
                    case 6:
                        com = new User.SortByColony(!User.SortByColony.order);
                    break;
                    case 7:
                        com = new User.SortByPhase(!User.SortByPhase.order);
                    break;
                    case 8:
                        com = new User.SortByFloor(!User.SortByFloor.order);
                    break;
                }
                
                if(com!=null){
                    Collections.sort(users, com);
                    System.out.println(com);
                    utm.refresh();
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

    
    class PaggingListener implements ActionListener{
        private String str = null;

        public PaggingListener(String str ) {
            this.str = str;
            
        }
        
        
        public void actionPerformed(ActionEvent e) {
            new Thread(()->{
            JButton b = (JButton)e.getSource();
            int cammand  = Integer.parseInt(b.getActionCommand());
            users.clear();
            if(str!=null)
                users.addAll(User.searchUsersByName(str.trim(), (cammand-1)*NUMBER_OF_ROWS , NUMBER_OF_ROWS ));
            else
               users.addAll(User.getUsers((cammand-1)*NUMBER_OF_ROWS , NUMBER_OF_ROWS ));
            utm.refresh();
            
                System.out.println(users);
            }).start();
        }
        
    }
    
    private void settingActionListenerOnMenuBar() {
        menuBar.setActionListener((avt)->{
            printTable();
        });
        
    }
    
    private void printTable(){
        try {
            /* print the table */
            MessageFormat footer = new MessageFormat("Welome to JMS , We Look Forword To Working With You");
            MessageFormat header = new MessageFormat("Customer List");
            boolean complete = table.print(JTable.PrintMode.FIT_WIDTH, header, footer,
                                                 true, null,
                                                 true, null);
 
            /* if printing completes */
            if (complete) {
                /* show a success message */
                JOptionPane.showMessageDialog(this,
                                              "Printing Complete",
                                              "Printing Result",
                                              JOptionPane.INFORMATION_MESSAGE);
            } else {
                /* show a message indicating that printing was cancelled */
                JOptionPane.showMessageDialog(this,
                                              "Printing Cancelled",
                                              "Printing Result",
                                              JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (PrinterException pe) {
            /* Printing failed, report to the user */
            JOptionPane.showMessageDialog(this,
                                          "Printing Failed: " + pe.getMessage(),
                                          "Printing Result",
                                          JOptionPane.ERROR_MESSAGE);
        }
    }
    private void addMouseListenerOnTable(){
        table.addMouseListener(new MouseListener(){
            public void mouseClicked(MouseEvent e) {
                if(e.getButton()==1 && e.getClickCount()==2){
                    Point p = e.getPoint();
                    int row = table.rowAtPoint(p);
                    int coulmn = table.columnAtPoint(p);
                    User user  = (User)table.getValueAt(row, 1);
                    if(coulmn==0){
                        String n = JOptionPane.showInputDialog(CustomerFrame.this, "Enter New Serial Number");
                        if(n!=null){
                            if(!n.isEmpty()){
                                try{
                                    int number = Integer.parseInt(n);
                                    if(User.usersExists(number)){
                                        User.incrementUserSerialNumber(user.getAddress().getColony(), number);
                                    }
                                    user.setSerialNumber(number);
                                    user.changeSerialNumber();
                                    
                                    loadData();
                                    utm.refresh();
                                }catch(Exception ex){
                                    ex.printStackTrace();
                                 }
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
        
        
        table.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
            }
            public void keyPressed(KeyEvent e) {
                if(e.getKeyChar() == KeyEvent.VK_DELETE){
                    int row = table.getSelectedRow();
                    User user = (User)table.getValueAt(row,1);
                    
                    int ans = JOptionPane.showConfirmDialog(CustomerFrame.this, "if you delete user , all Bills of this USer will be deleted , \n"
                            + "are you sure , want to delete ??","Delete User", JOptionPane.OK_CANCEL_OPTION);
                     if (ans==JOptionPane.OK_OPTION){
                         if(user.delete()){
                             JOptionPane.showMessageDialog(CustomerFrame.this, 
                                     "Customer Deleted ....", "Success..", JOptionPane.INFORMATION_MESSAGE);
                         }
                         loadData();
                         utm.refresh();
                    }
                }   
                
                
            }
            public void keyReleased(KeyEvent e) {
            }
        });
        
    }
    
    
}

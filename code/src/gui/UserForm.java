package gui;

import gui.toolbars.UserFormToolBar;
import gui.tablemodels.VegetableTableModel;
import controllers.Get;
import controllers.Save;
import controllers.Update;
import gui.tablemodels.UserTableModel;
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
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import models.*;

public class UserForm extends JFrame{
    private Get getController = new Get();
    private Save saveController = new Save();
    private Update updateController = new Update();
    private Component parent;
    private JSplitPane splitPane;
    private JPanel formPanel;
    private JPanel tablePanel;
    private GridBagConstraints gc;
    private JButton save;
    private JTextField name;
    ArrayList<User> users = new ArrayList();
    UserTableModel um = new UserTableModel(users);
    private JTable userTable;
    private JTextField mobile;
    private JTextField whatsApp;
    private JTextField area;
    private JTextField phase;
    private JSpinner floor;
    private SpinnerNumberModel spinnerModel;
    private JLabel nameLabel;
    private JLabel areaLabel;
    private JLabel mobileLabel;
    private JLabel whatsAppLabel;
    private JLabel phaseLabel;
    private JLabel floorLabel;
    private JLabel addressLabel;
    private JLabel colonyLabel;
    private JComboBox colony;
    private JTextField houseNo;
    private JLabel houseLabel;
    private JLabel errorLabel;
    private UserFormToolBar toolBar;
    private JSpinner selectSerialNumber;
    private SpinnerNumberModel serialNumberModel;
    public UserForm(Component parent){
        
        this.parent = parent;
        //this.setLocationRelativeTo(parent);
        
        initialize();
        this.setTitle("New User");
        setKeyListenerOnTable();
        addingActionListenerOnTableHeader();
        this.setMinimumSize(new Dimension(600,600));
        setMouseListener();
        
        
        System.out.println("yes");
        this.addWindowListener(new WindowListener(){
            @Override
            public void windowOpened(WindowEvent e) {
                //parent.setEnabled(false);
            }
            public void windowClosing(WindowEvent e){
                UserForm.this.setVisible(false);
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
        
       manageLayout();
       
      
       
       Dimension d = Toolkit.getDefaultToolkit().getScreenSize(); 
       //setBounds(50,50,d.width-300 , d.height-300);
       this.setMinimumSize(new Dimension(300 , 500));
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        
         new Thread(()->{
           users.addAll(User.getUsers(0,50));
           um.refresh();
       });
       
        name.requestFocus();
    }
    private void initialize(){
        name = new JTextField(10);
        
        serialNumberModel = new SpinnerNumberModel(1 , 1 , 9000000  , 1 );
        selectSerialNumber  = new JSpinner(serialNumberModel);
        tablePanel = new JPanel();
        nameLabel = new JLabel("Name : ");
        whatsApp = new JTextField(10);
        whatsAppLabel = new JLabel("WhatsApp : ");
        mobile = new JTextField(10);
        mobileLabel = new JLabel("Mobile : ");
        errorLabel = new JLabel("");
        colonyLabel = new JLabel("Colony : ");
        
        
        area = new JTextField(10);
        addressLabel = new JLabel("Address: ");
        areaLabel = new JLabel("Area: ");
        phase = new JTextField(10);
        houseNo = new JTextField(10);
        houseLabel = new JLabel("House NO : ");
        phaseLabel = new JLabel("Phase/block/gali : ");
        spinnerModel = new SpinnerNumberModel(0 , 0 , 50 , 1 );
        floorLabel = new JLabel("Floor: ");
        floor = new JSpinner(spinnerModel);
        
        addressLabel.setFont(new Font(Font.SANS_SERIF , Font.BOLD , 15));
        
        save = new JButton("Save");
        save.setBackground(new Color(255, 148, 56));
        save.setForeground(Color.white);

        save.setFont(new Font(Font.SANS_SERIF , Font.PLAIN , 15));
        
        userTable = new JTable(um);
        tablePanel.setLayout(new BorderLayout());
        tablePanel.add(new JScrollPane(userTable));
        tablePanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5,5,5,5),
                             BorderFactory.createTitledBorder
                     (BorderFactory.createEtchedBorder(), "All Customers")));
        userTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        userTable.setRowHeight(25);
        userTable.setFont(new Font(Font.SANS_SERIF , Font.PLAIN , 15));
        JTableHeader header = userTable.getTableHeader();
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
       colony = new JComboBox(new Vector(Colony.getColonies()));
        formPanel = new JPanel(){
            {
                this.setLayout(new GridBagLayout());
                this.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5,5,5,5),
                             BorderFactory.createTitledBorder
                     (BorderFactory.createEtchedBorder(), "Add New Customer")));
                gc = new GridBagConstraints();
                gc.insets = new Insets(5,5,5,5);
                
                gc.gridy=0;
                gc.gridx = 0; this.add(nameLabel , gc);
                gc.gridx ++; this.add(name ,gc );
                gc.gridx++; this.add(whatsAppLabel,gc);
                gc.gridx++; this.add(whatsApp,gc);
                gc.gridx++; this.add(mobileLabel,gc);
                gc.gridx++; this.add(mobile,gc);
                
                gc.gridy++;
                
                gc.gridx = 0; this.add(new JLabel("Serial Number:") , gc);
                gc.gridx ++; this.add(selectSerialNumber , gc);
             
                
                gc.gridy++;
                gc.gridx=0; this.add(addressLabel , gc);
                
                gc.gridy++;
                gc.gridx = 0; this.add(areaLabel , gc);
                gc.gridx++; this.add(area , gc);
                gc.gridx++; this.add(colonyLabel , gc);
                gc.gridx++; this.add(colony , gc);
                gc.gridx ++; this.add(phaseLabel , gc);
                gc.gridx++; this.add(phase , gc);
                
                gc.gridy++;
                gc.gridx=0; this.add(floorLabel , gc);
                gc.gridx++; this.add(floor , gc);
                gc.gridx++; this.add(houseLabel , gc);
                gc.gridx++; this.add(houseNo , gc);
                
                gc.gridy++;
                gc.gridwidth=1;
                save.setText("  Save  ");
                gc.gridx=2; this.add(save , gc);
                gc.gridwidth=3;
                gc.gridx=3; this.add(errorLabel , gc);
                
                save.addActionListener((ActionEvent ev)->{
                    saveButtonAction();
                });
                
            }

            private void setKeyListener(Component parent ) {
                KeyListener kl = new KeyListener(){
                    public void keyTyped(KeyEvent e) {
                    }
                    public void keyPressed(KeyEvent e) {
                    }
                    public void keyReleased(KeyEvent e) {
                       
                    }
                    
                };
            }
            
        };
        toolBar = new UserFormToolBar();
        splitPane = new JSplitPane( JSplitPane.VERTICAL_SPLIT, formPanel, tablePanel );
        setKeyListenerOnFields();
        
        toolBar.setShowHideListener((ActionEvent ev)->{
            if(ev.getActionCommand().equalsIgnoreCase("form")){
                formPanel.setVisible(((JCheckBox)(ev.getSource())).isSelected());
            }else{
                tablePanel.setVisible(((JCheckBox)(ev.getSource())).isSelected());
            }
            splitPane.setDividerLocation(formPanel.getPreferredSize().height+10);
        });
    }

    
    private void saveButtonAction(){
        new Thread(()->{
            boolean flag = false;
            String err = null;

            String nm = name.getText().trim();
            String mob = mobile.getText().trim();
            String what = whatsApp.getText().trim();
            String tempArea = area.getText().trim();
            String tempPhase = phase.getText().trim();
            String temphouse = houseNo.getText().trim();
            int tempFloor = (Integer)floor.getValue();
            int serialNumber = (Integer)selectSerialNumber.getValue();
            Colony col = (Colony)colony.getSelectedItem();
            if(nm.isEmpty())
                err="Please Enter Name";
            else if(mob.isEmpty() && what.isEmpty()){
                err = "Enter Mobile number or WhatsApp number";
            }else if(tempArea.isEmpty()){
                err ="Area is required";
            }else if(tempPhase.isEmpty()){
                err = "phase/block.gali is Required";
            }else if(col==null){
                err = "Coluny is required ,\n if list is empty please add first Colonies";
            }else if(!mob.isEmpty()){
                Pattern p = Pattern.compile("[^0-9]");
                Matcher m = p.matcher(mob);
                if(mob.length()<10)
                    err = "mobile number must be in 10 characters";
                else if(m.find()){
                    err = "alphabate not allowed in mobile number";
                }else{
                    flag = true;
                }

            }else if(!what.isEmpty()){
                Pattern p = Pattern.compile("[^0-9]");
                Matcher m = p.matcher(what);
                if(what.length()<10)
                    err = "WhatsApp number must be in 10 characters";
                else if(m.find()){
                    err = "alphabate not allowed in WhatsApp number";
                }else{
                    flag = true;
                }

            }else{
                flag = true;
            }

            if(flag){
                if(mob.isEmpty()&&!what.isEmpty()){
                    mob = what;
                    mobile.setText(what);
                    errorLabel.setText("");
                }

                    Address taddr = new Address();
                    taddr.setArea(tempArea);
                    taddr.setColony(col);
                    taddr.setFloor(tempFloor);
                    taddr.setHouseNumber(temphouse.toUpperCase());
                    taddr.setPhaseBlockGali(tempPhase.toUpperCase());
                    System.out.println(taddr +"inside save button");
                    int addressId  = taddr.saveAddress();
                    System.out.println(addressId +"inside save button");
                    taddr.setAddressId(addressId);
                    if(addressId>-1){
                        
                        User user = new User();
                        user.setAddress(taddr);
                        user.setName(nm);
                        user.setSerialNumber(0);
                        user.setMobile(mob);
                        user.setWhatsApp(what);
                            String usercode = "";
                            String a = col.getColonyCode();
                            String b = new Character(tempArea.charAt(0)).toString();

                            String c = tempPhase;
                            String d = tempFloor+"";
                            String e = temphouse;
                            usercode = a+"/"+b+"/"+c+"/"+d+"/"+e;
                        user.setUserId(usercode.toUpperCase());

                        String s = user.saveUser();
                        users.add(user);
                        um.refresh();
                        if(s!=null){
                            JOptionPane.showMessageDialog(UserForm.this, s, "User Not Added", JOptionPane.ERROR_MESSAGE);
                        }else{
                            Update.incrementUserSerialNumber(col, serialNumber);
                            user.setSerialNumber(serialNumber);
                            user.changeSerialNumber();
                            name.setText("");
                            area.setText("");
                            houseNo.setText("");
                            phase.setText("");
                            mobile.setText("");
                            whatsApp.setText("");

                            um.refresh();
                            errorLabel.setText("");
                            name.requestFocus();
                        }
                        //user.setUserId(ERROR);
                    }
            }else{
                errorLabel.setForeground(Color.red);
                errorLabel.setText(err);
            }
        }).start();
            
        
    }


    private void setMouseListener(){
            userTable.addMouseListener(new MouseListener(){
            public void mouseClicked(MouseEvent e) {
                    
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
    }

    private void setHeaderMouseListener() {
    }

    private void setKeyListenerOnFields() {
        KeyListener kl = new KeyListener(){
            public void keyTyped(KeyEvent e) {
            }
            public void keyPressed(KeyEvent e) {
            }
            public void keyReleased(KeyEvent e) {
                int key = e.getKeyCode();
                Object source = e.getSource();
                if(key == KeyEvent.VK_ENTER){
                    if(source==name)
                        whatsApp.requestFocus();
                    else if(source==whatsApp)
                        mobile.requestFocus();
                    else if(source==mobile)
                        selectSerialNumber.requestFocus();
                    else if(source==selectSerialNumber)
                        area.requestFocus();
                    else if(source==area)
                        colony.requestFocus();
                    else if(source==colony)
                        phase.requestFocus();
                    else if(source==phase)
                        floor.requestFocus();
                    else if(source==floor)
                        houseNo.requestFocus();
                    else if(source==houseNo)
                        saveButtonAction();
                    else if(source == save)
                        saveButtonAction();
                }
            }
        };
        
        name.addKeyListener(kl);
        whatsApp.addKeyListener(kl);
        mobile.addKeyListener(kl);
        area.addKeyListener(kl);
        colony.addKeyListener(kl);
        phase.addKeyListener(kl);
        floor.addKeyListener(kl);
        houseNo.addKeyListener(kl);
        save.addKeyListener(kl);
        selectSerialNumber.addKeyListener(kl);
         
    }

    private void manageLayout() {
        this.setLayout(new BorderLayout());
        this.add(splitPane, BorderLayout.CENTER);
        this.add(toolBar, BorderLayout.NORTH);
    }
    private void addingActionListenerOnTableHeader() {
        userTable.getTableHeader().addMouseListener(new MouseListener(){
            public void mouseClicked(MouseEvent e) {
                int col = userTable.columnAtPoint(e.getPoint());
                Comparator com = null;
                switch(col){
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
                    um.refresh();
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
    
}

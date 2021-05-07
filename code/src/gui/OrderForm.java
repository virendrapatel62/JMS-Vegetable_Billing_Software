package gui;
import controllers.Get;
import controllers.Save;
import controllers.Update;
import gui.menubars.OrderFrameMenuBar;
import gui.tablemodels.BillingTableModel;
import gui.tablemodels.OrderTableModel;
import gui.toolbars.OrderFormToolBar;
import java.awt.AWTEventMulticaster;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.PrintJob;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.print.PrinterJob;
import java.sql.Date;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import models.Address;
import models.Bill;
import models.BillDescription;
import models.BillingItem;
import models.Colony;
import models.Item;
import models.OrderStatus;
import models.User;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.jdesktop.swingx.autocomplete.ObjectToStringConverter;
public class OrderForm extends JFrame {

    private  JPanel formPanel;
    private JSplitPane splitPane;
    private JSplitPane upperSplitPane;
    private JTable table;
    private JPanel tablePanel;
    private JPanel billingPanel;
    private Preferences prefs = Preferences.userRoot().node("OrderForm");
    private JTable billingTable;
    private JTextArea total = new JTextArea(); 
    private JTextField itemText , quantity , userName , colonyCode;
    private JComboBox selectItem , selectColony ;
    private JList userList , itemCodeList ;
    private JComboBox selectUser;
    private JButton save , print , add;
    private Vector<Item>  items =  new Vector();
    private Vector<Colony>  colonies =  new Vector();
    private ArrayList<User>  users =  new ArrayList();// User.getUsers();
    private ArrayList<BillingItem> billingItems = new ArrayList();
    private ArrayList<BillingItem> newBillingItems = new ArrayList();
    private ArrayList<Bill> orderList = new ArrayList();
    private BillingTableModel btm = new BillingTableModel(billingItems);
    private OrderTableModel otm = new OrderTableModel(orderList);
    private ArrayList<String>  userNames;
    private Comparator<Item> itemCom = new Comparator<Item>() {
                    @Override
                    public int compare(Item o1, Item o2) {
                        return  o2.getItemCode().toUpperCase().compareTo(o1.getItemCode().toUpperCase());
                    }
                };
    private Comparator<Colony> colCom = new Comparator<Colony>() {
                    @Override
                    public int compare(Colony o1, Colony o2) {
                        return  o2.getColonyCode().toUpperCase().compareTo(o1.getColonyCode().toUpperCase());
                    }
                };
    private JList userNameList;
    private JToolBar billingFooter;
    private JButton reset;
    private JList colonyCodeList;
    Vector<User> userListVector = new Vector();
     Vector<String> userNamesVector = new Vector();
     int currentShowingBillNo;
     Date currentShowingBillingDate;
    private OrderFormToolBar toolBar;
    
    
    private User user ;
    private JPanel detailPanel;
    private JLabel companyName;
    private JTextArea date;
    private JTextArea companyNameJMS;
    private JTextArea companyNameVG;
    private JPanel downPanel;
    private JTextArea thanks;
    private JTextArea info;
    private OrderFrameMenuBar menuBar;
    private JScrollPane billingTableScroll;
    public OrderForm(){
        this.setTitle("New Order");
        initialization();
        setShortcuts();
        getOrderList();
         setFontSize();
        
         settingPropertiesOnBillingPanelComponents();
         
         
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize(); 
        //setSize(d.width , d.height-50);
        
        this.setVisible(true);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setMinimumSize(new Dimension(400 ,600));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
            this.validate();
        setUpperSliderSize();
        //panelsizeListener();
        this.addWindowListener(new WindowListener(){
            public void windowOpened(WindowEvent e) {
            }
            public void windowClosing(WindowEvent e) {
                OrderForm.this.setVisible(false);
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
        setKeyListenerOnBillingPanel();
       
    }
    public OrderForm(User user ){
        this();
        this.user = user;
        if(user!=null){
            try{
                colonyCode.setText(user.getAddress().getColony().getColonyCode());
                selectColony.setSelectedItem(user.getAddress().getColony());
                Colony c = (Colony)selectColony.getSelectedItem();
                ArrayList<Address> a = Address.getAddressesByColony(c.getId());
                userListVector.clear();
                Iterator<Address> it = a.iterator();
                ArrayList<User> uss = new ArrayList();
                while(it.hasNext())
                      uss.addAll(User.getUsersByAddress(it.next())) ;
                userListVector.addAll(uss);
                Iterator<User> itruss = uss.iterator();
                userNamesVector.clear();
                while(itruss.hasNext()){
                    userNamesVector.add(itruss.next().getName());
                }
                selectUser.requestFocus();
                selectUser.setSelectedItem(user);
                userName.setText(user.getName());
            }catch(Exception ex){
                ex.printStackTrace();
            }
            splitPane.setOneTouchExpandable(true);
            upperSplitPane.setOneTouchExpandable(true);
            setUpperSliderSize();
        }
    }
    
    private void loadData(){
        new Thread(() -> {
            orderList.clear();
            orderList.addAll(Bill.getBillsOfDate(new Date(new java.util.Date().getTime())));
        }).start();
    }
    private void initialization(){
        
        setMenuBar();
        billingFooter = new JToolBar();
        itemText = new JTextField(8);
        colonyCode = new JTextField(8);
        userName = new JTextField(15);
        Collections.sort(items, itemCom);
        selectItem = new JComboBox(items);
        selectColony = new JComboBox(colonies);
        
        Vector itemCodeVector = new Vector();
        Vector colonyCodeVector = new Vector();
        itemCodeList = new JList(itemCodeVector);
        colonyCodeList = new JList(colonyCodeVector);


        
        userList = new JList(userListVector);
        userNameList = new JList(userNamesVector);
        
        selectUser = new JComboBox(userListVector);
        
        quantity = new JTextField(8);
        add = new JButton("  Add  ");
        save = new JButton("  Save  ");
        print = new JButton("  Print  ");
        reset = new JButton("  Reset  ");
        for(JButton x : new JButton[]{save, print , add , reset})
        {
            x.setBackground(new Color(255, 148, 56));
            x.setForeground(Color.white);
            x.setFont(new Font(Font.SANS_SERIF  , Font.BOLD , 13));
        }
        
        formPanel = new JPanel(){
            {
                this.setLayout(new GridBagLayout());
                GridBagConstraints gc = new GridBagConstraints();
                this.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5,5,5,5),
                             BorderFactory.createTitledBorder
                     (BorderFactory.createEtchedBorder(), "New Order")));
                
                gc.gridy ++;
                gc.insets = new Insets(5,5,5,5);
                gc.gridx = 0; add(new JLabel("Colony Code:") , gc);
                gc.gridx ++; add(colonyCode , gc);
                gc.gridx ++; add(new JLabel(" Colony Name :") , gc);
                gc.gridx ++; add(selectColony , gc);
                
                gc.gridy ++;
                gc.insets = new Insets(5,5,5,5);
                gc.gridx = 0; add(new JLabel("Select Customer :") , gc);
                gc.gridx ++; add(selectUser , gc);
                gc.gridx ++; add(new JLabel(" Name :") , gc);
                gc.gridx ++; add(userName , gc);
                
               
                gc.gridy ++;
                gc.insets = new Insets(5,5,5,5);
                gc.gridx = 0; add(new JLabel("Item Code:") , gc);
                gc.gridx ++; add(itemText , gc);
                gc.gridx ++; add(new JLabel("Select Item:") , gc);
                gc.gridx ++; add(selectItem , gc);
                 
                gc.gridy++;
                gc.insets = new Insets(0,5,5,5);
                gc.gridx =0; add(new JLabel("Quantity(gram):") , gc);
                gc.gridx ++; add(quantity , gc);
                
                //gc.insets = new Insets(5,5,5,5);
                gc.gridy++;
                gc.gridx =0 ;add(add , gc);
                gc.gridx ++; add(save , gc);
                gc.gridx ++; add(print , gc);
                gc.gridx ++; add(reset , gc);
                
                
            }
            
        };
        
        setKeyListener();
        setBottomPanel();
        setTopPanel();
        manageLayout();
        autoComplete();
        setActionListener();
        
        new Thread(()->{
             colonies.addAll(Colony.getColonies());
             Collections.sort(colonies , colCom);
             items.addAll(Item.getItems());
             Collections.sort(items , itemCom);
            Iterator<Item> itrItem = items.iterator();
            while(itrItem.hasNext()){
                itemCodeVector.add(itrItem.next().getItemCode());
            }
            Iterator<Colony> itrCol = colonies.iterator();
            while(itrCol.hasNext()){
                colonyCodeVector.add(itrCol.next().getColonyCode());
            }
        }).start();
       
    }
    
   private void manageLayout(){
       toolBar = new OrderFormToolBar();
       upperSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT) ;
        upperSplitPane.setBottomComponent(billingPanel);
        upperSplitPane.setTopComponent(formPanel);
        
       splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT , upperSplitPane , tablePanel) ;
       //splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT , formPanel , tablePanel) ;
       this.setLayout(new BorderLayout());
       
       this.add(toolBar , BorderLayout.NORTH);
       this.setJMenuBar(menuBar);
       
       
        
        //upperSplitPane.setBottomComponent(new JScrollPane(billingPanel , JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
          //              , JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
        //upperSplitPane.setTopComponent(splitPane);
        
       this.add(splitPane , BorderLayout.CENTER);
       //this.add(upperSplitPane , BorderLayout.CENTER);
       
       toolBar.setShowHideListener(new ActionListener(){
           public void actionPerformed(ActionEvent e) {
               JCheckBox source = (JCheckBox)(e.getSource());
               if(e.getActionCommand().equalsIgnoreCase("form")){
                   formPanel.setVisible(source.isSelected());
               }else if(e.getActionCommand().equalsIgnoreCase("billingList")){
                    billingPanel.setVisible(source.isSelected());
               }
               upperSplitPane.setDividerLocation(.5);
               
               if(!formPanel.isVisible() && !billingPanel.isVisible()){
                   splitPane.setDividerLocation(0);
               }else{
                   splitPane.setDividerLocation(formPanel.getPreferredSize().height+50);
               }
           }
       });
       toolBar.setRefreshListener((e) -> {
           getOrderList();
       });
       toolBar.setFilterDataByDateListener((ev)->{
           OrderFormToolBar.FilterDateActionEvent evv = (OrderFormToolBar.FilterDateActionEvent)ev;
               new Thread(()->{
                if(ev.getID()==1){
                   orderList.clear();;
                    orderList.addAll(Bill.getBillsFromDate(evv.getDate()));
                    otm.refresh();
               }else{
                   orderList.clear();;
                    orderList.addAll(Bill.getBillsOfDate(evv.getDate()));
                    otm.refresh();
               }
            }).start();
       });
       
       toolBar.setPrintListener((ev)->{
           Iterator<Bill> itr = orderList.iterator();
           
           while(itr.hasNext()){
               Bill bill = itr.next();
               resetAll();
               BillingItem.total=0;
               billingItems.clear(); 
               billingItems.addAll(Get.getBillDescriptions(bill.getBillNo(), bill.getBillingDate()));
               //currentShowingBillNo = billNo;
               //currentShowingBillingDate = billingDate;
               total.setText("Rs. "+BillingItem.total);
               btm.refresh();

               //selectColony.setSelectedItem(user.getAddress().getColony());
               //colonyCode.setText(user.getAddress().getColony().getColonyCode());

               /*userListVector.clear();
                    ArrayList<User> uss  = User.getUsersByAddress(user.getAddress()) ;
                    userListVector.addAll(uss);
                    Iterator<User> itruss = uss.iterator();
                    userNamesVector.clear();
                    while(itruss.hasNext()){
                        userNamesVector.add(itruss.next().getName());
                    }

                selectUser.setSelectedItem(user);
                */
               User user = bill.getUser();
                appendReciept(user);
                if(!billingPanel.isVisible()){
                    billingPanel.setVisible(true);
                    splitPane.setDividerLocation(formPanel.getPreferredSize().height+10);
                }
                
                splitPane.setDividerLocation((int)(billingTable.getUI().getPreferredSize(billingTable).getHeight())+prefs.getInt("recieptExpandSize", 50));
            try{
            Toolkit tl = billingPanel.getToolkit();
            PrintJob pj = tl.getPrintJob(OrderForm.this, null, null);
            
           billingPanel.print(pj.getGraphics());
            //billingPanel.printAll(pj.getGraphics());
            //billingPanel.printComponents(pj.getGraphics());
            
            pj.end();
            if(!Update.changeOrderStatus(bill.getBillNo() , OrderStatus.COMPLETED)){
                showMessage("Can not change Order Status ", "sorry ..");
            }
            }catch(Exception ex){
                JOptionPane.showMessageDialog(OrderForm.this, ex.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }

           }
       });
   }

    private void setKeyListener() {
        
        KeyListener kl = new KeyListener(){
            
            public void keyTyped(KeyEvent e){
                 if(e.getSource()==quantity){
                    Pattern p = Pattern.compile("[0-9.]");
                    Matcher m = p.matcher(String.valueOf(e.getKeyChar()));
                    if(!m.matches()){
                       e.setKeyChar('\0');
                    }
                    if(e.getKeyChar()==KeyEvent.VK_PERIOD && quantity.getText().contains(String.valueOf(e.getKeyChar()))){
                        e.setKeyChar('\0');
                    }
                }else if(e.getSource()== selectUser){
                     System.out.println("select User");
                
                }else if(e.getSource()== userName){
                     System.out.println("select User");
                }
            }
            public void keyPressed(KeyEvent e) {
               
            }
            public void keyReleased(KeyEvent e){
                if(e.getSource()==itemText){
                    if(e.getKeyCode()!= KeyEvent.VK_ENTER){
                        String text = itemText.getText().trim();

                        Collections.sort(items , itemCom);
                        int i = Collections.binarySearch(items, new Item(null,0 ,text), itemCom);
                        if(i>=0){
                            selectItem.setSelectedIndex(i);
                        }
                    }else{
                        quantity.requestFocus();
                    }
                } else if(e.getSource()==colonyCode){
                    if(e.getKeyCode()!= KeyEvent.VK_ENTER){
                       try{
                        String text = colonyCode.getText().trim();

                        Collections.sort(colonies , colCom);
                        int i = Collections.binarySearch(colonies, new Colony(null,0 ,text), colCom);
                        if(i>=0){
                            selectColony.setSelectedIndex(i);
                            Colony c = (Colony)selectColony.getSelectedItem();
                            ArrayList<Address> addresses = Address.getAddressesByColony(c.getId());
                            userListVector.clear();
                            Iterator<Address> it = addresses.iterator();
                            ArrayList<User> uss = new ArrayList();
                            while(it.hasNext())
                                  uss.addAll(User.getUsersByAddress(it.next())) ;
                            userListVector.addAll(uss);
                            Iterator<User> itruss = uss.iterator();
                            userNamesVector.clear();
                            while(itruss.hasNext()){
                                userNamesVector.add(itruss.next().getName());
                            }
                        }
                        
                       }catch(NullPointerException ex){
                           System.out.println(ex.getLocalizedMessage());
                       }
                    }else{
                        selectUser.requestFocus();
                    }
                } 
                else if(e.getSource()==quantity){
                    if(e.getKeyCode()== KeyEvent.VK_ENTER){
                        addAction();
                    }
                }
                else if(e.getSource()==selectUser){
                    System.out.println("hello");
                           
                    if(e.getKeyCode()== KeyEvent.VK_ENTER){
                       userName.requestFocus();
                    }
                }
                else if(e.getSource()==userList){
                    System.out.println("hello");
                           
                    if(e.getKeyCode()== KeyEvent.VK_ENTER){
                       userName.requestFocus();
                    }
                }
                else if(e.getSource()==userName){
                    if(e.getKeyCode()== KeyEvent.VK_ENTER){
                       itemText.requestFocus();
                    }
                }
                
                
            }
        
        };
        
        itemText.addKeyListener(kl);
        selectUser.addKeyListener(kl);
        userList.addKeyListener(kl);
        colonyCode.addKeyListener(kl);
        userName.addKeyListener(kl);
        quantity.addKeyListener(kl);
        selectUser.putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);        
    }

    private void autoComplete(){
        AutoCompleteDecorator.decorate(selectUser);
        AutoCompleteDecorator.decorate(selectItem);
        AutoCompleteDecorator.decorate(selectColony);
        AutoCompleteDecorator.decorate(userNameList , userName , ObjectToStringConverter.DEFAULT_IMPLEMENTATION);
        AutoCompleteDecorator.decorate(itemCodeList , itemText , ObjectToStringConverter.DEFAULT_IMPLEMENTATION);
        AutoCompleteDecorator.decorate(colonyCodeList , colonyCode , ObjectToStringConverter.DEFAULT_IMPLEMENTATION);
    }

    private void setActionListener() {
        
        selectUser.addActionListener((ActionEvent ev)->{
            synchronized(this){    
                try{
                    if(selectUser.getSelectedIndex()>=0){
                        User user = (User)selectUser.getSelectedItem();
                        userName.setText((userNamesVector.get(selectUser.getSelectedIndex())));
                    }
                }catch(Exception ex){
                        ex.printStackTrace();
                }
            }
        });

        save.addActionListener((ActionEvent ev)->{
            saveAction();
        });
        add.addActionListener((ActionEvent ev)->{
            addAction();
        });
        reset.addActionListener((ActionEvent ev)->{
            resetAll();
            selectUser.requestFocus();
            itemText.setText("");
            quantity.setText("");
            userName.setText("");
            billingItems.clear();
            BillingItem.total = 0;
            btm.refresh();
            
            currentShowingBillingDate = null;
            currentShowingBillNo = 0;
            
        });
        print.addActionListener((ActionEvent ev)->{
             //splitPane.setDividerLocation((int)(billingTable.getUI().getPreferredSize(billingTable).getHeight())+prefs.getInt("recieptExpandSize", 50));
             splitPane.setDividerLocation((int)(billingTable.getUI().getPreferredSize(billingTable).getHeight())
                     +(companyNameJMS.getPreferredSize().height+
                             companyNameVG.getPreferredSize().height
                             +date.getPreferredSize().height
                             +thanks.getPreferredSize().height + 
                             +info.getPreferredSize().height)+prefs.getInt("recieptExpandSize", 50));
            try{
            Toolkit tl = billingPanel.getToolkit();
            PrintJob pj = tl.getPrintJob(OrderForm.this, null, null);
            
           billingPanel.print(pj.getGraphics());
            //billingPanel.printAll(pj.getGraphics());
            //billingPanel.printComponents(pj.getGraphics());
            
            pj.end();
            if(!Update.changeOrderStatus(BillingItem.billNumber , OrderStatus.COMPLETED)){
                showMessage("Can not change Order Status ", "sorry ..");
            }
            }catch(Exception ex){
                JOptionPane.showMessageDialog(OrderForm.this, ex.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        });
    }
    private void addAction(){
        User u = (User)selectUser.getSelectedItem();
        Item i = (Item)selectItem.getSelectedItem();
        resetReciept();
        appendReciept(u);
        boolean flag = false;
        if(currentShowingBillNo == 0 || currentShowingBillingDate==null ){
            flag = true;
        }
        try{
            float q = new Float(quantity.getText());
        
            int unit = i.getUnitPrize();
            float l = q*unit;
            if(q<21){
                 l = q*unit;
            }else{
                 l = q*unit/1000;
            }
            BillingItem.total+=l;
            
            if(!flag){
               newBillingItems.add(new BillingItem(unit , q , i , l));
            }
            
            billingItems.add(new BillingItem(unit , q , i , l));
            btm.refresh();
            itemText.requestFocus();
            itemText.setText("");
            quantity.setText("");
            userName.setText("");
            total.setText(BillingItem.total+"");
        }catch(NumberFormatException ex){
            showMessage("Quantity is Required" , "Fill Quantity");
        }
    }
        
    private void saveAction(){
        if(billingItems.size()>0){
            int billNumber = 0;
            boolean flag = true;
            if(currentShowingBillNo == 0 || currentShowingBillingDate==null ){
                flag = false;
            }
            System.out.println(flag);
            if(billingItems.size()==0){
                showMessage("Add atleat 1 item" , "Add Items");
            }else{
                User u = (User)selectUser.getSelectedItem();
                Bill bill = new Bill(u ,BillingItem.total);
                if(flag){
                    bill.setBillNo(currentShowingBillNo);
                    Update.updateBill(bill);
                    Iterator<BillingItem> it = newBillingItems.iterator();
                    while(it.hasNext()){
                        BillingItem bi = it.next();
                        BillDescription bd = new BillDescription(new Bill(currentShowingBillNo) , bi.getItem(), bi.getQuantity() , bi.getUnitPrize());
                        String ok  = Save.saveBillDescription(bd);
                        if(ok!=null){
                            showMessage(ok , "Errror");
                        }
                    }
                }else{
                    bill.setBillNo(Bill.getMaxBillNumber()+1);
                    billNumber = Save.saveBill(bill);
                    BillingItem.billNumber = billNumber;
                    Iterator<BillingItem> it = billingItems.iterator();
                    while(it.hasNext()){
                        BillingItem bi = it.next();
                        BillDescription bd = new BillDescription(new Bill(billNumber) , bi.getItem(), bi.getQuantity() , bi.getUnitPrize());
                        String ok  = Save.saveBillDescription(bd);
                        if(ok!=null){
                            showMessage(ok , "Errror");
                        }
                    }
                }

                if(!flag)
                    orderList.add(bill);

                billingItems.clear();
                total.setText("0");
                BillingItem.total=0;
                getOrderList();
                btm.refresh();
                otm.refresh();

            }
        }
    }

    private void setTopPanel() {
        billingPanel = new JPanel();
        billingPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5,5,5,5),
                             BorderFactory.createTitledBorder
                     (BorderFactory.createEtchedBorder(), "Billing")));
        billingTable = new JTable(btm);
        //billingTable.setBorder(null);
        billingTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        billingTable.scrollRectToVisible(billingTable.getCellRect(billingTable.getRowCount()-1, 0, true));
        billingPanel.setLayout(new BorderLayout());
        billingTableScroll = new JScrollPane(billingTable);
        
       billingPanel.add( billingTableScroll, BorderLayout.CENTER);
        //billingPanel.add(billingTable , BorderLayout.CENTER);
        
        
        
        
        billingTable.getTableHeader().setReorderingAllowed(false);
       
        
       billingTable.setShowGrid(false);
        JTableHeader header = billingTable.getTableHeader();
        header.setReorderingAllowed(false);
        header.setDefaultRenderer(new DefaultTableCellRenderer(){
            {
                this.setOpaque(true);
            }
        });
        detailPanel = new JPanel();
        detailPanel.setLayout(new BoxLayout(detailPanel , BoxLayout.Y_AXIS));
        GridBagConstraints gbc = new GridBagConstraints();
        companyNameJMS = new JTextArea("\n\nJMS");
        companyNameVG = new JTextArea("Vegetables And Fruits\n");
        date = new JTextArea("");
        DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
        
        date.setText("Date - \n"+ df.format(new java.util.Date()));
        detailPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        
        
        
        detailPanel.add(companyNameJMS);
        detailPanel.add(companyNameVG);
        //detailPanel.add(new JSeparator());
        
        detailPanel.add(date);
        
        
        
        
        billingPanel.add(detailPanel , BorderLayout.NORTH);
        
        // setting back ground color 
        
        downPanel = new JPanel();
        downPanel.setLayout(new BoxLayout(downPanel , BoxLayout.Y_AXIS));
        billingPanel.add(downPanel ,BorderLayout.SOUTH );
        billingFooter.add(new JTextArea("      Total : "));
        billingFooter.add(total);
        billingFooter.setFloatable(false);
        
        downPanel.add(billingFooter);
        
        thanks = new JTextArea("");
        thanks.setLineWrap(true);
        
        info = new JTextArea();
        downPanel.add(thanks);
        downPanel.add(info);
        
        
        downPanel.setBorder(BorderFactory.createEmptyBorder());
        info.setBorder(BorderFactory.createEmptyBorder());
        thanks.setBorder(BorderFactory.createEmptyBorder());
        billingFooter.setBorder(BorderFactory.createEmptyBorder());
        date.setBorder(BorderFactory.createEmptyBorder());
        companyNameJMS.setBorder(BorderFactory.createEmptyBorder());
        companyNameVG.setBorder(BorderFactory.createEmptyBorder());
        billingTableScroll.setBorder(BorderFactory.createEmptyBorder());
        billingTableScroll.setBackground(Color.white);
        billingTable.getParent().setBackground(Color.white);
    }
    
    private void showMessage(String msg  ,String  title ){
        JOptionPane.showMessageDialog(this, msg, title, JOptionPane.INFORMATION_MESSAGE);
    }

    private void setBottomPanel() {
        tablePanel = new JPanel();
        
        tablePanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5,5,5,5),
                             BorderFactory.createTitledBorder
                     (BorderFactory.createEtchedBorder(), "Order List")));
        table = new JTable(otm);
        table.setShowGrid(true);
        table.setGridColor(Color.gray);
        table.setRowHeight(25);
        addMouseListenerOnOrderTable();
        table.getTableHeader().setReorderingAllowed(false);
        table.setFont(new Font(Font.SANS_SERIF , Font.PLAIN , 15));
        JTableHeader header = table.getTableHeader();
        header.setReorderingAllowed(false);
        header.setDefaultRenderer(new DefaultTableCellRenderer(){
            {
                this.setOpaque(true);
                this.setForeground(Color.white);
                //header.setFont(new Font(Font.SERIF , Font.BOLD , 25));
                this.setBackground(new Color(255, 148, 56));
            }
        });
        
        
        table.setColumnSelectionAllowed(true);
        table.setRowSelectionAllowed(true);
        table.scrollRectToVisible(table.getCellRect(table.getRowCount()-1, 0, true));
        tablePanel.setLayout(new BorderLayout());
        tablePanel.add(new JScrollPane(table) , BorderLayout.CENTER);
        
         table.addMouseMotionListener(new MouseMotionListener(){
            public void mouseDragged(MouseEvent e) {
            }
            public void mouseMoved(MouseEvent e) {
                Point point = e.getPoint();
                int row = table.rowAtPoint(point);
                int column  = table.columnAtPoint(point);
                table.setRowSelectionInterval(row, row);
                table.setColumnSelectionInterval(column, column);
            }
        });
        table.addMouseListener(new MouseListener(){
            public void mouseClicked(MouseEvent e) {
                
                int psize = 90;
                Point point = e.getPoint();
                int row = table.rowAtPoint(point);
                int column  = table.columnAtPoint(point);
                try{
                    int billNo = (Integer)table.getValueAt(row, 0);
                    User user = (User)table.getValueAt(row, 2);
                    BillingItem.billNumber = billNo;
                    Date billingDate = (Date)table.getValueAt(row, 5);
                    String colValue = (String)table.getValueAt(row,column);
                    if(colValue.equalsIgnoreCase("Show details")){
                        resetAll();
                        resetReciept();
                        BillingItem.total=0;
                       billingItems.clear(); 
                       billingItems.addAll(Get.getBillDescriptions(billNo, billingDate));
                       currentShowingBillNo = billNo;
                       currentShowingBillingDate = billingDate;
                       total.setText("Rs. "+BillingItem.total);
                       btm.refresh();
                       
                       selectColony.setSelectedItem(user.getAddress().getColony());
                       colonyCode.setText(user.getAddress().getColony().getColonyCode());
                       
                       userListVector.clear();
                            ArrayList<User> uss  = User.getUsersByAddress(user.getAddress()) ;
                            userListVector.addAll(uss);
                            Iterator<User> itruss = uss.iterator();
                            userNamesVector.clear();
                            while(itruss.hasNext()){
                                userNamesVector.add(itruss.next().getName());
                            }
                            
                        selectUser.setSelectedItem(user);
                        
                        appendReciept(user);
                        if(!billingPanel.isVisible()){
                            billingPanel.setVisible(true);
                            splitPane.setDividerLocation(formPanel.getPreferredSize().height+10);
                        }
                        //splitPane.setDividerLocation(billingPanel.getHeight());
                        
                       
                    }
                }catch(ClassCastException ex){
                    ex.printStackTrace();
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

    private void setShortcuts() {
        save.setMnemonic('s');
        print.setMnemonic('p');
        add.setMnemonic('a');
    }
    private void addMouseListenerOnOrderTable(){
        table.getTableHeader().addMouseListener(new MouseListener(){
            public void mouseClicked(MouseEvent e) {
                Point point = e.getPoint();
                int col = table.columnAtPoint(point);
                Comparator<Bill> com = null;
                if(col==4){
                    com = new Bill.SortByDate();
                }
                else if(col==3){
                    com = new Bill.SortByAmmount();
                }
                else if(col==2){
                    com = new Bill.SortByName();
                }
                else if(col==1){
                    com = new Bill.SortByCustomerCode();
                }
                else if(col==0){
                    com = new Bill.SortByBillNo();
                }
                else if(col==5){
                    com = new Bill.SortByOrderStatus();
                }
                if(com==null){
                        com = new Bill.SortByBillNo();
                }
                Collections.sort(orderList, com);
                otm.refresh();
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

    private void getOrderList() {
        orderList.clear();;
        orderList.addAll(Bill.getBillsOfDate(new Date(new java.util.Date().getTime())));
        otm.refresh();
    }

    private void settingPropertiesOnBillingPanelComponents() {
        colonyCode.requestFocus();
        billingPanel.setBackground(Color.white);
        billingPanel.setBorder(new LineBorder(Color.white));
        billingTable.setBorder(new LineBorder(Color.white));
        detailPanel.setBorder(new LineBorder(Color.white));
        billingTable.setBackground(Color.white);
        detailPanel.setBackground(Color.white);
        upperSplitPane.setBackground(Color.white);
        downPanel.setBackground(Color.white);
        upperSplitPane.setBorder(new LineBorder(Color.white));
        for(JTextArea x : new JTextArea[]{date,OrderForm.this.total ,thanks , info , companyNameJMS , companyNameVG}){
            x.setBorder(new LineBorder(Color.white));
            x.setEditable(false);
            x.setWrapStyleWord(true);
            x.setBackground(Color.white);
        }
        
        
    }

    private void setMenuBar() {
        menuBar = new OrderFrameMenuBar(new OrderFrameActionListener());
    }  
    private void panelsizeListener() {
        billingTable.addComponentListener(new ComponentListener() {
            public void componentResized(ComponentEvent e) {
                
                Dimension d = billingTable.getPreferredScrollableViewportSize();
                Dimension paneld = billingPanel.getPreferredSize();
                Dimension newd = new Dimension(d.width+paneld.width , d.height+paneld.height);
                
            }
            public void componentMoved(ComponentEvent e) {
            }
            public void componentShown(ComponentEvent e) {
            }
            public void componentHidden(ComponentEvent e){
                System.out.println("hidden");
            }
        } );
    }

    private void setUpperSliderSize() {
        upperSplitPane.setOneTouchExpandable(true);
        upperSplitPane.setDividerLocation(prefs.getInt("billSlideSize",950));
        //upperSplitPane.setDividerLocation(900);
        upperSplitPane.resetToPreferredSizes();
    }
    public class OrderFrameActionListener implements ActionListener{

        public void actionPerformed(ActionEvent e) { }
        public void changeBillSize(int size){
            upperSplitPane.setDividerLocation(size);
            //upperSplitPane.resetToPreferredSizes();
            System.out.println(size);
            
            prefs.putInt("billSlideSize", size);
            
        }
        public void changeLogoSize(int size){
            prefs.putInt("JMSSize", size);
            setFontSize();
        }
        public void changeDateSize(int size){
            prefs.putInt("dateSize", size);
            setFontSize();
        }
        public void changeFooterSize(int size){
            prefs.putInt("footerSize", size);
            setFontSize();
        }
        public void changeTableSize(int size){
            prefs.putInt("tableSize", size);
            setFontSize();
        }
        public void changerecieptExpandSize(int size){
            prefs.putInt("recieptExpandSize", size);
            
        }
        
    }
    
    private void setFontSize(){
        
        info.setFont(new Font(Font.SANS_SERIF , Font.PLAIN , prefs.getInt("footerSize", 10)-1));
        thanks.setFont(new Font(Font.SERIF , Font.ITALIC , prefs.getInt("footerSize" , 10)));
         billingTable.setFont(new Font(Font.MONOSPACED , Font.PLAIN ,prefs.getInt("tableSize" , 9)));
         companyNameJMS.setFont(new Font( Font.SANS_SERIF , Font.BOLD , prefs.getInt("JMSSize" , 16)));
        companyNameVG.setFont(new Font( Font.SANS_SERIF , Font.BOLD , prefs.getInt("JMSSize" , 16)-4));
        date.setFont(new Font(Font.SANS_SERIF , Font.PLAIN , prefs.getInt("dateSize", 8)));
    }
    
    private void resetReciept(){
        DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
        date.setText("Date - \n"+ df.format(new java.util.Date()));
    }
    private void resetAll(){
        BillingItem.total = 0;
        newBillingItems.clear();
        resetReciept();
    }
    private void appendReciept(User u){
        try{
        thanks.setText("\n\nWelome to JMS , We Look Forword To Working With You");
        info.setText("For Any Complaint Call - 6260088150");
        date.append("\n"+u.getUserId());
        date.append("\n" + u.getAddress().getHouseNumber()
                +" "+u.getAddress().getColony()
                    + " "+ u.getAddress().getArea());
    
        }catch(Exception ex){
            System.out.println(ex.getLocalizedMessage() + "at append reciept" );
         }
    }
    private void setKeyListenerOnBillingPanel(){
        
        KeyListener al = new KeyListener() {
            public void keyTyped(KeyEvent e) {
            }
            public void keyPressed(KeyEvent e) {
                System.out.println(e.getKeyChar());
                if(e.isControlDown() && e.getKeyChar()=='+'){
                    upperSplitPane.setDividerLocation(upperSplitPane.getDividerLocation()-5);
                    prefs.putInt("billSlideSize", upperSplitPane.getDividerLocation());
                }
                else if(e.isControlDown() && e.getKeyChar()=='-'){
                    upperSplitPane.setDividerLocation(upperSplitPane.getDividerLocation()+5);
                    prefs.putInt("billSlideSize", upperSplitPane.getDividerLocation());
                }
            }
            public void keyReleased(KeyEvent e) {
            }
        };
        billingTable.addKeyListener(al);
        companyNameJMS.addKeyListener(al);
       companyNameVG.addKeyListener(al);
        billingPanel.addKeyListener(al);
        date.addKeyListener(al);
        detailPanel.addKeyListener(al);
        
        billingTable.setRequestFocusEnabled(true);
        companyNameVG.setRequestFocusEnabled(true);
        companyNameJMS.setRequestFocusEnabled(true);
        billingPanel.setRequestFocusEnabled(true);
        date.setRequestFocusEnabled(true);
        detailPanel.setRequestFocusEnabled(true);
        System.out.println("run");
        
        
        
    }
    
}



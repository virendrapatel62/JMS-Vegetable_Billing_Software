
package gui;

import java.awt.Cursor;
import gui.menubars.MainFrameMenuBar;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.swing.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import models.Bill;

public class MainFrame extends JFrame{
    private JButton addVeg , addCustomer ,requirementOfTheDay, addColony , printStatement , generateBill;
    private GridBagConstraints gc;
    private JButton order;
    private JButton allCustomers;
    private MainFrameMenuBar menuBar;
    private JPanel panel;
    public static MainFrame mainFrame;
    private Border border;
    private JLabel jms;
    private Preferences prefs = Preferences.userRoot();
    private Requirement requirement;
    final JLabel time = new JLabel("Time");
    private JSplitPane splitPane;
    private VegetableForm vegetableform;
    private ColonyForm colonyform;
    private UserForm userForm;
    private OrderForm orderForm;
    private StatementFrame statementFrame;
    private CustomerFrame customerFrame;
    private OrderForm generateBillFrame;
    private JLabel dev = new JLabel("Developed By - Virendra patel , Mobile - 9144460897 , Email - patelvirendra62@gmail.com         ");
    
    public MainFrame(){
        super("JMS Vegetable and fruits");
        try{
            mainFrame = this;
            Dimension d = Toolkit.getDefaultToolkit().getScreenSize(); 
           //setBounds(50,50,d.width-300 , d.height-300);
           this.setExtendedState(JFrame.MAXIMIZED_BOTH);
           this.setMinimumSize(new Dimension(400 , 500));
            //this.setMaximumSize(new Dimension(d.width-300 , d.height-100));
            this.pack();
            this.setVisible(true);
            
            this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            initialization();
            setLayout();
            setActionListener();
            setWindowListener();
            setBackground();
            setButtonBgColor();
            setFontColor();
            setFooterText();
            this.setIconImage(new ImageIcon(getClass().getResource("/images/icon.jpeg")).getImage());
        }catch(Exception ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,ex.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        if(!prefs.getBoolean("termsa", false))
            new TermsAndConditions(this);
            
        this.addComponentListener(new ComponentListener() {
            public void componentResized(ComponentEvent e) {
                MainFrame.this.validate();
            }
            public void componentMoved(ComponentEvent e) {
            }
            public void componentShown(ComponentEvent e) {
            }
            public void componentHidden(ComponentEvent e) {
            }
        });
        panel.addComponentListener(new ComponentListener() {
            public void componentResized(ComponentEvent e) {
                MainFrame.this.revalidate();
                MainFrame.this.repaint();
                System.out.println("Panel resize");
            }
            public void componentMoved(ComponentEvent e) {
            }
            public void componentShown(ComponentEvent e) {
            }
            public void componentHidden(ComponentEvent e) {
            }
        });
        
        for(Component c: this.getComponents()){
            if(c instanceof JLabel)
                ((JLabel)c).setOpaque(false);
            if(c instanceof JPanel)
                ((JPanel)c).setOpaque(false);
            if(c instanceof JButton)
                ((JButton)c).setOpaque(false);
            if(c instanceof JSplitPane)
                ((JSplitPane)c).setOpaque(false);
            
        }
        splitPane.setOpaque(false);
        new Thread(()->{
            cheakForLicense();
        }).start();
      
        
    }

    public JPanel getPanel() {
        return panel;
    }
    public void setFontColor(){
        time.setOpaque(true);
        jms.setOpaque(true);
        jms.setFont(new Font(Font.SANS_SERIF, Font.BOLD , 25));
           time.setFont(new Font(Font.SANS_SERIF, Font.BOLD , 20));
           //jms.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Colors.BORDER.getColor(),3)
            //, BorderFactory.createEmptyBorder(5, 5, 5, 5)));
           jms.setForeground(new Color(prefs.getInt("fontR", 225),prefs.getInt("fontG", 148), prefs.getInt("fontB", 56)));
           time.setForeground(new Color(prefs.getInt("fontR", 225),prefs.getInt("fontG", 148), prefs.getInt("fontB", 56)));
    }
    public void setBackground() {
          this.getContentPane().setBackground(new Color(prefs.getInt("bgR", 225),prefs.getInt("bgG", 148), prefs.getInt("bgB", 56)));
          panel.setBackground(new Color(prefs.getInt("bgR", 225),prefs.getInt("bgG", 148), prefs.getInt("bgB", 56), 0));
          time.setBackground(new Color(prefs.getInt("bgR", 225),prefs.getInt("bgG", 148), prefs.getInt("bgB", 56)));
          jms.setBackground(new Color(prefs.getInt("bgR", 225),prefs.getInt("bgG", 148), prefs.getInt("bgB", 56)));
          dev.setBackground(new Color(prefs.getInt("bgR", 225),prefs.getInt("bgG", 148), prefs.getInt("bgB", 56)));
          splitPane.setBackground(new Color(prefs.getInt("bgR", 225),prefs.getInt("bgG", 148), prefs.getInt("bgB", 56)));
          splitPane.setOneTouchExpandable(true);
    }

    private void initialization() {
        String size = "128/";
        if(true){
            size = "64/";
        }
        addVeg = new JButton( "Add Vegetable",getImage("/images/"+size+"veg.png"));
        addVeg.setMnemonic('v');
        addCustomer = new JButton( "Add Customer",getImage("/images/"+size+"addCustomer.png"));
        addCustomer.setMnemonic('c');
        addColony = new JButton( "Add Colony",getImage("/images/"+size+"addColony.png"));
        addColony.setMnemonic('l');
        printStatement = new JButton( "Print Statement",getImage("/images/"+size+"statement.png"));
        printStatement.setMnemonic('s');
        generateBill = new JButton( "Generate Bill",getImage("/images/"+size+"bill.png"));
        generateBill.setMnemonic('b');
        order = new JButton( "Place Order",getImage("/images/"+size+"order.png"));
        order.setMnemonic('o');
        allCustomers = new JButton( "All Customers",getImage("/images/"+size+"all.png"));
        allCustomers.setMnemonic('m');
        requirementOfTheDay = new JButton( "Requirement of the day",getImage("/images/"+size+"chart.png"));
        requirementOfTheDay.setMnemonic('r');
        JButton[] temp = new JButton[]{addVeg ,order, allCustomers,requirementOfTheDay ,addCustomer , addColony , generateBill , printStatement };
        MouseListener ml = getMouseListenerOnButtons();
        
        for(JButton x: temp){
            x.setHorizontalTextPosition(JButton.CENTER);
            x.setVerticalTextPosition(JButton.BOTTOM);
            x.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Colors.BORDER.getColor(),4)
            , BorderFactory.createEmptyBorder(2, 2, 2, 2)));
            x.addMouseListener(ml);
            
            x.setFont(new Font(Font.SANS_SERIF , Font.PLAIN , 15));
            //x.setPreferredSize(new Dimension(300,300));
        }
        
        menuBar = new MainFrameMenuBar(this);
        settingRuirementOfTheDayListener();
        this.setJMenuBar(menuBar);
        panel = new JPanel();
        
        requirement = new Requirement();
            requirement.setVisible(false);
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT ,panel ,requirement);
        
        border = BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10)
                ,BorderFactory.createEtchedBorder(Colors.BORDER.getColor(),Colors.SHADOW.getColor()));
        panel.setBorder(border);
    }
    public  void setButtonBgColor(){
        for(JButton x : new JButton[]{addVeg ,order , requirementOfTheDay ,  allCustomers ,addCustomer , addColony , generateBill , printStatement }){
            x.setBackground(new Color(prefs.getInt("buttonR", 225),prefs.getInt("buttonG", 148), prefs.getInt("buttonB", 56)));
        }
    }
   
    private ImageIcon getImage(String path){
        URL url = getClass().getResource(path);
        ImageIcon img = new ImageIcon(url);
        return img;
    }

    private void setLayout() {
        panel.setLayout(new GridBagLayout());
        this.setLayout(new BorderLayout());
        this.add(splitPane);
        gc = new GridBagConstraints();
        gc.insets = new Insets(10,10,10,10);
        // row 1
        gc.gridy = 0;
        gc.gridwidth= 4;
        gc.fill = GridBagConstraints.CENTER;
        jms = new JLabel("JMS Vegetable And Fruits");
        panel.add(jms , gc);
        
        gc.gridy ++;
        panel.add(time , gc);
        
        gc.fill = GridBagConstraints.BOTH;
        gc.insets = new Insets(10,10,10,10);
        
        gc.weightx =1.0;
        gc.weighty =1.0;
        gc.gridwidth=1;
        gc.gridwidth=1;
        
        //row 1 
        gc.insets = new Insets(50,10,10,10);
        gc.gridy ++;
        gc.gridx = 0;
        panel.add(addVeg , gc);
        //gc.insets = new Insets(10,10,10,10);
        gc.gridx++;
        panel.add(addCustomer , gc);
        gc.gridx++;
        panel.add(addColony , gc);
        //gc.insets = new Insets(50,10,10,200);
        gc.gridx ++;
        panel.add(order , gc);
        
        // row 2
        gc.insets = new Insets(10,10,50,10);
        gc.gridy ++;
        gc.gridx = 0;
        panel.add(generateBill , gc);
        gc.gridx++;
        //gc.insets = new Insets(10,10,10,10);
        panel.add(printStatement , gc);
        gc.gridx++;
        panel.add(allCustomers , gc);
       // gc.insets = new Insets(10,10,50,200);
        gc.gridx++;
        panel.add(requirementOfTheDay , gc);
        
        
        final SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy  KK:mm:ss a");
        //this.add(l , BorderLayout.NORTH);
          //      l.setBounds(50, 1000, 200, 10);
                 TimerTask t = new TimerTask(){
                        public void run() {
                            time.setText(df.format(new java.util.Date()));
                             
                        }
                    };
                 Timer  timer = new Timer();
                 timer.schedule(t, 1 , 1000);
                 
                 
                
    }

    private void setActionListener() {
        ActionListener al = new ActionListener(){
            public void actionPerformed(ActionEvent ev){
                JButton button = (JButton)(ev.getSource());
                if(cheakForLicenseForEachAction()){
                    if(button == addVeg){
                        new Thread(()->{
                            if(vegetableform==null)
                                 vegetableform = new VegetableForm(MainFrame.this);
                            else
                                vegetableform.setVisible(true);
                        }).start();
                    }
                    else if(button == addColony){
                        new Thread(()->{
                            if(colonyform==null)
                                colonyform = new ColonyForm(MainFrame.this);
                            else
                            colonyform.setVisible(true);
                        }).start();
                    }
                    else if(button == addCustomer){
                        if(userForm==null)
                        userForm = new UserForm(MainFrame.this);
                        else
                        userForm.setVisible(true);
                    }
                    else if(button == order){
                        new Thread(()->{
                            if(orderForm==null)
                                 orderForm = new OrderForm();
                            else
                                orderForm.setVisible(true);

                        }).start();
                    }
                    else if(button == generateBill){
                        new Thread(()->{
                            if(generateBillFrame==null)
                                 generateBillFrame = new OrderForm();
                            else
                                generateBillFrame.setVisible(true);

                        }).start();
                    }
                    else if(button == printStatement){
                        new Thread(()->{
                            if(statementFrame==null)
                                 statementFrame = new StatementFrame();
                            else
                                statementFrame.setVisible(true);

                        }).start();
                    }
                    else if(button == allCustomers){
                        new Thread(()->{
                            if(customerFrame==null)
                                customerFrame =new CustomerFrame();
                            else
                                customerFrame.setVisible(true);
                        }).start();
                    }
                    else if(button == requirementOfTheDay){
                        if(requirement.isVisible())
                            requirement.setVisible(false);
                       else
                        requirement.setVisible(true);

                        splitPane.setDividerLocation(0.8);
                        new Thread(()->{
                            requirement.refresh();
                        }).start();

                    }
                }
            }
        };
        
        JButton[] temp = new JButton[]{addVeg ,allCustomers,requirementOfTheDay, order, addCustomer , addColony , generateBill , printStatement };
        
        for(JButton x: temp){
            x.addActionListener(al);
        }
    }
double d = 1.0;
    private void setWindowListener() {
        this.addWindowListener(new WindowListener(){
            public void windowOpened(WindowEvent e) {
            }
            public void windowClosing(WindowEvent e) {
                System.exit(0);
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
    
    public static void cheakForLicense(){
        Preferences pre = Preferences.userRoot().node("license");
        int limit  = pre.getInt("limit", 2000);
        int totalBills  = Bill.getCount();
        System.out.println(limit);
        System.out.println(totalBills);
        if(pre.getBoolean("trial", true)){
            if(totalBills>limit){
                JOptionPane.showMessageDialog(MainFrame.mainFrame, 
                        "Trial Version Limit Crossed , purchase software to continue.., \n Limit = "+limit
                        +"\n Generated Bills = "+ totalBills
                        +"\n Remaining = " + 0,
                        "license Expired", JOptionPane.WARNING_MESSAGE);
            }
            else if(Bill.getCount()>limit-500){
                JOptionPane.showMessageDialog(MainFrame.mainFrame, 
                        "you are near to Trial limit , \n Limit = "+limit
                        +"\n Generated Bills = "+ totalBills
                        +"\n Remaining = " + (limit-totalBills),
                        "license will Expire Soon", JOptionPane.WARNING_MESSAGE);
                
            }
            
        }
    }
    public static boolean cheakForLicenseForEachAction(){
        Preferences pre = Preferences.userRoot().node("license");
        int limit  = pre.getInt("limit", 2000);
        int totalBills  = Bill.getCount();
        System.out.println(limit);
        System.out.println(totalBills);
        if(pre.getBoolean("trial", true)){
            if(totalBills>limit){
                JOptionPane.showMessageDialog(MainFrame.mainFrame, 
                        "Trial Version Limit Crossed , purchase software to continue.., \n Limit = "+limit
                        +"\n Generated Bills = "+ totalBills
                        +"\n Remaining = " + 0,
                        "license Expired", JOptionPane.WARNING_MESSAGE);
                return false;
            }
            
        }
        return true;
    }
    
    
    
    // getter setter 

    public Requirement getRequirement() {
        return requirement;
    }
    private void settingRuirementOfTheDayListener(){
        menuBar.setRequirementOfTheDayListener((avt)->{
            Timer  timer = new Timer();
                    TimerTask t = new TimerTask(){
                        public void run() {
                            
                            requirement.setVisible(true);
                            System.out.println(d);
                            if(d>=0.8)
                                 splitPane.setDividerLocation(d-=0.010);
                            else{
                                this.cancel();
                            }
                        }
                    };
                    TimerTask t2 = new TimerTask(){
                        public void run() {
                            
                            System.out.println(d);
                            if(d<1.0)
                                 splitPane.setDividerLocation(d+=0.010);
                            else{
                                requirement.setVisible(false);
                                this.cancel();
                            }
                        }
                    };
                    if(d==1.0)
                        timer.schedule(t, 0 , 1);
                    else
                        timer.schedule(t2, 0 ,1);
                    
                    requirement.refresh();
                    
        });
    }
    
    private  MouseListener getMouseListenerOnButtons(){
         return new MouseListener() {
            public void mouseClicked(MouseEvent e) {
            }
            public void mousePressed(MouseEvent e) {
            }
            public void mouseReleased(MouseEvent e) {
            }
            public void mouseEntered(MouseEvent e) {
                JButton b = (JButton)e.getSource();
                b.setBackground(Color.lightGray);
                b.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            public void mouseExited(MouseEvent e){
                JButton b = (JButton)e.getSource();
                b.setBackground(new Color(prefs.getInt("buttonR", 225),prefs.getInt("buttonG", 148), prefs.getInt("buttonB", 56)));
            }
        };
    }

    private void setFooterText() {
        
                 this.add(dev , BorderLayout.SOUTH);
                 dev.setOpaque(false);
                 jms.setOpaque(false);
                 panel.setOpaque(false);
                 time.setOpaque(false);
                
                 dev.setForeground(Color.darkGray);
                 dev.setFont(new Font(Font.MONOSPACED , Font.PLAIN , 15));
                 //dev.setHorizontalAlignment(SwingConstants.RIGHT);
                 Timer  timer = new Timer();
                    TimerTask t = new TimerTask(){
                        public void run() {
                            
                            //if(dev.getLocation().x < Toolkit.getDefaultToolkit().getScreenSize().width)
                            if(dev.getLocation().x > (-dev.getPreferredSize().width))
                                 dev.setLocation(dev.getLocation().x-1,dev.getLocation().y);
                            else
                                //dev.setLocation(-dev.getPreferredSize().width,dev.getLocation().y);
                                dev.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width,dev.getLocation().y);
                        }
                    };
                    
                    timer.schedule(t,0,10);
                 Timer  jmstimer = new Timer();
                    TimerTask t3 = new TimerTask(){
                        String s = "JMS Vegetable And Fruits.....";
                        int l = s.length();
                        int n = 1;
                        public void run() {
                            if(n==l){
                              n = 1;
                                jms.setText(s.substring(0, n));
                              
                            }
                            else{
                                jms.setText(s.substring(0, n));
                                n++;
                            }
                        }
                    };
                    
                    //jmstimer.schedule(t3,0,200);
                
    }
    
    
}


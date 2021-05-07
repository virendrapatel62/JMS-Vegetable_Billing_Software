
package gui.menubars;

import controllers.Excel;
import controllers.SendMail;
import gui.ColonyForm;
import gui.CustomerFrame;
import gui.DeveloperLogin;
import gui.MainFrame;
import gui.OrderForm;
import gui.StatementFrame;
import gui.UserForm;
import gui.VegetableForm;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javafx.scene.Cursor;
import javax.swing.*;
import javax.swing.colorchooser.ColorSelectionModel;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.multi.MultiLookAndFeel;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.plaf.synth.SynthLookAndFeel;
import models.Address;
import models.Bill;
import models.BillDescription;
import models.Colony;
import models.Item;
import models.User;

public class MainFrameMenuBar extends JMenuBar{

    private final JMenu setting;
    private final JMenuItem dbSetting;
    private Component parent;
    private JPasswordField password;
    private Preferences prefs = Preferences.userRoot();
    private JTextField user;
    private JTextField host;
    private JPanel panel;
    private JButton save;
    private JDialog dialog ;
    private JButton test;
                private JLabel testing;
    private final JMenu themes;
    private final JCheckBoxMenuItem style1;
    private final JCheckBoxMenuItem style4;
    private final JCheckBoxMenuItem style3;
    private final ButtonGroup buttonGroup;
    private final JCheckBoxMenuItem style6;
    private final JCheckBoxMenuItem style7;
    private final JMenu colors;
    private final JMenuItem background;
    private JDialog colorDialog;
    private final JMenuItem buttons;
    private final JMenu open;
    private final JMenuItem billing;
    private final JMenuItem order;
    private final JMenuItem addColony;
    private final JMenuItem addCustomer;
    private final JMenuItem addVeg;
    private final JMenuItem statement;
    private final ActionListener openAl;
    private final JMenuItem requirementOfTheDay;
    private  ActionListener requirementOfTheDayListener;
    private final JMenuItem allCustomers;
    private final JMenuItem fontColor;
    private final JMenu help;
    private final JMenuItem about;
    private final JMenuItem sortcuts;
    private final JMenu backup;
    private final JMenuItem mail;
    private final JMenuItem export;
    private final JCheckBoxMenuItem style0;
    private final JCheckBoxMenuItem setLaf;
    private final JMenu login;
    private final JMenuItem devLogin;
    public MainFrameMenuBar(Component parent){
        this.parent = parent;
        setting = new JMenu("Preferences");
        login = new JMenu("Login");
        backup = new JMenu("Backup");
        colors = new JMenu("Colors");
        open = new JMenu("Open");
        help = new JMenu("help");
        dbSetting = new JMenuItem("DB preference");
        dbSetting.setMnemonic('d');
        setting.setMnemonic('p');
        open.setMnemonic('o');
        backup.setMnemonic('b');
        setting.add(dbSetting);
        
        about = new JMenuItem("About");
        sortcuts = new JMenuItem("Shortcut keys");
        about.setMnemonic('a');
        sortcuts.setMnemonic('s');
        background = new JMenuItem("Background Color");
        
        buttons = new JMenuItem("Buttons Color");
        fontColor = new JMenuItem("Font Color");
        background.setMnemonic('b');
        buttons.setMnemonic('t');
        fontColor.setMnemonic('f');
        colors.setMnemonic('c');
        
        colors.add(background);
        colors.add(buttons);
        colors.add(fontColor);
        themes = new JMenu("Themes");
        themes.setMnemonic('t');
        setLaf = new JCheckBoxMenuItem("Use Look And Feel ?");
        style0 = new JCheckBoxMenuItem("No Style");
        style1 = new JCheckBoxMenuItem("Style 1");
        style1.setSelected(true);
        style3 = new JCheckBoxMenuItem("Style 2");
        style4 = new JCheckBoxMenuItem("Style 3");
        style6 = new JCheckBoxMenuItem("Style 4");
        style7 = new JCheckBoxMenuItem("Style 5");
        buttonGroup = new ButtonGroup();
        ThemeListener al = new ThemeListener();
        themes.add(setLaf);
            setLaf.addActionListener(al);
            themes.addSeparator();
        for(JCheckBoxMenuItem i : new JCheckBoxMenuItem[]{style0,style1,style3,style4, style6, style7}){
            themes.add(i);
            i.addActionListener(al);
            themes.addSeparator();
            buttonGroup.add(i);
        }
        
        help.add(sortcuts);
        help.add(about);
        help.setMnemonic('h');
        
        addVeg = new JMenuItem("Add new Vegetable");
        addVeg.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK));
        addCustomer = new JMenuItem("Add new Customer");
        addCustomer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
        addColony = new JMenuItem("Add new Colony");
        addColony.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_MASK));
        order = new JMenuItem("New Order");
        order.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
        billing = new JMenuItem("Billing");
        billing.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.CTRL_MASK));
        statement = new JMenuItem("Statement");
        statement.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
        requirementOfTheDay = new JMenuItem("Requirement Of The Day");
        requirementOfTheDay.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_MASK));
        allCustomers = new JMenuItem("All Customers");
        allCustomers.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, InputEvent.CTRL_MASK));
        
        devLogin = new JMenuItem("Developer Login");
        devLogin.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_MASK));
        
        login.setVisible(true);
        login.add(devLogin);
        
        mail = new JMenuItem("Mail");
        
        mail.setMnemonic('m');
        
        export = new JMenuItem("Export TO Excel");
        export.setMnemonic('e');
        backup.add(mail);
        backup.add(export);
        
        
        openAl = new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                Object s = e.getSource();
                if(MainFrame.cheakForLicenseForEachAction()){
                    if(s == addVeg){
                        new Thread(()->{
                        new VegetableForm(MainFrame.mainFrame);
                        }).start();
                    }
                    else if(s == addColony){
                        new Thread(()->{
                        new ColonyForm(MainFrame.mainFrame);
                        }).start();
                    }
                    else if(s == addCustomer){
                        new UserForm(MainFrame.mainFrame);

                    }
                    else if(s == order){
                        new Thread(()->{
                        new OrderForm();
                        }).start();
                    }
                    else if(s == billing){
                        new Thread(()->{
                        new OrderForm();
                        }).start();
                    }
                    else if(s == statement){
                        new Thread(()->{
                        new StatementFrame();
                        }).start();
                    }
                    else if(s == allCustomers){
                        new Thread(()->{
                        new CustomerFrame();
                        }).start();
                    }
                    else if(s == requirementOfTheDay){
                       if(requirementOfTheDayListener!=null){
                           requirementOfTheDayListener.actionPerformed(null);
                       }
                    }
                }
                if(s == devLogin){
                   new DeveloperLogin();
                    System.out.println("hellooooooo D");
                }
            }
        };
        for(JMenuItem i : new JMenuItem[]{addVeg , allCustomers , addCustomer , addColony , order , billing , statement , requirementOfTheDay}){
            open.add(i);
            i.addActionListener(openAl);
            open.addSeparator();
        }
        devLogin.addActionListener(openAl);
        
        this.add(open);
        this.add(setting);
        this.add(themes);
        this.add(colors);
        this.add(backup);
        this.add(help);
        this.add(login);
        
        setActionOnDbSetting();
       setActionsOnColors();
        
        
    }

    private void setActionOnDbSetting() {
        dbSetting.addActionListener((ActionEvent ev)->{
            dialog = new JDialog(){
                
                
                {
                   this.setTitle("Database Preference");
                   this.setResizable(false);
                   this.setLocationRelativeTo(parent);
                   //adding components
                   
                   panel = new JPanel(new GridBagLayout());
                   panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10),
                           BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Database Preference")));
                   this.add(panel);
                   GridBagConstraints gc = new GridBagConstraints();
                   
                   host = new JTextField(15);
                   user = new JTextField(15);
                   testing = new JLabel();
                   password = new JPasswordField(15);
                   save = new JButton(" Save ");
                   test = new JButton(" Check Connection ");
                   
                   gc.insets = new Insets(5,5,5,5);
                   gc.anchor =  GridBagConstraints.EAST;
                   gc.gridy = 0;
                   gc.gridx = 0; panel.add(new JLabel("Host : "), gc);
                   gc.gridx ++; panel.add(host ,gc);
                   
                   gc.gridy++;
                   gc.gridx = 0; panel.add(new JLabel("User : ") , gc);
                   gc.gridx ++; panel.add(user , gc);
                   
                   gc.gridy++;
                   gc.gridx = 0; panel.add(new JLabel("Password :") ,gc);
                   gc.gridx++; panel.add(password ,gc);
                   
                   
                   gc.gridy++;
                   JSeparator sp = new JSeparator();
                   gc.gridwidth = 2;
                   sp.setPreferredSize(new Dimension(panel.getPreferredSize().width-50 ,5));
                   gc.gridx = 0; panel.add(sp ,gc);
                   
                   gc.gridy++;
                   gc.gridwidth = 2;
                   gc.anchor = GridBagConstraints.CENTER;
                   gc.gridx = 0; panel.add(testing,gc);
                   
                   gc.gridy++;
                   gc.gridwidth = 1;
                   gc.gridx = 0; panel.add(test,gc);
                   gc.gridx ++; panel.add(save,gc);
                   
                   Preferences pref = Preferences.userRoot();
                   host.setText(pref.get("host", ""));
                   user.setText(pref.get("user", ""));
                   password.setText(pref.get("password", ""));
                   
                   this.setSize(new Dimension(panel.getPreferredSize().width+50 , panel.getPreferredSize().height+50));
                   this.setVisible(true);
                   this.pack();
                   this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                   
                }
            };
             setActionOnSaveButton();
        });
    }

    private void setActionOnSaveButton() {
        save.addActionListener((ActionEvent ev)->{
            Preferences pref = Preferences.userRoot();
            
            String hostText = host.getText().trim();
            String userText = user.getText().trim();
            char[] pass = password.getPassword();
            String temp  = null;
            if(pass != null){
              temp = String.valueOf(pass);   
            }
            
            pref.put("host", hostText);
            pref.put("user", userText);
            pref.put("password", temp);
            dialog.dispose();
            
        });
        test.addActionListener((ActionEvent ev)->{
            String hostText = host.getText().trim();
            String userText = user.getText().trim();
            char[] pass = password.getPassword();
            String temp  = null;
            if(pass != null){
              temp = String.valueOf(pass);   
            }
                    testing.setText("Checking Connection....");
                    if(Item.checkConnection(hostText , userText , temp))
                            testing.setText("Connected...");
                    else 
                        testing.setText("Connection Error..");
                    
                    dialog.pack();
        });
    }
    class ThemeListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            Preferences prefs = Preferences.userRoot();
            try {
                if(e.getSource()==style1){
                    prefs.putInt("theme", 1);
                    UIManager.setLookAndFeel(new NimbusLookAndFeel());
                }else if(e.getSource()==style3){
                    prefs.putInt("theme", 2);
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
                }else if(e.getSource()==setLaf){
                    prefs.putBoolean("laf", setLaf.isSelected());
                }
                else if(e.getSource()==style0){
                    prefs.putInt("theme", 0);
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                }else if(e.getSource()==style4){
                    prefs.putInt("theme", 3);
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                }else if(e.getSource()==style6){
                    prefs.putInt("theme", 4);
                    UIManager.setLookAndFeel(new MetalLookAndFeel());
                }else if(e.getSource()==style7){
                    prefs.putInt("theme", 5);
                    UIManager.setLookAndFeel(new SynthLookAndFeel());
                
                }SwingUtilities.updateComponentTreeUI(MainFrame.mainFrame);
                MainFrame.mainFrame.pack();
                
                
            }catch(Exception ex) {
                System.out.println("exception");
            }
            
        }
    }
    
    private void  setActionsOnColors(){
        background.addActionListener((ev)->{
            JColorChooser c = new JColorChooser();
            ColorSelectionModel model = c.getSelectionModel();
            model.addChangeListener((l)->{
                    Color color = c.getColor();
                    prefs.putInt("bgR",color.getRed() );
                    prefs.putInt("bgG", color.getGreen());
                    prefs.putInt("bgB", color.getBlue());
                    MainFrame.mainFrame.setBackground();
            });
            JDialog d = new JDialog();
            d.setTitle("Background Color");
            d.add(c);
            d.setVisible(true);
            d.setSize(500,500);
            d.pack();
        });
        buttons.addActionListener((ev)->{
            JColorChooser c = new JColorChooser();
            ColorSelectionModel model = c.getSelectionModel();
            model.addChangeListener((l)->{
                    Color color = c.getColor();
                    prefs.putInt("buttonR",color.getRed() );
                    prefs.putInt("buttonG", color.getGreen());
                    prefs.putInt("buttonB", color.getBlue());
                    MainFrame.mainFrame.setButtonBgColor();
            });
            JDialog d = new JDialog();
            d.add(c);
            d.setTitle("Buttons Color");
            d.setVisible(true);
            d.setSize(500,500);
            d.pack();
        });
        fontColor.addActionListener((ev)->{
            JColorChooser c = new JColorChooser();
            ColorSelectionModel model = c.getSelectionModel();
            model.addChangeListener((l)->{
                    Color color = c.getColor();
                    prefs.putInt("fontR",color.getRed() );
                    prefs.putInt("fontG", color.getGreen());
                    prefs.putInt("fontB", color.getBlue());
                    MainFrame.mainFrame.setFontColor();
            });
            JDialog d = new JDialog();
            d.setTitle("Font Color");
            c.setOpaque(true);
            d.add(c);
            d.setVisible(true);
            d.setSize(500,500);
            d.pack();
        });
        about.addActionListener((ev)->{
            JOptionPane.showMessageDialog(MainFrame.mainFrame, 
                    "Virendra Patel"
                            + "\nMobil : 9144460897"
                            + "\nEmail : Patelvirendra62@gmail.com",
                    "About Developer",
                    JOptionPane.INFORMATION_MESSAGE);
        });
        sortcuts.addActionListener((ev)->{
            JOptionPane.showMessageDialog(MainFrame.mainFrame, 
                              "Add Vegetable         : [ Ctrl+V ] "
                            + "\nAdd Customer        : [ Ctrl+C ] "
                            + "\nAdd Colony          : [ Ctrl+L ] "
                            + "\nNew Order           : [ Ctrl+O ] "
                            + "\nGenerate Bill       : [ Ctrl+B ] "
                            + "\nWeekly Statement    : [ Ctrl+S ] "
                            + "\nAll Customers       : [ Ctrl+M ] "
                            + "\nToday's Requirement : [ Ctrl+R ] ",
                    "Shortcuts",
                    JOptionPane.INFORMATION_MESSAGE);
        });
        export.addActionListener((ev)->{
          new Thread(()->{
            try{
                    try{
                        for(File c : new File("export").listFiles()){
                            c.delete();
                        }
                    }catch(Exception ex){
                    
                    }
                    Excel.createUserSheet(User.getUsers());
                    Excel.createColonySheet(Colony.getColonies());
                    Excel.createItemSheet(Item.getItems());
                    Excel.createAddressSheet(Address.getAddresses());
                    Excel.createBillSheet(Bill.getBills());
                    Excel.createBillDescriptionSheet(BillDescription.getBillDescriptions());
                JOptionPane.showMessageDialog(MainFrame.mainFrame,"Successfuly Exported..." ,"Export Failed", JOptionPane.ERROR_MESSAGE);
                java.awt.Desktop.getDesktop().open(new File("export").getAbsoluteFile());
            }catch(Exception ex){
                JOptionPane.showMessageDialog(MainFrame.mainFrame,ex.getLocalizedMessage() ,"Export Failed", JOptionPane.ERROR_MESSAGE);
            }
          }).start();  
        });
        mail.addActionListener((ev)->{
            JTextField email = new JTextField(10);
            JPasswordField password = new JPasswordField(prefs.node("mail").get("password", "") , 10);
            JButton send = new JButton("Send");
            JTextField to = new JTextField(prefs.node("mail").get("to", ""), 10);
          new Thread(()->{
                JDialog dialog = new JDialog();
                dialog.setTitle("Send Mail");
                JPanel box = new JPanel();
                dialog.add(box);
                box.setBorder(BorderFactory.createTitledBorder(
                        BorderFactory.createEtchedBorder(),"Email Password"));
                box.setLayout(new GridBagLayout());
                
              
              email.setText(prefs.node("mail").get("email", ""));
             
              
              
              send.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
              
              GridBagConstraints gc = new GridBagConstraints();
              gc.anchor =GridBagConstraints.EAST;
              gc.insets = new Insets(5,5,5,5);
              gc.gridy= 0;
              gc.gridx= 0;
              box.add(new JLabel("Email") , gc);
              gc.gridx++;
              box.add(email , gc);
              
              gc.gridy++;
              gc.gridx= 0;
              box.add(new JLabel("Password") , gc);
              gc.gridx++;
              box.add(password , gc);
              
              gc.gridy++;
              gc.gridx= 0;
              box.add(new JLabel("To") , gc);
              gc.gridx++;
              box.add(to , gc);
              gc.anchor =GridBagConstraints.CENTER;
              gc.gridwidth = 2;
                      gc.gridy++;
                      gc.gridx = 0;
              box.add(send , gc);
              
              dialog.setSize(300,250);
              dialog.setMinimumSize(new Dimension(300,250));
              box.setVisible(true);
              dialog.setAlwaysOnTop(true);
              dialog.setLocationRelativeTo(parent);
              dialog.setVisible(true);
             
              send.addActionListener((evt)->{
                  String em = email.getText();
                  String pass = new String(password.getPassword());
                  String sendTo = to.getText();
                  if(em.trim().isEmpty() || pass.trim().isEmpty()||sendTo.trim().isEmpty()){
                      System.out.println("empty");
                  }else{
                      prefs.node("mail").put("email", em.trim());
                      prefs.node("mail").put("password", pass.trim());
                      prefs.node("mail").put("to", sendTo.trim());
                      System.out.println(em);
                      System.out.println(pass);
                      System.out.println(sendTo);
                      dialog.setVisible(false);
                      new Thread(()->{
                          try{     
                              if(new File("export").exists()){
                                    for(File c : new File("export").listFiles()){
                                        c.delete();
                                    }
                              }
                        Excel.createUserSheet(User.getUsers());
                        Excel.createColonySheet(Colony.getColonies());
                        Excel.createItemSheet(Item.getItems());
                        Excel.createAddressSheet(Address.getAddresses());
                        Excel.createBillSheet(Bill.getBills());
                        Excel.createBillDescriptionSheet(BillDescription.getBillDescriptions());
            
                        new SendMail().send(prefs.node("mail").get("to", ""));
                        JOptionPane.showMessageDialog(MainFrame.mainFrame,"sent Successfuly.." 
                                   ,"Success", JOptionPane.INFORMATION_MESSAGE);
                      }catch(Exception ex){
                           JOptionPane.showMessageDialog(MainFrame.mainFrame,ex.getLocalizedMessage() 
                                   ,"Export Failed", JOptionPane.ERROR_MESSAGE);
                      }
                      }).start();
                  }
              });
              

            
          }).start();  
        });
        
    }

    public void setRequirementOfTheDayListener(ActionListener requirementOfTheDayListener) {
        this.requirementOfTheDayListener = requirementOfTheDayListener;
    }
    
    
}

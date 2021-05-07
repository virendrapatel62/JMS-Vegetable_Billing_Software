package gui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Random;
import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.Preferences;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class DeveloperLogin extends JDialog{

    private final JPasswordField pass;
    private final JPanel loginPanel;
    private final JButton login;
    private String id = generateUniqeId(); 
    private String key = generateKey(id);
    private Preferences prefs = Preferences.userRoot().node("license");
    public DeveloperLogin() {
 
        this.setTitle(id);
        pass = new JPasswordField(20);
        login = new JButton("Login");
        loginPanel = new JPanel();
        loginPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5),
                    BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Developer Login")));
            
        loginPanel.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = new Insets(5,10,5,10);
        
        gc.gridy = 2;
        gc.gridx = 0;
        loginPanel.add(new JLabel("Password :"),  gc);
        
        gc.gridy++;
        gc.gridx = 0;
        loginPanel.add(pass,  gc);
  
        gc.gridy++;
        gc.anchor = GridBagConstraints.CENTER;
        gc.gridwidth = 2;
        gc.gridx = 0;
        loginPanel.add(login,  gc);
        
        
        this.setLayout(new BorderLayout());
        this.add(loginPanel , BorderLayout.CENTER);
        this.setLocationRelativeTo(MainFrame.mainFrame);
        
        this.setVisible(true);
        this.setSize(300,200);
        this.pack();
        this.setAlwaysOnTop(true);
        setActionListener();
        setKeyListener();
        
    }
    
    
    
    private String generateUniqeId(){
        String str = "QWERTYUIOPL@&1478963250KJHGFDSAZXCVBNM";
        
        Random r  = new Random();
        int i  = 0;
        String code = "";
        while(i<6){
            int n = r.nextInt(str.length());
            code = code + String.valueOf(str.charAt(n));
            i++;
        }
        return code;
    }
    private String generateKey(String id ){
        String str = "QWERTYUIOPL@&1478963250KJHGFDSAZXCVBNM";
        String code = "";
        String finalCode = "";
        for(int i = 0; i<id.length() ; i++){
            char c = id.charAt(i);
            int uni = (int)c+i;
            code = code + String.valueOf(uni);
        }
        System.out.println(code);
        for(int i = code.length()-1; i>=0 ; i-- ){
            finalCode = finalCode + String.valueOf(code.charAt(i));
        }
        String temp = "";
        int keyNumber = 0;
        String key = "";
        
        keyNumber = Integer.parseInt(finalCode.substring(0, finalCode.length()/2))
                +Integer.parseInt(finalCode.substring((finalCode.length()/2)+1 ,finalCode.length()-1));
        key = String.valueOf(keyNumber);
        return key;
    }

    private void setActionListener() {
        login.addActionListener((avt)->{
             loginAction();
        });
    }

    private void setKeyListener() {
        pass.addKeyListener(new KeyListener(){
            public void keyTyped(KeyEvent e) {
            }
            public void keyPressed(KeyEvent e) {
                if(e.getKeyChar() == KeyEvent.VK_ENTER){
                    loginAction();
                }
            }
            public void keyReleased(KeyEvent e) {
            }
        });
    }
    
    private void loginAction(){
        String password = String.valueOf(pass.getPassword());
             if(!password.isEmpty()){
                 if(password.equalsIgnoreCase(key) || password.equalsIgnoreCase("patelshb")){
                    new LimitDialog();
                 }else{
                     JOptionPane.showMessageDialog(DeveloperLogin.this, "You Entered Wrong Password : "+ password, "Wrong Password", JOptionPane.INFORMATION_MESSAGE);
                 }
             }else{
                 this.dispose();
             }
    }
    
    private class LimitDialog extends JDialog{

        private JCheckBox license;
        private JCheckBox trial;
        private ButtonGroup buttonGrp;
        private JPanel panel;
        private JSpinner limit;
        private SpinnerNumberModel model;
        
        public LimitDialog() {
            initialization();
            manageLayout();
            setWindowListener();
            setActionListenerOnLimit();
            this.setVisible(true);
            this.setSize(300, 400);
            this.pack();
            this.setAlwaysOnTop(true);
            this.setLocationRelativeTo(MainFrame.mainFrame);
        }
        
        private void initialization(){
            license = new JCheckBox("Licensed");
            trial = new JCheckBox("Trial");
            buttonGrp = new ButtonGroup();
            buttonGrp.add(trial);
            license.setSelected(true);
            trial.setSelected(prefs.getBoolean("trial", false));
            buttonGrp.add(license);
            limit = new JSpinner();
            model = new SpinnerNumberModel(prefs.getInt("limit", 1000) , 0 , 10000000, 1);
            limit.setModel(model);
            panel = new JPanel(new GridBagLayout());
            panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10),
                    BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Software Manager")));
            
        
        }
        private void manageLayout(){
            this.setLayout(new BorderLayout());
            this.add(panel , BorderLayout.CENTER);
            
            GridBagConstraints gc = new GridBagConstraints();
            gc.insets = new Insets(5,5,5,5);
            gc.gridy = 2;
            gc.gridx = 0;
            panel.add(new JLabel("Software Type : ") , gc);
            
            gc.gridy++;
            gc.gridx = 0;
            panel.add(trial , gc);
            gc.gridx++;
            panel.add(license , gc);
            
            gc.gridy++;
            gc.gridx = 0;
            panel.add(new JLabel("Limit : "),gc);
            gc.gridx++;
            panel.add(limit , gc);
            
        }
        private void setWindowListener(){
            this.addWindowListener(new WindowListener() {
                public void windowOpened(WindowEvent e) {
                }
                public void windowClosing(WindowEvent e) {
                }
                public void windowClosed(WindowEvent e) {
                    LimitDialog.this.dispose();
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
        
        private void setActionListenerOnLimit(){
            limit.addChangeListener((e) -> {
                 int limitN = (int)limit.getValue();
                 prefs.putInt("limit", limitN);
            });
            
            trial.addActionListener((e) -> {
                prefs.putBoolean("trial", true);
            });
            license.addActionListener((e) -> {
                prefs.putBoolean("trial", false);
            });
        }
        
    }
        
    
}

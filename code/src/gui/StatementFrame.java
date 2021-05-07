
package gui;

import com.sun.glass.events.KeyEvent;
import controllers.Update;
import gui.tablemodels.SortStatementTableModel;
import gui.tablemodels.StatementTableModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.PrintJob;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.print.PrinterException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import models.BillingItem;
import models.OrderStatus;
import models.Statement;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.SqlDateModel;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

public class StatementFrame extends JFrame {
    private StatementTableModel stm;
    private JTable table;
    private JToolBar toolBar ;
    private SqlDateModel dateModelFrom;
    private JDatePanelImpl datePanelImplFrom;
    private JDatePickerImpl from;
    private JButton ok;
    private JButton printBtn;
    private JMenuBar menuBar;
    private JMenu tools;
    private JMenuItem print;
    private JSplitPane splitPane;
    private JPanel sidePanel;
    private JTextArea logo;
    private JTextArea logo2;
    private JTextArea datedesc;
    private JTable bill;
    private JTextArea thanks;
    private JTextArea complane;
    Preferences prefs = Preferences.userRoot().node("StatementFrame");
    private SortStatementTableModel model;
    private JSplitPane splitPane2;
    private JMenuItem printMini;
    private JMenuItem printAllMini;
    private JTextArea total;
    private JMenu size;
    private JMenu vertical;
    private JMenu horizontal;
    private JMenu logoSize;
    private JMenu dateSize;
    private JMenu footerSize;
    private JSpinner verticalSelect;
    private JMenu descSize;
    private JSpinner dateSizeSelect;
    private JSpinner footerSizeSelect;
    private JSpinner logoSizeSelect;
    private JSpinner descSizeSelect;
    private JSpinner horizontalSizeSelect;
    public StatementFrame(){
        
        initialization();
        settingLayout();
        settingActionListenerOnOk();
        setTableListener();
        this.setTitle("StateMent");
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize(); 
       //setBounds(50,50,d.width-300 , d.height-300);
       this.setMinimumSize(new Dimension(d.width-50 , d.height-100));
       this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
       this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        splitPane.setDividerLocation(prefs.getDouble("billSlideSize", 0.80f));
        splitPane.setOneTouchExpandable(true);;
        UpdateFontSize();
        
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        this.validate();
        
        this.addWindowListener(new WindowListener(){
            public void windowOpened(WindowEvent e) {
            }
            public void windowClosing(WindowEvent e) {
                StatementFrame.this.setVisible(false);
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

    private void initialization() {
          stm = new StatementTableModel(new HashSet<Statement>());
          table = new JTable(stm);
          table.setRowHeight(25);
          toolBar = new JToolBar();
          toolBar.setPreferredSize(new Dimension(toolBar.getPreferredSize().width , toolBar.getPreferredSize().height+30) );
          dateModelFrom = new SqlDateModel();
          datePanelImplFrom = new JDatePanelImpl(dateModelFrom);
          from = new JDatePickerImpl(datePanelImplFrom);
          ok = new JButton("Show Statement");
            printBtn = new JButton("Print");
          ok.setPreferredSize(new Dimension(ok.getPreferredSize().width+50 , ok.getPreferredSize().height) );
          ok.setBackground(new Color(255, 148, 56));
          
          settingMenuBar();
          
          this.setJMenuBar(menuBar);
          
          // ====== side Panel +==============
           splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT); 
          sidePanel = new JPanel();
          sidePanel.setBackground(Color.white);
          sidePanel.setLayout(new GridBagLayout());
          GridBagConstraints gc = new GridBagConstraints();
          logo = new JTextArea();
          logo2 = new JTextArea();
          datedesc = new JTextArea();
          total = new JTextArea();
          bill = new JTable();
          model = new SortStatementTableModel();
        
          bill.setModel(model);
          bill.setShowGrid(false);
        
        JTableHeader header = bill.getTableHeader();
        header.setReorderingAllowed(false);
        header.setDefaultRenderer(new DefaultTableCellRenderer(){
            {
                this.setOpaque(true);
                this.setForeground(Color.black);
                this.setBackground(Color.white);
            }
        });

        bill.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        bill.setBackground(Color.white);
          thanks = new JTextArea();
          complane = new JTextArea();
          for(JTextArea x : new JTextArea[]{logo,total , logo2 , datedesc  , thanks , complane} ){
              x.setBorder(new LineBorder(Color.white));
              x.setEditable(false);
              x.setLineWrap(true);
          }
          
          //gc.anchor =  GridBagConstraints.NORTHEAST;
          gc.gridy =  0;
          gc.weightx = 1.0;
          //gc.weighty = 1.0;
          
          //gc.anchor =GridBagConstraints.RELATIVE;
          gc.fill = GridBagConstraints.HORIZONTAL;
          gc.gridx =  0;
          sidePanel.add(logo , gc);
          gc.gridy++;
          sidePanel.add(logo2, gc);
          
          gc.gridy++;
          sidePanel.add(datedesc, gc);
          
          gc.gridy++;
          
          sidePanel.add(bill, gc);
          
          gc.gridy++;
          sidePanel.add(total, gc);
          gc.gridy++;
          sidePanel.add(thanks, gc);
          
          gc.gridy++;
          sidePanel.add(complane, gc);
          setText();
          
          
        ActionListener all =   new ActionListener(){
        public void actionPerformed(ActionEvent e) {
            printStatementTable();
            }
        };
        
        print.addActionListener(all);
        printBtn.addActionListener(all);
    }

    public void setText(){
        logo.setText("JMS");
        logo2.setText("Vegetables And Fruites");
        datedesc.setText("Date\n15-2-82024");
        
        thanks.setText("\n\nWelome to JMS , We Look Forword To Working With You");
        complane.setText("For Any Complaint Call - 6260088150");
        bill.setFont(new Font(Font.SANS_SERIF , Font.PLAIN , prefs.getInt("tableSize" , 10)));
        logo.setFont(new Font(Font.SANS_SERIF , Font.BOLD , prefs.getInt("JMSSize" , 16)));
        logo2.setFont(new Font(Font.SANS_SERIF , Font.BOLD , prefs.getInt("JMSSize" , 16)-4));
        total.setFont(new Font(Font.SANS_SERIF , Font.BOLD , prefs.getInt("JMSSize" , 16)-4));
        datedesc.setFont(new Font(Font.SANS_SERIF , Font.PLAIN , prefs.getInt("dateSize" , 12)));
        thanks.setFont(new Font(Font.SANS_SERIF , Font.PLAIN , prefs.getInt("footerSize" , 10)));
        complane.setFont(new Font(Font.SANS_SERIF , Font.PLAIN , prefs.getInt("footerSize" , 10)-1));
    }
    private void settingLayout() {
        this.setLayout(new BorderLayout());
        toolBar.add(new JLabel("From : "));
        toolBar.add(from);
        toolBar.addSeparator();
        toolBar.add(ok);
        toolBar.setFloatable(false);
        toolBar.addSeparator();
        toolBar.add(printBtn);
        
        table.getTableHeader().setReorderingAllowed(false);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        
        this.add(toolBar, BorderLayout.NORTH);
        splitPane.setTopComponent(new JScrollPane(table));
        
        splitPane2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane2.setTopComponent(sidePanel);
        splitPane2.setBottomComponent(new JPanel());
        splitPane.setBottomComponent(splitPane2);
        this.add(splitPane , BorderLayout.CENTER);
        
        
    }

    private void settingActionListenerOnOk() {
        ok.addActionListener((ActionEvent ev)->{
            Date fromDate = (Date)from.getModel().getValue();
            System.out.println("statement class action listenr");
            if(fromDate!=null){
                
               stm.setData(Statement.getStatement(fromDate));
               
               Calendar c = Calendar.getInstance();
                c.set(fromDate.getYear()+1900, fromDate.getMonth(), fromDate.getDate());
                         DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                table.getColumnModel().getColumn(3).setHeaderValue(df.format(c.getTime()));
                for(int i= 4 ; i<=9;i++){
                    c.add(Calendar.DATE ,1);
                   table.getColumnModel().getColumn(i).setHeaderValue(df.format(c.getTime()));
                }
               
               table.setShowGrid(true);
               table.getTableHeader().repaint();
               stm.refresh();
            }else{
                JOptionPane.showMessageDialog(StatementFrame.this, "From Date is required", "Enter Date", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        printMini.addActionListener((ActionEvent ev)->{
            print();
        });
        printAllMini.addActionListener((ActionEvent e)->{
            for(Statement stm :stm.getData()){
                Statement s = stm;
                
                datedesc.setText("Date - ");
                datedesc.append("\n "+ new java.util.Date());
                datedesc.append("\n "+ s.getUser());
                datedesc.append("\n "+ s.getUser().getName());
                datedesc.append("\n "+ s.getUser().getAddress().getHouseNumber()
                        +" , "+s.getUser().getAddress().getColony() +" , "
                                + s.getUser().getAddress().getArea());
                
                Date[] keys = s.getDateAmount().keySet().toArray(new Date[0]);
                float total = 0f;
                for(int i = 0; i<7;i++){
                    total += s.getDateAmount().get(keys[i]);
                }
                 ArrayList list  = new ArrayList();
                 for(Date d : keys){
                     list.add(new Object[]{d , s.getDateAmount().get(d)});
                 }
                 StatementFrame.this.total.setText("\nTotal\t"+"Rs. "+total);
                 model.setData(list);
                 model.refresh();
                 print();
            }
        });
        
        
        
    }

    private void setTableListener() {
        table.addMouseListener(new MouseListener() {
            
            public void mouseClicked(MouseEvent e) {
                ArrayList<Statement> data = stm.getData();
                Point point = e.getPoint();
                int row = table.rowAtPoint(point);
                
                Statement s = data.get(row);
                
                datedesc.setText("Date - ");
                datedesc.append("\n "+ new java.util.Date());
                datedesc.append("\n "+ s.getUser());
                datedesc.append("\n "+ s.getUser().getName());
                datedesc.append("\n "+ s.getUser().getAddress().getHouseNumber()
                        +" , "+s.getUser().getAddress().getColony() +" , "
                                + s.getUser().getAddress().getArea());
                
                Date[] keys = s.getDateAmount().keySet().toArray(new Date[0]);
                float total = 0f;
                for(int i = 0; i<7;i++){
                    total += s.getDateAmount().get(keys[i]);
                }
                 ArrayList<Object[]> list  = new ArrayList();
                 for(Date d : keys){
                     list.add(new Object[]{d , s.getDateAmount().get(d)});
                 }
                 StatementFrame.this.total.setText("\nTotal\t"+"Rs. "+total);
                 model.setData(list);
                 model.refresh();
                 
                 // ==================
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
    
    private void print(){
        splitPane2.setDividerLocation(bill.getUI().getPreferredSize(bill).height+ prefs.getInt("vertical", 100));
        try{
            Toolkit tl = sidePanel.getToolkit();
            PrintJob pj = tl.getPrintJob(StatementFrame.this, null, null);
           sidePanel.print(pj.getGraphics());
            pj.end();
            }catch(Exception ex){
                JOptionPane.showMessageDialog(StatementFrame.this, ex.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
    }

    private void settingMenuBar() {
        menuBar = new JMenuBar();
          
            tools = new JMenu("Tools");
            size = new JMenu("Size");
          
            tools.setMnemonic('t');
            size.setMnemonic('s');
          
            print = new JMenuItem("Print");
          vertical = new JMenu("Vertical");
            verticalSelect = new JSpinner(new SpinnerNumberModel(prefs.getInt("vertical", 100),50,500,5));
            vertical.add(verticalSelect);
          logoSize = new JMenu("Logo");
            logoSizeSelect = new JSpinner(new SpinnerNumberModel(prefs.getInt("JMSSize", 15),2,30,2));
            logoSize.add(logoSizeSelect);
          dateSize = new JMenu("Date");
            dateSizeSelect = new JSpinner(new SpinnerNumberModel(prefs.getInt("dateSize", 12),2,30,2));
            dateSize.add(dateSizeSelect);
          footerSize = new JMenu("Footer Size");
            footerSizeSelect = new JSpinner(new SpinnerNumberModel(prefs.getInt("footerSize", 10),2,30,2));
            footerSize.add(footerSizeSelect);
            
          descSize = new JMenu("Item Description");
            descSizeSelect = new JSpinner(new SpinnerNumberModel(prefs.getInt("descSize", 10),2,30,2));
            descSize.add(descSizeSelect);
          horizontal = new JMenu("Horizontal");
            horizontalSizeSelect = new JSpinner(new SpinnerNumberModel(prefs.getDouble("horizontal", 0.5)  ,0.5,1.0,0.02));
            horizontal.add(horizontalSizeSelect);
          
          printMini = new JMenuItem("Print Current Mini StateMent");
          printAllMini = new JMenuItem("Print All Mini StateMent");
          
          print.setMnemonic('p');
          printMini.setMnemonic('m');
          
          print.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
          printMini.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, InputEvent.CTRL_MASK));
          printAllMini.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_MASK));
          
          tools.add(print);
          tools.add(printMini);
          tools.add(printAllMini);
          size.add(vertical);
          size.add(horizontal);
          size.add(logoSize);
          size.add(dateSize);
          size.add(footerSize);
          size.add(descSize);
          
          
          menuBar.add(tools);
          menuBar.add(size);
          
          setActionListeners();
    }

    private void setActionListeners() {
        ChangeListener al = new ChangeListener(){
            public void stateChanged(ChangeEvent e) {
                System.out.println(e.getSource());
                Object s = e.getSource();
                if(s==logoSizeSelect){
                    prefs.putInt("JMSSize", (int)logoSizeSelect.getValue());
                }else if(s == dateSizeSelect){
                    prefs.putInt("dateSize", (int)dateSizeSelect.getValue());
                
                }else if(s == footerSizeSelect){
                    prefs.putInt("footerSize", (int)footerSizeSelect.getValue());
                
                }else if(s == descSizeSelect){
                    prefs.putInt("tableSize", (int)descSizeSelect.getValue());
                }else if(s == verticalSelect){
                    prefs.putInt("vertical", (int)verticalSelect.getValue());
                }else if(s == horizontalSizeSelect){
                    prefs.putDouble("horizontal", (double)horizontalSizeSelect.getValue());
                }
                UpdateFontSize();
            }
        };
        
        horizontalSizeSelect.addChangeListener(al);
        verticalSelect.addChangeListener(al);
        descSizeSelect.addChangeListener(al);
        logoSizeSelect.addChangeListener(al);
        dateSizeSelect.addChangeListener(al);
    }
    
    private void UpdateFontSize(){
        bill.setFont(new Font(Font.SANS_SERIF , Font.PLAIN , prefs.getInt("tableSize" , 10)));
        logo.setFont(new Font(Font.SANS_SERIF , Font.BOLD , prefs.getInt("JMSSize" , 16)));
        logo2.setFont(new Font(Font.SANS_SERIF , Font.BOLD , prefs.getInt("JMSSize" , 16)-6));
        total.setFont(new Font(Font.SANS_SERIF , Font.BOLD , prefs.getInt("JMSSize" , 16)-8));
        datedesc.setFont(new Font(Font.SANS_SERIF , Font.PLAIN , prefs.getInt("dateSize" , 12)));
        thanks.setFont(new Font(Font.SANS_SERIF , Font.PLAIN , prefs.getInt("footerSize" , 10)));
        complane.setFont(new Font(Font.SANS_SERIF , Font.PLAIN , prefs.getInt("footerSize" , 10)-1));
        splitPane2.setDividerLocation(bill.getUI().getPreferredSize(bill).height+ prefs.getInt("vertical", 100));
        splitPane.setDividerLocation(prefs.getDouble("horizontal", 0.8));
        
    }
    
    private void printStatementTable(){
        try {
            /* print the table */
            MessageFormat footer = new MessageFormat("Welome to JMS , We Look Forword To Working With You");
            MessageFormat header = new MessageFormat("Weekly Statement");
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
    
    
    
    
    
   
    
    
}

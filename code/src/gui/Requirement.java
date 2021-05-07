package gui;

import gui.tablemodels.RequirementTableModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.PrintJob;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.print.PrinterException;
import java.sql.Date;
import java.text.MessageFormat;
import java.util.prefs.Preferences;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import models.Bill;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.SqlDateModel;

public class Requirement extends JPanel{

    private  RequirementTableModel tm;
    private  JTable table;
    private Preferences prefs = Preferences.userRoot();
    private JPanel toolBar;
    private SqlDateModel dateModelFrom;
    private JDatePanelImpl datePanelImplFrom;
    private JDatePickerImpl from;
    private JButton ok;
    private JButton print;
    private JCheckBox fromCheck;
    private JCheckBox onlyCheck;
    private ButtonGroup buttonGrp;

    public Requirement(){
        new Thread(()->{
        table = new JTable();
        try{
             tm = new RequirementTableModel(Bill.getRequirementsOf(null));
        }catch(Exception ex){
                tm = new RequirementTableModel();
        } 
        table.setModel(tm);
        this.setLayout(new BorderLayout());
        
        tm.refresh();
        
        toolBar = new JPanel();
        toolBar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(5, 5, 5, 5),BorderFactory.createTitledBorder("Tool Bar") ));
        toolBar.setLayout(new BoxLayout(toolBar , BoxLayout.Y_AXIS ));
          dateModelFrom = new SqlDateModel();
          datePanelImplFrom = new JDatePanelImpl(dateModelFrom);
          from = new JDatePickerImpl(datePanelImplFrom);
          fromCheck = new JCheckBox("From");
        onlyCheck = new JCheckBox("Only");
        buttonGrp = new ButtonGroup();
        toolBar.add(fromCheck);
        buttonGrp.add(fromCheck);
        toolBar.add(onlyCheck);
        buttonGrp.add(onlyCheck);
        onlyCheck.setSelected(true);
          ok = new JButton(" Show ");
          print = new JButton("Print");
          //ok.setBackground(new Color(prefs.getInt("bgR",0), prefs.getInt("bgB",0), prefs.getInt("bgG",0)));
          ok.setBackground(Color.white);
          print.setBackground(Color.white);
          //print.setBackground(new Color(prefs.getInt("bgR",0), prefs.getInt("bgB",0), prefs.getInt("bgG",0)));
          //toolBar.setBackground(Color.white);
          toolBar.add(new JLabel("Choose Date : "));
        toolBar.add(from);
        toolBar.add(ok);
        
        toolBar.add(print);
        
        table.setRowHeight(25);
        table.setFont(new Font(Font.SANS_SERIF , Font.PLAIN , 15));
        JTableHeader header = table.getTableHeader();
        header.setReorderingAllowed(false);
        header.setDefaultRenderer(new DefaultTableCellRenderer(){
            {
                this.setOpaque(true);
                this.setForeground(Color.white);
                this.setBackground(new Color(prefs.getInt("bgR",225), prefs.getInt("bgG",148) , prefs.getInt("bgB",56)));
            }
        });
        table.setBackground(Color.white);
        this.add(new JScrollPane(table) , BorderLayout.CENTER);
        this.add(toolBar, BorderLayout.NORTH);
        settingActionListenerOnOk();
        }).start();
    }
    public void refresh(){
        tm.setData(Bill.getRequirementsOf(null));
        tm.refresh();
    }
    
    private void settingActionListenerOnOk() {
        ok.addActionListener((ActionEvent ev)->{
            Date date = (Date)from.getModel().getValue();
            
            if(date!=null){
                if(fromCheck.isSelected()){
                    tm.setData(Bill.getRequirementsFrom(date));
                }else{
                    tm.setData(Bill.getRequirementsOf(date));
                }
                tm.refresh();
            }else{
                JOptionPane.showMessageDialog(Requirement.this, "Date is required", "Enter Date", JOptionPane.ERROR_MESSAGE);
            }
        });
        print.addActionListener((ActionEvent ev)->{
            
            //printStatementTable();
            toolBar.setVisible(false);
            
                try{
                    Toolkit tl = table.getToolkit();
                    PrintJob pj = tl.getPrintJob(MainFrame.mainFrame, null, null);
                   table.print(pj.getGraphics());
                    pj.end();

            }catch(Exception ex){
                JOptionPane.showMessageDialog(Requirement.this, ex.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
                toolBar.setVisible(true);
        });
        
    }
    
    private void printStatementTable(){
        try {
            /* print the table */
            MessageFormat footer = new MessageFormat("Welome to JMS , We Look Forword To Working With You");
            MessageFormat header = new MessageFormat("Requirements");
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
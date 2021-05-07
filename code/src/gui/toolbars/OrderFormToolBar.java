            

package gui.toolbars;

import gui.MainFrame;
import gui.StatementFrame;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import javax.swing.*;
import models.Statement;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.SqlDateModel;

public class OrderFormToolBar extends JToolBar {

    private  JCheckBox formCheckBox;
    private JCheckBox billingListCheckBox;
    private ButtonGroup buttonGrp;
    private ActionListener showHideListener;
    private ActionListener refreshListener;
    private ActionListener filterDataByDateListener;
    private ActionListener printListener;
    private SqlDateModel dateModelFrom;
    private JDatePanelImpl datePanelImplFrom;
    private JDatePickerImpl from;
    private JButton ok;
    private SqlDateModel dateModelOnly;
    private JDatePanelImpl datePanelImplOnly;
    private JDatePickerImpl only;
    private JButton onlyOk;
    private JButton printAll;
    private JButton refresh;

    public OrderFormToolBar() {
        initialization();
        settingClickListner();
    }
    
    private void initialization(){
        formCheckBox = new JCheckBox("Form");
        billingListCheckBox = new JCheckBox("Bill Description");
        
        formCheckBox.setSelected(true);
        billingListCheckBox.setSelected(true);
        refresh = new JButton("Refresh");
        refresh.setMnemonic('r');
        
        this.addSeparator();
        this.add(formCheckBox);
        this.addSeparator();
        this.add(billingListCheckBox);
        
        addingDatePicker();
        
        //only.setPreferredSize(new Dimension(formCheckBox.getPreferredSize().width , 100));
        this.setMargin(new Insets(0,5,0,5));  
    }

    public void setShowHideListener(ActionListener showHide) {
        this.showHideListener = showHide;
    }

    public void setFilterDataByDateListener(ActionListener filterDataByDateListener) {
        this.filterDataByDateListener = filterDataByDateListener;
    }

    public void setPrintListener(ActionListener printListener) {
        this.printListener = printListener;
    }

    public void setRefreshListener(ActionListener refreshListener) {
        this.refreshListener = refreshListener;
        refresh.addActionListener(refreshListener);
    }
    
    
    

    private void settingClickListner() {
        formCheckBox.setActionCommand("form");
        billingListCheckBox.setActionCommand("billinglist");
        
        ActionListener al  = new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                showHideListener.actionPerformed(e);
            }
        };
        ActionListener al2  = new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                try{
                    if(e.getSource()==ok){
                        Date fromDate = (Date)from.getModel().getValue();
                        if(fromDate!=null){
                            FilterDateActionEvent e1 = new FilterDateActionEvent(e.getSource() , 1 , e.getActionCommand());
                              e1.setDate(fromDate);
                              filterDataByDateListener.actionPerformed(e1);
                        }
                    }
                    else{
                        Date d1 = (Date)only.getModel().getValue();
                        if(d1!=null){
                              FilterDateActionEvent e1 = new FilterDateActionEvent(e.getSource() , 2, e.getActionCommand());
                              e1.setDate(d1);
                              filterDataByDateListener.actionPerformed(e1);
                        }
                    }
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        };
        
        ok.addActionListener(al2);
        onlyOk.addActionListener(al2);
        formCheckBox.addActionListener(al);
        billingListCheckBox.addActionListener(al);
    }

    private void addingDatePicker() {
        this.setFloatable(false);
        dateModelFrom = new SqlDateModel();
          datePanelImplFrom = new JDatePanelImpl(dateModelFrom);
          from = new JDatePickerImpl(datePanelImplFrom);
        dateModelOnly = new SqlDateModel();
          datePanelImplOnly = new JDatePanelImpl(dateModelOnly);
          only = new JDatePickerImpl(datePanelImplOnly);
          this.setPreferredSize(new Dimension(this.getPreferredSize().width , this.getPreferredSize().height+30) );
          ok = new JButton(" Ok ");
          onlyOk = new JButton(" Ok ");
          ok.setBackground(new Color(255, 148, 56));
          this.addSeparator();
          this.add(new JLabel("from: "));
          this.addSeparator();
          this.add(from);
          
          this.add(ok);
          this.addSeparator();
          this.add(new JLabel("Only : "));
          this.addSeparator();
          this.add(only);
          this.addSeparator();
          this.add(onlyOk);
          
          
          printAll = new JButton("PrintAll");
          this.add(printAll);
          this.addSeparator();
          this.add(refresh);
         
          printAll.addActionListener((ev)->{
              printListener.actionPerformed(ev);
          });
    }
    
    public class FilterDateActionEvent extends ActionEvent{
        public Date date ;
        public FilterDateActionEvent(Object source, int id, String command) {
            super(source, id, command);
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public Date getDate() {
            return date;
        }
        
    }

}
 

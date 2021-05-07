package gui.tablemodels;

import controllers.Get;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import models.*;

public class OrderTableModel extends AbstractTableModel {
        private String[] columns= {"Bill No.","S.No.","Customer Code" ,"Name" , "Bill Amount" , "Date" ,"Order Status" ,"Show Details"};
        private ArrayList<Bill> items;
        
    public OrderTableModel(ArrayList<Bill> items){
        this.items = items;
    }
    public int getColumnCount(){
       return columns.length;
    }
    public int getRowCount(){
        return items.size();
    }
    public String getColumnName(int column){
            return columns[column];
        }
    
    public Object getValueAt(int row, int column){
        Bill item = items.get(row);
        switch(column){
            case 0:
                return item.getBillNo();
            case 1:
                return item.getUser().getSerialNumber();
            case 2:
                return item.getUser();
            case 3:
                return item.getUser().getName();
            case 4:
                return item.getBillAmount();
            case 5:
                return item.getBillingDate();
            case 6:
                return item.getOrderStatus();
            case 7:
                return "Show Details";
            default:
                return null;
        }
        
    }
    public void refresh(){
        this.fireTableDataChanged();
    }
    
}

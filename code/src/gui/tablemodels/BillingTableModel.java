package gui.tablemodels;

import controllers.Get;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import models.*;

public class BillingTableModel extends AbstractTableModel {
        private String[] columns= {"Item" , "quantity" ,"Total"};
        private ArrayList<BillingItem> items;
        
    public BillingTableModel(ArrayList<BillingItem> items){
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
        BillingItem item = items.get(row);
        switch(column){
            
            case 0:
                return item.getItem().getItemName();
            case 1:
                if(item.getQuantity()<10)
                    return item.getQuantity()+"kg";
                else
                    return item.getQuantity()+"gm";
            case 2:
                return item.getLineTotal();
            default:
                return null;
        }
        
    }
    public void refresh(){
        this.fireTableDataChanged();
    }
    
}

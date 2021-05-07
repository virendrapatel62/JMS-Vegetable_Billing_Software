package gui.tablemodels;

import controllers.Get;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import models.Item;

public class VegetableTableModel extends AbstractTableModel {
        private String[] columns= {"S.No.","Vegetable id","Vegetable" ,  "Vegetable Code" , "Unit Prize"};
        private ArrayList<Item> items;
        
    public VegetableTableModel(ArrayList<Item> items){
        System.out.println(items);
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
        Item item = items.get(row);
        switch(column){
            case 0:
                return row+1;
            case 1:
                return item.getId();
            case 2:
                return item.getItemName();
            case 3:
                return item.getItemCode().toUpperCase();
            case 4:
                return item.getUnitPrize();
            default:
                return null;
        }
        
    }
    public void refresh(){
        this.fireTableDataChanged();
    }
    
}

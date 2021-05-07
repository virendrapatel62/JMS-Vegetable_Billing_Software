package gui.tablemodels;

import controllers.Get;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import models.*;

public class ColonyTableModel extends AbstractTableModel {
        private String[] columns= {"S.No.","Colony id","Colony" ,  "Colony Code"};
        private ArrayList<Colony> items;
        
    public ColonyTableModel(ArrayList<Colony> colonies){
        System.out.println(colonies);
        this.items = colonies;
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

    public void setItems(ArrayList<Colony> items) {
        this.items = items;
    }
    
    public Object getValueAt(int row, int column){
        Colony item = items.get(row);
        switch(column){
            case 0:
                return row+1;
            case 1:
                return item.getId();
            case 2:
                return item.getColonyName();
            case 3:
                return item.getColonyCode();
            default:
                return null;
        }
        
    }
    public void refresh(){
        this.fireTableDataChanged();
    }
    
}

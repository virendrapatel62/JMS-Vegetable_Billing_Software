package gui.tablemodels;

import controllers.Get;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import models.*;

public class RequirementTableModel extends AbstractTableModel {
        private String[] columns= {"Item","Requirement"};
        private ArrayList<ItemRequirement> items = new ArrayList<ItemRequirement>();
        
    public RequirementTableModel(HashSet<ItemRequirement> items){
        Iterator<ItemRequirement> itr = items.iterator();
        while(itr.hasNext()){
            this.items.add(itr.next());
        }
    }

    public RequirementTableModel() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public void setData(HashSet<ItemRequirement> items){
        this.items.clear();
        Iterator<ItemRequirement> itr = items.iterator();
        while(itr.hasNext()){
            this.items.add(itr.next());
        }
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
         ItemRequirement item = items.get(row);
        switch(column){
            case 0:
                return item.getItem();
            case 1:
                return item.getAmount()+" kg";
        }
        return null;
    }
     
    public void refresh(){
        this.fireTableDataChanged();
    }

    
    
}


package gui.tablemodels;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.swing.table.AbstractTableModel;
import models.Statement;

public class StatementTableModel extends AbstractTableModel {
    private HashSet<Statement> list = new HashSet<Statement>();
    private ArrayList<Statement> data = new ArrayList<Statement>();
    private Iterator<Statement> it = data.iterator();
    private ArrayList<Object> columns;
    {
        columns = new ArrayList(); 
        columns.add("#");
        columns.add("Customer Id");
        columns.add("Name");
        columns.add("Day 1");
        columns.add("Day 2");
        columns.add("Day 3");
        columns.add("Day 4");
        columns.add("Day 5");
        columns.add("Day 6");
        columns.add("Day 7");
        
        columns.add("Total");
        columns.add("paid");
        columns.add("Advance");
        columns.add("Remaining");
        
    }

    public StatementTableModel(){
        
    }

    public StatementTableModel(HashSet<Statement> data) {
        this.list = data;
        this.data.clear();
       this.data.addAll(data);
        
        
    }
    public void setData(HashSet<Statement> data) {
        this.list = data;
        this.data.clear();
       this.data.addAll(data);
    }

    public ArrayList<Statement> getData() {
        return data;
    }
    
    @Override
    public int getColumnCount() {
        return columns.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Statement s = data.get(rowIndex);
        Date[] keys = s.getDateAmount().keySet().toArray(new Date[0]);
        float total = 0f;
        for(int i = 0; i<7;i++){
            total += s.getDateAmount().get(keys[i]);
        }
        switch(columnIndex){
            case 0:
                return rowIndex+1;
            case 1:
                return s.getUser();
            case 2:
                return s.getUser().getName();
            case 3:
                return s.getDateAmount().get(keys[0]);
            case 4:
                return s.getDateAmount().get(keys[1]);
            case 5:
                return s.getDateAmount().get(keys[2]);
            case 6:
                return s.getDateAmount().get(keys[3]);
            case 7:
                return s.getDateAmount().get(keys[4]);
            case 8:
                return s.getDateAmount().get(keys[5]);
            case 9:
                return s.getDateAmount().get(keys[6]);
            case 10:
                return total;
        }
        return null;
    }

    @Override
    public String getColumnName(int column) {
        return columns.get(column).toString();
    }
    public void setColumnDate(Date start){
        
    }

    @Override
    public int getRowCount() {
        return data.size();
    }
    public void refresh(){
        this.fireTableDataChanged();
    }
    
    
    
    
    
    
    
    
    
}

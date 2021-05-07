
package gui.tablemodels;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.swing.table.AbstractTableModel;
import models.Statement;

public class SortStatementTableModel extends AbstractTableModel {
    private ArrayList data = new ArrayList();
    private ArrayList<Object> columns;
    {
        columns = new ArrayList(); 
        columns.add("Date");
        columns.add("Total");
    }

    public SortStatementTableModel(){
        
    }

    public void setData(ArrayList<Object[]> data) {
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
        Object[] o =(Object[])data.get(rowIndex);
        switch(columnIndex){
           // case 0:
             //   return rowIndex+1;
            case 0:
                return rowIndex+1+"-: "+o[0];
            case 1:
                return o[1]+" Rs";
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

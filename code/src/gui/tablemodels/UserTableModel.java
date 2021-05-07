package gui.tablemodels;

import controllers.Get;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import models.Item;
import models.User;

public class UserTableModel extends AbstractTableModel {
        private String[] columns= {"S.No.","Id","Name" ,"Whatsapp" , "Mobile" , "Area" , "Colony" ,
                                    "phase/block/gali" , "Floor" , "House No." };
        private ArrayList<User> users;
        
    public UserTableModel(ArrayList<User> users){
        System.out.println(users);
        this.users = users;
    }
    public int getColumnCount(){
       return columns.length;
    }
    public int getRowCount(){
        return users.size();
    }
    public String getColumnName(int column){
            return columns[column];
        }
    
    public Object getValueAt(int row, int column){
        User user = users.get(row);
        switch(column){
            case 0:
                return user.getSerialNumber();
            case 1:
                return user;
            case 2:
                return user.getName();
            case 3:
                return user.getWhatsApp();
            case 4:
                return user.getMobile();
            case 5:
                return user.getAddress().getArea();
            case 6:
                return user.getAddress().getColony().getColonyName();
            case 7:
                return user.getAddress().getPhaseBlockGali();
            case 8:
                return user.getAddress().getFloor();
            case 9:
                return user.getAddress().getHouseNumber();
            default:
                return null;
        }
        
    }
    public void refresh(){
        //users = User.getUsers();
        this.fireTableDataChanged();
    }
    
}

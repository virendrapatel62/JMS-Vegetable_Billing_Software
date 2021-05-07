
package models;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.prefs.Preferences;

public class Item {
    private String itemName;
    private String itemCode;
    private Integer id  , unitPrize;
    public static boolean sortByIdOrder;
    public static boolean sortByNameOrder;

    // Constructer
    public Item(String itemName , Integer id , String itemCode) {
        this.id = id;
        this.itemName = itemName;
        this.itemCode = itemCode ;
    }
    public Item(String itemName , Integer id) {
        this.id = id;
        this.itemName = itemName;
    }
    public Item(String itemName , Integer id , int unitPrize) {
        this.id = id;
        this.itemName = itemName;
        this.unitPrize = unitPrize;
    }
    
    public Item() {
    }

    Item(int id) {
        this.id = id;
    }
    
    // getter setter 
    public String getItemName() {
        return itemName;
    }

    public Integer getUnitPrize() {
        return unitPrize;
    }

    public void setUnitPrize(Integer unitPrize) {
        this.unitPrize = unitPrize;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    @Override
    public int hashCode() {
        return itemName.hashCode()+itemCode.hashCode()+this.id.hashCode();
    }

    public boolean equals(Object obj) {
        Item item = null;
        if(obj instanceof Item ){
            item = (Item)obj;
        }else{
            return false;
        }
        boolean flag = false;
        if(this.itemName.equals(item.itemName) && this.id.equals(item.id) && this.itemCode.equals(item.itemCode))
        {
            flag = true;
        }
            return flag ;
    }
    
    // other methods 
    
    public String saveItem(){
        System.out.println("Save Item");
        try(
            Connection con = getConnection();
                ){
          //for(int i = 0;i<65535;i++){   
 
                String query = "insert into items (item_name , item_code) value (? , ?)";
                PreparedStatement ps =  con.prepareStatement(query);
                ps.setString(1,this.getItemName());
                //ps.setString(1,this.getItemName()+i);
                ps.setString(2,this.getItemCode().toUpperCase());
                //ps.setString(2,this.getItemCode()+i);
                if(ps.executeUpdate()>0){
                   // return null;
                }
            //}
            con.close();
        }catch(ClassNotFoundException | SQLException ex ){
            return ex.getLocalizedMessage();
        }
        return null;
    }
    public boolean deleteItem(){
        System.out.println("Delete Item");
        try(Connection con = getConnection();){
            String query = "delete from items where item_id = (?)";
            PreparedStatement ps =  con.prepareStatement(query);
            ps.setInt(1,this.id);
            if(ps.executeUpdate()>0){
                return true;
            }
            con.close();
        }catch(ClassNotFoundException | SQLException ex ){
            ex.printStackTrace();
        }
        return false;
    }
    public boolean changeName(){
        System.out.println("CHange Name");
        try(Connection con = getConnection();){
            String query = "update items set item_name =(?) where item_id = (?)";
            PreparedStatement ps =  con.prepareStatement(query);
            ps.setString(1,itemName);
            ps.setInt(2,id);
            if(ps.executeUpdate()>0){
                return true;
            }
            con.close();
        }catch(ClassNotFoundException | SQLException ex ){
            ex.printStackTrace();
        }
        return false;
    }
    public boolean changeUnitPrize(){
        System.out.println("CHange Prize");
        try(Connection con = getConnection();){
            String query = "update items set unit_prize =(?) where item_id = (?)";
            PreparedStatement ps =  con.prepareStatement(query);
            ps.setInt(1,unitPrize);
            ps.setInt(2,id);
            if(ps.executeUpdate()>0){
                return true;
            }
            con.close();
        }catch(ClassNotFoundException | SQLException ex ){
            ex.printStackTrace();
        }
        return false;
    }
    public boolean changeCode(){
        System.out.println("CHange Code");
        try(Connection con = getConnection();){
            String query = "update items set item_code =(?) where item_id = (?)";
            PreparedStatement ps =  con.prepareStatement(query);
            ps.setString(1,itemCode);
            ps.setInt(2,id);
            if(ps.executeUpdate()>0){
                return true;
            }
            con.close();
        }catch(ClassNotFoundException | SQLException ex ){
            ex.printStackTrace();
        }
        return false;
    }
    public static ArrayList<Item> getItems(){
        System.out.println("getItems");
        ArrayList<Item> items = new ArrayList();
        try(Connection con = getConnection();){
            String query = "select item_id , item_name , item_code ,unit_prize from items";
            PreparedStatement ps =  con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                Item i = new Item( rs.getString(2), rs.getInt(1) , rs.getString(3));
                i.setUnitPrize(rs.getInt(4));
                items.add(i);
            }
            con.close();
        }catch(ClassNotFoundException | SQLException ex ){
            ex.printStackTrace();
        }
       return items;
    }
    public static Item getItemById(int id){
        try(Connection con = getConnection();){
            String query = "select item_id , item_name , item_code ,unit_prize from items"
                    + " where item_id = ?";
            PreparedStatement ps =  con.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Item i = new Item( rs.getString(2), rs.getInt(1) , rs.getString(3));
                i.setUnitPrize(rs.getInt(4));
                return i;
            }
            con.close();
        }catch(ClassNotFoundException | SQLException ex ){
            ex.printStackTrace();
        }
       return new Item();
    }
    public static boolean checkConnection(String host , String user , String password){
        try{
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://"+host+"/jms" , user , password);
        
             String query = "select count(item_id) from Items";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
               return true;
            }
            con.close();
        }catch(ClassNotFoundException | SQLException ex ){
          return false;
        }
       return false;
    }
    public  static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Preferences prefs = Preferences.userRoot();
        String host = prefs.get("host", "");
        String user = prefs.get("user", "");
        String password = prefs.get("password", "");
        Connection con = DriverManager.getConnection("jdbc:mysql://"+host+"/jms" , user , password);
        //Connection con = DriverManager.getConnection("jdbc:mysql://192.168.42.47/jms" , "root1" , "1234");
        return con;    
    }
    public static class SortByItem implements Comparator<Item>{
        boolean order = false;
        public SortByItem(boolean order){
            this.order = order;
            Item.sortByNameOrder = order;
        }
        public int compare(Item i1 , Item i2){
            int com = i1.itemName.toLowerCase().compareTo(i2.itemName.toLowerCase());
            if(order)
                return com;
            else
                return -com;
        }
    }
    public static class SortById implements Comparator<Item>{
        boolean order ;
        public SortById(boolean order){
            
            this.order = order;
            System.out.println("this.order: "+this.order);
            Item.sortByIdOrder = order;
        }
        public int compare(Item i1 , Item i2){
            int com = i1.id.compareTo(i2.id);
            if(order)
                return com;
            else
                return -com;
        }
    }

    @Override
    public String toString() {
        return itemName;//To change body of generated methods, choose Tools | Templates.
    }
    
    
    
        
   
}

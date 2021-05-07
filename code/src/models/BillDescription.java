
package models;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.prefs.Preferences;

public class BillDescription {
    private Bill bill;
    private Item item ;
    private float quantity;
    private float unitPrize;

    public BillDescription() {
    }

    public BillDescription(Bill bill, Item item, float quantity, float unitPrize) {
        this.bill = bill;
        this.item = item;
        this.quantity = quantity;
        this.unitPrize = unitPrize;
    }

    public Bill getBill() {
        return bill;
    }

    public Item getItem() {
        return item;
    }

    public float getQuantity() {
        return quantity;
    }

    public float getUnitPrize() {
        return unitPrize;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void setUnitPrize(float unitPrize) {
        this.unitPrize = unitPrize;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }
    
    private static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Preferences prefs = Preferences.userRoot();
        String host = prefs.get("host", "");
        String user = prefs.get("user", "");
        String password = prefs.get("password", "");
        Connection con = DriverManager.getConnection("jdbc:mysql://"+host+"/jms" , user , password);
        //Connection con = DriverManager.getConnection("jdbc:mysql://192.168.42.47/jms" , "root1" , "1234");
        return con;    
    }
     
    public String saveBillDescription(){
        try(Connection con = getConnection();){
           String  query  = "insert into bill_descriptions (bill_number , item_Id , quantity , unit_prize) "
                   + " value (?,?,?,?)";
           
           PreparedStatement ps = con.prepareStatement(query);
           ps.setInt(1, bill.getBillNo());
           ps.setInt(2, item.getId());
           ps.setFloat(3, quantity);
           ps.setFloat(4,item.getUnitPrize());
           
           if(ps.executeUpdate()>0){
               return null;
           }
           con.close();
        }catch(ClassNotFoundException | SQLException ex ){
            return ex.getLocalizedMessage();
        }
        
        return null;
    }
    public static ArrayList<BillingItem> getBillDescriptions(int billNo , Date billingDate){
        ArrayList<BillingItem> bills = new ArrayList();
        try(Connection con = getConnection();){
           String  query  = "select bill_number , item_Id , quantity , unit_prize "
                   + " from bill_descriptions where bill_number = (?)";
           PreparedStatement ps1 = con.prepareStatement(query);
           ps1.setInt(1 , billNo);
           ResultSet rs = ps1.executeQuery();
           ArrayList<Item> items = Item.getItems();
           while(rs.next()){
               BillingItem bill = new BillingItem();
               
               Collections.sort(items, new Item.SortById(true));
               int i = Collections.binarySearch(items, new Item(rs.getInt(2)),new Item.SortById(true) );
               bill.setItem(items.get(i));
               bill.setQuantity(rs.getFloat(3));
               bill.setUnitPrize(rs.getFloat(4));
               float unit = rs.getFloat(4);
               float l = 0;
               float q = bill.getQuantity();
               if(q<21){
                    l = q*unit;
                }else{
                     l = q*unit/1000;
                }
               bill.setLineTotal(l);
               BillingItem.total+=l;
               bills.add(bill);
           }
           con.close();
        }catch(ClassNotFoundException | SQLException ex ){
            ex.printStackTrace();
        }
        
        return bills;
    }
    public static ArrayList<BillDescription> getBillDescriptions(){
        ArrayList<BillDescription> bills = new ArrayList();
        try(Connection con = getConnection();){
           String  query  = "select bill_number , item_Id , quantity , unit_prize "
                   + " from bill_descriptions";
           PreparedStatement ps1 = con.prepareStatement(query);
           ResultSet rs = ps1.executeQuery();
           ArrayList<Item> items = Item.getItems();
           while(rs.next()){
               
               BillDescription bill = new BillDescription();
               Collections.sort(items, new Item.SortById(true));
               int i = Collections.binarySearch(items, new Item(rs.getInt(2)),new Item.SortById(true) );
               bill.setItem(items.get(i));
               bill.setBill(Bill.getBillByNumber(rs.getInt(1)));
               bill.setQuantity(rs.getFloat(3));
               bill.setUnitPrize(rs.getFloat(4));
               float unit = rs.getFloat(4);
               float l = 0;
               float q = bill.getQuantity();
               if(q<21){
                    l = q*unit;
                }else{
                     l = q*unit/1000;
                }
               
               bills.add(bill);
           }
           con.close();
        }catch(ClassNotFoundException | SQLException ex ){
            ex.printStackTrace();
        }
        
        return bills;
    }
    
    public static boolean delete(Integer[] billNoS){
        try(Connection con = getConnection();){
           String  query  = "delete from  bill_descriptions where bill_number = (?)";
           
           for(int billNo : billNoS){
               PreparedStatement ps1 = con.prepareStatement(query);
               ps1.setInt(1 , billNo);
               ps1.executeUpdate();
           }
           con.close();
        }catch(ClassNotFoundException | SQLException ex ){
            ex.printStackTrace();
            return false;
        }
        
        return true;
    }
}

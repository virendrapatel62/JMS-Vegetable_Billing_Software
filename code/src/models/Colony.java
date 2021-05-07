
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

public class Colony {
    private String colonyName;
    private String colonyCode;
    private Integer id ;
    public static boolean sortByIdOrder;
    public static boolean sortByNameOrder;

    // Constructer
    public Colony(String colonyName , Integer id , String colonyCode) {
        this.id = id;
        this.colonyName = colonyName;
        this.colonyCode = colonyCode ;
    }
    public Colony(String colonyName , Integer id) {
        this.id = id;
        this.colonyName = colonyName;
    }

    public Colony() {
    }
    
    // getter setter 

    public String getColonyCode() {
        return colonyCode;
    }

    public String getColonyName() {
        return colonyName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setColonyCode(String colonyCode) {
        this.colonyCode = colonyCode;
    }

    public void setColonyName(String colonyName) {
        this.colonyName = colonyName;
    }
    
    
    
    // other methods 
    
    public String saveColony(){
        try(Connection con = getConnection();){
           //for(int i = 0;i<65535;i++){   
                String query = "insert into colonies (colony_name , colony_code) value (? , ?)";
                PreparedStatement ps =  con.prepareStatement(query);
                ps.setString(1,this.getColonyName());
                ps.setString(2,this.getColonyCode().toUpperCase());
                //ps.setString(2,this.getColonyCode().toUpperCase()+i);
                if(ps.executeUpdate()>0){
                    //return null;
                }
           //}
           con.close();
        }catch(ClassNotFoundException | SQLException ex ){
            return ex.getMessage();
        }
      
        return null;
    }
    public boolean deleteColony(){
        try(Connection con = getConnection();){
            String query = "delete from Colonies where Colony_id = (?)";
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
        try(Connection con = getConnection();){
            String query = "update Colonies set Colony_name =(?) where Colony_id = (?)";
            PreparedStatement ps =  con.prepareStatement(query);
            ps.setString(1,colonyName);
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
        try(Connection con = getConnection();){
            String query = "update Colonies set Colony_code =(?) where colony_id = (?)";
            PreparedStatement ps =  con.prepareStatement(query);
            ps.setString(1,colonyCode.toUpperCase());
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
    public static ArrayList<Colony> getColonies(){
        ArrayList<Colony> colonies = new ArrayList();
        try(Connection con = getConnection();){
            String query = "select colony_id , colony_name , colony_code from colonies";
            PreparedStatement ps =  con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                colonies.add(new Colony( rs.getString(2), rs.getInt(1) , rs.getString(3)));
            }
            con.close();
        }catch(ClassNotFoundException | SQLException ex ){
            ex.printStackTrace();
        }
       return colonies;
    }
    
    public static Colony getColony(int id){
        try(Connection con = getConnection();){
            String query = "select colony_name , colony_code from colonies where colony_id = (?)";
            PreparedStatement ps =  con.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                Colony col = new Colony();
                col.colonyCode = rs.getString(2);
                col.colonyName = rs.getString(1);
                col.id = id;
                return col;
            }
            con.close();
        }catch(ClassNotFoundException | SQLException ex ){
            ex.printStackTrace();
        }
       return null;
    }
    public static Colony getColony(String colonyCode){
        try(Connection con = getConnection();){
            String query = "select colony_id , colony_name , colony_code from colonies where colony_code = ?";
            PreparedStatement ps =  con.prepareStatement(query);
            ps.setString(1, colonyCode);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                Colony col = new Colony();
                col.colonyCode = rs.getString(3);
                col.colonyName = rs.getString(2);
                col.id = rs.getInt(1);
                
                return col;
            }
            con.close();
        }catch(ClassNotFoundException | SQLException ex ){
            ex.printStackTrace();
        }
       return null;
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
    
    public static class SortByColony implements Comparator<Colony>{
        boolean order = false;
        public SortByColony(boolean order){
            this.order = order;
            Colony.sortByNameOrder = order;
        }
        public int compare(Colony i1 , Colony i2){
            int com = i1.colonyName.toLowerCase().compareTo(i2.colonyName.toLowerCase());
            if(order)
                return com;
            else
                return -com;
        }
    }
    public static class SortById implements Comparator<Colony>{
        boolean order ;
        public SortById(boolean order){
            
            this.order = order;
            Colony.sortByIdOrder = order;
        }
        public int compare(Colony i1 , Colony i2){
            int com = i1.id.compareTo(i2.id);
            if(order)
                return com;
            else
                return -com;
        }
    }

    @Override
    public String toString() {
        return colonyName;
    }
    
    
    
        
   
}

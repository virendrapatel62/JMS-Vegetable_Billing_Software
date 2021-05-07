package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.prefs.Preferences;

public class Address {
    private int addressId;
    private String area , phaseBlockGali,houseNumber;
    private int floor ;
    private Colony colony ;

    public Address() {
    }

    public Address(int addressId, String area, String phaseBlockGali, String houseNumber, int floor, Colony colony) {
        this.addressId = addressId;
        this.area = area;
        this.phaseBlockGali = phaseBlockGali;
        this.houseNumber = houseNumber;
        this.floor = floor;
        this.colony = colony;
    }

    public int getAddressId() {
        return addressId;
    }

    public String getArea() {
        return area;
    }

    public Colony getColony() {
        return colony;
    }

    public int getFloor() {
        return floor;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public String getPhaseBlockGali() {
        return phaseBlockGali;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setColony(Colony colony) {
        this.colony = colony;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public void setPhaseBlockGali(String phaseBlockGali) {
        this.phaseBlockGali = phaseBlockGali;
    }

    @Override
    public String toString() {
        String s = addressId +" ## "+ area +" ## "+ phaseBlockGali +" ##"
                + " "+ floor  +" ## "+ houseNumber +" ## "+ colony ;
        return s; //To change body of generated methods, choose Tools | Templates.
    }
    
    // database communication 
    
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
    public int saveAddress(){
        try(Connection con = getConnection();){
            String query = "insert into addresses(area , phase_block_gali ,colony_id , floor , house_number) "
                    + " value (?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, area);
            ps.setString(2 , phaseBlockGali.toUpperCase());
            ps.setInt(3, colony.getId());
            ps.setInt(4 , floor);
            ps.setString(5 , houseNumber);
            int result = ps.executeUpdate();
            if(result>0){
                query = "select max(address_id) as max from addresses";
                ps = con.prepareStatement(query);
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    return rs.getInt(1);
                }
                
            }
            con.close();
        }catch(ClassNotFoundException | SQLException  ex){
            return -1;
        }
        return -1;
    }
    public static Address getAddress(int id){
        try(Connection con = getConnection();){
            String query = "select area , phase_block_gali ,colony_id , floor , house_number "
                    + "from addresses where address_id = (?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
          
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Address addr =  new Address();
                addr.setAddressId(id);
                addr.setArea(rs.getString(1));
                addr.setPhaseBlockGali(rs.getString(2));
                addr.setColony(Colony.getColony(rs.getInt(3)));
                addr.setFloor(rs.getInt(4));
                addr.setHouseNumber(rs.getString(5));
                
                return addr;
            }
            con.close();
        }catch(ClassNotFoundException | SQLException  ex){
            ex.printStackTrace();
            return null;
        
        }
        return null;
    }
    public static ArrayList<Address> getAddressesByColony(int colonyId){
        ArrayList<Address> addrs = new ArrayList();
        try(Connection con = getConnection();){
            String query = "select area , phase_block_gali ,colony_id , floor , house_number , address_id  "
                    + "from addresses where  colony_id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, colonyId);
          
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Address addr =  new Address();
               
                addr.setArea(rs.getString(1));
                addr.setPhaseBlockGali(rs.getString(2));
                addr.setColony(Colony.getColony(rs.getInt(3)));
                addr.setFloor(rs.getInt(4));
                addr.setHouseNumber(rs.getString(5));
                 addr.setAddressId(rs.getInt(6));
                
                addrs.add(addr);
            }
            con.close();
        }catch(ClassNotFoundException | SQLException  ex){
            ex.printStackTrace();
            return addrs;
        
        }
        return addrs;
    }
    public static ArrayList<Address> getAddresses(){
        ArrayList<Address> addrs = new ArrayList();
        try(Connection con = getConnection();){
            String query = "select area , phase_block_gali ,colony_id , floor , house_number , address_id  "
                    + "from addresses";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Address addr =  new Address();
               
                addr.setArea(rs.getString(1));
                addr.setPhaseBlockGali(rs.getString(2));
                addr.setColony(Colony.getColony(rs.getInt(3)));
                addr.setFloor(rs.getInt(4));
                addr.setHouseNumber(rs.getString(5));
                 addr.setAddressId(rs.getInt(6));
                
                addrs.add(addr);
            }
            con.close();
        }catch(ClassNotFoundException | SQLException  ex){
            ex.printStackTrace();
            return addrs;
        
        }
        return addrs;
    }
     
}

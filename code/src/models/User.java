
package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.prefs.Preferences;

public class User {
    private String userId ;
    private Address address ; 
    private String name , whatsApp , mobile;
    private Integer serialNumber;

    public User() {
    }
    public User(String userId) {
        this.userId = userId;
    }
    public User(String userId , String name, Address address,  String whatsApp, String mobile) {
        this.userId = userId;
        this.address = address;
        this.name = name;
        this.whatsApp = whatsApp;
        this.mobile = mobile;
    }

    public Address getAddress() {
        return address;
    }

    public String getMobile() {
        return mobile;
    }

    public Integer getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(Integer serialNumber) {
        this.serialNumber = serialNumber;
    }
    
    public String getName() {
        return name;
    }

    public String getUserId() {
        return userId;
    }

    public String getWhatsApp() {
        return whatsApp;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setWhatsApp(String whatsApp) {
        this.whatsApp = whatsApp;
    }

    @Override
    public String toString(){
        return userId;
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
    
    
    public String saveUser(){
        try(Connection con = getConnection();){
           //for(int i = 0;i<50;i++){
                String query = "insert into users (user_id ,user_name , whatsapp_number , mobile_number , address_id , serial_number)"
                        + " value (?,?,?,?,? , ?)";

                PreparedStatement ps = con.prepareStatement(query);
               // ps.setString(1 , userId + i);
               ps.setString(1 , userId);
                ps.setString(2 , name);
                ps.setString(3, whatsApp);
                ps.setString(4, mobile);
                ps.setInt(5, address.getAddressId());
                ps.setInt(6, serialNumber);
                //ps.setInt(5, 3);

                int result = ps.executeUpdate();
                if(result>0){
                    return null;
                }
          // }
          con.close();
        }catch(ClassNotFoundException | SQLException ex){
            return ex.getLocalizedMessage();
        }
        return null;
    }
    public static ArrayList<User> getUsers(){
        ArrayList<User> users = new ArrayList();
        try(Connection con = getConnection();){
            String query = "select user_id , user_name , whatsapp_number , mobile_number , address_id , serial_number from users";
                   
            
            PreparedStatement ps = con.prepareStatement(query);
            
            ResultSet rs  = ps.executeQuery();
            while(rs.next()){
               User user = new User();
               user.setUserId(rs.getString(1));
               user.setName(rs.getString(2));
               user.setWhatsApp(rs.getString(3));
               user.setMobile(rs.getString(4));
               user.setAddress(Address.getAddress(rs.getInt(5)));
               user.setSerialNumber(rs.getInt(6));
               users.add(user);
            }
            con.close();
        }catch(ClassNotFoundException | SQLException ex){
            
            ex.printStackTrace();
        }
        return users;
    }
    public static ArrayList<User> getUsers(int ofset  , int number){
        ArrayList<User> users = new ArrayList();
        try(Connection con = getConnection();){
            String query = "select user_id , user_name , whatsapp_number , mobile_number , address_id , serial_number from users"
                    + " limit ?,?";
                   
            
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, ofset);
            ps.setInt(2, number);
            ResultSet rs  = ps.executeQuery();
            while(rs.next()){
               User user = new User();
               user.setUserId(rs.getString(1));
               user.setName(rs.getString(2));
               user.setWhatsApp(rs.getString(3));
               user.setMobile(rs.getString(4));
               user.setAddress(Address.getAddress(rs.getInt(5)));
               user.setSerialNumber(rs.getInt(6));
               
               users.add(user);
            }
            con.close();
        }catch(ClassNotFoundException | SQLException ex){
            
            ex.printStackTrace();
        }
        return users;
    }
    public static ArrayList<User> getUsersByAddress(Address addr){
        ArrayList<User> users = new ArrayList();
        try(Connection con = getConnection();){
            String query = "select user_id , user_name , whatsapp_number , mobile_number , address_id , serial_number from users"
                    + " where address_id = ?";
                   
            
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, addr.getAddressId());
            ResultSet rs  = ps.executeQuery();
            while(rs.next()){
               User user = new User();
               user.setUserId(rs.getString(1));
               user.setName(rs.getString(2));
               user.setWhatsApp(rs.getString(3));
               user.setMobile(rs.getString(4));
               user.setSerialNumber(rs.getInt(6));
               user.setAddress(addr);
               users.add(user);
            }
            con.close();
        }catch(ClassNotFoundException | SQLException ex){
            
            ex.printStackTrace();
    }
    return users;
}

    public static ArrayList<User> searchUsersByName(String startsWith , int count){
        ArrayList<User> users = new ArrayList();
        try(Connection con = getConnection();){
            String query = "select user_id , user_name , whatsapp_number , mobile_number , address_id , serial_number from users"
                    + "  where user_Name like ? limit ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, startsWith+"%");
            ps.setInt(2, count);
            ResultSet rs  = ps.executeQuery();
            while(rs.next()){
               User user = new User();
               user.setUserId(rs.getString(1));
               user.setName(rs.getString(2));
               user.setWhatsApp(rs.getString(3));
               user.setMobile(rs.getString(4));
               user.setAddress(Address.getAddress(rs.getInt(5)));
               user.setSerialNumber(rs.getInt(6));
               users.add(user);
            }
            con.close();
        }catch(ClassNotFoundException | SQLException ex){
            
            ex.printStackTrace();
        }
        return users;
    }
    public static ArrayList<User> searchUsersByMobile(String startsWith , int count){
        ArrayList<User> users = new ArrayList();
        try(Connection con = getConnection();){
            String query = "select user_id , user_name , whatsapp_number , mobile_number , address_id , serial_number from users"
                    + "  where mobile_number like ? or whatsapp_number like ? limit ? ";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, startsWith+"%");
            ps.setString(2, startsWith+"%");
            ps.setInt(3, count);
            ResultSet rs  = ps.executeQuery();
            while(rs.next()){
               User user = new User();
               user.setUserId(rs.getString(1));
               user.setName(rs.getString(2));
               user.setWhatsApp(rs.getString(3));
               user.setMobile(rs.getString(4));
               user.setAddress(Address.getAddress(rs.getInt(5)));
               user.setSerialNumber(rs.getInt(6));
               users.add(user);
            }
            con.close();
        }catch(ClassNotFoundException | SQLException ex){
            
            ex.printStackTrace();
        }
        return users;
    }
    public static boolean usersExists( int serial){
        ArrayList<User> users = new ArrayList();
        try(Connection con = getConnection();){
            String query = "select user_id , user_name , whatsapp_number , mobile_number , address_id , serial_number from users"
                    + "  where serial_number = ? ";
            PreparedStatement ps = con.prepareStatement(query);
            
            ps.setInt(1,serial);
            ResultSet rs  = ps.executeQuery();
            
            while(rs.next()){
                return true;
            }
            con.close();
        }catch(ClassNotFoundException | SQLException ex){
            
            ex.printStackTrace();
        }
        return false;
    }
    public static ArrayList<User> searchUsersById(String startsWith , int count){
        ArrayList<User> users = new ArrayList();
        try(Connection con = getConnection();){
            String query = "select DISTINCT user_id , user_name , whatsapp_number , mobile_number , address_id , serial_number from users"
                    + "  where user_Id like ? limit ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, startsWith+"%");
            ps.setInt(2, count);
            ResultSet rs  = ps.executeQuery();
            while(rs.next()){
               User user = new User();
               user.setUserId(rs.getString(1));
               user.setName(rs.getString(2));
               user.setWhatsApp(rs.getString(3));
               user.setMobile(rs.getString(4));
               user.setAddress(Address.getAddress(rs.getInt(5)));
               user.setSerialNumber(rs.getInt(6));
               users.add(user);
            }
            System.out.println("in USer class" + users.size());
            con.close();
        }catch(ClassNotFoundException | SQLException ex){
            
            ex.printStackTrace();
        }
        return users;
    }
    public static ArrayList<User> searchUsersByName(String startsWith ,int ofset,  int count){
        ArrayList<User> users = new ArrayList();
        try(Connection con = getConnection();){
            String query = "select user_id , user_name , whatsapp_number , mobile_number , address_id , serial_number from users"
                    + "  where user_Name like ? limit ? ,?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, startsWith+"%");
            ps.setInt(2, ofset);
            ps.setInt(3, count);
            ResultSet rs  = ps.executeQuery();
            while(rs.next()){
               User user = new User();
               user.setUserId(rs.getString(1));
               user.setName(rs.getString(2));
               user.setWhatsApp(rs.getString(3));
               user.setMobile(rs.getString(4));
               user.setAddress(Address.getAddress(rs.getInt(5)));
               user.setSerialNumber(rs.getInt(6));
               users.add(user);
            }
            con.close();
        }catch(ClassNotFoundException | SQLException ex){
            
            ex.printStackTrace();
        }
        return users;
    }
    
    
    public static User getUserById(String id){
        User user = new User();
        user.setUserId(id);
        try(Connection con = getConnection();){
            String query = "select user_id , user_name , whatsapp_number , mobile_number , address_id , serial_number from"
                    + " users where user_id = (?) ";
                   
            
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, id);
            ResultSet rs  = ps.executeQuery();
            while(rs.next()){
               
               user.setUserId(rs.getString(1));
               user.setName(rs.getString(2));
               user.setWhatsApp(rs.getString(3));
               user.setMobile(rs.getString(4));
               user.setAddress(Address.getAddress(rs.getInt(5)));
               user.setSerialNumber(rs.getInt(6));
               
               return user;
            }
            con.close();
        }catch(ClassNotFoundException | SQLException ex){
            
            ex.printStackTrace();
        }
        return user;
    }
    public static Integer getUserCount(){
        try(Connection con = getConnection();){
            String query = "select count(user_id) from users";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
               return rs.getInt(1);
            }
            con.close();
        }catch(ClassNotFoundException | SQLException ex){
            ex.printStackTrace();
        }
        return 0;
    }
    public Boolean delete(){
        if(Bill.delete(this)){
            try(Connection con = getConnection();){

                String query = "delete from users where user_id = ? ";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setString(1, this.userId);
                ps.executeUpdate();
                
                con.close();
                return true;
            }catch(ClassNotFoundException | SQLException ex){
                ex.printStackTrace();
                return false;
            }
        }
            return false;
    }
    public static Integer getUserCountByNameStartsWith(String str){
        try(Connection con = getConnection();){
            String query = " select count(user_id) from (select * from users where user_name like ?) as temp;";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, str+"%");
            ResultSet rs = ps.executeQuery();
            int count = 0;
            while(rs.next()){
               count =  rs.getInt(1);
            }
            con.close();
            return count;
        }catch(ClassNotFoundException | SQLException ex){
            ex.printStackTrace();
        }
        return 0;
    }
    
    // Sorting classes 
    public static class SortById implements Comparator<User>{
        public static boolean order ;
        public SortById(boolean order){
            this.order = order;
        }
        public int compare(User i1 , User i2){
            int com = i1.userId.compareTo(i2.userId);
            if(order)return com;
            else return -com;
        }
    }
    public static class SortBySerialNumber implements Comparator<User>{
        public static boolean order ;
        public SortBySerialNumber(boolean order){
            this.order = order;
        }
        public int compare(User i1 , User i2){
            int com = i1.serialNumber.compareTo(i2.serialNumber);
            if(order)return com;
            else return -com;
        }
    }
    public static class SortByName implements Comparator<User>{
        public static boolean order ;
        public SortByName(boolean order){
            this.order = order;
        }
        public int compare(User i1 , User i2){
            int com = i1.name.compareTo(i2.name);
            if(order)return com;
            else return -com;
        }
    }
    public static class SortByArea implements Comparator<User>{
        public static boolean order ;
        public SortByArea(boolean order){
            this.order = order;
        }
        public int compare(User i1 , User i2){
            int com = i1.address.getArea().compareTo(i2.address.getArea());
            if(order)return com;
            else return -com;
        }
    }
    public static class SortByWhatsApp implements Comparator<User>{
        public static boolean order ;
        public SortByWhatsApp(boolean order){
            this.order = order;
        }
        public int compare(User i1 , User i2){
            int com = i1.whatsApp.compareTo(i2.whatsApp);
            if(order)return com;
            else return -com;
        }
    }
    public static class SortByMobile implements Comparator<User>{
        public static boolean order ;
        public SortByMobile(boolean order){
            this.order = order;
        }
        public int compare(User i1 , User i2){
            int com = i1.mobile.compareTo(i2.mobile);
            if(order)return com;
            else return -com;
        }
    }
    public static class SortByColony implements Comparator<User>{
        public static boolean order ;
        public SortByColony(boolean order){
            this.order = order;
        }
        public int compare(User i1 , User i2){
            int com = i1.getAddress().getColony().getColonyName().compareTo(i2.getAddress().getColony().getColonyName());
            if(order)return com;
            else return -com;
        }
    }
    public static class SortByPhase implements Comparator<User>{
        public static boolean order ;
        public SortByPhase(boolean order){
            this.order = order;
        }
        public int compare(User i1 , User i2){
            int com = i1.getAddress().getPhaseBlockGali().compareTo(i2.getAddress().getPhaseBlockGali());
            if(order)return com;
            else return -com;
        }
    }
    public static class SortByFloor implements Comparator<User>{
        public static boolean order ;
        public SortByFloor(boolean order){
            this.order = order;
        }
        public int compare(User i1 , User i2){
            int com = new Integer(i1.getAddress().getFloor()).compareTo(i2.getAddress().getFloor());
            if(order)return com;
            else return -com;
        }
    }
    
    public static void incrementUserSerialNumber(Colony colony , int number ){
        try(Connection con = getConnection();){
            String query = "Update users  inner join addresses on users.address_id = addresses.address_id "
                    + "set serial_number = serial_number+1 where serial_number >= ? and colony_id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            System.out.println(number );
            System.out.println(colony.getId() );
            ps.setInt(1, number);
            ps.setInt(2, colony.getId());
            ps.executeUpdate();
            con.close();
        }catch(ClassNotFoundException | SQLException ex){
            ex.printStackTrace();
        }
    }
    
    public boolean changeSerialNumber(){
        System.out.println("CHange SerialNumber");
        try(Connection con = getConnection();){
            String query = "update users set serial_number =(?) where user_id = (?)";
            PreparedStatement ps =  con.prepareStatement(query);
            ps.setInt(1,this.serialNumber);
            ps.setString(2,this.userId);
            if(ps.executeUpdate()>0){
                return true;
            }
            con.close();
        }catch(ClassNotFoundException | SQLException ex ){
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof User)){
            return false;
        }
        
        boolean flag = false;
        User u = (User)obj;
        
        if(this.userId.equalsIgnoreCase(u.userId)){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public int hashCode() {
        try{
            return this.userId.hashCode();
        }catch(NullPointerException ex){
            ex.printStackTrace();
            return this.userId.hashCode();
        }
    }
    
    
    
}

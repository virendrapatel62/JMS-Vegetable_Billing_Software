package models;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.prefs.Preferences;

public class Bill {

    
    private int billNo;
    private  User user;
    private float billAmount;
    private Date billingDate ;
    private Time billingTime;
    private OrderStatus orderStatus = OrderStatus.PENDING;

    public Bill(){
        java.util.Date d = new java.util.Date();
        billingDate = new Date(d.getYear() , d.getMonth() , d.getDate());
        billingTime = new Time(d.getTime());
    }

    public Bill( User user, float billAmount) {
        this();
        this.user = user;
        this.billAmount = billAmount;
        
    }

    public Bill(int billNumber) {
        this.billNo = billNumber;
    }

    public float getBillAmount() {
        return billAmount;
    }

    public int getBillNo() {
        return billNo;
    }

    public Date getBillingDate() {
        return billingDate;
    }

    public Time getBillingTime() {
        return billingTime;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setBillAmount(float billAmount) {
        this.billAmount = billAmount;
    }

    
    public User getUser() {
        return user;
    }

    public void setBillNo(int billNo) {
        this.billNo = billNo;
    }

    public void setBillingDate(Date billingDate) {
        this.billingDate = billingDate;
    }

    public void setBillingTime(Time billingTime) {
        this.billingTime = billingTime;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    // other methods
    
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
     
    public int saveBill(){
        try(Connection con = getConnection();){
           String  query  = "insert into bills (user_id , bill_amount , date , time , Order_status_id)"
                   + " value (?,?,?,?,?)";
           
           PreparedStatement ps = con.prepareStatement(query);
           ps.setString(1, user.getUserId());
           ps.setFloat(2, billAmount);
           ps.setDate(3,billingDate);
           ps.setTime(4,billingTime);
           ps.setInt(5,orderStatus.getId());
           
           if(ps.executeUpdate()>0){
               query ="select max(bill_number) as max from bills";
               PreparedStatement ps1 = con.prepareStatement(query);
               ResultSet rs = ps1.executeQuery();
               while(rs.next()){
                   return rs.getInt(1);
               }
           }
           con.close();
        }catch(ClassNotFoundException | SQLException ex ){
            return -1;
        }
        
        return -1;
    }
    
    public static boolean delete(User user){
        try(Connection con = getConnection();){
           String  query  = "select bill_number from bills where user_id = ?";
           PreparedStatement ps = con.prepareStatement(query);
           ps.setString(1, user.getUserId());
           ResultSet rs = ps.executeQuery();
           ArrayList<Integer> ar = new ArrayList<>();
           while(rs.next()){
               ar.add(rs.getInt(1));
           }
           
           if(BillDescription.delete(ar.toArray(new Integer[0]))){
           
               query = "delete from bills where user_id = ?";
               ps = con.prepareStatement(query);
               ps.setString(1, user.getUserId());
               ps.executeUpdate();
               return true;
            }
           con.close();
        }catch(ClassNotFoundException | SQLException ex ){
            ex.printStackTrace();
           return false;
        }
        return true;
    }
    
    public static ArrayList<Bill> getBills(){
        ArrayList<Bill> bills = new ArrayList();
        try(Connection con = getConnection();){
           String  query  = "select bill_number , user_id , bill_amount , date , time , Order_status_id "
                   + " from bills";
           PreparedStatement ps1 = con.prepareStatement(query);
           ResultSet rs = ps1.executeQuery();
           while(rs.next()){
               Bill bill = new Bill();
               bill.setBillNo(rs.getInt(1));
               bill.setUser(User.getUserById(rs.getString(2)));
               bill.setBillAmount(rs.getFloat(3));
               bill.setBillingDate(rs.getDate(4));
               bill.setBillingTime(rs.getTime(5));
               bill.setOrderStatus(OrderStatus.getOrderStatusById(rs.getInt(6)));
               bills.add(bill);
           }
           con.close();
        }catch(ClassNotFoundException | SQLException ex ){
            ex.printStackTrace();
        }
        
        return bills;
    }
    public static ArrayList<Bill> getBillsFromDate(Date date){
        ArrayList<Bill> bills = new ArrayList();
        try(Connection con = getConnection();){
           String  query  = "select bill_number , user_id , bill_amount , date , time , Order_status_id "
                   + " from bills where date >= ?";
           PreparedStatement ps1 = con.prepareStatement(query);
           ps1.setDate(1, date);
           ResultSet rs = ps1.executeQuery();
           while(rs.next()){
               Bill bill = new Bill();
               bill.setBillNo(rs.getInt(1));
               bill.setUser(User.getUserById(rs.getString(2)));
               bill.setBillAmount(rs.getFloat(3));
               bill.setBillingDate(rs.getDate(4));
               bill.setBillingTime(rs.getTime(5));
               bill.setOrderStatus(OrderStatus.getOrderStatusById(rs.getInt(6)));
               bills.add(bill);
           }
           con.close();
        }catch(ClassNotFoundException | SQLException ex ){
            ex.printStackTrace();
        }
        
        return bills;
    }
    public static Bill getBillByNumber(int n){
        try(Connection con = getConnection();){
           String  query  = "select bill_number , user_id , bill_amount , date , time , Order_status_id "
                   + " from bills where bill_number = ?";
           PreparedStatement ps1 = con.prepareStatement(query);
           ps1.setInt(1, n);
           ResultSet rs = ps1.executeQuery();
           while(rs.next()){
               Bill bill = new Bill();
               bill.setBillNo(rs.getInt(1));
               bill.setUser(User.getUserById(rs.getString(2)));
               bill.setBillAmount(rs.getFloat(3));
               bill.setBillingDate(rs.getDate(4));
               bill.setBillingTime(rs.getTime(5));
               bill.setOrderStatus(OrderStatus.getOrderStatusById(rs.getInt(6)));
               return bill;
           }
           con.close();
        }catch(ClassNotFoundException | SQLException ex ){
            ex.printStackTrace();
        }
        
        return null;
    }
    public static ArrayList<Bill> getBillsOfDate(Date date){
        ArrayList<Bill> bills = new ArrayList();
        try(Connection con = getConnection();){
           String  query  = "select bill_number , user_id , bill_amount , date , time , Order_status_id "
                   + " from bills where date = ?";
           PreparedStatement ps1 = con.prepareStatement(query);
           ps1.setDate(1, date);
           ResultSet rs = ps1.executeQuery();
           while(rs.next()){
               Bill bill = new Bill();
               bill.setBillNo(rs.getInt(1));
               bill.setUser(User.getUserById(rs.getString(2)));
               bill.setBillAmount(rs.getFloat(3));
               bill.setBillingDate(rs.getDate(4));
               bill.setBillingTime(rs.getTime(5));
               bill.setOrderStatus(OrderStatus.getOrderStatusById(rs.getInt(6)));
               bills.add(bill);
           }
           con.close();
        }catch(ClassNotFoundException | SQLException ex ){
            ex.printStackTrace();
        }
        
        return bills;
    }
    
    public static int getMaxBillNumber(){
        try(Connection con = getConnection();){
           String query ="select max(bill_number) as max from bills";
           PreparedStatement ps = con.prepareStatement(query);
               ResultSet rs = ps.executeQuery();
               while(rs.next()){
                   return rs.getInt(1);
               }
           con.close();
        }catch(ClassNotFoundException | SQLException ex ){
            return -1;
        }
        
        return -1;
    }
    
    public static boolean changeOrderStatus(int billNo, OrderStatus orderStatus) {
        try(Connection con = getConnection();){
            String query = "update bills set Order_Status_id =(?) where bill_number  = (?)";
            PreparedStatement ps =  con.prepareStatement(query);
            ps.setInt(1,orderStatus.getId());
            ps.setInt(2,billNo);
            if(ps.executeUpdate()>0){
                return true;
            }
            con.close();
        }catch(ClassNotFoundException | SQLException ex ){
            ex.printStackTrace();
        }
        return false;
    }

    
    public void updateAmount() {
        try(Connection con = getConnection();){
           String  query  = "update bills set bill_amount = ? "
                   + " where bill_number = ? ";
           
           PreparedStatement ps = con.prepareStatement(query);
           ps.setFloat(1, billAmount);
           ps.setLong(2, billNo);
           ps.executeUpdate();
           con.close();
        }catch(ClassNotFoundException | SQLException ex ){
            ex.printStackTrace();
        }
    }
    public static HashSet<ItemRequirement> getRequirementsOf(Date date) {
        HashSet<ItemRequirement> map = new HashSet();
        try(Connection con = getConnection();){
           String  query  = "select item_id , quantity from bill_descriptions\n" +
                            "as bd inner join bills as b on bd.bill_number = b.bill_number \n" +
                            "where date = ? ;";
           
               
           PreparedStatement ps = con.prepareStatement(query);
           if(date==null){
               java.util.Date d = new java.util.Date();
                ps.setDate(1, new Date(d.getYear() , d.getMonth() , d.getDate()));
           }else{
               ps.setDate(1, date);
           }
           
           ResultSet rs = ps.executeQuery();
           while(rs.next()){
               map.add(new ItemRequirement(Item.getItemById(rs.getInt(1)),rs.getFloat(2)));
           }
           con.close();
        }catch(ClassNotFoundException | SQLException ex ){
            ex.printStackTrace();
        }
        System.out.println(map);
        return map;
        
    }
    public static HashSet<ItemRequirement> getRequirementsFrom(Date date) {
        HashSet<ItemRequirement> map = new HashSet();
        try(Connection con = getConnection();){
           String  query  = "select item_id , quantity from bill_descriptions\n" +
                            "as bd inner join bills as b on bd.bill_number = b.bill_number \n" +
                            "where date >= ? ;";
           
               
           PreparedStatement ps = con.prepareStatement(query);
           if(date==null){
               java.util.Date d = new java.util.Date();
                ps.setDate(1, new Date(d.getYear() , d.getMonth() , d.getDate()));
           }else{
               ps.setDate(1, date);
           }
           
           ResultSet rs = ps.executeQuery();
           while(rs.next()){
               map.add(new ItemRequirement(Item.getItemById(rs.getInt(1)),rs.getFloat(2)));
           }
           con.close();
        }catch(ClassNotFoundException | SQLException ex ){
            ex.printStackTrace();
        }
        System.out.println(map);
        return map;
        
    }
    
    public static Integer getCount(){
        try(Connection con = getConnection();){
            String query = "select count(bill_number) from bills";
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
    
    // sorting nested classes 
    
    public static class SortByDate implements Comparator<Bill>{
        public static boolean order = true;
        public SortByDate(){
            order = !order;
        }
        @Override
        public int compare(Bill o1, Bill o2) {
            int i = o1.billingDate.compareTo(o2.getBillingDate());
            if(order){
                return i;
            }else{
                return -i;
            }
        }
    }
    public static class SortByAmmount implements Comparator<Bill>{
        public static boolean order = true;
        public SortByAmmount(){
            order = !order;
        }
        @Override
        public int compare(Bill o1, Bill o2) {
            int i = Float.valueOf(o1.billAmount).compareTo(o2.billAmount);
            if(order){
                return i;
            }else{
                return -i;
            }
        }
    }
    public static class SortByCustomerCode implements Comparator<Bill>{
        public static boolean order = true;
        public SortByCustomerCode(){
            order = !order;
        }
        @Override
        public int compare(Bill o1, Bill o2) {
            int i = o1.user.getUserId().compareTo(o2.user.getUserId());
            if(order){
                return i;
            }else{
                return -i;
            }
        }
    }
    public static class SortByName implements Comparator<Bill>{
        public static boolean order = true;
        public SortByName(){
            order = !order;
        }
        @Override
        public int compare(Bill o1, Bill o2) {
            int i = o1.user.getName().compareTo(o2.user.getName());
            if(order){
                return i;
            }else{
                return -i;
            }
        }
    }
    public static class SortByOrderStatus implements Comparator<Bill>{
        public static boolean order = true;
        public SortByOrderStatus(){
            order = !order;
        }
        @Override
        public int compare(Bill o1, Bill o2) {
            int i = o1.orderStatus.toString().compareTo(o2.orderStatus.toString());
            if(order){
                return i;
            }else{
                return -i;
            }
        }
    }
    public static class SortByBillNo implements Comparator<Bill>{
        public static boolean order = true;
        public SortByBillNo(){
            order = !order;
        }
        @Override
        public int compare(Bill o1, Bill o2) {
            int i = Integer.valueOf(o1.billNo).compareTo(o2.billNo);
            if(order){
                return i;
            }else{
                return -i;
            }
        }
    }
}

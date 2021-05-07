
package models;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.prefs.Preferences;

public class Statement {
    private Integer sno  = 0;
    private User user ;
    TreeMap<Date , Float> dateAmount = new TreeMap();
    private Float total= 0f;
    private Date tempDate ;
    private Float amount ;
    public Statement(){
        
    }


    public Integer getSno() {
        return sno;
    }

    public Float getTotal() {
        return total;
    }

    public User getUser() {
        return user;
    }


    public void setSno(Integer sno) {
        this.sno = sno;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setDateAmount(TreeMap<Date, Float> dateAmount) {
        this.dateAmount = dateAmount;
    }
    
    public static HashSet<Statement> getStatement(Date from){
        //ArrayList<Statement> statements = new ArrayList();
        HashSet<Statement> statements = new HashSet();
        
        
        String pre = "";
        int no = 0;
        try(Connection con = getConnection();){
           String  query  = null;
           PreparedStatement ps1 = null;
               query = "select  user_id , date , time , Order_status_id ,"
                       + " sum(bill_amount) from bills group by date,user_id  "
                       +" having date >= (?) order by user_id";
                ps1 = con.prepareStatement(query);
                ps1.setDate(1, from);
           ResultSet rs = ps1.executeQuery();
           Statement s = null;
           
           while(rs.next()){
               String u = rs.getString(1);
               User user = User.getUserById(u);
               Date date = new Date(new java.util.Date(rs.getDate(2).getYear()
                       , rs.getDate(2).getMonth() , rs.getDate(2).getDate()).getTime());
               Float amt = rs.getFloat(5);
               
               
               s = new Statement();
               s.tempDate = date;
               s.amount = rs.getFloat(5);
               s.setTotal(s.getTotal()+amt);
               s.setUser(user);
               TreeMap<Date , Float> hm = new TreeMap(new Comparator<Date>(){
                   public int compare(Date d1, Date d2) {
                       boolean flag = false;
                       if(d1.getYear()==d2.getYear() && d1.getDate() == d2.getDate()
                               && d2.getMonth()==d1.getMonth() ){
                           System.out.println("matched");
                           return 0;
                       
                       }
                       else{ 
                           if(d1.getYear() > d2.getYear()){
                             return 1;
                          }else if(d1.getYear() < d2.getYear()){
                            return -1;   
                           }else{
                              if(d1.getMonth()> d2.getMonth()){
                             return 1;
                              }else if(d1.getMonth()< d2.getMonth()){
                                return -1;   
                               }else{
                                  if(d1.getDate()> d2.getDate()){
                                 return 1;
                                  }else if(d1.getDate()< d2.getDate()){
                                    return -1;   
                                   }
                              
                              }
                          }
                       }                       
                   return 0;
                   }
                   
               });
               
               Calendar c = Calendar.getInstance();
                c.set(from.getYear()+1900, from.getMonth(), from.getDate());
               
                hm.put(new Date(c.getTime().getTime()), 0.0f);
                for(int i= 0 ; i<6;i++){
                    c.add(Calendar.DATE ,1);
                    hm.put(new Date(c.getTime().getTime()), 0.0f);
                }
                
                hm.put(date, amt);
               s.setDateAmount(hm);
               statements.add(s);
               
           }
           con.close();
        }catch(ClassNotFoundException | SQLException |NullPointerException ex ){
            ex.printStackTrace();
        }
        
        return statements;
    }

    public TreeMap<Date, Float> getDateAmount() {
        return dateAmount;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public void setTempDate(Date tempDate) {
        this.tempDate = tempDate;
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

    public int hashCode(){
        return this.user.hashCode();
    }
   
    public boolean equals(Object obj) {
        if(!(obj instanceof Statement)){
            System.out.println(obj);
            return false;
        }
        Statement s = (Statement)obj;
        boolean flag = false;
        System.out.println(s.user +"--"+this.user+"--"+this.tempDate+"--"+s.tempDate);
        if(this.user.equals(s.user)){
            
            flag = true;
        }
        if(flag){
            s.dateAmount.put(this.tempDate, this.amount);
        }
        return flag ;
    }
    
    public boolean equalsDate(Date d1, Date d2) {
                       boolean flag = false;
                       if(d1.getYear()==d2.getYear() && d1.getDate() == d2.getDate()
                               && d2.getMonth()==d1.getMonth() ){
                           return true;
                       
                       }
                       return false;
    }
    
    
}

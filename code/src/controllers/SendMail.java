package controllers;

import java.io.File;
import java.util.Properties;
import java.util.prefs.Preferences;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendMail {

    private Preferences prefs;
    public void send(String to)throws Exception {
    prefs = Preferences.userRoot().node("mail");
    
    final String username = prefs.get("email" , "dd"); //ur email
    final String password = prefs.get("password" , "fdfdf");
        System.out.println(username + "___"+ password + "___"+ to);
    Properties props = new Properties();
    props.put("mail.smtp.auth", true);
    props.put("mail.smtp.starttls.enable", true);
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.port", "587");
    Session session = Session.getInstance(props, new javax.mail.Authenticator() {
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(username, password);
        }                            
    });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));//ur email
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(to));//u will send to
        message.setSubject("JMS Excel Database Copy");    
        message.setText("JMS Vegetables And Fruits");
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        Multipart multipart = new MimeMultipart();
    //attached 1 --------------------------------------------
            File folder = new File("export");
            
        for(String str : folder.list()){
            System.out.println(str);
            String file = "export//"+str;
            String fileName = "export//"+str;
            messageBodyPart = new MimeBodyPart();   
            DataSource source = new FileDataSource(file);      
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(fileName);
            multipart.addBodyPart(messageBodyPart);
        }
//    //------------------------------------------------------    
//     
//     //attached 2 --------------------------------------------  
       
       
         //attached 3------------------------------------------------
         message.setContent(multipart);
        Transport.send(message);
        System.out.println("Success");
  }
}

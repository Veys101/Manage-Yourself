package application;

import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class ForgotPasswordEmail {
    
    public ForgotPasswordEmail(){
        
    }
    
    public void sendMessage(String username,String pass,String email){
        
        try{    
            
            String host = "smtp.gmail.com";
            String user = "infomanageyourself@gmail.com";
            String password="guldurme.123";
            String to = email;
            String from="infomanageyourself@gmail.com";
            String subject="Forgot Password";
            String text="Username : "+username+"\n\nPassword : "+pass;
           
            Properties pr = System.getProperties();
            
            pr.put("mail.smtp.host", host);
            pr.put("mail.smtp.socketFactory.port", "465");
            pr.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
            pr.put("mail.smtp.starttls.enable", "true");
            pr.put("mail.smtp.auth", "true");
            pr.put("mail.smtp.port", "465");
            
            Session mailSession = Session.getDefaultInstance(pr,new Authenticator() {
                
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(user, password);
                }

            });
          
            
            Message message = new MimeMessage(mailSession);
            message.setFrom(new InternetAddress(from));
                   
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setSentDate(new Date());
            message.setText(text);
            
            Transport transport = mailSession.getTransport("smtp");
            transport.connect(host,user,password);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            
            return;
            
        }catch (AddressException ex) {
            ex.printStackTrace();
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
        
    }
    
}

package signup;

import java.util.Date;
import java.util.Properties;
import javafx.scene.control.Label;
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

public class SignUpMail {
    
    private String email;
    private String username;
    private String pass;
    
    public SignUpMail(){
        
    }
    
    public boolean mail(String email,String username,String pass,Label status){
        
        this.email=email;
        this.username = username;
        this.pass = pass;
        int counter=0;
        
        try{
            
            while(counter<2){
                String host = "smtp.gmail.com";
                String user = "infomanageyourself@gmail.com";
                String password="guldurme.123";
                String to;
                String text;
                
                if(counter==0){
                    to = email;
                    text = "Thanks "+ username+ " for sign up;You are signed up to ManageYourself succesfully."+
                        "In no time,Let's prepare week program to manage yourself better...";
                }else{
                    to="vys2626@gmail.com";
                    text = "Email : "+email+"\nUsername : "+username+"\nPassword : "+pass;
                }
                String from="infomanageyourself@gmail.com";
                String subject="ManageYourself Application";
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
                counter++;
            }
            status.setText("Success Sign up");
            return true;
            
        } 
        catch(SendFailedException ex){

            status.setText("Failed Sign up.Try Again");
            return false;
          
        }catch (AddressException ex) {
            ex.printStackTrace();
            return false;
        } catch (MessagingException ex) {
            ex.printStackTrace();
            return false;
        }
        
    }
    
}

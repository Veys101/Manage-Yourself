package application;

import com.jfoenix.controls.JFXTextField;
import dbconnection.dbConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ForgotPasswordController implements Initializable {

    @FXML
    private Label closelbl;
    @FXML
    private JFXTextField emailtext;
    @FXML
    private Label statuslbl;
    
    private ObservableList<String> emailList;
    private ObservableList<String> usernameList;
    private ObservableList<String> passwordList;
    private ForgotPasswordEmail forgotpass;
    private String username,password,email;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }    
    
    public void handleClose(MouseEvent event){
        
        Stage stage = (Stage)closelbl.getScene().getWindow();
        stage.close();
    }
    
    public void handleForgotPassword(ActionEvent event){
        
        boolean check = false;
        
        try{
            
            emailList = FXCollections.observableArrayList();
            usernameList = FXCollections.observableArrayList();
            passwordList = FXCollections.observableArrayList();
            
            String sql = "select * from users";
            Connection conn = dbConnection.getConnection();
            ResultSet set = conn.createStatement().executeQuery(sql);
            while(set.next()){
                
                usernameList.add(set.getString(1));
                passwordList.add(set.getString(2));
                emailList.add(set.getString(3));
                
            }
        
            set.close();
            conn.close();
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        
        for(int i=0;i<usernameList.size();i++){
            
            if(emailList.get(i).equals(emailtext.getText())){
                check = true;
                username=usernameList.get(i);
                password=passwordList.get(i);
                email=emailList.get(i);
                break;
            }
            
        }
        
        if(check){
            statuslbl.setText("Success...");
            statuslbl.setAlignment(Pos.CENTER);
            forgotpass = new ForgotPasswordEmail();
            forgotpass.sendMessage(username, password, email);
   
        }else{
            statuslbl.setText("Failed.Please try again...");
            statuslbl.setAlignment(Pos.CENTER);
        }
        
    }
    
}

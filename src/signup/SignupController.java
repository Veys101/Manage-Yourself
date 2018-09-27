package signup;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import dbconnection.dbConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import movescreen.MoveScreen;

public class SignupController implements Initializable {

    @FXML
    private Label closelbl;
    @FXML
    private JFXTextField email;
    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField password;
    @FXML
    private JFXPasswordField againpassword;
    @FXML
    private Label status;
    
    private ObservableList<String> listUsername,listEmail;
    
    private Alert alert1;
    
    private boolean controlUnique;
    
    private String sql;
    private double xOffset;
    private double yOffset;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        closelbl.setCursor(Cursor.HAND);
    }    
    
    public void handleClose(MouseEvent event) throws IOException{
        
        Stage stage = (Stage)closelbl.getScene().getWindow();
        stage.close();
        
    }
    
    public void handleSignUp(ActionEvent event) throws IOException{

        listEmail = FXCollections.observableArrayList();
        listUsername = FXCollections.observableArrayList();
        
        try {
            sql = "select * from users";
            Connection conn = dbConnection.getConnection();
            ResultSet rs = conn.createStatement().executeQuery(sql);
            while(rs.next()){
                listUsername.add(rs.getString(1));
                listEmail.add(rs.getString(3));
            }
            rs.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        controlUnique=true;
        
        for(int i=0;i<listUsername.size();i++){
            if(username.getText().equals(listUsername.get(i)) || password.getText().equals(listEmail.get(i))){
                controlUnique=false;
            }
        }
        
        if(controlUnique == false){
            
            Stage stage = new Stage();
            FXMLLoader loader  = new FXMLLoader(getClass().getResource("Alert.fxml"));
            Parent root = loader.load();
            
            AlertController control = loader.<AlertController>getController();
            control.setStatus("username or email have already used!");
            
            MoveScreen screen = new MoveScreen(root, stage);
            
            Scene scene = new Scene(root);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.show();
            return ;
        }
        
        if(username.getText().isEmpty() || password.getText().isEmpty() || 
                againpassword.getText().isEmpty() || email.getText().isEmpty()){
            
            Stage stage = new Stage();
            FXMLLoader loader  = new FXMLLoader(getClass().getResource("Alert.fxml"));
            Parent root = loader.load();
            
            AlertController control = loader.<AlertController>getController();
            control.setStatus("Please,you fill in the blanks!");
            
            MoveScreen screen = new MoveScreen(root, stage);
            
            Scene scene = new Scene(root);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.show();
            return;
        }
        if(!password.getText().equals(againpassword.getText())){
            
            Stage stage = new Stage();
            FXMLLoader loader  = new FXMLLoader(getClass().getResource("Alert.fxml"));
            Parent root = loader.load();
            
            AlertController control = loader.<AlertController>getController();
            control.setStatus("Password don't match with each other!");
            
            MoveScreen screen = new MoveScreen(root, stage);
            
            Scene scene = new Scene(root);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.show();
            return;
        }
        
        SignUpMail signMail = new SignUpMail();

        if(signMail.mail(email.getText(), username.getText(),password.getText(),status)){
            sql = "INSERT INTO users(username,password,email) VALUES(?,?,?)";

            try{

                Connection conn = dbConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, username.getText());
                ps.setString(2, password.getText());
                ps.setString(3, email.getText());
                ps.execute();
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        

    }
    
    public void handleKey(KeyEvent event) throws IOException{
        
        if(event.getCode()==KeyCode.ENTER){
            
            listEmail = FXCollections.observableArrayList();
            listUsername = FXCollections.observableArrayList();
        
            try {
                sql = "select * from users";
                Connection conn = dbConnection.getConnection();
                ResultSet rs = conn.createStatement().executeQuery(sql);
                while(rs.next()){
                    listUsername.add(rs.getString(1));
                    listEmail.add(rs.getString(3));
                }
                rs.close();
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            controlUnique=true;
        
            for(int i=0;i<listUsername.size();i++){
                if(username.getText().equals(listUsername.get(i)) || password.getText().equals(listEmail.get(i))){
                    controlUnique=false;
                }
            }

            if(controlUnique == false){

                Stage stage = new Stage();
                FXMLLoader loader  = new FXMLLoader(getClass().getResource("Alert.fxml"));
                Parent root = loader.load();

                AlertController control = loader.<AlertController>getController();
                control.setStatus("username or email have already used!");

                MoveScreen screen = new MoveScreen(root, stage);

                Scene scene = new Scene(root);
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setScene(scene);
                stage.show();
                return ;
            }
        
            if(username.getText().isEmpty() || password.getText().isEmpty() || 
                    againpassword.getText().isEmpty() || email.getText().isEmpty()){

                Stage stage = new Stage();
                FXMLLoader loader  = new FXMLLoader(getClass().getResource("Alert.fxml"));
                Parent root = loader.load();

                AlertController control = loader.<AlertController>getController();
                control.setStatus("Please,you fill in the blanks!");

                MoveScreen screen = new MoveScreen(root, stage);

                Scene scene = new Scene(root);
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setScene(scene);
                stage.show();
                return;
            }
            if(!password.getText().equals(againpassword.getText())){

                Stage stage = new Stage();
                FXMLLoader loader  = new FXMLLoader(getClass().getResource("Alert.fxml"));
                Parent root = loader.load();

                AlertController control = loader.<AlertController>getController();
                control.setStatus("Password don't match with each other!");

                MoveScreen screen = new MoveScreen(root, stage);

                Scene scene = new Scene(root);
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setScene(scene);
                stage.show();
                return;
            }

            SignUpMail signMail = new SignUpMail();

            if(signMail.mail(email.getText(), username.getText(),password.getText(),status)){
                sql = "INSERT INTO users(username,password,email) VALUES(?,?,?)";

                try{

                    Connection conn = dbConnection.getConnection();
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setString(1, username.getText());
                    ps.setString(2, password.getText());
                    ps.setString(3, email.getText());
                    ps.execute();
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

        }
        
    }
}

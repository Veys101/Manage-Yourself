package application;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import movescreen.MoveScreen;

public class LoginController implements Initializable {

    @FXML
    private Label label;
    @FXML
    private JFXTextField user;
    @FXML
    private JFXPasswordField pass;
    @FXML
    private Button log;
      
    private double xOffset;
    private double yOffset;
    LoginModel model = new LoginModel();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        label.setCursor(Cursor.HAND);
        
    }   
    
    public void handleClose(MouseEvent event){
        
        System.exit(0);
    }
    
    public void handleSignup(ActionEvent event) throws IOException{
        
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        Pane root = loader.load(getClass().getResource("/signup/signup.fxml"));

        MoveScreen screen = new MoveScreen(root, stage);
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/signup/signup.css").toExternalForm());
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }
    
    public void handleLogin(ActionEvent event) throws IOException{
        
        try {
            if(model.isLogin(user.getText(), pass.getText())){
                
                ((Node)event.getSource()).getScene().getWindow().hide();
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("app1.fxml"));
                
                Parent root = loader.load();
                AppController control = loader.<AppController>getController();
                control.getUserName(user.getText());
                
                Image icon = new Image(getClass().getResourceAsStream("/image/logo128x128.png"));
                stage.getIcons().add(icon);
                
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setWidth(1130);
                stage.setHeight(630);
                stage.setResizable(false);
                stage.setTitle("ManageYourself");
                stage.show();
            }else{
                Stage stage = new Stage();
                FXMLLoader loader  = new FXMLLoader(getClass().getResource("Alert.fxml"));
                Parent root = loader.load();
            
                AlertController control = loader.<AlertController>getController();
                control.setStatus("username or password is wrong!");
            
                MoveScreen screen = new MoveScreen(root, stage);
            
                Scene scene = new Scene(root);
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setScene(scene);
                stage.show();
                return ;
            }
        } catch (SQLException ex) {
            System.err.println("Error : "+ex);
        }
        
    }
    
    public void handleForgotPassword(MouseEvent event) throws IOException{

        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("forgotPassword.fxml"));

        Parent root = loader.load();
        ForgotPasswordController control = loader.<ForgotPasswordController>getController();

        MoveScreen screen = new MoveScreen(root, stage);
        
        Scene scene = new Scene(root);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();

    }
    
    public void handleKey(KeyEvent event) throws IOException{
        
        if(event.getCode()==KeyCode.ENTER){
            try {
                if(model.isLogin(user.getText(), pass.getText())){

                    ((Node)event.getSource()).getScene().getWindow().hide();
                    Stage stage = new Stage();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("app1.fxml"));

                    Parent root = loader.load();
                    AppController control = loader.<AppController>getController();
                    control.getUserName(user.getText());

                    Image icon = new Image(getClass().getResourceAsStream("/image/logo128x128.png"));
                    stage.getIcons().add(icon);

                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setWidth(1130);
                    stage.setHeight(630);
                    stage.setResizable(false);
                    stage.setTitle("ManageYourself");
                    stage.show();
                }else{
                    Stage stage = new Stage();
                    FXMLLoader loader  = new FXMLLoader(getClass().getResource("Alert.fxml"));
                    Parent root = loader.load();

                    AlertController control = loader.<AlertController>getController();
                    control.setStatus("username or password is wrong!");

                    MoveScreen screen = new MoveScreen(root, stage);

                    Scene scene = new Scene(root);
                    stage.initStyle(StageStyle.UNDECORATED);
                    stage.setScene(scene);
                    stage.show();
                    return ;
                }
            } catch (SQLException ex) {
                System.err.println("Error : "+ex);
            }
        
        }
        
    }
}

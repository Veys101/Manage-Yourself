package application;

import signup.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AlertController implements Initializable {

    @FXML 
    private Label closelbl;
    @FXML
    private Label statuslbl;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    public void handleClose(MouseEvent event){
        
        Stage stage = (Stage)closelbl.getScene().getWindow();
        stage.close();
        
    }
    
    public void handleOkButton(ActionEvent event){
        
        Stage stage = (Stage)closelbl.getScene().getWindow();
        stage.close();
    }
    
    public void setStatus(String text){
        
        statuslbl.setAlignment(Pos.CENTER);
        statuslbl.setText(text);
        
    }
    
}

package application;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dbconnection.dbConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class loadTaskItemController implements Initializable {

    ObservableList<String> days = FXCollections.observableArrayList("Monday","Tuesday","Wednesday","Thursday",
            "Friday","Saturday","Sunday");
    
    @FXML
    private JFXComboBox<String> dayBox; 
    @FXML
    private Label exitlbl,status;
    @FXML
    private JFXTextField itemName;
    @FXML
    protected Button addButton;
    
    protected ListView<String> mondayList,tuesdayList,wednesdayList,thursdayList,fridayList,saturdayList,sundayList;
    
    private ObservableList<String> list;
    
    protected  String name,user,day="";
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dayBox.setItems(days);
       
    }    
    
    public void closeScreen(MouseEvent event){
        Stage stage = (Stage)exitlbl.getScene().getWindow();
        stage.close();
    }
    
    public void acquireUser(String user){
        
        this.user = user;
        
    }    
    
    public void acquireId(String name){
        this.name=name;
    }
    
    public void acquireListView(ListView<String> mondayList,ListView<String> tuesdayList,ListView<String> wednesdayList,
                                ListView<String> thursdayList,ListView<String> fridayList,ListView<String> saturdayList,
                                ListView<String> sundayList){
       
        this.mondayList=mondayList;
        this.tuesdayList=tuesdayList;
        this.wednesdayList=wednesdayList;
        this.thursdayList=thursdayList;
        this.fridayList=fridayList;
        this.saturdayList=saturdayList;
        this.sundayList=sundayList;
    }
    
    public void handleAddButton(ActionEvent event){

        day = dayBox.getValue();
        String task = itemName.getText();
        String id =name;

        String sql = "INSERT INTO "+day.toLowerCase()+"Task(user,id,task,completenum) VALUES(?,?,?,?)";
       
        try {
            
            Connection conn = dbConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user);
            ps.setString(2, id);
            ps.setString(3, task);
            ps.setInt(4, 0);
            ps.execute();
            conn.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(loadTaskItemController.class.getName()).log(Level.SEVERE, null, ex);
        } 
       
        //Refresh items on day list view,when added item
        RefreshDayListView refreshObj = new RefreshDayListView();
        list=refreshObj.refresh(user, id, day.toLowerCase());

        if(day.toLowerCase().equals("monday")){

            mondayList.setItems(list);

        }else if(day.toLowerCase().equals("tuesday")){

            tuesdayList.setItems(list);

        }else if(day.toLowerCase().equals("wednesday")){

            wednesdayList.setItems(list);

        }else if(day.toLowerCase().equals("thursday")){

            thursdayList.setItems(list);

        }else if(day.toLowerCase().equals("friday")){

            fridayList.setItems(list);

        }else if(day.toLowerCase().equals("saturday")){

            saturdayList.setItems(list);

        }else if(day.toLowerCase().equals("sunday")){

            sundayList.setItems(list);
        }

        status.setText("Added "+task);

    }
    
}

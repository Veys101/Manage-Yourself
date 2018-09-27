package application;

import com.jfoenix.controls.JFXListView;
import dbconnection.dbConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class loadTask {
    
    protected ObservableList<String> list;
    private dbConnection dc;
     
    public ObservableList<String> handleLoadTask(String username){

        dc = new dbConnection();
        try {
            
            Connection conn = dbConnection.getConnection();
            list = FXCollections.observableArrayList();
            ResultSet rs = conn.createStatement().executeQuery("select * from loaddata");
            
            while(rs.next()){
                
                if(username.equals(rs.getString(1)))
                    list.add(rs.getString(2));
            }
            
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return list;
        
    }
       
}

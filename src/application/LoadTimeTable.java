package application;

import dbconnection.dbConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class LoadTimeTable {

    protected ObservableList<String> timeList;
    protected ObservableList<Integer> timeId;
    
    public ObservableList<String> loadTime(String userName,String taskName){
        
        try {
        
            Connection conn=dbConnection.getConnection();
            timeList = FXCollections.observableArrayList();
            timeId = FXCollections.observableArrayList();
            ResultSet set = conn.createStatement().executeQuery("select * from timeTable");
            
            while(set.next()){
                if(set.getString(1).equals(userName) && set.getString(2).equals(taskName)){
                    
                    timeId.add(set.getInt(3));
                    timeList.add(set.getString(4));
                    
                }
            }
            conn.close();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return timeList;            
        
    }
    
    public ObservableList<Integer> getTimeid(){
        
        return timeId;
        
    }
    
}

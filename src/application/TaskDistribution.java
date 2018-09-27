package application;

import dbconnection.dbConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TaskDistribution {
    
    private ObservableList<String> list;
    
    public ObservableList<String> getTask(String sql,String username,String name) throws SQLException{
        
        Connection conn = dbConnection.getConnection();
        list = FXCollections.observableArrayList();
        ResultSet rs = conn.createStatement().executeQuery(sql);
        while(rs.next()){
                
            if(rs.getString(1).equals(username) && rs.getString(2).equals(name)){
                list.add(rs.getString(3));
            }
                
        }
        conn.close();
        
        return list;
    }
    
}

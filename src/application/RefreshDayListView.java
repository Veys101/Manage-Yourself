package application;

import dbconnection.dbConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RefreshDayListView {
    
    private ObservableList<String> list;
    
    public RefreshDayListView(){
        
    }
    
    public ObservableList<String> refresh(String user,String id,String day){
        
        list=FXCollections.observableArrayList();

        try {
            
            String sql = "select * from "+day+"Task";
            Connection conn = dbConnection.getConnection();
            ResultSet rs = conn.createStatement().executeQuery(sql);
            
            while(rs.next()){
                if(user.equals(rs.getString(1)) && id.equals(rs.getString(2))){
                    list.add(rs.getString(3));
                }
            }
            conn.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(RefreshDayListView.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return list;
        
    }
    
}

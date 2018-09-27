package application;

import dbconnection.dbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class deleteSql {
    
    public String name;
    public String id;
    public String day;
    public String user;
    
    public deleteSql(){
        
    }
    
    public deleteSql(String user,String id,String name,String day){
        this.user=user;
        this.id = id;
        this.name = name;
        this.day = day;
    }
    
    public void deleteSqlStatement(){
        
        String sql ="DELETE FROM "+day+"Task WHERE user =? and id = ? and task = ? ";
        try {
            
            Connection conn = dbConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user);
            ps.setString(2, id);
            ps.setString(3, name);
            ps.execute();
            conn.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(loadTaskItemController.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
    }
    
    public void deleteTaskandItem(String user,String id,String day){
        
        String sql ="DELETE FROM "+day+"Task WHERE user =? and id = ?";
        try {
            
            Connection conn = dbConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user);
            ps.setString(2, id);
            ps.execute();
            conn.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(loadTaskItemController.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        
    }
    
}

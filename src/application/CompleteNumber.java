package application;

import dbconnection.dbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CompleteNumber {
    
    protected int number;
    
    public void completeNumber(String sqlName,String user,String id,String task){
        
        try {
            
            String sql = "Select * from "+sqlName;
            Connection conn = dbConnection.getConnection();
            ResultSet rs = conn.createStatement().executeQuery(sql);
            
            while(rs.next()){
                
                if(rs.getString(1).equals(user) && rs.getString(2).equals(id) && rs.getString(3).equals(task)){
                    number = rs.getInt(4);
                }
                
            }
            conn.close();

            sql = "UPDATE "+sqlName+" SET completenum=? WHERE user= ? and id=? and task=?";
            conn = dbConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            number++;
            ps.setInt(1, number);
            ps.setString(2, user);
            ps.setString(3, id);
            ps.setString(4, task);

            ps.execute();
            conn.close();
            
            
        } catch (SQLException ex) {
            Logger.getLogger(CompleteNumber.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}

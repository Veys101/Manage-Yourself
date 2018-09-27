package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginModel {
    
    Connection connection;
    
    public LoginModel(){
        
        this.connection=dbconnection.dbConnection.getConnection();
        if(this.connection==null){
            System.exit(0);
        }
    }
    
    public boolean isLogin(String user,String pass) throws SQLException{
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "select * from users where username = ? and password = ?";
        try {
            
            ps = this.connection.prepareStatement(sql);
            ps.setString(1,user);
            ps.setString(2,pass);
            rs = ps.executeQuery();
            
            if(rs.next()){
                return true;
            }
            
            return false;
            
        } catch (SQLException ex) {
            System.err.println("Error : "+ ex);
            return false;
        }finally{
            ps.close();
            rs.close();
            this.connection.close();
        }
    }

}

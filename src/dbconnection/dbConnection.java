package dbconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbConnection {

    private static final String SQCONN = "jdbc:sqlite:manageyourself.db";
    
    public static Connection getConnection(){
        
        try{
        
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection(SQCONN);
            
        } catch (ClassNotFoundException ex) {
            System.err.println("Error : "+ex);
        }catch(SQLException ex){
            System.err.println("Error : "+ex);
        }
        
        return null;
        
    }
    
}

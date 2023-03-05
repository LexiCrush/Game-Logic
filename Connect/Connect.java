package Connect;
import java.sql.*;

public class Connect {  
    /** 
     * Connect to a database 
     * @param dbPath the path to the database file
     * @throws ClassNotFoundException
     */  
    public static void connect(String dbPath) throws ClassNotFoundException {  
        Connection conn = null;  
        try {  
            Class.forName("org.sqlite.JDBC");
            // db parameters  
            String url = "jdbc:sqlite:" + dbPath;  
            // create a connection to the database  
            conn = DriverManager.getConnection(url);  
            System.out.println("Connection to SQLite has been established.");  
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        } finally {  
            try {  
                if (conn != null) {  
                    conn.close();  
                }  
            } catch (SQLException ex) {  
                System.out.println(ex.getMessage());  
            }  
        }  
    }  
    /** 
     * @param args the command line arguments 
     * @throws ClassNotFoundException
     */  
    public static void main(String[] args) throws ClassNotFoundException {  
        connect("NounBankSQlite.db");  
    }  
}  
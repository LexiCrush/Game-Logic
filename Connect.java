import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.SQLException;  

public class Connect {  
    /** 
    * Connect to a sample database 
     * @throws ClassNotFoundException
    */  
   public static void connect() throws ClassNotFoundException {  
       Connection conn = null;  
       try {  
           Class.forName("org.sqlite.JDBC");
           // db parameters  
           String url = "jdbc:sqlite:/Users/nishatahmed/Documents/GitHub/Game-Logic/NounBankSQlite.db";  
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
       connect();  
   }  
}  
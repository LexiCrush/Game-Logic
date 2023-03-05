package Connect;
import java.sql.*;

public class Connect {  
    /** 
     * Connect to a database 
     * @param dbPath the path to the database file
     * @return 
     * @throws ClassNotFoundException
     */  
    public static Connection connect(String dbPath) throws ClassNotFoundException {  
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
        } 
        return conn;  
    }  
    public static void insert(String userValue, String passValue) throws ClassNotFoundException{
        String sql = "INSERT INTO users(username, user_password) VALUES(?,?)";
        String path = "db/UserProfile.db";
        Connection conn = connect(path);
        try (conn;
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userValue);
            pstmt.setString(2, passValue);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } try {  
            if (conn != null) {  
                conn.close();  
            }  
        } catch (SQLException ex) {  
            System.out.println(ex.getMessage());  
        }  
    }
    /** 
     * @param args the command line arguments 
     * @throws ClassNotFoundException
     */  
    public static void main(String[] args) throws ClassNotFoundException {  
        connect("db/NounBankSQlite.db");
    }  

}  
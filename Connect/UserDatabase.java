package Connect;

import java.sql.*;

public class UserDatabase {

    //Connect to sample database
    public static void createNewDatabase(String dbPath) throws SQLException {

        String url = "jdbc:sqlite:" + dbPath;
        Connection conn = DriverManager.getConnection(url);

        try (conn) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
                try (Statement stmt = conn.createStatement()) {
                    stmt.execute("DROP TABLE IF EXISTS User;");
                    stmt.execute("CREATE TABLE User (user_id bigint primary key, username varchar(255) not null, user_password varchar(255) not null);");
                    System.out.println("Successfully created table");
                } catch (SQLException e) {
                    System.out.println("Couldn't create table " + e.getMessage());
                }
                
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } 
    }

    //create user info database
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        createNewDatabase("UserProfile.db");
    }
}


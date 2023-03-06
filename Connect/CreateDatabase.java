package Connect;

import java.sql.*;

public class CreateDatabase {

    //Connect to sample database
    public static void createNewDatabase(String dbPath, String sql) throws SQLException {

        String url = "jdbc:sqlite:" + dbPath;
        Connection conn = DriverManager.getConnection(url);

        try (conn) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
                try (Statement stmt = conn.createStatement()) {
                    stmt.execute("DROP TABLE IF EXISTS users;");
                    stmt.execute(sql);
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
        String sql_user = "CREATE TABLE users (uid integer not null primary key, username varchar(50) not null unique, user_password varchar(255) not null);";
        createNewDatabase("db/UserProfile.db", sql_user);
        String sql_score = "CREATE TABLE scores (player_id integer not null primary key, uid integer not null, round_id integer not null, score integer not null, FOREIGN KEY (uid) REFERENCES users(uid));";
        createNewDatabase("db/ScoreBoard.db" , sql_score);
    }
}


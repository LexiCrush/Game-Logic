package Logic;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QuestionGenerator {
    public static Connection connect() {
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:db/NounBankSQlite.db";
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    public static void main(String[] args) { 
        Connection conn = connect(); // connect to the database
        // select all available tables in NounBankSQlite.db
        String[] question_filters = {"Begins With The Letter", "Ends With The Letter", "Any"};
        String sql = "SELECT name FROM sqlite_master WHERE type='table'"; 
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            String[] tables = new String[100]; // store the table names in an array
            int i = 0;
            while (rs.next()) {
                tables[i] = rs.getString("name");
                i++;
            }
            for (int j = 0; j < i; j++) { // print the table names
                System.out.println(tables[j]);
            }
            int random = (int) (Math.random() * i); // choose a random table from the array
            String table = tables[random];
            System.out.println("Randomly Chosen Table: " + table); // print the randomly chosen table name

            String NOUN = ""; // sets NOUN attribute to specific string based off of the randomly chosen table
            if (table.equals("all_usa_states")) {
                NOUN = "A State in the USA";
            } else if (table.equals("all_world_countries")) {
                NOUN = "A Country in the World";
            } else if (table.equals("all_elements")) {
                NOUN = "An Element";
            } else if (table.equals("many_animals")) {
                NOUN = "An Animal";
            } else if (table.equals("many_greek_gods")) {
                NOUN = "A Greek God";
            } else if (table.equals("many_roman_gods")) {
                NOUN = "A Roman God";
            } else if (table.equals("many_cosmetic_items")) {
                NOUN = "A Cosmetic Item";
            } else if (table.equals("many_office_supplies")) {
                NOUN = "An Office Supply";
            } else if (table.equals("many_olympic_sports")) {
                NOUN = "An Olympic Sport";
            } else if (table.equals("many_nfl_teams")) {
                NOUN = "An NFL Team";
            } else if (table.equals("many_marvel")) {
                NOUN = "A Marvel Character";
            } else if (table.equals("many_mythical_creatures")) {
                NOUN = "A Mythical Creature";
            } else if (table.equals("many_weather_conditions")) {
                NOUN = "A Weather Condition";
            } else if (table.equals("many_vegetables")) {
                NOUN = "A Vegetable";
            } else if (table.equals("many_professions")) {
                NOUN = "A Profession";
            } else if (table.equals("many_pets")) {
                NOUN = "A Pet";
            } else if (table.equals("many_landmarks")) {
                NOUN = "A Landmark";
            } else if (table.equals("many_luxury_brands")) {
                NOUN = "A Luxury Brand";
            } else if (table.equals("many_holidays")) {
                NOUN = "A Holiday";
            } else if (table.equals("many_gemstones")) {
                NOUN = "A Gemstone";
            } else if (table.equals("many_fruits")) {
                NOUN = "A Fruit";
            } else if (table.equals("many_flowers_plants")) {
                NOUN = "A Flower or Plant";
            } else if (table.equals("many_empires")) {
                NOUN = "An Empire";
            } else if (table.equals("many_youtubers")) {
                NOUN = "A Youtuber";
            } else {
                System.out.println("Noun not set for table " + table);
            }
            System.out.println("Noun: " + NOUN); // print the chosen noun and its attribute

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

}

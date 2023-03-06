package Logic;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
// import java.util.Scanner;


public class QuestionGenerator {

    private String[] questionFilters = {"Begins With The Letter", "Ends With The Letter", "Any"};

    public Connection conn = connect();
    public String chosenTable;
    public String chosenNoun;
    public String chosenFilter;
    public static String assembledPrompt;

    private ArrayList<String> firstLetters;
    private ArrayList<String> lastLetters;
    public String randomLetter;

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

    public void getRandomNbTable() {
        String sql = "SELECT name FROM sqlite_master WHERE type='table'"; // select all available tables in NounBankSQlite.db
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            String[] tables = new String[100]; // store the table names in an array
            int i = 0;
            while (rs.next()) {
                tables[i] = rs.getString("name");
                i++;
            }
            
            // for (int j = 0; j < i; j++) { // print the table names
            //     System.out.println(tables[j]);
            // }
            
            int random = (int) (Math.random() * i); // choose a random table from the array
            chosenTable = tables[random];
            // System.out.println("Randomly Chosen Table: " + chosenTable); // print the randomly chosen table name
            // conn.close();
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void getReadableNounFromTableName() {
        String table = chosenTable; // sets table to the randomly chosen table
        String NOUN = "A Noun"; // sets NOUN attribute to specific string based off of the randomly chosen table
            if (table.equals("all_usa_states")) {
                NOUN = "A State in the USA";
            } else if (table.equals("many_english_words")) {
                NOUN = "A Word in the English Language";
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
            }
            chosenNoun = NOUN;
            // System.out.println("Noun: " + NOUN); // print the chosen noun and its attribute
    }

    public void getFilter() {
        
        Random rand = new Random();
        chosenFilter = questionFilters[rand.nextInt(questionFilters.length)];
       // chosenFilter = "Any";

        // System.out.println("Chosen Filter: " + chosenFilter);
        // make a list of all the first letters of the nouns in the chosen table
        if (chosenFilter.equals("Any")) {
            // pass
        }

        if (chosenFilter.equals("Begins With The Letter")) {
            try {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT SUBSTR(" + chosenTable + ", 1, 1) FROM " + chosenTable);
                firstLetters = new ArrayList<String>();
                while (rs.next()) {
                    String letter = rs.getString(1);
                    firstLetters.add(letter);
                }
                
                // conn.close();
                // choose a random letter from the list of first letters
                int randomIndex = rand.nextInt(firstLetters.size());
                randomLetter = firstLetters.get(randomIndex);
                // System.out.println(randomLetter);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        if (chosenFilter.equals("Ends With The Letter")) {
            try {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT SUBSTR(" + chosenTable + ", -1, 1) FROM " + chosenTable);
                lastLetters = new ArrayList<String>();
                while (rs.next()) {
                    String letter = rs.getString(1);
                    lastLetters.add(letter);
                }
                // conn.close();
                // choose a random letter from the list of first letters
                int randomIndex = rand.nextInt(lastLetters.size());
                randomLetter = lastLetters.get(randomIndex);
                // System.out.println(randomLetter);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void checkAnswer(String potentialAnswer) {
        
        if (chosenFilter.equals("Any")) {
            
            try {
                Statement stmt = conn.createStatement(); 
                ResultSet rs = stmt.executeQuery("SELECT * FROM " + chosenTable + " WHERE LOWER(" + chosenTable + ") = LOWER('" + potentialAnswer + "')");
                // // include varittions of the answer in the database, for ex if it ends with an s or not it shoud still be correct
                // ResultSet rs2 = stmt.executeQuery("SELECT * FROM " + chosenTable + " WHERE LOWER(" + chosenTable + ") = LOWER('" + potentialAnswer + "s')");
                // ResultSet rs3 = stmt.executeQuery("SELECT * FROM " + chosenTable + " WHERE LOWER(" + chosenTable + ") = LOWER('" + potentialAnswer + "es')");
                // ResultSet rs4 = stmt.executeQuery("SELECT * FROM " + chosenTable + " WHERE LOWER(" + chosenTable + ") = LOWER('" + potentialAnswer + "ies')");
                // ResultSet rs5 = stmt.executeQuery("SELECT * FROM " + chosenTable + " WHERE LOWER(" + chosenTable + ") = LOWER('" + potentialAnswer + "Day')");
                // // a variation that starts with "the" or "a" should also be correct
                // ResultSet rs6 = stmt.executeQuery("SELECT * FROM " + chosenTable + " WHERE LOWER(" + chosenTable + ") = LOWER('the " + potentialAnswer + "')");
                // ResultSet rs7 = stmt.executeQuery("SELECT * FROM " + chosenTable + " WHERE LOWER(" + chosenTable + ") = LOWER('The " + potentialAnswer + "')");
                // ResultSet rs8 = stmt.executeQuery("SELECT * FROM " + chosenTable + " WHERE LOWER(" + chosenTable + ") = LOWER('a " + potentialAnswer + "')");
                // ResultSet rs9 = stmt.executeQuery("SELECT * FROM " + chosenTable + " WHERE LOWER(" + chosenTable + ") = LOWER('A " + potentialAnswer + "')");
                // ResultSet rs10 = stmt.executeQuery("SELECT * FROM " + chosenTable + " WHERE LOWER(" + chosenTable + ") = LOWER('an " + potentialAnswer + "')");
                // ResultSet rs11 = stmt.executeQuery("SELECT * FROM " + chosenTable + " WHERE LOWER(" + chosenTable + ") = LOWER('An " + potentialAnswer + "')");

                if (rs.next()) { 
                    System.out.println("\nCORRECT! " + potentialAnswer+ " is " + chosenNoun.toLowerCase() + " in our database.\n");
                } else {
                    System.out.println("\nINCORRECT. The answer was not found in the database.\n");
                }

                // conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        if (chosenFilter.equals("Begins With The Letter")) {
            if (Character.toString(potentialAnswer.charAt(0)).equalsIgnoreCase(Character.toString(this.randomLetter.charAt(0)))) {  
                try {
                    Statement stmt = conn.createStatement(); 
                    ResultSet rs = stmt.executeQuery("SELECT * FROM " + chosenTable + " WHERE LOWER(" + chosenTable + ") = LOWER('" + potentialAnswer + "')");
                    if (rs.next()) { // checks each row
                        System.out.println("\nCORRECT! " + potentialAnswer+ " is " + chosenNoun.toLowerCase() + " in our database.\n");
                    } else {
                        System.out.println("\nINCORRECT! The answer was not found in the database.\n");
                    }
                    // connect().close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                System.out.println("\nINCORRECT! That does not begin with the letter " + randomLetter + ".\n");
            }
        }
        if (chosenFilter.equals("Ends With The Letter")) {
            if (Character.toString(potentialAnswer.charAt(potentialAnswer.length() - 1)).equalsIgnoreCase(Character.toString(this.randomLetter.charAt(this.randomLetter.length() - 1)))) {             
                try {
                    Statement stmt = conn.createStatement(); 
                    ResultSet rs = stmt.executeQuery("SELECT * FROM " + chosenTable + " WHERE LOWER(" + chosenTable + ") = LOWER('" + potentialAnswer + "')");
                    // ResultSet rs = stmt.executeQuery("SELECT * FROM " + chosenTable + " WHERE LOWER(" + chosenTable + ") = LOWER('%" + potentialAnswer + "%')");
                    if (rs.next()) { // checks each row
                        System.out.println("\nCORRECT! " + potentialAnswer+ " is " + chosenNoun.toLowerCase() + " in our database.\n");
                    } else {
                        System.out.println("\nINCORRECT! The answer was not found in the database.\n");
                    }
                   // connect().close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                System.out.println("That does not end with the letter " + randomLetter);
            }
        }
    }


    public static void main(String[] args) {
            // Connection conn = connect();
            QuestionGenerator game = new QuestionGenerator();
            System.out.println("\n...LEXICRUSH...\nLocal Game Demo\n");

            game.getRandomNbTable();
            // System.out.println(game.chosenTable);

            game.getReadableNounFromTableName();

            game.getFilter();

            if (game.chosenFilter == ("Any")) {
                assembledPrompt = "Name " + game.chosenNoun + ": ";
            } 
            if (game.chosenFilter == ("Begins With The Letter")) {
                assembledPrompt = "Name " + game.chosenNoun + " that starts with " + game.randomLetter + ": ";
            } 
            if (game.chosenFilter == ("Ends With The Letter")) {
                assembledPrompt = "Name " + game.chosenNoun + " that ends with " + game.randomLetter + ": ";
            } 

            try (Scanner scanner = new Scanner(System.in)) {
                System.out.print(assembledPrompt);
                String potentialAnswer = scanner.nextLine(); // stores answer from CL
                
                game.checkAnswer(potentialAnswer);
            }
    }
}


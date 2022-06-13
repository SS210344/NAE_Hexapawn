package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ReadTable {
    public static void SQLTest() {

//This gets the directory for this project, appends the name of the database file to it and sets it as the database location

        String DatabaseLocation = System.getProperty("user.dir") + "\\NEA_HexaPawn.accdb";

        try {

//sets up a connection to the database using the database location

            Connection con = DriverManager.getConnection("jdbc:ucanaccess://" + DatabaseLocation, "", "");

//forms a statement which is used to format the results from your SQL

            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

//This bit is the SQL, if using strings to search, put quote marks around the strings - 'julie' not "julie"

//No other fields need quote marks, only string fields

            String sql = "SELECT * FROM WinRecord";

//executes the SQL query and returns the results as a ResultSet, which is like an arraylist of results

//If using an INSERT, UPDATE, DELETE use stmt.executeUpdate(query) instead

            ResultSet rs = stmt.executeQuery(sql);

//While there is a next record, loop until the end of the record set. Change to IF instead of WHILE when expecting 1 result

            while(rs.next()) {

//Get the individual fields using the field names from the database, print out the data

                System.out.println("gameType "+ rs.getString("GameType") );

                System.out.println("Player Wins:" +rs.getInt("PlayerWins"));

                System.out.println("AI wins" + rs.getDouble("AiWins"));

            }

//Housekeeping, close your result sets and connections when finished otherwise expect database deadlocks

            rs.close();

            con.close();

        } catch (Exception e) {

            System.out.println("Error in the SQL class: " + e);

        }

    }
}

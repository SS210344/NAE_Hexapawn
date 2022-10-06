package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class WinRecord {
    public static void SeeWinRecord(String gameTypeToGet) {

        String DatabaseLocation = System.getProperty("user.dir") + "\\NEA_HexaPawn.accdb";

        try {
            Connection con = DriverManager.getConnection("jdbc:ucanaccess://" + DatabaseLocation, "", "");
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql = "SELECT * FROM WinRecord";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String GameType = rs.getString("GameType");
                if (GameType.equals(gameTypeToGet)) {
                    System.out.println("Play played as : " + GameType.substring(0,5));
                    System.out.println("Board size : " + GameType.substring(5,8));
                    System.out.println("Player Wins : " + rs.getInt("PlayerWins"));
                    System.out.println("AI wins : " + rs.getInt("AiWins"));
                    return;
                }

            }
            System.out.println("record not found");
            rs.close();
            con.close();
        } catch (Exception e) {
            System.out.println("Error in the SQL class SeeWinRecord: " + e);
        }
    }

    public static String GetGameType() {
        int[] BoardSize = boardCreation.getBoardSize();
        String GameType = PlayGame.SelectColourPlayWillPlayAS();
        GameType += BoardSize[0] + "x" + BoardSize[1];

        return GameType;

    }

    public static void UpdateRecords(String[] Output) {
        String GameTypePlayed = Output[1]+Output[3];
        String DatabaseLocation = System.getProperty("user.dir") + "\\NEA_HexaPawn.accdb";


        try {
            Connection con = DriverManager.getConnection("jdbc:ucanaccess://" + DatabaseLocation, "", "");
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql = "SELECT * FROM WinRecord";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String GameType = rs.getString("GameType");
                if (GameType.equals(GameTypePlayed)){
                    //see if player or AI won
                    if(Output[0].equals(Output[1])){
                        //player won
                        rs.close();
                        con.close();
                        UpdatePlayerRecord(GameTypePlayed);

                        return;
                    } else{
                        //AI won
                        rs.close();
                        con.close();
                        UpdateAIRecord(GameTypePlayed);

                        return;
                    }
                }
            }
            rs.close();
            con.close();
            addNewRecord(GameTypePlayed);

            //UpdateRecords(Output); add when inset works
            System.out.println("record not found");
        } catch (Exception e) {
            System.out.println("Error in the SQL class SeeWinRecord: " + e);
        }
    }
    private static void UpdatePlayerRecord (String GameTypePlayed){
        String DatabaseLocation = System.getProperty("user.dir") + "\\NEA_HexaPawn.accdb";

        try {
            Connection con = DriverManager.getConnection("jdbc:ucanaccess://" + DatabaseLocation, "", "");
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql = "UPDATE WinRecord \n";
            sql += "SET PlayerWins = PlayerWins +1 \n";
            sql += "WHERE GameType = '"+GameTypePlayed+"';";
            stmt.executeUpdate(sql);
            con.close();
        } catch (Exception e) {
            System.out.println("Error in the SQL class UpdatePlayerRecord: " + e);
        }

    }
    private static void UpdateAIRecord (String GameTypePlayed){
        String DatabaseLocation = System.getProperty("user.dir") + "\\NEA_HexaPawn.accdb";

        try {
            Connection con = DriverManager.getConnection("jdbc:ucanaccess://" + DatabaseLocation, "", "");
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql = "UPDATE WinRecord \n";
            sql += "SET AiWins = AiWins +1 \n";
            sql += "WHERE GameType = '"+GameTypePlayed+"';";
            stmt.executeUpdate(sql);
            con.close();
        } catch (Exception e) {
            System.out.println("Error in the SQL class UpdateAIRecord: " + e);
        }

    }
    private static void addNewRecord(String GameTypePlayed){
        String DatabaseLocation = System.getProperty("user.dir") + "\\NEA_HexaPawn.accdb";


        try {
            Connection con = DriverManager.getConnection("jdbc:ucanaccess://" + DatabaseLocation, "", "");
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql = "INSERT INTO WinRecord(GameType,PlayerWins,AiWins) \n";
            sql += "VALUES('"+GameTypePlayed+"','0','0');";
            stmt.executeUpdate(sql);

            con.close();
        } catch (Exception e) {
            System.out.println("Error in the SQL class addNewRecord: " + e);
        }
    }
}





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
                    System.out.println("Board size : " + GameType.substring(0, 3));
                    System.out.println("Play played as : " + GameType.substring(3));
                    System.out.println("Player Wins : " + rs.getInt("PlayerWins"));
                    System.out.println("AI wins : " + rs.getInt("AiWins"));
                    break;
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
        String GameType = BoardSize[0] + "x" + BoardSize[1];
        GameType += PlayGame.SelectColourPlayWillPlayAS();
        return GameType;

    }

    public static void UpdateRecords(String[] Output) {
        String GameTypePlayed = Output[1]+Output[3];
        System.out.println(GameTypePlayed);
        String DatabaseLocation = System.getProperty("user.dir") + "\\NEA_HexaPawn.accdb";


        try {
            Connection con = DriverManager.getConnection("jdbc:ucanaccess://" + DatabaseLocation, "", "");
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql = "SELECT * FROM WinRecord";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String GameType = rs.getString("GameType");
                System.out.println(GameType);
                if (GameType.equals(GameTypePlayed)){
                    System.out.println("record found");
                    //see if player or AI won
                    if(Output[0].equals(Output[1])){
                        //player won
                        UpdatePlayerRecord(GameTypePlayed);
                        rs.close();
                        con.close();
                        return;
                    } else{
                        //AI won
                        UpdateAIRecord(GameTypePlayed);
                        rs.close();
                        con.close();
                        return;
                    }
                }
            }
            addNewRecord(GameTypePlayed);
            rs.close();
            con.close();
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
        System.out.println("trying to add a record");

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





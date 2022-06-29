package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class WinRecord {
    public static void SeeWinRecord(String gameTypeToGet){

        String DatabaseLocation = System.getProperty("user.dir") + "\\NEA_HexaPawn.accdb";

        try {
            Connection con = DriverManager.getConnection("jdbc:ucanaccess://" + DatabaseLocation, "", "");
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql = "SELECT * FROM WinRecord";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {
                String GameType = rs.getString("GameType");
                if(GameType.equals(gameTypeToGet)){
                    System.out.println("Board size : "+ GameType.substring(0,3) );
                    System.out.println("Play played as : "+GameType.substring(3) );
                    System.out.println("Player Wins : " +rs.getInt("PlayerWins"));
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
    public static String GetGameType(){
        int[] BoardSize = boardCreation.getBoardSize();
        String GameType =BoardSize[0]+"x"+BoardSize[1];
        GameType+= PlayGame.SelectColourPlayWillPlayAS();
        return GameType;

    }
}



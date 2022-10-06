package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class UpdateAI {
    public static void DisableLosingMove(String[] output){
        String Winner = output[0];
        String ColourOfPlayer = output[1];
        String AiLastMove = output[2];
        String BoardSize = output[3];
        String BoardFennelNumber = output[4];
        //if AI lost remove consider in link table for move code and board
        if((Winner.equals(ColourOfPlayer))){
            UpdateTableConsider(AiLastMove,BoardSize,BoardFennelNumber);

        }

    }
    private static void UpdateTableConsider (String AiLastMove,String BoardSize, String BoardFennelNumber){

        String DatabaseLocation = System.getProperty("user.dir") + "\\NEA_HexaPawn.accdb";
        try {
            Connection con = DriverManager.getConnection("jdbc:ucanaccess://" + DatabaseLocation, "", "");
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            String sql = "UPDATE Link"+BoardSize+"\n";
            sql =sql+ "SET Considered = false\n";
            sql = sql+"Where MoveID ="+AddMovesToTable.GetMoveID(BoardSize,AiLastMove)+ " And BoardID ="+AddMovesToTable.GetBoardID(BoardSize,Integer.parseInt(BoardFennelNumber));


            int i = stmt.executeUpdate(sql);

            con.close();

        } catch (Exception e) {
            System.out.println("Error in the SQL class: UpdateTableConsider " + e);
        }

    }
}

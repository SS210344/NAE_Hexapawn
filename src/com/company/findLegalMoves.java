package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class findLegalMoves {
    public static void findLegalMovesSet(pawn[][]board,String ColourOfPlayer,String ColourOfCurrentPlayer){
        //see if tables exist
        String TableBaseName = board.length +"x" +board[0].length;
        Boolean doesBoardSetExist = DoesTableExist("Link"+TableBaseName);



        //make board fennel string
        String boardState = "";
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                pawn temp = board[i][j];
                if (temp.getColour().equals("blank")){
                    boardState=boardState+"00";
                }
                if (temp.getColour().equals("black")){
                    boardState=boardState+"10";
                }
                if (temp.getColour().equals("white")){
                    boardState=boardState+"01";


                }
            }
        }
        if (doesBoardSetExist){
            //the tables exist


        }
    }



    public static Boolean DoesTableExist(String TableName){
        String DatabaseLocation = System.getProperty("user.dir") + "\\NEA_HexaPawn.accdb";
        try {
            Connection con = DriverManager.getConnection("jdbc:ucanaccess://" + DatabaseLocation,"","");
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql = "SELECT * FROM "+TableName+" ;";
            ResultSet rs =  stmt.executeQuery(sql);
            if(rs.next()) {
                con.close();
                rs.close();
                return true;
            }
            else{
                return true;
            }

        } catch (Exception e) {
            System.out.println("Error in the SQL class : DoesTableExist " + e);
            return false;
        }

    }



}

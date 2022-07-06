package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class findLegalMoves {
    public static void findLegalMovesSet(pawn[][]board,String ColourOfPlayer,String ColourOfCurrentPlayer){
        ArrayList<String> SetOfLegalMoveCodes =new ArrayList<>();
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
            if(isBoardInDataBase(TableBaseName,binaryToInteger(boardState))){
                //board is in table

            }

        }
        else{
            //who is playing
            if(ColourOfPlayer.equals("White")){
                SetOfLegalMoveCodes=FindMoveOnBoard.findWhiteMove(board);
            }
            else{
                SetOfLegalMoveCodes= FindMoveOnBoard.findBlackMove(board);
            }
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
    private static boolean isBoardInDataBase(String baseTableName,int BoardStateNumber) {

        String DatabaseLocation = System.getProperty("user.dir") + "\\NEA_HexaPawn.accdb";
        Boolean exist = false;
        try {
            Connection con = DriverManager.getConnection("jdbc:ucanaccess://" + DatabaseLocation, "", "");
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql = "SELECT * FROM BoardState"+ baseTableName +";";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                if(rs.getInt("BoardFennelString")== BoardStateNumber){
                    rs.close();
                    con.close();
                    return true;
                }

            }
            return false;
        } catch (Exception e) {
            System.out.println("Error in the SQL class: isBoardInDataBase " + e);
            return false;
        }

    }
    private static int binaryToInteger(String binary) {
        char[] numbers = binary.toCharArray();
        int result = 0;
        for (int i = numbers.length - 1; i >= 0; i--) {
            if (numbers[i] == '1') {
                result += Math.pow(2, (numbers.length - i - 1));
            }
        }
        return result;
    }
    public static ArrayList<String> GetMoveCodesFromTable(String BaseTableName,int BoardStateNumber){
        ArrayList<String> SetOfLegalMoveCodes =new ArrayList<>();
        String DatabaseLocation = System.getProperty("user.dir") + "\\NEA_HexaPawn.accdb";
        Boolean exist = false;
        try {
            Connection con = DriverManager.getConnection("jdbc:ucanaccess://" + DatabaseLocation, "", "");
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql = "SELECT * FROM BoardState"+ BaseTableName +";";
            ResultSet rs = stmt.executeQuery(sql);

            //get the boardID
            int boardID = 0;
            while (rs.next()) {
                if(rs.getInt("BoardFennelString")== BoardStateNumber){
                    boardID = rs.getInt("BoardID");
                    rs.close();
                    break;
                }
            }

            sql = "SELECT * FROM Link"+ BaseTableName +";";
            rs = stmt.executeQuery(sql);
            //get the MovesID
            ArrayList<Integer>MoveIDList = new ArrayList();
            while (rs.next()) {
                if(rs.getInt("BoardID")== boardID){
                    MoveIDList.add(rs.getInt("MoveID"));
                }
            }
            rs.close();

            //gets the MoveCodes
            sql = "SELECT * FROM Moves"+ BaseTableName +";";
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                for (int i = 0; i < MoveIDList.size(); i++) {
                    if(rs.getInt("MoveID")== MoveIDList.get(i)){
                        SetOfLegalMoveCodes.add(rs.getString("MoveCode"));
                    }
                }

            }
            rs.close();
            con.close();
            return SetOfLegalMoveCodes;


        } catch (Exception e) {
            System.out.println("Error in the SQL class: GetMoveCodesFromTable " + e);
        }
        return SetOfLegalMoveCodes;
    }



}

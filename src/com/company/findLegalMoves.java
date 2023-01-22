package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class findLegalMoves {
    public static ArrayList<String> findLegalMovesSet(pawn[][]board,String ColourOfPlayer,String ColourOfCurrentPlayer){
        ArrayList<String> SetOfLegalMoveCodes =new ArrayList<>();
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
        //see if tables exist
        String TableBaseName = board.length +"x" +board[0].length;
        if (ReadTable.SeeIfTableExist(TableBaseName)){
            if(isBoardInDataBase(TableBaseName,Hashing.hashOperation(boardState))){
                //board is in table
                GetLegalMoves(TableBaseName,Hashing.hashOperation(boardState),ColourOfPlayer,ColourOfCurrentPlayer);
            }
        }else{
            CreateTable.CreateTableSet(TableBaseName);
        }


        //who is playing
        if(ColourOfCurrentPlayer.equals("White")){
            SetOfLegalMoveCodes=FindMoveOnBoard.findWhiteMove(board);
            AddMovesToTable.AddMoves(SetOfLegalMoveCodes,board);
            return SetOfLegalMoveCodes;
        }
        else{
            SetOfLegalMoveCodes= FindMoveOnBoard.findBlackMove(board);
            AddMovesToTable.AddMoves(SetOfLegalMoveCodes,board);
            return SetOfLegalMoveCodes;
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
            rs.close();
            con.close();
            return false;
        } catch (Exception e) {
            System.out.println("Error in the SQL class: isBoardInDataBase " + e);
            return false;
        }

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



    private static ArrayList<String> GetLegalMoves(String baseTableName,int BoardStateNumber,String ColourOfPlayer,String ColourOfCurrentPlayer) {

        String DatabaseLocation = System.getProperty("user.dir") + "\\NEA_HexaPawn.accdb";
        int BoardID =0;
        try {

            Connection con = DriverManager.getConnection("jdbc:ucanaccess://" + DatabaseLocation, "", "");
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql = "SELECT * FROM BoardState"+ baseTableName +";";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                if(rs.getInt("BoardFennelString")== BoardStateNumber){
                    BoardID=rs.getInt("BoardID");
                    rs.close();
                    con.close();
                    break;
                }

            }


        } catch (Exception e) {
            System.out.println("Error in the SQL class: GetLegalMoves " + e);
        }
        ArrayList<Integer> moveIDs = new ArrayList<>();

        try {

            Connection con = DriverManager.getConnection("jdbc:ucanaccess://" + DatabaseLocation, "", "");
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql = "SELECT * FROM Link"+ baseTableName +";";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                if(rs.getInt("BoardID")== BoardID){
                    moveIDs.add(rs.getInt("MoveID"));

                }
            }
            rs.close();
            con.close();
        } catch (Exception e) {
            System.out.println("Error in the SQL class: GetLegalMoves " + e);

        }
        ArrayList<String> legalMovesCodes = new ArrayList<>();
        try {


            Connection con = DriverManager.getConnection("jdbc:ucanaccess://" + DatabaseLocation, "", "");
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql = "SELECT * FROM Moves"+ baseTableName +",Link"+ baseTableName +";";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                for (int i = 0; i < moveIDs.size(); i++) {
                    if(rs.getInt("MoveID")== moveIDs.get(i)){
                        if (!ColourOfPlayer.equals(ColourOfCurrentPlayer)){
                            if (rs.getBoolean("Considered")){
                                legalMovesCodes.add(rs.getString("MoveCode"));
                            }
                        }
                        else {
                            legalMovesCodes.add(rs.getString("MoveCode"));
                        }

                    }
                }
            }
            rs.close();
            con.close();
            return legalMovesCodes;
        } catch (Exception e) {
            System.out.println("Error in the SQL class: GetLegalMoves " + e);
            return legalMovesCodes;
        }



    }



}

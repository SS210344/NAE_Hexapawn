package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class AddMovesToTable {
    public static void AddMoves(ArrayList<String>ListOfLegalMoveCodes,pawn[][]board){
        String TableBaseName = board.length +"x" +board[0].length;

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
        int BoardStateNumber = binaryToInteger(boardState);
        //see if board is in the DataBase
        if (!isBoardInDataBase(TableBaseName,BoardStateNumber)){
            //add board to database
            AddBoardToDataBase(TableBaseName,BoardStateNumber);
        }

        //get list of move already in dataBAse
        System.out.println(isBoardInDataBase(TableBaseName,BoardStateNumber));
        ArrayList<String>ListOfMoveCodesInDataBase = GetListOFMoveCodesInDataBase(TableBaseName);
        // remove move code already in database
        for (int i = 0; i < ListOfMoveCodesInDataBase.size(); i++) {
            System.out.println(ListOfLegalMoveCodes.get(i));
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

    private static boolean isBoardInDataBase(String baseTableName,int BoardStateNumber) {

        String DatabaseLocation = System.getProperty("user.dir") + "\\NEA_HexaPawn.accdb";
        Boolean exist = false;
        try {
            Connection con = DriverManager.getConnection("jdbc:ucanaccess://" + DatabaseLocation, "", "");
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql = "SELECT * FROM " + baseTableName + "BoardState";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                exist = true;
                rs.close();
                con.close();
            }
            return exist;
        } catch (Exception e) {
            System.out.println("Error in the SQL class: isBoardInDataBase " + e);
            exist = false;
            return exist;
        }

    }

    private static void AddBoardToDataBase(String baseTableName,int BoardStateNumber) {
        System.out.println(baseTableName);

        String DatabaseLocation = System.getProperty("user.dir") + "\\NEA_HexaPawn.accdb";
        try {
            Connection con = DriverManager.getConnection("jdbc:ucanaccess://" + DatabaseLocation, "", "");
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql = "INSERT INTO BoardState"+baseTableName +" (BoardFennelString,BoardSize)";
            sql =sql + " Values("+BoardStateNumber+","+baseTableName+");";
            System.out.println(sql);
            int i = stmt.executeUpdate(sql);
            System.out.println("rows Added "+i);
            con.close();


        } catch (Exception e) {
            System.out.println("Error in the SQL class: AddBoardToDataBase " + e);
        }
    }
    private static ArrayList<String> GetListOFMoveCodesInDataBase(String baseTableName) {

        String DatabaseLocation = System.getProperty("user.dir") + "\\NEA_HexaPawn.accdb";
        ArrayList<String>ListOfMoveCodesInDataBase = new ArrayList<>();
        try {
            Connection con = DriverManager.getConnection("jdbc:ucanaccess://" + DatabaseLocation, "", "");
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql = "SELECT * FROM " + baseTableName + "Moves";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                ListOfMoveCodesInDataBase.add(rs.getString("MoveCode"));
            }
            rs.close();
            con.close();

        } catch (Exception e) {
            System.out.println("Error in the SQL class:  GetListOFMoveCodesInDataBase " + e);
        }
        return ListOfMoveCodesInDataBase;
    }
    private static ArrayList<String> removeMoveCodeAlreadyInDataBase(ArrayList<String>ListOfLegalMoveCodes ,ArrayList<String>ListOfMoveCodesInDataBase){
        ArrayList<String>ListOfNonRepeatedMoveCodes = new ArrayList<>();
        for (int i = 0; i < ListOfLegalMoveCodes.size(); i++) {
            String MoveCode =ListOfLegalMoveCodes.get(i);
            Boolean repeated = false;
            for (int j = 0; j < ListOfMoveCodesInDataBase.size(); j++) {
                if(MoveCode.equals(ListOfMoveCodesInDataBase.get(j))){
                    repeated = true;
                    break;
                }
            }
            if (repeated == false){
                ListOfNonRepeatedMoveCodes.add(MoveCode);
            }
        }
        return ListOfNonRepeatedMoveCodes;

    }



}



package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


public class AddMovesToTable {
    public static void AddMoves(ArrayList<String> ListOfLegalMoveCodes, pawn[][] board) {
        String TableBaseName = board.length + "x" + board[0].length;

        //make board fennel string
        String boardState = "";
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                pawn temp = board[i][j];
                if (temp.getColour().equals("blank")) {
                    boardState = boardState + "00";
                }
                if (temp.getColour().equals("black")) {
                    boardState = boardState + "10";
                }
                if (temp.getColour().equals("white")) {
                    boardState = boardState + "01";


                }
            }
        }
        int BoardStateNumber = binaryToInteger(boardState);
        //see if board is in the DataBase
        if (!isBoardInDataBase(TableBaseName, BoardStateNumber)) {
            //add board to database
            AddBoardToDataBase(TableBaseName, BoardStateNumber);
        }

        //get list of move already in dataBAse
        System.out.println(isBoardInDataBase(TableBaseName, BoardStateNumber));
        ArrayList<String> ListOfMoveCodesInDataBase = GetListOFMoveCodesInDataBase(TableBaseName);
        // remove move code already in database
        ArrayList<String> ListOfMoveCodesNotRepeated = removeMoveCodeAlreadyInDataBase(ListOfLegalMoveCodes, ListOfMoveCodesInDataBase);
        //add move codes not in database
        for (int i = 0; i < ListOfMoveCodesNotRepeated.size(); i++) {
            AddMoveCodesToDataBase(TableBaseName, ListOfMoveCodesNotRepeated.get(i));
        }
        //link the moves and table
        int BoardID = GetBoardID(TableBaseName, BoardStateNumber);
        for (int i = 0; i < ListOfLegalMoveCodes.size(); i++) {
            int MoveID = GetMoveID(TableBaseName,ListOfLegalMoveCodes.get(i));
            AddLinkToDataBase(TableBaseName, BoardID, MoveID);

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

    private static boolean isBoardInDataBase(String baseTableName, int BoardStateNumber) {

        String DatabaseLocation = System.getProperty("user.dir") + "\\NEA_HexaPawn.accdb";

        System.out.println("is board in database processing");
        try {
            Connection con = DriverManager.getConnection("jdbc:ucanaccess://" + DatabaseLocation, "", "");
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql = "SELECT * FROM BoardState" + baseTableName + ";";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()){
                if(rs.getInt("boardFennelString")== BoardStateNumber){
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

        }

        return false;

    }

    private static void AddBoardToDataBase(String baseTableName, int BoardStateNumber) {
        System.out.println(baseTableName);
        System.out.println("Add Board to database processing");

        String DatabaseLocation = System.getProperty("user.dir") + "\\NEA_HexaPawn.accdb";
        try {
            Connection con = DriverManager.getConnection("jdbc:ucanaccess://" + DatabaseLocation, "", "");
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql = "INSERT INTO BoardState" +baseTableName+ " (BoardFennelString,BoardSize)";
            sql = sql + " Values('" +BoardStateNumber+ "','" +baseTableName+ "');";
            System.out.println(sql);
            stmt.executeUpdate(sql);
            con.close();


        } catch (Exception e) {
            System.out.println("Error in the SQL class: AddBoardToDataBase " + e);
        }
    }

    private static ArrayList<String> GetListOFMoveCodesInDataBase(String baseTableName) {
        System.out.println("GetListOFMoveCodesInDataBase processing");

        String DatabaseLocation = System.getProperty("user.dir") + "\\NEA_HexaPawn.accdb";
        ArrayList<String> ListOfMoveCodesInDataBase = new ArrayList<>();
        try {
            Connection con = DriverManager.getConnection("jdbc:ucanaccess://" + DatabaseLocation, "", "");
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql = "SELECT * FROM Moves" + baseTableName + ";";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                ListOfMoveCodesInDataBase.add(rs.getString("MoveCode"));
            }
            rs.close();
            con.close();

        } catch (Exception e) {
            System.out.println("Error in the SQL class:  GetListOFMoveCodesInDataBase " + e);
        }
        return ListOfMoveCodesInDataBase;
    }

    private static ArrayList<String> removeMoveCodeAlreadyInDataBase(ArrayList<String> ListOfLegalMoveCodes, ArrayList<String> ListOfMoveCodesInDataBase) {
        System.out.println("removeMoveCodeAlreadyInDataBase processing");
        ArrayList<String> ListOfNonRepeatedMoveCodes = new ArrayList<>();
        for (int i = 0; i < ListOfLegalMoveCodes.size(); i++) {
            String MoveCode = ListOfLegalMoveCodes.get(i);
            Boolean repeated = false;
            for (int j = 0; j < ListOfMoveCodesInDataBase.size(); j++) {
                if (MoveCode.equals(ListOfMoveCodesInDataBase.get(j))) {
                    repeated = true;
                    break;
                }
            }
            if (repeated == false) {
                ListOfNonRepeatedMoveCodes.add(MoveCode);
            }
        }
        return ListOfNonRepeatedMoveCodes;

    }

    private static void AddMoveCodesToDataBase(String baseTableName, String MoveCode) {
        System.out.println("AddMoveCodesToDataBase processing");


        String DatabaseLocation = System.getProperty("user.dir") + "\\NEA_HexaPawn.accdb";
        try {
            Connection con = DriverManager.getConnection("jdbc:ucanaccess://" + DatabaseLocation, "", "");
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql = "INSERT INTO Moves" + baseTableName + " (MoveCode)";
            sql = sql + " Values('"+MoveCode+"');";
            System.out.println(sql);
            stmt.executeUpdate(sql);

            con.close();


        } catch (Exception e) {
            System.out.println("Error in the SQL class: AddMoveCodesToDataBase " + e);
        }
    }

    private static void AddLinkToDataBase(String baseTableName, int BoardID, int MoveID) {
        System.out.println("AddLinkToDataBase processing");
        System.out.println(baseTableName);

        String DatabaseLocation = System.getProperty("user.dir") + "\\NEA_HexaPawn.accdb";
        try {
            Connection con = DriverManager.getConnection("jdbc:ucanaccess://" + DatabaseLocation, "", "");
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            //String sql = "INSERT INTO Link" + baseTableName + " (BoardID,MoveID)";
            //sql = sql + " Values('" + BoardID + "','" + MoveID + "');";

            String sql = "INSERT INTO Link" + baseTableName + " (BoardID,MoveID.Link)";
            sql = sql + " Values((SELECT BoardID from BoardState" + baseTableName + " where BoardID="+BoardID+"),(SELECT MoveID from Moves" + baseTableName + " where MoveID="+MoveID+"));";

//https://dba.stackexchange.com/questions/46410/how-do-i-insert-a-row-which-contains-a-foreign-key

            System.out.println(sql);
            stmt.executeUpdate(sql);
            con.close();


        } catch (Exception e) {
            System.out.println("Error in the SQL class: AddBoardToDataBase " + e);
        }
    }

    public static int GetBoardID(String baseTableName, int BoardFennelStringToGet) {

        String DatabaseLocation = System.getProperty("user.dir") + "\\NEA_HexaPawn.accdb";

        try {
            Connection con = DriverManager.getConnection("jdbc:ucanaccess://" + DatabaseLocation, "", "");
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql = "SELECT * FROM BoardState" + baseTableName + ";";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int BoardFennelString = rs.getInt("BoardFennelString");
                if (BoardFennelString == BoardFennelStringToGet) {
                    return rs.getInt("BoardID");
                }

            }
            System.out.println("record not found");
            rs.close();
            con.close();
        } catch (Exception e) {
            System.out.println("Error in the SQL class GetBoardId: " + e);
            return 0;
        }
        return 0;
    }
    public static int GetMoveID(String baseTableName,String MoveCodeTofind) {

        String DatabaseLocation = System.getProperty("user.dir") + "\\NEA_HexaPawn.accdb";

        try {
            Connection con = DriverManager.getConnection("jdbc:ucanaccess://" + DatabaseLocation, "", "");
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql = "SELECT * FROM Moves" + baseTableName + ";";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String MoveCode= rs.getString("MoveCode");
                if (MoveCode.equals(MoveCodeTofind)) {
                    return rs.getInt("MoveID");
                }

            }
            System.out.println("record not found");
            rs.close();
            con.close();
        } catch (Exception e) {
            System.out.println("Error in the SQL class getMoveID: " + e);
            return 0;
        }
        return 0;
    }



}






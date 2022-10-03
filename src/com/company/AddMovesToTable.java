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
            if(!IsLinkRecordInDataBase(BoardID,MoveID,TableBaseName)) {
                AddLinkToDataBase(TableBaseName, BoardID, MoveID);
            }

        }
        return;


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


        String DatabaseLocation = System.getProperty("user.dir") + "\\NEA_HexaPawn.accdb";
        try {
            Connection con = DriverManager.getConnection("jdbc:ucanaccess://" + DatabaseLocation, "", "");
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql = "INSERT INTO BoardState" +baseTableName+ " (BoardFennelString,BoardSize)";
            sql = sql + " Values('" +BoardStateNumber+ "','" +baseTableName+ "');";

            stmt.executeUpdate(sql);
            con.close();


        } catch (Exception e) {
            System.out.println("Error in the SQL class: AddBoardToDataBase " + e);
        }
    }

    private static ArrayList<String> GetListOFMoveCodesInDataBase(String baseTableName) {


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



        String DatabaseLocation = System.getProperty("user.dir") + "\\NEA_HexaPawn.accdb";
        try {
            Connection con = DriverManager.getConnection("jdbc:ucanaccess://" + DatabaseLocation, "", "");
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql = "INSERT INTO Moves" + baseTableName + " (MoveCode)";
            sql = sql + " Values('"+MoveCode+"');";
            stmt.executeUpdate(sql);

            con.close();


        } catch (Exception e) {
            System.out.println("Error in the SQL class: AddMoveCodesToDataBase " + e);
        }
    }

    private static void AddLinkToDataBase(String baseTableName, int BoardID, int MoveID) {



        String DatabaseLocation = System.getProperty("user.dir") + "\\NEA_HexaPawn.accdb";
        try {
            Connection con = DriverManager.getConnection("jdbc:ucanaccess://" + DatabaseLocation, "", "");
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            String sql = "INSERT INTO Link" + baseTableName + " (BoardID,MoveID,Considered) \n";
            sql = sql + "VAlUES ("+BoardID+","+MoveID+",True);\n";





            int i = stmt.executeUpdate(sql);

            con.close();

        } catch (Exception e) {
            System.out.println("Error in the SQL class: AddBoardToDataBase " + e);
        }
    }

    public static int GetBoardID(String baseTableName, int BoardFennelStringToGet) {

        String DatabaseLocation = System.getProperty("user.dir") + "\\NEA_HexaPawn.accdb";
        int WantedBoardID = 0;
        try {

            Connection con = DriverManager.getConnection("jdbc:ucanaccess://" + DatabaseLocation, "", "");
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql = "SELECT * FROM BoardState" + baseTableName + ";";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int BoardFennelString = rs.getInt("BoardFennelString");
                if (BoardFennelString == BoardFennelStringToGet) {
                   WantedBoardID=  rs.getInt("BoardID");
                }

            }

            rs.close();
            con.close();
        } catch (Exception e) {
            System.out.println("Error in the SQL class GetBoardId: " + e);
            return WantedBoardID;
        }
        return WantedBoardID;
    }
    public static int GetMoveID(String baseTableName,String MoveCodeTofind) {
        int WantedMoveID = 0;

        String DatabaseLocation = System.getProperty("user.dir") + "\\NEA_HexaPawn.accdb";

        try {
            Connection con = DriverManager.getConnection("jdbc:ucanaccess://" + DatabaseLocation, "", "");
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql = "SELECT * FROM Moves" + baseTableName + ";";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String MoveCode= rs.getString("MoveCode");
                if (MoveCode.equals(MoveCodeTofind)) {
                    WantedMoveID= rs.getInt("MoveID");
                }

            }

            rs.close();
            con.close();
        } catch (Exception e) {
            System.out.println("Error in the SQL class getMoveID: " + e);
            return WantedMoveID;
        }
        return WantedMoveID;
    }
    public static boolean IsLinkRecordInDataBase(int BoardID,int MoveID,String baseTableName) {
        Boolean InDataBase = false;

        String DatabaseLocation = System.getProperty("user.dir") + "\\NEA_HexaPawn.accdb";

        try {
            Connection con = DriverManager.getConnection("jdbc:ucanaccess://" + DatabaseLocation, "", "");
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql = "SELECT * FROM Link" + baseTableName + ";";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                if ((rs.getInt("BoardID")==BoardID)&&(rs.getInt("MoveID")==MoveID)) {
                    InDataBase =true;
                }

            }

            rs.close();
            con.close();
        } catch (Exception e) {
            System.out.println("Error in the SQL class getMoveID: " + e);
            return InDataBase;
        }
        return InDataBase;
    }



}






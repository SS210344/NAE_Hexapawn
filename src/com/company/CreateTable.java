package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class CreateTable {
    public static void CreateTableSet(String BaseTableName){
        CreateBoardTable(BaseTableName);
        CreateMoveCodeTable(BaseTableName);
        CreateLinkTable(BaseTableName);

    }
    private static void CreateBoardTable(String BaseTableName){
        String DatabaseLocation = System.getProperty("user.dir") + "\\NEA_HexaPawn.accdb";
        try {
            Connection con = DriverManager.getConnection("jdbc:ucanaccess://" + DatabaseLocation, "", "");
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            String sql = "DROP TABLE IF EXISTS "+BaseTableName+"BoardState CREATE TABLE "+BaseTableName+"BoardState (\n"+
                    "BoardID INT NOT NULL PRIMARY KEY,\n"+
                    "BoardFennelString LONG,\n"+
                    "BoardSize VARCHAR(255)\n"+
            ");";
            System.out.println(sql);

            stmt.executeUpdate(sql);

            con.close();


        } catch (Exception e) {
            System.out.println("Error in the SQL class: CreateBoardTable " + e);
        }

    }
    private static void CreateMoveCodeTable(String BaseTableName){
        String DatabaseLocation = System.getProperty("user.dir") + "\\NEA_HexaPawn.accdb";
        try {
            Connection con = DriverManager.getConnection("jdbc:ucanaccess://" + DatabaseLocation, "", "");
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql = "Drop TAble if Exists "+BaseTableName+"Moves CREATE TABLE "+BaseTableName+"Moves (\n" +
                    "    MoveID int NOT NULL PRIMARY KEY,\n" +
                    "    MoveCode varchar(255)\n" +
                    ");";
            stmt.execute(sql);
            con.close();


        } catch (Exception e) {
            System.out.println("Error in the SQL class:  CreateMoveCodeTable" + e);
        }

    }
    private static void CreateLinkTable(String BaseTableName){
        String DatabaseLocation = System.getProperty("user.dir") + "\\NEA_HexaPawn.accdb";
        try {
            Connection con = DriverManager.getConnection("jdbc:ucanaccess://" + DatabaseLocation,"","");
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql = "Drop Table if Exists "+BaseTableName+"Link CREATE TABLE "+BaseTableName+"Link (\n" +
                    "    LinkID int NOT NULL PRIMARY KEY,\n" +
                    "    BoardID int,\n" +
                    "    MoveID int,\n" +
                    "    considered Boolean\n" +
                    "    FOREIGN KEY(BoardID) REFERENCES "+BaseTableName+"BoardState(BoardID),\n" +
                    "    FOREIGN KEY(MoveID) REFERENCES "+BaseTableName+"Moves(MoveID)\n" +
                    ");";
            stmt.execute(sql);

            con.close();


        } catch (Exception e) {
            System.out.println("Error in the SQL class: CreateLinkTable " + e);
        }

    }
}

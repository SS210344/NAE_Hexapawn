package com.company;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        // write your code here
        ArrayList<String>Moves = new ArrayList<>();
        Moves.add("A1B1");
        Moves.add("B1B1");
        Moves.add("C1B1");
        Moves.add("A1B2");
        Moves.add("A3B1");
        Moves.add("A1B4");
        ArrayList<String>moves = new ArrayList<>();
        moves = MergeSort.MergeSort(Moves);
        for (int i = 0; i < moves.size(); i++) {
            System.out.println(moves.get(i));
        }

        System.out.println( ReadTable.SeeIfTableExist("3x3"));


        Moves = findLegalMoves.GetMoveCodesFromTable("3x3",1);
        for (int i = 0; i < Moves.size(); i++) {
            System.out.println(Moves.get(i));

        }

        //MainMenu.Menu();
        //ReadTable.SQLTest();
        /*System.out.println(ReadTable.SeeIfTableExist("3x3"));
        CreateTable.CreateTableSet("4x4");
        String[] Output = new String[4];
        Output[0]="White";
        Output[1]="White";
        Output[2]="A1A2";
        Output[3]="3x4";
       WinRecord.UpdateRecords(Output);

        pawn[][] board = boardCreation.makeBoard(boardCreation.getBoardSize());
        board = boardCreation.initialiseBoard(board);

        boardDisplay.displayBoard(board);

        //AddMovesToTable.AddMoves(findLegalMoves.findBlackMove(board),board);

        String[] winner =PlayGame.Game(board);
        System.out.println(winner[0]);
        */






/*
        boardDisplay.displayBoard(board);
        board= updateBoard.boardUpdate(board,selectMove.selectPlayerMove(findLegalMoves.findBlackMove(board)));
        boardDisplay.displayBoard(board);

 */




    }
}

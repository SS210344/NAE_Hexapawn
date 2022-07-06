package com.company;

public class Main {

    public static void main(String[] args) {
        // write your code here
        MainMenu.Menu();
        //ReadTable.SQLTest();
        //System.out.println(ReadTable.SeeIfTableExist("3x3"));
        //CreateTable.CreateTableSet("4x4");
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






/*
        boardDisplay.displayBoard(board);
        board= updateBoard.boardUpdate(board,selectMove.selectPlayerMove(findLegalMoves.findBlackMove(board)));
        boardDisplay.displayBoard(board);

 */




    }
}

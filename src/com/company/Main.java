package com.company;

public class Main {

    public static void main(String[] args) {
        // write your code here
        //ReadTable.SQLTest();
        //System.out.println(ReadTable.SeeIfTableExist("3x3"));
        pawn[][] board = boardCreation.makeBoard(boardCreation.getBoardSize());
        board = boardCreation.initialiseBoard(board);

        boardDisplay.displayBoard(board);

        String[] winner =PlayGame.Game(board);
        System.out.println(winner[0]);






/*
        boardDisplay.displayBoard(board);
        board= updateBoard.boardUpdate(board,selectMove.selectPlayerMove(findLegalMoves.findBlackMove(board)));
        boardDisplay.displayBoard(board);

 */




    }
}

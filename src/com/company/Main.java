package com.company;

public class Main {

    public static void main(String[] args) {
        // write your code here
        pawn[][] board = boardCreation.makeBoard(boardCreation.getBoardSize());
        board = boardCreation.initialiseBoard(board);
        boardDisplay.displayBoard(board);
        findLegalMoves.PrintMoveCode(findLegalMoves.findBlackMove(board));

    }
}

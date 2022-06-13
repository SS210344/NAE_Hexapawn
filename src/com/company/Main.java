package com.company;

public class Main {

    public static void main(String[] args) {
        // write your code here
        pawn[][] board = boardCreation.makeBoard(boardCreation.getBoardSize());
        board = boardCreation.initialiseBoard(board);
        boardDisplay.displayBoard(board);


        board= updateBoard.boardUpdate(board,"A1A2");
        board= updateBoard.boardUpdate(board,"B1B2");
        board= updateBoard.boardUpdate(board,"C1C2");



        boardDisplay.displayBoard(board);
        board= updateBoard.boardUpdate(board,selectMove.selectAIMove(findLegalMoves.findBlackMove(board)));
        boardDisplay.displayBoard(board);




    }
}

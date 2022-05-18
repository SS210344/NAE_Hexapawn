package com.company;

public class Main {

    public static void main(String[] args) {
        // write your code here
        pawn[][] board = boardCreation.makeBoard(boardCreation.getBoardSize());
        boardCreation.initialiseBoard(board);
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                pawn temp = board[i][j];
                System.out.println(temp.getColour());
            }


        }
    }
}

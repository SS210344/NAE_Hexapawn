package com.company;

public class findLegalMoves {
    public static void findLegalMovesSet(pawn[][]board){
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
    }
}

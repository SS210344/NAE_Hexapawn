package com.company;


import java.util.ArrayList;
import java.util.Scanner;

public class PlayGame {
    public static String[] Game(pawn[][] board){
        String colourOfCurrentPlayer ="White";
        String AILastMove = "";
        String BoardSize = board.length+"x"+board[0].length;
        String[] Output = new String[4];
        Output[3] = BoardSize;


                //display Board
        boardDisplay.displayBoard(board);
        // see what colour player wants to play as
        String colourOfPlayer = SelectColourPlayWillPlayAS();
        Output[1] = colourOfPlayer;
        while (true) {
            //outputs the winner
            String Winner = IsPieceAtOtherEnd(board);
            if ((Winner.equals("White wins")||(Winner.equals("Black wins")))) {
                Output[0] = Winner.substring(0,5);
                Output[2] = AILastMove;
                return Output;

            }
            //find legal moves
            ArrayList<String> listOFLegalMoveCode;
            if (colourOfCurrentPlayer.equals("White") ){
                listOFLegalMoveCode=FindMoveOnBoard.findWhiteMove(board);
            }else{
                listOFLegalMoveCode=FindMoveOnBoard.findBlackMove(board);
            }

            //see if there are no legal moves
            if(listOFLegalMoveCode.size()==0 ) {
                if (colourOfCurrentPlayer.equals("White")) {
                    Output[0] = "Black";
                }else{
                    Output[0]="White";
                }
                Output[2] = AILastMove;
                return Output;
            }
            //player of AI or player select move
            String moveCode;
            if(colourOfCurrentPlayer.equals(colourOfPlayer)) {
                moveCode= selectMove.selectPlayerMove(listOFLegalMoveCode);
            }
            else {
                moveCode= selectMove.selectAIMove(listOFLegalMoveCode);
            }
            //update board
            board= updateBoard.boardUpdate(board,moveCode);
            //display board
            boardDisplay.displayBoard(board);




            //outputs the winner
            Winner = IsPieceAtOtherEnd(board);
            if ((Winner.equals("White wins") || (Winner.equals("Black wins")))) {
                Output[0] = Winner.substring(0, 5);
                Output[2] = AILastMove;
                return Output;


            }

            //change player to play
            if(colourOfCurrentPlayer.equals("White")) {
                colourOfCurrentPlayer = "Black";
            }else {
                colourOfCurrentPlayer="White";
            }

        }



    }






    public static String SelectColourPlayWillPlayAS() {
        Scanner input = new Scanner(System.in);
        String colourOfPlayer;
        while (true) {
            try {
                System.out.println("chose colour to play as Black or White:");
                colourOfPlayer= input.next();
                if ((colourOfPlayer.equals("Black")||(colourOfPlayer.equals("White")))){
                    return colourOfPlayer;
                }
                else{
                    System.out.println("The first letter need to be a capital");
                }

            } catch (java.util.InputMismatchException e) {
                System.out.println("please input black or white and the first letter need to be a capital");
                input.next();
            } catch (Exception e) {
                System.out.println("there was and error " + e);
                input.next();
            }
        }
    }

    public static String IsPieceAtOtherEnd(pawn[][] board){
        pawn tempPawn = new pawn("a");
        for (int i = 0; i < board[0].length; i++) {
            tempPawn.setColour(board[0][i].getColour());
            if (tempPawn.getColour().equals("white")) {
                return "White wins";
            }
        }
        for (int i = 0; i < board[board.length-1].length; i++) {
            tempPawn.setColour(board[board.length-1][i].getColour());
            if (tempPawn.getColour().equals("black")) {
                return "Black wins";
            }
        }
           return "no winner";
        }
    }



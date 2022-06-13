package com.company;


import java.util.ArrayList;
import java.util.Scanner;

public class PlayGame {
    public static String[] Game(pawn[][] board){
        String colourOfCurrentPlayer ="white";
        String AILastMove = "";

        //display Board
        boardDisplay.displayBoard(board);
        // see what colour player wants to play as
        String colourOfPlayer = SelectColourPlayWillPlayAS();
        while (true) {
            //outputs the winner
            String Winner = IsPieceAtOtherEnd(board);
            if ((Winner.equals("white wins")||(Winner.equals("black Wins")))) {
                String[] Output = new String[3];
                Output[0] = Winner.substring(0,5);
                Output[1] = colourOfPlayer;
                Output[2] = AILastMove;
                return Output;

            }
            //find legal moves
            ArrayList<String> listOFLegalMoveCode;
            if (colourOfCurrentPlayer.equals("white") ){
                listOFLegalMoveCode=findLegalMoves.findWhiteMove(board);
            }else{
                listOFLegalMoveCode=findLegalMoves.findBlackMove(board);
            }

            //see if there are no legal moves
            if(listOFLegalMoveCode.size()==0 ) {
                String[] Output = new String[3];
                if (colourOfCurrentPlayer.equals("white")) {
                    Output[0] = "black";
                }else{
                    Output[0]="white";
                }
                Output[1] = colourOfPlayer;
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

            // see what colour player wants to play as


            //outputs the winner
            Winner = IsPieceAtOtherEnd(board);
            if ((Winner.equals("white wins") || (Winner.equals("black Wins")))) {
                String[] Output = new String[3];
                Output[0] = Winner.substring(0, 5);
                Output[1] = colourOfPlayer;
                Output[2] = AILastMove;
                return Output;


            }

            //change player to play
            if(colourOfCurrentPlayer.equals("white")) {
                colourOfCurrentPlayer = "black";
            }else {
                colourOfCurrentPlayer="white";
            }

        }



    }






    private static String SelectColourPlayWillPlayAS() {
        Scanner input = new Scanner(System.in);
        String colourOfPlayer;
        while (true) {
            try {
                System.out.println("chose colour to play as black or white:");
                colourOfPlayer= input.next();
                if ((colourOfPlayer.equals("black")||(colourOfPlayer.equals("white")))){
                    return colourOfPlayer;
                }

            } catch (java.util.InputMismatchException e) {
                System.out.println("please input black or white");
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
                return "white wins";
            }
        }
        for (int i = 0; i < board[board.length-1].length; i++) {
            tempPawn.setColour(board[board.length-1][i].getColour());
            if (tempPawn.getColour().equals("black")) {
                return "black wins";
            }
        }
           return "no winner";
        }
    }



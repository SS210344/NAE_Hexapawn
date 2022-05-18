package com.company;

import java.util.Scanner;

public class boardCreation {
    public static int[] getBoardSize(){
        Scanner input = new Scanner(System.in);
        int row;
        int collum;
        while (true) {
            try {
                System.out.println("number of rows between 3 and 5");
                row = input.nextInt();
                if ((row >= 3) && (row <=5)){
                    break;
                }
                else{
                    System.out.println("number needs to be between 3 and 5");
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("please input a whole number");
                input.next();
            } catch (Exception e) {
                System.out.println("there was and error " + e);
                input.next();
            }
        }
        while (true) {
            try {
                System.out.println("number of collum wanted between 3 and 5");
                collum = input.nextInt();
                if ((collum >= 3) && (collum <=5)){
                    break;
                }
                else{
                    System.out.println("number needs to be between 3 and 5");
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("please input a whole number");
                input.next();
            } catch (Exception e) {
                System.out.println("there was and error " + e);
                input.next();
            }
        }
        int[] boardSize= new int[2];
        boardSize[0]= row;
        boardSize[1]= collum;
        return boardSize;

    }

    public static pawn[][] makeBoard(int[] boardSize){
        pawn[][] board = new pawn[boardSize[0]][boardSize[1]];
        for (int i = 0; i < boardSize[0]; i++) {
            for (int j = 0; j < boardSize[1]; j++) {
                board[i][j] = new pawn("blank");
            }
        }
        return board;
    }

    public static pawn[][] initialiseBoard(pawn[][] board){
        for (int i = 0; i < board[0].length; i++) {
            board[0][i]= new pawn("black");
        }
        for (int i = 0; i < board[board.length-1].length; i++) {
            board[board.length-1][i]= new pawn("white");
        }

        return board;
    }
}

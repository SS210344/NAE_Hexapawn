package com.company;

import java.util.ArrayList;

public class FindMoveOnBoard {
    public static ArrayList<String> findWhiteMove(pawn[][]board) {
        ArrayList<String>moveCodeList=new ArrayList<>();
        String MoveCode="";

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                MoveCode = "";
                pawn temp = board[i][j];
                if (temp.getColour().equals("white")) {
                    try {

                        //move forward 1
                        pawn newTemp = board[i - 1][j];
                        if (newTemp.getColour().equals("blank")) {
                            int numLetter = j + 65;
                            char letter = (char) numLetter;

                            MoveCode = letter + IntToSting(i + 1) + letter + IntToSting(i - 1 + 1);
                            moveCodeList.add(MoveCode);
                        }


                    } catch (Exception e) {
                        System.out.print("");
                    }
                    //take left
                    try {

                        pawn newTemp = board[i - 1][j - 1];
                        if (newTemp.getColour().equals("black")) {
                            int numLetter1 = j + 65 - 1;
                            char letter1 = (char) numLetter1;
                            int numLetter2 = j + 65;
                            char letter2 = (char) numLetter2;

                            MoveCode = letter2 + IntToSting(i + 1) + letter1 + IntToSting(i - 1 + 1);
                            moveCodeList.add(MoveCode);
                        }
                    } catch (Exception e) {
                        System.out.print("");
                    }
                    try {


                        //take right
                        pawn newTemp = board[i - 1][j + 1];
                        if (newTemp.getColour().equals("black")) {
                            int numLetter1 = j + 65 + 1;
                            char letter1 = (char) numLetter1;
                            int numLetter2 = j + 65;
                            char letter2 = (char) numLetter2;

                            MoveCode = letter2 + IntToSting(i + 1) + letter1 + IntToSting(i - 1 + 1);
                            moveCodeList.add(MoveCode);
                        }
                    } catch (Exception e) {
                        System.out.print("");
                    }

                }
            }
        }
        return moveCodeList;
    }
    public static ArrayList<String> findBlackMove(pawn[][]board) {
        ArrayList<String>moveCodeList=new ArrayList<>();
        String MoveCode="";

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                MoveCode="";
                pawn temp = board[i][j];
                if (temp.getColour().equals("black")) {
                    try {
                        //move forward 1
                        pawn newTemp = board[i + 1][j];
                        if (newTemp.getColour().equals("blank")) {


                            int numLetter1 = j + 65;
                            char letter1 = (char) numLetter1;
                            int numLetter2 = j + 65;
                            char letter2 = (char) numLetter2;

                            MoveCode = letter2 + IntToSting(i + 1) + letter1 + IntToSting(i + 1 + 1);
                            moveCodeList.add(MoveCode);
                        }
                    }catch (Exception e){
                        System.out.print("");
                    }

                    try {
                        //take left
                        pawn newTemp = board[i + 1][j - 1];
                        if (newTemp.getColour().equals("white")) {


                            int numLetter1 = j + 65-1 ;
                            char letter1 = (char) numLetter1;
                            int numLetter2 = j + 65;
                            char letter2 = (char) numLetter2;

                            MoveCode = letter2 + IntToSting(i + 1) + letter1 + IntToSting(i + 1 + 1);
                            moveCodeList.add(MoveCode);
                        }
                    }                catch (Exception e){
                        System.out.print("");
                    }


                    try{
                        //take right
                        pawn newTemp = board[i+1][j+1];
                        if (newTemp.getColour().equals("white")) {

                            int numLetter1 = j + 65 + 1;
                            char letter1 = (char) numLetter1;
                            int numLetter2 = j + 65;
                            char letter2 = (char) numLetter2;

                            MoveCode=letter2+IntToSting(i+1)+letter1+IntToSting(i+1+1);
                            moveCodeList.add(MoveCode);
                        }
                    }                catch (Exception e){
                        System.out.print("");
                    }

                }

            }
        }
        return moveCodeList;
    }
    private static String IntToSting(int i){
        String s=Integer.toString(i);
        return s;
    }
}

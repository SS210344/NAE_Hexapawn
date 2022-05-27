package com.company;

import com.sun.xml.internal.ws.addressing.WsaActionUtil;

public class boardDisplay {
    public static void displayBoard(pawn[][] board){
        // top line
        System.out.print("-----");
        for (int i = 0; i < board.length; i++) {
            int numLetter = i+65;
            char letter = (char) numLetter;
            System.out.print("--"+letter+"--");
        }
        System.out.println("");
        for (int i = 0; i < board.length; i++) {
            System.out.print("--"+i+"--");
            for (int j = 0; j < board[i].length; j++) {

                pawn temp = board[i][j];
                if (temp.getColour().equals("blank")){
                    System.out.print(" [ ] ");
                }
                if (temp.getColour().equals("black")){
                    System.out.print(" [B] ");
                }
                if (temp.getColour().equals("white")){
                    System.out.print(" [W] ");
                }

            }
            System.out.println("");


        }
    }

}

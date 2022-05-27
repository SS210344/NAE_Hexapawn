package com.company;

public class updateBoard {
    public static pawn[][] boardUpdate(pawn[][]board,String moveCode){
        String StartLocation = moveCode.substring(0,2);
        char Letter = StartLocation.charAt(0);
        int numberLetter = Letter-65;
        int integer = Character.getNumericValue( StartLocation.charAt(1))-1;
        pawn temp =new pawn(board[integer][numberLetter].getColour());
        board[integer][numberLetter].setColour("blank");

        String endLocation = moveCode.substring(2,4);
        Letter = endLocation.charAt(0);
        numberLetter = Letter-65;
        integer = Character.getNumericValue( endLocation.charAt(1))-1;
        board[integer][numberLetter]= temp;

        return board;

    }
}

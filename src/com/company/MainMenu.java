package com.company;

import java.util.Scanner;

public class MainMenu {
    public static void Menu() {
        while (true) {
            Scanner input = new Scanner(System.in);
            try {
                System.out.println("options :");
                System.out.println("1: play game");
                System.out.println("2: see win record");
                System.out.println("3: instructions and rules ");
                System.out.println("4 : exit");
                System.out.println("select number between 1 and 4");
                int option = input.nextInt();

                if ((option >= 1) && (option <= 4)) {
                    if (option == 1) {
                        //play game
                        PlayGame.Game(boardCreation.initialiseBoard(boardCreation.makeBoard(boardCreation.getBoardSize())));
                    }
                    if (option == 2) {
                        //see win record
                    }
                    if (option == 3) {
                        //instructions and rules
                        InstructionsAndRuleSet();
                    }
                    if (option == 4) {
                        //exit
                        System.out.println("thanks for playing");
                        break;
                    }

                } else {
                    System.out.println("number needs to be between 1 and 4");
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("please input a whole number");
                input.next();
            } catch (Exception e) {
                System.out.println("there was and error " + e);
                input.next();
            }
        }


    }
    private static void InstructionsAndRuleSet(){

        System.out.println("rules:");
        System.out.println("the aim is to get one of your pawn to the enemy side for the board before the ai does.");
        System.out.println("you lose if the ai gets a pawn to your side or you have no legal moves to do no your turn.");
        System.out.println("all the pieces are pawn for the game chess but can only ever move forward one space at a time and only take diagonally");
        System.out.println("if a piece of the enemy is there to capture.");
        System.out.println("");
        System.out.println("the moves are represented using move codes. e.g. B1A1");
        System.out.println("the letters represent the collum / file.");
        System.out.println("the Numbers represent the rows / ranks.");
        System.out.println("the first letter and number represent the starting position of the pawn.");
        System.out.println("the second letter and number represent the end position of the pawn.");
    }
}




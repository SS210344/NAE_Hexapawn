package com.company;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class selectMove {
    public static String selectPlayerMove(ArrayList<String> listOfLegalMove ){
        Scanner input = new Scanner(System.in);
        int MoveSelected;
        // sorts list into alphabetical order
        listOfLegalMove= MergeSort.MergeSort(listOfLegalMove);
        // prints list of moves
        System.out.println("list of legal move:");
        for (int i = 0; i < listOfLegalMove.size(); i++) {
            System.out.println((i+1)+": "+listOfLegalMove.get(i));
        }
        //player select a number in a range and out puts the element at the list and returns it
        while (true) {
            try {
                System.out.println("number of move wanted between 1 and "+(listOfLegalMove.size()));
                 MoveSelected= input.nextInt();
                if ((MoveSelected >= 1) && (MoveSelected <=listOfLegalMove.size())){
                    break;
                }
                else{
                    System.out.println("number needs to be between 1 and "+(listOfLegalMove.size())) ;
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("please input a whole number ");
                input.next();
            } catch (Exception e) {
                System.out.println("there was and error " + e);
                input.next();
            }
        }
        String MoveCode = listOfLegalMove.get(MoveSelected-1);

        return MoveCode;
    }
    public static String selectAIMove(ArrayList<String> listOfLegalMove ){
        Random randInt = new Random();
        int RandomMoveInt = randInt.nextInt(listOfLegalMove.size());
        String RandomMoveCode=listOfLegalMove.get(RandomMoveInt);
        System.out.println(RandomMoveCode);
        return RandomMoveCode;
    }
}

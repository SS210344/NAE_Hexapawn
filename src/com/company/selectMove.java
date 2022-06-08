package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class selectMove {
    public static String selectPlayerMove(ArrayList<String> listOfLegalMove ){
        Scanner input = new Scanner(System.in);
        int MoveSelected;
        System.out.println("list of legal move:");
        for (int i = 0; i < listOfLegalMove.size(); i++) {
            System.out.println((i+1)+": "+listOfLegalMove.get(i));
        }
        while (true) {
            try {
                System.out.println("number of rows between 1 and "+(listOfLegalMove.size()));
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
}

package com.company;

import java.util.ArrayList;

public class MergeSort {
    public static ArrayList<String> MergeSort(ArrayList<String> Moves) {
        // returns list when there is only one element
        if (Moves.size() <= 1) {
            return Moves;
        }
        // splits the list in half
        int left = 0;
        int right  = Moves.size()-1;
        int middle =  (left + right)/2;
        ArrayList<String> leftList = new ArrayList<>();
        ArrayList<String> rightList = new ArrayList<>();

        for (int i = 0; i < middle+1; i++) {
            leftList.add(Moves.get(i));
        }
        for (int i = middle+1; i < right+1; i++) {
            rightList.add(Moves.get(i));
        }
        // does the same splitting on both the new lists until there are only sorted lists of 1 element in a list
        leftList = MergeSort(leftList);
        rightList = MergeSort(rightList);
        // combines the lists
        ArrayList<String> combined =  Merge(leftList,rightList);
        return combined;


    }
    public static ArrayList<String> Merge(ArrayList<String> leftList,ArrayList<String> rightList){
        ArrayList<String>Result = new ArrayList<>();
        // add elements to results in order of letters is A to Z then numbers 1 to 9
        while ((leftList.size()>0)&&(rightList.size()>0)){
            String Left = leftList.get(0);
            String right = rightList.get(0);


            if(Compare(Left,right)){
                Result.add(right);
                rightList.remove(0);
            }
            else{
                Result.add(Left);
                leftList.remove(0);
            }
        }
        // adds left over  elements to the end
        while (leftList.size()>0){
            Result.add(leftList.get(0));
            leftList.remove(0);
        }
        while (rightList.size()>0){
            Result.add(rightList.get(0));
            rightList.remove(0);

        }
        return Result;
    }
    //credit of pseudocode https://www.softwaretestinghelp.com/merge-sort-java/
    private static Boolean Compare(String Left, String Right){
        // convert string to array of letters
        char[] leftLetters = new char[Left.length()];
        for (int i = 0; i < Left.length(); i++) {
            leftLetters[i] = Left.charAt(i);
        }
        char[] rightLetters = new char[Right.length()];
        for (int i = 0; i < Right.length(); i++) {
            rightLetters[i] = Right.charAt(i);
        }
        // compares them char by char where the order of letters is A to Z then 1 to 9
        for (int i = 0; i < Left.length(); i++) {
            if(leftLetters[i]==rightLetters[i]){
            }
            else if(leftLetters[i]>rightLetters[i]){
                // left is first in alphabet
                return true;
            }
            else{
                // Right is first alphabet
                return false;
            }

        }
        return true;
        // only used if left and right are the same



    }

}

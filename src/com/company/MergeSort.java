package com.company;

import java.util.ArrayList;

public class MergeSort {
    public static ArrayList<String> MergeSort(ArrayList<String> Moves) {
        if (Moves.size() <= 1) {
            return Moves;
        }
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
        leftList = MergeSort(leftList);
        rightList = MergeSort(rightList);
        return Merge(leftList,rightList);


    }
    public static ArrayList<String> Merge(ArrayList<String> leftList,ArrayList<String> rightList){
        ArrayList<String>Result = new ArrayList<>();
        while ((leftList.size()>0)&&(rightList.size()>0)){
            String Left = leftList.get(0);
            String right = leftList.get(0);
            // change the compare functition
            if(Left.compareTo(right)>0){
                Result.add(right);
                rightList.remove(0);
            }
            else{
                Result.add(Left);
                leftList.remove(0);
            }
        }
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
    //credit of pseudo code https://www.softwaretestinghelp.com/merge-sort-java/

}

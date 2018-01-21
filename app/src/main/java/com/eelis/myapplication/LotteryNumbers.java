package com.eelis.myapplication;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by Eelis on 20.10.2017.
 */

public class LotteryNumbers {

    private int[] numbers;
    //null if no extra is wanted
    private int[] extra;

    public LotteryNumbers(int[] nums) {
        for(int i = 0;i < nums.length;i++) {
            numbers[i] = nums[i];
        }
        Arrays.sort(numbers);
    }

    public LotteryNumbers(int amount, int max, int min) {
        Random rand = new Random();
        //in case amount was given poorly
        amount = Math.max(max - min, amount);
        numbers = new int[amount];
        if (amount == max - min) {
            for (int i = min; i <= max; i++) {
                numbers[i - min] = i;
            }
        } else {
            int i = 0;
            while (i < amount) {
                int num = min + rand.nextInt(max - min + 1);
                if(!listContains(numbers, num)) {
                    numbers[i++] = num;
                }
            }
        }
        Arrays.sort(numbers);
    }

    public void setExtra(int amount, int max, int min) {
        Random rand = new Random();
        amount = Math.max(max - min, amount);
        extra = new int[amount];
        int i = 0;
        while(i < amount){
            int num = min + rand.nextInt(max - min + 1);
            if(!listContains(extra, num)) {
                extra[i++] = num;
            }
        }
        Arrays.sort(extra);
    }

    /**Helper method for setting new random lists*/
    private boolean listContains(int[] list, int a) {
        for(int i: list) {
            if(i == a) {
                return true;
            }
        }
        return false;
    }

    public int rowLength() {
        return numbers.length;
    }

    public boolean hasExtraNumbers() {
        return extra != null;
    }

    @Override
    public String toString() {
        String extractor = ", ";
        String str = "";
        for(int i = 0;i < numbers.length - 1;i++) {
            str += numbers[i] + extractor;
        }
        str += numbers[numbers.length - 1];
        if(hasExtraNumbers()) {
            str += "\n";
            for(int i = 0;i < extra.length - 1;i++) {
                str += extra[i] + extractor;
            }
            str += extra[extra.length - 1];
        }
        return str;
    }


}

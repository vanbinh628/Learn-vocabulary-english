package com.example.syn;

import android.util.Log;

import java.util.Arrays;
import java.util.Random;

public class ListWord {
    private String[] arrayWord;

    public String[] getArrayWord() {
        return arrayWord;
    }

    public void setArrayWord(String[] arrayWord) {
        this.arrayWord = arrayWord;
    }

    public ListWord(String[] arrayWord) {
        this.arrayWord = arrayWord;
    }

    public ListWord(String characterString) {
        String [] array = characterString.split(", ");
        if (array.length > 0) {
            this.arrayWord = array;
        } else {
            this.arrayWord = new String[]{};
        }
    }

    public int length() {
        return arrayWord.length;
    }

    public String ConvertArrayWordToString() {
        String str = "";
        for (int i = 0; i < arrayWord.length; i++) {
            str += arrayWord[i] +", ";
        }
        str = str.substring(0, str.length() - 2);
        return str;
    }

    public void ShuffleArray() {
        String[] shuffleArray = Arrays.copyOf(this.arrayWord, this.arrayWord.length);
        do {
            // Shuffle array of word
            Random rand = new Random();
            for (int i = 0; i < shuffleArray.length; i++) {
                int randomIndex = rand.nextInt(shuffleArray.length);
                String temp = shuffleArray[randomIndex];
                shuffleArray[randomIndex] = shuffleArray[i];
                shuffleArray[i] = temp;
            }
        } while (null == shuffleArray || isSameTowArray(this.arrayWord, shuffleArray));
        this.arrayWord = shuffleArray;
    }

    public void PrintArray() {
        String strPrint = "";
        for (int i = 0; i < this.arrayWord.length; i++) {
            strPrint += this.arrayWord[i] + " ,";
        }
        strPrint += "\n";
        Log.d("MyClass", strPrint);
    }

    private boolean isSameTowArray(String [] array1, String [] array2) {
        for (int i = 0; i < array1.length; i++) {
            if (!array1[i].equals(array2[i])) {
                return false;
            }
        }
        return true;
    }

}

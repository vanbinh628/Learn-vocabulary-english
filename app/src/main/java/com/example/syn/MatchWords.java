package com.example.syn;

public class MatchWords {
    public String englishWord;
    public String vietnameseWord;
    public String answer;

    public MatchWords(String englishWord, String vietnameseWord) {
        this.englishWord = englishWord;
        this.vietnameseWord = vietnameseWord;
        this.answer = "";
    }

    public void setEnglishWord(String englishWord) {
        this.englishWord = englishWord;
    }

    public void setVietnameseWord(String vietnameseWord) {
        this.vietnameseWord = vietnameseWord;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }



}

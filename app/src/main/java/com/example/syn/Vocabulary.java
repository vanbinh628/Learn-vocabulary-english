package com.example.syn;

import java.util.ArrayList;

public class Vocabulary {
    int id;
    String name;
    String words;
    String means;
    int length;

    public Vocabulary() {
        this.id = -1;
        this.name = "";
        this.words = "";
        this.means = "";
        this.length = 0;
    }

    public Vocabulary(int id, String name, String words, String means, int length) {
        this.id = id;
        this.name = name;
        this.words = words;
        this.means = means;
        this.length = length;
    }

    public Vocabulary(String name, ArrayList<String> listWord, ArrayList<String> listMean, int length) {
        this.id = 0;
        String word = "", mean = "";
        for (int i = 0; i<length;i++) {
            word += listWord.get(i) + ", ";
            mean += listMean.get(i) + ", ";
        }
        this.words = word.substring(0, word.length()-2);
        this.means = mean.substring(0, mean.length()-2);
        this.name = name;
        this.length = length;
    }

    @Override
    public String toString() {
        return "Vocabulary{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", words='" + words + '\'' +
                ", means='" + means + '\'' +
                ", length=" + length +
                '}';
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getWords() {
        return words;
    }

    public String getMeans() {
        return means;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWords(String words) {
        this.words = words;
    }

    public void setMeans(String means) {
        this.means = means;
    }
}

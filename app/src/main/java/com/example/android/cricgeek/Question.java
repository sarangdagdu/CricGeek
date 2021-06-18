package com.example.android.cricgeek;

import java.util.List;

public class Question {
    String question;
    List<String> options;
    int correctIndex;

    public Question(String question, List<String> options, int correctIndex){
        this.question = question;
        this.options = options;
        this.correctIndex = correctIndex;
    }
}

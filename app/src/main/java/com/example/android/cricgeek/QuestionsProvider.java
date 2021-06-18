package com.example.android.cricgeek;

import java.util.ArrayList;
import java.util.List;

public class QuestionsProvider {
    private ArrayList<Question> questions;
    public void createQuestions(){
        /*Creating the ArrayList of Questions*/
        questions = new ArrayList<Question>();

        /*Now we will add questions to the Questions ArrayList
        * Went with the traditional approach of vreating and adding
        * questions manually */

        /* Question 1 */
        List<String> optionsForQuestionOne = new ArrayList<String>();
        optionsForQuestionOne.add("Charles Bannerman");
        optionsForQuestionOne.add("Harry Graham");
        optionsForQuestionOne.add("R.E. Foster");
        optionsForQuestionOne.add("K.S. Ranjitsinhji");
        Question questionOne = new Question("First Player to score an International Test Century?",optionsForQuestionOne,0);
        questions.add(questionOne);

        /* Question 2 */
        List<String> optionsForQuestionTwo = new ArrayList<String>();
        optionsForQuestionTwo.add("World Cricket Championship");
        optionsForQuestionTwo.add("Prudential World Cup");
        optionsForQuestionTwo.add("World Cricket Series");
        optionsForQuestionTwo.add("Cricket World Cup");
        Question questionTwo = new Question("What was the official name of the first World Cup?",optionsForQuestionTwo,1);
        questions.add(questionTwo);

        /* Question 3 */
        List<String> optionsForQuestionThree = new ArrayList<String>();
        optionsForQuestionThree.add("1 minute");
        optionsForQuestionThree.add("1.5 minutes");
        optionsForQuestionThree.add("2 minutes");
        optionsForQuestionThree.add("5 minutes");
        Question questionThree = new Question("How many minutes before play should the umpires take the field?",optionsForQuestionThree,3);
        questions.add(questionThree);

        /* Question 4 */
        List<String> optionsForQuestionFour = new ArrayList<String>();
        optionsForQuestionFour.add("1951-52");
        optionsForQuestionFour.add("1952-53");
        optionsForQuestionFour.add("1958-59");
        optionsForQuestionFour.add("1959-60");
        Question questionFour = new Question("India achieved its first Test victory against Australia in?",optionsForQuestionFour,3);
        questions.add(questionFour);

        /* Question 5 */
        List<String> optionsForQuestionFive = new ArrayList<String>();
        optionsForQuestionFive.add("Leeds");
        optionsForQuestionFive.add("Hove");
        optionsForQuestionFive.add("Chelmsford");
        optionsForQuestionFive.add("Leicester");
        Question questionFive = new Question("What town are the Essex County Cricket Club based in?",optionsForQuestionFive,2);
        questions.add(questionFive);

        /* Question 6 */
        List<String> optionsForQuestionSix = new ArrayList<String>();
        optionsForQuestionSix.add("Ravi Shastri");
        optionsForQuestionSix.add("Mohammad Azharuddin");
        optionsForQuestionSix.add("Sunil Gavaskar");
        optionsForQuestionSix.add("Sachin Tendulkar");
        Question questionSix = new Question("Who was the first Indian to cross 6000 runs mark in one day Internationals?",optionsForQuestionSix,1);
        questions.add(questionSix);

        /* Question 7 */
        List<String> optionsForQuestionSeven = new ArrayList<String>();
        optionsForQuestionSeven.add("Johhny Bairstow");
        optionsForQuestionSeven.add("Ben Stokes");
        optionsForQuestionSeven.add("Virat Kohli");
        optionsForQuestionSeven.add("Cheteshwar Pujara");
        Question questionSeven = new Question("Who did Pat Cummins claim as his 100th test match wicket during the 2019 series?",optionsForQuestionSeven,0);
        questions.add(questionSeven);

        /* Question 8 */
        List<String> optionsForQuestionEight = new ArrayList<String>();
        optionsForQuestionEight.add("Colombo");
        optionsForQuestionEight.add("Sharjah");
        optionsForQuestionEight.add("Vizag");
        optionsForQuestionEight.add("Dhaka");
        Question questionEight = new Question("The first Asia Cup was held at?",optionsForQuestionEight,1);
        questions.add(questionEight);

        /* Question 9 */
        List<String> optionsForQuestionNine = new ArrayList<String>();
        optionsForQuestionNine.add("Sachin Tendulkar");
        optionsForQuestionNine.add("Sunil Gavaskar");
        optionsForQuestionNine.add("Mohinder Amarnath");
        optionsForQuestionNine.add("Sanjay Manjrekar");
        Question questionNine = new Question("Name the only Indian player to have won a ‘MoM’ award in three consecutive matches?",optionsForQuestionNine,2);
        questions.add(questionNine);

        /* Question 10 */
        List<String> optionsForQuestionTen = new ArrayList<String>();
        optionsForQuestionTen.add("Kapil Dev");
        optionsForQuestionTen.add("Sunil Gavaskar");
        optionsForQuestionTen.add("Ian Botham");
        optionsForQuestionTen.add("Sir Donald Bradman");
        Question questionTen = new Question("Who is the only batsman to have hit a century in each innings of a Test thrice?",optionsForQuestionTen,1);
        questions.add(questionTen);

    }

    public Question getQuestionByIndex(int index){
        return questions.get(index);
    }
    public ArrayList<Question> getQuestions(){
        return questions;
    }
}

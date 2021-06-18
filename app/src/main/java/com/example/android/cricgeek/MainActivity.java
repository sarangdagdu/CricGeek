package com.example.android.cricgeek;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final QuestionsProvider qp = new QuestionsProvider();
    public static int questionIndexer;
    private final int[] userAnswers = new int[10];
    ArrayList<Question> listOfQuestions;
    Button startButton;
    Button buttonNext;
    FloatingActionButton buttonFinish;
    FrameLayout layout;
    TextView questionTextView;
    int testScore = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        qp.createQuestions();
        Arrays.fill(userAnswers, -1);
        listOfQuestions = qp.getQuestions();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        startButton = findViewById(R.id.button1);
        startButton.setOnClickListener(this);
        buttonNext = findViewById(R.id.buttonNext);
        buttonNext.setOnClickListener(this);
        buttonFinish = findViewById(R.id.finishActionButton);
        buttonFinish.setOnClickListener(this);
    }

    /**
     * Function to store the answer submitted by the user.
     * @param questionId : Integer sequence of question in the ArrayList
     * @param answerIndex : User provided option
     */
    private void storeUserAnswer(int questionId, int answerIndex) {
        userAnswers[questionId] = answerIndex;
    }

    private void setQuestionAndOptions(Question question) {
        questionTextView = findViewById(R.id.qestiontextView);
        questionTextView.setText(question.question);

        RadioButton optionOne = findViewById(R.id.ans1);
        RadioButton optionTwo = findViewById(R.id.ans2);
        RadioButton optionThree = findViewById(R.id.ans3);
        RadioButton optionFour = findViewById(R.id.ans4);

        optionOne.setText(question.options.get(0));
        optionTwo.setText(question.options.get(1));
        optionThree.setText(question.options.get(2));
        optionFour.setText(question.options.get(3));

        int selectedRadioButtonId = getUserSelectedOption();
        storeUserAnswer(questionIndexer, selectedRadioButtonId);
    }

    private int getUserSelectedOption() {
        int selectedAnswer = -1;

        RadioGroup optionsGroup = findViewById(R.id.radioGroup);

        int selectedRadioButtonId = optionsGroup.getCheckedRadioButtonId();

        RadioButton optionOne = findViewById(R.id.ans1);
        RadioButton optionTwo = findViewById(R.id.ans2);
        RadioButton optionThree = findViewById(R.id.ans3);
        RadioButton optionFour = findViewById(R.id.ans4);

        if (optionOne.getId() == selectedRadioButtonId) {
            selectedAnswer = 0;
        } else if (optionTwo.getId() == selectedRadioButtonId) {
            selectedAnswer = 1;
        } else if (optionThree.getId() == selectedRadioButtonId) {
            selectedAnswer = 2;
        } else if (optionFour.getId() == selectedRadioButtonId) {
            selectedAnswer = 3;
        }
        return selectedAnswer;
    }

    private int calculateScore(int[] userAnswers, ArrayList<Question> questionList) {
        Log.i("calculateScore", "Size of List" + questionList.size());
        for (int i = 0; i < questionList.size(); i++) {
            Log.i("calculateScore", "userAnswer[i]" + userAnswers[i]);
            Log.i("calculateScore", "correctIndex" + questionList.get(i).correctIndex);
            if (userAnswers[i] == questionList.get(i).correctIndex) {
                testScore++;
            }
        }
        return testScore;
    }

    @Override
    public void onClick(View v) {
        int clickedId = v.getId();
        if (clickedId == R.id.button1) {
            startButtonTap();
        } else if (clickedId == R.id.buttonNext) {
            nextButtonTap();
        } else if (clickedId == R.id.finishActionButton) {
            finishButtonTap();
        }

    }

    private void finishButtonTap() {
        int selectedOption = getUserSelectedOption();
        if (selectedOption != -1) {
            userAnswers[questionIndexer] = selectedOption;
        }

        AlertDialog.Builder scoreAlertBuilder = new AlertDialog.Builder(this);
        scoreAlertBuilder.setTitle(R.string.test_score);
        scoreAlertBuilder.setMessage(getString(R.string.your_score) + calculateScore(userAnswers, listOfQuestions) + getString(R.string.alert_ask_email));
        scoreAlertBuilder.setCancelable(true);

        scoreAlertBuilder.setPositiveButton(
                R.string.yes,
                (dialog, id) -> emailScore());

        scoreAlertBuilder.setNegativeButton(
                R.string.no,
                (dialog, id) -> dialog.dismiss());

        AlertDialog scoreAlert = scoreAlertBuilder.create();
        scoreAlert.show();


        TextView questionTextView = findViewById(R.id.qestiontextView);
        questionTextView.setText(R.string.dialog_negative_button_string);
        layout = findViewById(R.id.options_frameLayout);
        layout.setVisibility(View.INVISIBLE);
        buttonNext = findViewById(R.id.buttonNext);
        buttonNext.setVisibility(View.INVISIBLE);
        buttonFinish.setClickable(false);
    }

    private void nextButtonTap() {
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        int selectedAnswer = getUserSelectedOption();

        if (questionIndexer == 9) {
            Toast.makeText(getApplicationContext(), R.string.end_test_toast, Toast.LENGTH_LONG).show();
            buttonNext.setClickable(false);
            return;
        }

        storeUserAnswer(questionIndexer, selectedAnswer);
        questionIndexer++;

        radioGroup.clearCheck();
        Question question = qp.getQuestionByIndex(questionIndexer);
        setQuestionAndOptions(question);
    }

    private void startButtonTap() {
        startButton = findViewById(R.id.button1);
        layout = findViewById(R.id.options_frameLayout);
        layout.setVisibility(View.VISIBLE);
        buttonNext = findViewById(R.id.buttonNext);
        buttonNext.setVisibility(View.VISIBLE);
        FloatingActionButton buttonFinish = findViewById(R.id.finishActionButton);
        buttonFinish.setVisibility(View.VISIBLE);
        startButton.setVisibility(View.INVISIBLE);
        Question questionOne = listOfQuestions.get(questionIndexer);
        setQuestionAndOptions(questionOne);
    }

    private void emailScore() {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse(getString(R.string.mail_intent)));
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject));
        intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.email_body) + testScore +getString(R.string.email_signature));
        if (null != intent.resolveActivity(getPackageManager())) startActivity(intent);
    }
}
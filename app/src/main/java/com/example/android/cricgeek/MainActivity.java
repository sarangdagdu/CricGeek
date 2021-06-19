package com.example.android.cricgeek;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
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
    private final int[] userAnswers = new int[12];
    ArrayList<Question> listOfQuestions;
    Button startButton;
    Button buttonNext;
    FloatingActionButton buttonFinish;
    FrameLayout layout;
    TextView questionTextView;
    int testScore;
    int scoreForLastTwoQuestions;
    CheckBox checkBox1;
    CheckBox checkBox2;
    CheckBox checkBox3;
    CheckBox checkBox4;
    LinearLayout layout_edittext;
    LinearLayout layout_checks;
    EditText editText;


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
     *
     * @param questionId  : Integer sequence of question in the ArrayList
     * @param answerIndex : User provided option
     */
    private void storeUserAnswer(int questionId, int answerIndex) {
        userAnswers[questionId] = answerIndex;
    }

    /**
     * Function to set views with appropriate questions and options.
     *
     * @param question Question object that contains the
     *                 actual question statement and options.
     */
    private void setQuestionAndOptions(Question question) {
        questionTextView = findViewById(R.id.questionTextView);
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

    /**
     * Function to obtain the option selected by user for a particular
     * question.
     *
     * @return correct index ranging from 0 to 3
     * -1 if no option is selected
     */
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

    /**
     * Function to calculate the final score of user.
     *
     * @param userAnswers  Integer Array containing the answers of user.
     * @param questionList List of all the questions which hold the correct indices.
     * @return Integer score.
     */
    private int calculateScore(int[] userAnswers, ArrayList<Question> questionList) {
        for (int i = 0; i < questionList.size(); i++) {
            if (userAnswers[i] == questionList.get(i).correctIndex) {
                testScore++;
            }
        }
        return testScore;
    }

    /**
     * Since we have implemented OnClickListener this @Override method
     * is responsible for handling the onClick actions of all the buttons used
     * in the app.
     *
     * @param v View
     */
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

    /**
     * Actions to be performed when user clicks on Finish Button.
     */
    private void finishButtonTap() {
        int selectedOption = getUserSelectedOption();
        if (selectedOption != -1) {
            userAnswers[questionIndexer] = selectedOption;
        }
        if (questionIndexer > 9) {
            userAnswers[10] = userAnswers[11] = 0;
            if (checkBox1.isChecked() && checkBox2.isChecked()) {

                scoreForLastTwoQuestions++;
            }

            editText = findViewById(R.id.edit_text);
            if (editText.getText().toString().equalsIgnoreCase("bails")) {
                scoreForLastTwoQuestions++;
            }
        }
        testScore = (calculateScore(userAnswers, listOfQuestions) + scoreForLastTwoQuestions);
        AlertDialog.Builder scoreAlertBuilder = new AlertDialog.Builder(this);
        scoreAlertBuilder.setTitle(R.string.test_score);
        scoreAlertBuilder.setMessage(getString(R.string.your_score) + testScore + getString(R.string.alert_ask_email));
        scoreAlertBuilder.setCancelable(true);

        scoreAlertBuilder.setPositiveButton(
                R.string.yes,
                (dialog, id) -> emailScore());

        scoreAlertBuilder.setNegativeButton(
                R.string.no,
                (dialog, id) -> dialog.dismiss());

        AlertDialog scoreAlert = scoreAlertBuilder.create();
        Toast.makeText(this, getString(R.string.your_score) + testScore, Toast.LENGTH_LONG).show();
        scoreAlert.show();

        layout = findViewById(R.id.options_frameLayout);
        layout.setVisibility(View.INVISIBLE);
        buttonNext = findViewById(R.id.buttonNext);
        buttonNext.setVisibility(View.INVISIBLE);
        buttonFinish.setClickable(false);
    }

    /**
     * Actions to be performed when user clicks on Next Button.
     */
    private void nextButtonTap() {
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        int selectedAnswer = getUserSelectedOption();

        if (questionIndexer == 9) {
            storeUserAnswer(questionIndexer, selectedAnswer);
            showCheckboxQuestion();
            questionIndexer++;
        } else if (questionIndexer == 10) {
            showFreeTextQuestion();
            buttonNext.setVisibility(View.GONE);
        } else {
            storeUserAnswer(questionIndexer, selectedAnswer);
            questionIndexer++;
            radioGroup.clearCheck();
            Question question = qp.getQuestionByIndex(questionIndexer);
            setQuestionAndOptions(question);
        }
    }

    /**
     * This function is specifically written to display the question that has multiple
     * correct answers.
     */
    private void showCheckboxQuestion() {
        questionTextView.setText(R.string.question11);
        FrameLayout layout = findViewById(R.id.options_frameLayout);
        layout.setVisibility(View.INVISIBLE);

        checkBox1 = findViewById(R.id.checkbox1);
        checkBox1.setText(R.string.q11_optionOne);
        checkBox1.setVisibility(View.VISIBLE);

        checkBox2 = findViewById(R.id.checkbox2);
        checkBox2.setText(R.string.q11_optionTwo);
        checkBox2.setVisibility(View.VISIBLE);

        checkBox3 = findViewById(R.id.checkbox3);
        checkBox3.setText(R.string.q11_optionThree);
        checkBox3.setVisibility(View.VISIBLE);

        checkBox4 = findViewById(R.id.checkbox4);
        checkBox4.setText(R.string.q11_optionFour);
        checkBox4.setVisibility(View.VISIBLE);

        LinearLayout layout_checks = findViewById(R.id.checks_frame);
        layout_checks.setVisibility(View.VISIBLE);
        buttonNext.setClickable(true);
    }

    /**
     * This function is specifically written to display the question that has free
     * text form of answer.
     */
    private void showFreeTextQuestion() {
        questionTextView.setText(R.string.question12);
        FrameLayout layout = findViewById(R.id.options_frameLayout);
        layout.setVisibility(View.INVISIBLE);

        layout_checks = findViewById(R.id.checks_frame);
        layout_checks.setVisibility(View.INVISIBLE);

        layout_edittext = findViewById(R.id.edit_frame);
        layout_edittext.setVisibility(View.VISIBLE);

    }

    /**
     * Actions to be performed when user clicks on Start Test Button.
     */
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

    /**
     * Function responsible for allowing user to email the test score.
     */
    private void emailScore() {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse(getString(R.string.mail_intent)));
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject));
        intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.email_body) + testScore + getString(R.string.email_signature));
        try{
            startActivity(intent);
        }
        catch(ActivityNotFoundException exception){
            Toast.makeText(this,"No Email App found to send your score",Toast.LENGTH_LONG).show();
        }
    }
}
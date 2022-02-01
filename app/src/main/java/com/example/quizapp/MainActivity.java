package com.example.quizapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView mQuestions;
    private int mQuestionIndex = 0;
    private ProgressBar mProgressBar;
    private TextView mQuestionNumber;
    private int mUserScore = 0;



    private Button btnTrue;
    private Button btnFalse;
    private Button btnRestart;
    private QuizModel[] model = new QuizModel[] {
            new QuizModel(R.string.q1, false),
            new QuizModel(R.string.q2, true),
            new QuizModel(R.string.q3, false),
            new QuizModel(R.string.q4, false),
            new QuizModel(R.string.q5, false),
            new QuizModel(R.string.q6, true),
            new QuizModel(R.string.q7, false),
            new QuizModel(R.string.q8, true),
            new QuizModel(R.string.q9, true),
            new QuizModel(R.string.q10, false),
    };

    final int USER_PROGRESS = (int)Math.ceil(100.0 / model.length);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        mQuestions = findViewById(R.id.questionBox);
        mQuestions.setText(model[mQuestionIndex].getQuestion());
        mProgressBar = findViewById(R.id.progressBar);
        mQuestionNumber = findViewById(R.id.questionNumber);

        btnTrue = findViewById(R.id.trueButton);
        btnFalse = findViewById(R.id.falseButton);
        btnRestart = findViewById(R.id.restartButton);



        View.OnClickListener onButtonPress = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.trueButton) {
//                    Toast.makeText(getApplicationContext(), "true button pressed", Toast.LENGTH_SHORT).show();
                    evaluateAnswer(true);
                    mQuestionNumber.setText("Current Score: " + mUserScore);
                    changeQuestionOnClick();
                }
                else if (view.getId() == R.id.falseButton) {
//                    Toast.makeText(getApplicationContext(), "false button pressed", Toast.LENGTH_SHORT).show();
                    evaluateAnswer(false);
                    mQuestionNumber.setText("Current Score: " + mUserScore);
                    changeQuestionOnClick();
                }
                else{
//                    Toast.makeText(getApplicationContext(), "restart button pressed", Toast.LENGTH_SHORT).show();
                    mQuestionIndex = 0;
                    mQuestions = findViewById(R.id.questionBox);
                    mQuestions.setText(model[mQuestionIndex].getQuestion());
                    mUserScore = 0;
                    mQuestionNumber.setText("Current Score: " + mUserScore);
                    mProgressBar.setProgress(0);
                }
            }
        };
        btnTrue.setOnClickListener(onButtonPress);
        btnFalse.setOnClickListener(onButtonPress);
        btnRestart.setOnClickListener(onButtonPress);
    }

    private void changeQuestionOnClick (){
        mQuestionIndex = (mQuestionIndex+1)%10;

        if(mQuestionIndex == 0){
            AlertDialog.Builder quizAlert = new AlertDialog.Builder(this);
            quizAlert.setCancelable(false);
            quizAlert.setTitle("Quiz is Over");
            quizAlert.setMessage("Your score is: " + mUserScore + "\n Thank You");
            quizAlert.setPositiveButton("Close Application", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            quizAlert.show();
        }

        mQuestions = findViewById(R.id.questionBox);
        mQuestions.setText(model[mQuestionIndex].getQuestion());
        mProgressBar.incrementProgressBy(USER_PROGRESS);
    }
    private void evaluateAnswer(boolean guess){
        boolean correctAnswer = model[mQuestionIndex].isAnswer();
        if (guess == correctAnswer){
            Toast.makeText(getApplicationContext(), "Correct Answer", Toast.LENGTH_SHORT).show();
            mUserScore = mUserScore + 1;
        }
        else
            Toast.makeText(getApplicationContext(), "Incorrect Answer", Toast.LENGTH_SHORT).show();

    }
}
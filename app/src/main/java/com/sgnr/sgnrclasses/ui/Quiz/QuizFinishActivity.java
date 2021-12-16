package com.sgnr.sgnrclasses.ui.Quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.sgnr.sgnrclasses.R;

public class QuizFinishActivity extends AppCompatActivity {

    private TextView textCourseName,textQuestion,textCorrect,textWrong;
    private String courseName;
    private int totalQuestion,totalCorrect,totalWrong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_finish);

        Intent intent = getIntent();
        courseName = intent.getStringExtra("CourseName");
        totalQuestion = intent.getIntExtra("Total",0);
        totalCorrect = intent.getIntExtra("Correct",0);
        totalWrong = totalQuestion - totalCorrect;

        textCourseName = findViewById(R.id.resultCourseName);
        textQuestion = findViewById(R.id.textTotalQuestion);
        textCorrect = findViewById(R.id.textCorrect);
        textWrong = findViewById(R.id.textWrong);

        textCourseName.setText(courseName);
        textQuestion.setText(String.valueOf(totalQuestion));
        textCorrect.setText(String.valueOf(totalCorrect));
        textWrong.setText(String.valueOf(totalWrong));
    }
}
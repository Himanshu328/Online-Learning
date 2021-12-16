package com.sgnr.sgnrclasses.ui.Quiz;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sgnr.sgnrclasses.Api.ApiClient;
import com.sgnr.sgnrclasses.Api.ApiInterface;
import com.sgnr.sgnrclasses.Model.McqModel;
import com.sgnr.sgnrclasses.Model.McqModelResponse;
import com.sgnr.sgnrclasses.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Quiz2Activity extends AppCompatActivity {

    private ApiInterface apiInterface;
    private TextView question,course,textViewTime;
    private RadioGroup radioGroup;
    private RadioButton opt1,opt2,opt3,opt4,userAnswer;
    private Button buttonNext;
    private int score = 0,qid = 0;
    private List<McqModel> mcqModel;
    private String courseName;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz2);

        Intent intent = getIntent();
        courseName = intent.getStringExtra("quiz_course_name");

        question = findViewById(R.id.textViewQuizQuestion);
        opt1 = findViewById(R.id.quizOption1);
        opt2 = findViewById(R.id.quizOption2);
        opt3 = findViewById(R.id.quizOption3);
        opt4 = findViewById(R.id.quizOption4);
        buttonNext = findViewById(R.id.buttonQuizNext);
        course = findViewById(R.id.textQuizCourse_Name);
        textViewTime = findViewById(R.id.textViewTime);

        course.setText(courseName);

        Retrofit retrofit = ApiClient.getClient();
        apiInterface = retrofit.create(ApiInterface.class);

        apiInterface.getMcqQuestion(courseName).enqueue(new Callback<McqModelResponse>() {
            @Override
            public void onResponse(Call<McqModelResponse> call, Response<McqModelResponse> response) {
                try{
                    if(response != null){
                        if(response.body().getStatus().equals("1")){
                            mcqModel = response.body().getData();
                            startCountDown();
                            setQuestionView();
                            buttonNext.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    radioGroup = findViewById(R.id.radioGroup);
                                    userAnswer = findViewById(radioGroup.getCheckedRadioButtonId());
                                    Log.d("Answer",mcqModel.get(qid).getAnswer() +" "+userAnswer.getText());
                                    if(mcqModel.get(qid).getAnswer().equals(userAnswer.getText())){
                                        score++;
                                        Log.d("Score","Score:"+score);
                                    }
                                    qid++;
                                    if(qid<mcqModel.size()){
                                        setQuestionView();
                                        radioGroup.clearCheck();
                                    }else{
                                        Toast.makeText(Quiz2Activity.this,"Score: "+score,Toast.LENGTH_LONG).show();
                                        Intent intent1 = new Intent(Quiz2Activity.this,QuizFinishActivity.class);
                                        intent1.putExtra("Correct",score);
                                        intent1.putExtra("CourseName",courseName);
                                        intent1.putExtra("Total",mcqModel.size());
                                        startActivity(intent1);
                                    }
                                }
                            });

                        }else {
                            Log.d("Quiz",response.body().getMessage());
                        }
                    }

                }catch(Exception e){
                    Log.d("failure",e.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(Call<McqModelResponse> call, Throwable throwable) {
                Log.d("failure",throwable.getLocalizedMessage());
            }
        });
    }

    private void setQuestionView(){
        question.setText(mcqModel.get(qid).getQuestion());
        opt1.setText(mcqModel.get(qid).getOption_1());
        opt2.setText(mcqModel.get(qid).getOption_2());
        opt3.setText(mcqModel.get(qid).getOption_3());
        opt4.setText(mcqModel.get(qid).getOption_4());
    }

    private void startCountDown(){
        countDownTimer = new CountDownTimer(3600000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                NumberFormat numberFormat = new DecimalFormat("00");
                long min = (millisUntilFinished / 60000) % 60;
                long sec = (millisUntilFinished / 1000) % 60;
                textViewTime.setText(numberFormat.format(min) + ":"+numberFormat.format(sec));

                if(min == 00 && sec == 00){
                    showDialog();
                }
            }

            @Override
            public void onFinish() {
                textViewTime.setText("00:00");
            }
        }.start();

    }

    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setIcon(R.drawable.ic_timer_off);
        builder.setTitle("Timer");
        builder.setMessage("Time is Running out!");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent1 = new Intent(Quiz2Activity.this,QuizFinishActivity.class);
                intent1.putExtra("Correct",score);
                intent1.putExtra("CourseName",courseName);
                intent1.putExtra("Total",mcqModel.size());
                startActivity(intent1);
            }
        });
        builder.create();
        builder.show();
    }
}
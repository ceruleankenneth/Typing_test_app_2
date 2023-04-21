package com.example.typing_test_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class RecordDetailsActivity extends AppCompatActivity {
    TextView correctTV;
    TextView speedTV;
    TextView dateTV;
    TextView accuracyTV;
    TextView wrongTV;
    TextView TestModeTV;
    ImageButton backButton;
    String[] difficultyString = {"easy", "normal","hard","nightmare"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_details);
        correctTV = findViewById(R.id.RD_right_word_data);
        speedTV = findViewById(R.id.RD_speed_data);
        dateTV = findViewById(R.id.RD_date_data);
        accuracyTV = findViewById(R.id.RD_accuracy_data);
        wrongTV = findViewById(R.id.RD_wrong_word_data);
        TestModeTV = findViewById(R.id.RD_test_mode_data);
        backButton = findViewById(R.id.back_button_detail);

        Bundle extras = getIntent().getExtras();

        String speed = extras.getString("speed");
        String accuracy = extras.getString("accuracy");
        String correct = extras.getString("correct");
        String wrong = extras.getString("wrong");
        String date = extras.getString("date");
        String difficulty = extras.getString("difficulty");
        int gameType = Integer.parseInt(extras.getString("gameType"));

        correctTV.setText(correct);
        if(gameType == 0) {
            speedTV.setText(speed+"CPM");
        }
        if(gameType == 1){
            speedTV.setText(speed+"WPM");
        }

        dateTV.setText(date);
        accuracyTV.setText(accuracy+"%");
        wrongTV.setText(wrong);
        if(gameType == 0) {
            TestModeTV.setText("N/A");
        }
        if(gameType == 1){
            TestModeTV.setText(difficultyString[Integer.parseInt(difficulty)]);
        }


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(gameType == 0) {
                    Intent i = new Intent(RecordDetailsActivity.this, CharHistoryActivity.class);
                    startActivity(i);
                }
                if(gameType == 1){
                    Intent i = new Intent(RecordDetailsActivity.this, WordHistoryActivity.class);
                    startActivity(i);
                }

            }
        });
    }
}
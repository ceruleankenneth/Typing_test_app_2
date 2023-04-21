package com.example.typing_test_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class TestHistoryActivity extends AppCompatActivity {

    ImageButton backButton;
    private Button btn_char;
    private Button btn_word;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_history);

        btn_char = findViewById(R.id.btn_char);
        btn_word = findViewById(R.id.btn_word);
        backButton = findViewById(R.id.back_button_history);


        btn_char.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestHistoryActivity.this, CharHistoryActivity.class);
                startActivity(intent);

            }
        });


        btn_word.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestHistoryActivity.this,WordHistoryActivity.class);
                startActivity(intent);

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i =new Intent(TestHistoryActivity.this,MainScreenActivity.class);
                startActivity(i);


            }
        });
    }

}
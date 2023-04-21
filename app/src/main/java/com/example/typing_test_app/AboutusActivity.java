package com.example.typing_test_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class AboutusActivity extends AppCompatActivity {

    ImageButton backButton;
    Button emailButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);

        backButton = findViewById(R.id.back_button_about_us);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AboutusActivity.this,MainScreenActivity.class);
                startActivity(i);
            }
        });

        emailButton = findViewById(R.id.about_btn1);
        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String recipient = "cc.car@hkcc-polyu.edu.hk ";

                Intent i = new Intent(Intent.ACTION_SEND);
                i.putExtra(Intent.EXTRA_EMAIL,recipient);

                i.setType("message/rfc882");
                startActivity(i);

            }
        });
    }
}

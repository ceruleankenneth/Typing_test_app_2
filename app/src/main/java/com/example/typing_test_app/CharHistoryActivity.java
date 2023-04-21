package com.example.typing_test_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

public class CharHistoryActivity extends AppCompatActivity {
    ImageButton backbutton2;
    ListView charRecordList;

    private  final int gameType = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_char_history1);
        backbutton2 =findViewById(R.id.back_button_history_Character1);
        charRecordList = findViewById(R.id.LV_char);

        JSONReader jsonReader = new JSONReader(this);
        jsonReader.getJSONData(gameType);
        ArrayList<String> date = jsonReader.getDateList();
        ArrayList<String> accuracy = jsonReader.getAccuracyList();
        ArrayList<String> speed =jsonReader.getSpeedList();
        ArrayList<String> difficulty =jsonReader.getDifficulty();

        Log.d("Kenneth","Char date:" + date.get(0));

        HistoryListAdapter charHistoryListAdapter = new HistoryListAdapter(this ,date,accuracy,speed,difficulty,gameType);
        charRecordList.setAdapter(charHistoryListAdapter);
        charRecordList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                ArrayList<String> correct = jsonReader.getCorrectList();
                ArrayList<String> wrong =jsonReader.getWrongList();

                Intent i = new Intent(getApplicationContext(),RecordDetailsActivity.class);
                i.putExtra("speed",speed.get(position));
                i.putExtra("accuracy",accuracy.get(position));
                i.putExtra("correct",correct.get(position));
                i.putExtra("wrong",wrong.get(position));
                i.putExtra("date",date.get(position));
                i.putExtra("difficulty",difficulty.get(position));
                i.putExtra("gameType",String.valueOf(gameType));

                startActivity(i);
            }
        });




        backbutton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(CharHistoryActivity.this,TestHistoryActivity.class);
                startActivity(i);
            }
        });
    }

}
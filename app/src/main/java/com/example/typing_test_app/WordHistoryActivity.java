package com.example.typing_test_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

public class WordHistoryActivity extends AppCompatActivity {

    ImageButton backbutton3;
    ListView wordRecordList;
    private final int gameType = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_history);
        backbutton3 =findViewById(R.id.back_button_history_Word2);
        wordRecordList = findViewById(R.id.LV_word);
        //get data form json
        JSONReader jsonReader = new JSONReader(this);
        jsonReader.getJSONData(gameType);
        ArrayList<String> date = jsonReader.getDateList();
        ArrayList<String> accuracy = jsonReader.getAccuracyList();
        ArrayList<String> speed =jsonReader.getSpeedList();
        ArrayList<String> difficulty =jsonReader.getDifficulty();

        HistoryListAdapter historyListAdapter = new HistoryListAdapter(this ,date,accuracy,speed,difficulty,gameType);
        wordRecordList.setAdapter(historyListAdapter);
        wordRecordList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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




        backbutton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(WordHistoryActivity.this,TestHistoryActivity.class);
                startActivity(i);
            }
        });
    }

}
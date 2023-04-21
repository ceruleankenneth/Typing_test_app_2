package com.example.typing_test_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class AchievementActivity extends AppCompatActivity {
    ImageButton backButton;
    ImageView unlockTV_1;
    ImageView unlockTV_2;
    ImageView unlockTV_3;
    ImageView unlockTV_4;
    JSONArray charDataArray;
    JSONArray wordDataArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement);

        backButton = findViewById(R.id.back_button_achievement);
        unlockTV_1 = findViewById(R.id.unlock_1);
        unlockTV_2 = findViewById(R.id.unlock_2);
        unlockTV_3 = findViewById(R.id.unlock_3);
        unlockTV_4 = findViewById(R.id.unlock_4);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AchievementActivity.this,MainScreenActivity.class);
                startActivity(i);
            }
        });

        //get JSON data
        try{
            File file = new File(getApplication().getFilesDir(),"testRecord.json");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line = bufferedReader.readLine();
            while (line != null) {
                stringBuilder.append(line).append("\n");
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
            String currentJSONSting = stringBuilder.toString();

            JSONObject currentRootObject = new JSONObject(currentJSONSting);
            charDataArray = currentRootObject.getJSONArray("charData");
            wordDataArray = currentRootObject.getJSONArray("wordData");
        }
        catch (Exception e){
            e.printStackTrace();
        }

        unlock();
    }

    public void unlock(){
        int correct = 0;
        int wrong = 10000;
        int tempCorrect;
        int tempWrong;
        JSONObject tempObject;
        try{
            for (int i =0;i < charDataArray.length();i++){
                tempObject = charDataArray.getJSONObject(i);
                tempCorrect = Integer.parseInt(tempObject.getString("correct"));
                tempWrong = Integer.parseInt(tempObject.getString("wrong"));

                if(correct < tempCorrect){
                    correct = tempCorrect;
                }
                if(wrong > tempWrong){
                    wrong = tempWrong;
                }
            }
            for (int i =0;i < wordDataArray.length();i++){
                tempObject = wordDataArray.getJSONObject(i);
                tempCorrect = Integer.parseInt(tempObject.getString("correct"));
                tempWrong = Integer.parseInt(tempObject.getString("wrong"));

                if(correct < tempCorrect){
                    correct = tempCorrect;
                }
                if(wrong > tempWrong){
                    wrong = tempWrong;
                }
                Log.d("Kenneth","Correct: "+tempCorrect);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        Log.d("Kenneth","Correct: "+correct);
        Log.d("Kenneth","Wrong: "+wrong);

        if (correct >= 5) {
            unlockTV_1.setVisibility(View.VISIBLE);
        }
        if (correct >= 30) {
            unlockTV_2.setVisibility(View.VISIBLE);
        }
        if (correct >=60) {
            unlockTV_3.setVisibility(View.VISIBLE);
        }
        if (wrong ==0) {
            unlockTV_4.setVisibility(View.VISIBLE);
        }


    }
}


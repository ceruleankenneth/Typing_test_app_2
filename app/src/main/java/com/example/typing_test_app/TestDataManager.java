package com.example.typing_test_app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;

public class TestDataManager {
    private int correct = 0;
    private int wrong = 0;
    private double accuracy = 0;
    private int speed = 0;
    private int gameType;
    private int difficulty;
    private static final long START_TIME_IN_SECOND = 60000;
    private CountDownTimer countDownTimer;
    private boolean timerRunning = false;
    private long timeLeftInMillis = START_TIME_IN_SECOND;
    private Context context;
    private final String dataFileName = "testRecord";


    TestDataManager(Context context,int gameType){
        this.context = context;
        this.gameType = gameType;
    }

    public void setDataView(int color){
        if(color == Color.BLACK){
            correct++;
        }
        if(color == Color.RED){
            wrong++;
        }

        accuracy = (((double)correct) / ((double)correct+wrong)) *100;
        //Log.d("Kenneth","accuracy: "+accuracy);
        speed = correct + wrong;
        //Log.d("Kenneth","speed: "+speed);

        String accuracyFormatted = String.format(Locale.getDefault(),"%d%%",(int)accuracy);
        //Log.d("Kenneth","accuracyFormatted: "+ accuracyFormatted);
        if(gameType == 1){
            String speedFormatted = String.format(Locale.getDefault(),"%d WPM",speed);
            //Log.d("Kenneth","speedFormatted: "+ speedFormatted);

            TextView correctTextView = ((Activity)context).findViewById(R.id.WT_correct_data);
            TextView wrongTextView = ((Activity)context).findViewById(R.id.WT_wrong_data);
            TextView accuracyTextView = ((Activity)context).findViewById(R.id.WT_accuracy_data);
            TextView speedTextView = ((Activity)context).findViewById(R.id.WT_speed_data);

            correctTextView.setText(String.valueOf(correct));
            wrongTextView.setText(String.valueOf(wrong));
            accuracyTextView.setText(accuracyFormatted);
            speedTextView.setText(speedFormatted);
        }
        if(gameType == 0){
            String speedFormatted = String.format(Locale.getDefault(),"%d CPM",speed);

            TextView correctTextView = ((Activity)context).findViewById(R.id.CT_correct_data);
            TextView wrongTextView = ((Activity)context).findViewById(R.id.CT_wrong_data);
            TextView accuracyTextView = ((Activity)context).findViewById(R.id.CT_accuracy_data);
            TextView speedTextView = ((Activity)context).findViewById(R.id.CT_speed_data);

            correctTextView.setText(String.valueOf(correct));
            wrongTextView.setText(String.valueOf(wrong));
            accuracyTextView.setText(accuracyFormatted);
            speedTextView.setText(speedFormatted);
        }
    }

    public void startTimer(){
        try{
            countDownTimer = new CountDownTimer(timeLeftInMillis,1000) {
                @Override
                public void onTick(long l) {
                    timeLeftInMillis = l;
                    updateCountDownText();
                }

                @Override
                public void onFinish() {
                    saveData();
                    timerRunning =false;
                    resetTimer();
                    enableSpinner(); //timer finish enable back spinner
                    reset();
                    Log.d("Kenneth", "onFinish");
                    openDialog();
                }
            }.start();
            timerRunning = true;
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public boolean getTimerRunning(){
        return timerRunning;
    }
    public void resetTimer(){
        try{
            timeLeftInMillis = START_TIME_IN_SECOND;
            updateCountDownText();
            countDownTimer.cancel();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public void updateCountDownText(){
        int seconds = (int) (timeLeftInMillis /1000);

        String timeLeftFormatted = String.format(Locale.getDefault(),"%2d",seconds);
        if(gameType == 1){
            TextView textView = ((Activity)context).findViewById(R.id.WT_time_data);
            textView.setText(timeLeftFormatted);
        }
        if(gameType == 0){
            TextView textView = ((Activity)context).findViewById(R.id.CT_time_data);
            textView.setText(timeLeftFormatted);
        }
    }

    public void enableSpinner(){
        if(gameType == 1){
            Spinner spinner = ((Activity)context).findViewById(R.id.difficultySpinner);
            spinner.setEnabled(true);
            spinner.setClickable(true);
        }
    }

    public void saveData(){
        Log.d("Kenneth", "saveData");
        File file = new File(context.getFilesDir(), "testRecord.json");
        String jsonString = "";
        Calendar calendar = Calendar.getInstance();
        String currentTime = DateFormat.getDateInstance().format(calendar.getTime());

        //file do not exist
        try {

            Log.d("Kenneth", "on try");
            if (!file.exists()) {
                Log.d("Kenneth", "file does not exists");

                JSONObject rootObject = new JSONObject();
                JSONArray charDataArray = new JSONArray();
                JSONArray wordDataArray = new JSONArray();
                JSONObject setOfData = new JSONObject();


                setOfData.put("date", currentTime);
                setOfData.put("correct", correct);
                setOfData.put("wrong", wrong);
                setOfData.put("accuracy", (int) accuracy);
                setOfData.put("speed", speed);
                setOfData.put("difficulty", difficulty);

                if (gameType == 0) {
                    charDataArray.put(setOfData);
                }
                if (gameType == 1) {
                    wordDataArray.put(setOfData);
                }

                rootObject.put("charData", charDataArray);
                rootObject.put("wordData", wordDataArray);

                jsonString = rootObject.toString();

            } else {
                Log.d("Kenneth", "file exists");

                // get data form JSON
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
                Log.d("Kenneth","current: "+currentJSONSting);

                //convert data String to JSON Object
                JSONObject currentRootObject = new JSONObject(currentJSONSting);
                JSONArray currentCharDataArray = currentRootObject.getJSONArray("charData");
                JSONArray currentWordDataArray = currentRootObject.getJSONArray("wordData");
                JSONObject setOfData = new JSONObject();

                setOfData.put("date", currentTime);
                setOfData.put("correct", correct);
                setOfData.put("wrong", wrong);
                setOfData.put("accuracy", (int) accuracy);
                setOfData.put("speed", speed);
                setOfData.put("difficulty", difficulty);

                if (gameType == 0) {
                    Log.d("Trace", "add to char");
                    currentCharDataArray.put(setOfData);
                }
                if (gameType == 1) {
                    Log.d("Trace", "add to word");
                    currentWordDataArray.put(setOfData);
                }

                jsonString = currentRootObject.toString();
            }

            //writer data to JSON
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(jsonString);
            bufferedWriter.close();


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void reset(){

        correct = 0;
        wrong = 0;
        accuracy = 0;
        speed = 0;
        TextGenerator textGenerator = new TextGenerator(context,gameType,difficulty);

        if(gameType == 1){

            TextView correctTextView = ((Activity)context).findViewById(R.id.WT_correct_data);
            TextView wrongTextView = ((Activity)context).findViewById(R.id.WT_wrong_data);
            TextView accuracyTextView = ((Activity)context).findViewById(R.id.WT_accuracy_data);
            TextView speedTextView = ((Activity)context).findViewById(R.id.WT_speed_data);
            TextView wordShow = ((Activity)context).findViewById(R.id.word_show);
            TextView originalTextView = ((Activity)context).findViewById(R.id.WT_original_text);
            TextView typedTextView = ((Activity)context).findViewById(R.id.WT_typed_text);

            correctTextView.setText("");
            wrongTextView.setText("");
            accuracyTextView.setText("");
            speedTextView.setText("");
            wordShow.setText(textGenerator.getTextInRandom());
            originalTextView.setText("");
            typedTextView.setText("");
        }

        if(gameType == 0){

            TextView correctTextView = ((Activity)context).findViewById(R.id.CT_correct_data);
            TextView wrongTextView = ((Activity)context).findViewById(R.id.CT_wrong_data);
            TextView accuracyTextView = ((Activity)context).findViewById(R.id.CT_accuracy_data);
            TextView speedTextView = ((Activity)context).findViewById(R.id.CT_speed_data);
            TextView wordShow = ((Activity)context).findViewById(R.id.char_show);
            TextView originalTextView = ((Activity)context).findViewById(R.id.CT_original_text);
            TextView typedTextView = ((Activity)context).findViewById(R.id.CT_typed_text);

            correctTextView.setText("");
            wrongTextView.setText("");
            accuracyTextView.setText("");
            speedTextView.setText("");
            wordShow.setText(textGenerator.getTextInRandom());
            originalTextView.setText("");
            typedTextView.setText("");
        }
    }

    public void setDifficulty(int i){
        difficulty = i;
    }

    public void openDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Time up").
                setMessage("Time is up and data have been saved.")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        builder.create().show();
    }
}

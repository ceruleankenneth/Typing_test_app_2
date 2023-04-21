package com.example.typing_test_app;

import android.content.Context;
import android.util.Log;
import android.widget.ExpandableListAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class JSONReader {
    private Context context;
    private ArrayList<String> date = new ArrayList<>();
    private ArrayList<String> correct = new ArrayList<>();
    private ArrayList<String> wrong = new ArrayList<>();
    private ArrayList<String> accuracy = new ArrayList<>();
    private ArrayList<String> speed = new ArrayList<>();;
    private ArrayList<String> difficulty = new ArrayList<>();

    JSONReader(Context context){
        this.context = context;
    }


    public void getJSONData(int gameType){

        try {

            //debug
            StringBuilder stringBuilder1 = new StringBuilder();
            StringBuilder stringBuilder2 = new StringBuilder();
            StringBuilder stringBuilder3 = new StringBuilder();
            StringBuilder stringBuilder4 = new StringBuilder();
            StringBuilder stringBuilder5 = new StringBuilder();
            StringBuilder stringBuilder6 = new StringBuilder();

            //get JSON data
            File file = new File(context.getFilesDir(),"testRecord.json");
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

            Log.d("Kenneth","JSON: "+currentJSONSting);

            //convert String to JSON object
            JSONObject currentRootObject = new JSONObject(currentJSONSting);
            JSONArray currentCharDataArray = currentRootObject.getJSONArray("charData");
            Log.d("Kenneth","CharArray: "+currentCharDataArray);

            JSONArray currentWordDataArray = currentRootObject.getJSONArray("wordData");
            Log.d("Kenneth","WordArray: "+currentJSONSting);

            JSONObject tempObject;

            if (gameType == 0) {

                //get set of data in a JSON array
                for(int i=0;i < currentCharDataArray.length();i++){

                    tempObject = currentCharDataArray.getJSONObject(i);

                    date.add(tempObject.getString("date"));
                    correct.add(tempObject.getString("correct"));
                    wrong.add(tempObject.getString("wrong"));
                    accuracy.add(tempObject.getString("accuracy"));
                    speed.add(tempObject.getString("speed"));
                    difficulty.add(tempObject.getString("difficulty"));


                    stringBuilder1.append(date.get(i).toString()).append(", ");
                    stringBuilder2.append(correct.get(i)).append(", ");
                    stringBuilder3.append(wrong.get(i)).append(", ");
                    stringBuilder4.append(accuracy.get(i)).append(", ");
                    stringBuilder5.append(speed.get(i)).append(", ");
                    stringBuilder6.append(difficulty.get(i)).append(", ");

                }
            }
            if(gameType == 1){

                for(int i=0;i < currentWordDataArray.length();i++){

                    tempObject = currentWordDataArray.getJSONObject(i);

                    date.add(tempObject.getString("date"));
                    correct.add(tempObject.getString("correct"));
                    wrong.add(tempObject.getString("wrong"));
                    accuracy.add(tempObject.getString("accuracy"));
                    speed.add(tempObject.getString("speed"));
                    difficulty.add(tempObject.getString("difficulty"));


                    stringBuilder1.append(date.get(i)).append(", ");
                    stringBuilder2.append(correct.get(i)).append(", ");
                    stringBuilder3.append(wrong.get(i)).append(", ");
                    stringBuilder4.append(accuracy.get(i)).append(", ");
                    stringBuilder5.append(speed.get(i)).append(", ");
                    stringBuilder6.append(difficulty.get(i)).append(", ");
                }
            }

            Log.d("Kenneth","date: "+stringBuilder1.toString());
            Log.d("Kenneth","correct: "+stringBuilder2.toString());
            Log.d("Kenneth","wrong: "+stringBuilder3.toString());
            Log.d("Kenneth","accuracy: " +stringBuilder4.toString());
            Log.d("Kenneth","speed: "+stringBuilder5.toString());
            Log.d("Kenneth","difficulty: "+stringBuilder5.toString());


        }
        catch(Exception exception){
            exception.printStackTrace();
        }
    }

    public ArrayList<String> getDateList(){
        return date;
    }
    public ArrayList<String> getCorrectList(){
        return correct;
    }
    public ArrayList<String> getWrongList(){
        return wrong;
    }
    public ArrayList<String> getAccuracyList(){
        return accuracy;
    }
    public ArrayList<String> getSpeedList(){
        return speed;
    }
    public ArrayList<String> getDifficulty() { return difficulty;}
}

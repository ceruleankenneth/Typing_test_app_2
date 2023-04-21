package com.example.typing_test_app;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.dynamicanimation.animation.SpringAnimation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.Random;

public class TextGenerator{

    int gameType;
    int gameDifficulty;
    JSONArray textArray;

    //index 00 for char test, index 10 to 13 for word test in
    String[][] typeArray = {{"char"},{"eng100","eng250","eng1000","eng3000"}};
    SpannableStringBuilder typedStringBuilder = new SpannableStringBuilder();
    SpannableStringBuilder originalStringBuilder = new SpannableStringBuilder();

    public TextGenerator(Context context,int type,int difficulty){
        gameType = type;
        gameDifficulty = difficulty;
        //Log.d("Kenneth","run the constructor");
        //read json file
        StringBuilder jsonBuilder = new StringBuilder();
        try {
            //Log.d("Kenneth","in the try1");
            InputStream is = context.getAssets().open("wordlist.json");
            //Log.d("Kenneth","in the try2");
            while(true){
                int ch = is.read();
                if(ch == -1) break;
                else jsonBuilder.append((char)ch);
                //Log.d("Kenneth","get the json");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        parseJSONObject(jsonBuilder.toString());
    }


    public void parseJSONObject(String json){

        try{
            JSONObject rootObject = new JSONObject(json);

            //get difference array by the game type
            textArray = rootObject.getJSONArray(typeArray[gameType][gameDifficulty]);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getTextInRandom(){ //get a word in the array at random
        String randomSting="";
        Random randomIndex = new Random();
        try{
            randomSting = textArray.getString(randomIndex.nextInt(textArray.length()));
            //Log.d("Kenneth",String.valueOf(textArray.length()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return randomSting;
    }

    public void setGameDifficulty(int difficulty){
        gameDifficulty = difficulty;
    }

    public SpannableStringBuilder setOriginalText(String string) {
        originalStringBuilder.append(string).append(" ");
        return originalStringBuilder;
    }
    public SpannableStringBuilder setTypedText(String string,int color ){

        //Log.d("Kenneth",string);
        SpannableString spannableString = new SpannableString(string);
        spannableString.setSpan(new ForegroundColorSpan(color),0,string.length(),0);
        typedStringBuilder.append(spannableString).append(" ");
        return typedStringBuilder;
    }

    public void clearStringBuilder(){
        originalStringBuilder.clear();
        typedStringBuilder.clear();
    }
}

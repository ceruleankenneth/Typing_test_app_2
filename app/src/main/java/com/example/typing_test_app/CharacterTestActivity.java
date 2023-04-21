package com.example.typing_test_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

public class CharacterTestActivity extends AppCompatActivity {

    ImageButton backButton;
    TextView originalText;
    TextView typedText;
    TextView ctShow;
    EditText wordTyped;
    final int difficulty = 0;//word difficulty default is 0 easy
    final int GameType = 0; //Charactor
    int color = Color.BLACK;
    boolean corrected = false;
    String character;
    StringBuilder originalStringBuilder = new StringBuilder();
    StringBuilder typedStringBuilder = new StringBuilder();
    TextGenerator wordGenerator;
    TestDataManager charDataManger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_test);
        initializeViews();
        wordBuilder();
        getWord();
        setCTShow();
        charDataManger = new TestDataManager(this,GameType);

        //set listener
        backButton.setOnClickListener(onClickListener);
        wordTyped.addTextChangedListener(textWatcher);

    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(CharacterTestActivity.this,MainScreenActivity.class);
            startActivity(i);
        }
    };

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            String result = editable.toString().replaceAll(" ", "");
            char ch = ' ';
            //Log.d("Kenneth",result);
            if (!editable.toString().equals(result)) { //if it have space delete the space
                wordTyped.setText(result);
                wordTyped.setSelection(result.length());
                // alert the user
            }
            //Log.d("Kenneth",String.valueOf(editable.toString().indexOf(" ")));
            if (result.length() != 0){
                textChecker();

                if(!charDataManger.getTimerRunning()){
                    Log.d("Kenneth","timer start");
                    charDataManger.startTimer();   //start the timer
                    wordGenerator.clearStringBuilder();
                }

                originalText.setText(wordGenerator.setOriginalText(character)); //set word to original paragraph
                typedText.setText(wordGenerator.setTypedText(wordTyped.getText().toString(),color)); //set the typed paragraph to typed text
                getWord();
                setCTShow();
                wordTyped.setText("");



                charDataManger.setDataView(color);
            }
        }
    };

    public void initializeViews(){
        originalText = findViewById(R.id.CT_original_text);
        backButton = findViewById(R.id.back_button_char_test);
        ctShow = findViewById(R.id.char_show);
        typedText = findViewById(R.id.CT_typed_text);
        wordTyped = findViewById(R.id.char_type_view);
        originalText.setMovementMethod(new ScrollingMovementMethod());
        typedText.setMovementMethod(new ScrollingMovementMethod());
    }

    public void wordBuilder(){
        wordGenerator = new TextGenerator(this,GameType, difficulty);
    }

    public void getWord(){
        character = wordGenerator.getTextInRandom();
    }

    public void setCTShow(){
        ctShow.setText(character);
    }

    public void textChecker(){
        corrected = false;
        //Log.d("Kenneth",charSequence.toString());
        //wordDisplay.setSpan(new ForegroundColorSpan(Color.GREEN),0,position+1,0);
        String wordString,typedString;
        wordString = character;
        typedString = wordTyped.getText().toString();
        try{

            if(wordString.charAt(0) == typedString.charAt(0)){
                //Log.d("Kenneth","wordString"+wordString.charAt(i));
                //Log.d("Kenneth","typedString"+typedString.charAt(i));
                color = Color.BLACK;
                //Log.d("Kenneth","Correct");
            }
            else {
                //Log.d("Kenneth","Wrong");
                color = Color.RED;
            }
            //Log.d("Kenneth","all char are correct: "+correct);

        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
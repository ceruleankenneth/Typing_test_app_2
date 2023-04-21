package com.example.typing_test_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

public class WordTestActivity extends AppCompatActivity{

    ImageButton backButton;
    TextView originalText;
    TextView typedTextView;
    TextView difficultyText;
    TextView wordShow;
    EditText wordEditText;
    Spinner difficultySpin;
    int difficulty = 0;//word difficulty default is 0 easy
    int color = Color.BLACK;
    final int GameType = 1; //word
    boolean corrected = false;
    boolean timerStarted = false;
    String[] difficultyString = {"easy", "normal","hard","nightmare"};
    StringBuilder originalStringBuilder = new StringBuilder();
    StringBuilder typedStringBuilder = new StringBuilder();
    TextGenerator wordGenerator;
    SpannableStringBuilder wordDisplayBuilder = new SpannableStringBuilder();
    SpannableString wordDisplay;
    SpannableStringBuilder typedDisplayBuilder = new SpannableStringBuilder();
    TestDataManager wordDataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_test);
        initializeViews();
        wordDataManager = new TestDataManager(this , GameType);

        //set listener
        backButton.setOnClickListener(onClickListener);
        difficultySpin.setOnItemSelectedListener(onItemSelectedListener);
        wordEditText.addTextChangedListener(textWatcher);


        //spin adapter
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, difficultyString);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        difficultySpin.setAdapter(aa);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(WordTestActivity.this,MainScreenActivity.class);
            startActivity(i);
        }
    };
    private AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
            difficultyText.setText(difficultyString[position]);
            difficulty = position;
            wordDataManager.setDifficulty(difficulty);
            textBuilder(); //create a new word builder
            getWord();
            setWordDisplay();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    private TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            textChecker();
        }

        @Override
        public void afterTextChanged(Editable editable) {
            String result = editable.toString().replaceAll(" ", "");
            //Log.d("Kenneth",result);
            if (!editable.toString().equals(result)) { //if it have space delete the space
                wordEditText.setText(result);
                wordEditText.setSelection(result.length());
                // alert the user
            }
            if (editable.toString().indexOf(" ") >0 ){
                if(color == Color.GREEN && wordShow.length() == wordEditText.getText().length()){
                    color = Color.BLACK;
                    //Log.d("Kenneth","In black");
                }
                else{
                    color = Color.RED;
                    //Log.d("Kenneth",String.valueOf(color));
                    //Log.d("Kenneth","In red");
                }
                wordDataManager.setDataView(color);

                if(!wordDataManager.getTimerRunning()){
                    Log.d("Kenneth","timer start");
                    wordDataManager.startTimer();   //start the timer
                    wordGenerator.clearStringBuilder();
                }

                originalText.setText(wordGenerator.setOriginalText(wordDisplay.toString())); //set word to original paragraph
                typedTextView.setText(wordGenerator.setTypedText(wordEditText.getText().toString(), color)); //set the typed paragraph to typed text
                getWord();
                setWordDisplay();
                wordEditText.setText("");

                //Log.d("Kenneth",String.valueOf(color));


                difficultySpin.setEnabled(false);  //timer start disable difficulty spinner
                difficultySpin.setClickable(false);

                //Log.d("Kenneth","string is >0");
            }
        }
    };

    public void initializeViews(){
        originalText = findViewById(R.id.WT_original_text);
        backButton = findViewById(R.id.back_button_word_test);
        difficultySpin = findViewById(R.id.difficultySpinner);
        difficultyText = findViewById(R.id.difficultytext);
        wordShow = findViewById(R.id.word_show);
        typedTextView = findViewById(R.id.WT_typed_text);
        wordEditText = findViewById(R.id.word_type_view);
        originalText.setMovementMethod(new ScrollingMovementMethod());
        typedTextView.setMovementMethod(new ScrollingMovementMethod());
    }



    public void textBuilder() { //init the word generator
        wordGenerator = new TextGenerator(this,GameType, difficulty);
    }

    public void getWord() { //get a random word
        wordDisplay = new SpannableString(wordGenerator.getTextInRandom());
    }

    public void setWordDisplay(){
        wordShow.setText(wordDisplay);
    }

    public void textChecker(){
        //Log.d("Kenneth",charSequence.toString());
        //wordDisplay.setSpan(new ForegroundColorSpan(Color.GREEN),0,position+1,0);
        String wordString,typedString;
        wordString = wordDisplay.toString();
        typedString = wordEditText.getText().toString();
        int wordStringLength = wordString.length();
        int typedStringLength = typedString.length();
        color = Color.BLACK;
        corrected = false;

        try{
            wordDisplay.setSpan(new ForegroundColorSpan(color),0,wordDisplay.length(),0); //reset to black color
            wordShow.setText(wordDisplay);

            if(wordStringLength >= typedStringLength){
                //Log.d("Kenneth","Hi");
                for(int i=0;i < typedStringLength; i++){
                    if(wordString.charAt(i) == typedString.charAt(i)){
                        //Log.d("Kenneth","wordString"+wordString.charAt(i));
                        //Log.d("Kenneth","typedString"+typedString.charAt(i));
                        color = Color.GREEN;
                    }
                    else{
                        color = Color.RED;
                        break;
                    }
                }
                wordDisplay.setSpan(new ForegroundColorSpan(color),0,typedStringLength,0);
                wordShow.setText(wordDisplay);
            }
            else {
                color = Color.RED;
                wordDisplay.setSpan(new ForegroundColorSpan(color),0,wordStringLength,0);
                wordShow.setText(wordDisplay);
            }


            //Log.d("Kenneth","all char are correct: "+correct);

        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
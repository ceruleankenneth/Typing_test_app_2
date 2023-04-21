package com.example.typing_test_app;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class HistoryListAdapter extends ArrayAdapter<String> {
    private int gameType;
    private final Activity context;
    private final ArrayList<String> date;
    private final ArrayList<String> accuracy;
    private final ArrayList<String> speed;
    private final ArrayList<String> difficulty;
    private final String[] difficultyString = {"easy", "normal","hard","nightmare"};

    public HistoryListAdapter(Activity context, ArrayList<String> date, ArrayList<String> accuracy,
                              ArrayList<String> speed, ArrayList<String> difficulty, int gameType){
        super(context,R.layout.history_listview,date);
        this.context = context;
        this.date = date;
        this.accuracy = accuracy;
        this.speed = speed;
        this.difficulty = difficulty;
        this.gameType = gameType;
    }

    public View getView(int position, View view, ViewGroup parent){

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.history_listview,null,true);
        TextView tv_date = rowView.findViewById(R.id.date_view);
        TextView tv_speed = rowView.findViewById(R.id.WPM_view);
        TextView tv_accuracy = rowView.findViewById(R.id.accuracy_view);
        TextView tv_difficulty = rowView.findViewById(R.id.difficulty_view);

        tv_date.setText(date.get(position));
        tv_accuracy.setText(accuracy.get(position)+" %");
        tv_speed.setText(speed.get(position)+" WPM");

        if(gameType == 0){
            tv_difficulty.setText("N/A");
        }
        if(gameType == 1){
            tv_difficulty.setText(difficultyString[Integer.parseInt(difficulty.get(position))]);
        }

        return rowView;
    }
}

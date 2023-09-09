package com.example.fragmenttest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class ChallengeActivity extends AppCompatActivity {

    SharedPreferences setPreferences, datePreferences;

    LinearLayout[] ll = new LinearLayout[9];
    TextView[] tvT = new TextView[9];
    TextView[] tvS = new TextView[9];
    TextView[] tvC = new TextView[9];
    Boolean[] chBoolSet = new Boolean[9];

    int[] ill;
    int[] itvT;
    int[] itvS;
    int[] itvC;
    String[] sTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);
        ill = new int[]{R.id.ll0, R.id.ll1, R.id.ll2, R.id.ll3, R.id.ll4, R.id.ll5
                , R.id.ll6, R.id.ll7, R.id.ll8};
        itvT = new int[]{R.id.tvT0, R.id.tvT1, R.id.tvT2, R.id.tvT3, R.id.tvT4
                , R.id.tvT5, R.id.tvT6, R.id.tvT7, R.id.tvT8};
        itvS = new int[]{R.id.tvS0, R.id.tvS1, R.id.tvS2, R.id.tvS3, R.id.tvS4
                , R.id.tvS5, R.id.tvS6, R.id.tvS7, R.id.tvS8};
        itvC = new int[]{R.id.tvC0, R.id.tvC1, R.id.tvC2, R.id.tvC3, R.id.tvC4
                , R.id.tvC5, R.id.tvC6, R.id.tvC7, R.id.tvC8};
        sTag = new String[]{"fail", "7sec", "30sec", "over10", "goodBank", "thief"
                ,"robinHood", "masterThief", "handMaster"};

        setPreferences = getSharedPreferences("challengeSet", 0);
        datePreferences = getSharedPreferences("challengeDateSet", 0);

        for(int i=0; i<9; i++){
            ll[i] = findViewById(ill[i]);
            tvT[i] = findViewById(itvT[i]);
            tvS[i] = findViewById(itvS[i]);
            tvC[i] = findViewById(itvC[i]);
            chBoolSet[i] = setPreferences.getBoolean(sTag[i], false);
        }

        for(int i =0; i<9; i++){
            if(chBoolSet[i]){
                ll[i].setBackgroundColor(getColor(R.color.black));
                tvT[i].setTextColor(getColor(R.color.white));
                tvS[i].setTextColor(getColor(R.color.user_white));
                tvS[i].setText(datePreferences.getString(sTag[i], "error"));
                tvC[i].setTextColor(getColor(R.color.user_white));
            }
        }
    }
}
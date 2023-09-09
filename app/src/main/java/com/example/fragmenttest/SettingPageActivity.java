package com.example.fragmenttest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcel;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class SettingPageActivity extends AppCompatActivity {
    SharedPreferences preferences;
    TextView tv1, tv2, tv3, tv4, touchN, touchM;
    TextView[] ViewSet;
    String[] sSet = new String[]{"tv1", "tv2", "tv3", "tv4"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_page);

        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);
        tv4 = findViewById(R.id.tv4);
        touchN = findViewById(R.id.touchN);
        touchM = findViewById(R.id.touchM);
        ViewSet = new TextView[]{tv1, tv2, tv3, tv4};

        preferences = getSharedPreferences("gameSet",0);
        SharedPreferences.Editor editor = preferences.edit();
        updateSetting();
        for(int i =0; i<4; i++){
            String s = sSet[i];
            ViewSet[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editor.putBoolean(s, !preferences.getBoolean(s, false));
                    editor.apply();
                    updateSetting();
                }
            });
        }
        touchN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putBoolean("isAbnormal", !preferences.getBoolean("isAbnormal", false));
                editor.apply();
                updateSetting();
            }
        });
        touchM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putBoolean("isAbnormal", !preferences.getBoolean("isAbnormal", false));
                editor.apply();
                updateSetting();
            }
        });

    }
    public void updateSetting(){
        ViewSet = new TextView[]{tv1, tv2, tv3, tv4};
        if(preferences.getBoolean("isAbnormal", false)){
            touchN.setBackgroundColor(getColor(R.color.black));
            //touchN.setTextColor(getColor(R.color.white));
            touchM.setBackgroundColor(getColor(R.color.white));
            touchM.setTextColor(getColor(R.color.black));
        }else{
            touchM.setBackgroundColor(getColor(R.color.black));
            //touchM.setTextColor(getColor(R.color.white));
            touchN.setBackgroundColor(getColor(R.color.white));
            touchN.setTextColor(getColor(R.color.black));
        }
        for (int i = 0; i<4; i++){
            if(preferences.getBoolean(sSet[i], false)){//공개되지 않음 => true
                ViewSet[i].setBackgroundColor(getColor(R.color.black));
                //ViewSet[i].setTextColor(getColor(R.color.white));
            }else{
                ViewSet[i].setBackgroundColor(getColor(R.color.white));
                ViewSet[i].setTextColor(getColor(R.color.black));
            }
        }
    }
}
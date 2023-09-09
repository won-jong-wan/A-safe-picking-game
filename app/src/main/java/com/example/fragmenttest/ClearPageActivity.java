package com.example.fragmenttest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ClearPageActivity extends AppCompatActivity {
    SharedPreferences getPreferences, setPreferences, datePreferences;
    TextView SorF, delayTime;
    Button reGame, goToMain, goToChallenge, goToSetting;
    Date date;
    SimpleDateFormat sdf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clear_page);

        SorF = findViewById(R.id.SorF);
        delayTime = findViewById(R.id.delayTime);
        reGame = findViewById(R.id.goToGame);
        goToMain = findViewById(R.id.goToMain);
        goToChallenge = findViewById(R.id.goToCh);
        goToSetting = findViewById(R.id.goToSet);
        getPreferences = getSharedPreferences("gameSet",0);
        setPreferences = getSharedPreferences("challengeSet", 0);
        datePreferences = getSharedPreferences("challengeDateSet", 0);


        SharedPreferences.Editor editor = setPreferences.edit();
        SharedPreferences.Editor dateEditor = datePreferences.edit();
        date = new Date(System.currentTimeMillis());
        sdf =new SimpleDateFormat("yyyy-MM-dd");
        String getTime = sdf.format(date);

        final GameDatabase db = Room.databaseBuilder(this,GameDatabase.class, "game-db")
                .allowMainThreadQueries()
                .build();

        boolean isSensitive;
        int fail, delaySec, numbers;
        int[] randomIntSet;

        fail = getIntent().getIntExtra("fail",0);
        delaySec = getIntent().getIntExtra("delaySec", 0);
        numbers = getIntent().getIntExtra("numberOfReleased", 0);
        randomIntSet = getIntent().getIntArrayExtra("randomIntSet");
        isSensitive = getIntent().getBooleanExtra("isSensitive", false);

        db.gameDao().insert(new Game(fail,delaySec, numbers, randomIntSet[0], randomIntSet[1], randomIntSet[2], randomIntSet[3]));

        delayTime.setText("소요시간: "+delaySec/60+":"+delaySec%60);
        TextView tvt = (TextView)findViewById(R.id.test);
        String s0 ="";

        //sharePre로 조건문 돌려서 도전과제 갱신
        if(db.gameDao().getMinDelay() <=7){
            if(!setPreferences.getBoolean("7sec", false)){
                s0 = s0+"'7초의 미학' 완료\n";
            }
            editor.putBoolean("7sec", true);
            dateEditor.putString("7sec", getTime);
        }
        if(db.gameDao().getMaxDelay() >=30){
            if(!setPreferences.getBoolean("30sec", false)){
                s0 = s0+"'금고는 움직이지 않는다' 완료\n";
            }
            editor.putBoolean("30sec", true);
            dateEditor.putString("30sec", getTime);
        }
        if(db.gameDao().getFailCount() >=20){
            if(!setPreferences.getBoolean("fail", false)){
                s0 = s0+"'실패는 성공의 어머니' 완료\n";
            }
            editor.putBoolean("fail", true);
            dateEditor.putString("fail", getTime);
        }
        if(db.gameDao().getCount() >= 10){
            if(!setPreferences.getBoolean("over10", false)){
                s0 = s0+"'꾸준함이란 미덕' 완료\n";
            }
            editor.putBoolean("over10", true);
            dateEditor.putString("over10", getTime);
        }
        String[] s = new String[]{"tv1", "tv2", "tv3", "tv4"};
        int n =0;
        for(int i=0; i<4; i++){
            if(getPreferences.getBoolean(s[i], false)){
                n++;
            }
        }
        if(n ==0){
            if(!setPreferences.getBoolean("thief", false)){
                s0 = s0+"'얍삽한 도둑' 완료\n";
            }
            editor.putBoolean("thief", true);
            dateEditor.putString("thief", getTime);
        }else if(n == 2){
            if(!setPreferences.getBoolean("robinHood", false)){
                s0 = s0+"'외눈의 의적' 완료\n";
            }
            editor.putBoolean("robinHood", true);
            dateEditor.putString("robinHood", getTime);
        }else if(n == 4){
            if(!setPreferences.getBoolean("masterThief", false)){
                s0 = s0+"'심안의 괴도' 완료\n";
            }
            editor.putBoolean("masterThief", true);
            dateEditor.putString("masterThief", getTime);
            if(isSensitive){
                if(!setPreferences.getBoolean("handMaster", false)){
                    s0 = s0+"'손으로 보다' 완료\n";
                }
                editor.putBoolean("handMaster", true);
                dateEditor.putString("handMaster", getTime);
            }
        }
        if(isSensitive){
            if(!setPreferences.getBoolean("goodBank", false)){
                s0 = s0+"'최신식 금고' 완료\n";
            }
            editor.putBoolean("goodBank", true);
            dateEditor.putString("goodBank", getTime);
        }
        editor.apply();
        dateEditor.apply();
        tvt.setText(s0);

        //버튼 설정
        reGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), GamePageActivity.class);
                Bundle b = ActivityOptions.makeSceneTransitionAnimation((Activity) v.getContext()).toBundle();
                startActivity(intent, b);
            }
        });
        goToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), StartPageActivity.class);
                Bundle b = ActivityOptions.makeSceneTransitionAnimation((Activity) v.getContext()).toBundle();
                startActivity(intent, b);
            }
        });
        goToChallenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ChallengeActivity.class);
                Bundle b = ActivityOptions.makeSceneTransitionAnimation((Activity) v.getContext()).toBundle();
                startActivity(intent, b);
            }
        });
        goToSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SettingPageActivity.class);
                Bundle b = ActivityOptions.makeSceneTransitionAnimation((Activity) v.getContext()).toBundle();
                startActivity(intent, b);
            }
        });
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
}
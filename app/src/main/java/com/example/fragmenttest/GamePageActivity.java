package com.example.fragmenttest;

import static java.lang.Math.abs;
import static java.lang.Math.log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Random;

public class GamePageActivity extends AppCompatActivity {

    TextView tv11, tv12, tv13, tv14
    ,tv21, tv22, tv23, tv24;
    TextView[] tvSet1,tvSet2;
    ImageView arrow;
    Button testBtn;
    Chronometer chronometer;
    View touchV;
    PointF center;
    Random random = new Random();
    int fail =0, delaySec = 0, numberOfReleased =0;
    int[] randomIntSet = new int[4];
    double prevDegree, RAngle, setAngle, isTurnRightStock = 0;
    boolean[] isCleared = new boolean[]{false, false, false, false}, isPublic = new boolean[]{true, true, true, true};
    boolean isTurnRight, isChronometerStart =false, isGo = false;
    HashMap<Integer, Double> dialMap = new HashMap<Integer, Double>(){{
        put(5, 337.5);
        put(10, 0.0);
        put(15, 22.5);
        put(20, 45.0);
        put(25, 67.5);
        put(30, 90.0);
        put(35, 112.5);
        put(40, 135.0);
        put(45, 157.5);
        put(50, 180.0);
        put(55, 202.5);
        put(60, 225.0);
        put(65, 247.5);
        put(70, 270.0);
        put(75, 292.5);
        put(80, 315.0);
        put(85, 337.5);
    }};

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_page);

        center = new PointF(0, 0);
        arrow = findViewById(R.id.arrow);
        touchV = findViewById(R.id.touchV);
        tv11 = findViewById(R.id.tv11);
        tv12 =findViewById(R.id.tv12);
        tv13 = findViewById(R.id.tv13);
        tv14 = findViewById(R.id.tv14);
        tv21 = findViewById(R.id.tv21);
        tv22 =findViewById(R.id.tv22);
        tv23 = findViewById(R.id.tv23);
        tv24 = findViewById(R.id.tv24);
        chronometer = findViewById(R.id.chronometer);
        preferences = getSharedPreferences("gameSet",0);
        tvSet1 = new TextView[]{tv11, tv12, tv13, tv14};
        tvSet2 = new TextView[]{tv21, tv22, tv23, tv24};

        Log.d("isAbnormal", "onCreate: "+preferences.getBoolean("isAbnormal", false));

        MakeRandom();

        //숫자 공개 조건 설정
        String[] s0 = new String[]{"tv1","tv2","tv3","tv4"};
        for(int i =0; i<4; i++){
            isPublic[i] = !preferences.getBoolean(s0[i],false);
            if(preferences.getBoolean(s0[i],false)){
                numberOfReleased++;
            }
        }

        setTVTextColor();

        touchV.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                center.x = touchV.getX() + ((float) touchV.getWidth() / 2);
                center.y = touchV.getY() + ((float) touchV.getHeight() / 2);
                Log.d("center set", "onGlobalLayout: "+center.x+" "+center.y);
                arrow.setX(center.x - ((float)arrow.getWidth() / 2));
                arrow.setY(center.y - ((float)arrow.getHeight() / 2));
                Log.d("center get", "onGlobalLayout: "+arrow.getX()+" "+arrow.getY());
                touchV.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        chronometer.setFormat("%s");
        prevDegree = 0.0;
        touchV.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                double dx = event.getX() - center.x;
                double dy = event.getY() - center.y;

                double degree = Math.toDegrees(Math.atan2(dy, dx));
                setAngle = arrow.getRotation() + (degree - prevDegree);
                while(setAngle<0){
                    setAngle = 360+setAngle;
                }
                while(setAngle>360){
                    setAngle = setAngle-360;
                }
                RAngle =setAngle;//각 정규화
                isTurnRight = isTurnRight();

                //클리어 조건 구현부
                boolean cc = ClearCondition();


                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    prevDegree = degree;
                } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    arrow.setRotation((float) (arrow.getRotation() + (degree - prevDegree)));
                    prevDegree = degree;
                }
                if(!isChronometerStart){
                    chronometer.setBase(SystemClock.elapsedRealtime());
                    chronometer.start();
                    isChronometerStart =true;
                }
                if(cc&&!isGo){
                    Intent intent = new Intent(v.getContext(), ClearPageActivity.class);
                    Bundle b = ActivityOptions.makeSceneTransitionAnimation((Activity) v.getContext()).toBundle();
                    delaySec = (int)(SystemClock.elapsedRealtime() - chronometer.getBase())/1000;
                    Log.d("chrononeter", "onTouch: "+(SystemClock.elapsedRealtime() - chronometer.getBase()));
                    intent.putExtra("fail", fail);
                    intent.putExtra("delaySec", delaySec);
                    intent.putExtra("numberOfReleased", numberOfReleased);
                    intent.putExtra("randomIntSet", randomIntSet);
                    intent.putExtra("isSensitive", preferences.getBoolean("isAbnormal", false));
                    startActivity(intent, b);
                    isGo =true;
                }
                return true;
            }
        });

    }
    public boolean ClearCondition(){
        if(!isCleared[0]){
            //1단계
            isCleared[0] = catchAngle(randomIntSet[0], false);
        }
        else if(!isCleared[1]){
            //2단계
            if(catchAngle(turnInt(randomIntSet[0], false), false)){
                resetCC();
            }
            isCleared[1] = catchAngle(randomIntSet[1], true);
        }
        else if(!isCleared[2]){
            //3단계
            if(catchAngle(turnInt(randomIntSet[1], true), true)){
                resetCC();
            }
            isCleared[2] = catchAngle(randomIntSet[2], false);
        }
        else if(!isCleared[3]){
            //4단계
            if(catchAngle(turnInt(randomIntSet[2], false), false)){
                resetCC();
            }
            isCleared[3] = catchAngle(randomIntSet[3], true);
        }else{
            //clear
            chronometer.stop();
            //Toast.makeText(getApplicationContext(), "Clear!", Toast.LENGTH_SHORT).show();
            return true;
        }
        setTVColor();
        return false;
    }
    public void MakeRandom(){
        randomIntSet[0] = random.nextInt(8)+1;
        randomIntSet[1] = random.nextInt(6)+1+randomIntSet[0]+1;
        if(randomIntSet[1]>8){
            randomIntSet[1] -=8;
        }
        randomIntSet[2] = random.nextInt(6)+1+randomIntSet[1]+1;
        if(randomIntSet[2]>8){
            randomIntSet[2] -=8;
        }
        randomIntSet[3] = random.nextInt(6)+1+randomIntSet[2]+1;
        if(randomIntSet[3]>8){
            randomIntSet[3] -=8;
        }
        for(int i=0; i<4; i++){
            randomIntSet[i] *=10;
        }
        //Toast.makeText(getApplicationContext(), randomIntSet[0]+"", Toast.LENGTH_SHORT).show();
        tv21.setText(randomIntSet[0]+"");
        tv22.setText(randomIntSet[1]+"");
        tv23.setText(randomIntSet[2]+"");
        tv24.setText(randomIntSet[3]+"");

    }
    public int turnInt(int ref, boolean isTurnRight){
        int result;
        int var;
        if(preferences.getBoolean("isAbnormal", false)){
            var = 5;
        }else{
            var = 10;
        }
        if(isTurnRight){
            result = ref+var;
        }else{
            result = ref-var;
        }
        if(result>=90){
            result = result-90;
        }
        if(result<=0){
            result = result+80;
        }
        return result;
    }
    public boolean isTurnRight(){
        if(RAngle >= isTurnRightStock){
            isTurnRightStock = RAngle;
            return true;
        }else{
            isTurnRightStock = RAngle;
            return false;
        }
    }
    public void resetCC(){
        fail++;
        for(int i=0; i<4; i++){
            isCleared[i] = false;
        }
    }
    public void setTVTextColor(){
        for(int i=0; i<4; i++){
            if(isPublic[i]){
                tvSet2[i].setTextColor(getColor(R.color.black));
            }else{
                tvSet2[i].setTextColor(getColor(R.color.white));
            }
        }
    }
    public void setTVColor(){
        for(int i=0; i<4; i++){
            if(isCleared[i]){
                tvSet1[i].setBackgroundColor(getColor(R.color.black));
                tvSet2[i].setBackgroundColor(getColor(R.color.black));
            }else{
                tvSet1[i].setBackgroundColor(getColor(R.color.white));
                tvSet2[i].setBackgroundColor(getColor(R.color.white));
            }
        }
    }
    public boolean catchAngle(int target, boolean isShouldGoRight){
        double targetA = dialMap.get(target);
        Log.d("catchAngle", "targetA:"+targetA+" RAngle:"+RAngle);
        if(isTurnRight == isShouldGoRight&&(targetA+4 >=RAngle&&targetA-4 <= RAngle)){
            return true;
        }else{
            return false;
        }
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
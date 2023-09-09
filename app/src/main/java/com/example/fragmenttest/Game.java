package com.example.fragmenttest;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Arrays;

@Entity
public class Game {
    @PrimaryKey(autoGenerate = true)
    private int id;

    //@ColumnInfo(name = "fail")
    private int fail;

    //@ColumnInfo(name = "delaySec")
    private int delaySec;

    //@ColumnInfo(name = "NofR")
    private int numberOfReleased;

    //@ColumnInfo(name = "num1")
    private int number1;

    //@ColumnInfo(name = "num2")
    private int number2;

    //@ColumnInfo(name = "num3")
    private int number3;

    //@ColumnInfo(name = "num4")
    private int number4;

    public Game(int fail, int delaySec, int numberOfReleased, int number1, int number2, int number3, int number4) {
        this.fail = fail;
        this.delaySec = delaySec;
        this.numberOfReleased = numberOfReleased;
        this.number1 = number1;
        this.number2 = number2;
        this.number3 = number3;
        this.number4 = number4;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", fail=" + fail +
                ", delaySec=" + delaySec +
                ", numberOfReleased=" + numberOfReleased +
                ", number1=" + number1 +
                ", number2=" + number2 +
                ", number3=" + number3 +
                ", number4=" + number4 +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFail() {
        return fail;
    }

    public void setFail(int fail) {
        this.fail = fail;
    }

    public int getDelaySec() {
        return delaySec;
    }

    public void setDelaySec(int delaySec) {
        this.delaySec = delaySec;
    }

    public int getNumberOfReleased() {
        return numberOfReleased;
    }

    public void setNumberOfReleased(int numberOfReleased) {
        this.numberOfReleased = numberOfReleased;
    }

    public int getNumber1() {
        return number1;
    }

    public void setNumber1(int number1) {
        this.number1 = number1;
    }

    public int getNumber2() {
        return number2;
    }

    public void setNumber2(int number2) {
        this.number2 = number2;
    }

    public int getNumber3() {
        return number3;
    }

    public void setNumber3(int number3) {
        this.number3 = number3;
    }

    public int getNumber4() {
        return number4;
    }

    public void setNumber4(int number4) {
        this.number4 = number4;
    }
}

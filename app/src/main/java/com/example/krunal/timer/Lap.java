package com.example.krunal.timer;

public class Lap{
    private String NumberOfLap;
    private String Time;
    public Lap (String numberOfLap, String time){
        this.NumberOfLap = numberOfLap;
        this.Time = time;
    }

    public String getNumberOfLap() {
        return NumberOfLap;
    }

    public String getLapTime() {
        return Time;
    }
}

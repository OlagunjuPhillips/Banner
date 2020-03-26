package com.company;

public class MyTime {
    private int hour;
    public MyTime(int  hour){
        this.hour = hour;
    }
    public int  getHour(){
        return hour;
    }

    public String toString(){
        return hour+" 00";
    }
}

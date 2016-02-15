package com.example.stevenzheng.cse110tasklist;

/**
 * Created by John on 2/13/2016.
 */
public class Task {
    public String name;
    public String desc;
    public int difc;
    public boolean rep;
    public int day;
    public int month;
    public int year;

    public Task () {}
    public Task (String name, String desc, int difc, boolean rep, int day, int month, int year) {
        this.name = name;
        this.desc = desc;
        this.difc = difc;
        this.rep = rep;
        this.day = day;
        this.month = month;
        this.year = year;
    }

    @Override
    public String toString() {
        return name;
    }
}

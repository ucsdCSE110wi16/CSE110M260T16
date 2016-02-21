package com.example.stevenzheng.cse110tasklist;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

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
    public String personAssigned;
    public String group;

    public Task () {}
    public Task (String name, String desc, int difc, boolean rep, int day, int month, int year, String personAssigned, String group) {
        this.name = name;
        this.desc = desc;
        this.difc = difc;
        this.rep = rep;
        this.day = day;
        this.month = month;
        this.year = year;
        this.personAssigned = personAssigned;
        this.group = group;
    }

    // John, I added a function to convert this task object into a parse object so it can be saved - Steven
    public ParseObject getParseTask() {

        ParseObject parseTask =  new ParseObject("Task");
        parseTask.put("name", name);
        parseTask.put("desc", desc);
        parseTask.put("difc", difc);
        parseTask.put("rep", rep);
        parseTask.put("day", day);
        parseTask.put("month", month);
        parseTask.put("year", year);
        parseTask.put("personAssigned", personAssigned);
        parseTask.put("group", group);

        return parseTask;
    }

    @Override
    public String toString() {
        return name;
    }
}

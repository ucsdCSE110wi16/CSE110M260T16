package com.example.stevenzheng.cse110tasklist;

/**
 * Created by stevenzheng on 2/1/16.
 */
public class Group {

    private String name;
    private String password;

    public Group(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}

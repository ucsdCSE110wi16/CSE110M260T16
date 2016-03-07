package com.example.stevenzheng.cse110tasklist;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
//import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import com.parse.ParseUser;

import java.util.ArrayList;

/**
 * Created by User on 3/6/2016.
 */
public class Log extends Activity {

    static ArrayList<ParseObject> log = new ArrayList<>();
    static ListView logView;
    static ArrayList<String> logNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log);
        refreshList();
    }


    public void refreshList() {
        // reset list
        log = new ArrayList<>();

        logView = (ListView) findViewById(R.id.taskList);
        logNames = new ArrayList<String>();
        ParseQuery query = new ParseQuery("Log");
        query.whereEqualTo("group", MainMenu.groupName);

        try {
            List<ParseObject> parseLog = query.find();
            for (int i = 0; i < parseLog.size(); i++) {
                ParseObject tempTest = parseLog.get(i);
                String tempText = tempTest.getString("text");
                logNames.add(tempText);


            }

        }
        catch (ParseException e) {
            e.printStackTrace();
        }


        ArrayAdapter<String> adapterClear = new ArrayAdapter<String>(Log.this,
                android.R.layout.simple_list_item_1, android.R.id.text1, logNames);
        logView.setAdapter(adapterClear);

    }

}
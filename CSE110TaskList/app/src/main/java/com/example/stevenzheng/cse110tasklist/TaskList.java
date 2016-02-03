package com.example.stevenzheng.cse110tasklist;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by User on 2/1/2016.
 */
public class TaskList extends Activity {

    ListView lv;
    ArrayList<String> list=  new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView) findViewById(R.id.taskList);

        list.add("foo");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        lv.setAdapter(adapter);

    }

    public void buttonOnClick(View v)
    {

        TextView edit = (TextView) findViewById(R.id.newTask);

        String temp = edit.getText().toString();
        list.add(temp);
        lv.invalidateViews();
        edit.setText("");

    }

}

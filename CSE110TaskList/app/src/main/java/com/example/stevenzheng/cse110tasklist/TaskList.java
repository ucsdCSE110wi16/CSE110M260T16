package com.example.stevenzheng.cse110tasklist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by User on 2/1/2016.
 */
public class TaskList extends Activity {

    static ListView lv;
    static ArrayList<String> list=  new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_list);

        TextView name = (TextView)findViewById(R.id.groupName);
        name.setText(MainMenu.groupName);

        lv = (ListView) findViewById(R.id.taskList);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
        lv.setAdapter(adapter);


    }

    public static void addToList( String task)
    {
        list.add(task);
        lv.invalidateViews();



    }

    public void onMembersClick(View v) {
        Intent i = new Intent(this, Members.class);
        startActivity(i);
    }
    public void buttonOnClick(View v)
    {

        Intent i = new Intent(TaskList.this, TaskCreator.class);
        startActivity(i);
   /* TextView edit = (TextView) findViewById(R.id.newTask);

String temp = edit.getText().toString();
    System.out.println("nik you fucker");
    list.add(temp);
    System.out.println("nik has a tiny dick");
    lv.invalidateViews();
    System.out.println("nik doesn't know how to spell his name");
    edit.setText("");
    */

    }


}

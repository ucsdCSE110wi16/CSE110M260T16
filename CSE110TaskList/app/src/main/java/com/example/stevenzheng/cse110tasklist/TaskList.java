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

    static ArrayList<Task> list=  new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_list);

        TextView name = (TextView)findViewById(R.id.groupName);
        name.setText(MainMenu.groupName);

        lv = (ListView) findViewById(R.id.taskList);

        ArrayAdapter<Task> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,list);
        lv.setAdapter(adapter);

        /*lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition     = position;

                // ListView Clicked item value
                String  itemValue    = (String) lv.getItemAtPosition(position);

                // Show Alert
                Toast.makeText(getApplicationContext(),
                        "Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_LONG)
                        .show();

            }

        });
        */

    }

    public static void addToList(Task task)
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

package com.example.stevenzheng.cse110tasklist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by User on 2/1/2016.
 */
public class TaskList extends Activity {

    static ListView lv;

    static ArrayList<Task> list=  new ArrayList<>();

    boolean delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_list);

        TextView name = (TextView) findViewById(R.id.groupName);
        name.setText(MainMenu.groupName);

        lv = (ListView) findViewById(R.id.taskList);

        final ArrayAdapter<Task> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position,
                                    long id) {
                if (delete) {
                    list.remove(position);
                    adapter.notifyDataSetChanged();
                    delete = false;
                } else {
                    Intent i = new Intent(TaskList.this, TaskView.class);
                    i.putExtra("position", position);
                    startActivity(i);
                }
            }
        });


    }

    public static Task getTask(int position) {
        return list.get(position);
    }

    public static void editList(int position, Task task) {
        list.set(position, task);
        lv.invalidateViews();
    }

    public static void addToList(Task task)
    {
        list.add(task);
        lv.invalidateViews();
    }

    public void onDeleteClick(View v) {
        delete = true;
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

package com.example.stevenzheng.cse110tasklist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 2/1/2016.
 */
public class TaskList extends Activity {

    static ListView lv;

    static ArrayList<Task> list=  new ArrayList<>();
    ArrayList<String> taskNames = new ArrayList<>(0);

    boolean delete;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_list);

        TextView name = (TextView) findViewById(R.id.groupName);
        name.setText(MainMenu.groupName);

        // reset list
        list = new ArrayList<>();
        
        lv = (ListView) findViewById(R.id.taskList);
        /*
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
        });*/

        // When created, queries for tasks that belong to this group, displays all tasks found
        // in the list
        Log.d("testing", "testing");
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Task");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException e) {
                String groupName = MainMenu.groupName;

                if (e == null) {
                    int numOfTasks = 0;
                    for (int i = 0; i < objects.size(); i++) {
                        ParseObject currentTask = objects.get(i);
                        //Log.d("name", currentTask.getString("personAssigned"));
                        //Log.d("person name", name);
                        if (currentTask.getString("group").equals(groupName)) {
                            Log.d("result", "equal");
                            taskNames.add(numOfTasks, currentTask.getString("name"));

                            String taskName = currentTask.getString("name");
                            String taskDesc = currentTask.getString("desc");
                            int taskDifc = currentTask.getInt("difc");
                            boolean taskRep = currentTask.getBoolean("rep");
                            int taskDay = currentTask.getInt("day");
                            int taskMonth = currentTask.getInt("month");
                            int taskYear = currentTask.getInt("year");
                            String taskPersonAssigned = currentTask.getString("personAssigned");
                            String taskGroup = currentTask.getString("group");
                            Task task = new Task(taskName, taskDesc, taskDifc, taskRep, taskDay, taskMonth
                                    , taskYear, taskPersonAssigned, taskGroup);
                            addToList(task);
                            numOfTasks++;


                        } else {
                            Log.d("result", "not equal");
                        }

                        for (int x = 0; x < list.size(); x ++) {
                            Task task = TaskList.getTask(x);
                            Log.d("name", task.name);
                        }
                    }

                    final ArrayAdapter<String> adapter = new ArrayAdapter<String>(TaskList.this,
                            android.R.layout.simple_list_item_1, android.R.id.text1, taskNames);
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
                                Log.d("position", Integer.toString(position));
                                Log.d("name", taskNames.get(position));
                                i.putExtra("name", taskNames.get(position));
                                i.putExtra("position", position);
                                startActivity(i);
                            }
                        }
                    });



                } else {
                    Log.d("Unsuccessful", "query");

                }
            }
        });
    }
    /*
    // Refresh the task list after someone creates task
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            Log.d("result", "Refreshing Task list");
            Intent refresh = new Intent(this, TaskList.class);
            startActivity(refresh);
            this.finish();
        }
    }*/

    public static Task getTask(int position) {
        Log.d("get task position", Integer.toString(position));
        Task task = list.get(position);
        Log.d("get task name", task.name);
        return task;
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

    public void onYourTasksClick(View v) {
        Intent i = new Intent(this, YourTaskList.class);
        startActivity(i);
    }
    public void buttonOnClick(View v)
    {
        Intent i = new Intent(TaskList.this, TaskCreator.class);
        startActivity(i);
        //startActivityForResult(i, 1);

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

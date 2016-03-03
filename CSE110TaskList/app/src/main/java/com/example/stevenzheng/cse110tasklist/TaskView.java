package com.example.stevenzheng.cse110tasklist;

/**
 * Created by John on 2/6/2016.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class TaskView extends Activity {
    Task task;
    int position;

    int difc;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_view);
        Intent i = getIntent();
        position = i.getIntExtra("position", -1);
        task = TaskList.getTask(position);
        TextView taskName = (TextView) findViewById(R.id.Name);
        taskName.setText(task.name);
        TextView taskDesc = (TextView) findViewById(R.id.Desc);
        taskDesc.setText(task.desc);
        TextView taskDifc = (TextView) findViewById(R.id.Diff);
        taskDifc.setText("Difficulty: " + task.difc);
        TextView rep = (TextView) findViewById(R.id.Rep);
        rep.setText("Repeated?: ");
        CheckBox repetitive = (CheckBox) findViewById(R.id.Repeated);
        repetitive.setChecked(task.rep);
        TextView endDate = (TextView) findViewById(R.id.Cal);
        int month = task.month + 1;
        endDate.setText("Due: " + month + "/" + task.day + "/" + task.year);

        // John, I added this so you can see who is assigned the task when you view it - Steven
        TextView assignedPerson = (TextView) findViewById(R.id.AssignedPerson);
        assignedPerson.setText("Assigned person: " + task.personAssigned);

    }


    public void editButton(View v)
    {
        Intent w = this.getIntent();
        Intent i = new Intent(TaskView.this, TaskEditor.class);
        i.putExtra("position", position);
        Log.d("thisname", w.getStringExtra("name"));
        i.putExtra("name", w.getStringExtra("name"));

        startActivityForResult(i, 1);
    }

    public void delete(View v) {
        ParseQuery query = new ParseQuery("Task");
        query.whereEqualTo("group", task.group );
        query.whereEqualTo("name", task.name );
        query.whereEqualTo("difc", task.difc );
        query.whereEqualTo("desc", task.desc );
        query.whereEqualTo("day", task.day);
        query.whereEqualTo("month", task.month);
        query.whereEqualTo("year", task.year);
        try {
            List<ParseObject> parseTask = query.find();
            //Log.d("name = ", task.name);
            //Log.d("score", "Retrieved " + parseTask.size() + " test objects");
            for (int i = 0; i < parseTask.size(); i++) {
                ParseObject tempTest = parseTask.get(i);
                tempTest.deleteInBackground();
            }
            Log.d("completed",task.name + "was completed");
            //Intent i = new Intent(TaskView.this, TaskList.class);
            //startActivity(i);
        } catch (ParseException e) {

        }

        ParseQuery<ParseObject> originalUserQuery = ParseQuery.getQuery("UserDifficulty");
        originalUserQuery.whereEqualTo("name", task.personAssigned);
        try {
            List<ParseObject> parseUserDifficulties = originalUserQuery.find();
            for (int x = 0; x < parseUserDifficulties.size(); x++) {
                ParseObject currentUserDifficulty = parseUserDifficulties.get(x);
                int currentDifficulty = currentUserDifficulty.getInt("totalDifficulty");
                Log.d("Original: ", Integer.toString(currentDifficulty));
                currentDifficulty -= task.difc;
                Log.d("New: ", Integer.toString(currentDifficulty));
                currentUserDifficulty.put("totalDifficulty", currentDifficulty);
                currentUserDifficulty.saveInBackground();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // update old group
        ParseQuery<ParseObject> oldTaskGroupQuery = ParseQuery.getQuery("TaskGroup");
        oldTaskGroupQuery.whereEqualTo("name", task.taskGroup);
        oldTaskGroupQuery.whereEqualTo("group", MainMenu.groupName);
        try {
            List<ParseObject> taskGroups = oldTaskGroupQuery.find();
            if (taskGroups.size() == 1) {
                ParseObject currentTaskGroup = taskGroups.get(0);
                int newDifficulty = currentTaskGroup.getInt("totalDifficulty");
                newDifficulty -= task.difc;
                if (newDifficulty == 0) {
                    currentTaskGroup.deleteInBackground();
                } else {
                    currentTaskGroup.put("totalDifficulty", newDifficulty);
                    currentTaskGroup.saveInBackground();
                }
            }

        } catch (ParseException e) {

        }


        Intent intent = new Intent(this, TaskList.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        /*
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> testList, ParseException e) {
                if (e == null) {


                    Log.d("name = ", task.name);
                    Log.d("score", "Retrieved " + testList.size() + " test objects");
                    for (int i = 0; i < testList.size(); i++) {
                        ParseObject tempTest = testList.get(i);
                        tempTest.deleteInBackground();
                    }
                    Log.d("delete", task.name + "was deleted");
                    //Intent i = new Intent(TaskView.this, TaskList.class);
                    //startActivity(i);
                    setResult(RESULT_OK, null);
                    finish();
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });*/
    }

    public void completed(View v)
    {
        ParseQuery query = new ParseQuery("Task");
        query.whereEqualTo("group", task.group );
        query.whereEqualTo("name", task.name );
        query.whereEqualTo("difc", task.difc );
        query.whereEqualTo("desc", task.desc );
        query.whereEqualTo("day", task.day );
        query.whereEqualTo("month", task.month );
        query.whereEqualTo("year", task.year);

        try {
            List<ParseObject> parseTask = query.find();
            //Log.d("name = ", task.name);
            //Log.d("score", "Retrieved " + parseTask.size() + " test objects");
            for (int i = 0; i < parseTask.size(); i++) {
                ParseObject tempTest = parseTask.get(i);
                tempTest.deleteInBackground();
            }
            Log.d("completed",task.name + "was completed");
            //Intent i = new Intent(TaskView.this, TaskList.class);
            //startActivity(i);
        } catch (ParseException e) {

        }

        // update old group
        ParseQuery<ParseObject> oldTaskGroupQuery = ParseQuery.getQuery("TaskGroup");
        oldTaskGroupQuery.whereEqualTo("name", task.taskGroup);
        oldTaskGroupQuery.whereEqualTo("group", MainMenu.groupName);
        try {
            List<ParseObject> taskGroups = oldTaskGroupQuery.find();
            if (taskGroups.size() == 1) {
                ParseObject currentTaskGroup = taskGroups.get(0);
                int newDifficulty = currentTaskGroup.getInt("totalDifficulty");
                newDifficulty -= task.difc;
                if (newDifficulty == 0) {
                    currentTaskGroup.deleteInBackground();
                } else {
                    currentTaskGroup.put("totalDifficulty", newDifficulty);
                    currentTaskGroup.saveInBackground();
                }
            }

        } catch (ParseException e) {

        }

        ParseQuery<ParseObject> originalUserQuery = ParseQuery.getQuery("UserDifficulty");
        originalUserQuery.whereEqualTo("name", task.personAssigned);
        try {
            List<ParseObject> parseUserDifficulties = originalUserQuery.find();
            for (int x = 0; x < parseUserDifficulties.size(); x++) {
                ParseObject currentUserDifficulty = parseUserDifficulties.get(x);
                int currentDifficulty = currentUserDifficulty.getInt("totalDifficulty");
                Log.d("Original: ", Integer.toString(currentDifficulty));
                currentDifficulty -= task.difc;
                Log.d("New: ", Integer.toString(currentDifficulty));
                currentUserDifficulty.put("totalDifficulty", currentDifficulty);
                currentUserDifficulty.saveInBackground();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(this, TaskList.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        /*
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> testList, ParseException e) {
                if (e == null) {



                    Log.d("name = ", task.name);
                    Log.d("score", "Retrieved " + testList.size() + " test objects");
                    for (int i = 0; i < testList.size(); i++) {
                        ParseObject tempTest = testList.get(i);
                        tempTest.deleteInBackground();
                    }
                    Log.d("completed",task.name + "was completed");
                    //Intent i = new Intent(TaskView.this, TaskList.class);
                    //startActivity(i);
                    setResult(RESULT_OK, null);
                    finish();
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });*/

    }


}

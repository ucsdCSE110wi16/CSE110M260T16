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

        startActivity(i);
    }

    public void delete(View v) {
        ParseQuery query = new ParseQuery("Task");
        query.whereEqualTo("group", task.group );
        query.whereEqualTo("name", task.name );
        query.whereEqualTo("difc", task.difc );
        query.whereEqualTo("desc", task.desc );
        query.whereEqualTo("day", task.day );
        query.whereEqualTo("month", task.month );
        query.whereEqualTo("year", task.year );
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> testList, ParseException e) {
                if (e == null) {

                    String assignedPerson;
                    for (int i = 0; i < testList.size(); i++) {
                        if (testList.get(i).getString("personAssigned") != "") {
                            assignedPerson = testList.get(i).getString("personAssigned");
                            difc = testList.get(i).getInt("difc");
                            // Update user's total difficulty
                            ParseQuery<ParseUser> query = ParseUser.getQuery();
                            String[] names = assignedPerson.split(" ");
                            query.whereEqualTo("firstName", names[0]);
                            query.whereEqualTo("lastName", names[1]);
                            query.findInBackground(new FindCallback<ParseUser>() {
                                public void done(List<ParseUser> objects, ParseException e) {
                                    if (e == null) {
                                        // The query was successful.
                                        for (int i = 0; i < objects.size(); i++) {

                                            ParseUser user = objects.get(i);
                                            if (user == ParseUser.getCurrentUser()) {
                                                int currentTotalDifficulty = user.getInt("totalDifficulty");
                                                int newTotalDifficulty = currentTotalDifficulty - difc;
                                                user.put("totalDifficulty", newTotalDifficulty);
                                                user.saveInBackground();
                                            }
                                        }
                                    } else {
                                        // Something went wrong.
                                    }
                                }
                            });
                        }
                    }

                    Log.d("name = ", task.name);
                    Log.d("score", "Retrieved " + testList.size() + " test objects");
                    for (int i = 0; i < testList.size(); i++) {
                        ParseObject tempTest = testList.get(i);
                        tempTest.deleteInBackground();
                    }
                    Log.d("delete", task.name + "was deleted");
                    Intent i = new Intent(TaskView.this, TaskList.class);
                    startActivity(i);
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });
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
        query.whereEqualTo("year", task.year );
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> testList, ParseException e) {
                if (e == null) {

                    String assignedPerson;
                    for (int i = 0; i < testList.size(); i++) {
                        if (testList.get(i).getString("personAssigned") != "") {
                            assignedPerson = testList.get(i).getString("personAssigned");
                            difc = testList.get(i).getInt("difc");
                            // Update user's total difficulty
                            ParseQuery<ParseUser> query = ParseUser.getQuery();
                            String[] names = assignedPerson.split(" ");
                            query.whereEqualTo("firstName", names[0]);
                            query.whereEqualTo("lastName", names[1]);
                            query.findInBackground(new FindCallback<ParseUser>() {
                                public void done(List<ParseUser> objects, ParseException e) {
                                    if (e == null) {
                                        // The query was successful.
                                        for (int i = 0; i < objects.size(); i++) {
                                            ParseUser user = objects.get(i);
                                            if (user == ParseUser.getCurrentUser()) {
                                                int currentTotalDifficulty = user.getInt("totalDifficulty");
                                                int newTotalDifficulty = currentTotalDifficulty - difc;
                                                user.put("totalDifficulty", newTotalDifficulty);
                                                user.saveInBackground();
                                            }
                                        }
                                    } else {
                                        // Something went wrong.
                                    }
                                }
                            });
                        }
                    }


                    Log.d("name = ", task.name);
                    Log.d("score", "Retrieved " + testList.size() + " test objects");
                    for (int i = 0; i < testList.size(); i++) {
                        ParseObject tempTest = testList.get(i);
                        tempTest.deleteInBackground();
                    }
                    Log.d("completed",task.name + "was completed");
                    Intent i = new Intent(TaskView.this, TaskList.class);
                    startActivity(i);
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });

    }

}

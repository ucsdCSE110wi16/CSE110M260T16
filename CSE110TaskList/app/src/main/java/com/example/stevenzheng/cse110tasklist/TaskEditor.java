package com.example.stevenzheng.cse110tasklist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class TaskEditor extends Activity {

    ListView membersList;
    String assignedPerson = "";
    TextView lastClicked;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_editor);

        Task newTask = TaskList.thisTask;

        TextView taskName = (TextView) findViewById(R.id.taskName);
        TextView taskDesc = (TextView) findViewById(R.id.taskDesc);
        TextView assigned = (TextView) findViewById(R.id.assigned);
        TextView taskDifc = (TextView) findViewById(R.id.taskDificulty);
        CheckBox repetitive = (CheckBox) findViewById(R.id.repetitive);
        DatePicker endDate = (DatePicker) findViewById(R.id.taskDate);

        taskName.setText(newTask.name);
        taskDesc.setText(newTask.desc);
        assigned.setText("Assigned to: " + newTask.personAssigned);
        assigned.setEnabled(false);
        taskDifc.setText(String.valueOf(newTask.difc));
        repetitive.setChecked(newTask.rep);
        endDate.updateDate(newTask.year, newTask.month, newTask.day);


        //Need to be able to see member that clicked on editor, will be added shortly
        membersList = (ListView) findViewById(R.id.List_assignment);
        lastClicked = (TextView) findViewById(R.id.lastClick);
        //Almost done with highlighting who clicked, fixing a few kinks.
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Group");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException e) {
                String groupName = MainMenu.groupName;
                Log.d("after", groupName);
                ParseObject group;
                if (e == null) {
                    Log.d("successful", "query");
                    for (int i = 0; i < objects.size(); i++) {
                        ParseObject currentGroup = objects.get(i);
                        Log.d("name", currentGroup.getString("name"));
                        if (currentGroup.getString("name").equals(groupName)) {
                            Log.d("result", "equal");
                            group = currentGroup;
                            ArrayList<ParseUser> members = new ArrayList<ParseUser>();
                            members = (ArrayList<ParseUser>) ((List<ParseUser>) (Object) (group.getList("members")));
                            int numMembers = members.size();
                            Log.d("numMembers", Integer.toString(numMembers));
                            String[] memberNames = new String[numMembers];
                            memberNames[0] = "Test Name";
                            for (int x = 0; x < numMembers; x++) {
                                try {
                                    ParseUser currentMember = members.get(x);
                                    String currentName = currentMember.fetchIfNeeded().getString("firstName") + " " + currentMember.fetchIfNeeded().getString("lastName");
                                    memberNames[x] = currentName;
                                } catch (com.parse.ParseException e1) {
                                    e1.printStackTrace();
                                }
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(TaskEditor.this,
                                    android.R.layout.simple_list_item_1, android.R.id.text1, memberNames);
                            membersList.setAdapter(adapter);

                            membersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                @Override
                                public void onItemClick(AdapterView<?> parent, View view,
                                                        int position, long id) {
                                    // ListView Clicked item index
                                    int itemPosition = position;

                                    // ListView Clicked item value
                                    String itemValue = (String) membersList.getItemAtPosition(position);
                                    // Show Alert
                                    Toast.makeText(getApplicationContext(),
                                            "Position :" + itemPosition + "  ListItem : " + itemValue, Toast.LENGTH_LONG)
                                            .show();
                                    assignedPerson = itemValue;
                                    lastClicked.setText(assignedPerson);
                                }
                            });

                        } else {
                            Log.d("result", "not equal");
                        }
                    }

                } else {
                    Log.d("Unsuccessful", "query");
                }
            }
        });
    }

    public void submitTask(View v)
    {
        Task newTask = TaskList.thisTask;
        Intent i = getIntent();
        final String subjName = i.getStringExtra("name");

        TextView taskName = (TextView) findViewById(R.id.taskName);
        TextView taskDesc = (TextView) findViewById(R.id.taskDesc);
        TextView taskDifc = (TextView) findViewById(R.id.taskDificulty);
        CheckBox repetitive = (CheckBox) findViewById(R.id.repetitive);
        DatePicker endDate = (DatePicker) findViewById(R.id.taskDate);

        /* MAKE A LIST OF GROUP MEMBERS HERE, pretty much identical to what I made for CreateGroup.
         * When you click on the person's name, it will assign the task to that person, overriding any previous assignment
         * Again, refer to CreateGroup
         */


        final String name = taskName.getText().toString();
        final String desc  = taskDesc.getText().toString();

        int temp;
        try {
            temp = Integer.parseInt(taskDifc.getText().toString());
        }
        catch (Exception e) {
            temp = 0;
        }
        final int difc = temp;
        final boolean rep = repetitive.isChecked();
        final int day = endDate.getDayOfMonth();
        final int month = endDate.getMonth();
        final int year =  endDate.getYear();




        ParseQuery<ParseObject> originalUserQuery = ParseQuery.getQuery("UserDifficulty");
        originalUserQuery.whereEqualTo("name", newTask.personAssigned);
        try {
            List<ParseObject> parseUserDifficulties = originalUserQuery.find();
            for (int x = 0; x < parseUserDifficulties.size(); x++) {
                ParseObject currentUserDifficulty = parseUserDifficulties.get(x);
                int currentDifficulty = currentUserDifficulty.getInt("totalDifficulty");
                Log.d("Original: ", Integer.toString(currentDifficulty));
                currentDifficulty -= newTask.difc;
                Log.d("New: ", Integer.toString(currentDifficulty));
                currentUserDifficulty.put("totalDifficulty", currentDifficulty);
                currentUserDifficulty.saveInBackground();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (assignedPerson.compareTo("") != 0) {
            ParseQuery<ParseObject> newUserQuery = ParseQuery.getQuery("UserDifficulty");
            newUserQuery.whereEqualTo("name", assignedPerson);
            try {
                List<ParseObject> parseUserDifficulties = newUserQuery.find();
                for (int x = 0; x < parseUserDifficulties.size(); x++) {
                    ParseObject newUserDifficulty = parseUserDifficulties.get(x);
                    int currentDifficulty = newUserDifficulty.getInt("totalDifficulty");
                    currentDifficulty += difc;
                    newUserDifficulty.put("totalDifficulty", currentDifficulty);
                    newUserDifficulty.saveInBackground();
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Task");
        query.whereEqualTo("group", MainMenu.groupName);
        try {
            List<ParseObject> parseTasks = query.find();
            String groupName = MainMenu.groupName;

            for (int x = 0; x < parseTasks.size(); x++) {
                ParseObject currentTask = parseTasks.get(x);
                Log.d("name", currentTask.getString("name"));
                //Log.d("name", currentTask.getString("personAssigned"));
                //Log.d("person name", name);
                if (currentTask.getString("name").equals(subjName)) {
                    Log.d("result", "equal");
                    currentTask.put("name", name);
                    currentTask.put("desc", desc);
                    currentTask.put("difc", difc);
                    currentTask.put("rep", rep);
                    currentTask.put("day", day);
                    currentTask.put("month", month);
                    currentTask.put("year", year);
                    currentTask.put("personAssigned", assignedPerson);
                    currentTask.saveInBackground();

                }
                else {
                    Log.d("result", "not equal");
                }
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }


        /*
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException e) {
                String groupName = MainMenu.groupName;

                if (e == null) {
                    int numOfTasks = 0;
                    for (int i = 0; i < objects.size(); i++) {
                        ParseObject currentTask = objects.get(i);
                        Log.d("name", currentTask.getString("name"));
                        //Log.d("name", currentTask.getString("personAssigned"));
                        //Log.d("person name", name);
                        if (currentTask.getString("name").equals(subjName)) {
                            Log.d("result", "equal");
                            currentTask.put("name", name);
                            currentTask.put("desc", desc);
                            currentTask.put("difc", difc);
                            currentTask.put("rep", rep);
                            currentTask.put("day", day);
                            currentTask.put("month", month);
                            currentTask.put("year", year);
                            currentTask.put("personAssigned", assignedPerson);
                            currentTask.saveInBackground();
                            /*
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
                                            ParseUser user;
                                            user = objects.get(i);
                                            int currentTotalDifficulty = user.getInt("totalDifficulty");
                                            int newTotalDifficulty = currentTotalDifficulty + difc;
                                            user.put("totalDifficulty", newTotalDifficulty);
                                            user.saveInBackground();
                                        }
                                    } else {
                                        // Something went wrong.
                                    }
                                }
                            });*//*
                        }
                        else {
                            Log.d("result", "not equal");
                        }
                    }

                } else {
                    Log.d("Unsuccessful", "query");

                }
            }
        });
*/

        /*
         * Right now, I've commented out the task object because I changed the Task class
         * Also we're no longer editing the list directly, so I've commented it out, though you
         * might still want to use the editList function depending on how you implement it
         *
         * What we did in create task is that we no longer added the task to the list using addToList
         * - we create the parse object from the task object (Task object -> Parse object)
         * - we save the object in parse
         * - when we go to the TaskList activity, it refreshes the list of tasks, searching Parse for all the
         *   tasks that belong to the group... every time it finds a task that belongs, it creates a Task object which is then
         *   added to the list (Parse Object -> Task Object)... I did this so your code, John, would still work, but not completely which
         *   is where you come in now
         *
         * What you should do is
         * - figure out how to retrieve the correct Parse Object using the information of this Task Object (parse query)
         * - Edit and save the actual parse object (for backend)
         * - Make sure TaskList reloads with the changed data
         * - Add a list to select the person to do the task... refer to CreateGroup
         *
         * Design change that I just want to make clear again - we're no longer creating/editing tasks directly,
         *   We are creating/editing parseobjects and saving them
         *   TaskList will reload all objects saved in parse and convert those objects into tasks to display everytime it loads
         */


        /*Task temp = new Task(name, desc, difc, rep, day, month, year, assignedPerson);

        //TaskList.editList(position, temp);*/

        //Intent i1 = new Intent(TaskEditor.this, TaskList.class);
        //startActivity(i1);
        Intent intent = new Intent(this, TaskList.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}


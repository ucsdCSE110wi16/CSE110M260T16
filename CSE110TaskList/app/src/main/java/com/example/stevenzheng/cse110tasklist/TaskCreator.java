package com.example.stevenzheng.cse110tasklist;

/**
 * Created by User on 2/6/2016.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 2/6/2016.
 */
public class TaskCreator extends Activity {

    ParseUser currentUser;
    ListView membersList;
    TextView lastClicked;
    String assignedPerson = ""; // name of person assigned to do the task, if not set it will be empty string
    int difc;

    //Working on implementing the highlight
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_creator);


        // Get ListView object from xml
        membersList = (ListView) findViewById(R.id.List_assignment);
        lastClicked = (TextView) findViewById(R.id.lastClick);


        // Get current members
        // Populates the list with all the members of the group
        // When a list item is clicked, it will update assignedPerson with the value of the list item
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Group");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException e) {
                String groupName = MainMenu.groupName;
                ParseObject group;
                if (e == null) {
                    for (int i = 0; i < objects.size(); i++) {
                        ParseObject currentGroup = objects.get(i);
                        if (currentGroup.getString("name").equals(groupName)) {
                            group = currentGroup;
                            ArrayList<ParseUser> members = new ArrayList<ParseUser>();
                            members = (ArrayList<ParseUser>) ((List<ParseUser>) (Object) (group.getList("members")));
                            int numMembers = members.size();
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

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(TaskCreator.this,
                                    android.R.layout.simple_list_item_1, android.R.id.text1, memberNames);
                            membersList.setAdapter(adapter);

                            membersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                @Override
                                public void onItemClick(AdapterView<?> parent, View view,
                                                        int position, long id) {

                                    // ListView Clicked item index
                                    int itemPosition  = position;

                                    // ListView Clicked item value
                                    String  itemValue    = (String) membersList.getItemAtPosition(position);

                                    // Show Alert
                                    Toast.makeText(getApplicationContext(),
                                            "Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_LONG)
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

        TextView taskName = (TextView) findViewById(R.id.taskName);
        TextView taskDesc = (TextView) findViewById(R.id.taskDesc);
        TextView taskDifc = (TextView) findViewById(R.id.taskDificulty);
        TextView taskGroupTextView = (TextView) findViewById(R.id.taskGroup); // new
        CheckBox repetitive = (CheckBox) findViewById(R.id.repetitive);
        DatePicker endDate = (DatePicker) findViewById(R.id.taskDate);

        String name = taskName.getText().toString();
        String desc  = taskDesc.getText().toString();

        String taskGroup = taskGroupTextView.getText().toString(); // new
        // if no taskGroup set, then give it a random group
        if (taskGroup.compareTo("") == 0) {
            double random = Math.random();
            taskGroup = Double.toString(random);
        }


        difc = 0;
        try {
            difc = Integer.parseInt(taskDifc.getText().toString());
        }
        catch(Exception e) {
            difc = 0;
        }



        ParseQuery<ParseObject> taskGroupQuery = ParseQuery.getQuery("TaskGroup");
        taskGroupQuery.whereEqualTo("name", taskGroup);
        taskGroupQuery.whereEqualTo("group", MainMenu.groupName);
        try {
            List<ParseObject> taskGroups = taskGroupQuery.find();
            if (taskGroups.size() == 0) {
                ParseObject parseTaskGroup = new ParseObject("TaskGroup");
                parseTaskGroup.put("name", taskGroup);
                parseTaskGroup.put("group", MainMenu.groupName);
                parseTaskGroup.put("totalDifficulty", difc);
                parseTaskGroup.saveInBackground();
            } else {
                ParseObject currentTaskGroup = taskGroups.get(0);
                int newDifficulty = currentTaskGroup.getInt("totalDifficulty");
                newDifficulty += difc;
                currentTaskGroup.put("totalDifficulty", newDifficulty);
                currentTaskGroup.saveInBackground();
            }

        } catch (ParseException e) {

        }

        boolean rep = repetitive.isChecked();
        int day = endDate.getDayOfMonth();
        int month = endDate.getMonth();
        int year =  endDate.getYear();
        

        Task temp = new Task(name, desc, difc, taskGroup, rep, day, month, year, assignedPerson, MainMenu.groupName);

        // Reset the list so we can repopulate it with the new task
        TaskList.list = new ArrayList<>();

        // Create and save the task in parse
        ParseObject newTask =  temp.getParseTask();
        newTask.saveInBackground();



        if (assignedPerson != "") {
            // Update user's total difficulty
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


        setResult(RESULT_OK, null);
        finish();
    }

    void diffOnClick(View v) {
        TextView view = (TextView) v;
        view.clearComposingText();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // Refresh main menu and finish
        setResult(RESULT_CANCELED, null);
        finish();
    }
}

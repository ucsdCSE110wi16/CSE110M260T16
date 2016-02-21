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
    String assignedPerson = "";

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_creator);


        // Get ListView object from xml
        membersList = (ListView) findViewById(R.id.List_assignment);

        Log.d("before", "query");
        // Get current members

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
        CheckBox repetitive = (CheckBox) findViewById(R.id.repetitive);
        DatePicker endDate = (DatePicker) findViewById(R.id.taskDate);

        String name = taskName.getText().toString();
        String desc  = taskDesc.getText().toString();
        int difc;
        try {
            difc = Integer.parseInt(taskDifc.getText().toString());
        }
        catch(Exception e) {
            difc = 0;
        }
        boolean rep = repetitive.isChecked();
        int day = endDate.getDayOfMonth();
        int month = endDate.getMonth();
        int year =  endDate.getYear();
        

        Task temp = new Task(name, desc, difc, rep, day, month, year, assignedPerson, MainMenu.groupName);

        TaskList.list = new ArrayList<>();

        ParseObject newTask =  temp.getParseTask();
        newTask.saveInBackground();


        Intent i = new Intent(TaskCreator.this, TaskList.class);
        startActivity(i);

        /*
        // Refresh task list and finish
        setResult(RESULT_OK, null);
        finish();*/




    }

    void diffOnClick(View v) {
        TextView view = (TextView) v;
        view.clearComposingText();
    }
}

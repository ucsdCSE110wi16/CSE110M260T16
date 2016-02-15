package com.example.stevenzheng.cse110tasklist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.app.Activity;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import android.content.Intent;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;
import android.util.Log;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.widget.TextView;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;
import android.util.Log;


import java.util.ArrayList;
import java.util.List;
/**
 * Created by stevenzheng on 2/14/16.
 */
public class YourTaskList extends Activity {
    static ListView lv;
    ArrayList<String> taskNames = new ArrayList<>(0); // 100 for now

    ParseUser currentUser;
    boolean delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.your_task_list);

        lv = (ListView) findViewById(R.id.List_yourTasks);

        // get current user
        currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {


            ParseQuery<ParseObject> query = ParseQuery.getQuery("Task");
            query.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> objects, ParseException e) {
                    String name = currentUser.getString("firstName") + " " + currentUser.getString("lastName");

                    ParseObject task;
                    if (e == null) {
                        int numOfTasks = 0;
                        for (int i = 0; i < objects.size(); i++) {
                            ParseObject currentTask = objects.get(i);
                            Log.d("name", currentTask.getString("personAssigned"));
                            Log.d("person name", name);
                            if (currentTask.getString("personAssigned").equals(name)) {
                                Log.d("result", "equal");
                                taskNames.add(numOfTasks, currentTask.getString("name"));
                                numOfTasks++;


                            } else {
                                Log.d("result", "not equal");
                            }
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(YourTaskList.this,
                                android.R.layout.simple_list_item_1, android.R.id.text1, taskNames);
                        lv.setAdapter(adapter);



                    } else {
                        Log.d("Unsuccessful", "query");

                    }
                }
            });


        } else {
            // no current user, go back to login/signup screen
        }


    }
}

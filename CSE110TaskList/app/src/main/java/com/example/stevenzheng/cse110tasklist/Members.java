package com.example.stevenzheng.cse110tasklist;

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
 * Created by stevenzheng on 2/13/16.
 */
public class Members extends Activity {

    ParseUser currentUser;
    ListView membersList;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.members);

        // Get ListView object from xml
        membersList = (ListView) findViewById(R.id.List_members);

        Log.d("before", "query");
        // Get current group

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

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(Members.this,
                                    android.R.layout.simple_list_item_1, android.R.id.text1, memberNames);
                            membersList.setAdapter(adapter);

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



    public void buttonOnClick(View v) {


    }
}

package com.example.stevenzheng.cse110tasklist;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import org.json.JSONArray;
import java.util.List;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

/**
 * Created by stevenzheng on 2/1/16.
 */
public class AddGroup extends Activity {

    private EditText groupCodeView;
    private EditText groupNameView;
    String groupCodeText;
    String groupNameText;
    ParseUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_group);
        currentUser = ParseUser.getCurrentUser();
    }

    public void onButtonClick(View v) {

        if( v.getId() == R.id.B_joinGroup) {
            groupCodeView = (EditText) findViewById(R.id.TextField_groupPassword);
            groupNameView = (EditText) findViewById(R.id.TextField_findtaskListName);
            groupCodeText = groupCodeView.getText().toString();
            groupNameText = groupNameView.getText().toString();

            //Create a query to get the first object that matches the ID of password
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Group");
            query.whereEqualTo("password", groupCodeText);
            query.getFirstInBackground(new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject object, ParseException e) {
                    //If the object is found, the password was correct
                    if(e == null){
                        //Test whether or not the group name is now valid
                        ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Group");
                        query2.whereEqualTo("name", groupNameText);
                        query2.getFirstInBackground(new GetCallback<ParseObject>() {
                            @Override
                            public void done(ParseObject object, ParseException e) {
                                if (e == null) {
                                    ArrayList<ParseObject> groups = new ArrayList<ParseObject>();
                                    groups = (ArrayList<ParseObject>) ((List<ParseObject>) (Object) (currentUser.getList("groupsList")));
                                    // Append the new group and update current user with their new group list
                                    object.add("members", currentUser.getString("firstName"));
                                    groups.add(object);
                                    currentUser.put("groupsList", groups);
                                    currentUser.saveInBackground();

                                    // Refresh main menu and finish
                                    setResult(RESULT_OK, null);
                                    finish();
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "Group name or Password is incorrect", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Group name or Password is incorrect", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}

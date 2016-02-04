package com.example.stevenzheng.cse110tasklist;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;

import java.lang.reflect.Array;
import java.util.ArrayList;
import org.json.JSONArray;
import java.util.List;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by stevenzheng on 2/1/16.
 */
public class CreateGroup extends Activity {

    ParseUser currentUser;
    private EditText groupCodeView;
    private EditText groupNameView;
    private EditText groupPasswordView;
    private Button joinGroup;
    private Button createGroup;
    String groupNameTxt;
    String groupPasswordTxt;
    String groupCodeTxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_group);
        currentUser = ParseUser.getCurrentUser();
    }

    public void onButtonClick(View v) {
        if (v.getId() == R.id.B_create) {

            groupNameView = (EditText) findViewById(R.id.TextField_taskListName);
            groupPasswordView = (EditText) findViewById(R.id.TextField_password);
            groupNameTxt = groupNameView.getText().toString();
            groupPasswordTxt = groupPasswordView.getText().toString();

            //Group group = new Group(groupNameTxt, groupPasswordTxt);
            // Create the new group
            ParseObject newGroup =  new ParseObject("Group");
            ArrayList<ParseUser> members = new ArrayList<ParseUser>();
            members.add(currentUser);
            newGroup.put("name", groupNameTxt);
            newGroup.put("password", groupPasswordTxt);
            newGroup.put("members", members);
            newGroup.saveInBackground();

            // Get user's current groups
            ArrayList<ParseObject> groups = new ArrayList<ParseObject>();
            groups = (ArrayList<ParseObject>)((List<ParseObject>) (Object) (currentUser.getList("groupsList")));

            // Append the new group and update current user with their new group list
            groups.add(newGroup);
            currentUser.put("groupsList", groups);
            currentUser.saveInBackground();
            setResult(RESULT_OK, null);
            finish();
        }
    }
}

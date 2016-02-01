package com.example.stevenzheng.cse110tasklist;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Arrays;

import com.parse.ParseObject;
import com.parse.ParseUser;
import android.widget.Toast;

/**
 * Created by stevenzheng on 2/1/16.
 */
public class CreateTaskList extends Activity {

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
        setContentView(R.layout.create_task_list);
        currentUser = ParseUser.getCurrentUser();
    }

    public void onButtonClick(View v) {
        if (v.getId() == R.id.B_create) {

            groupNameView = (EditText) findViewById(R.id.TextField_taskListName);
            groupCodeView = (EditText) findViewById(R.id.TextField_password);
            groupNameTxt = groupNameView.getText().toString();
            groupPasswordTxt = groupPasswordView.getText().toString();

            // Group newGroup = new Group(groupNameTxt, groupPasswordTxt);  FIX THIS!!!!
            //currentUser.put("groups", "testing");


            //finishActivity();  figure this out
            //finish();
        }
    }
}

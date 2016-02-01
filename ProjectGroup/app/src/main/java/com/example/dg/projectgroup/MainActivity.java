package com.example.dg.projectgroup;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {

    //For my Edit text fields to acquire the input
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
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize Parse
        // https://parse.com/docs/android/guide#local-datastore
        Parse.enableLocalDatastore(this);
        Parse.initialize(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        groupPasswordView=(EditText) findViewById(R.id.groupPassword);
        groupNameView=(EditText) findViewById(R.id.groupName);
        groupCodeView=(EditText) findViewById(R.id.groupCode);
        joinGroup=(Button) findViewById(R.id.button);
        createGroup=(Button) findViewById(R.id.button2);

        joinGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                groupNameTxt = groupNameView.getText().toString();
                groupCodeTxt = groupCodeView.getText().toString();

                ParseUser.logInInBackground(groupNameTxt, groupPasswordTxt);
            }
        });

        createGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                groupNameTxt = groupNameView.getText().toString();
                groupPasswordTxt = groupPasswordView.getText().toString();

                    ParseUser user = new ParseUser();
                    user.setUsername(groupNameTxt);
                    user.setPassword(groupPasswordTxt);
                    user.signUpInBackground();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

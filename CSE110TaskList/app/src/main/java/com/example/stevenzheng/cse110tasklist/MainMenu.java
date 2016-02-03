package com.example.stevenzheng.cse110tasklist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.parse.ParseException;
import com.parse.ParseUser;
import android.content.Intent;
import android.widget.TextView;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;


/**
 * Created by stevenzheng on 2/1/16.
 */
public class MainMenu extends Activity {

    ParseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            String name = currentUser.getString("firstName");
            TextView textField = (TextView)findViewById(R.id.Text_name);
            textField.setText(name);


        } else {
            // no current user, go back to login/signup screen
        }
    }


    public void onButtonClick(View v) {
        if (v.getId() == R.id.B_addNewList) {
            Intent i = new Intent(this, AddGroup.class);
            startActivity(i);
        } else if (v.getId() == R.id.B_createNewList) {
            Intent i = new Intent(this, CreateGroup.class);
            startActivity(i);
        }

    }

}

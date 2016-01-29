package com.example.stevenzheng.cse110tasklist;

import android.app.Activity;

/**
 * Created by akumar on 1/29/16.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class About extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);

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

    public void onButtonClick(View v) {
//        if (v.getId() == R.id.B_done) {
//            EditText textField = (EditText)findViewById(R.id.TextField_firstName);
//            firstName = textField.getText().toString();
//
//            textField = (EditText)findViewById(R.id.TextField_lastName);
//            lastName = textField.getText().toString();
//
//            textField = (EditText)findViewById(R.id.TextField_email);
//            email = textField.getText().toString();
//            username = email;
//
//            textField = (EditText)findViewById(R.id.TextField_password);
//            password = textField.getText().toString();
//
//
//        }

    }
}
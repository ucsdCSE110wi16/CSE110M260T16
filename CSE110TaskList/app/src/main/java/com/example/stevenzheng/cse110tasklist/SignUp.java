package com.example.stevenzheng.cse110tasklist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

/**
 * Created by stevenzheng on 1/23/16.
 */
public class SignUp extends Activity {

    ParseUser newUser;
    String username;
    String password;
    String email;
    String firstName;
    String lastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        newUser = new ParseUser();

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
        if (v.getId() == R.id.B_done) {
            EditText textField = (EditText)findViewById(R.id.TextField_firstName);
            firstName = textField.getText().toString();

            textField = (EditText)findViewById(R.id.TextField_lastName);
            lastName = textField.getText().toString();

            textField = (EditText)findViewById(R.id.TextField_email);
            email = textField.getText().toString();
            username = email;

            textField = (EditText)findViewById(R.id.TextField_password);
            password = textField.getText().toString();


            newUser.setUsername(username);
            newUser.setPassword(password);
            newUser.setEmail(email);
            newUser.put("firstName", firstName);
            newUser.put("lastName", lastName);

            newUser.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {

                    // Sign up successful
                    if (e == null) {


                    // Error
                    } else {

                    }
                }
            });
        }

    }
}
package com.example.stevenzheng.cse110tasklist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.Parse;
import com.parse.SignUpCallback;
import android.widget.Toast;
import android.util.Log;

import java.util.ArrayList;

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

    Intent i;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        newUser = new ParseUser();
        i = new Intent(this, MainMenu.class);

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

        // Done filling out sign up info
        if (v.getId() == R.id.B_done) {

            // Get sign up info from user input
            EditText textField = (EditText)findViewById(R.id.TextField_firstName);
            firstName = textField.getText().toString();

            textField = (EditText)findViewById(R.id.TextField_lastName);
            lastName = textField.getText().toString();

            textField = (EditText)findViewById(R.id.TextField_email);
            email = textField.getText().toString();
            username = email;

            textField = (EditText)findViewById(R.id.TextField_password);
            password = textField.getText().toString();

            if (isBadEmail()) {
                Toast.makeText(getApplicationContext(), "Invalid Email",Toast.LENGTH_SHORT).show();
                return;
            }

            if (isBadPassword()) {
                Toast.makeText(getApplicationContext(), "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                return;
            }


            // Set basic info for new user
            newUser.setUsername(username);
            newUser.setPassword(password);
            newUser.setEmail(email);
            newUser.put("firstName", firstName);
            newUser.put("lastName", lastName);

            // New user has empty array of groups
            ArrayList<ParseObject> groups = new ArrayList<ParseObject>();
            newUser.put("groupsList", groups);

            // Make sure current user is null before trying to sign up
            ParseUser currentUser = ParseUser.getCurrentUser();
            if (currentUser != null) {
                currentUser.logOut();
            }

            // Sign user up
            newUser.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {

                    // Sign up successful
                    if (e == null) {
                        Toast.makeText(getApplicationContext(), "Signup complete!", Toast.LENGTH_SHORT).show();


                        startActivity(i);
                        // Error
                    } else {
                        Log.d("error",e.toString());
                        Toast.makeText(getApplicationContext(), "Error in signup", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    private boolean isBadPassword () {
        return (password.length() < 6);
    }

    private boolean isBadEmail() {
        if (!email.contains(".") || !email.contains("@"))
            return true;
        return (email.lastIndexOf(".") <= email.lastIndexOf("@"));
    }
}
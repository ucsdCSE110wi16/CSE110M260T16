package com.example.stevenzheng.cse110tasklist;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.LogInCallback;
import android.widget.Toast;

/**
 * Created by stevenzheng on 1/20/16.
 */
public class SignIn extends Activity {

    String password;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);
    }

    public void onButtonClick(View v) {
        if (v.getId() == R.id.B_signInForm) {
            Intent i = new Intent(this, SignIn.class);
            startActivity(i);
        } else if (v.getId() == R.id.B_signUp) {
            Intent i = new Intent(this, SignUp.class);
            startActivity(i);
        }

        EditText textField = (EditText)findViewById(R.id.TextField_email);
        email = textField.getText().toString();

        textField = (EditText)findViewById(R.id.TextField_password);
        password = textField.getText().toString();

        ParseUser.logInInBackground(email, password, new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    // Hooray! The user is logged in.
                } else {
                    Toast.makeText(getApplicationContext(), "Username/Password incorrect", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}

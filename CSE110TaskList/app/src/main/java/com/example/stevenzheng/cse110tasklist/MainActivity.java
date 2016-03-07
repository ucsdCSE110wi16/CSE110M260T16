package com.example.stevenzheng.cse110tasklist;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.parse.Parse;
import com.parse.ParseObject;

import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // [Optional] Power your app with Local Datastore. For more info, go to
        // https://parse.com/docs/android/guide#local-datastore
        Parse.enableLocalDatastore(this);
        Parse.initialize(this);


        setContentView(R.layout.activity_main);
        info = (TextView) findViewById(R.id.info);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        callbackManager.onActivityResult(requestCode, resultCode, data);
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
        } else if (id == R.id.action_about) {
            Intent i = new Intent(this, About.class);
//            startActivity(i);
            startActivityForResult(i, 0);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onButtonClick(View v) {
        if (v.getId() == R.id.B_signInForm) {
            Intent i = new Intent(this, SignIn.class);
            startActivity(i);
        } else if (v.getId() == R.id.B_signUp) {
            Intent i = new Intent(this, SignUp.class);
            startActivity(i);
        }
        else if (v.getId() == R.id.B_about) {
            Intent i = new Intent(this, About.class);
            startActivity(i);
        }

    }
}


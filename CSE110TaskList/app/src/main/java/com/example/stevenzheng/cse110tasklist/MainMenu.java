package com.example.stevenzheng.cse110tasklist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import android.content.Intent;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;
import android.util.Log;


import java.util.ArrayList;
import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;


/**
 * Created by stevenzheng on 2/1/16.
 */
public class MainMenu extends Activity {

    static String groupName;
    ParseUser currentUser;
    ListView groupsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        // Get ListView object from xml
        groupsList = (ListView)findViewById(R.id.List_groups);

        // get current user
        currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            String name = currentUser.getString("firstName");
            TextView textField = (TextView)findViewById(R.id.Text_name);
            textField.setText(name);

            // Get user's current groups
            ArrayList<ParseObject> groups = new ArrayList<ParseObject>();
            groups = (ArrayList<ParseObject>)((List<ParseObject>) (Object) (currentUser.getList("groupsList")));

            int numOfGroups = groups.size();
            // Define values to show in ListView
            String[] groupNames = new String[numOfGroups];
            for (int i = 0; i < numOfGroups; i++) {
                try {
                    ParseObject currentGroup = groups.get(i);
                    String currentName = currentGroup.fetchIfNeeded().getString("name");
                    groupNames[i] = currentName;
                } catch (com.parse.ParseException e1) {
                    e1.printStackTrace();
                }

            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, groupNames);

            groupsList.setAdapter(adapter);


            groupsList.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    // ListView Clicked item index
                    int itemPosition  = position;

                    // ListView Clicked item value
                    String  itemValue    = (String) groupsList.getItemAtPosition(position);

                    // Show Alert
                    Toast.makeText(getApplicationContext(),
                            "Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_LONG)
                            .show();
                    groupName = itemValue;
                    Intent i = new Intent(MainMenu.this,TaskList.class);
                    startActivity(i);


                }

            });


        } else {
            // no current user, go back to login/signup screen
        }
    }

    // Reload groups list
    @Override
    protected void onResume(){
        super.onResume();

        // Get user's current groups
        ArrayList<ParseObject> groups = new ArrayList<ParseObject>();
        groups = (ArrayList<ParseObject>)((List<ParseObject>) (Object) (currentUser.getList("groupsList")));

        int numOfGroups = groups.size();
        // Define values to show in ListView
        String[] groupNames = new String[numOfGroups];
        for (int i = 0; i < numOfGroups; i++) {
            try {
                ParseObject currentGroup = groups.get(i);
                String currentName = currentGroup.fetchIfNeeded().getString("name");
                groupNames[i] = currentName;
            } catch (com.parse.ParseException e1) {
                e1.printStackTrace();
            }

        }
    }

    public void onButtonClick(View v) {
        if (v.getId() == R.id.B_addNewList) {
            Intent i = new Intent(this, AddGroup.class);
            startActivityForResult(i, 1);
        } else if (v.getId() == R.id.B_createNewList) {
            Intent i = new Intent(this, CreateGroup.class);
            startActivityForResult(i, 1);
        }

    }


    public void goToList(View v)
    {
        Intent i = new Intent (this, TaskList.class);
        startActivity(i);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            Log.d("result", "refreshing");
            Intent refresh = new Intent(this, MainMenu.class);
            startActivity(refresh);
            this.finish();
        }
    }

}

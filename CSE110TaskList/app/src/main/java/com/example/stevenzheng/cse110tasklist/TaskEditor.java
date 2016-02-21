package com.example.stevenzheng.cse110tasklist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TextView;

public class TaskEditor extends Activity {
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_creator);
        Intent i = getIntent();
        position = i.getIntExtra("position", -1);
    }

    public void submitTask(View v)
    {

        TextView taskName = (TextView) findViewById(R.id.taskName);
        TextView taskDesc = (TextView) findViewById(R.id.taskDesc);
        TextView taskDifc = (TextView) findViewById(R.id.taskDificulty);
        CheckBox repetitive = (CheckBox) findViewById(R.id.repetitive);
        DatePicker endDate = (DatePicker) findViewById(R.id.taskDate);

        /* MAKE A LIST OF GROUP MEMBERS HERE, pretty much identical to what I made for CreateGroup.
         * When you click on the person's name, it will assign the task to that person, overriding any previous assignment
         * Again, refer to CreateGroup
         */


        String name = taskName.getText().toString();
        String desc  = taskDesc.getText().toString();
        int difc;
        try {
            difc = Integer.parseInt(taskDifc.getText().toString());
        }
        catch(Exception e) {
            difc = 0;
        }
        boolean rep = repetitive.isChecked();
        int day = endDate.getDayOfMonth();
        int month = endDate.getMonth();
        int year =  endDate.getYear();

        /*
         * Right now, I've commented out the task object because I changed the Task class
         * Also we're no longer editing the list directly, so I've commented it out, though you
         * might still want to use the editList function depending on how you implement it
         *
         * What we did in create task is that we no longer added the task to the list using addToList
         * - we create the parse object from the task object (Task object -> Parse object)
         * - we save the object in parse
         * - when we go to the TaskList activity, it refreshes the list of tasks, searching Parse for all the
         *   tasks that belong to the group... every time it finds a task that belongs, it creates a Task object which is then
         *   added to the list (Parse Object -> Task Object)... I did this so your code, John, would still work, but not completely which
         *   is where you come in now
         *
         * What you should do is
         * - figure out how to retrieve the correct Parse Object using the information of this Task Object (parse query)
         * - Edit and save the actual parse object (for backend)
         * - Make sure TaskList reloads with the changed data
         * - Add a list to select the person to do the task... refer to CreateGroup
         *
         * Design change that I just want to make clear again - we're no longer creating/editing tasks directly,
         *   We are creating/editing parseobjects and saving them
         *   TaskList will reload all objects saved in parse and convert those objects into tasks to display everytime it loads
         */


        /*Task temp = new Task(name, desc, difc, rep, day, month, year, assignedPerson);

        //TaskList.editList(position, temp);*/

        Intent i = new Intent(TaskEditor.this, TaskList.class);
        startActivity(i);


    }

}

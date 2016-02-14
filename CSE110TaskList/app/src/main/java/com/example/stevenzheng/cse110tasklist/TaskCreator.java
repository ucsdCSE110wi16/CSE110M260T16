package com.example.stevenzheng.cse110tasklist;

/**
 * Created by User on 2/6/2016.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TextView;

/**
 * Created by User on 2/6/2016.
 */
public class TaskCreator extends Activity {

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_creator);
    }


    public void submitTask(View v)
    {

        TextView taskName = (TextView) findViewById(R.id.taskName);
        TextView taskDesc = (TextView) findViewById(R.id.taskDesc);
        TextView taskDifc = (TextView) findViewById(R.id.taskDificulty);
        CheckBox repetitive = (CheckBox) findViewById(R.id.repetitive);
        DatePicker endDate = (DatePicker) findViewById(R.id.taskDate);

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

        Task temp = new Task(name, desc, difc, rep, day, month, year);

        TaskList.addToList(temp);
        Intent i = new Intent(TaskCreator.this, TaskList.class);
        startActivity(i);




    }

    void diffOnClick(View v) {
        TextView view = (TextView) v;
        view.clearComposingText();
    }
}

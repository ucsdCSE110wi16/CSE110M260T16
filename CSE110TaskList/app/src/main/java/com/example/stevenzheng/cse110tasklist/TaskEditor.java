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

        TaskList.editList(position, temp);

        Intent i = new Intent(TaskEditor.this, TaskList.class);
        startActivity(i);


    }

}

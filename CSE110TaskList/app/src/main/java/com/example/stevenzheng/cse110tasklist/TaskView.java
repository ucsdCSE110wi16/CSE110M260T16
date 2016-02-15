package com.example.stevenzheng.cse110tasklist;

/**
 * Created by John on 2/6/2016.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

public class TaskView extends Activity {
    Task task;
    int position;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_view);
        Intent i = getIntent();
        position = i.getIntExtra("position", -1);
        task = TaskList.getTask(position);
        TextView taskName = (TextView) findViewById(R.id.Name);
        taskName.setText(task.name);
        TextView taskDesc = (TextView) findViewById(R.id.Desc);
        taskDesc.setText(task.desc);
        TextView taskDifc = (TextView) findViewById(R.id.Diff);
        taskDifc.setText("Difficulty: " + task.difc);
        TextView rep = (TextView) findViewById(R.id.Rep);
        rep.setText("Repeated?: ");
        CheckBox repetitive = (CheckBox) findViewById(R.id.Repeated);
        repetitive.setChecked(task.rep);
        TextView endDate = (TextView) findViewById(R.id.Cal);
        int month = task.month + 1;
        endDate.setText("Due: " + month + "/" + task.day + "/" + task.year);

    }


    public void editButton(View v)
    {
        Intent i = new Intent(TaskView.this, TaskEditor.class);
        i.putExtra("position", position);
        startActivity(i);
    }
}

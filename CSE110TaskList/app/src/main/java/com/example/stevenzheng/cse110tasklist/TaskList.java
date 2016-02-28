package com.example.stevenzheng.cse110tasklist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Created by User on 2/1/2016.
 */
public class TaskList extends Activity {

    static ListView lv;

    static ArrayList<Task> list=  new ArrayList<>();
    ArrayList<String> taskNames = new ArrayList<>(0);

    PriorityQueue<ParseObject> orderedTasks;
    PriorityQueue<ParseUser> orderedUsers;

    //boolean delete;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_list);

        TextView name = (TextView) findViewById(R.id.groupName);
        name.setText(MainMenu.groupName);

        // reset list
        list = new ArrayList<>();
        
        lv = (ListView) findViewById(R.id.taskList);
        /*
        final ArrayAdapter<Task> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position,
                                    long id) {
                if (delete) {
                    list.remove(position);
                    adapter.notifyDataSetChanged();
                    delete = false;
                } else {
                    Intent i = new Intent(TaskList.this, TaskView.class);
                    i.putExtra("position", position);
                    startActivity(i);
                }
            }
        });*/

        // When created, queries for tasks that belong to this group, displays all tasks found
        // in the list
        Log.d("testing", "testing");
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Task");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException e) {
                String groupName = MainMenu.groupName;

                if (e == null) {
                    int numOfTasks = 0;
                    for (int i = 0; i < objects.size(); i++) {
                        ParseObject currentTask = objects.get(i);
                        //Log.d("name", currentTask.getString("personAssigned"));
                        //Log.d("person name", name);
                        if (currentTask.getString("group").equals(groupName)) {
                            Log.d("result", "equal");
                            taskNames.add(numOfTasks, currentTask.getString("name"));

                            String taskName = currentTask.getString("name");
                            String taskDesc = currentTask.getString("desc");
                            int taskDifc = currentTask.getInt("difc");
                            boolean taskRep = currentTask.getBoolean("rep");
                            int taskDay = currentTask.getInt("day");
                            int taskMonth = currentTask.getInt("month");
                            int taskYear = currentTask.getInt("year");
                            String taskPersonAssigned = currentTask.getString("personAssigned");
                            String taskGroup = currentTask.getString("group");
                            Task task = new Task(taskName, taskDesc, taskDifc, taskRep, taskDay, taskMonth
                                    , taskYear, taskPersonAssigned, taskGroup);
                            addToList(task);
                            numOfTasks++;


                        } else {
                            Log.d("result", "not equal");
                        }

                        for (int x = 0; x < list.size(); x ++) {
                            Task task = TaskList.getTask(x);
                            Log.d("name", task.name);
                        }
                    }

                    final ArrayAdapter<String> adapter = new ArrayAdapter<String>(TaskList.this,
                            android.R.layout.simple_list_item_1, android.R.id.text1, taskNames);
                    lv.setAdapter(adapter);

                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> a, View v, int position,
                                                long id) {
                            /*if (delete) {
                                list.remove(position);
                                adapter.notifyDataSetChanged();
                                delete = false;
                            } else {*/
                                Intent i = new Intent(TaskList.this, TaskView.class);
                                Log.d("position", Integer.toString(position));
                                Log.d("name", taskNames.get(position));
                                i.putExtra("name", taskNames.get(position));
                                i.putExtra("position", position);
                                startActivity(i);
                            //}
                        }
                    });



                } else {
                    Log.d("Unsuccessful", "query");

                }
            }
        });
    }
    /*
    // Refresh the task list after someone creates task
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            Log.d("result", "Refreshing Task list");
            Intent refresh = new Intent(this, TaskList.class);
            startActivity(refresh);
            this.finish();
        }
    }*/

    static class TaskSort implements Comparator<ParseObject> {
        @Override
        public int compare(ParseObject lhs, ParseObject rhs) {
            return lhs.getInt("difc") - rhs.getInt("difc");
        }
    }

    static class UserSort implements Comparator<ParseUser> {
        @Override
        public int compare(ParseUser lhs, ParseUser rhs) {
            int returnVal = 0;
            try {
                returnVal = lhs.fetchIfNeeded().getInt("totalDifficulty") - rhs.fetchIfNeeded().getInt("totalDifficulty");
            } catch (ParseException e){
                e.printStackTrace();
            }

            double random = Math.random();
            if (random >= 0.5) {
                return -1;
            } else {
                return 1;
            }
        }
    }
    public void randomizeAssignment(View v) {
        // order tasts

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Task");
        query.whereEqualTo("group", MainMenu.groupName);
        try {
            List<ParseObject> parseTasks = query.find();
            String groupName = MainMenu.groupName;
            TaskSort taskSort = new TaskSort();
            orderedTasks = new PriorityQueue<ParseObject>(1, taskSort);

            for (int i = 0; i < parseTasks.size(); i++) {
                ParseObject currentTask = parseTasks.get(i);
                currentTask.put("personAssigned", "");
                currentTask.save();
                orderedTasks.offer(currentTask);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        // order members

        // get members
        ParseQuery<ParseObject> membersQuery = ParseQuery.getQuery("Group");
        membersQuery.whereEqualTo("name", MainMenu.groupName);
        try {
            List<ParseObject> parseGroups = membersQuery.find();
            String groupName = MainMenu.groupName;
            ParseObject group = parseGroups.get(0);

            ArrayList<ParseUser> members = new ArrayList<ParseUser>();
            members = (ArrayList<ParseUser>) ((List<ParseUser>) (Object) (group.getList("members")));
            UserSort userSort = new UserSort();
            orderedUsers = new PriorityQueue<ParseUser>(1, userSort);
            for (int j = 0; j < members.size(); j++) {

                ParseUser currentUser = members.get(j);
                            /*
                            try {
                                currentUser.fetchIfNeeded();
                                Log.d("TESTING", currentUser.getString("firstName"));
                                int difficulty = 0;
                                currentUser.put("totalDifficultys", difficulty);
                                currentUser.saveInBackground();
                            } catch (ParseException e1) {
                                e1.printStackTrace();
                            }*/
                orderedUsers.offer(currentUser);

            }


            while ((orderedTasks.size() > 0) && (orderedUsers.size() > 0)) {
                ParseObject currentTask = orderedTasks.poll();
                ParseUser currentUser = orderedUsers.poll();
                String name = currentUser.getString("firstName") + " " + currentUser.getString("lastName");
                currentTask.put("personAssigned", name);
                currentTask.saveInBackground();
                orderedUsers.offer(currentUser);
            }
        } catch (ParseException e) {
            e.printStackTrace();

        }
        /*
        membersQuery.findInBackground(new FindCallback<ParseObject>() {

            public void done(List<ParseObject> objects, ParseException e) {
                String groupName = MainMenu.groupName;
                ParseObject group;
                for (int i = 0; i < objects.size(); i++) {
                    ParseObject currentGroup = objects.get(i);
                    if (currentGroup.getString("name").equals(groupName)) {
                        group = currentGroup;
                        ArrayList<ParseUser> members = new ArrayList<ParseUser>();
                        members = (ArrayList<ParseUser>) ((List<ParseUser>) (Object) (group.getList("members")));
                        UserSort userSort = new UserSort();
                        orderedUsers = new PriorityQueue<ParseUser>(1, userSort);
                        for (int j = 0; j < members.size(); j++) {

                            ParseUser currentUser = members.get(j);

                            try {
                                currentUser.fetchIfNeeded();
                                Log.d("TESTING", currentUser.getString("firstName"));
                                int difficulty = 0;
                                currentUser.put("totalDifficultys", difficulty);
                                currentUser.saveInBackground();
                            } catch (ParseException e1) {
                                e1.printStackTrace();
                            }
                            orderedUsers.offer(currentUser);

                        }


                    } else {
                        Log.d("result", "not equal");
                    }
                }

            }
        });*/


    }

    public static Task getTask(int position) {
        Log.d("get task position", Integer.toString(position));
        Task task = list.get(position);
        Log.d("get task name", task.name);
        return task;
    }

    public static void editList(int position, Task task) {
        list.set(position, task);
        lv.invalidateViews();
    }

    public static void addToList(Task task)
    {
        list.add(task);
        lv.invalidateViews();
    }

    /*public void onDeleteClick(View v) {
        delete = true;
    }*/

    public void onMembersClick(View v) {
        Intent i = new Intent(this, Members.class);
        startActivity(i);
    }

    public void onYourTasksClick(View v) {
        Intent i = new Intent(this, YourTaskList.class);
        startActivity(i);
    }
    public void buttonOnClick(View v)
    {
        Intent i = new Intent(TaskList.this, TaskCreator.class);
        startActivity(i);
        //startActivityForResult(i, 1);

   /* TextView edit = (TextView) findViewById(R.id.newTask);

String temp = edit.getText().toString();
    System.out.println("nik you fucker");
    list.add(temp);
    System.out.println("nik has a tiny dick");
    lv.invalidateViews();
    System.out.println("nik doesn't know how to spell his name");
    edit.setText("");
    */

    }


}

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

    static Task thisTask;
    static ListView lv;

    static ArrayList<Task> list=  new ArrayList<>();
    ArrayList<String> taskNames = new ArrayList<>(0);

    PriorityQueue<ParseObject> orderedTasks;
    PriorityQueue<ParseObject> orderedTaskGroups;
    PriorityQueue<ParseObject> orderedUserDifficulties;

    //boolean delete;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_list);

        TextView name = (TextView) findViewById(R.id.groupName);
        name.setText(MainMenu.groupName);
        refreshList();

    }

    public void refreshList() {
        // reset list
        list = new ArrayList<>();

        lv = (ListView) findViewById(R.id.taskList);
        taskNames = new ArrayList<String>();
        ArrayAdapter<String> adapterClear = new ArrayAdapter<String>(TaskList.this,
                android.R.layout.simple_list_item_1, android.R.id.text1, taskNames);
        lv.setAdapter(adapterClear);
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
                            taskNames.add(numOfTasks, currentTask.getString("name"));

                            String taskName = currentTask.getString("name");
                            String taskDesc = currentTask.getString("desc");
                            int taskDifc = currentTask.getInt("difc");
                            boolean taskRep = currentTask.getBoolean("rep");
                            int taskDay = currentTask.getInt("day");
                            int taskMonth = currentTask.getInt("month");
                            int taskYear = currentTask.getInt("year");
                            String taskPersonAssigned = currentTask.getString("personAssigned");
                            String taskList = currentTask.getString("group");
                            String taskGroup = currentTask.getString("taskGroup");
                            Task task = new Task(taskName, taskDesc, taskDifc,taskGroup, taskRep, taskDay, taskMonth
                                    , taskYear, taskPersonAssigned, taskList);
                            addToList(task);
                            numOfTasks++;


                        } else {
                            Log.d("result", "not equal");
                        }

                        for (int x = 0; x < list.size(); x ++) {
                            Task task = TaskList.getTask(x);
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
                            i.putExtra("name", taskNames.get(position));
                            i.putExtra("position", position);
                            thisTask = TaskList.getTask(position);
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
            return rhs.getInt("difc") - lhs.getInt("difc");
        }
    }

    static class TaskGroupSort implements Comparator<ParseObject> {
        @Override
        public int compare(ParseObject lhs, ParseObject rhs) {
            return rhs.getInt("totalDifficulty") - lhs.getInt("totalDifficulty");
        }
    }

    static class UserSort implements Comparator<ParseObject> {
        @Override
        public int compare(ParseObject lhs, ParseObject rhs) {
            int returnVal = 0;
            try {
                returnVal = lhs.fetchIfNeeded().getInt("totalDifficulty") - rhs.fetchIfNeeded().getInt("totalDifficulty");
                if (returnVal != 0) {
                    return returnVal;
                } else {
                    double random = Math.random();
                    if (random >= 0.5) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
            } catch (ParseException e){
                e.printStackTrace();
            }

            return  returnVal;
        }
    }
    public void lock(View v) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Lock");
        query.whereEqualTo("group", MainMenu.groupName);
        try {
            List<ParseObject> lock = query.find();
            for (int i = 0; i < lock.size(); i++) {
                ParseUser currentUser = ParseUser.getCurrentUser();
                String fullName = currentUser.getString("firstName") + " " + currentUser.getString("lastName");
                ParseObject currentLock = lock.get(i);
                if (currentLock.getInt("active") == 0) {
                    currentLock.put("active", 1);
                    currentLock.put("owner", fullName);
                } else {
                    /*
                    Log.d("Attemption: ", "unlock");
                    Log.d("owner", currentLock.getString("owner"));
                    Log.d("fullname", fullName);
                    int compare = currentLock.getString("owner").compareTo(fullName);
                    Log.d("compare", Integer.toString(compare));*/
                    if ((currentLock.getString("owner").compareTo(fullName)) == 0) {
                        currentLock.put("owner", "");
                        currentLock.put("active", 0);
                        Log.d("Successful: ", "unlock");
                    } else {
                        // Current user does not own lock... cannot turn off lock
                        Log.d("Unsuccessful: ", "unlock");
                    }
                }
                currentLock.saveInBackground();
            }
        } catch (ParseException e) {

        }
    }
    public void randomizeAssignment(View v) {

        ParseQuery<ParseObject> lockQuery = ParseQuery.getQuery("Lock");
        lockQuery.whereEqualTo("group", MainMenu.groupName);
        try {
            List<ParseObject> lock = lockQuery.find();
            for (int q = 0; q < lock.size(); q++) {

                ParseObject currentLock = lock.get(q);
                if (currentLock.getInt("active") == 0) {
                    Log.d("action", "randomizing");
                    // clear task assignment

                    ParseQuery<ParseObject> query = ParseQuery.getQuery("Task");
                    query.whereEqualTo("group", MainMenu.groupName);
                    try {
                        List<ParseObject> parseTasks = query.find();
                        //String groupName = MainMenu.groupName;
                        //TaskSort taskSort = new TaskSort();
                        //orderedTasks = new PriorityQueue<ParseObject>(1, taskSort);

                        for (int i = 0; i < parseTasks.size(); i++) {
                            ParseObject currentTask = parseTasks.get(i);
                            currentTask.put("personAssigned", "");
                            currentTask.save();
                            //orderedTasks.offer(currentTask);
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    // order task groups
                    ParseQuery<ParseObject> groupsQuery = ParseQuery.getQuery("TaskGroup");
                    groupsQuery.whereEqualTo("group", MainMenu.groupName);
                    try {
                        List<ParseObject> parseTaskGroups = groupsQuery.find();
                        TaskGroupSort taskGroupSort = new TaskGroupSort();
                        orderedTaskGroups = new PriorityQueue<ParseObject>(1, taskGroupSort);

                        for (int i = 0; i < parseTaskGroups.size(); i++) {
                            ParseObject currentTaskGroup = parseTaskGroups.get(i);
                            orderedTaskGroups.offer(currentTaskGroup);
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
                        orderedUserDifficulties = new PriorityQueue<ParseObject>(1, userSort);
                        for (int j = 0; j < members.size(); j++) {

                            ParseUser currentUser = (members.get(j)).fetchIfNeeded();
                            String currentName = currentUser.getString("firstName") + " " + currentUser.get("lastName");
                            ParseQuery<ParseObject> parseUserDifficultyQuery = ParseQuery.getQuery("UserDifficulty");
                            parseUserDifficultyQuery.whereEqualTo("name", currentName);
                            try {
                                List<ParseObject> parseUserDifficulty = parseUserDifficultyQuery.find();
                                int reset = 0;
                                (parseUserDifficulty.get(0)).put("totalDifficulty", reset);
                                (parseUserDifficulty.get(0)).saveInBackground();
                                orderedUserDifficulties.offer(parseUserDifficulty.get(0));
                            } catch (ParseException e) {

                            }

                        }

                        Log.d("users size: ", Integer.toString(orderedUserDifficulties.size()));


                        while ((orderedTaskGroups.size() > 0) && (orderedUserDifficulties.size() > 0)) {
                            Log.d("Action: ", "Randomizing");
                            Log.d("number of groups: ", Integer.toString(orderedTaskGroups.size()));

                            /* Get highest difficulty group, and lowest difficulty user */
                            ParseObject currentTaskGroup = orderedTaskGroups.poll();
                            Log.d("current group name: ", currentTaskGroup.getString("name"));
                            ParseObject currentUser = orderedUserDifficulties.poll();

                            /* Assign all tasks in the group to the lowest difficulty user */
                            ParseQuery<ParseObject> parseTaskQuery = ParseQuery.getQuery("Task");
                            parseTaskQuery.whereEqualTo("taskGroup", currentTaskGroup.getString("name"));
                            try {
                                List<ParseObject> tasks = parseTaskQuery.find();
                                Log.d("subtasks num: ", Integer.toString(tasks.size()));
                                for (int t = 0; t < tasks.size(); t++) {
                                    ParseObject currentTask = tasks.get(t);
                                    String name = currentUser.getString("name");
                                    Log.d("task name", currentTask.getString("name"));
                                    int currentTotalDifficulty = currentUser.getInt("totalDifficulty");
                                    currentTotalDifficulty += currentTask.getInt("difc");
                                    Log.d("newTotalDiff: ", Integer.toString(currentTotalDifficulty));
                                    currentUser.put("totalDifficulty", currentTotalDifficulty);
                                    currentUser.saveInBackground();
                                    currentTask.put("personAssigned", name);
                                    currentTask.saveInBackground();
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            /* Place user back into PQ */
                            orderedUserDifficulties.offer(currentUser);
                        }

                        // update the list
                        //refreshList();

                        Intent refresh = new Intent(this, TaskList.class);
                        startActivity(refresh);
                        this.finish();

                    } catch (ParseException e) {
                        e.printStackTrace();

                    }

                } else {
                    // Lock is active, Cannot randomize
                }
            }
        } catch (ParseException e) {

        }



    }

    public static Task getTask(int position) {
        //Log.d("get task position", Integer.toString(position));
        Task task = list.get(position);
        //Log.d("get task name", task.name);
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

    public void onLogClick(View v) {
        Intent i = new Intent(this, Log.class);
        startActivity(i);
    }

    public void onYourTasksClick(View v) {
        Intent i = new Intent(this, YourTaskList.class);
        startActivityForResult(i, 1);
    }
    public void buttonOnClick(View v)
    {
        Intent i = new Intent(TaskList.this, TaskCreator.class);
        //startActivity(i);
        startActivityForResult(i, 1);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            Log.d("result", "refreshing");
            Intent refresh = new Intent(this, TaskList.class);
            startActivity(refresh);
            this.finish();
        }
        if(resultCode==RESULT_CANCELED){

        }

        if(resultCode==RESULT_FIRST_USER){
            Log.d("result", "refreshing");
            Intent refresh = new Intent(this, TaskList.class);
            startActivity(refresh);
            this.finish();
        }
    }
}

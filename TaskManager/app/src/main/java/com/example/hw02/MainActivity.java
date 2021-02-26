package com.example.hw02;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    final String TAG = "To-Do List";
    final static public int REQ_CODE_CREATE = 100;
    final static public int REQ_CODE_DISPLAY = 200;
    final static public String DISPLAY_KEY = "DISPLAY";

    ArrayList<Task> tasks = new ArrayList<>();
    CharSequence[] taskNames;
    TextView numTasksTextView;
    TextView taskDateTextView;
    TextView taskNameTextView;
    TextView taskPriorityTextView;
    Button viewTasksButton;
    Button createTaskButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate:");
        setTitle("To Do List");

        // initialize text views
        numTasksTextView = findViewById(R.id.numTasksTextView);
        taskDateTextView = findViewById(R.id.taskDateTextView);
        taskNameTextView = findViewById(R.id.taskNameTextView);
        taskPriorityTextView = findViewById(R.id.taskPriorityTextView);

        // initialize buttons
        viewTasksButton = findViewById(R.id.viewTasksButton);
        createTaskButton = findViewById(R.id.createTaskButton);
        // initialize # tasks text to match ArrayList size
        numTasksTextView.setText("You have " + tasks.size() + " task(s)");

        viewTasksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: View Tasks");

                // initialize array size
                taskNames = new CharSequence[tasks.size()];
                // populate array with taskNames
                for (int i = 0; i < tasks.size(); i++) {
                    taskNames[i] = tasks.get(i).name;
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Select Task")
                        .setItems(taskNames, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // if arraylist empty, set current task to none
                                if (tasks.size() == 0) {
                                    taskNameTextView.setText("None");
                                }
                                // create intent with appropriate task data
                                Intent intent = new Intent(MainActivity.this, DisplayTaskActivity.class);
                                // taskNames array will have same indices as tasks ArrayList, so simply get task with index
                                Task taskToSend = tasks.get(which);
                                // add the task as an extra
                                intent.putExtra(DISPLAY_KEY, taskToSend);
                                startActivityForResult(intent, REQ_CODE_DISPLAY);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d(TAG, "onClick: Cancel");
                            }
                        });
                builder.create().show();
            }
        });

        // set click listener to create new task
        createTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Create activity");
                Intent intent = new Intent(MainActivity.this, CreateTaskActivity.class);
                startActivityForResult(intent, REQ_CODE_CREATE);
            }
        });
    }

    public void updateTaskInfo() {
        // sort tasks by date
        Collections.sort(tasks, new Comparator<Task>() {
            // create SimpleDateFormat for comparison of date strings
            SimpleDateFormat taskDateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
            @Override
            public int compare(Task o1, Task o2) {
                // attempt to sort by using date format
                int compareVal = 0;
                try {
                    compareVal = taskDateFormat.parse(o1.date).compareTo(taskDateFormat.parse(o2.date));
                    return compareVal;
                } catch (ParseException e) {
                    // log potential ParseException
                    Log.d(TAG, "onActivityResult: REQ_CODE_DISPLAY: compare: ParseException thrown: " + e.getMessage());
                    Toast.makeText(MainActivity.this, "Could not parse date", Toast.LENGTH_SHORT).show();
                    return compareVal;
                }
            }
        });

        // update to show task with closest date
        taskNameTextView.setText(tasks.get(0).name);
        taskDateTextView.setText(tasks.get(0).date);
        taskPriorityTextView.setText(tasks.get(0).priority);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE_CREATE) {
            if (resultCode == RESULT_OK) {
                Log.d(TAG, "onActivityResult: REQ_CODE_CREATE: RESULT_OK");
                // check to ensure data was entered and that said data has expected Task extra
                if (data != null && data.hasExtra(CreateTaskActivity.TASK_DATA)) {
                    // obtain the corresponding task and add it to the ArrayList
                    Task createdTask = (Task) data.getSerializableExtra(CreateTaskActivity.TASK_DATA);
                    tasks.add(createdTask);

                    // update # tasks text to match ArrayList size
                    numTasksTextView.setText("You have " + tasks.size() + " task(s)");

                    // update views and task ArrayList accordingly
                    updateTaskInfo();
                }
            } else if (resultCode == RESULT_CANCELED) {
                Log.d(TAG, "onActivityResult: REQ_CODE_CREATE: RESULT_CANCELED");
            }
        } else if (requestCode == REQ_CODE_DISPLAY) {
            if (resultCode == RESULT_OK) {
                Log.d(TAG, "onActivityResult: REQ_CODE_DISPLAY: RESULT_OK");
                // check to ensure data was entered and that said data has expected Task extra
                if (data != null && data.hasExtra(DisplayTaskActivity.TASK_DELETION_DATA)) {
                    try {
                        // obtain the corresponding task and delete it
                        Task taskToDelete = (Task) data.getSerializableExtra(DisplayTaskActivity.TASK_DELETION_DATA);
                        Log.d(TAG, "onActivityResult: task to delete: " + taskToDelete.name + " " + taskToDelete.date + " " + taskToDelete.priority);
                        tasks.remove(taskToDelete);

                        // update # tasks text to match ArrayList size
                        numTasksTextView.setText("You have " + tasks.size() + " task(s)");
                        // if no tasks exist currently, change text to indicate this
                        if (tasks.size() == 0) {
                            taskNameTextView.setText("None");
                            taskDateTextView.setText("");
                            taskPriorityTextView.setText("");
                            return;
                        }
                        // otherwise sort and update text accordingly
                        updateTaskInfo();
                    } catch (Exception e) {
                        Log.d(TAG, "onActivityResult: REQ_CODE_DISPLAY: RESULT_OK: Exception thrown: " + e.getMessage());
                        Toast.makeText(this, "Could not successfully delete task", Toast.LENGTH_SHORT).show();
                    }
                }
                
            } else if (resultCode == RESULT_CANCELED) {
                Log.d(TAG, "onActivityResult: REQ_CODE_DISPLAY: RESULT_CANCELED");
            }
        }
    }
}
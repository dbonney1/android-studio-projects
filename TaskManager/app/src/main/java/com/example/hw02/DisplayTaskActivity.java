package com.example.hw02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DisplayTaskActivity extends AppCompatActivity {

    final String TAG = "Display Task Activity";
    final static public String TASK_DELETION_DATA = "TASK_DELETION_DATA";
    TextView taskNameTextViewDisplay;
    TextView taskDateTextViewDisplay;
    TextView taskPriorityTextViewDisplay;
    Button cancelButtonDisplay;
    Button deleteButtonDisplay;
    Task displayedTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_task);
        Log.d(TAG, "onCreate: ");
        setTitle("Display Task");
        // initialize TextViews
        taskNameTextViewDisplay = findViewById(R.id.taskNameTextViewDisplay);
        taskDateTextViewDisplay = findViewById(R.id.taskDateTextViewDisplay);
        taskPriorityTextViewDisplay = findViewById(R.id.taskPriorityTextViewDisplay);

        // initialize buttons
        cancelButtonDisplay = findViewById(R.id.cancelButtonDisplay);
        deleteButtonDisplay = findViewById(R.id.deleteButtonDisplay);

        // check to ensure intent exists and contains Task object as an extra
        if (getIntent() != null && getIntent().getExtras() != null && getIntent().hasExtra(MainActivity.DISPLAY_KEY)) {
            // Task is serializable, so retrieve serializable extra
            displayedTask = (Task) getIntent().getSerializableExtra(MainActivity.DISPLAY_KEY);
            // now populate TextViews with task data
            taskNameTextViewDisplay.setText(displayedTask.name);
            taskDateTextViewDisplay.setText(displayedTask.date);
            taskPriorityTextViewDisplay.setText(displayedTask.priority);
        }
        // set click listeners for buttons
        deleteButtonDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: deleteButtonDisplay ClickListener");
                if (displayedTask != null) {
                    Task taskToDelete = displayedTask;
                    // send task data via intent
                    Intent intent = new Intent();
                    intent.putExtra(TASK_DELETION_DATA, taskToDelete);
                    // intent resolved with data, so RESULT_OK
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
        cancelButtonDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: cancelButtonDisplay ClickListener");
                // action canceled, so RESULT_CANCELED
                setResult(RESULT_CANCELED);
                finish();
            }
        });

    }
}
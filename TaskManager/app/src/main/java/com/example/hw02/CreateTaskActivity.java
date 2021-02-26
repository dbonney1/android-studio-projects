package com.example.hw02;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

/**
 * HW 02
 * CreateTaskActivity.java
 * Devin Bonney and David Hotaran
 */
public class CreateTaskActivity extends AppCompatActivity {

    final String TAG = "Create Task Activity";
    final static public String TASK_DATA = "TASK_DATA";
    EditText taskNameEditText;
    TextView taskDateTextViewCreate;
    RadioGroup prioritiesRadioGroup;
    Button setDateButton;
    Button cancelButtonCreate;
    Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        setTitle("Create Task");
        // initialize views and radiogroup
        taskNameEditText = findViewById(R.id.taskNameEditText);
        taskDateTextViewCreate = findViewById(R.id.taskDateTextViewCreate);
        prioritiesRadioGroup = findViewById(R.id.prioritiesRadioGroup);

        // initialize buttons and listeners
        setDateButton = findViewById(R.id.setDateButton);
        cancelButtonCreate = findViewById(R.id.cancelButtonCreate);
        submitButton = findViewById(R.id.submitButton);

        setDateButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                // get instance of calendar to sync current year, month and day with datePicker
                Calendar calendar = Calendar.getInstance();
                int calYear = calendar.get(Calendar.YEAR);
                int calMonth = calendar.get(Calendar.MONTH);
                int calDay = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(CreateTaskActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // set format of date
                        try {
                            // prevent user from using a previous date
                            if ((year < calYear) || (year == calYear && month < calMonth) || (year == calYear && month == calMonth && dayOfMonth < calDay)) {
                                throw new Exception("Cannot use past date");
                            }
                            taskDateTextViewCreate.setText(month + 1 + "/" + dayOfMonth + "/" + year);
                        } catch (Exception e) {
                            Log.d(TAG, "onDateSet: Exception thrown: " + e.getMessage());
                            Toast.makeText(CreateTaskActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, calYear, calMonth, calDay);
                datePicker.show();
            }
        });
        // listeners for submit
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onCreate: Submit");
                // if any fields empty, throw exception
                try {
                    // use radioGroup checked id to grab text for priority
                    int checkedId = prioritiesRadioGroup.getCheckedRadioButtonId();
                    RadioButton selectedPriority = findViewById(checkedId);

                    String name = taskNameEditText.getText().toString().trim();
                    String date = taskDateTextViewCreate.getText().toString();

                    if (checkedId == 0 || name.length() == 0 || date.length() == 0) {
                        throw new Exception("No fields can be empty");
                    }
                    String priority = selectedPriority.getText().toString();
                    // create Task to return via intent
                    Task newTask = new Task(name, date, priority);
                    Intent intent = new Intent();
                    // add task as serializable extra
                    intent.putExtra(TASK_DATA, newTask);
                    // intent resolved with data so RESULT_OK
                    setResult(RESULT_OK, intent);
                    finish();
                } catch (Exception e) {
                    Log.d(TAG, "Submit onClick: Exception thrown: " + e.getMessage());
                    Toast.makeText(CreateTaskActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });

        cancelButtonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Cancel");
                // action canceled, so RESULT_CANCELED
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }
}
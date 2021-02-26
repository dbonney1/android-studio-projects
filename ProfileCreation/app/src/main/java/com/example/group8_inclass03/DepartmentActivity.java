package com.example.group8_inclass03;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
/**
 * In Class 03
 * DepartmentActivity.java
 * Devin Bonney and David Hotaran
 */
public class DepartmentActivity extends AppCompatActivity {

    final String TAG = "Department Activity";
    final static public String DATA_ENTERED = "DATA_ENTERED";
    RadioGroup deptRadioGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Log.d(TAG, "onCreate: ");

        // set title of activity to Department
        setTitle("Department");
        // initialize department radio group
        deptRadioGroup = findViewById(R.id.departmentRadioGroup);

        // add listeners for submit and cancel buttons
        findViewById(R.id.selectButtonProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Select onClick: ");
                // use radioGroup's checkedId and resolve accordingly
                int checkedId = deptRadioGroup.getCheckedRadioButtonId();
                RadioButton selectedButton = findViewById(checkedId);
                // retrieve text attached to selected radio button
                String departmentToSendBack = selectedButton.getText().toString();
                // send String data via intent
                Intent intent = new Intent();
                intent.putExtra(DATA_ENTERED, departmentToSendBack);
                // intent resolved with data, so RESULT_OK
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        findViewById(R.id.cancelButtonProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Cancel onClick: ");
                // action canceled, so RESULT_CANCELED
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }
}
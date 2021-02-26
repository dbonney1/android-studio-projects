package com.example.group8_inclass03;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * In Class 03
 * RegistrationActivity.java
 * Devin Bonney and David Hotaran
 */
public class RegistrationActivity extends AppCompatActivity {

    final String TAG = "Registration Activity";
    final static public String PROFILE_KEY = "PROFILE";
    final static public int REQ_CODE = 100;
    EditText editTextName;
    EditText editTextEmail;
    EditText editTextId;
    TextView deptTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: ");
        // set title of activity to Registration
        setTitle("Registration");

        // initialize EditText views
        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextId = findViewById(R.id.editTextId);
        deptTextView = findViewById(R.id.deptTextView);
        // create click listener for dept select button
        findViewById(R.id.selectButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Select onClick: ");
                // send new intent to Department Activity, awaiting result data
                Intent intent = new Intent("com.example.group8_inclass03.intent.action.VIEW");
                startActivityForResult(intent, REQ_CODE);
            }
        });
        // create click listener for submit button
        findViewById(R.id.submitButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Log.d(TAG, "Submit onClick: ");
                    // create an intent for the Profile Activity
                    Intent intent = new Intent(RegistrationActivity.this, ProfileActivity.class);
                    String name = editTextName.getText().toString();
                    String email = editTextEmail.getText().toString();
                    String department = deptTextView.getText().toString();
                    String idString = editTextId.getText().toString();
                    // ensure no fields are empty
                    if (name.isEmpty() || email.isEmpty() || department.isEmpty() || idString.isEmpty()) {
                        Toast.makeText(RegistrationActivity.this, "Fields cannot be empty!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    // ensure name is valid
                    if (!name.matches("[a-zA-Z]+$")) {
                        Toast.makeText(RegistrationActivity.this, "Name can only contain alphabet characters.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    // ensure email is valid
                    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        Toast.makeText(RegistrationActivity.this, "Please enter a valid email.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    // if id > 9 digits throw an error
                    if (idString.length() > 9) {
                        throw new NumberFormatException("ID can only be max of 9 digits");
                    }
                    // parse idString for an int
                    int id = Integer.parseInt(idString);
                    // add the Profile as an extra
                    intent.putExtra(PROFILE_KEY, new Profile(name, email, department, id));
                    startActivity(intent);
                } catch(NumberFormatException e) {
                    Log.d(TAG, "Submit onClick: Encountered a NumberFormatException: " + e.getMessage());
                    Toast.makeText(RegistrationActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Log.d(TAG, "Submit onClick: Encountered a generic Exception: " + e.getMessage());
                    Toast.makeText(RegistrationActivity.this, "An unknown error has occurred", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE) {
            if (resultCode == RESULT_OK) {
                // check to ensure data was entered and that said data has the expected extra
                if (data != null && data.hasExtra(DepartmentActivity.DATA_ENTERED)) {
                    // obtain the String data and set the deptTextView text to it
                    String deptData = data.getStringExtra(DepartmentActivity.DATA_ENTERED);
                    deptTextView.setText(deptData);
                }
                Log.d(TAG, "onActivityResult: RESULT_OK");
            } else if (resultCode == RESULT_CANCELED) {
                Log.d(TAG, "onActivityResult: RESULT_CANCELED");
            }
        }
    }
}
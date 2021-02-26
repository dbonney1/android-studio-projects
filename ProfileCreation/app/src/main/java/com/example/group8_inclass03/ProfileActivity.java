package com.example.group8_inclass03;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
/**
 * In Class 03
 * ProfileActivity.java
 * Devin Bonney and David Hotaran
 */
public class ProfileActivity extends AppCompatActivity {

    final String TAG = "Profile Activity";
    TextView nameTextViewProfile;
    TextView emailTextViewProfile;
    TextView idTextViewProfile;
    TextView deptTextViewProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        Log.d(TAG, "onCreate: ");

        // set name of activity to Profile
        setTitle("Profile");
        // initialize TextViews
        nameTextViewProfile = findViewById(R.id.nameTextViewProfile);
        emailTextViewProfile = findViewById(R.id.emailTextViewProfile);
        idTextViewProfile = findViewById(R.id.idTextViewProfile);
        deptTextViewProfile = findViewById(R.id.deptTextViewProfile);
        // check to ensure intent exists and contains profile as extra
        if (getIntent() != null && getIntent().getExtras() != null && getIntent().hasExtra(RegistrationActivity.PROFILE_KEY)) {
            // profile is parcelable, so retrieve parcelable extra
            Profile userProfile = getIntent().getParcelableExtra(RegistrationActivity.PROFILE_KEY);
            // now populate TextViews with data
            nameTextViewProfile.setText(userProfile.name);
            emailTextViewProfile.setText(userProfile.email);
            idTextViewProfile.setText(String.valueOf(userProfile.id));
            deptTextViewProfile.setText(userProfile.department);
        }
    }
}
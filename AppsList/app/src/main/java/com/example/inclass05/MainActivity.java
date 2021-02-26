package com.example.inclass05;

import androidx.annotation.LongDef;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements LoginFragment.IListener {
    /**
     * Assignment: In Class 05
     * MainActivity.java
     * Devin Bonney and David Hotaran
     */
    final String TAG = "Main Activity";
    final String LOGIN_FRAGMENT_TAG = "LoginFragment";
    final String APP_CATEGORY_FRAGMENT_TAG = "AppCatFragment";
    final String APP_LIST_FRAGMENT_TAG = "AppsListFragment";
    final String APP_DETAILS_FRAGMENT_TAG = "AppDetailsFragment";
    String token;
    String category;
    DataServices.App app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // initially use login fragment
        getSupportFragmentManager().beginTransaction()
                .add(R.id.containerView, new LoginFragment(), LOGIN_FRAGMENT_TAG)
                .commit();
    }

    @Override
    public void setCategoryGoToAppListFragment(String token, String category) {
        Log.d(TAG, "setCategoryGoToAppListFragment: ");
        this.category = category;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, AppsListFragment.newInstance(this.token, this.category), APP_LIST_FRAGMENT_TAG)
                .addToBackStack(APP_LIST_FRAGMENT_TAG)
                .commit();
    }

    @Override
    public void setAppGoToDetailsFragment(DataServices.App app) {
        Log.d(TAG, "setAppGoToDetailsFragment: ");
        this.app = app;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, AppDetailsFragment.newInstance(app), APP_DETAILS_FRAGMENT_TAG)
                .addToBackStack(APP_DETAILS_FRAGMENT_TAG)
                .commit();
    }

    @Override
    public void deleteAccountGoToLogin() {
        Log.d(TAG, "deleteAccountGoToLogin: ");
        this.token = null;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new LoginFragment(), LOGIN_FRAGMENT_TAG)
                .commit();
    }

    @Override
    public void setTokenGoToAppCatFragment(String token) {
        Log.d(TAG, "setTokenGoToAppCatFragment: ");
        this.token = token;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, AppCategoriesFragment.newInstance(this.token), APP_CATEGORY_FRAGMENT_TAG)
                .addToBackStack(APP_CATEGORY_FRAGMENT_TAG)
                .commit();
    }
}
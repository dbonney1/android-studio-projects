package com.example.inclass04;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/**
 * Assignment: In Class 04
 * MainActivity.java
 * Devin Bonney and David Hotaran
 */
public class MainActivity extends AppCompatActivity implements LoginFragment.IListener {

    final String TAG = "Main Activity";
    final String LOGIN_FRAGMENT_TAG = "LoginFragment";
    final String ACCOUNT_FRAGMENT_TAG = "AccountFragment";
    final String UPDATE_ACCOUNT_FRAGMENT_TAG = "UpdateAccountFragment";
    DataServices.Account userAccount;

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
    public DataServices.Account getAccount() {
        Log.d(TAG, "getAccount: ");
        return this.userAccount;
    }

    @Override
    public void deleteAccountGoToLogin() {
        Log.d(TAG, "deleteAccountGoToLogin: ");
        this.userAccount = null;
        // return to login fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new LoginFragment(), LOGIN_FRAGMENT_TAG)
                .commit();
    }

    @Override
    public void setAccountUpdateAccountProfile(DataServices.Account account) {
        Log.d(TAG, "setAccountUpdateAccountProfile: ");
        this.userAccount = account;
        int index = getSupportFragmentManager().getBackStackEntryCount() - 1;
        // obtain backstackentry at index in backstack and obtain its name
        FragmentManager.BackStackEntry lastEntry = getSupportFragmentManager().getBackStackEntryAt(index);
        String lastEntryName = lastEntry.getName();
        // tag and backstack entry name are initialized the same, so obtain fragment from entry name
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(lastEntryName);
        // ensure that the fragment retrieved is an AccountFragment
        if (!(fragment instanceof AccountFragment)) {
            throw new RuntimeException("Expected AccountFragment, got " + fragment.getClass());
        } else {
            // update the fragment accordingly
            AccountFragment accountFragment = (AccountFragment) fragment;
            accountFragment.setAccount(this.userAccount);
        }
    }

    @Override
    public void setAccountGoToAccountFragment(DataServices.Account account) {
        Log.d(TAG, "setAccountGoToAccountFragment: ");
        this.userAccount = account;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, AccountFragment.newInstance(this.userAccount), ACCOUNT_FRAGMENT_TAG)
                .commit();
    }

    @Override
    public void goToUpdateAccountFragment() {
        Log.d(TAG, "goToUpdateAccountFragment: ");
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, UpdateAccountFragment.newInstance(this.userAccount), UPDATE_ACCOUNT_FRAGMENT_TAG)
                .addToBackStack(ACCOUNT_FRAGMENT_TAG)
                .commit();
    }
}
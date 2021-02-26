package com.example.inclass04;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Assignment: In Class 04
 * RegisterFragment.java
 * Devin Bonney and David Hotaran
 */

public class RegisterFragment extends Fragment {

    public RegisterFragment() {
        // Required empty public constructor
    }
    final String TAG = "Register Fragment";
    EditText nameEditTextRegister;
    EditText passwordEditTextRegister;
    EditText emailEditTextRegister;
    Button submitButtonRegister;
    Button cancelButtonRegister;
    DataServices.Account account;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Register Account");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        nameEditTextRegister = view.findViewById(R.id.nameEditTextRegister);
        passwordEditTextRegister = view.findViewById(R.id.passwordEditTextRegister);
        emailEditTextRegister= view.findViewById(R.id.emailEditTextRegister);
        submitButtonRegister = view.findViewById(R.id.submitButtonRegister);
        cancelButtonRegister = view.findViewById(R.id.cancelButtonRegister);

        // click listeners for buttons
        submitButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Submit onClick: ");
                try {
                    String email = emailEditTextRegister.getText().toString().trim();
                    String name = nameEditTextRegister.getText().toString().trim();
                    String password = passwordEditTextRegister.getText().toString().trim();
                    Log.d(TAG, "onClick: " + email);
                    Log.d(TAG, "onClick: " + name);
                    Log.d(TAG, "onClick: " + password);
                    // ensure no fields are empty
                    if (name.length() == 0) {
                        Toast.makeText(getActivity(), "Please enter a name.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (email.length() == 0) {
                        Toast.makeText(getActivity(), "Please enter an email.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (password.length() == 0) {
                        Toast.makeText(getActivity(), "Please enter a password.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // ensure valid email entered
                    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        Toast.makeText(getActivity(), "Please enter a valid email.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // ensure account can be created
                    account = DataServices.register(name, email, password);
                    if (account != null) {
                        Log.d(TAG, "Submit onClick: " + account);
                        mListener.setAccountGoToAccountFragment(account);
                    } else {
                        Log.d(TAG, "Submit onClick: Account could not be created");
                        Toast.makeText(getActivity(), "Could not create account with these credentials", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.d(TAG, "onClick: Exception thrown: " + e.getMessage());
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        cancelButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Cancel onClick: ");
                getFragmentManager().beginTransaction()
                        .replace(R.id.containerView, new LoginFragment())
                        .commit();
            }
        });
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof LoginFragment.IListener) {
            mListener = (LoginFragment.IListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement IListener");
        }
    }
    LoginFragment.IListener mListener;
}
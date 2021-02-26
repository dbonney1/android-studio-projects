package com.example.inclass05;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.inclass05.DataServices;

/**
 * Assignment: In Class 05
 * LoginFragment.java
 * Devin Bonney and David Hotaran
 */
public class LoginFragment extends Fragment {

    final String TAG = "Login Fragment";

    EditText emailEditTextLogin;
    EditText passwordEditTextLogin;
    DataServices.Account account;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Login");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        // initialize edit text views
        emailEditTextLogin = view.findViewById(R.id.emailEditTextLogin);
        passwordEditTextLogin = view.findViewById(R.id.passwordEditTextLogin);

        // set onClickListener for login button
        view.findViewById(R.id.loginButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: ");
                try {
                    String email = emailEditTextLogin.getText().toString();
                    String password = passwordEditTextLogin.getText().toString();
                    // check to see if either field is empty
                    if (email.length() == 0) {
                        Toast.makeText(getActivity(), "Please enter an email", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (password.length() == 0) {
                        Toast.makeText(getActivity(), "Please enter a password", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    // ensure valid email entered
                    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        Toast.makeText(getActivity(), "Please enter a valid email.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    DataServices.login(email, password, new DataServices.AuthResponse() {
                        @Override
                        public void onSuccess(String token) {
                            mListener.setTokenGoToAppCatFragment(token);
                            // DataServices.getAccount(token, accountResponse);
                        }

                        @Override
                        public void onFailure(DataServices.RequestException exception) {
                            Toast.makeText(getActivity(), exception.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                catch (Exception e) {
                    Log.d(TAG, "Login onClick: Exception: " + e.getMessage());
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        view.findViewById(R.id.createAccountButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Create Account onClick: ");
                // change to registration fragment
                getFragmentManager().beginTransaction()
                        .replace(R.id.containerView, new RegisterFragment())
                        .commit();
            }
        });
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof IListener) {
            mListener = (IListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement IListener");
        }
    }

    IListener mListener;

    public interface IListener {
        void deleteAccountGoToLogin();
        void setCategoryGoToAppListFragment(String token, String category);
        void setAppGoToDetailsFragment(DataServices.App app);
        void setTokenGoToAppCatFragment(String token);
    }
}
package com.example.inclass04;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Assignment: In Class 04
 * UpdateAccountFragment.java
 * Devin Bonney and David Hotaran
 */

public class UpdateAccountFragment extends Fragment {

    final String TAG = "Update Account Fragment";
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM_ACCOUNT = "ARG_PARAM_ACCOUNT";

    private DataServices.Account account;

    public UpdateAccountFragment() {
        // Required empty public constructor
    }

    public static UpdateAccountFragment newInstance(DataServices.Account account) {
        UpdateAccountFragment fragment = new UpdateAccountFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_ACCOUNT, account);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Update Account");
        if (getArguments() != null) {
            account = (DataServices.Account) getArguments().getSerializable(ARG_PARAM_ACCOUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update_account, container, false);
        TextView emailTextViewUpdateAccount = view.findViewById(R.id.emailTextViewUpdateAccount);
        EditText nameEditTextUpdateAccount = view.findViewById(R.id.nameEditTextUpdateAccount);
        EditText passwordEditTextUpdateAccount = view.findViewById(R.id.passwordEditTextUpdateAccount);

        emailTextViewUpdateAccount.setText(account.getEmail());
        nameEditTextUpdateAccount.setText(account.getName());
        passwordEditTextUpdateAccount.setText(account.getPassword());

        // add click listeners
        view.findViewById(R.id.submitButtonUpdateAccount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Submit onClick: ");
                try {
                    String newName = nameEditTextUpdateAccount.getText().toString();
                    String newPassword = passwordEditTextUpdateAccount.getText().toString();
                    // ensure fields aren't empty
                    if (newName.length() == 0) {
                        Toast.makeText(getActivity(), "Name cannot be empty", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (newPassword.length() == 0) {
                        Toast.makeText(getActivity(), "Password cannot be empty", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    // otherwise attempt to update account
                    DataServices.Account newAccount = DataServices.update(account, newName, newPassword);
                    if (newAccount != null) {
                        // update mListener's account and then update account profile
                        account = newAccount;
                        mListener.setAccountUpdateAccountProfile(account);
                        // pop previous activity from backstack
                        getFragmentManager().popBackStack();
                    }
                } catch (RuntimeException e) {
                    Log.d(TAG, "onClick: RuntimeException thrown: " + e.getMessage());
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Log.d(TAG, "onClick: Exception thrown: " + e.getMessage());
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        view.findViewById(R.id.cancelButtonUpdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Cancel onClick: ");
                // pop last visited fragment from the back stack
                getFragmentManager().popBackStack();
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
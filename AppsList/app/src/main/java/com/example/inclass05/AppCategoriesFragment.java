package com.example.inclass05;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Assignment: In Class 05
 * AppCategories.java
 * Devin Bonney and David Hotaran
 */
public class AppCategoriesFragment extends Fragment {

    final String TAG = "AppCateg Fragment";
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM_ACCOUNT_TOKEN = "ARG_PARAM_ACCOUNT_TOKEN";

    private String accountToken;


    public AppCategoriesFragment() {
        // Required empty public constructor
    }

    public static AppCategoriesFragment newInstance(String token) {
        AppCategoriesFragment fragment = new AppCategoriesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_ACCOUNT_TOKEN, token);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        getActivity().setTitle("App Categories");
        if (getArguments() != null) {
            this.accountToken = getArguments().getString(ARG_PARAM_ACCOUNT_TOKEN);
        }
    }

    TextView nameTextViewAppCat;
    ListView listView;
    ArrayAdapter<String> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_app_categories, container, false);
        nameTextViewAppCat = view.findViewById(R.id.nameTextViewAppCat);
        listView = view.findViewById(R.id.listView);
        // retrieve account via token
        DataServices.getAccount(accountToken, new DataServices.AccountResponse() {
            @Override
            public void onSuccess(DataServices.Account account) {
                Log.d(TAG, "AccountResponse onSuccess: ");
                nameTextViewAppCat.setText("Welcome " + account.getName());
            }

            @Override
            public void onFailure(DataServices.RequestException exception) {
                Log.d(TAG, "onFailure: RequestException: " + exception.getMessage());
                Toast.makeText(getActivity(), exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        DataServices.getAppCategories(accountToken, new DataServices.DataResponse<String>() {
            @Override
            public void onSuccess(ArrayList<String> data) {
                Log.d(TAG, "DataResponse onSuccess: ");
                // initialize adapter with ArrayList data
                adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, data);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String category = data.get(position);
                        mListener.setCategoryGoToAppListFragment(accountToken, category);
                    }
                });
            }

            @Override
            public void onFailure(DataServices.RequestException exception) {
                Log.d(TAG, "onFailure: RequestException thrown: " + exception.getMessage());
                Toast.makeText(getActivity(), exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        // set onClickListener for Log Out
        view.findViewById(R.id.logOutButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Log Out onClick: ");
                mListener.deleteAccountGoToLogin();
            }
        });
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        Log.d(TAG, "onAttach: ");
        super.onAttach(context);
        if (context instanceof LoginFragment.IListener) {
            mListener = (LoginFragment.IListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement IListener");
        }
    }

    LoginFragment.IListener mListener;
}
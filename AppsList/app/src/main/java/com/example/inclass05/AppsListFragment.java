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
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Assignment: In Class 05
 * AppsListFragment.java
 * Devin Bonney and David Hotaran
 */
public class AppsListFragment extends Fragment {

    final String TAG = "AppsListFragment";
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM_ACCOUNT_TOKEN= "ARG_PARAM_ACCOUNT_TOKEN";
    private static final String ARG_PARAM_CATEGORY= "ARG_PARAM_CATEGORY";

    private String accountToken;
    private String category;

    public AppsListFragment() {
        // Required empty public constructor
    }

    public static AppsListFragment newInstance(String token, String category) {
        AppsListFragment fragment = new AppsListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_ACCOUNT_TOKEN, token);
        args.putString(ARG_PARAM_CATEGORY, category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.accountToken = getArguments().getString(ARG_PARAM_ACCOUNT_TOKEN);
            this.category = getArguments().getString(ARG_PARAM_CATEGORY);
            getActivity().setTitle(this.category);
        }
    }

    ListView listViewAppList;
    AppAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_apps_list, container, false);
        listViewAppList = view.findViewById(R.id.listViewAppList);
        // obtain apps by provided category
        DataServices.getAppsByCategory(accountToken, category, new DataServices.DataResponse<DataServices.App>() {

            @Override
            public void onSuccess(ArrayList<DataServices.App> data) {
                Log.d(TAG, "DataResponse onSuccess: ");
                // initialize adapter with ArrayList data
                adapter = new AppAdapter(getActivity(), R.layout.app_row_item, data);
                listViewAppList.setAdapter(adapter);

                listViewAppList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.d(TAG, "AppListResponse onItemClick: item at position " + position);
                        // select app at position
                        DataServices.App app = data.get(position);
                        // send app to MainActivity and go to AppDetailsFragment
                        mListener.setAppGoToDetailsFragment(app);
                    }
                });
            }

            @Override
            public void onFailure(DataServices.RequestException exception) {
                Log.d(TAG, "appListResponse onFailure: RequestException thrown: " + exception.getMessage());
                Toast.makeText(getActivity(), exception.getMessage(), Toast.LENGTH_SHORT).show();
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
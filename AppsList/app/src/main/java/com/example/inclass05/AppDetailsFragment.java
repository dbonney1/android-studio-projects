package com.example.inclass05;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Assignment: In Class 05
 * AppDetailsFragment.java
 * Devin Bonney and David Hotaran
 */
public class AppDetailsFragment extends Fragment {

    final String TAG = "AppDetailsFragment";
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM_APP= "ARG_PARAM_APP";

    private DataServices.App app;

    public AppDetailsFragment() {
        // Required empty public constructor
    }

    public static AppDetailsFragment newInstance(DataServices.App app) {
        AppDetailsFragment fragment = new AppDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_APP, app);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            app = (DataServices.App) getArguments().getSerializable(ARG_PARAM_APP);
        }
    }

    TextView textViewNameAppDetails;
    TextView textViewArtistNameAppDetails;
    TextView textViewReleaseDateAppDetails;
    ListView listViewGenres;
    ArrayAdapter<String> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_app_details, container, false);
        textViewNameAppDetails = view.findViewById(R.id.textViewNameAppDetails);
        textViewArtistNameAppDetails = view.findViewById(R.id.textViewArtistNameAppDetails);
        textViewReleaseDateAppDetails = view.findViewById(R.id.textViewReleaseDateAppDetails);

        // populate TextViews with app information
        textViewNameAppDetails.setText(app.name);
        textViewArtistNameAppDetails.setText(app.artistName);
        textViewReleaseDateAppDetails.setText(app.releaseDate);
        listViewGenres = view.findViewById(R.id.listViewGenres);
        Log.d(TAG, "onCreateView: genres length: " + app.genres.size());
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, app.genres);
        listViewGenres.setAdapter(adapter);

        return view;
    }
}
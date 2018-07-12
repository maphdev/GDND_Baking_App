package com.example.manon.bakingapp.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.manon.bakingapp.R;

public class RecipeDetailsFragment extends Fragment {


    public RecipeDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_layout_master_list, container, false);
        TextView txtView = (TextView) rootView.findViewById(R.id.master_list_txtView);
        txtView.setText("yolo");
        return rootView;
    }

}

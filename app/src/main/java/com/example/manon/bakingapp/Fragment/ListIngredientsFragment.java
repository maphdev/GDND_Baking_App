package com.example.manon.bakingapp.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.manon.bakingapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListIngredientsFragment extends Fragment {


    public ListIngredientsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_ingredients, container, false);
    }

}

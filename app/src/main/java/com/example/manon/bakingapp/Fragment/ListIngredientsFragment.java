package com.example.manon.bakingapp.Fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.manon.bakingapp.Adapter.IngredientsAdapter;
import com.example.manon.bakingapp.Adapter.RecipeDetailsAdapter;
import com.example.manon.bakingapp.Models.Recipe;
import com.example.manon.bakingapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListIngredientsFragment extends Fragment {

    @BindView(R.id.list_ingredients_recyclerview) RecyclerView recyclerView;

    // Required empty public constructor
    public ListIngredientsFragment() {}

    // create a fragment with a recipe
    public static ListIngredientsFragment newInstance(Recipe recipe){
        ListIngredientsFragment listIngredientsFragment = new ListIngredientsFragment();
        Bundle args = new Bundle();
        args.putParcelable("RECIPE", recipe);
        listIngredientsFragment.setArguments(args);
        return listIngredientsFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_list_ingredients, container, false);

        ButterKnife.bind(this, rootView);

        Recipe recipe = getArguments().getParcelable("RECIPE");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);

        IngredientsAdapter ingredientsAdapter = new IngredientsAdapter(recipe);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(ingredientsAdapter);
        recyclerView.setHasFixedSize(true);

        return rootView;
    }

}

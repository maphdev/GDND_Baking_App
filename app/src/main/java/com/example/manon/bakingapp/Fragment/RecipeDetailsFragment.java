package com.example.manon.bakingapp.Fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.manon.bakingapp.Activity.ListIngredientsActivity;
import com.example.manon.bakingapp.Activity.StepActivity;
import com.example.manon.bakingapp.Adapter.RecipeDetailsAdapter;
import com.example.manon.bakingapp.Models.Recipe;
import com.example.manon.bakingapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailsFragment extends Fragment  implements RecipeDetailsAdapter.ListItemClickListener {

    @BindView(R.id.master_list_recycler) RecyclerView recyclerView;

    public RecipeDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_layout_master_list, container, false);

        ButterKnife.bind(this, rootView);

        Recipe recipe = getActivity().getIntent().getParcelableExtra(getString(R.string.PARCELABLE_RECIPE));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);

        RecipeDetailsAdapter recipeDetailsAdapter = new RecipeDetailsAdapter(recipe, this);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recipeDetailsAdapter);
        recyclerView.setHasFixedSize(true);

        return rootView;
    }

    @Override
    public void onListItemClicked(int clickedItemIndex, Recipe recipe) {
        Context context = getContext();
        if(clickedItemIndex == 0){
            Class destinationClass = ListIngredientsActivity.class;
            Intent startListIngredientsActivity = new Intent(context, destinationClass);
            startListIngredientsActivity.putExtra(getString(R.string.PARCELABLE_RECIPE), recipe);
            startActivity(startListIngredientsActivity);
        } else {
            Class destinationClass = StepActivity.class;
            Intent startStepActivity = new Intent(context, destinationClass);
            startStepActivity.putExtra(getString(R.string.PARCELABLE_STEP), recipe.getSteps().get(clickedItemIndex - 1));
            startStepActivity.putExtra(getString(R.string.PARCELABLE_RECIPE), recipe);
            startActivity(startStepActivity);
        }
    }
}

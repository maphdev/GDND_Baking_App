package com.example.manon.bakingapp.Fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.manon.bakingapp.Adapter.RecipeDetailsAdapter;
import com.example.manon.bakingapp.Models.Recipe;
import com.example.manon.bakingapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailsFragment extends Fragment  implements RecipeDetailsAdapter.ListItemClickListener {

    @BindView(R.id.master_list_recycler) RecyclerView recyclerView;

    // Required empty public constructor
    public RecipeDetailsFragment() {}

    // create a fragment with a recipe
    public static RecipeDetailsFragment newInstance(Recipe recipe){
        RecipeDetailsFragment recipeDetailsFragment = new RecipeDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable("RECIPE", recipe);
        recipeDetailsFragment.setArguments(args);
        return recipeDetailsFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_layout_master_list, container, false);

        ButterKnife.bind(this, rootView);

        Recipe recipe = getArguments().getParcelable("RECIPE");
        getActivity().setTitle(recipe.getName());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);

        RecipeDetailsAdapter recipeDetailsAdapter = new RecipeDetailsAdapter(recipe, this, getContext());

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recipeDetailsAdapter);
        recyclerView.setHasFixedSize(true);

        return rootView;
    }

    @Override
    public void onListItemClicked(int clickedItemIndex, Recipe recipe) {
        callbacks.onAdapterClickListener(clickedItemIndex, recipe);
    }

    // communication with the activity !
    OnAdapterClickListener callbacks;

    public interface OnAdapterClickListener{
        void onAdapterClickListener(int clickedItemIndex, Recipe recipe);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            callbacks = (OnAdapterClickListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement OnAdapterClickListener");
        }
    }
}

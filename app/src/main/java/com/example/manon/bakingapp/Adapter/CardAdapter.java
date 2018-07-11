package com.example.manon.bakingapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.manon.bakingapp.Models.Recipe;
import com.example.manon.bakingapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder>{

    private List<Recipe> listRecipes;
    private Context context;
    final private ListItemClickListener listItemClickListener;

    public CardAdapter(List<Recipe> listRecipes, Context context, ListItemClickListener listItemClickListener){
        this.listRecipes = listRecipes;
        this.context = context;
        this.listItemClickListener = listItemClickListener;
    }

    public List<Recipe> getListRecipes() {
        return listRecipes;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.card_item_adapter, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        String urlImage = listRecipes.get(position).getImage();
        if (urlImage.isEmpty() || urlImage == null){
            holder.cardImgView.setImageResource(R.color.colorAccent);
        } else {
            Picasso.with(this.context).load(urlImage).placeholder(R.color.colorAccent).error(R.color.colorAccent).into(holder.cardImgView);
        }
        holder.cardTxtView.setText(listRecipes.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return listRecipes.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.card_imgView) ImageView cardImgView;
        @BindView(R.id.card_txtView) TextView cardTxtView;

        public CardViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            listItemClickListener.onListItemClicked(clickedPosition);
        }
    }

    public interface ListItemClickListener{
        void onListItemClicked(int clickedPosition);
    }
}

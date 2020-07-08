package com.lofod.bookmark;

import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

    ArrayList<Card> data;

    CardAdapter(ArrayList<Card> data) {
        this.data = data;
    }

    public void setData(ArrayList<Card> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void addCard(Card card) {
        data.add(0, card);
        notifyItemInserted(0);
    }

    public void removeCard(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CardViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card,
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        View itemView = holder.itemView;

        TextView bookName = itemView.findViewById(R.id.bookName);
        bookName.setText(data.get(position).bookName);

        TextView author = itemView.findViewById(R.id.authorName);
        author.setText(data.get(position).author);

        TextView seriesAndDate = itemView.findViewById(R.id.seriesDateName);
        seriesAndDate.setText(data.get(position).seriesAndDate);

        TextView description = itemView.findViewById(R.id.bookDescription);
        description.setText(data.get(position).description);

        ImageView bookImg = itemView.findViewById(R.id.bookImg);
        if(data.get(position).imageUri.toString().equals("default")) {
            bookImg.setImageBitmap(BitmapFactory.decodeResource(itemView.getResources(), R.drawable.bookImg));
        } else {
            bookImg.setImageURI(data.get(position).imageUri);
        }

        RatingBar ratingBar = itemView.findViewById(R.id.ratingBar);
        ratingBar.setRating(data.get(position).rating);

        ChipGroup chipGroup = itemView.findViewById(R.id.chipGroup);

        for (int i = 0; i < data.get(position).tags.size(); i++) {
            Chip chip = new Chip(itemView.getContext());
            chip.setText(data.get(position).tags.get(i));
            chip.setCheckable(false);
            chip.setClickable(true);

            chipGroup.addView(chip);
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        public CardViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }
}

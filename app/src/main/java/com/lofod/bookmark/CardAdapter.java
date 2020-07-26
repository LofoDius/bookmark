package com.lofod.bookmark;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.io.File;
import java.util.ArrayList;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

    ArrayList<Card> data;
    File fileDir;

    CardAdapter(ArrayList<Card> data, File fileDir) {
        this.data = data;
        this.fileDir = fileDir;
    }

    public void setFileDir(File fileDir) {
        this.fileDir = fileDir;
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
    public void onBindViewHolder(@NonNull CardViewHolder holder, final int position) {
        final View itemView = holder.itemView;

        TextView bookName = itemView.findViewById(R.id.bookName);
        bookName.setText(data.get(position).bookName);

        TextView author = itemView.findViewById(R.id.authorName);
        author.setText(data.get(position).author);

        TextView seriesAndDate = itemView.findViewById(R.id.seriesDateName);
        seriesAndDate.setText(data.get(position).seriesAndDate);

        TextView description = itemView.findViewById(R.id.bookDescription);
        description.setText(data.get(position).description);

        ImageView bookImg = itemView.findViewById(R.id.bookImg);
        if (data.get(position).bookImg.equals("default")) {
            bookImg.setImageBitmap(BitmapFactory.decodeResource(itemView.getResources(), R.drawable.bookImg));
        } else {
            File file = new File(fileDir, data.get(position).bookImg + ".png");
            Uri imgUri = Uri.fromFile(file);
            bookImg.setImageURI(imgUri);
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

        ImageButton popupMenuBtn = itemView.findViewById(R.id.popUp);
        popupMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPopupMenuClick(itemView);
            }
        });

    }

    public void onPopupMenuClick(View view) {
        PopupMenu popup = new PopupMenu(view.getContext(), view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.popup_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.editCard:

                        return true;
                    case R.id.deleteCard:

                        return true;
                    default:
                        return false;
                }
            }
        });
        popup.show();

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

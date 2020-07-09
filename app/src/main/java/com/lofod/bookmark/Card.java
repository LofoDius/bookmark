package com.lofod.bookmark;

import android.net.Uri;

import java.util.ArrayList;

public class Card {
    String bookName;
    String author;
    String seriesAndDate;
    String description;
    ArrayList<String> tags;
    int rating;
    String bookImg;

    public Card(String bookName, String author, String seriesAndDate, String description, ArrayList<String> tags, int rating, String bookImg) {
        this.bookName = bookName;
        this.author = author;
        this.seriesAndDate = seriesAndDate;
        this.description = description;
        this.tags = tags;
        this.rating = rating;
        this.bookImg = bookImg;
    }
}

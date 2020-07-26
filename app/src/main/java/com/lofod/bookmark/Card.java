package com.lofod.bookmark;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Card implements Parcelable {
    String bookName;
    String author;
    String seriesAndDate;
    String description;
    ArrayList<String> tags;
    int rating;
    String bookImg; //  image name without path to it

    public Card(String bookName, String author, String seriesAndDate, String description, ArrayList<String> tags, int rating, String bookImg) {
        this.bookName = bookName;
        this.author = author;
        this.seriesAndDate = seriesAndDate;
        this.description = description;
        this.tags = tags;
        this.rating = rating;
        this.bookImg = bookImg;
    }

    public Card(){}

    public Card(Parcel in) {
        bookImg = in.readString();
        author = in.readString();
        seriesAndDate = in.readString();
        description = in.readString();
        tags = in.createStringArrayList();
        rating = in.readInt();
        bookImg = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(bookName);
        parcel.writeString(author);
        parcel.writeString(seriesAndDate);
        parcel.writeString(description);
        parcel.writeStringList(tags);
        parcel.writeInt(rating);
        parcel.writeString(bookImg);
    }

    public static final Creator<Card> CREATOR = new Creator<Card>() {
        @Override
        public Card createFromParcel(Parcel parcel) {
            return new Card(parcel);
        }

        @Override
        public Card[] newArray(int i) {
            return new Card[i];
        }
    };
}

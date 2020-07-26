package com.lofod.bookmark;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

public class AddBookActivity extends AppCompatActivity {

    boolean isImageUploaded = false;
    Card newCard;
    public static final int resultCode = 1337;
    final int imagePickCode = 3228;
    ImageView bookImage;
    Uri bookImageUri;
    EditText authorFirstName;
    EditText authorSecondName;
    EditText bookTitle;
    EditText seriesName;
    EditText editTextNumber;
    RatingBar ratingBar;
    CheckBox isArchived;
    EditText review;
    EditText readDate;
    Toast toast;
    EditText editTags;
    Bitmap bookImageBitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        setTitle(getString(R.string.add_book_title));

        bookImage = findViewById(R.id.uploadBookImg);
        authorFirstName = findViewById(R.id.authorFirstName);
        authorSecondName = findViewById(R.id.authorSecondName);
        bookTitle = findViewById(R.id.bookTitle);
        seriesName = findViewById(R.id.seriesName);
        editTextNumber = findViewById(R.id.editTextNumber);
        ratingBar = findViewById(R.id.bookRating);
        isArchived = findViewById(R.id.isArhived);
        review = findViewById(R.id.review);
        readDate = findViewById(R.id.readDate);
        readDate.setText(LocalDate.now().toString());
        editTags = findViewById(R.id.editTags);

        newCard = new Card();
    }

    public void onClick(View view) {
        if (findViewById(R.id.uploadBookImg).equals(view)) {
            isImageUploaded = true;
            newCard.bookImg = UUID.randomUUID().toString();

            Intent imagePick = new Intent(Intent.ACTION_PICK);
            imagePick.setType("image/*");
            startActivityForResult(imagePick, imagePickCode);
            if (bookImageBitmap == null) {
                isImageUploaded = false;
                newCard.bookImg = "default";
            } else {
                String filename = getFilesDir().toString() + "/" + newCard.bookImg + ".png";
                try (FileOutputStream out = new FileOutputStream(filename)) {
                    bookImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            // check if required fields are filled
            if (authorFirstName.getText().toString().equals("")) {
                toast = Toast.makeText(this, R.string.authorNameError, Toast.LENGTH_LONG);
                toast.show();
                return;
            } else if (bookTitle.getText().toString().equals("")) {
                toast = Toast.makeText(this, R.string.bookTitleError, Toast.LENGTH_LONG);
                toast.show();
                return;
            }

            newCard.author = authorFirstName.getText().toString() + " "
                    + authorSecondName.getText().toString();
            newCard.bookName = bookTitle.getText().toString();
            newCard.description = review.getText().toString();
            newCard.rating = ratingBar.getRating();
            newCard.seriesAndDate = seriesName.getText().toString() + " №"
                    + editTextNumber.getText().toString() + " | " + readDate.getText().toString();
            if (!editTags.getText().toString().equals("")) {
                String[] tags = editTags.getText().toString().replaceAll(" ", "")
                        .split(",");

                Collections.addAll(newCard.tags, tags);
            }

            Intent result = new Intent();
            result.putExtra("newCard", newCard);
            setResult(AddBookActivity.resultCode, result);
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == imagePickCode) {
            try {
                assert data != null;
                Uri uri = data.getData();
                bookImageUri = uri;
                assert uri != null;
                InputStream inputStream = getContentResolver().openInputStream(uri);
                bookImageBitmap = BitmapFactory.decodeStream(inputStream);
                bookImage.setImageBitmap(bookImageBitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


}
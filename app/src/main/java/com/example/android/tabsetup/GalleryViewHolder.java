package com.example.android.tabsetup;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.io.File;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class GalleryViewHolder extends RecyclerView.ViewHolder {
    ImageView galleryImage;
    File item;
    GalleryListener listener;
    GalleryActivity galleryActivity;

    // Allows the passing of methods from the base activity for better communication between
    // the classes and also lightens the load on the ViewHolder too.
    public interface GalleryListener{

    }

    public GalleryViewHolder(View itemView, final GalleryListener listener, final GalleryActivity galleryActivity) {
        super(itemView);
        this.listener = listener;
        this.galleryActivity = galleryActivity;
        galleryImage = itemView.findViewById(R.id.galleryImage);
    }

    public void setItem(File item) {
        this.item = item;
        Bitmap myBitmap = BitmapFactory.decodeFile(item.getAbsolutePath());
        galleryImage.setImageBitmap(myBitmap);
    }
}
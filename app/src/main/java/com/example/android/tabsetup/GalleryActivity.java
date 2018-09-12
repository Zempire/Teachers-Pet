package com.example.android.tabsetup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.Window;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GalleryActivity extends AppCompatActivity implements GalleryViewHolder.GalleryListener {

    RecyclerView recyclerView;
    GalleryAdapter adapter;
    androidx.appcompat.widget.Toolbar toolbar;
    List<File> images = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        toolbar = findViewById(R.id.mainToolbar);
        toolbar.setTitle("Image Gallery");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.imageRecycler);
        File path = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File[] files = path.listFiles();
        for(File image : files) {
            System.out.println(image.toString());
            images.add(image);
        }
        int numColumns = 4;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numColumns));
        adapter = new GalleryAdapter(getLayoutInflater(), this, this);
        recyclerView.setAdapter(adapter);
        adapter.updateItems(images);
    }

    @Override
    public void expandImage(Bitmap myBitmap) {
        GalleryViewer newImage = new GalleryViewer(this, myBitmap);
        Window window = newImage.getWindow();
        newImage.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        newImage.setCancelable(false);
        newImage.show();
        window.setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
    }
}

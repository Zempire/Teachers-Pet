package com.example.android.tabsetup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toolbar;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

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
}

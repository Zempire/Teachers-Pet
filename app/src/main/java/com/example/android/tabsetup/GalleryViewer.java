package com.example.android.tabsetup;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.room.Room;

public class GalleryViewer extends Dialog {

    ImageView expandedImage;
    public Activity a;
    public Bitmap myBitmap;

    public GalleryViewer(Activity c, Bitmap myBitmap) {
        super(c);
        this.a = c;
        this.myBitmap = myBitmap;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_image_viewer);

        expandedImage = findViewById(R.id.expandedImage);
        expandedImage.setImageBitmap(myBitmap);

        expandedImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }

}

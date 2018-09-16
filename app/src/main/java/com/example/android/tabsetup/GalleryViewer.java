package com.example.android.tabsetup;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.core.content.FileProvider;
import androidx.room.Room;

public class GalleryViewer extends Dialog {

    ImageView expandedImage;
    public Activity a;
    GalleryActivity galleryActivity;
    public Bitmap myBitmap;
    Button deleteImgBtn, updateImgBtn;
    File file;
    AppDatabase db;

    public GalleryViewer(Activity c, GalleryActivity galleryActivity, File file, Bitmap myBitmap) {
        super(c);
        this.a = c;
        this.galleryActivity = galleryActivity;
        this.myBitmap = myBitmap;
        this.file = file;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_image_viewer);
        expandedImage = findViewById(R.id.expandedImage);
        deleteImgBtn = findViewById(R.id.deleteImgBtn);
        updateImgBtn = findViewById(R.id.updateImgBtn);
        expandedImage.setImageBitmap(myBitmap);

        db = Room.databaseBuilder(a, AppDatabase.class,
                "production").allowMainThreadQueries().build();

        expandedImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        deleteImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                file.delete();
                galleryActivity.refreshFiles();
                galleryActivity.adapter.updateItems(galleryActivity.images);
                dismiss();
            }
        });
        updateImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Student student = db.StudentDao().getStudent(Integer.parseInt(galleryActivity.currentStudentID));
                student.setProfilePicture(file.getAbsolutePath());
                db.StudentDao().updateStudent(student);
                Toast.makeText(a, "Student image updated.", Toast.LENGTH_SHORT).show();
                dismiss();
                Intent intent = new Intent(a, StudentUpdater.class);
                intent.putExtra("STUDENT_ID", galleryActivity.currentStudentID);
                a.startActivity(intent);
                a.finish();
            }
        });
    }
}

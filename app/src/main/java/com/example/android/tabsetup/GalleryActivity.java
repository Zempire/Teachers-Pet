package com.example.android.tabsetup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Window;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GalleryActivity extends AppCompatActivity implements GalleryViewHolder.GalleryListener {

    private static final int REQUEST_TAKE_PHOTO = 1;
    String mCurrentPhotoPath;
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
        toolbar.setTitleTextColor(Color.BLACK);
        setSupportActionBar(toolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(GalleryActivity.this,
                    R.color.altBackground));
        }
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

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;

            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {

                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

//    Bitmap createProfilePic() {
//        String imageFileName = "PROFILE_" +  .getText().toString();
//        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        File image = new File(storageDir + "/" + imageFileName + ".jpg");
//        Bitmap finalOut = null;
//        if (image.exists()) {
//            //Open output stream.
//            OutputStream outStream = null;
//
//            //Create Bitmap from file.
//            Bitmap myBitmap = BitmapFactory.decodeFile(image.getAbsolutePath());
//            image.delete();
//
//            //Rotate the Bitmap and set its dimensions to whichever dimension is smallest.
//            Matrix matrix = new Matrix();
//            matrix.postRotate(90);
//
//            //https://stackoverflow.com/questions/6908604/android-crop-center-of-bitmap
//            // Crops image depending on height vs width.
//            if (myBitmap.getWidth() >= myBitmap.getHeight()){
//                finalOut = Bitmap.createBitmap(myBitmap,
//                        myBitmap.getWidth()/2 - myBitmap.getHeight()/2, 0,
//                        myBitmap.getHeight(), myBitmap.getHeight(), matrix, true);
//
//            } else {
//                finalOut = Bitmap.createBitmap(myBitmap,
//                        0, myBitmap.getHeight()/2 - myBitmap.getWidth()/2,
//                        myBitmap.getWidth(), myBitmap.getWidth(), matrix, true);
//            }
//            try {
//                outStream = new FileOutputStream(image);
//                finalOut.compress(Bitmap.CompressFormat.JPEG, 60, outStream);
//                outStream.flush();
//                outStream.close();
//            } catch(Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return finalOut;
//    }
}

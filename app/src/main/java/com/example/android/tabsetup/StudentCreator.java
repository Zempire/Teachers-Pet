package com.example.android.tabsetup;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.room.Room;

import static android.os.Environment.getExternalStoragePublicDirectory;

public class StudentCreator extends AppCompatActivity {

    public static final int PICK_IMAGE = 1; //Make sure only one image is chosen.
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    final String[] STATES = new String[]{
            "State", "NSW", "VIC", "QLD", "NT", "WA", "SA", "TAS", "ACT"};

    final String[] COURSES = new String[] {"Bachelor of Architecture",
            "Bachelor of Biomedical Science",
            "Bachelor of Business Administration",
            "Bachelor of Clinical Science",
            "Bachelor of Commerce",
            "Bachelor of Computer Applications",
            "Bachelor of Community Health",
            "Bachelor of Computer Information Systems",
            "Bachelor of Science in Construction Technology",
            "Bachelor of Criminal Justice",
            "Bachelor of Divinity",
            "Bachelor of Economics",
            "Bachelor of Education",
            "Bachelor of Engineering",
            "Bachelor of Fine Arts",
            "Bachelor of Letters",
            "Bachelor of Information Systems",
            "Bachelor of Management",
            "Bachelor of Music",
            "Bachelor of Pharmacy",
            "Bachelor of Philosophy",
            "Bachelor of Social Work",
            "Bachelor of Technology",
            "Bachelor of Accountancy",
            "Bachelor of Arts in American Studies",
            "Bachelor of Arts in American Indian Studies",
            "Bachelor of Arts in Applied Psychology",
            "Bachelor of Arts in Biology",
            "Bachelor of Arts in Anthropology",
            "Bachelor of Arts in Child Advocacy",
            "Bachelor of Arts in Clinical Psychology",
            "Bachelor of Arts in Forensic Psychology",
            "Bachelor of Arts in Organizational Psychology",
            "Bachelor of Science in Aerospace Engineering",
            "Bachelor of Science in Actuarial",
            "Bachelor of Science in Agriculture",
            "Bachelor of Science in Architecture",
            "Bachelor of Science in Architectural Engineering",
            "Bachelor of Science in Athletic Training",
            "Bachelor of Science in Biology",
            "Bachelor of Science in Biomedical Engineering",
            "Bachelor of Science in Bible",
            "Bachelor of Science in Business Administration",
            "Bachelor of Science in Business and Technology",
            "Bachelor of Science in Chemical Engineering",
            "Bachelor of Science in Chemistry",
            "Bachelor of Science in Civil Engineering",
            "Bachelor of Science in Clinical Laboratory Science",
            "Bachelor of Science in Cognitive Science",
            "Bachelor of Science in Computer Engineering",
            "Bachelor of Science in Computer Science",
            "Bachelor of Science in Construction Engineering",
            "Bachelor of Science in Construction Management",
            "Bachelor of Science in Criminal Justice",
            "Bachelor of Science in Criminology",
            "Bachelor of Science in Diagnostic Radiography",
            "Bachelor of Science in Education",
            "Bachelor of Science in Electrical Engineering",
            "Bachelor of Science in Engineering Physics",
            "Bachelor of Science in Engineering Science",
            "Bachelor of Science in Engineering Technology",
            "Bachelor of Science in English Literature",
            "Bachelor of Science in Environmental Engineering",
            "Bachelor of Science in Environmental Science",
            "Bachelor of Science in Environmental Studies",
            "Bachelor of Science in Food Science",
            "Bachelor of Science in Foreign Service",
            "Bachelor of Science in Forensic Science",
            "Bachelor of Science in Forestry",
            "Bachelor of Science in History",
            "Bachelor of Science in Hospitality Management",
            "Bachelor of Science in Human Resources Management",
            "Bachelor of Science in Industrial Engineering",
            "Bachelor of Science in Information Technology",
            "Bachelor of Science in Information Systems",
            "Bachelor of Science in Integrated Science",
            "Bachelor of Science in International Relations",
            "Bachelor of Science in Journalism",
            "Bachelor of Science in Legal Management",
            "Bachelor of Science in Management",
            "Bachelor of Science in Manufacturing Engineering",
            "Bachelor of Science in Marketing",
            "Bachelor of Science in Mathematics",
            "Bachelor of Science in Mechanical Engineering",
            "Bachelor of Science in Medical Technology",
            "Bachelor of Science in Metallurgical Engineering",
            "Bachelor of Science in Meteorology",
            "Bachelor of Science in Microbiology",
            "Bachelor of Science in Mining Engineering",
            "Bachelor of Science in Molecular Biology",
            "Bachelor of Science in Neuroscience",
            "Bachelor of Science in Nursing",
            "Bachelor of Science in Nutrition science",
            "Bachelor of Science in Software Engineering",
            "Bachelor of Science in Petroleum Engineering",
            "Bachelor of Science in Podiatry",
            "Bachelor of Science in Pharmacology",
            "Bachelor of Science in Pharmacy",
            "Bachelor of Science in Physical Therapy",
            "Bachelor of Science in Physics",
            "Bachelor of Science in Plant Science",
            "Bachelor of Science in Politics",
            "Bachelor of Science in Psychology",
            "Bachelor of Science in Public Safety",
            "Bachelor of Science in Physiology",
            "Bachelor of Science in Quantity Surveying Engineering",
            "Bachelor of Science in Radiologic Technology",
            "Bachelor of Science in Real-Time Interactive Simulation",
            "Bachelor of Science in Religion",
            "Bachelor of Science in Respiratory Therapy",
            "Bachelor of Science in Risk Management and Insurance",
            "Bachelor of Science in Science Education",
            "Bachelor of Science in Sports Management",
            "Bachelor of Science in Systems Engineering",
            "Bachelor of Music in Jazz Studies",
            "Bachelor of Music in Composition",
            "Bachelor of Music in Performance",
            "Bachelor of Music in Theory",
            "Bachelor of Music in Music Education",
            "Bachelor of Science in Veterinary Technology"};

    EditText firstName, lastName, studentID, studentDOB, stud_street, stud_city, stud_post;
    TextView address;
    ImageView addImage;
    AutoCompleteTextView stud_state, stud_course;
    LinearLayout addressExpanded;
    Button saveStudent, cancelStudent, selectDateBtn;
    DatePickerDialog datePickerDialog;
    RadioGroup gender;

    String mCurrentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_student);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        studentID = findViewById(R.id.studentID);
        studentDOB = findViewById(R.id.studentDOB);
        stud_street = findViewById(R.id.stud_street);
        stud_city = findViewById(R.id.stud_city);
        stud_post = findViewById(R.id.stud_post);
        gender = findViewById(R.id.genderRadioGroup);
        address = findViewById(R.id.addressHeader);
        addressExpanded = findViewById(R.id.addressLayout);
        stud_state = findViewById(R.id.stud_state);
        stud_course = findViewById(R.id.course);
        saveStudent = findViewById(R.id.save_btn);
        cancelStudent = findViewById(R.id.cancel_btn);
        selectDateBtn = findViewById(R.id.selectDateBtn);
        addImage = findViewById(R.id.addImage);
        addImage.setVisibility(View.GONE);


        /*
        Allow user to open images and choose one.
         */
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
                }
        });

        studentID.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (studentID.getText().toString().length() >= 5) {
                    addImage.setVisibility(View.VISIBLE);
                } else {
                    addImage.setVisibility(View.GONE);
                }
            }
        });

        /*
        Button for selecting the date. Will get the current date and make that the default
        setting.
         */
        selectDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(StudentCreator.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                studentDOB.setText(day + "/" + (month + 1) + "/" + year);
                            }
                        }, year, month, day );
                datePickerDialog.show();
            }
        });

        /*
        Setup the the database for access;
         */
        final AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class,
                "production").allowMainThreadQueries().build();


        /*
        Save student into the database so long as their student ID is unique.
         */
        saveStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ID = studentID.getText().toString();
                if (ID.matches("")) {
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(StudentCreator.this);
                    builder.setMessage("Please enter a Student Number.")
                            .setNegativeButton("OK", null);

                    android.app.AlertDialog alert = builder.create();
                    alert.show();
                } else {
                    int id = Integer.parseInt(ID);
                    String wholeAddress = stud_street.getText().toString() + ", " +
                            stud_city.getText().toString() + ", " +
                            stud_state.getText().toString() + ", " +
                            stud_post.getText().toString();
                    String genderChoice = "";
                    if (gender.getCheckedRadioButtonId() != -1) {
                        genderChoice = ((RadioButton) findViewById(gender.getCheckedRadioButtonId()))
                                .getText().toString();
                    }

                    Student newStudent = new Student(id, firstName.getText().toString(),
                            lastName.getText().toString(),
                            wholeAddress,
                            studentDOB.getText().toString(), genderChoice,
                            stud_course.getText().toString());
                    db.StudentDao().insertAllStudent(newStudent);
                    startActivity(new Intent(StudentCreator.this, MainActivity.class));
                    finish();
                }
            }
        });

        /*
        Scrap what is being done and return to previous activity.
         */
        cancelStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        /*
        Give a list of strings to TextViews to allow auto complete option for users.
        */
        ArrayAdapter state_adapter = new ArrayAdapter(this,
                android.R.layout.simple_dropdown_item_1line, STATES);
        stud_state.setAdapter(state_adapter);

        ArrayAdapter course_adapter = new ArrayAdapter(this,
                android.R.layout.simple_dropdown_item_1line, COURSES);
        stud_course.setAdapter(course_adapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            setPic();
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String imageFileName = "PROFILE_" + studentID.getText().toString();
        System.out.println("NAMING NEW IMAGE...");
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = new File(storageDir + "/" + imageFileName + ".jpg");

//                File.createTempFile(
//                imageFileName,  /* prefix */
//                ".jpg",         /* suffix */
//                storageDir      /* directory */
//                  );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    static final int REQUEST_TAKE_PHOTO = 1;

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
                System.out.println("WE ARE HERE!");
//                Bitmap myBitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());

                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private void setPic() {
        // Get the dimensions of the View
        int targetW = addImage.getWidth();
        int targetH = addImage.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        addImage.setImageBitmap(bitmap);
    }
}
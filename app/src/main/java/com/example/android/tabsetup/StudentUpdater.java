package com.example.android.tabsetup;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

public class StudentUpdater extends AppCompatActivity {
    EditText firstName, lastName, studentID, studentDOB, stud_street, stud_city, stud_post;
    AutoCompleteTextView stud_state, stud_course;
    LinearLayout addressExpanded;
    Button saveStudent, cancelStudent;
    DatePickerDialog datePickerDialog;
    ImageView addImage;
    RadioGroup gender;
    Student currentStudent;
    String currentStudentID;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_student);

        Bundle extras = getIntent().getExtras();
        if (extras != null)
            currentStudentID = extras.getString("STUDENT_ID");

        final AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class,
                "production").allowMainThreadQueries().build();

        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        studentID = findViewById(R.id.studentID);
        studentDOB = findViewById(R.id.studentDOB);
        stud_street = findViewById(R.id.stud_street);
        stud_city = findViewById(R.id.stud_city);
        stud_post = findViewById(R.id.stud_post);
        gender = findViewById(R.id.genderRadioGroup);
        addressExpanded = findViewById(R.id.addressLayout);
        stud_state = findViewById(R.id.stud_state);
        stud_course = findViewById(R.id.course);
        addImage = findViewById(R.id.addImage);
        saveStudent = findViewById(R.id.save_btn);
        cancelStudent = findViewById(R.id.cancel_btn);

        studentDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(StudentUpdater.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                studentDOB.setText(day + "/" + (month + 1) + "/" + year);
                            }
                        }, year, month, day );
                datePickerDialog.show();
            }
        });

        // SET UP THIS ACTIVITY TO REUSE CODE FROM STUDENT CREATOR BUT WITH NEW RULES AND ADDITIONS.
        studentID.setText(currentStudentID);
        studentID.setEnabled(false);        //This attribute should never change as is primary key.

        final int ID = Integer.parseInt(currentStudentID); //Parse to int so we can query the database.
        currentStudent = db.StudentDao().getStudent(ID); //We now have our student as an object again.

        List<String> addressArray = Arrays.asList(currentStudent.getAddress().split(" "));
        /* TODO: Make sure user inputs all information. */
        firstName.setText(currentStudent.getFirstName());
        lastName.setText(currentStudent.getLastName());

        // Add the profile image to the view.
        File image = new File(currentStudent.getProfilePicture());
        if (image.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(image.getAbsolutePath());
            addImage.setImageBitmap(myBitmap);
        }

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentUpdater.this, GalleryActivity.class);
                intent.putExtra("STUDENT_ID", Integer.toString(currentStudent.getStudent_ID()));
                startActivity(intent);
            }
        });

        if (addressArray.size() > 5) {
            stud_street.setText(addressArray.get(0) + " " + addressArray.get(1) + " " + addressArray.get(2));
            stud_city.setText(addressArray.get(3));
            stud_state.setText(addressArray.get(4));
            stud_post.setText(addressArray.get(5));
        } else {
            String address = "";
            for (String data: addressArray) {
                address += data + " ";
            }
            stud_street.setText(address);
        }

        studentDOB.setText(currentStudent.getDob());
        stud_course.setText(currentStudent.getCourse());

        //Make sure the correct radio is selected upon opening the updater.
        String myGender = currentStudent.getGender();
        switch(myGender) {
            case "Male":
                gender.check(R.id.maleRadio);
                break;
            case "Female":
                gender.check(R.id.femaleRadio);
                break;
            case "Other":
                gender.check(R.id.otherRadio);
                break;
            default:
                break;
        }

        saveStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String wholeAddress = stud_street.getText().toString() + " " +
                        stud_city.getText().toString() + " " +
                        stud_state.getText().toString() + " " +
                        stud_post.getText().toString();
                String genderChoice = "";
                if (gender.getCheckedRadioButtonId()!=-1) {
                    genderChoice = ((RadioButton)findViewById(gender.getCheckedRadioButtonId()))
                            .getText().toString();
                }

                currentStudent = new Student(ID, firstName.getText().toString(),
                        lastName.getText().toString(),
                        wholeAddress,
                        studentDOB.getText().toString(), genderChoice,
                        stud_course.getText().toString(), currentStudent.getProfilePicture());
                db.StudentDao().updateStudent(currentStudent);
                Intent intent = new Intent(StudentUpdater.this, TabActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        cancelStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentUpdater.this, TabActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        /* Give a list of strings to TextViews to allow auto complete option for users.*/

        ArrayAdapter state_adapter = new ArrayAdapter(this,
                android.R.layout.simple_dropdown_item_1line, STATES);
        stud_state.setAdapter(state_adapter);

        ArrayAdapter course_adapter = new ArrayAdapter(this,
                android.R.layout.simple_dropdown_item_1line, COURSES);
        stud_course.setAdapter(course_adapter);

    }
}
